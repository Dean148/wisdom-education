package com.education.api.config.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.auth.realm.LoginAuthRealm;
import com.education.auth.LoginToken;
import com.education.business.service.education.GradeInfoService;
import com.education.business.service.education.StudentInfoService;
import com.education.business.session.StudentSession;
import com.education.business.task.TaskManager;
import com.education.common.enums.LoginEnum;
import com.education.common.exception.BusinessException;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.PasswordUtil;
import com.education.model.entity.GradeInfo;
import com.education.model.entity.StudentInfo;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * @author zengjintao
 * @create_at 2021年11月30日 0030 14:28
 * @since version 1.6.7
 */
@Component
public class StudentLoginRealm implements LoginAuthRealm<StudentSession> {

    @Resource
    private StudentInfoService studentInfoService;
    @Resource
    private GradeInfoService gradeInfoService;
    @Resource
    private TaskManager taskManager;

    @Override
    public StudentSession doLogin(LoginToken loginToken) {
        LambdaQueryWrapper queryWrapper = Wrappers.<StudentInfo>lambdaQuery()
                .eq(StudentInfo::getLoginName, loginToken.getUsername());
        StudentInfo studentInfo = studentInfoService.selectFirst(queryWrapper);
        if (ObjectUtils.isEmpty(studentInfo)) {
            throw new BusinessException("用户不存在");
        }

        String dataBasePassword = studentInfo.getPassword();
        String encrypt = studentInfo.getEncrypt();
        if (!dataBasePassword.equals(PasswordUtil.createPassword(encrypt, loginToken.getPassword()))) {
            throw new BusinessException("用户名或密码错误");
        }

        if (studentInfo.isDisabledFlag()) {
            throw new BusinessException("账号已被禁用");
        }

        GradeInfo gradeInfo = gradeInfoService.getById(studentInfo.getGradeInfoId());
        StudentSession studentSession = new StudentSession(studentInfo.getId());
        studentSession.setName(studentInfo.getName());
        studentSession.setHeadImg(studentInfo.getHeadImg());
        studentSession.setSex(studentInfo.getSex());
        studentSession.setAge(studentInfo.getAge());
        studentSession.setAddress(studentInfo.getAddress());
        studentSession.setMobile(studentInfo.getMobile());
        studentSession.setGradeInfoName(gradeInfo.getName());
        return studentSession;
    }

    @Override
    public String getLoginType() {
        return LoginEnum.STUDENT.getValue();
    }

    @Override
    public void onLoginSuccess(StudentSession userSession) {
        taskManager.pushTask(() -> {
            studentInfoService.updateLoginInfo(userSession.getId());
        });
    }

    @Override
    public void onLogoutSuccess(StudentSession userSession) {

    }

    @Override
    public void onRejectSession(StudentSession userSession) {

    }

    @Override
    public void onLoginFail(String username, Exception e) {

    }
}
