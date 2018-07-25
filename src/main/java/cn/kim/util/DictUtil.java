package cn.kim.util;

import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.CacheName;
import cn.kim.common.attr.TableName;
import cn.kim.common.eu.NameSpace;
import cn.kim.entity.DictInfo;
import cn.kim.entity.DictType;
import cn.kim.service.DictService;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/26
 * 字典工具
 */
@Component
public class DictUtil {
    private static Logger logger = LogManager.getLogger(DictUtil.class.getName());

    @Autowired
    private DictService dictService;
    private static DictUtil dictUtil;

    public void setDictService(DictService dictService) {
        this.dictService = dictService;
    }

    @PostConstruct
    public void init() {
        dictUtil = this;
        dictUtil.dictService = this.dictService;
    }

    /**
     * 加载字典到缓存
     */
    public static Map<String, DictType> initDictToCache() {
        System.out.println("====加载字典到缓存=====");
        //清空字典表缓存
        CacheUtil.clear(NameSpace.DictMapper.getValue());
        //清空文件表缓存
        CacheUtil.clear(NameSpace.FileMapper.getValue());
        //字典存入缓存
        Map<String, DictType> cacheDictTypeMap = Maps.newHashMapWithExpectedSize(16);

        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(3);
        //查询全部字典类型
        mapParam.put("IS_STATUS", Attribute.STATUS_SUCCESS);
        List<DictType> dictTypes = dictUtil.dictService.selectDictTypeList(mapParam);

        dictTypes.stream().forEach(dictType -> {
            mapParam.clear();
            mapParam.put("SDT_ID", dictType.getId());
            mapParam.put("SDI_PARENTID", "0");
            mapParam.put("IS_STATUS", Attribute.STATUS_SUCCESS);
            dictType.setInfos(dictUtil.dictService.selectDictInfoList(mapParam));
            //放入缓存MAP
            cacheDictTypeMap.put(dictType.getSdtCode(), dictType);
        });
        //设置到缓存中
        setDictCache(cacheDictTypeMap);

        return cacheDictTypeMap;
    }

    /**
     * 设置字典缓存
     *
     * @param dicts
     */
    public static void setDictCache(Map<String, DictType> dicts) {
        CacheUtil.put(CacheName.VALUE_COLLECTION, TableName.SYS_DICT_TYPE, dicts);
    }

    /**
     * 获取字典缓存
     *
     * @return
     */
    public static Map<String, DictType> getDictCache() {
        Object dicts = CacheUtil.get(CacheName.VALUE_COLLECTION, TableName.SYS_DICT_TYPE);
        if (ValidateUtil.isEmpty(dicts)) {
            dicts = initDictToCache();
        }
        return (Map<String, DictType>) dicts;
    }

    /**
     * 根据SDT_CODE获取字典
     *
     * @param dictTypeCode
     * @return
     */
    public static DictType getDictType(String dictTypeCode) {
        return getDictCache().get(dictTypeCode);
    }

    /**
     * 根据字典类型和字典编码获取字典名称
     *
     * @param dictTypeCode
     * @param dictInfoCode
     * @return
     */
    public static String getDictName(String dictTypeCode, String dictInfoCode) {
        DictType dictType = getDictCache().get(dictTypeCode);
        if (ValidateUtil.isEmpty(dictType)) {
            return dictInfoCode;
        }
        List<DictInfo> dictInfoList = dictType.getInfos();
        if (ValidateUtil.isEmpty(dictInfoList)) {
            return dictInfoCode;
        }
        for (DictInfo dictInfo : dictInfoList) {
            if (dictInfo.getSdiCode().equals(dictInfoCode)) {
                return dictInfo.getSdiName();
            }
        }
        return dictInfoCode;
    }
}
