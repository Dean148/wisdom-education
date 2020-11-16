package com.education.service.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.common.model.ModelBeanMap;
import com.education.common.utils.MapTreeUtils;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import com.education.mapper.system.SystemMenuMapper;
import com.education.mapper.system.SystemRoleMenuMapper;
import com.education.model.dto.MenuTree;
import com.education.model.dto.RoleMenuDto;
import com.education.model.entity.SystemRoleMenu;
import com.education.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 15:57
 */
@Service
public class SystemRoleMenuService extends BaseService<SystemRoleMenuMapper, SystemRoleMenu> {


    public int deleteByRoleId(Integer roleId) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(SystemRoleMenu.class).eq(SystemRoleMenu::getRoleId, roleId);
        return baseMapper.delete(queryWrapper);
    }

    /*@Autowired
    private SystemMenuMapper systemMenuMapper;

    *//**
     * @param roleId
     * @return
     *//*
    public List<Integer> getMenuListByRoleId(Integer roleId) {
        List<ModelBeanMap> menuList = mapper.getByRoleId(roleId);
        List<Integer> ids = new ArrayList<>();
        for (ModelBeanMap menu : menuList) {
            Integer menuId = (Integer)menu.get("id");
            if (!((Integer)menu.get("parent_id") == ResultCode.FAIL)) {//是否父级菜单
                List<ModelBeanMap> parentList = MapTreeUtils.getChildrenTree(menuList, menuId);
                if (ObjectUtils.isEmpty(parentList)) {
                    ids.add(menuId);
                }
            }
        }
        return ids;
    }*/
}
