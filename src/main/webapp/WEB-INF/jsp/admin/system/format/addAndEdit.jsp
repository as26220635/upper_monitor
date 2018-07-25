<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/3/27
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="addAndEditForm">
    <input type="hidden" name="${SUBMIT_TOKEN_NAME}" value="${token}">
    <input type="hidden" name="ID" value="${FORMAT.ID}">
    <div class="form-group has-feedback">
        <label>格式名称:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_FORMAT", "SF_NAME")}
               value="${FORMAT.SF_NAME}">
    </div>
    <div class="form-group has-feedback">
        <label>格式唯一标识:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_FORMAT", "SF_CODE")}
               value="${FORMAT.SF_CODE}">
    </div>
    <div class="form-group has-feedback">
        <label>格式配置年份:</label>
        <s:datebox custom='${fns:validField("SYS_FORMAT", "SF_YEAR")}' value="${FORMAT.SF_YEAR}"></s:datebox>
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>