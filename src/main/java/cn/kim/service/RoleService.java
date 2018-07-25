package cn.kim.service;

import cn.kim.entity.Tree;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/27
 */
public interface RoleService extends BaseService {
    Map<String, Object> selectRole(Map<String, Object> mapParam);

    List<Map<String, Object>> selectRoleList(Map<String, Object> mapParam);

    List<Tree> selectRoleListTree(Map<String, Object> mapParam);

    Map<String, Object> selectRoleMenu(Map<String, Object> mapParam);

    /**
     * 获取菜单树并且根据角色选中菜单
     *
     * @param roleId
     * @return
     */
    List<Map<String, Object>> selectMenuTreeByRoleId(String roleId);

    /**
     * 获取角色菜单按钮
     *
     * @param mapParam
     * @return
     */
    List<Tree> selectRoleTree(Map<String, Object> mapParam);

    Map<String, Object> insertAndUpdateRole(Map<String, Object> mapParam);

    Map<String, Object> changeRoleStatus(Map<String, Object> mapParam);

    /**
     * 保存角色关联菜单
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> updateRoleMenuPermission(Map<String, Object> mapParam);

    /**
     * 保存角色菜单关联按钮
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> updateRoleButtonPermission(Map<String, Object> mapParam);

    Map<String, Object> deleteRole(Map<String, Object> mapParam);

}
