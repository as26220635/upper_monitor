package cn.kim.common.tag;

import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.AttributePath;
import cn.kim.common.attr.ConfigProperties;
import cn.kim.entity.DictType;
import cn.kim.service.FileService;
import cn.kim.common.annotation.Validate;
import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.AttributePath;
import cn.kim.common.attr.ConfigProperties;
import cn.kim.entity.DictType;
import cn.kim.service.FileService;
import cn.kim.util.*;
import com.google.common.collect.Maps;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.servlet.jsp.JspException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by 余庚鑫 on 2018/3/29
 * 文件上传
 */
public class FileInput extends BaseTagSupport {
    /**
     * 预览类型
     */
    private static final String FILE_TYPE_IMG = "image";
    private static final String FILE_TYPE_PDF = "pdf";
    private static final String FILE_TYPE_HTML = "html";
    private static final String FILE_TYPE_TEXT = "text";
    private static final String FILE_TYPE_OFFICE = "office";
    private static final String FILE_TYPE_VIDEO = "video";
    private static final String FILE_TYPE_AUDIO = "audio";
    private static final String FILE_TYPE_FLASH = "flash";
    private static final String FILE_TYPE_OBJECT = "object";
    private static final String FILE_TYPE_OTHER = "other";

    /**
     * 文件服务
     */
    private FileService fileService;

    /**
     * 标题
     */
    private String title = "";
    /**
     * 字典代码
     */
    private String sdtCode = "";
    /**
     * 上传表主键
     */
    private String tableId = "";
    /**
     * 上传表名
     */
    private String tableName = "";
    /**
     * 初始路径
     */
    private String typeCode = "";
    /**
     * 是否可以不用登录查看 1 是 0 否
     */
    private int seeType = Attribute.STATUS_SUCCESS;
    /**
     * 是否多选 默认多选
     */
    private boolean multiple = true;
    /**
     * 上传文件主题 默认为缩略预览  主题 : explorer
     */
    private String theme = "";
    /**
     * 是否开启异步上传
     */
    private boolean showUpload = true;
    /**
     * 是否开启删除
     */
    private boolean showRemove = true;
    /**
     * 最大上传数量
     */
    private int maxFilesNum = 99;
    /**
     * 最大允许同时上传数量
     */
    private int maxFileCount = 99;
    /**
     * 最大允许上传大小
     */
    private long maxFileSize = 8000;
    /**
     * 上传类型 默认只能上传图片 为true可以上传文件
     */
    private boolean allowFile = false;

    @Override
    protected int doStartTagInternal() throws Exception {
        //获取bean
        this.fileService = this.getRequestContext().getWebApplicationContext().getBean(FileService.class);

        //查询字典
        DictType dictType = DictUtil.getDictType(sdtCode);
        if (ValidateUtil.isEmpty(dictType) || ValidateUtil.isEmpty(dictType.getInfos())) {
            return EVAL_BODY_INCLUDE;
        }

        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(4);

        StringBuilder builder = new StringBuilder();

        //最外层的ID
        String groupId = uuid();

        builder.append("<div class='box box-solid'>" +
                "   <div class='box-header with-border'>" +
                "       <h3 class='box-title'><i class='mdi mdi-file-multiple'></i>附件</h3>" +
                "   </div>" +
                "   <div class='box-body'>");
        builder.append("<div class='box-group' id='" + groupId + "'>");

        //循环插入
        FuncUtil.forEach(dictType.getInfos(), (index, info) -> {
            int required = info.getSdiRequired();
            String boxClass = required == Attribute.STATUS_SUCCESS ? " box-danger " : " box-success ";
            String aClass = (index == 0 ? "" : " collapsed ") + (required == Attribute.STATUS_SUCCESS ? " text-red " : " text-black ");
            String collapseStyle = index == 0 ? "" : "height: 0px; ";
            String collapseClass = index == 0 ? " in " : "";

            //查询改SDI_CODE下面拥有的文件
            mapParam.clear();
            mapParam.put("SF_TABLE_ID", tableId);
            mapParam.put("SF_TABLE_NAME", tableName);
            mapParam.put("SF_SDT_CODE", sdtCode);
            mapParam.put("SF_SDI_CODE", info.getSdiCode());
            List<Map<String, Object>> files = fileService.selectFileList(mapParam);

            builder.append("<div class='panel box " + boxClass + "'>");

            String collapseId = uuid();
            String inputId = uuid();
            String numberId = uuid();
            //标签DIV
            builder.append("<div class='box-header with-border'><h4 class='box-title' style='width: 100%;'>");
            builder.append("<a data-toggle='collapse' data-parent='#" + groupId + "' href='#" + collapseId + "' aria-expanded='true' class='" + aClass + "'>");
            builder.append(info.getSdiName() + (required == Attribute.STATUS_SUCCESS ? "（必填）" : "") + "<span class='pull-right'>数量:<span id='" + numberId + "' class='" + (required == Attribute.STATUS_SUCCESS ? "file-validate" : "") + "' data-validate-message='" + info.getSdiName() + "'>" + files.size() + "</span></span>");
            builder.append("</a>");
            builder.append("</h4></div>");

            //内容DIV
            builder.append("<div id='" + collapseId + "' class='panel-collapse collapse " + collapseClass + "' aria-expanded='true' style='" + collapseStyle + "'>");
            builder.append("<input id='" + inputId + "' type='file' class='file' " + (!allowFile ? " accept='image/*' " : " ") + (multiple ? "multiple" : "") + " >");
            //插入格式化文件上传的JS
            builder.append("<script>");

            //拿到预览需要的数组
            String[] initialPreview = new String[files.size()];
            String[] initialPreviewConfig = new String[files.size()];
            FuncUtil.forEach(files, (i, file) -> {
                String id = idEncrypt(file.get("ID"));

                String fileType = getFileTypeName(toString(file.get("SF_SUFFIX")));
                if (fileType.equals(FILE_TYPE_IMG)) {
                    initialPreview[i] = mosaicImg(getBaseUrl() + AttributePath.FILE_PREVIEW_URL + id, file.get("SF_ORIGINAL_NAME"));
                } else if (fileType.equals(FILE_TYPE_PDF)) {
                    initialPreview[i] = mosaicPdf(getBaseUrl() + AttributePath.FILE_PREVIEW_URL + id);
                } else if (fileType.equals(FILE_TYPE_OFFICE)) {
                    initialPreview[i] = mosaicOffice(getBaseUrl() + AttributePath.FILE_OFFICE_URL + id);
                } else {
                    initialPreview[i] = mosaicDefault();
                }
                //文件配置
                initialPreviewConfig[i] = "{" +
                        "caption:'" + toString(file.get("SF_ORIGINAL_NAME")) + "'," +
                        "width:'140px'," +
                        "url:FILE_DEL," +
                        "downloadUrl:FILE_DOWN + '" + id + "'," +
                        "key:'" + id + "'," +
                        "size:" + toString(file.get("SF_SIZE")) + "," +
                        "type:'" + fileType + "'," +
                        "extra:{caption:'" + toString(file.get("SF_ORIGINAL_NAME")) + "',title:'" + title + "'}," +
                        "},";
            });
            //js数组名称
            String enclosureId = "enclosure" + random();
            String enclosureNameId = "enclosureName" + random();

            builder.append("var " + enclosureId + " = " + TextUtil.toString(initialPreview) + ";");
            builder.append("var " + enclosureNameId + " = " + TextUtil.toString(initialPreviewConfig, false) + ";");
            builder.append("file.init({" +
                    "id:'#" + inputId + "'," +
                    "theme:'" + theme + "'," +
                    "uploadExtraData:{SF_TABLE_ID:'" + idEncrypt(tableId) + "',SF_TABLE_NAME:'" + idEncrypt(tableName) + "',SF_TYPE_CODE:'" + idEncrypt(typeCode) + "',SF_SEE_TYPE:'" + idEncrypt(seeType) + "',SF_SDT_CODE:'" + idEncrypt(sdtCode) + "',SF_SDI_CODE:'" + idEncrypt(info.getSdiCode()) + "'}," +
                    "showUpload:" + toString(showUpload) + "," +
                    "showRemove:" + toString(showRemove) + "," +
                    "allowedFileExtensions:" + TextUtil.toString(!allowFile ? ConfigProperties.ALLOW_SUFFIX_IMG : ConfigProperties.ALLOW_SUFFIX_FILE) + "," +
                    "maxFileSize:" + maxFileSize + "," +
                    "maxFilesNum:" + maxFilesNum + "," +
                    "maxFileCount:" + maxFileCount + "," +
                    "initialPreview:" + enclosureId + "," +
                    "initialPreviewConfig:" + enclosureNameId + "," +
                    //删除数量-1
                    "}).on('filedeleted', function() {$('#" + numberId + "').text(Number($('#" + numberId + "').text()) - 1); })" +
                    //上传数量+1
                    ".on('fileuploaded', function() {$('#" + numberId + "').text(Number($('#" + numberId + "').text()) + 1);});");

            builder.append("</script>");
            builder.append("</div>");

            builder.append("</div>");
        });


        builder.append("</div></div></div>");
        //解决多层模态框关闭导致下一层不能滚动
        builder.append("<script>");
        builder.append("$('#kvFileinputModal').on('hidden.bs.modal', function () {" +
                "        if ($('.model-custom').length > 0) {" +
                "            $(document.body).addClass('modal-open');" +
                "        }" +
                "    });");
        builder.append("</script>");

        pageContext.getOut().print(builder.toString());

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
        title = "";
        sdtCode = "";
        tableId = "";
        tableName = "";
        typeCode = "";
        seeType = Attribute.STATUS_SUCCESS;
        multiple = true;
        theme = "";
        showUpload = true;
        showRemove = true;
        maxFilesNum = 99;
        maxFileCount = 99;
        maxFileSize = 8000;
        allowFile = false;
        return super.doEndTag();
    }

    /**
     * 获取文件类型名称
     *
     * @param fileName
     * @return
     */
    private String getFileTypeName(String fileName) {
        fileName = fileName.toLowerCase();

        if (fileName.endsWith("jpg") || fileName.endsWith("jpeg") || fileName.endsWith("png") || fileName.endsWith("gif") || fileName.endsWith("bmp") || fileName.endsWith("jpg")) {
            return FILE_TYPE_IMG;
        } else if (fileName.endsWith("pdf")) {
            return FILE_TYPE_PDF;
        } else if (fileName.endsWith("doc") || fileName.endsWith("docx") || fileName.endsWith("xls") || fileName.endsWith("xlsx") || fileName.endsWith("ppt") || fileName.endsWith("pptx")) {
            return FILE_TYPE_OFFICE;
        } else {
            return FILE_TYPE_OTHER;
        }
    }

    private String mosaicDefault() {
        return "<div class=\"kv-preview-data file-preview-other-frame\"><div class=\"file-preview-other\"><span class=\"file-other-icon\"><i class=\"glyphicon glyphicon-king\"></i></span></div></div>";
    }

    private String mosaicThumbnail(Object url) {
        return "<img src=\"" + toString(url) + "\" class=\"margin\" data-action=\"zoom\" height=\"auto\" width=\"100\" >";
    }

    private String mosaicImg(Object url, Object name) {
        return "<img src=\"" + toString(url) + "\" class=\"file-preview-image kv-preview-data\" title=\"" + name + "\" alt=\"" + name + "\" style=\"width:auto;height:150px;\">";
    }

    private String mosaicPdf(Object url) {
        return "<embed class=\"kv-preview-data file-preview-pdf\" src=\"" + toString(url) + "\" type=\"application/pdf\" style=\"width:100%;height:100%;\" />";
    }

    private String mosaicOffice(Object url) {
        return "<iframe class=\"kv-preview-data file-preview-office\" src=\"" + toString(url) + "\"></iframe>";
    }

    public FileService getFileService() {
        return fileService;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    public String getSdtCode() {
        return sdtCode;
    }

    public void setSdtCode(String sdtCode) {
        this.sdtCode = sdtCode;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public int getSeeType() {
        return seeType;
    }

    public void setSeeType(int seeType) {
        this.seeType = seeType;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isShowUpload() {
        return showUpload;
    }

    public void setShowUpload(boolean showUpload) {
        this.showUpload = showUpload;
    }

    public int getMaxFilesNum() {
        return maxFilesNum;
    }

    public void setMaxFilesNum(int maxFilesNum) {
        this.maxFilesNum = maxFilesNum;
    }

    public int getMaxFileCount() {
        return maxFileCount;
    }

    public void setMaxFileCount(int maxFileCount) {
        this.maxFileCount = maxFileCount;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShowRemove() {
        return showRemove;
    }

    public void setShowRemove(boolean showRemove) {
        this.showRemove = showRemove;
    }

    public boolean isAllowFile() {
        return allowFile;
    }

    public void setAllowFile(boolean allowFile) {
        this.allowFile = allowFile;
    }
}

