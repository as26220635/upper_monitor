package cn.kim.service.impl;

import cn.kim.common.attr.MagicValue;
import cn.kim.common.attr.TableName;
import cn.kim.common.eu.NameSpace;
import cn.kim.exception.CustomException;
import cn.kim.service.AllocationService;
import com.google.common.collect.Maps;
import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/5/22
 */
@Service
public class AllocationServiceImpl extends BaseServiceImpl implements AllocationService {

    @Override
    public Map<String, Object> selectAllocation(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
        paramMap.put("ID", mapParam.get("ID"));
        paramMap.put("SA_KEY", mapParam.get("SA_KEY"));
        return baseDao.selectOne(NameSpace.AllocationMapper, "selectAllocation", paramMap);
    }

    @Override
    public String selectAllocation(@NotNull String key) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        if (isEmpty(key)) {
            return "";
        }

        paramMap.put("SA_KEY", key);
        Map<String, Object> allocation = this.selectAllocation(paramMap);

        return isEmpty(allocation) ? "" : toString(allocation.get("SA_VALUE"));
    }

    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateAllocation(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
            String id = toString(mapParam.get("ID"));
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_ALLOCATION);

            paramMap.put("ID", mapParam.get("ID"));
            paramMap.put("SA_KEY", mapParam.get("SA_KEY"));
            paramMap.put("SA_VALUE", mapParam.get("SA_VALUE"));
            paramMap.put("SA_MODIFY_TIME", mapParam.get("SA_MODIFY_TIME"));

            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);

                baseDao.insert(NameSpace.AllocationMapper, "insertAllocation", paramMap);
            } else {
                baseDao.update(NameSpace.AllocationMapper, "updateAllocation", paramMap);
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
    public Map<String, Object> insertAndUpdateAllocation(@NotNull String key, Object value) {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(5);
        if (isEmpty(key) || isEmpty(value)) {
            return mapParam;
        }
        mapParam.put("SA_KEY", key);
        Map<String, Object> allocation = this.selectAllocation(mapParam);

        mapParam.clear();
        if (!isEmpty(allocation)) {
            mapParam.put("ID", allocation.get("ID"));
        }
        mapParam.put("SA_KEY", key);
        mapParam.put("SA_VALUE", value);
        mapParam.put("SA_MODIFY_TIME", getDate());

        return this.insertAndUpdateAllocation(mapParam);
    }

    @Override
    @Transactional
    public Map<String, Object> deleteAllocation(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            if (isEmpty(mapParam.get("ID"))) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_ALLOCATION);
            paramMap.put("ID", mapParam.get("ID"));
            paramMap.put("SA_KEY", mapParam.get("SA_KEY"));
            baseDao.delete(NameSpace.AllocationMapper, "deleteAllocation", paramMap);

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
    @Transactional
    public Map<String, Object> deleteAllocation(@NotNull String key) {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        if (isEmpty(key)) {
            return mapParam;
        }
        mapParam.put("SA_KEY", key);

        return this.deleteAllocation(mapParam);
    }

}
