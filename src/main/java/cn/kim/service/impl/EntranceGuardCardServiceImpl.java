package cn.kim.service.impl;

import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.MagicValue;
import cn.kim.common.attr.ParamTypeResolve;
import cn.kim.common.attr.TableName;
import cn.kim.common.eu.NameSpace;
import cn.kim.exception.CustomException;
import cn.kim.service.EntranceGuardCardService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 余庚鑫 on 2018/7/26
 */
@Service
public class EntranceGuardCardServiceImpl extends BaseServiceImpl implements EntranceGuardCardService {

    @Override
    public Map<String, Object> selectEntranceGuardCard(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
        paramMap.put("ID", mapParam.get("ID"));
        paramMap.put("BEGC_ID", mapParam.get("BEGC_ID"));
        return baseDao.selectOne(NameSpace.EntranceGuardCardMapper, "selectEntranceGuardCard", paramMap);
    }


    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateEntranceGuardCard(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(6);
            String id = toString(mapParam.get("ID"));
            //查询字段是否重复
            paramMap.put("NOT_ID", id);
            paramMap.put("BEGC_ID", mapParam.get("BEGC_ID"));
            int count = baseDao.selectOne(NameSpace.EntranceGuardCardMapper, "selectEntranceGuardCardCount", paramMap);
            if (count > 0) {
                throw new CustomException("门禁卡ID重复,请检查!");
            }
            paramMap.clear();
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.BUS_ENTRANCE_GUARD_CARD);
            paramMap.put("ID", id);
            paramMap.put("BEGC_ID", mapParam.get("BEGC_ID"));
            paramMap.put("BEGC_USERNAME", mapParam.get("BEGC_USERNAME"));
            paramMap.put("BEGC_PASSWORD", mapParam.get("BEGC_PASSWORD"));

            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);
                paramMap.put("BEGC_ENTRY_TIME", getDate());

                baseDao.insert(NameSpace.EntranceGuardCardMapper, "insertEntranceGuardCard", paramMap);
                resultMap.put(MagicValue.LOG, "添加门禁卡:" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectEntranceGuardCard(oldMap);

                baseDao.update(NameSpace.EntranceGuardCardMapper, "updateEntranceGuardCard", paramMap);
                resultMap.put(MagicValue.LOG, "更新门禁卡,更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
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
    public Map<String, Object> updateEntranceGuardCard(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(6);
            String id = toString(mapParam.get("ID"));
            //根据ID查询是否存在
            paramMap.put("BEGC_ID", mapParam.get("BEGC_ID"));
            Map<String, Object> card = this.selectEntranceGuardCard(paramMap);
            if (isEmpty(card)) {
                throw new CustomException("门禁卡信息不存在!");
            }
            paramMap.clear();
            paramMap.put("ID", card.get("ID"));
            paramMap.put("BEGC_ID", mapParam.get("BEGC_ID"));
            paramMap.put("BEGC_SERIAL", mapParam.get("BEGC_SERIAL"));
            paramMap.put("BEGC_KEY", mapParam.get("BEGC_KEY"));
            paramMap.put("BEGC_STATUS", mapParam.get("BEGC_STATUS"));
            paramMap.put("BEGC_INPUT", mapParam.get("BEGC_INPUT"));
            paramMap.put("BEGC_NOW", mapParam.get("BEGC_NOW"));
            paramMap.put("BEGC_T1", mapParam.get("BEGC_T1"));
            paramMap.put("BEGC_H1", mapParam.get("BEGC_H1"));
            paramMap.put("BEGC_T2", mapParam.get("BEGC_T2"));
            paramMap.put("BEGC_H2", mapParam.get("BEGC_H2"));
            paramMap.put("BEGC_INDEX", mapParam.get("BEGC_INDEX"));
            paramMap.put("BEGC_VER", mapParam.get("BEGC_VER"));
            paramMap.put("BEGC_NEXT_NUM", mapParam.get("BEGC_NEXT_NUM"));
            paramMap.put("BEGC_MAC", mapParam.get("BEGC_MAC"));
            paramMap.put("BEGC_UPDATE_TIME", getDate());

            baseDao.update(NameSpace.EntranceGuardCardMapper, "updateEntranceGuardCard", paramMap);
            //插入心跳日志
            paramMap.clear();
            paramMap.put("BEGC_ID", card.get("ID"));
            paramMap.put("BEGCL_SERIAL", mapParam.get("BEGC_SERIAL"));
            paramMap.put("BEGCL_ID", mapParam.get("BEGC_ID"));
            paramMap.put("BEGCL_KEY", mapParam.get("BEGC_KEY"));
            paramMap.put("BEGCL_STATUS", mapParam.get("BEGC_STATUS"));
            paramMap.put("BEGCL_INPUT", mapParam.get("BEGC_INPUT"));
            paramMap.put("BEGCL_NOW", mapParam.get("BEGC_NOW"));
            paramMap.put("BEGCL_T1", mapParam.get("BEGC_T1"));
            paramMap.put("BEGCL_H1", mapParam.get("BEGC_H1"));
            paramMap.put("BEGCL_T2", mapParam.get("BEGC_T2"));
            paramMap.put("BEGCL_H2", mapParam.get("BEGC_H2"));
            paramMap.put("BEGCL_INDEX", mapParam.get("BEGC_INDEX"));
            paramMap.put("BEGCL_VER", mapParam.get("BEGC_VER"));
            paramMap.put("BEGCL_NEXT_NUM", mapParam.get("BEGC_NEXT_NUM"));
            paramMap.put("BEGCL_MAC", mapParam.get("BEGC_MAC"));
            this.insertEntranceGuardCardLog(paramMap);

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
    public Map<String, Object> deleteEntranceGuardCard(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            if (isEmpty(mapParam.get("ID"))) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            String id = toString(mapParam.get("ID"));

            //删除门禁卡表
            paramMap.clear();
            paramMap.put("ID", mapParam.get("ID"));
            Map<String, Object> oldMap = selectEntranceGuardCard(paramMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.BUS_ENTRANCE_GUARD_CARD);
            baseDao.delete(NameSpace.EntranceGuardCardMapper, "deleteEntranceGuardCard", paramMap);

            resultMap.put(MagicValue.LOG, "删除门禁卡,信息:" + toString(oldMap));
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
    public Map<String, Object> insertEntranceGuardCardLog(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(16);
            String id = getId();

            paramMap.put("ID", id);
            paramMap.put("BEGC_ID", mapParam.get("BEGC_ID"));
            paramMap.put("BEGCL_SERIAL", mapParam.get("BEGCL_SERIAL"));
            paramMap.put("BEGCL_ID", mapParam.get("BEGCL_ID"));
            paramMap.put("BEGCL_KEY", mapParam.get("BEGCL_KEY"));
            paramMap.put("BEGCL_STATUS", mapParam.get("BEGCL_STATUS"));
            paramMap.put("BEGCL_INPUT", mapParam.get("BEGCL_INPUT"));
            paramMap.put("BEGCL_NOW", mapParam.get("BEGCL_NOW"));
            paramMap.put("BEGCL_T1", mapParam.get("BEGCL_T1"));
            paramMap.put("BEGCL_H1", mapParam.get("BEGCL_H1"));
            paramMap.put("BEGCL_T2", mapParam.get("BEGCL_T2"));
            paramMap.put("BEGCL_H2", mapParam.get("BEGCL_H2"));
            paramMap.put("BEGCL_INDEX", mapParam.get("BEGCL_INDEX"));
            paramMap.put("BEGCL_VER", mapParam.get("BEGCL_VER"));
            paramMap.put("BEGCL_NEXT_NUM", mapParam.get("BEGCL_NEXT_NUM"));
            paramMap.put("BEGCL_MAC", mapParam.get("BEGCL_MAC"));
            paramMap.put("BEGCL_ENTRY_TIME", getDate());

            baseDao.insert(NameSpace.EntranceGuardCardMapper, "insertEntranceGuardCardLog", paramMap);
            resultMap.put(MagicValue.LOG, "添加门禁卡心跳日志:" + toString(paramMap));
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
}
