package cn.kim.common.tag;

import cn.kim.common.sequence.Sequence;
import cn.kim.common.sequence.Sequence;
import cn.kim.entity.DictType;
import cn.kim.util.DictUtil;
import cn.kim.util.ValidateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 余庚鑫 on 2018/3/29
 * 选择图标组件
 */
public class IconSelect extends BaseTagSupport {
    //自定义
    private String custom = "";
    private String id = "";
    private String name = "";
    private String value = "";

    @Override
    public int doStartTagInternal() throws Exception {
        if (!ValidateUtil.isEmpty(custom)) {
            Map<String, Object> customMap = customResolve(custom);
            id = toString(customMap.get("id"));
            name = toString(customMap.get("name"));
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

            String uuid = Sequence.getId();

            builder.append("<input type='hidden' class='form-control' " + toTagId(custom, id, name) + " value='" + value + "'>");
            builder.append("<i id='" + id + "_NAME' style='margin-left: 10px;margin-right: 10px;font-size: 20px;' class='" + value + "'></i>");
            builder.append("<a class='btn btn-default btn-sm' onclick='previewIconModalShow" + uuid + "();'>选择图标</a><a class='btn btn-default btn-sm' style='margin-left:10px;' onclick=\"$('#" + id + "').val('');$('#" + id + "_NAME').removeClass();\">清空</a>");

            builder.append("<script>var iconModel" + uuid + ";function previewIconModalShow" + uuid + "() { ajax.getHtml(PREVIEW_ICON_URL, {}, function (html) { model.show({title: '预览图标 <span class=\"text-red\" style=\"font-size: 14px;\">&nbsp;&nbsp;注:点击选择</span>', content: html, footerModel: model.footerModel.ADMIN, size: model.size.LG, onLoadSuccess: function ($model) { iconModel" + uuid + " = $model; }}); })} function callbackPreviewIcon(icon) {$('#" + id + "').val(icon); $('#" + id + "_NAME').removeClass().addClass(icon);model.hide(iconModel" + uuid + ");}</script>");
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
}

