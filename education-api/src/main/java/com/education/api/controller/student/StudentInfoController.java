package com.education.api.controller.student;

import com.education.auth.AuthUtil;
import com.education.auth.LoginToken;
import com.education.business.service.education.StudentInfoService;
import com.education.business.session.StudentSession;
import com.education.common.annotation.Param;
import com.education.common.annotation.ParamsType;
import com.education.common.annotation.ParamsValidate;
import com.education.common.base.BaseController;
import com.education.common.constants.AuthConstants;
import com.education.common.enums.LoginEnum;
import com.education.common.utils.*;
import com.education.model.dto.StudentInfoDto;
import com.education.model.entity.StudentInfo;
import com.education.model.request.UserLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 学员登录接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 20:58
 */
@RequestMapping("/student")
@RestController("student-controller")
public class StudentInfoController extends BaseController {

    @Autowired
    private StudentInfoService studentInfoService;

    /**
     * 学员登录接口
     * @param userLoginRequest
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody UserLoginRequest userLoginRequest, HttpServletResponse response) {
        LoginToken loginToken = new LoginToken(userLoginRequest.getUserName(), userLoginRequest.getPassword(), LoginEnum.STUDENT.getValue(),
                userLoginRequest.isChecked());
        loginToken.setDeviceType(userLoginRequest.getDeviceType());
        StudentSession session = AuthUtil.login(loginToken);
        response.addHeader(AuthConstants.AUTHORIZATION, session.getToken());
        return Result.success(ResultCode.SUCCESS, "登录成功", session);
    }

    @PostMapping("logout")
    public Result logout() {
        AuthUtil.logout(LoginEnum.STUDENT.getValue());
        return Result.success(ResultCode.SUCCESS, "退出成功");
    }

    /**
     * 修改新密码
     * @param studentInfoDto
     * @return
     */
    @PostMapping("updatePassword")
    public Result updatePassword(@RequestBody StudentInfoDto studentInfoDto) {
        return Result.success(studentInfoService.updatePassword(studentInfoDto.getPassword(),
                studentInfoDto.getNewPassword(), studentInfoDto.getConfirmPassword()));
    }

    /**
     * 修改个人资料
     * @param studentInfo
     * @return
     */
    @PostMapping("updateStudentInfo")
    @ParamsValidate(params = {
        @Param(name = "name", message = "请输入姓名"),
        @Param(name = "age", message = "请输入年龄"),
        @Param(name = "sex", message = "请选择性别"),
        @Param(name = "mobile", message = "请输入手机号", regexp = RegexUtils.MOBILE_REGEX, regexpMessage = "非法手机号")
    }, paramsType = ParamsType.JSON_DATA)
    public Result updateInfo(@RequestBody StudentInfo studentInfo) {
        return Result.success(studentInfoService.updateInfo(studentInfo));
    }
}
