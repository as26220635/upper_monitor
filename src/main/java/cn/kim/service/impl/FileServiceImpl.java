package cn.kim.service.impl;

import cn.kim.common.attr.MagicValue;
import cn.kim.common.attr.TableName;
import cn.kim.exception.CustomException;
import cn.kim.common.eu.NameSpace;
import cn.kim.exception.CustomException;
import cn.kim.service.FileService;
import cn.kim.util.TextUtil;
import cn.kim.util.ValidateUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/5/22
 */
@Service
public class FileServiceImpl extends BaseServiceImpl implements FileService {

    @Override
    public Map<String, Object> selectFile(String id) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", id);
        return this.selectFile(paramMap);
    }

    @Override
    public Map<String, Object> selectFile(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", mapParam.get("ID"));
        return baseDao.selectOne(NameSpace.FileMapper, "selectFile", paramMap);
    }

    @Override
    public List<Map<String, Object>> selectFileList(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
        paramMap.put("ID", mapParam.get("ID"));
        paramMap.put("SF_TABLE_ID", mapParam.get("SF_TABLE_ID"));
        paramMap.put("SF_TABLE_NAME", mapParam.get("SF_TABLE_NAME"));
        paramMap.put("SF_SDT_CODE", mapParam.get("SF_SDT_CODE"));
        paramMap.put("SF_SDI_CODE", mapParam.get("SF_SDI_CODE"));
        return baseDao.selectList(NameSpace.FileMapper, "selectFile", paramMap);
    }


    @Override
    @Transactional
    public Map<String, Object> insertFile(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(13);
            String id = toString(mapParam.get("ID"));
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_FILE);

            paramMap.put("ID", id);
            paramMap.put("SO_ID", mapParam.get("SO_ID"));
            paramMap.put("SF_TABLE_ID", mapParam.get("SF_TABLE_ID"));
            paramMap.put("SF_TABLE_NAME", mapParam.get("SF_TABLE_NAME"));
            paramMap.put("SF_SDT_CODE", mapParam.get("SF_SDT_CODE"));
            paramMap.put("SF_SDI_CODE", mapParam.get("SF_SDI_CODE"));
            paramMap.put("SF_ORIGINAL_NAME", mapParam.get("SF_ORIGINAL_NAME"));
            paramMap.put("SF_NAME", mapParam.get("SF_NAME"));
            paramMap.put("SF_PATH", mapParam.get("SF_PATH"));
            paramMap.put("SF_SUFFIX", mapParam.get("SF_SUFFIX"));
            paramMap.put("SF_SIZE", mapParam.get("SF_SIZE"));
            paramMap.put("SF_SEE_TYPE", mapParam.get("SF_SEE_TYPE"));
            paramMap.put("SF_ENTRY_TIME", mapParam.get("SF_ENTRY_TIME"));

            baseDao.insert(NameSpace.FileMapper, "insertFile", paramMap);

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
    public Map<String, Object> deleteFile(String id) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", id);
        return this.deleteFile(paramMap);
    }


    @Override
    @Transactional
    public Map<String, Object> deleteFile(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            if (isEmpty(mapParam.get("ID"))) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            paramMap.put("ID", mapParam.get("ID"));
            //查询文件详细信息
            Map<String, Object> old = this.selectFile(paramMap);
            //删除文件
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_FILE);
            baseDao.delete(NameSpace.FileMapper, "deleteFile", paramMap);

            String tableName = TextUtil.toString(old.get("SF_TABLE_NAME"));
            String originalName = TextUtil.toString(old.get("SF_ORIGINAL_NAME"));

            resultMap.put(MagicValue.LOG, "删除文件:" + originalName + ",来源:" + tableName);

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
