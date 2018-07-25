package cn.kim.service.impl;

import cn.kim.common.attr.MagicValue;
import cn.kim.common.attr.ParamTypeResolve;
import cn.kim.common.attr.TableName;
import cn.kim.common.attr.Tips;
import cn.kim.entity.Tree;
import cn.kim.exception.CustomException;
import cn.kim.common.eu.NameSpace;
import cn.kim.entity.Tree;
import cn.kim.entity.TreeState;
import cn.kim.exception.CustomException;
import cn.kim.service.OperatorService;
import cn.kim.service.OperatorService;
import cn.kim.util.DateUtil;
import cn.kim.util.PasswordMd5;
import cn.kim.util.RandomSalt;
import com.google.common.collect.Maps;
import org.springframework.expression.spel.ast.Operator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 余庚鑫 on 2018/3/27
 */
@Service
public class OperatorServiceImpl extends BaseServiceImpl implements OperatorService {
    @Override
    public Map<String, Object> selectOperator(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", mapParam.get("ID"));
        return baseDao.selectOne(NameSpace.OperatorMapper, "selectOperator", paramMap);
    }

    @Override
    public List<Map<String, Object>> selectOperatorByRoleId(String roleId) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("SR_ID", roleId);
        return baseDao.selectList(NameSpace.OperatorMapper, "selectOperatorByRoleId", paramMap);
    }

    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateOperator(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
            String operatorId = toString(mapParam.get("ID"));

            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_OPERATOR);

            paramMap.put("ID", operatorId);
            paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));

            if (isEmpty(operatorId)) {
                operatorId = getId();
                paramMap.put("ID", operatorId);
                //设置账号和盐
                String salt = RandomSalt.salt();
                paramMap.put("SO_SALT", salt);
                paramMap.put("SO_PASSWORD", PasswordMd5.password("123456", salt));

                paramMap.put("IS_STATUS", STATUS_SUCCESS);
                baseDao.insert(NameSpace.OperatorMapper, "insertOperator", paramMap);

                //添加accountinfo表
                paramMap.clear();
                //记录日志
                paramMap.put("SVR_TABLE_NAME", TableName.SYS_ACCOUNT_INFO);

                paramMap.put("ID", getId());
                paramMap.put("SO_ID", operatorId);
                paramMap.put("SAI_NAME", mapParam.get("SAI_NAME"));
                paramMap.put("SAI_PHONE", mapParam.get("SAI_PHONE"));
                paramMap.put("SAI_EMAIL", mapParam.get("SAI_EMAIL"));
                baseDao.insert(NameSpace.OperatorMapper, "insertAccountInfo", paramMap);

                resultMap.put(MagicValue.LOG, "添加操作员:" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", operatorId);
                oldMap = selectOperator(oldMap);

                baseDao.update(NameSpace.OperatorMapper, "updateOperator", paramMap);

                if (!isEmpty(mapParam.get("SAI_NAME")) || !isEmpty(mapParam.get("SAI_PHONE")) || !isEmpty(mapParam.get("SAI_EMAIL"))) {
                    //修改accountinfo表
                    paramMap.clear();
                    paramMap.put("SEARCH_SO_ID", operatorId);
                    paramMap.put("SAI_NAME", mapParam.get("SAI_NAME"));
                    paramMap.put("SAI_PHONE", mapParam.get("SAI_PHONE"));
                    paramMap.put("SAI_EMAIL", mapParam.get("SAI_EMAIL"));
                    baseDao.insert(NameSpace.OperatorMapper, "updateAccountInfo", paramMap);
                }

                resultMap.put(MagicValue.LOG, "更新操作员,更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
            }
            status = STATUS_SUCCESS;
            desc = SAVE_SUCCESS;

            resultMap.put("ID", operatorId);
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }

    /**
     * 更新操作员状态
     *
     * @param mapParam
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> changeOperatorStatus(Map<String, Object> mapParam) {
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
            oldMap = selectOperator(oldMap);

            baseDao.update(NameSpace.OperatorMapper, "updateOperator", paramMap);
            resultMap.put(MagicValue.LOG, "更新操作员状态,操作员:" + toString(oldMap.get("SAI_NAME")) + ",状态更新为:" + ParamTypeResolve.statusExplain(mapParam.get("IS_STATUS")));

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
     * 重置密码
     *
     * @param mapParam
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> resetOperatorPassword(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = Tips.RESET_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(3);
            String id = toString(mapParam.get("ID"));

            paramMap.put("ID", id);
            //设置账号和盐
            String salt = RandomSalt.salt();
            paramMap.put("SO_SALT", salt);
            paramMap.put("SO_PASSWORD", PasswordMd5.password("123456", salt));

            Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
            oldMap.put("ID", id);
            oldMap = selectOperator(oldMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_OPERATOR);

            baseDao.update(NameSpace.OperatorMapper, "updateOperator", paramMap);
            resultMap.put(MagicValue.LOG, "重置操作员密码,操作员:" + toString(oldMap.get("SAI_NAME")));

            status = STATUS_SUCCESS;
            desc = Tips.RESET_SUCCESS;

            resultMap.put("ID", id);
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> deleteOperator(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            String id = toString(mapParam.get("ID"));

            if (isEmpty(id)) {
                throw new CustomException("ID不能为空!");
            }
            if (MagicValue.ONE.equals(id)) {
                throw new CustomException("不能删除超级管理员!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);


            paramMap.put("SO_ID", id);
            baseDao.delete(NameSpace.OperatorMapper, "deleteAccountInfo", paramMap);
            baseDao.delete(NameSpace.OperatorMapper, "deleteOperatorSub", paramMap);
            baseDao.delete(NameSpace.OperatorMapper, "deleteOperatorRole", paramMap);
            //删除操作员表
            paramMap.clear();
            paramMap.put("ID", id);
            Map<String, Object> oldMap = selectOperator(paramMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_OPERATOR);
            baseDao.delete(NameSpace.OperatorMapper, "deleteOperator", paramMap);

            resultMap.put(MagicValue.LOG, "删除操作员,信息:" + toString(oldMap));
            status = STATUS_SUCCESS;
            desc = DELETE_SUCCESS;
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }


    @Override
    public Map<String, Object> selectOperatorSub(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", mapParam.get("ID"));
        return baseDao.selectOne(NameSpace.OperatorMapper, "selectOperatorSub", paramMap);
    }

    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateOperatorSub(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(10);
            String id = toString(mapParam.get("ID"));
            String operatorUserName = toString(mapParam.get("SOS_USERNAME"));

            //验证登录名是否重复
            paramMap.put("NOT_ID", id);
            paramMap.put("SOS_USERNAME", operatorUserName);
            List<Map<String, Object>> subs = baseDao.selectList(NameSpace.OperatorMapper, "selectOperatorSub", paramMap);
            if (!isEmpty(subs)) {
                throw new CustomException("账号已经存在!");
            }

            paramMap.clear();
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_OPERATOR_SUB);
            paramMap.put("ID", id);
            paramMap.put("SO_ID", mapParam.get("SO_ID"));
            paramMap.put("SOS_USERNAME", operatorUserName);
            paramMap.put("SOS_REMARK", mapParam.get("SOS_REMARK"));
            paramMap.put("SOS_USERTYPE", mapParam.get("SOS_USERTYPE"));
            paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));

            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);
                paramMap.put("IS_STATUS", STATUS_SUCCESS);
                paramMap.put("SOS_CREATETIME", DateUtil.getDate());

                //查询是否多次添加 第一次添加设为默认角色
                Map<String, Object> subMap = Maps.newHashMapWithExpectedSize(1);
                subMap.put("SO_ID", mapParam.get("SO_ID"));
                subs = baseDao.selectList(NameSpace.OperatorMapper, "selectOperatorSub", subMap);
                if (isEmpty(subs)) {
                    paramMap.put("SOS_DEFAULT", STATUS_SUCCESS);
                } else {
                    paramMap.put("SOS_DEFAULT", STATUS_ERROR);
                }

                baseDao.insert(NameSpace.OperatorMapper, "insertOperatorSub", paramMap);

                resultMap.put(MagicValue.LOG, "添加账号信息:" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectOperatorSub(oldMap);

                baseDao.update(NameSpace.OperatorMapper, "updateOperatorSub", paramMap);

                resultMap.put(MagicValue.LOG, "更新账号信息,更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
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
     * 更新操作员账号状态
     *
     * @param mapParam
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> changeOperatorSubStatus(Map<String, Object> mapParam) {
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
            oldMap = selectOperatorSub(oldMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_OPERATOR_SUB);
            baseDao.update(NameSpace.OperatorMapper, "updateOperatorSub", paramMap);
            resultMap.put(MagicValue.LOG, "更新操作员账号状态,操作员:" + toString(oldMap.get("SAI_NAME")) + "账号:" + toString(oldMap.get("SOS_USERNAME")) + ",状态更新为:" + ParamTypeResolve.statusExplain(mapParam.get("IS_STATUS")));

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

    @Override
    @Transactional
    public Map<String, Object> deleteOperatorSub(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            String id = toString(mapParam.get("ID"));

            if (isEmpty(id)) {
                throw new CustomException("ID不能为空!");
            }

            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);

            paramMap.put("ID", id);
            Map<String, Object> oldMap = selectOperatorSub(paramMap);
            //验证是不是默认账号
            if (toString(oldMap.get("SOS_DEFAULT")).equals(toString(STATUS_SUCCESS))) {
                throw new CustomException("默认账号不能删除!");
            }
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_OPERATOR_SUB);
            baseDao.delete(NameSpace.OperatorMapper, "deleteOperatorSub", paramMap);

            resultMap.put(MagicValue.LOG, "删除账号信息,信息:" + toString(oldMap));
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
     * 获取操作员的角色信息
     *
     * @param mapParam
     * @return
     */
    @Override
    public List<Tree> selectOperatorRole(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        List<Map<String, Object>> roles = baseDao.selectList(NameSpace.RoleMapper, "selectRole", paramMap);

        paramMap.clear();
        paramMap.put("SO_ID", mapParam.get("ID"));
        List<Map<String, Object>> nowOperatorRoles = baseDao.selectList(NameSpace.OperatorMapper, "selectOperatorRole", paramMap);

        //吧角色菜单按钮转为MAP格式
        Map<String, String> operatorRoleIds = toMapKey(nowOperatorRoles, "SR_ID");

        //转为TREE 并选中已经选择的按钮
        return getOperatorRoleTree(roles, operatorRoleIds);
    }

    /**
     * 保存操作员关联角色
     *
     * @param mapParam
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> updateOperatorRole(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
            String id = toString(mapParam.get("ID"));
            //选中的角色id
            String[] roleIds = toString(mapParam.get("ROLEIDS")).split(SERVICE_SPLIT);

            //查询操作员
            paramMap.put("ID", id);
            Map<String, Object> operator = selectOperator(paramMap);

            //查询旧操作员角色转为MAP格式
            paramMap.clear();
            paramMap.put("SO_ID", id);
            Map<String, String> oldOperatorRoleIds = toMapKey(baseDao.selectList(NameSpace.OperatorMapper, "selectOperatorRole", paramMap), "SR_ID");
            //新菜单权限
            Map<String, String> newOperatorRoleIds = Arrays.stream(roleIds).collect(Collectors.toMap(String::toString, String::toString));

            //原来没有的就添加
            if (!isEmpty(newOperatorRoleIds)) {
                for (String roleId : newOperatorRoleIds.keySet()) {
                    if (!isEmpty(roleId)) {
                        if (isEmpty(oldOperatorRoleIds) || !oldOperatorRoleIds.containsKey(roleId)) {
                            //添加
                            paramMap.clear();
                            paramMap.put("ID", getId());
                            paramMap.put("SO_ID", id);
                            paramMap.put("SR_ID", roleId);
                            baseDao.insert(NameSpace.OperatorMapper, "insertOperatorRole", paramMap);
                        }
                    }
                }
            }

            //新的菜单id 旧的还存在的就要删除
            if (!isEmpty(oldOperatorRoleIds)) {
                for (String oldRoleId : oldOperatorRoleIds.keySet()) {
                    if (!isEmpty(oldRoleId)) {
                        if (isEmpty(newOperatorRoleIds) || !newOperatorRoleIds.containsKey(oldRoleId)) {
                            //删除
                            paramMap.clear();
                            paramMap.put("SO_ID", id);
                            paramMap.put("SR_ID", oldRoleId);
                            baseDao.delete(NameSpace.OperatorMapper, "deleteOperatorRole", paramMap);
                        }

                    }
                }
            }
            //清除缓存
            customRealm.clearCached();

            status = STATUS_SUCCESS;
            desc = SAVE_SUCCESS;

            resultMap.put("ID", id);
            resultMap.put(MagicValue.LOG, "操作员:" + operator.get("SAI_NAME") + ",旧角色:" + toString(toKeySet(oldOperatorRoleIds)) + ",新角色:" + toString(toKeySet(newOperatorRoleIds)));
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }
}
