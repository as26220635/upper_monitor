package cn.kim.service;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/21
 */
public interface ManagerService extends BaseService {

    /**
     * 验证登录账号是否存在
     *
     * @param operatorUserName
     * @return
     */
    public boolean checkLoginUsername(String operatorUserName);

    /**
     * 根据账号查询信息
     *
     * @param operatorUserName
     * @return
     */
    public Map<String, Object> queryLoginUsername(String operatorUserName);

    /**
     * 根据SO_ID查询用户角色
     *
     * @param mapParam
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryOperatorRole(Map<String, Object> mapParam);

    /**
     * 查询账号信息
     *
     * @param mapParam
     * @return
     */
    public Map<String, Object> queryAccountInfo(Map<String, Object> mapParam);


    /**
     * 根据SO_ID查询用户角色菜单按钮权限
     *
     * @param mapParam
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryOperatorMenuButtonPrecode(Map<String, Object> mapParam);

    /**
     * 根据SO_ID查询用户角色菜单权限
     *
     * @param mapParam
     * @return
     */
    public List<Map<String, Object>> queryOperatorMenu(Map<String, Object> mapParam);

    /**
     * 获取菜单树
     *
     * @param operatorId
     * @return
     */
    public List<Map<String, Object>> queryOperatorMenuTree(String operatorId);

}
