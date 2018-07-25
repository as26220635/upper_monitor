package cn.kim.service;

import cn.kim.entity.DataTablesView;

import java.security.InvalidKeyException;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/26
 */
public interface DataGridService extends BaseService {
    /**
     * 根据configureId查询 配置列表 配置列表字段 配置列表搜索
     *
     * @param configureId
     * @return
     */
    Map<String, Object> selectConfigureById(String configureId);

    /**
     * 通用列表数据
     *
     * @param mapParam
     * @return
     * @throws Exception
     */
    DataTablesView<Map<String, Object>> selectByMap(Map<String, Object> mapParam) throws Exception;
}
