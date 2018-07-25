package cn.kim.service;

import cn.kim.entity.Tree;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/27
 */
public interface ValidateService extends BaseService {
    Map<String, Object> selectValidate(Map<String, Object> mapParam);

    Map<String, Object> insertAndUpdateValidate(Map<String, Object> mapParam);

    Map<String, Object> changeValidateStatus(Map<String, Object> mapParam);

    Map<String, Object> deleteValidate(Map<String, Object> mapParam);

    Map<String, Object> selectValidateField(Map<String, Object> mapParam);

    List<Map<String, Object>> selectValidateFieldList(Map<String, Object> mapParam);

    Map<String, Object> insertAndUpdateValidateField(Map<String, Object> mapParam);

    Map<String, Object> changeValidateFieldStatus(Map<String, Object> mapParam);

    Map<String, Object> deleteValidateField(Map<String, Object> mapParam);

    Map<String, Object> selectValidateRegex(Map<String, Object> mapParam);

    List<Map<String, Object>> selectValidateRegexList(Map<String, Object> mapParam);

    Map<String, Object> insertAndUpdateValidateRegex(Map<String, Object> mapParam);

    Map<String, Object> changeValidateRegexStatus(Map<String, Object> mapParam);

    Map<String, Object> deleteValidateRegex(Map<String, Object> mapParam);

    Map<String, Object> selectValidateGroup(Map<String, Object> mapParam);

    List<Map<String, Object>> selectValidateGroupList(Map<String, Object> mapParam);

    Map<String, Object> insertAndUpdateValidateGroup(Map<String, Object> mapParam);

    Map<String, Object> deleteValidateGroup(Map<String, Object> mapParam);
}
