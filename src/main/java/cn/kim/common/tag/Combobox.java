package cn.kim.common.tag;

import cn.kim.common.attr.Attribute;
import cn.kim.entity.DictType;
import cn.kim.util.DictUtil;
import cn.kim.util.TextUtil;
import cn.kim.util.ValidateUtil;
import com.sun.istack.internal.Nullable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/27
 * 下拉框通用组件
 */
public class Combobox extends BaseTagSupport {
    /**
     * 自定义select参数
     */
    private String custom = "";
    /**
     * 宽度默认为100%
     */
    private String width = "100%";
    /**
     * 默认选中的值
     */
    private String value = "";
    private String id = "";
    private String name = "";
    private String sdtCode = "";
    private String defaultValue = "";
    /**
     * 数据加载url,加载的url返回json格式数组 中有 NAME和VALUE字段
     */
    @Nullable
    private String url;
    /**
     * 是否单选
     */
    private boolean single = true;
    /**
     * 是否必填
     */
    private boolean required;
    /**
     * 是否开启禁用不能选择
     */
    private boolean disabled = true;

    @Override
    protected int doStartTagInternal() throws Exception {
        value = !ValidateUtil.isEmpty(value) ? value : defaultValue;

        if (!ValidateUtil.isEmpty(custom)) {
            Map<String, Object> customMap = customResolve(custom);
            id = toString(customMap.get("id"));
            name = toString(customMap.get("name"));
            required = toBoolean(customMap.get("required"));
        }

        if (single) {
            return single(pageContext.getOut());
        } else {
            return multiple(pageContext.getOut());
        }
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
        width = "100%";
        value = "";
        id = "";
        name = "";
        sdtCode = "";
        defaultValue = "";
        url = null;
        single = true;
        required = false;
        disabled = true;
        return super.doEndTag();
    }

    /**
     * 单选
     *
     * @param out
     * @return
     */
    public int single(JspWriter out) {
        try {
            StringBuilder builder = new StringBuilder();

//            <select id="locked" name="locked" class="form-control select2" style="width:100%;">
//                                    <option value="" selected>请选择</option>
//                                    <c:forEach items="${status}" var="status">
//                                        <option value="${status.key}">${status.value}</option>
//                                    </c:forEach>
//                                </select>

            builder.append("<select " + (isEmpty(id) ? "" : "id = '" + id + "'") + " " + (isEmpty(name) ? "" : "name = '" + name + "'") + " " + custom + " class='form-control select2' style='width:" + width + ";' " + (required ? "required" : "") + ">");

            if (!required) {
                builder.append("<option value='' selected>请选择</option>");
            }
            appendSdtCodeOption(builder);

            builder.append("</select>");

            builder.append("<script>$('#" + id + "').select2({language: 'zh-CN'});</script>");
            out.print(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return SKIP_BODY;
        }
        return EVAL_BODY_INCLUDE;
    }

    /**
     * 多选
     *
     * @param out
     * @return
     */
    public int multiple(JspWriter out) {
        try {
            StringBuilder builder = new StringBuilder();

            builder.append("<select " + toTagId(custom, id, name) + " class='form-control select2' style='width:" + width + ";' " + (required ? "required" : "") + " readonly multiple>");

            if (!isEmpty(sdtCode)) {
                appendSdtCodeOption(builder);
            }
            builder.append("</select>");

            builder.append("<script>$('#" + id + "').select2({language: 'zh-CN'});</script>");
            if (!isEmpty(sdtCode)) {
                builder.append("<script>$('#" + id + "').prop('readonly','false');</script>");
            } else {
                String[] selects = value.split(Attribute.SERVICE_SPLIT);
                builder.append("<script>var SELECT_" + id + " = " + TextUtil.toString(selects) + "; ajax.get('" + url + "',{},function (data) {for(var i in data){var obj = data[i];$('#" + id + "').append('<option value=\"'+obj.ID+'\" '+ ($.inArray(obj.ID, SELECT_" + id + ") == '-1' ? '' : 'selected')+'>' + obj.NAME +'</option>')}$('#" + id + "').prop('readonly','false');});</script>");
            }
            out.print(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return SKIP_BODY;
        }
        return EVAL_BODY_INCLUDE;
    }

    /**
     * 添加sdicode选择
     *
     * @param builder
     */
    private void appendSdtCodeOption(StringBuilder builder) {
        if (!isEmpty(sdtCode)) {
            DictType dictType = DictUtil.getDictType(sdtCode);
            if (!isEmpty(dictType)) {
                dictType.getInfos().forEach(info -> {
                    String selected = "";
                    //选中已选
                    if (isSingle()) {
                        //单选
                        if (info.getSdiCode().equals(value)) {
                            selected = "selected";
                        }
                    } else {
                        //多选
                        for (String val : value.split(Attribute.SERVICE_SPLIT)) {
                            if (info.getSdiCode().equals(val)) {
                                selected = "selected";
                                break;
                            }
                        }
                    }
                    builder.append("<option value='" + info.getSdiCode() + "' " + selected + (disabled && info.getIsStatus() == Attribute.STATUS_ERROR ? " disabled " : "") + ">" + info.getSdiName() + "</option>");
                });
            }
        }
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdtCode() {
        return sdtCode;
    }

    public void setSdtCode(String sdtCode) {
        this.sdtCode = sdtCode;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}

