package com.education.admin.api.controller.system;

import com.education.common.utils.Result;
import com.education.model.entity.SystemMenu;
import com.education.service.core.ApiController;
import com.education.service.system.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 菜单管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/22 21:44
 */
@RestController
@RequestMapping("/system/menu")
public class SystemMenuController extends ApiController {

    @Autowired
    private SystemMenuService systemMenuService;

    /**
     * 菜单列表
     * @return
     */
    @GetMapping("menuTreeList")
   // @RequiresPermissions("system:menu:list")
    public Result menuTreeList() {
        return Result.success(systemMenuService.selectMenuTreeList());
    }

    /**
     * 菜单详情
     * @param id
     * @return
     */
    @GetMapping("selectById")
    public Result selectById(Integer id) {
        return Result.success(systemMenuService.selectById(id));
    }

    /**
     * 保存或修改菜单
     * @param systemMenu
     * @return
     */
    @PostMapping
    public Result saveOrUpdate(@RequestBody SystemMenu systemMenu) {
        systemMenuService.saveOrUpdate(systemMenu);
        return Result.success();
    }

    /**
     * 根据id 删除菜单
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable Integer id) {
        systemMenuService.removeById(id);
        return Result.success();
    }


  /*  *//**
     * 获取角色拥有菜单id 集合
     * @param roleId
     * @return
     *//*
    @GetMapping("getMenuByRole")
    public Result getMenuByRole(Integer roleId) {
        return Result.success(systemRoleMenuService.getMenuListByRoleId(roleId));
    }

    @GetMapping(value = "getMenuByParent")
    public Result getMenuByParent() {
        return Result.success(systemMenuService.treeMenu());
    }

    @GetMapping({"", "list"})
    @RequiresPermissions("system:menu:list")
    public Result list(@RequestParam Map params) {
        return systemMenuService.pagination(params);
    }

    @GetMapping("findById")
    public Result findById(Integer id) {
        return systemMenuService.findById(id);
    }

    @DeleteMapping
    @RequiresPermissions("system:menu:deleteById")
    public ResultCode deleteById(@RequestBody ModelBeanMap menuMap) {
        Integer createType = menuMap.getInt("create_type");
        if (createType == ResultCode.SUCCESS) {
            return new ResultCode(ResultCode.FAIL, "您不能删除系统内置菜单");
        }
        return systemMenuService.deleteById(menuMap.getInt("value"));
    }

    @PostMapping("saveOrUpdate")
    @RequiresPermissions(value = {"system:menu:save", "system:menu:update"}, logical = Logical.OR)
    public Result saveOrUpdate(@RequestBody ModelBeanMap menuMap) {
        return systemMenuService.saveOrUpdate(menuMap);
    }*/
}
