package cn.kim.service;

import cn.kim.entity.Tree;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/6/6
 * 格式管理
 */
public interface FormatService extends BaseService {
    /**
     * 查询格式管理
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> selectFormat(Map<String, Object> mapParam);

    /**
     * 根据code查询格式管理
     *
     * @param formatCode
     * @return
     */
    Map<String, Object> selectFormat(String formatCode);

    /**
     * 插入或更新格式管理
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> insertAndUpdateFormat(Map<String, Object> mapParam);

    /**
     * 删除格式管理
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> deleteFormat(Map<String, Object> mapParam);

    /**
     * 查询格式管理详细
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> selectFormatDetail(Map<String, Object> mapParam);

    /**
     * 查询格式管理详细列表
     *
     * @param mapParam
     * @return
     */
    List<Map<String, Object>> selectFormatDetailList(Map<String, Object> mapParam);

    /**
     * 查询格式管理详细列表转为tree
     *
     * @param mapParam
     * @return
     */
    List<Tree> selectFormatDetailTree(Map<String, Object> mapParam);

    /**
     * 插入或更新格式管理详细
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> insertAndUpdateFormatDetail(Map<String, Object> mapParam);

    /**
     * 变更格式管理详细状态
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> changeFormatDetailStatus(Map<String, Object> mapParam);

    /**
     * 删除格式管理详细
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> deleteFormatDetail(Map<String, Object> mapParam);

}
