package cn.kim.common.tag;

import cn.kim.common.annotation.Validate;
import cn.kim.common.sequence.Sequence;
import cn.kim.util.TextUtil;
import cn.kim.util.ValidateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/31
 * 时间选择控件
 */
public class Datebox extends BaseTagSupport {
    private String custom = "";
    private String id = "";
    private String name = "";
    private String value = "";
    private String placeholder = "";
    /**
     * 1 年 2年月日 3 年月日时分秒
     */
    private int type = 1;
    /**
     * 是否要有清理的选项
     */
    private boolean clear;
    /**
     * 是否必填
     */
    private boolean required;

    @Override
    public int doStartTagInternal() throws Exception {

        if (!ValidateUtil.isEmpty(custom)) {
            Map<String, Object> customMap = customResolve(custom);
            id = toString(customMap.get("id"));
            name = toString(customMap.get("name"));
            required = toBoolean(customMap.get("required"));
        }
        return init(pageContext.getOut());
    }

    /**
     * 清除参数
     *
     * @return
     * @throws JspException
     */
    @Override
    public int doEndTag() throws JspException {
        custom = "";
        id = "";
        name = "";
        value = "";
        placeholder = "";
        type = 1;
        clear = false;
        required = false;
        return super.doEndTag();
    }

    /**
     * 选择图标
     *
     * @param out
     * @return
     */
    public int init(JspWriter out) {
        try {
            StringBuilder builder = new StringBuilder();

            builder.append("<input " + toTagId(custom, id, name) + " type='text' class='form-control form-control-input-search' placeholder='" + placeholder + "' value='" + value + "'>");

            builder.append("<script>datepick.init({obj: $('#" + id + "'),model:" + getFormat(type) + ",clear: " + TextUtil.toString(clear) + "});</script>");
            out.print(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return SKIP_BODY;
        }
        return EVAL_BODY_INCLUDE;
    }

    /**
     * 获取格式化
     *
     * @param type
     * @return
     */
    private String getFormat(int type) {
        if (type == 1) {
            return "datepick.model.Y";
        } else if (type == 2) {
            return "datepick.model.YM";
        } else if (type == 3) {
            return "datepick.model.YMD";
        } else if (type == 4) {
            return "datepick.model.YMDHI";
        }
        return "";
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public boolean isClear() {
        return clear;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}

