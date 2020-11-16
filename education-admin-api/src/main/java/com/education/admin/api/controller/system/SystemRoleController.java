package com.education.admin.api.controller.system;

import com.education.common.annotation.SystemLog;
import com.education.common.base.BaseController;
import com.education.common.constants.EnumConstants;
import com.education.common.model.PageInfo;
import com.education.common.utils.Result;
import com.education.model.dto.RoleMenuDto;
import com.education.model.entity.SystemRole;
import com.education.model.request.PageParam;
import com.education.service.system.SystemRoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 角色管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/22 21:39
 */
@RestController
@RequestMapping("/system/role")
public class SystemRoleController extends BaseController {

    @Autowired
    private SystemRoleService systemRoleService;

    /**
     * 管理员列表
     * @param pageParam
     * @param systemRole
     * @return
     */
    @GetMapping
    @SystemLog(describe = "获取管理员列表")
    @RequiresPermissions("system:role:list")
    public Result<PageInfo<SystemRole>> list(PageParam pageParam, SystemRole systemRole) {
        return Result.success(systemRoleService.listPage(pageParam, systemRole));
    }

    /**
     * 修改角色权限
     * @param roleMenuDto
     * @return
     */
    @PostMapping("saveRolePermission")
    @RequiresPermissions("system:role:saveRolePermission")
    public Result saveRolePermission(@RequestBody RoleMenuDto roleMenuDto) {
        systemRoleService.saveRolePermission(roleMenuDto);
        return Result.success("权限设置成功");
    }

    /**
     * 保存或修改角色
     * @param systemRole
     * @return
     */
    @PostMapping
    @RequiresPermissions(value = {"system:role:save", "system:role:update"}, logical = Logical.OR)
    public Result saveOrUpdate(@RequestBody SystemRole systemRole) {
        systemRoleService.saveOrUpdate(systemRole);
        return Result.success();
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @RequiresPermissions("system:role:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        SystemRole systemRole = systemRoleService.getById(id);
        if (systemRole.getCreateType() == EnumConstants.CreateType.SYSTEM_CREATE.getValue()) {
            return Result.success("您不能删除系统内置角色");
        }
        systemRoleService.removeById(id);
        return Result.success("删除角色" + systemRole.getName() + "成功");
    }
}
