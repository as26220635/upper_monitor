package cn.kim.common.tag;

import cn.kim.util.ValidateUtil;
import com.sun.istack.internal.Nullable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/27
 * 点击弹出树菜单
 */
public class TreeBox extends BaseTagSupport {
    /**
     * 默认选中的值
     */
    private String custom = "";
    private String id = "";
    private String name = "";
    private String value = "";
    private String nameValue = "";
    private String url = "";
    private String title = "";
    private String sdtId = "";
    private String notId = "";
    /**
     * 小：modal-sm 大：modal-lg
     */
    private String modelSize = "";
    /**
     * 是否单选
     */
    private boolean single = true;
    /**
     * 是否必填
     */
    private boolean required;

    /**
     * url传递参数
     */
    @Nullable
    private String requestParams;

    @Override
    public int doStartTagInternal() throws Exception {
        if (isEmpty(name)) {
            name = uuid();
        }
        if (!ValidateUtil.isEmpty(custom)) {
            Map<String, Object> customMap = customResolve(custom);
            id = toString(customMap.get("id"));
            required = toBoolean(customMap.get("required"));
        }
        if (single) {
            return single(pageContext.getOut());
        } else {
            return SKIP_BODY;
        }
    }

    @Override
    public int doEndTag() throws JspException {
        //清除参数
        custom = "";
        id = "";
        name = "";
        value = "";
        nameValue = "";
        url = "";
        title = "";
        sdtId = "";
        notId = "";
        custom = "";
        modelSize = "";
        single = true;
        required = false;
        requestParams = null;

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

            builder.append("<input type='text' class='form-control' id='" + name + "' name='" + name + "' value='" + nameValue + "' readonly " + (required ? "required" : "") + " data-bv-trigger='focus'><input type='hidden' class='form-control' id='" + id + "' name='" + id + "' value='" + value + "'>");

            String func = "<script>$('#" + name + "').selectInput(function () {" +
                    "        treeBox.init({" +
                    "            title: '" + title + "'," +
                    "            selectMode: treeBox.mode.SINGLE," +
                    "            modelSize: '" + modelSize + "'," +
                    "            url: '" + url + "'," +
                    "            searchParams: {" +
                    "                ID: $('#" + id + "').val()," +
                    "                SDT_ID: '" + sdtId + "'," +
                    "                NOT_ID: '" + notId + "'," +
                    //请求传递参数
                    toString(requestParams) +
                    "            }," +
                    "            isConfirm: true," +
                    "            confirm: function ($model, nodes) {" +
                    "                if (nodes.length > 0) {" +
                    "                    $('#" + id + "').val(nodes[0].id);" +
                    "                    $('#" + name + "').val(nodes[0].text);" +
                    "                } else {" +
                    "                    $('#" + id + "').val('');" +
                    "                    $('#" + name + "').val('');" +
                    "                }" +
                    "                $('#" + name + "').focus();" +
                    "                model.hide($model);" +
                    "            }" +
                    "        });" +
                    "    });</script>";

            builder.append(func);
            out.print(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return SKIP_BODY;
        }
        return EVAL_BODY_INCLUDE;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getNameValue() {
        return nameValue;
    }

    public void setNameValue(String nameValue) {
        this.nameValue = nameValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    @Override
    public boolean isEmpty(Object o) {
        return ValidateUtil.isEmpty(o);
    }

    public String getSdtId() {
        return sdtId;
    }

    public void setSdtId(String sdtId) {
        this.sdtId = sdtId;
    }

    public String getNotId() {
        return notId;
    }

    public void setNotId(String notId) {
        this.notId = notId;
    }

    public String getModelSize() {
        return modelSize;
    }

    public void setModelSize(String modelSize) {
        this.modelSize = modelSize;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }
}

