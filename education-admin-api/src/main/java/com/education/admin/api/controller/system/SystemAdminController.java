package com.education.admin.api.controller.system;

import com.education.common.annotation.Param;
import com.education.common.annotation.ParamsType;
import com.education.common.annotation.ParamsValidate;
import com.education.common.annotation.SystemLog;
import com.education.common.base.BaseController;
import com.education.common.model.ModelBeanMap;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.service.system.SystemAdminService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 管理员管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/21 19:48
 */
@RestController
@Api(tags = "管理员管理")
@RequestMapping("/system/admin")
public class SystemAdminController extends BaseController {

    @Autowired
    private SystemAdminService systemAdminService;

    /**
     * 管理员列表
     * @param params
     * @return
     */
    @GetMapping({"", "list"})
    @RequiresPermissions("system:admin:list")
    @ParamsValidate(params = {
        @Param(name = "offset", message = "请传递当前页参数"),
        @Param(name = "limit", message = "请输入每页显示条数")
    })
    @SystemLog(describe = "获取管理员列表")
    public Result list(@RequestParam Map params) {
        return systemAdminService.pagination(params);
    }
    /**
     * 重置密码
     * @param systemAdmin
     * @return
     */
    @PostMapping("updatePassword")
    @ParamsValidate(params = {
       @Param(name = "password", message = "请输入密码"),
       @Param(name = "confirmPassword", message = "请输入确认密码")
    }, paramsType = ParamsType.JSON_DATA)
    @RequiresPermissions("system:admin:updatePassword")
    @SystemLog(describe = "重置密码管理员密码")
    public ResultCode updatePassword(@RequestBody ModelBeanMap systemAdmin) {
        String password = (String)systemAdmin.get("password");
        String confirmPassword = (String)systemAdmin.get("confirmPassword");
        if (!password.equals(confirmPassword)) {
            return new ResultCode(ResultCode.FAIL, "密码与确认密码不一致");
        }
        systemAdmin.remove("confirmPassword");
        return systemAdminService.updatePassword(systemAdmin);
    }

    /**
     * 修改密码
     * @param systemAdmin
     * @return
     */
    @PostMapping("resettingPassword")
    @SystemLog(describe = "修改密码")
    public ResultCode resettingPassword (@RequestBody ModelBeanMap systemAdmin) {
        String newPassword = systemAdmin.getStr("newPassword");
        String confirmPassword = systemAdmin.getStr("confirmPassword");
        if (!newPassword.equals(confirmPassword)) {
            return new ResultCode(ResultCode.FAIL, "密码与确认密码不一致");
        }
        systemAdmin.remove("confirmPassword");
        return systemAdminService.resettingPassword(systemAdmin);
    }

    @DeleteMapping
    @RequiresPermissions({"system:admin:deleteById"})
    @SystemLog(describe = "删除管理员")
    public ResultCode deleteById(@RequestBody ModelBeanMap systemAdmin) {
        Integer createType = (Integer)systemAdmin.get("create_type");
        if (createType == ResultCode.SUCCESS) {
            return new ResultCode(ResultCode.FAIL, "您不能删除系统内置管理员");
        }
        systemAdmin.put("operation", "管理员" + systemAdmin.get("login_name"));
        return systemAdminService.deleteById(systemAdmin);
    }

    @GetMapping("findById")
    @SystemLog(describe = "获取管理员详情")
    public Result findById(Integer id) {
        return systemAdminService.findById(id);
    }

    /**
     * 添加或修改管理员
     * @param params
     * @return
     */
    @PostMapping
    @RequiresPermissions(value = {"system:admin:save", "system:admin:update"}, logical = Logical.OR)
    @SystemLog(describe = "添加或修改管理员")
    public ResultCode saveOrUpdate(@RequestBody Map params) {
        Integer createType = (Integer)params.get("create_type");
        if (createType != null && createType == ResultCode.SUCCESS) {
            return new ResultCode(ResultCode.FAIL, "您不能编辑系统内置管理员");
        }
        return systemAdminService.saveOrUpdate(params);
    }
}
