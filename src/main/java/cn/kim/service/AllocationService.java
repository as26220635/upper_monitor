package cn.kim.service;

import cn.kim.entity.Tree;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/5/22
 * 系统配置
 */
public interface AllocationService extends BaseService {
    /**
     * 查询配置
     * @param mapParam
     * @return
     */
    Map<String, Object> selectAllocation(Map<String, Object> mapParam);

    /**
     * 根据KEY查询配置
     * @param key
     * @return
     */
    String selectAllocation(String key);

    /**
     * 插入或更新系统配置
     * @param mapParam
     * @return
     */
    Map<String, Object> insertAndUpdateAllocation(Map<String, Object> mapParam);

    /**
     * 插入或更新系统配置
     * @param key
     * @param value
     * @return
     */
    Map<String, Object> insertAndUpdateAllocation(String key, Object value);

    /**
     * 删除系统配置
     * @param mapParam
     * @return
     */
    Map<String, Object> deleteAllocation(Map<String, Object> mapParam);

    /**
     * 根据KEY删除系统配置
     * @param key
     * @return
     */
    Map<String, Object> deleteAllocation(String key);

}
