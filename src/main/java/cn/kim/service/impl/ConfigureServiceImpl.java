package cn.kim.service.impl;

import cn.kim.common.attr.MagicValue;
import cn.kim.common.attr.TableName;
import cn.kim.exception.CustomException;
import cn.kim.common.eu.NameSpace;
import cn.kim.exception.CustomException;
import cn.kim.service.ConfigureService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/26
 * 配置列表
 */
@Service
public class ConfigureServiceImpl extends BaseServiceImpl implements ConfigureService {

    @Override
    public Map<String, Object> selectConfigure(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", mapParam.get("ID"));
        return baseDao.selectOne(NameSpace.ConfigureMapper, "selectConfigure", paramMap);
    }

    @Override
    public List<Map<String, Object>> selectConfigureList(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
        return baseDao.selectList(NameSpace.ConfigureMapper, "selectConfigure", paramMap);
    }

    @Override
    public List<Map<String, Object>> selectConfigureColumnList(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
        paramMap.put("ID", mapParam.get("ID"));
        paramMap.put("SC_ID", mapParam.get("SC_ID"));
        return baseDao.selectList(NameSpace.ConfigureMapper, "selectConfigureColumn", paramMap);
    }

    @Override
    public List<Map<String, Object>> selectConfigureSearchList(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
        paramMap.put("ID", mapParam.get("ID"));
        paramMap.put("SC_ID", mapParam.get("SC_ID"));
        return baseDao.selectList(NameSpace.ConfigureMapper, "selectConfigureSearch", paramMap);
    }

    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateConfigure(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(10);
            String id = toString(mapParam.get("ID"));
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_CONFIGURE);

            paramMap.put("ID", id);
            paramMap.put("SC_NAME", mapParam.get("SC_NAME"));
            paramMap.put("SC_VIEW", mapParam.get("SC_VIEW"));
            paramMap.put("SC_ORDER_BY", mapParam.get("SC_ORDER_BY"));
            paramMap.put("SC_JSP", mapParam.get("SC_JSP"));
            paramMap.put("SC_IS_SINGLE", mapParam.get("SC_IS_SINGLE"));
            paramMap.put("SC_IS_SELECT", mapParam.get("SC_IS_SELECT"));
            paramMap.put("SC_IS_PAGING", mapParam.get("SC_IS_PAGING"));
            paramMap.put("SC_IS_SEARCH", mapParam.get("SC_IS_SEARCH"));
            paramMap.put("SC_IS_FILTER", mapParam.get("SC_IS_FILTER"));
            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);

                baseDao.insert(NameSpace.ConfigureMapper, "insertConfigure", paramMap);
                resultMap.put(MagicValue.LOG, "添加配置列表:" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectConfigure(oldMap);

                baseDao.update(NameSpace.ConfigureMapper, "updateConfigure", paramMap);
                resultMap.put(MagicValue.LOG, "更新配置列表,更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
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

    @Override
    @Transactional
    public Map<String, Object> deleteConfigure(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            if (isEmpty(mapParam.get("ID"))) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            String id = toString(mapParam.get("ID"));

            paramMap.put("SC_ID", id);
            //删除配置列表字段
            baseDao.delete(NameSpace.ConfigureMapper, "deleteConfigureColumn", paramMap);
            //删除配置列表搜索
            baseDao.delete(NameSpace.ConfigureMapper, "deleteConfigureSearch", paramMap);
            //删除配置列表表
            paramMap.clear();
            paramMap.put("ID", id);
            Map<String, Object> oldMap = selectConfigure(paramMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_CONFIGURE);
            baseDao.delete(NameSpace.ConfigureMapper, "deleteConfigure", paramMap);

            resultMap.put(MagicValue.LOG, "删除配置列表,信息:" + toString(oldMap));
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
    public Map<String, Object> selectConfigureColumn(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", mapParam.get("ID"));
        return baseDao.selectOne(NameSpace.ConfigureMapper, "selectConfigureColumn", paramMap);
    }

    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateConfigureColumn(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(13);
            String id = toString(mapParam.get("ID"));
            String configureId = toString(mapParam.get("SC_ID"));

            paramMap.put("ID", configureId);
            Map<String, Object> configure = selectConfigure(paramMap);

            paramMap.clear();
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_CONFIGURE_COLUMN);

            paramMap.put("ID", id);
            paramMap.put("SC_ID", configureId);
            paramMap.put("SCC_NAME", mapParam.get("SCC_NAME"));
            paramMap.put("SCC_FIELD", mapParam.get("SCC_FIELD"));
            paramMap.put("SCC_ALIGN", mapParam.get("SCC_ALIGN"));
            paramMap.put("SCC_WIDTH", mapParam.get("SCC_WIDTH"));
            paramMap.put("SCC_CLASS", mapParam.get("SCC_CLASS"));
            paramMap.put("SCC_FUNC", mapParam.get("SCC_FUNC"));
            paramMap.put("SCC_SDT_CODE", mapParam.get("SCC_SDT_CODE"));
            paramMap.put("SCC_IS_OPERATION", mapParam.get("SCC_IS_OPERATION"));
            paramMap.put("SCC_IS_VISIBLE", mapParam.get("SCC_IS_VISIBLE"));
            paramMap.put("SCC_IS_STATUS", mapParam.get("SCC_IS_STATUS"));
            paramMap.put("SCC_ORDER", mapParam.get("SCC_ORDER"));

            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);

                baseDao.insert(NameSpace.ConfigureMapper, "insertConfigureColumn", paramMap);
                resultMap.put(MagicValue.LOG, "添加配置列表字段,配置列表:" + toString(configure.get("SC_NAME")) + ",字段参数" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectConfigure(oldMap);

                baseDao.update(NameSpace.ConfigureMapper, "updateConfigureColumn", paramMap);
                resultMap.put(MagicValue.LOG, "更新配置列表字段,配置列表:" + toString(configure.get("SC_NAME")) + ",更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
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

    @Override
    @Transactional
    public Map<String, Object> deleteConfigureColumn(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            if (isEmpty(mapParam.get("ID"))) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            String id = toString(mapParam.get("ID"));

            //删除配置列表表
            paramMap.put("ID", id);
            Map<String, Object> oldMap = selectConfigureColumn(paramMap);
            paramMap.clear();
            paramMap.put("ID", oldMap.get("SC_ID"));
            Map<String, Object> configure = selectConfigure(paramMap);

            paramMap.clear();
            paramMap.put("ID", id);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_CONFIGURE_COLUMN);
            baseDao.delete(NameSpace.ConfigureMapper, "deleteConfigureColumn", paramMap);

            resultMap.put(MagicValue.LOG, "删除配置列表字段,配置列表:" + toString(configure.get("SC_NAME")) + "字段:" + toString(oldMap));
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
    public Map<String, Object> selectConfigureSearch(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", mapParam.get("ID"));
        return baseDao.selectOne(NameSpace.ConfigureMapper, "selectConfigureSearch", paramMap);
    }

    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateConfigureSearch(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(10);
            String id = toString(mapParam.get("ID"));
            String configureId = toString(mapParam.get("SC_ID"));

            paramMap.put("ID", configureId);
            Map<String, Object> configure = selectConfigure(paramMap);

            paramMap.clear();
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_CONFIGURE_SEARCH);
            paramMap.put("ID", id);
            paramMap.put("SC_ID", configureId);
            paramMap.put("SCS_NAME", mapParam.get("SCS_NAME"));
            paramMap.put("SCS_FIELD", mapParam.get("SCS_FIELD"));
            paramMap.put("SCS_SDT_CODE", mapParam.get("SCS_SDT_CODE"));
            paramMap.put("SCS_METHOD_TYPE", mapParam.get("SCS_METHOD_TYPE"));
            paramMap.put("SCS_TYPE", mapParam.get("SCS_TYPE"));
            paramMap.put("SCS_REMARK", mapParam.get("SCS_REMARK"));
            paramMap.put("SCC_IS_VISIBLE", mapParam.get("SCC_IS_VISIBLE"));
            paramMap.put("SCS_ORDER", mapParam.get("SCS_ORDER"));

            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);

                baseDao.insert(NameSpace.ConfigureMapper, "insertConfigureSearch", paramMap);
                resultMap.put(MagicValue.LOG, "添加配置列表搜索,配置列表:" + toString(configure.get("SC_NAME")) + ",搜索参数" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectConfigure(oldMap);

                baseDao.update(NameSpace.ConfigureMapper, "updateConfigureSearch", paramMap);
                resultMap.put(MagicValue.LOG, "更新配置列表搜索,配置列表:" + toString(configure.get("SC_NAME")) + ",更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
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

    @Override
    @Transactional
    public Map<String, Object> deleteConfigureSearch(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            if (isEmpty(mapParam.get("ID"))) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            String id = toString(mapParam.get("ID"));

            //删除配置列表表
            paramMap.put("ID", id);
            Map<String, Object> oldMap = selectConfigureSearch(paramMap);
            paramMap.clear();
            paramMap.put("ID", oldMap.get("SC_ID"));
            Map<String, Object> configure = selectConfigure(paramMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_CONFIGURE_SEARCH);
            baseDao.delete(NameSpace.ConfigureMapper, "deleteConfigureSearch", paramMap);

            resultMap.put(MagicValue.LOG, "删除配置列表搜索,配置列表:" + toString(configure.get("SC_NAME")) + "搜索:" + toString(oldMap));
            status = STATUS_SUCCESS;
            desc = DELETE_SUCCESS;
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }
}
