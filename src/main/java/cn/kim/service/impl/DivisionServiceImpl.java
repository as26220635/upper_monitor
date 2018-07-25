package cn.kim.service.impl;

import cn.kim.common.attr.MagicValue;
import cn.kim.common.attr.TableName;
import cn.kim.common.eu.NameSpace;
import cn.kim.entity.Tree;
import cn.kim.entity.TreeState;
import cn.kim.exception.CustomException;
import cn.kim.service.DivisionService;
import cn.kim.service.DivisionService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/7/8
 * 部门管理
 */
@Service
public class DivisionServiceImpl extends BaseServiceImpl implements DivisionService {

    @Override
    public Map<String, Object> selectDivision(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", mapParam.get("ID"));
        return baseDao.selectOne(NameSpace.DivisionMapper, "selectDivision", paramMap);
    }

    @Override
    public List<Tree> selectDivisionTreeList(String id, String notId) {
        return getDivisionTreeList("0", id, notId);
    }


    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateDivision(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(10);
            String id = toString(mapParam.get("ID"));
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.BUS_DIVISION);

            paramMap.put("ID", id);
            //父类id默认为0
            paramMap.put("BD_PARENT_ID", isEmpty(mapParam.get("BD_PARENT_ID")) ? MagicValue.ZERO : mapParam.get("BD_PARENT_ID"));
            paramMap.put("BD_NAME", mapParam.get("BD_NAME"));
            paramMap.put("BD_CONTACTS", mapParam.get("BD_CONTACTS"));
            paramMap.put("BD_PHONE", mapParam.get("BD_PHONE"));
            paramMap.put("BD_FIXED_PHONE", mapParam.get("BD_FIXED_PHONE"));
            paramMap.put("BD_EMAIL", mapParam.get("BD_EMAIL"));
            paramMap.put("BD_ADDRESS", mapParam.get("BD_ADDRESS"));
            paramMap.put("BD_DESCRIBE", mapParam.get("BD_DESCRIBE"));
            paramMap.put("BD_ORDER", mapParam.get("BD_ORDER"));

            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);
                paramMap.put("BD_ENTER_TIME", getDate());

                baseDao.insert(NameSpace.DivisionMapper, "insertDivision", paramMap);
                resultMap.put(MagicValue.LOG, "添加部门:" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectDivision(oldMap);

                baseDao.update(NameSpace.DivisionMapper, "updateDivision", paramMap);
                resultMap.put(MagicValue.LOG, "更新部门,更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
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
    public Map<String, Object> deleteDivision(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            if (isEmpty(mapParam.get("ID"))) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            String id = toString(mapParam.get("ID"));

            //删除部门表
            paramMap.clear();
            paramMap.put("ID", id);
            Map<String, Object> oldMap = selectDivision(paramMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.BUS_DIVISION);
            baseDao.delete(NameSpace.DivisionMapper, "deleteDivision", paramMap);

            resultMap.put(MagicValue.LOG, "删除部门,信息:" + toString(oldMap));
            status = STATUS_SUCCESS;
            desc = DELETE_SUCCESS;
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }

    /**
     * 获取部门树
     *
     * @param parentId 父ID
     * @param selectId 选中的ID
     * @param notId    不显示id和父类id
     * @return
     */
    public List<Tree> getDivisionTreeList(String parentId, String selectId, String notId) {
        List<Tree> treeList = new ArrayList<>();

        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(3);
        paramMap.put("BD_PARENT_ID", parentId);
        paramMap.put("NOT_ID", notId);
        paramMap.put("NOT_PARENT_ID", notId);
        List<Map<String, Object>> divisionList = baseDao.selectList(NameSpace.DivisionMapper, "selectDivision", paramMap);

        if (!isEmpty(divisionList)) {
            divisionList.forEach(button -> {
                String id = toString(button.get("ID"));

                Tree tree = new Tree();
                tree.setId(id);
                tree.setText(toString(button.get("BD_NAME")));
                tree.setTags(new String[]{
//                        "电话:" + toHtmlBColor(button.get("BD_PHONE"), "yellow"),
                        "联系人:" + toHtmlBColor(button.get("BD_CONTACTS"), "yellow")
                });

                TreeState state = new TreeState();
                //是否选中
                if (!isEmpty(selectId) && selectId.equals(id)) {
                    state.setChecked(true);
                    //选中的设置打开
                    state.setExpanded(true);
                }

                //设置状态
                tree.setState(state);

                //查询子类
                List<Tree> childrenTreeList = getDivisionTreeList(id, selectId, notId);
                if (!isEmpty(childrenTreeList)) {
                    tree.setNodes(childrenTreeList);
                }

                treeList.add(tree);
            });
        }

        return treeList;
    }
}
