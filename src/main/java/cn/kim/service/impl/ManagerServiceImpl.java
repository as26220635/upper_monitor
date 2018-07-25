package cn.kim.service.impl;

import cn.kim.common.attr.Attribute;
import cn.kim.common.eu.NameSpace;
import cn.kim.common.eu.SystemEnum;
import cn.kim.dao.BaseDao;
import cn.kim.exception.ParameterException;
import cn.kim.service.ManagerService;
import cn.kim.util.CommonUtil;
import cn.kim.util.TextUtil;
import cn.kim.util.ValidateUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/21
 */
@Service
public class ManagerServiceImpl extends BaseServiceImpl implements ManagerService {

    /**
     * 登录账号是否存在
     *
     * @param operatorUserName
     * @return
     */
    @Override
    public boolean checkLoginUsername(String operatorUserName) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("SOS_USERNAME", operatorUserName);
        int count = baseDao.selectOne(NameSpace.ManagerMapper, "checkLoginUsername", paramMap);
        return count > 0;
    }

    /**
     * 根据账号查询信息
     *
     * @param operatorUserName
     * @return
     */
    @Override
    public Map<String, Object> queryLoginUsername(String operatorUserName) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("SOS_USERNAME", operatorUserName);
        return baseDao.selectOne(NameSpace.ManagerMapper, "queryLoginUsername", paramMap);
    }

    /**
     * 根据SO_ID查询用户角色
     *
     * @param mapParam
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> queryOperatorRole(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("SO_ID", mapParam.get("SO_ID"));
        List<Map<String, Object>> roles = baseDao.selectList(NameSpace.ManagerMapper, "queryOperatorRole", paramMap);
        return roles;
    }

    /**
     * 查询账号信息
     *
     * @param mapParam
     * @return
     */
    @Override
    public Map<String, Object> queryAccountInfo(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("SO_ID", mapParam.get("SO_ID"));
        return baseDao.selectOne(NameSpace.ManagerMapper, "queryAccountInfo", paramMap);
    }

    /**
     * 根据SO_ID查询用户角色菜单按钮权限
     *
     * @param mapParam
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> queryOperatorMenuButtonPrecode(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("SO_ID", mapParam.get("SO_ID"));
        List<Map<String, Object>> precodes = baseDao.selectList(NameSpace.ManagerMapper, "queryOperatorMenuButtonPrecode", paramMap);
        return precodes;
    }

    /**
     * 根据SO_ID查询用户角色菜单权限
     *
     * @param mapParam
     * @return
     */
    @Override
    public List<Map<String, Object>> queryOperatorMenu(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
        paramMap.put("SO_ID", mapParam.get("SO_ID"));
        paramMap.put("SM_PARENTID", mapParam.get("SM_PARENTID"));
        return baseDao.selectList(NameSpace.ManagerMapper, "queryOperatorMenu", paramMap);
    }

    /**
     * 获取菜单树
     *
     * @param operatorId
     * @return
     */
    @Override
    public List<Map<String, Object>> queryOperatorMenuTree(String operatorId) {
        return getOperatorMenuTree(baseDao, NameSpace.ManagerMapper, "queryOperatorMenu", operatorId, "0", null, null, null);
    }

}
