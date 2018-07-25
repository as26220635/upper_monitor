<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/3/30
  Time: 0:26
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="addAndEditForm">
    <input type="hidden" name="${SUBMIT_TOKEN_NAME}" value="${token}">
    <input type="hidden" name="ID" value="${REGEX.ID}">
    <div class="form-group has-feedback">
        <label>字段名称:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_VALIDATE_REGEX", "SVR_NAME")}
               value="${REGEX.SVR_NAME}">
    </div>
    <div class="form-group has-feedback">
        <label>正则:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_VALIDATE_REGEX", "SVR_REGEX")}
               value="${REGEX.SVR_REGEX}">
    </div>
    <div class="form-group has-feedback">
        <label>错误消息提示:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_VALIDATE_REGEX", "SVR_REGEX_MESSAGE")}
               value="${REGEX.SVR_REGEX_MESSAGE}">
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>