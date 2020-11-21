package com.education.api.controller.student;

import com.education.business.service.education.StudentInfoService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.request.UserLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result login(@RequestBody UserLoginRequest userLoginRequest) {
        return studentInfoService.doLogin(userLoginRequest);
    }

}
