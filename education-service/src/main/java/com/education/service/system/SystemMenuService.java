package com.education.service.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.common.utils.TreeUtils;
import com.education.mapper.system.SystemMenuMapper;
import com.education.model.dto.MenuTree;
import com.education.model.entity.SystemAdmin;
import com.education.model.entity.SystemMenu;
import com.education.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单管理service
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 15:38
 */
@Service
public class SystemMenuService extends BaseService<SystemMenuMapper, SystemMenu> {

    /**
     * 获取角色菜单列表
     * @param roleIds
     * @return
     */
    public List<SystemMenu> getMenuListByRoles(List<Integer> roleIds) {
        return baseMapper.getMenuListByRoles(roleIds);
    }

    /**
     * 树菜单列表
     * @return
     */
    public List<MenuTree> selectMenuTreeList() {
        return TreeUtils.buildTreeData(baseMapper.getTreeMenuList());
    }

    /**
     * 树菜单详情
     * @param id
     * @return
     */
    public MenuTree selectById(Integer id) {
        MenuTree menuTree = baseMapper.selectMenuTreeById(id);
        List<MenuTree> menuTreeList = baseMapper.getTreeMenuList();
        Integer parentId = menuTree.getParentId();
        List<MenuTree> parentMenuList = TreeUtils.getParentList(menuTreeList, parentId);
        List<Integer> parentIds = parentMenuList.stream()
                .map(MenuTree::getId)
                .collect(Collectors.toList());
        menuTree.setParentIds(parentIds);
        return menuTree;
    }
/*

    public List<ModelBeanMap> treeMenu() {
        List<ModelBeanMap> menuList = mapper.treeList();
        return MapTreeUtils.buildTreeData(menuList);
    }

    public Result findById(Integer id) {
        Map menuMap = mapper.findById(id); //
        List<ModelBeanMap> menuList = mapper.queryList(new HashMap());
        int parentId = (Integer)menuMap.get("parent_id");
        List<ModelBeanMap> parentMenuList = MapTreeUtils.getParentList(menuList, parentId);
        List<Integer> parentIds = parentMenuList.stream()
                .map(item -> item.getInt("id"))
                .collect(Collectors.toList());
        menuMap.put("parentArrayId", parentIds);
        return Result.success(menuMap);
    }
*/

   /* private List<Integer> getParentIds(int parentId, List<Integer> parentIds) {
        if (parentId != ResultCode.FAIL) {
            Map parentMap = mapper.findById(parentId);
            if (ObjectUtils.isNotEmpty(parentMap)) {
                int newParentId = (Integer)parentMap.get("parent_id");
                parentIds.add((Integer)parentMap.get("id"));
                return getParentIds(newParentId, parentIds);
            }
        }
        return parentIds;
    }
*/
  /*  public List<ModelBeanMap> getMenuByUser() {
        AdminUserSession userSession = getAdminUserSession();
        List<ModelBeanMap> menuList = userSession.getMenuList();
        if (ObjectUtils.isEmpty(menuList)) {
            Integer userId = userSession.getUserId();
            Map params = new HashMap<>();
            if (!userSession.isSuperAdmin()) {
                params.put("adminId", userId);
            }
            menuList = mapper.findMenuByUser(params);
            for (Map menu : menuList) {
                Integer parentId = (Integer)menu.get("id");
                params.put("parentId", parentId);
                List<ModelBeanMap> children = mapper.findByParentIdAndRoleId(params);
                menu.put("children", children);
            }
            userSession.setMenuList(menuList);
        }
        return menuList;
    }*/
}
