package cn.kim.service.impl;

import cn.kim.common.BaseData;
import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.MagicValue;
import cn.kim.common.attr.Tips;
import cn.kim.common.eu.NameSpace;
import cn.kim.common.sequence.Sequence;
import cn.kim.common.shiro.CustomRealm;
import cn.kim.dao.BaseDao;
import cn.kim.entity.Tree;
import cn.kim.entity.TreeState;
import cn.kim.exception.CustomException;
import cn.kim.service.BaseService;
import cn.kim.util.CommonUtil;
import cn.kim.util.TextUtil;
import cn.kim.util.ValidateUtil;
import com.google.common.collect.Maps;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.util.*;

/**
 * Created by 余庚鑫 on 2017/11/1.
 */

@Service
public abstract class BaseServiceImpl extends BaseData implements BaseService {

    /**
     * 数据库基础DAO
     */
    @Autowired
    protected BaseDao baseDao;
    /**
     * shiro域
     */
    @Autowired
    protected CustomRealm customRealm;


    /******************     公用方法    *********************/
    /**
     * 验证返回结果是否出错
     *
     * @param resultMap
     * @throws CustomException
     */
    protected void validateResultMap(Map<String, Object> resultMap) throws CustomException {
        if (toInt(resultMap.get(MagicValue.STATUS)) == STATUS_ERROR) {
            throw new CustomException(toString(resultMap.get(MagicValue.DESC)));
        }
    }

    /**
     * 返回参数
     *
     * @param code
     * @param message
     * @param value
     * @return
     */
    protected Map<String, Object> resultMap(int code, String message, Object value) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(3);
        resultMap.put("code", code);
        resultMap.put("message", message);
        resultMap.put("val", value);
        return resultMap;
    }

    /**
     * 捕获异常处理
     *
     * @param e
     * @param baseDao   回滚
     * @param resultMap
     * @return 异常提示
     */
    protected String catchException(@NotNull Exception e, @NotNull BaseDao baseDao, @NotNull Map<String, Object> resultMap) {
        String desc = "";
        if (e instanceof CustomException) {
            desc = e.getMessage();
        } else {
            desc = "网络异常,请联系管理员!";
        }
        resultMap.put(MagicValue.DESC, desc);
        resultMap.put(MagicValue.STATUS, STATUS_ERROR);
        resultMap.put(MagicValue.LOG, e.getMessage());
        //回滚
        baseDao.rollback();
        //输出异常
        e.printStackTrace();
        return desc;
    }

    /**
     * 递归菜单
     *
     * @param operatorId
     * @param menuParentId
     * @param selectId     选中菜单的ID
     * @param notParentId  不显示父的ID
     * @param roleMenus    当前角色拥有的菜单
     * @return
     */
    public List<Map<String, Object>> getOperatorMenuTree(BaseDao baseDao, NameSpace nameSpace, String sqlId, String operatorId, String menuParentId, @Nullable String selectId, @Nullable String notParentId, @Nullable Map<String, String> roleMenus) {
        List<Map<String, Object>> trees = new ArrayList<>();
        if (!isEmpty(notParentId) && menuParentId.equals(notParentId)) {
            return trees;
        }

        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
        paramMap.put("SO_ID", operatorId);
        paramMap.put("SM_PARENTID", menuParentId);
        paramMap.put("NOT_ID", notParentId);

        List<Map<String, Object>> menus = baseDao.selectList(nameSpace, sqlId, paramMap);
        if (!ValidateUtil.isEmpty(menus)) {
            menus.forEach(menu -> {
                        String id = toString(menu.get("ID"));
                        //选中菜单
                        menu.put("IS_HAVE", false);
                        if (!isEmpty(selectId)) {
                            if (id.equals(selectId)) {
                                menu.put("IS_HAVE", true);
                            }
                        }
                        if (!isEmpty(roleMenus)) {
                            if (roleMenus.containsKey(id)) {
                                menu.put("IS_HAVE", true);
                            }
                        }
                        //连接url
                        String menuUrl = toString(menu.get("SM_URL"));
                        String menuUrlParams = toString(menu.get("SM_URL_PARAMS"));

                        menu.put("SM_URL", CommonUtil.getMenuUrlJoin(toString(menu.get("ID")), menuUrl, menuUrlParams));
                        trees.add(menu);
                    }
            );
        }

        if (!ValidateUtil.isEmpty(trees)) {
            for (Map<String, Object> tree : trees) {
                //遇到不是叶节点的 直接return;
                if ("-1".equals(selectId) && toInt(tree.get("SM_IS_LEAF")) == STATUS_ERROR) {
                    continue;
                }
                tree.put("CHILDREN_MENU", getOperatorMenuTree(baseDao, nameSpace, sqlId, operatorId, toString(tree.get("ID")), selectId, notParentId, roleMenus));
            }
        }
        return trees;
    }

    /**
     * 转为TREE 并选中已经选择的按钮
     *
     * @param roles
     * @param operatorRoles
     * @return
     */
    public List<Tree> getOperatorRoleTree(List<Map<String, Object>> roles, Map<String, String> operatorRoles) {
        List<Tree> operatorRoleTree = new ArrayList<>();
        if (!isEmpty(roles)) {
            roles.forEach(role -> {
                String id = toString(role.get("ID"));

                Tree tree = new Tree();
                tree.setId(id);
                tree.setText(toString(role.get("SR_NAME")));
                tree.setTags(new String[]{toString(role.get("SR_CODE"))});

                TreeState state = new TreeState();
                //是否选中
                if (!isEmpty(operatorRoles) && operatorRoles.containsKey(id)) {
                    state.setChecked(true);
                    //选中的设置打开
                    state.setExpanded(true);
                }

                //设置状态
                tree.setState(state);

                operatorRoleTree.add(tree);
            });
        }

        return operatorRoleTree;
    }
}
