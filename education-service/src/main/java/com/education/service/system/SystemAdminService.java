package com.education.service.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.common.constants.EnumConstants;
import com.education.common.exception.BusinessException;
import com.education.common.model.ModelBeanMap;
import com.education.common.model.PageInfo;
import com.education.common.utils.*;
import com.education.mapper.system.SystemAdminMapper;
import com.education.model.dto.AdminRoleDto;
import com.education.model.dto.AdminUserSession;
import com.education.model.dto.MenuTree;
import com.education.model.entity.SystemAdmin;
import com.education.model.entity.SystemAdminRole;
import com.education.model.entity.SystemMenu;
import com.education.model.entity.SystemRole;
import com.education.model.request.PageParam;
import com.education.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 11:25
 */
@Service
@Slf4j
public class SystemAdminService extends BaseService<SystemAdminMapper, SystemAdmin> {

    @Autowired
    private SystemMenuService systemMenuService;
    @Autowired
    private SystemRoleService systemRoleService;
    @Autowired
    private SystemAdminRoleService systemAdminRoleService;

    public PageInfo<AdminRoleDto> listPage(PageParam pageParam, SystemAdmin systemAdmin) {
        Page<AdminRoleDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectPageList(page, systemAdmin));
    }

    public AdminRoleDto selectById(Integer id) {
        return baseMapper.selectById(id);
    }

    /**
     * 加载用户菜单及权限标识
     *
     * @param userSession
     */
    public void loadUserMenuAndPermission(AdminUserSession userSession) {
        List<SystemMenu> menuList = null;
        if (userSession.isSuperAdmin()) {
            menuList = systemMenuService.list();
        } else {
            Integer adminId = userSession.getAdminId();
            List<SystemRole> systemRoleList = systemRoleService.findRoleListByAdminId(adminId);
            userSession.setRoleList(systemRoleList);
            List<Integer> roleIds = systemRoleList.stream()
                    .map(SystemRole::getId)
                    .collect(Collectors.toList());
            menuList = systemMenuService.getMenuListByRoles(roleIds);
        }
        if (ObjectUtils.isNotEmpty(menuList)) {
            List<MenuTree> menuTreeList = new ArrayList<>();
            Set<String> permissionList = new HashSet<>();
            menuList.forEach(menu -> {
                permissionList.add(menu.getPermissions());
                MenuTree menuTree = new MenuTree();
                menuTree.setId(menu.getId());
                menuTree.setParentId(menu.getParentId());
                menuTree.setLabel(menu.getName());
                menuTreeList.add(menuTree);
            });
            userSession.setPermissionList(permissionList);
            userSession.setMenuTreeList(TreeUtils.buildTreeData(menuTreeList));
        }
    }

    public Result login(String loginName, String password) {
        Result result = new Result();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        try {
            subject.login(token);
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("登录成功");
        } catch (Exception e) {
            log.error("登录失败", e);
            result.setCode(ResultCode.FAIL);
            if (e instanceof UnknownAccountException) {
                result.setMessage("用户不存在");
            } else {
                result.setMessage("用户名或密码错误");
            }
        }
        return result;
    }

    @Transactional
    public void saveOrUpdate(AdminRoleDto adminRoleDto) {
        if (ObjectUtils.isEmpty(adminRoleDto.getId())) {
            String password = adminRoleDto.getPassword();
            String confirmPassword = adminRoleDto.getConfirmPassword();
            if (!password.equals(confirmPassword)) {
                throw new BusinessException(new ResultCode(ResultCode.FAIL, "密码与确认密码不一致"));
            }
            String encrypt = Md5Utils.encodeSalt(Md5Utils.generatorKey());
            adminRoleDto.setEncrypt(encrypt);
            password = Md5Utils.getMd5(password, encrypt);
            adminRoleDto.setPassword(password);
        }

        super.saveOrUpdate(adminRoleDto);

        // 保存管理员角色信息
        if (ObjectUtils.isNotEmpty(adminRoleDto.getRoleIds())) {
            List<SystemAdminRole> adminRoleList = new ArrayList<>();
            adminRoleDto.getRoleIds().forEach(roleId -> {
                SystemAdminRole systemAdminRole = new SystemAdminRole();
                systemAdminRole.setAdminId(adminRoleDto.getId());
                systemAdminRole.setRoleId(roleId);
            });

            systemAdminRoleService.deleteByAdminId(adminRoleDto.getId());
            systemAdminRoleService.saveBatch(adminRoleList);
        }
    }

    @Transactional
    public Result deleteById(Integer id) {
        SystemAdmin systemAdmin = super.getById(id);
        if (systemAdmin.isSuperFlag()) {
            return Result.fail(ResultCode.FAIL, "不允许删除超级管理员");
        }
        super.removeById(id);
        systemAdminRoleService.deleteByAdminId(id);
        return Result.success("删除管理员" + systemAdmin.getLoginName() + "成功");
    }

    /**
     * 重置密码
     * @param adminRoleDto
     */
    public void updatePassword(AdminRoleDto adminRoleDto) {
        String password = adminRoleDto.getPassword();
        SystemAdmin systemAdmin = super.getById(adminRoleDto.getId());
        String encrypt = systemAdmin.getEncrypt();
        password = Md5Utils.getMd5(password,  encrypt);
        adminRoleDto.setPassword(password);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("password", password);
        updateWrapper.eq("id", adminRoleDto.getId());
        super.update(updateWrapper);
    }
}

  /*  public ResultCode updatePassword(ModelBeanMap systemAdmin) {
        try {
           *//* String password = systemAdmin.getStr("password");
            Map userMap = mapper.findById(systemAdmin.getInt("id"));
            String encrypt = (String)userMap.get("encrypt");
            password = Md5Utils.getMd5(password,  encrypt);
            systemAdmin.put("password", password);
            int result = super.update(systemAdmin);
            if (result > 0) {
                return new ResultCode(ResultCode.SUCCESS, "密码重置成功");
            }*//*
        } catch (Exception e) {
            log.error("密码修改失败", e);
        }
        return new ResultCode(ResultCode.FAIL, "密码重置失败");
    }

    public ResultCode resettingPassword(ModelBeanMap systemAdmin) {
        try {
            String password = systemAdmin.getStr("password");
            String newPassword = systemAdmin.getStr("newPassword");
            Map userMap = getAdminUser();
            String encrypt = (String)userMap.get("encrypt");
            password = Md5Utils.getMd5(password, encrypt);
            String userPassword = (String)userMap.get("password");
            if (!password.equals(userPassword)) {
                return new ResultCode(ResultCode.FAIL, "密码输入错误");
            }
            password = Md5Utils.getMd5(newPassword, (String)userMap.get("encrypt"));
            systemAdmin.put("password", password);
            systemAdmin.remove("newPassword");
            systemAdmin.put("id", userMap.get("id"));
            int result = this.update(systemAdmin);
            if (result > 0) {
                return new ResultCode(ResultCode.SUCCESS, "密码修改成功, 退出后请使用新密码登录");
            }
        } catch (Exception e) {
            log.error("密码修改失败", e);
        }
        return new ResultCode(ResultCode.FAIL, "密码修改失败");
    }
}*/
