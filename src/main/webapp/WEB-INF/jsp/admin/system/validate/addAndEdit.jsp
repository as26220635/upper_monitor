<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/3/31
  Time: 21:52
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="addAndEditForm">
    <input type="hidden" name="${SUBMIT_TOKEN_NAME}" value="${token}">
    <input type="hidden" name="ID" value="${VALIDATE.ID}">
    <div class="form-group has-feedback">
        <label>表名:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_VALIDATE", "SV_TABLE")}
               value="${VALIDATE.SV_TABLE}" required>
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>