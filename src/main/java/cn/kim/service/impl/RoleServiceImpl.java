package cn.kim.service.impl;

import cn.kim.common.attr.MagicValue;
import cn.kim.common.attr.ParamTypeResolve;
import cn.kim.common.attr.TableName;
import cn.kim.common.eu.NameSpace;
import cn.kim.entity.Tree;
import cn.kim.entity.TreeState;
import cn.kim.exception.CustomException;
import cn.kim.service.RoleService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 余庚鑫 on 2018/3/27
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
    @Override
    public Map<String, Object> selectRole(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", mapParam.get("ID"));
        return baseDao.selectOne(NameSpace.RoleMapper, "selectRole", paramMap);
    }

    @Override
    public List<Map<String, Object>> selectRoleList(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));
        return baseDao.selectList(NameSpace.RoleMapper, "selectRole", paramMap);
    }

    @Override
    public List<Tree> selectRoleListTree(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("IS_STATUS", STATUS_SUCCESS);
        List<Map<String, Object>> roles = this.selectRoleList(paramMap);

        String roleId = toString(mapParam.get("SR_ID"));
        Map<String, String> operatorRoleIds = Maps.newHashMapWithExpectedSize(1);
        operatorRoleIds.put(roleId, roleId);
        //转为TREE 并选中已经选择的按钮
        return getOperatorRoleTree(roles, operatorRoleIds);
    }

    @Override
    public Map<String, Object> selectRoleMenu(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(3);
        paramMap.put("ID", mapParam.get("ID"));
        paramMap.put("SR_ID", mapParam.get("SR_ID"));
        paramMap.put("SM_ID", mapParam.get("SM_ID"));
        return baseDao.selectOne(NameSpace.RoleMapper, "selectRoleMenu", paramMap);
    }

    /**
     * 获取菜单树并且根据角色选中菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Map<String, Object>> selectMenuTreeByRoleId(String roleId) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("SR_ID", roleId);
        List<Map<String, Object>> roleMenus = baseDao.selectList(NameSpace.RoleMapper, "selectRoleMenu", paramMap);
        //吧角色菜单转为MAP格式
        Map<String, String> roleMenuIds = toMapKey(roleMenus, "SM_ID");

        return getOperatorMenuTree(baseDao, NameSpace.MenuMapper, "selectMenu", null, "0", null, null, roleMenuIds);
    }

    /**
     * 获取角色菜单按钮
     *
     * @param mapParam
     * @return
     */
    @Override
    public List<Tree> selectRoleTree(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
        paramMap.put("SM_ID", mapParam.get("SM_ID"));
        List<Map<String, Object>> menuButtons = baseDao.selectList(NameSpace.MenuMapper, "selectMenuButton", paramMap);
        paramMap.clear();
        paramMap.put("SR_ID", mapParam.get("SR_ID"));
        paramMap.put("SM_ID", mapParam.get("SM_ID"));
        Map<String, Object> roleMenu = baseDao.selectOne(NameSpace.RoleMapper, "selectRoleMenu", paramMap);
        List<Map<String, Object>> nowMenuButtons = new ArrayList<>();
        if (!isEmpty(roleMenu)) {
            paramMap.clear();
            paramMap.put("SRM_ID", roleMenu.get("ID"));
            nowMenuButtons = baseDao.selectList(NameSpace.RoleMapper, "selectRoleButton", paramMap);
        }
        //吧角色菜单按钮转为MAP格式
        Map<String, String> roleButtonIds = toMapKey(nowMenuButtons, "SB_ID");

        //转为TREE 并选中已经选择的按钮
        return getMenuButtonTree(menuButtons, roleButtonIds);
    }

    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateRole(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(8);
            String id = toString(mapParam.get("ID"));
            //检测角色编码是否重复
            paramMap.put("NOT_ID", id);
            paramMap.put("SR_CODE", mapParam.get("SR_CODE"));
            int count = baseDao.selectOne(NameSpace.RoleMapper, "selectRoleCount", paramMap);
            if (count > 0) {
                throw new CustomException("角色编码重复,请检查!");
            }

            paramMap.clear();
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_ROLE);
            paramMap.put("ID", mapParam.get("ID"));
            paramMap.put("SR_NAME", mapParam.get("SR_NAME"));
            paramMap.put("SR_CODE", mapParam.get("SR_CODE"));
            paramMap.put("SR_EXPLAIN", mapParam.get("SR_EXPLAIN"));
            paramMap.put("SR_REMARK", mapParam.get("SR_REMARK"));
            paramMap.put("SR_TYPE", mapParam.get("SR_TYPE"));
            paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));
            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);

                paramMap.put("IS_STATUS", STATUS_SUCCESS);
                baseDao.insert(NameSpace.RoleMapper, "insertRole", paramMap);
                resultMap.put(MagicValue.LOG, "添加角色:" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectRole(oldMap);

                baseDao.update(NameSpace.RoleMapper, "updateRole", paramMap);
                resultMap.put(MagicValue.LOG, "更新角色,更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
            }
            status = STATUS_SUCCESS;
            desc = SAVE_SUCCESS;

            resultMap.put("ID", id);
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }

    /**
     * 变更角色状态
     *
     * @param mapParam
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> changeRoleStatus(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
            String id = toString(mapParam.get("ID"));

            paramMap.put("ID", id);
            paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));

            Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
            oldMap.put("ID", id);
            oldMap = selectRole(oldMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_ROLE);
            baseDao.update(NameSpace.RoleMapper, "updateRole", paramMap);
            resultMap.put(MagicValue.LOG, "更新角色状态,角色:" + toString(oldMap.get("SR_NAME")) + ",状态更新为:" + ParamTypeResolve.statusExplain(mapParam.get("IS_STATUS")));

            status = STATUS_SUCCESS;
            desc = SAVE_SUCCESS;

            resultMap.put("ID", id);
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }

    /**
     * 保存角色关联菜单
     *
     * @param mapParam
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> updateRoleMenuPermission(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
            String id = toString(mapParam.get("ID"));
            //选中的菜单ID
            String[] menuIds = toString(mapParam.get("MENUIDS")).split(SERVICE_SPLIT);

            //查询角色
            paramMap.put("ID", id);
            Map<String, Object> role = selectRole(paramMap);

            //查询旧角色菜单转为MAP格式
            paramMap.clear();
            paramMap.put("SR_ID", id);
            Map<String, String> oldRoleMenuIds = toMapKey(baseDao.selectList(NameSpace.RoleMapper, "selectRoleMenu", paramMap), "SM_ID");
            //新菜单权限
            Map<String, String> newRoleMenuIds = Arrays.stream(menuIds).collect(Collectors.toMap(String::toString, String::toString));

            //原来没有的就添加
            if (!isEmpty(newRoleMenuIds)) {
                for (String menuId : newRoleMenuIds.keySet()) {
                    if (!isEmpty(menuId)) {
                        if (isEmpty(oldRoleMenuIds) || !oldRoleMenuIds.containsKey(menuId)) {
                            paramMap.clear();
                            paramMap.put("ID", getId());
                            paramMap.put("SR_ID", id);
                            paramMap.put("SM_ID", menuId);
                            //添加
                            baseDao.insert(NameSpace.RoleMapper, "insertRoleMenu", paramMap);
                        }
                    }
                }
            }

            //新的菜单id 旧的还存在的就要删除
            if (!isEmpty(oldRoleMenuIds)) {
                for (String oldMenuId : oldRoleMenuIds.keySet()) {
                    if (!isEmpty(oldMenuId)) {
                        if (isEmpty(newRoleMenuIds) || !newRoleMenuIds.containsKey(oldMenuId)) {
                            paramMap.clear();
                            paramMap.put("SR_ID", id);
                            paramMap.put("SM_ID", oldMenuId);
                            Map<String, Object> roleMenu = selectRoleMenu(paramMap);
                            //删除sysRoleButton表
                            paramMap.clear();
                            paramMap.put("SRM_ID", roleMenu.get("ID"));
                            baseDao.delete(NameSpace.RoleMapper, "deleteRoleButton", paramMap);
                            //删除
                            paramMap.clear();
                            paramMap.put("ID", roleMenu.get("ID"));
                            baseDao.delete(NameSpace.RoleMapper, "deleteRoleMenu", paramMap);
                        }
                    }
                }
            }
            //清除缓存
            customRealm.clearCached();

            status = STATUS_SUCCESS;
            desc = SAVE_SUCCESS;

            resultMap.put("ID", id);
            resultMap.put(MagicValue.LOG, "角色:" + role.get("SR_NAME") + ",旧菜单:" + toString(toKeySet(oldRoleMenuIds)) + ",新菜单:" + toString(toKeySet(newRoleMenuIds)));
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }

    /**
     * 保存角色菜单关联按钮
     *
     * @param mapParam
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> updateRoleButtonPermission(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(3);
            String id = toString(mapParam.get("ID"));
            String menuId = toString(mapParam.get("SM_ID"));
            //选中的按钮ID
            String[] buttonIds = toString(mapParam.get("BUTTONIDS")).split(SERVICE_SPLIT);

            //查询角色
            paramMap.clear();
            paramMap.put("ID", id);
            Map<String, Object> role = selectRole(paramMap);
            //查询sys_role_menu表
            paramMap.clear();
            paramMap.put("SR_ID", id);
            paramMap.put("SM_ID", menuId);
            Map<String, Object> roleMenu = selectRoleMenu(paramMap);
            if (isEmpty(roleMenu)) {
                throw new CustomException("还未保存菜单,不能保存菜单关联的按钮!");
            }
            String roleMenuId = toString(roleMenu.get("ID"));

            paramMap.clear();
            paramMap.put("SRM_ID", roleMenuId);
            //吧角色菜单转为MAP格式
            Map<String, String> oldRoleButtonIds = toMapKey(baseDao.selectList(NameSpace.RoleMapper, "selectRoleButton", paramMap), "SB_ID");
            //新菜单权限
            Map<String, String> newRoleButtonIds = Arrays.stream(buttonIds).collect(Collectors.toMap(String::toString, String::toString));

            //原来没有的就添加
            if (!isEmpty(newRoleButtonIds)) {
                for (String buttonId : newRoleButtonIds.keySet()) {
                    if (!isEmpty(buttonId)) {
                        if (isEmpty(oldRoleButtonIds) || !oldRoleButtonIds.containsKey(buttonId)) {
                            paramMap.clear();
                            paramMap.put("ID", getId());
                            paramMap.put("SRM_ID", roleMenuId);
                            paramMap.put("SB_ID", buttonId);
                            //添加
                            baseDao.insert(NameSpace.RoleMapper, "insertRoleButton", paramMap);
                        }
                    }
                }
            }
            //新的菜单id 旧的还存在的就要删除
            if (!isEmpty(oldRoleButtonIds)) {
                for (String oldButtonId : oldRoleButtonIds.keySet()) {
                    if (!isEmpty(oldButtonId)) {
                        if (isEmpty(newRoleButtonIds) || !newRoleButtonIds.containsKey(oldButtonId)) {
                            //删除
                            paramMap.clear();
                            paramMap.put("SRM_ID", roleMenu.get("ID"));
                            paramMap.put("SB_ID", oldButtonId);
                            baseDao.delete(NameSpace.RoleMapper, "deleteRoleButton", paramMap);
                        }
                    }
                }
            }

            //清除缓存
            customRealm.clearCached();

            status = STATUS_SUCCESS;
            desc = UPDATE_SUCCESS;

            resultMap.put("ID", id);
            resultMap.put(MagicValue.LOG, "角色:" + role.get("SR_NAME") + ",菜单名称:" + roleMenu.get("SM_NAME") + ",旧按钮:" + toString(toKeySet(oldRoleButtonIds)) + ",新按钮:" + toString(toKeySet(newRoleButtonIds)));
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> deleteRole(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            if (isEmpty(mapParam.get("ID"))) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            String id = toString(mapParam.get("ID"));

            paramMap.put("SR_ID", id);
            //删除角色关联菜单
            baseDao.delete(NameSpace.RoleMapper, "deleteRoleMenu", paramMap);
            //删除角色关联按钮
            baseDao.delete(NameSpace.RoleMapper, "deleteRoleButton", paramMap);
            //删除角色关联用户表
            baseDao.delete(NameSpace.OperatorMapper, "deleteOperatorRole", paramMap);
            //删除角色表
            paramMap.clear();
            paramMap.put("ID", id);
            Map<String, Object> oldMap = selectRole(paramMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_ROLE);
            baseDao.delete(NameSpace.RoleMapper, "deleteRole", paramMap);

            resultMap.put(MagicValue.LOG, "删除角色,信息:" + toString(oldMap));
            status = STATUS_SUCCESS;
            desc = DELETE_SUCCESS;
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }

    /**
     * 转为TREE 并选中已经选择的按钮
     *
     * @param buttons
     * @param roleButtons
     * @return
     */
    public List<Tree> getMenuButtonTree(List<Map<String, Object>> buttons, Map<String, String> roleButtons) {
        List<Tree> menuButtonTree = new ArrayList<>();
        if (!isEmpty(buttons)) {
            buttons.forEach(button -> {
                String id = toString(button.get("SB_ID"));

                Tree tree = new Tree();
                tree.setId(id);
                tree.setText(toString(button.get("SB_NAME")));
                tree.setTags(new String[]{toString(button.get("SB_CODE"))});

                TreeState state = new TreeState();
                //是否选中
                if (!isEmpty(roleButtons) && roleButtons.containsKey(id)) {
                    state.setChecked(true);
                    //选中的设置打开
                    state.setExpanded(true);
                }

                //设置状态
                tree.setState(state);

                menuButtonTree.add(tree);
            });
        }

        return menuButtonTree;
    }
}
