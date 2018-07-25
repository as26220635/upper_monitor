package cn.kim.service;

import cn.kim.entity.Tree;
import cn.kim.entity.Tree;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/27
 */
public interface OperatorService extends BaseService {
    /**
     * 查询操作员
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> selectOperator(Map<String, Object> mapParam);

    /**
     * 根据角色id查询操作员
     *
     * @param roleId
     * @return
     */
    List<Map<String, Object>> selectOperatorByRoleId(String roleId);

    /**
     * 插入或更新操作员表
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> insertAndUpdateOperator(Map<String, Object> mapParam);

    /**
     * 变更操作员状态
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> changeOperatorStatus(Map<String, Object> mapParam);

    /**
     * 重置操作员密码
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> resetOperatorPassword(Map<String, Object> mapParam);

    /**
     * 删除操作员
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> deleteOperator(Map<String, Object> mapParam);

    /**
     * 查询操作员附属表
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> selectOperatorSub(Map<String, Object> mapParam);

    /**
     * 插入或更新操作员附属表
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> insertAndUpdateOperatorSub(Map<String, Object> mapParam);

    /**
     * 变更操作员附属表状态
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> changeOperatorSubStatus(Map<String, Object> mapParam);

    /**
     * 删除操作员附属表
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> deleteOperatorSub(Map<String, Object> mapParam);

    /**
     * 获取操作员的角色信息
     *
     * @param mapParam
     * @return
     */
    List<Tree> selectOperatorRole(Map<String, Object> mapParam);

    /**
     * 保存操作员关联角色
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> updateOperatorRole(Map<String, Object> mapParam);

}
