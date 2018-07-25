package cn.kim.service;

import cn.kim.entity.Tree;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/7/8
 * 部门管理
 */
public interface DivisionService extends BaseService {
    /**
     * 查询部门
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> selectDivision(Map<String, Object> mapParam);

    /**
     * 获取部门树
     *
     * @param id
     * @param notId 不显示的ID 自身和父类
     * @return
     */
    List<Tree> selectDivisionTreeList(String id, String notId);

    /**
     * 插入或更新部门
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> insertAndUpdateDivision(Map<String, Object> mapParam);

    /**
     * 删除部门
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> deleteDivision(Map<String, Object> mapParam);


}
