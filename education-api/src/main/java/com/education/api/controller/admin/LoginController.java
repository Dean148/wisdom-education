package com.education.api.controller.admin;

import com.education.auth.AuthUtil;
import com.education.auth.LoginToken;
import com.education.business.session.AdminUserSession;
import com.education.business.task.TaskManager;
import com.education.business.task.TaskParam;
import com.education.business.task.UserLoginSuccessListener;
import com.education.common.annotation.*;
import com.education.common.base.BaseController;
import com.education.common.constants.*;
import com.education.common.enums.LoginEnum;
import com.education.common.utils.RequestUtils;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.request.UserLoginRequest;
import com.jfinal.kit.Kv;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * 系统登录
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/22 22:12
 */
@RestController
@RequestMapping("/system")
@Api(value = "管理员登录登出接口", tags = "系统登录登出接口")
public class LoginController extends BaseController {

    @Resource
    private TaskManager taskManager;

    /**
     * 管理员登录接口
     * @param userLoginRequest
     * @return
     */
    @PostMapping("/login")
    @SystemLog(describe = "登录管理系统")
    @FormLimit
    public Result<Map> login(@RequestBody @Validated UserLoginRequest userLoginRequest, HttpServletResponse response) {
       /* String codeKey = userLoginRequest.getKey();
        String imageCode = userLoginRequest.getCode();
        String cacheCode = cacheBean.get(codeKey);
        if (!imageCode.equalsIgnoreCase(cacheCode)) {
            return Result.fail(ResultCode.CODE_ERROR, "验证码输入错误");
        }*/
        LoginToken loginToken = new LoginToken(userLoginRequest.getUserName(), userLoginRequest.getPassword(),
                LoginEnum.ADMIN.getValue(), false);
        AdminUserSession userSession = AuthUtil.login(loginToken);
        response.addHeader(AuthConstants.AUTHORIZATION, userSession.getToken());
        Kv userInfo = Kv.create().set("id", userSession.getId())
                .set("permissionList", userSession.getPermissionList())
                .set("menuList", userSession.getMenuTreeList())
                .set("login_name", userLoginRequest.getUserName());
        Map resultMap = new HashMap<>();
        resultMap.put("userInfo", userInfo);
        // 异步更新用户相关信息
        TaskParam taskParam = new TaskParam(UserLoginSuccessListener.class);
        taskParam.put("userSession", userSession);
        taskParam.put("request", RequestUtils.getRequest());
        taskManager.pushTask(taskParam);
        return Result.success(ResultCode.SUCCESS, "登录成功", resultMap);
    }

    /**
     * 系统退出
     * @return
     */
    @PostMapping("logout")
    @SystemLog(describe = "退出管理系统")
    @FormLimit
    public Result logout() {
        AuthUtil.logout();
        return Result.success(ResultCode.SUCCESS, "退出成功");
    }
}
