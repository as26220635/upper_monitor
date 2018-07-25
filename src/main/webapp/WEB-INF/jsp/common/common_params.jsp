<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/8/1
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>

<script>
    <%-- 基础属性  url--%>
    var BASE_URL = '${BASE_URL}';
    var MANAGER_URL = '${MANAGER_URL}';
    var IMG_URL = '${IMG_URL}';
    <%-- icon预览地址--%>
    var PREVIEW_ICON_URL = BASE_URL + "previewIcon";
    <%--错误图片地址--%>
    var IMG_ERROR = '${Attribute.IMAGE_ERROR}';
    <%-- 文件删除地址--%>
    var FILE_URL = BASE_URL + '${AttributePath.FILE_URL}';
    var FILE_PREVIEW = BASE_URL + '${AttributePath.FILE_PREVIEW_URL}';
    var FILE_UPLOAD = BASE_URL + '${AttributePath.FILE_UPLOAD_URL}';
    var FILE_UPLOAD_TEXTAREA = BASE_URL + '${AttributePath.FILE_UPLOAD_TEXTAREA_URL}';
    var FILE_DOWN = BASE_URL + '${AttributePath.FILE_DOWNLOAD_URL}';
    var FILE_DEL = BASE_URL + '${AttributePath.FILE_DEL_URL}';
    <%--token 提交名称--%>
    var SUBMIT_TOKEN_NAME = '${Attribute.SUBMIT_TOKEN_NAME}';
    <%--状态码,服务器获取--%>
    var STATUS_SUCCESS = '${Attribute.STATUS_SUCCESS}';
    var STATUS_ERROR = '${Attribute.STATUS_ERROR}';
    <%--分割符号--%>
    var SERVICE_SPLIT = '${Attribute.SERVICE_SPLIT}';
    <%--验证失败提示--%>
    var VALIDATE_FAIL = '${Tips.VALIDATE_FAIL_TEXT}';
    <%--pjax主内容DIVid--%>
    var CONTAINER = '#${container}';
</script>