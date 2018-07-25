package cn.kim.service;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/21
 */
public interface MenuService extends BaseService {

    /**
     * 根据条件查询菜单
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> selectMenu(Map<String, Object> mapParam);

    /**
     * 根据条件查询菜单
     *
     * @param mapParam
     * @return
     */
    List<Map<String, Object>> selectMenuList(Map<String, Object> mapParam);

    /**
     * 获取菜单列表
     *
     * @return
     */
    List<Map<String, Object>> queryMenuList();

    /**
     * 获取菜单树并且选中菜单
     *
     * @param selectId    选中ID
     * @param notParentId 不显示父ID
     * @return
     */
    List<Map<String, Object>> selectMenuTreeSelectID(String selectId, String notParentId);

    /**
     * 查询菜单
     *
     * @param id
     * @return
     */
    Map<String, Object> queryMenuById(String id);

    /**
     * 插入或菜单
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> insertAndUpdateMenu(Map<String, Object> mapParam);

    /**
     * 变更菜单状态
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> changeMenuStatus(Map<String, Object> mapParam);

    /**
     * 更新菜单关联按钮
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> updateMenuButton(Map<String, Object> mapParam);

    /**
     * 删除菜单
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> deleteMenu(Map<String, Object> mapParam);

    /**
     * 根据参数参数现在用户在此菜单下拥有的按钮
     *
     * @param mapParam
     * @return
     */
    List<Map<String, Object>> selectOperatorNowMenu(Map<String, Object> mapParam);

}
