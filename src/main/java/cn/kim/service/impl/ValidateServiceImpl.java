package cn.kim.service.impl;

import cn.kim.common.attr.MagicValue;
import cn.kim.common.attr.ParamTypeResolve;
import cn.kim.common.attr.TableName;
import cn.kim.exception.CustomException;
import cn.kim.common.attr.Attribute;
import cn.kim.common.eu.NameSpace;
import cn.kim.entity.Tree;
import cn.kim.entity.TreeState;
import cn.kim.exception.CustomException;
import cn.kim.service.ValidateService;
import cn.kim.service.ValidateService;
import cn.kim.util.TextUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 余庚鑫 on 2018/3/27
 */
@Service
public class ValidateServiceImpl extends BaseServiceImpl implements ValidateService {

    @Override
    public Map<String, Object> selectValidate(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", mapParam.get("ID"));
        return baseDao.selectOne(NameSpace.ValidateMapper, "selectValidate", paramMap);
    }


    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateValidate(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
            String id = toString(mapParam.get("ID"));
            //查询字段是否重复
            paramMap.put("NOT_ID", id);
            paramMap.put("SV_TABLE", mapParam.get("SV_TABLE"));
            int count = baseDao.selectOne(NameSpace.ValidateMapper, "selectValidateCount", paramMap);
            if (count > 0) {
                throw new CustomException("验证表名重复,请检查!");
            }
            paramMap.clear();
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_VALIDATE);
            paramMap.put("ID", id);
            paramMap.put("SV_TABLE", mapParam.get("SV_TABLE"));
            paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));

            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);
                paramMap.put("IS_STATUS", Attribute.STATUS_SUCCESS);

                baseDao.insert(NameSpace.ValidateMapper, "insertValidate", paramMap);
                resultMap.put(MagicValue.LOG, "添加验证:" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectValidate(oldMap);

                baseDao.update(NameSpace.ValidateMapper, "updateValidate", paramMap);
                resultMap.put(MagicValue.LOG, "更新验证,更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
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

    /**
     * 更新验证状态
     *
     * @param mapParam
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> changeValidateStatus(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
            String id = toString(mapParam.get("ID"));

            paramMap.put("ID", id);
            paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));

            Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
            oldMap.put("ID", id);
            oldMap = selectValidate(oldMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_VALIDATE);
            baseDao.update(NameSpace.ValidateMapper, "updateValidate", paramMap);
            resultMap.put(MagicValue.LOG, "更新验证状态,验证表名:" + toString(oldMap.get("SV_TABLE")) + ",状态更新为:" + ParamTypeResolve.statusExplain(mapParam.get("IS_STATUS")));

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
    public Map<String, Object> deleteValidate(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            if (isEmpty(mapParam.get("ID"))) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            String id = toString(mapParam.get("ID"));

            //查询验证组后删除
            paramMap.put("SV_ID", id);

            List<Map<String, Object>> groups = baseDao.selectList(NameSpace.ValidateMapper, "selectValidateGroup", paramMap);
            for (Map<String, Object> group : groups) {
                //删除组字段
                paramMap.clear();
                paramMap.put("SVG_ID", group.get("ID"));
                baseDao.delete(NameSpace.ValidateMapper, "deleteValidateGroupField", paramMap);
                //删除组
                paramMap.clear();
                paramMap.put("ID", group.get("ID"));
                baseDao.delete(NameSpace.ValidateMapper, "deleteValidateGroup", paramMap);
            }
            //删除验证字段
            paramMap.clear();
            paramMap.put("SV_ID", id);
            baseDao.delete(NameSpace.ValidateMapper, "deleteValidateField", paramMap);
            //删除验证表
            paramMap.clear();
            paramMap.put("ID", mapParam.get("ID"));
            Map<String, Object> oldMap = selectValidate(paramMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_VALIDATE);
            baseDao.delete(NameSpace.ValidateMapper, "deleteValidate", paramMap);

            resultMap.put(MagicValue.LOG, "删除验证,信息:" + toString(oldMap));
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
    public Map<String, Object> selectValidateField(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(6);
        paramMap.put("ID", mapParam.get("ID"));
        paramMap.put("SV_TABLE", mapParam.get("SV_TABLE"));
        paramMap.put("SVF_FIELD", mapParam.get("SVF_FIELD"));
        paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));
        paramMap.put("SV_IS_STATUS", mapParam.get("SV_IS_STATUS"));
        paramMap.put("SVR_IS_STATUS", mapParam.get("SVR_IS_STATUS"));
        return baseDao.selectOne(NameSpace.ValidateMapper, "selectValidateField", paramMap);
    }

    @Override
    public List<Map<String, Object>> selectValidateFieldList(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
        paramMap.put("SV_ID", mapParam.get("SV_ID"));
        paramMap.put("SV_TABLE", mapParam.get("SV_TABLE"));
        paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));
        paramMap.put("SV_IS_STATUS", mapParam.get("SV_IS_STATUS"));
        paramMap.put("SVG_GROUPS", mapParam.get("SVG_GROUPS"));
        return baseDao.selectList(NameSpace.ValidateMapper, "selectValidateField", paramMap);
    }

    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateValidateField(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(10);
            String id = toString(mapParam.get("ID"));
            //查询字段是否重复
            paramMap.put("NOT_ID", id);
            paramMap.put("SV_ID", mapParam.get("SV_ID"));
            paramMap.put("SVF_FIELD", mapParam.get("SVF_FIELD"));
            int count = baseDao.selectOne(NameSpace.ValidateMapper, "selectValidateFieldCount", paramMap);
            if (count > 0) {
                throw new CustomException("验证字段重复,请检查!");
            }

            paramMap.clear();
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_VALIDATE_FIELD);
            paramMap.put("ID", id);
            paramMap.put("SV_ID", mapParam.get("SV_ID"));
            paramMap.put("SVF_NAME", mapParam.get("SVF_NAME"));
            paramMap.put("SVF_FIELD", mapParam.get("SVF_FIELD"));
            paramMap.put("SVF_IS_REQUIRED", mapParam.get("SVF_IS_REQUIRED"));
            paramMap.put("SVF_MAX_LENGTH", mapParam.get("SVF_MAX_LENGTH"));
            paramMap.put("SVF_MIN_LENGTH", mapParam.get("SVF_MIN_LENGTH"));
            paramMap.put("SVR_ID", mapParam.get("SVR_ID"));
            paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));

            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);
                paramMap.put("IS_STATUS", Attribute.STATUS_SUCCESS);

                baseDao.insert(NameSpace.ValidateMapper, "insertValidateField", paramMap);
                resultMap.put(MagicValue.LOG, "添加验证字段:" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectValidateField(oldMap);

                baseDao.update(NameSpace.ValidateMapper, "updateValidateField", paramMap);
                resultMap.put(MagicValue.LOG, "更新验证字段,更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
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

    /**
     * 更新验证字段状态
     *
     * @param mapParam
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> changeValidateFieldStatus(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
            String id = toString(mapParam.get("ID"));

            paramMap.put("ID", id);
            paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));

            Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
            oldMap.put("ID", id);
            oldMap = selectValidateField(oldMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_VALIDATE_FIELD);
            baseDao.update(NameSpace.ValidateMapper, "updateValidateField", paramMap);
            resultMap.put(MagicValue.LOG, "更新验证字段状态,验证字段:" + toString(oldMap.get("SVF_NAME")) + ",状态更新为:" + ParamTypeResolve.statusExplain(mapParam.get("IS_STATUS")));

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
    public Map<String, Object> deleteValidateField(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            if (isEmpty(mapParam.get("ID"))) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            String id = toString(mapParam.get("ID"));

            //删除组字段
            paramMap.clear();
            paramMap.put("SVF_ID", id);
            baseDao.delete(NameSpace.ValidateMapper, "deleteValidateGroupField", paramMap);
            //删除验证字段表
            paramMap.clear();
            paramMap.put("ID", mapParam.get("ID"));
            Map<String, Object> oldMap = selectValidateField(paramMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_VALIDATE_FIELD);
            baseDao.delete(NameSpace.ValidateMapper, "deleteValidateField", paramMap);

            resultMap.put(MagicValue.LOG, "删除验证字段,信息:" + toString(oldMap));
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
    public Map<String, Object> selectValidateRegex(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", mapParam.get("ID"));
        return baseDao.selectOne(NameSpace.ValidateMapper, "selectValidateRegex", paramMap);
    }

    @Override
    public List<Map<String, Object>> selectValidateRegexList(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
        return baseDao.selectList(NameSpace.ValidateMapper, "selectValidateRegex", paramMap);
    }

    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateValidateRegex(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
            String id = toString(mapParam.get("ID"));
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_VALIDATE_REGEX);

            paramMap.put("ID",id);
            paramMap.put("SVR_NAME", mapParam.get("SVR_NAME"));
            paramMap.put("SVR_REGEX", mapParam.get("SVR_REGEX"));
            paramMap.put("SVR_REGEX_MESSAGE", mapParam.get("SVR_REGEX_MESSAGE"));
            paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));

            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);
                paramMap.put("IS_STATUS", Attribute.STATUS_SUCCESS);

                baseDao.insert(NameSpace.ValidateMapper, "insertValidateRegex", paramMap);
                resultMap.put(MagicValue.LOG, "添加验证正则:" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectValidateRegex(oldMap);

                baseDao.update(NameSpace.ValidateMapper, "updateValidateRegex", paramMap);
                resultMap.put(MagicValue.LOG, "更新验证正则,更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
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
    public Map<String, Object> changeValidateRegexStatus(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
            String id = toString(mapParam.get("ID"));

            paramMap.put("ID", id);
            paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));

            Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
            oldMap.put("ID", id);
            oldMap = selectValidateRegex(oldMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_VALIDATE_REGEX);
            baseDao.update(NameSpace.ValidateMapper, "updateValidateRegex", paramMap);
            resultMap.put(MagicValue.LOG, "更新验证正则状态,正则名:" + toString(oldMap.get("SVR_NAME")) + ",状态更新为:" + ParamTypeResolve.statusExplain(mapParam.get("IS_STATUS")));

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
    public Map<String, Object> deleteValidateRegex(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            if (isEmpty(mapParam.get("ID"))) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            String id = toString(mapParam.get("ID"));

            //删除验证正则表
            paramMap.clear();
            paramMap.put("ID", id);
            Map<String, Object> oldMap = selectValidateRegex(paramMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_VALIDATE_REGEX);
            baseDao.delete(NameSpace.ValidateMapper, "deleteValidateRegex", paramMap);

            resultMap.put(MagicValue.LOG, "删除验证正则,信息:" + toString(oldMap));
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
    public Map<String, Object> selectValidateGroup(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", mapParam.get("ID"));
        Map<String, Object> group = baseDao.selectOne(NameSpace.ValidateMapper, "selectValidateGroup", paramMap);

        //查询字段
        paramMap.clear();
        paramMap.put("SVG_ID", mapParam.get("ID"));

        String fieldIds = "";
        List<Map<String, Object>> fields = baseDao.selectList(NameSpace.ValidateMapper, "selectValidateGroupField", paramMap);

        if (!isEmpty(fields)) {
            fieldIds = fields.stream().map(map -> map.get("SVF_ID")).map(Object::toString).collect(Collectors.joining(","));
        }
        group.put("SVF_IDS", fieldIds);

        return group;
    }

    @Override
    public List<Map<String, Object>> selectValidateGroupList(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
        paramMap.put("SV_TABLE", mapParam.get("SV_TABLE"));
        paramMap.put("SVG_GROUPS", mapParam.get("SVG_GROUPS"));
        List<Map<String, Object>> groups = baseDao.selectList(NameSpace.ValidateMapper, "selectValidateGroup", paramMap);
        return groups;
    }

    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateValidateGroup(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
            String id = toString(mapParam.get("ID"));
            String fieldIds = toString(mapParam.get("SVF_IDS"));
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_VALIDATE_GROUP);
            paramMap.put("ID", id);
            paramMap.put("SV_ID", mapParam.get("SV_ID"));
            paramMap.put("SVG_GROUP", mapParam.get("SVG_GROUP"));
            paramMap.put("SVF_IDS", fieldIds);

            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);

                baseDao.insert(NameSpace.ValidateMapper, "insertValidateGroup", paramMap);
                resultMap.put(MagicValue.LOG, "添加验证组:" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectValidateGroup(oldMap);

                baseDao.update(NameSpace.ValidateMapper, "updateValidateGroup", paramMap);
                resultMap.put(MagicValue.LOG, "更新验证组,更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
            }

            //删除旧SVGF字段信息
            paramMap.clear();
            paramMap.put("SVG_ID", id);
            baseDao.delete(NameSpace.ValidateMapper, "deleteValidateGroupField", paramMap);
            //插入SVF_IDS
            for (String fieldId : fieldIds.split(SERVICE_SPLIT)) {
                if (!isEmpty(fieldId)) {
                    paramMap.clear();
                    paramMap.put("ID", getId());
                    paramMap.put("SVG_ID", id);
                    paramMap.put("SVF_ID", fieldId);
                    baseDao.insert(NameSpace.ValidateMapper, "insertValidateGroupField", paramMap);
                }
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
    public Map<String, Object> deleteValidateGroup(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            if (isEmpty(mapParam.get("ID"))) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            String id = toString(mapParam.get("ID"));

            //删除组字段
            paramMap.clear();
            paramMap.put("SVG_ID", id);
            baseDao.delete(NameSpace.ValidateMapper, "deleteValidateGroupField", paramMap);
            //删除验证组
            paramMap.clear();
            paramMap.put("ID", mapParam.get("ID"));
            Map<String, Object> oldMap = selectValidateGroup(paramMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_VALIDATE_GROUP);
            baseDao.delete(NameSpace.ValidateMapper, "deleteValidateGroup", paramMap);

            resultMap.put(MagicValue.LOG, "删除验证正则,信息:" + toString(oldMap));
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
