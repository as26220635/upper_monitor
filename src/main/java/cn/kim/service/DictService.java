package cn.kim.service;

import cn.kim.entity.DictInfo;
import cn.kim.entity.DictType;
import cn.kim.entity.Tree;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/21
 */
public interface DictService extends BaseService {
    Map<String, Object> selectDictType(Map<String, Object> mapParam);

    List<DictType> selectDictTypeList(Map<String, Object> mapParam);

    Map<String, Object> selectDictInfo(Map<String, Object> mapParam);

    List<DictInfo> selectDictInfoList(Map<String, Object> mapParam);

    Map<String, Object> insertAndUpdateDictType(Map<String, Object> mapParam);

    Map<String, Object> deleteDictType(Map<String, Object> mapParam);

    Map<String, Object> insertAndUpdateDictInfo(Map<String, Object> mapParam);

    Map<String, Object> changeDictInfoStatus(Map<String, Object> mapParam);

    Map<String, Object> deleteDictInfo(Map<String, Object> mapParam);

    List<Tree> selectDictInfoTree(Map<String, Object> mapParam);
}
