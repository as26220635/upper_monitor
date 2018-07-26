package cn.kim.service;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/7/26
 */
public interface EntranceGuardCardService extends BaseService {
    /**
     * 查询门禁卡
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> selectEntranceGuardCard(Map<String, Object> mapParam);

    /**
     * 添加或更门禁卡
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> insertAndUpdateEntranceGuardCard(Map<String, Object> mapParam);

    /**
     * 更新门禁卡
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> updateEntranceGuardCard(Map<String, Object> mapParam);

    /**
     * 删除门禁卡
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> deleteEntranceGuardCard(Map<String, Object> mapParam);

    /**
     * 插入心跳日志
     * @param mapParam
     * @return
     */
    Map<String, Object> insertEntranceGuardCardLog(Map<String, Object> mapParam);
}
