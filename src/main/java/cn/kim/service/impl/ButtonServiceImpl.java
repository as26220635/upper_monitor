package cn.kim.service.impl;

import cn.kim.common.attr.MagicValue;
import cn.kim.common.attr.TableName;
import cn.kim.exception.CustomException;
import cn.kim.common.annotation.Token;
import cn.kim.common.attr.Attribute;
import cn.kim.common.eu.NameSpace;
import cn.kim.entity.Tree;
import cn.kim.entity.TreeState;
import cn.kim.exception.CustomException;
import cn.kim.exception.ParameterException;
import cn.kim.service.ButtonService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/27
 */
@Service
public class ButtonServiceImpl extends BaseServiceImpl implements ButtonService {
    @Override
    public Map<String, Object> selectButton(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("ID", mapParam.get("ID"));
        return baseDao.selectOne(NameSpace.ButtonMapper, "selectButton", paramMap);
    }

    /**
     * 获取按钮并且设置菜单已经选择的按钮
     *
     * @param mapParam
     * @return
     */
    @Override
    public List<Tree> selectButtonTree(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("SM_ID", mapParam.get("SM_ID"));
        List<Map<String, Object>> buttons = baseDao.selectList(NameSpace.ButtonMapper, "selectButton", paramMap);
        paramMap.clear();
        paramMap.put("SM_ID", mapParam.get("SM_ID"));
        List<Map<String, Object>> menuButtons = baseDao.selectList(NameSpace.MenuMapper, "selectMenuButton", paramMap);
        //吧菜单按钮转为MAP格式
        Map<String, String> menuButtonIds = toMapKey(menuButtons, "SB_ID");

        return getMenuButtonTree(buttons, menuButtonIds);
    }

    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateButton(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(10);
            String id = toString(mapParam.get("ID"));
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_BUTTON);

            paramMap.put("ID", id);
            paramMap.put("SB_NAME", mapParam.get("SB_NAME"));
            paramMap.put("SB_BUTTONID", mapParam.get("SB_BUTTONID"));
            paramMap.put("SB_FUNC", mapParam.get("SB_FUNC"));
            paramMap.put("SB_CLASS", mapParam.get("SB_CLASS"));
            paramMap.put("SB_ICON", mapParam.get("SB_ICON"));
            paramMap.put("SB_CODE", mapParam.get("SB_CODE"));
            paramMap.put("SB_ORDER", mapParam.get("SB_ORDER"));
            paramMap.put("SB_TYPE", mapParam.get("SB_TYPE"));
            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);

                baseDao.insert(NameSpace.ButtonMapper, "insertButton", paramMap);
                resultMap.put(MagicValue.LOG, "添加按钮:" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectButton(oldMap);

                baseDao.update(NameSpace.ButtonMapper, "updateButton", paramMap);
                resultMap.put(MagicValue.LOG, "更新按钮,更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
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
    public Map<String, Object> deleteButton(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            if (isEmpty(mapParam.get("ID"))) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            String id = toString(mapParam.get("ID"));

            paramMap.put("SB_ID", id);
            //删除菜单关联按钮表
            baseDao.delete(NameSpace.MenuMapper, "deleteMenuButton", paramMap);
            //删除角色关联按钮表
            baseDao.delete(NameSpace.RoleMapper, "deleteRoleButton", paramMap);
            //删除按钮表
            paramMap.clear();
            paramMap.put("ID", id);
            Map<String, Object> oldMap = selectButton(paramMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_BUTTON);
            baseDao.delete(NameSpace.ButtonMapper, "deleteButton", paramMap);

            resultMap.put(MagicValue.LOG, "删除按钮,信息:" + toString(oldMap));
            status = STATUS_SUCCESS;
            desc = DELETE_SUCCESS;
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }

    public List<Tree> getMenuButtonTree(List<Map<String, Object>> buttons, Map<String, String> menuButtons) {
        List<Tree> menuButtonTree = new ArrayList<>();
        if (!isEmpty(buttons)) {
            buttons.forEach(button -> {
                String id = toString(button.get("ID"));

                Tree tree = new Tree();
                tree.setId(id);
                tree.setText(toString(button.get("SB_NAME")));
                tree.setTags(new String[]{"编码:" + toHtmlBColor(button.get("SB_CODE"), "yellow"), "ID:" + toHtmlBColor(button.get("SB_BUTTONID"), "yellow"), "类型:" + toHtmlBColor(button.get("SB_TYPE_NAME"), "yellow")});

                TreeState state = new TreeState();
                //是否选中
                if (!isEmpty(menuButtons) && menuButtons.containsKey(id)) {
                    state.setChecked(true);
                    //选中的设置打开
                    state.setExpanded(true);
                }

                //设置状态
                tree.setState(state);

                menuButtonTree.add(tree);
            });
        }

        return menuButtonTree;
    }
}
