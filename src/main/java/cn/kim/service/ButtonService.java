package cn.kim.service;

import cn.kim.entity.Tree;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/27
 */
public interface ButtonService extends BaseService {
    Map<String, Object> selectButton(Map<String, Object> mapParam);

    /**
     * 获取按钮并且设置菜单已经选择的按钮
     *
     * @param mapParam
     * @return
     */
    List<Tree> selectButtonTree(Map<String, Object> mapParam);

    Map<String, Object> insertAndUpdateButton(Map<String, Object> mapParam);

    Map<String, Object> deleteButton(Map<String, Object> mapParam);
}
