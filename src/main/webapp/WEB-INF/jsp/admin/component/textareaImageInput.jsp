<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/2/26
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--上传图片--%>
<iframe id="form_target" name="form_target" style="display:none"></iframe>
<form id="my_form" target="form_target" method="post" enctype="multipart/form-data"
      style="width:0px;height:0;overflow:hidden">
    <input id="imgUpload" name="imgUpload" type="file">
    <%--上传参数--%>
    <%--<input name="tableId" value="${tableId}" type="text">--%>
    <input name="tableName" value="${fns:AESEncode(tableName)}" type="text">
    <input name="typeCode" value="${fns:AESEncode(typeCode)}" type="text">
    <input name="isSee" value="${fns:AESEncode(isSee)}" type="text">
    <input name="percodeDown" value="${fns:AESEncode(percodeDown)}" type="text">
    <input name="percodeDel" value="${fns:AESEncode(percodeDel)}" type="text">
</form>