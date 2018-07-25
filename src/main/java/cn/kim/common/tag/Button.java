package cn.kim.common.tag;

import cn.kim.common.attr.Constants;
import cn.kim.service.MenuService;
import cn.kim.common.attr.Constants;
import cn.kim.common.sequence.Sequence;
import cn.kim.service.MenuService;
import cn.kim.util.*;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.xml.soap.Text;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/29
 * 按钮
 */
public class Button extends BaseTagSupport {

    private static String[] BTN_ID_AJAX_CLOSE = {"save"};

    private MenuService menuService;

    private String contextPath;

    /**
     * 菜单ID
     */
    private String smId = "";
    /**
     * 按钮类型 0 顶部按钮 1 列表按钮
     */
    private int type = 0;

    private boolean back = true;

    @Override
    protected int doStartTagInternal() throws Exception {
        this.contextPath = toString(SessionUtil.get(Constants.SESSION_SERVLET_PATH));
        if (ValidateUtil.isEmpty(this.contextPath)) {
            return SKIP_BODY;
        }
        if (this.contextPath.startsWith("/")) {
            this.contextPath = this.contextPath.substring(1, this.contextPath.length());
        }
        //获取bean
        this.menuService = this.getRequestContext().getWebApplicationContext().getBean(MenuService.class);
        //拿到当前访问的连接
        //查询菜单列表
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(3);
        if (!isEmpty(smId)) {
            mapParam.put("ID", CommonUtil.idDecrypt(smId));
        }
        mapParam.put("SM_URL", this.contextPath);
        Map<String, Object> menu = this.menuService.selectMenu(mapParam);
        if (ValidateUtil.isEmpty(menu) || ValidateUtil.isEmpty(menu.get("SM_CODE")) || !AuthcUtil.isPermitted(toString(menu.get("SM_CODE")))) {
            return SKIP_BODY;
        }
        //查询菜单
        //查询顶部操作按钮
        mapParam.clear();
        mapParam.put("SM_ID", menu.get("ID"));
        mapParam.put("SO_ID", AuthcUtil.getCurrentUser().getId());
        mapParam.put("SB_TYPE", type);
        List<Map<String, Object>> buttons = menuService.selectOperatorNowMenu(mapParam);

        if (!ValidateUtil.isEmpty(buttons)) {
            //根据权限过滤按钮
            buttons.removeIf(map -> !AuthcUtil.isPermitted(toString(menu.get("SM_CODE")) + "_" + toString(map.get("SB_CODE"))));
        }


        if (type == 0) {
            //顶部按钮
            return top(pageContext.getOut(), buttons);
        } else if (type == 1) {
            //列表按钮
            return list(pageContext.getOut(), buttons);
        }

        return SKIP_BODY;
    }

    /**
     * 清除参数
     *
     * @return
     * @throws JspException
     */
    @Override
    public int doEndTag() throws JspException {
        smId = "";
        type = 0;
        back = true;
        return super.doEndTag();
    }

    /**
     * 顶部按钮
     *
     * @param out
     * @param buttons
     * @return
     */
    public int top(JspWriter out, List<Map<String, Object>> buttons) {
        try {
            StringBuilder builder = new StringBuilder();

            if (isBack()) {
                builder.append("<button type='button' class='btn btn-warning' onclick='backHtml();'><i class='mdi mdi-keyboard-return'></i>返回</button>");
            }

            if (!isEmpty(buttons)) {
                buttons.forEach(button -> {
                    //额外的class
                    String additionalClass = "";
                    if (Arrays.binarySearch(BTN_ID_AJAX_CLOSE, button.get("SB_BUTTONID")) != -1) {
                        additionalClass = " model-ok ";
                    }
                    builder.append("<button id='" + button.get("SB_BUTTONID") + "' type='button' class='" + button.get("SB_CLASS") + additionalClass + "' onclick='" + button.get("SB_FUNC") + "'><i class='" + button.get("SB_ICON") + "'></i>" + button.get("SB_NAME") + "</button>");
                });
            }

            out.print(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return SKIP_BODY;
        }
        return EVAL_BODY_INCLUDE;
    }

    /**
     * 列表按钮
     *
     * @param out
     * @param buttons
     * @return
     */
    public int list(JspWriter out, List<Map<String, Object>> buttons) {
        try {
            StringBuilder builder = new StringBuilder();

            if (!isEmpty(buttons)) {
                buttons.forEach(button -> {
//                builder.append("<button id='" + button.get("SB_BUTTONID") + "' type='button' class='" + button.get("SB_CLASS") + "' onclick='" + button.get("SB_FUNC") + "'><i class='" + button.get("SB_ICON") + "'></i>" + button.get("SB_NAME") + "</button>");
                });
            }

            out.print(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return SKIP_BODY;
        }
        return EVAL_BODY_INCLUDE;
    }

    public String getSmId() {
        return smId;
    }

    public void setSmId(String smId) {
        this.smId = smId;
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

