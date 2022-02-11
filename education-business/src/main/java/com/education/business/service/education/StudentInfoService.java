package com.education.business.service.education;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.auth.AuthUtil;
import com.education.auth.LoginToken;
import com.education.business.mapper.education.StudentInfoMapper;
import com.education.business.service.BaseService;
import com.education.business.service.system.WebsiteConfigService;
import com.education.business.session.StudentSession;
import com.education.common.constants.AuthConstants;
import com.education.common.constants.CacheKey;
import com.education.common.constants.CacheTime;
import com.education.common.exception.BusinessException;
import com.education.common.model.JwtToken;
import com.education.common.model.PageInfo;
import com.education.common.model.StudentInfoImport;
import com.education.common.utils.*;
import com.education.model.dto.StudentInfoDto;
import com.education.model.entity.GradeInfo;
import com.education.model.entity.StudentInfo;
import com.education.model.entity.WebsiteConfig;
import com.education.model.request.PageParam;
import com.education.model.request.UserLoginRequest;
import com.jfinal.kit.Kv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 学员管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 18:16
 */
@Service
public class StudentInfoService extends BaseService<StudentInfoMapper, StudentInfo> {

    @Autowired
    private GradeInfoService gradeInfoService;
    @Autowired
    private JwtToken jwtToken;
    @Resource
    private WebsiteConfigService websiteConfigService;

    public PageInfo<StudentInfoDto> selectPageList(PageParam pageParam, StudentInfo studentInfo) {
        Page<StudentInfoDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectPageList(page, studentInfo));
    }

    @Override
    public boolean saveOrUpdate(StudentInfo studentInfo) {
        if (studentInfo.getId() == null) {
            String encrypt = PasswordUtil.createEncrypt();
            String password = PasswordUtil.createPassword(encrypt, studentInfo.getPassword()); //生成默认密码
            studentInfo.setPassword(password);
            studentInfo.setEncrypt(encrypt);
        }
        return super.saveOrUpdate(studentInfo);
    }

    public void updatePassword(StudentInfoDto studentInfoDto) {
        String password = studentInfoDto.getPassword();
        String encrypt = studentInfoDto.getEncrypt();
        password = PasswordUtil.createPassword(encrypt, password);
        studentInfoDto.setPassword(password);
        super.updateById(studentInfoDto);
    }

    public int importStudentFromExcel(List<StudentInfoImport> studentList) throws Exception {
        List<GradeInfo> gradeInfoList = gradeInfoService.list();
        Map<String, Integer> gradeInfoMap = new HashMap<>();
        gradeInfoList.forEach(gradeInfo -> {
            gradeInfoMap.put(gradeInfo.getName(), gradeInfo.getId());
        });
        List<StudentInfo> studentInfoList = new ArrayList<>();
        Date now = new Date();
        for (StudentInfoImport studentInfoImport : studentList) {
            StudentInfo studentInfo = new StudentInfo();
            Integer gradeInfoId = gradeInfoMap.get(studentInfoImport.getGradeName());
            if (ObjectUtils.isEmpty(gradeInfoId)) {
                continue;
            }
            studentInfo.setMobile(studentInfoImport.getMobile());
            studentInfo.setGradeInfoId(gradeInfoId);
            studentInfo.setSex("男".equals(studentInfo.getSex()) ? ResultCode.SUCCESS : ResultCode.FAIL);
            String name = studentInfoImport.getName();
            studentInfo.setName(name);
            studentInfo.setAge(studentInfoImport.getAge());
            studentInfo.setAddress(studentInfoImport.getAddress());
            String loginName = SpellUtils.getSpellHeadChar(name); // 获取登录名
            String encrypt = PasswordUtil.createEncrypt();
            String password = PasswordUtil.createPassword(encrypt, loginName); //生成默认密码
            studentInfo.setPassword(password);
            studentInfo.setEncrypt(encrypt);
            studentInfo.setLoginName(loginName);
            studentInfo.setMotherName(studentInfoImport.getMotherName());
            studentInfo.setFatherName(studentInfoImport.getFatherName());
            studentInfo.setCreateDate(now);
            studentInfoList.add(studentInfo);
        }
        super.saveBatch(studentInfoList);

        return studentInfoList.size();
    }

    /**
     * 缓存学员登录信息
     * @param studentId
     */
    public void updateLoginInfo(Integer studentId, Integer loginCount) {
        StudentInfo studentInfo = new StudentInfo();
        Date now = new Date();
        studentInfo.setLastLoginTime(now);
        studentInfo.setId(studentId);
        studentInfo.setLoginIp(IpUtils.getAddressIp(RequestUtils.getRequest()));
        studentInfo.setLoginCount(++loginCount);
        studentInfo.setUpdateDate(now);
        super.updateById(studentInfo);
    }

    public ResultCode updatePassword(String password, String newPassword, String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            return new ResultCode(ResultCode.FAIL, "新密码与确认密码不一致");
        }

        // 验证新密码是否正确
        StudentInfo studentInfo = super.selectFirst(Wrappers.<StudentInfo>lambdaQuery()
                .select(StudentInfo::getPassword)
                .select(StudentInfo::getEncrypt)
                .eq(StudentInfo::getId, getStudentId()));
        String encrypt = studentInfo.getEncrypt();
        String dataBasePassword = PasswordUtil.createPassword(encrypt, password);
        if (dataBasePassword.equals(password)) {
            return new ResultCode(ResultCode.FAIL, "原始密码错误");
        }

        password = PasswordUtil.createPassword(encrypt, password);
        LambdaUpdateWrapper updateWrapper = Wrappers.lambdaUpdate(StudentInfo.class)
                .set(StudentInfo::getPassword, password)
                .eq(StudentInfo::getId, studentInfo.getId());
        super.update(updateWrapper);
        return new ResultCode(ResultCode.SUCCESS, "密码修改成功,退出后请使用新密码进行登录");
    }

    public boolean updateInfo(StudentInfo studentInfo) {
        studentInfo.setId(getStudentId());
        return super.updateById(studentInfo);
    }

    public void updateSocketSessionId(Integer studentId, String socketSessionId) {
        LambdaUpdateWrapper updateWrapper = Wrappers.lambdaUpdate(StudentInfo.class)
                .set(StudentInfo::getSocketSessionId, socketSessionId)
                .eq(StudentInfo::getId, studentId);
        super.update(updateWrapper);
    }

    public String getStudentSocketSessionId(Integer studentId) {
        StudentInfo studentInfo = this.getOne(Wrappers.<StudentInfo>lambdaQuery()
                .select(StudentInfo::getSocketSessionId)
                .eq(StudentInfo::getId, studentId));
        return studentInfo.getSocketSessionId();
    }
}
