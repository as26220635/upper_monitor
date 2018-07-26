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
    <input type="hidden" name="ID" value="${card.ID}">
    <div class="form-group has-feedback">
        <label>ID(唯一标识):</label>
        <input type="text" class="form-control" ${fns:validField("BUS_ENTRANCE_GUARD_CARD", "BEGC_ID")}
               value="${card.BEGC_ID}">
    </div>
    <div class="form-group has-feedback">
        <label>用户名:</label>
        <input type="text" class="form-control" ${fns:validField("BUS_ENTRANCE_GUARD_CARD", "BEGC_USERNAME")}
               value="${card.BEGC_USERNAME}">
    </div>
    <div class="form-group has-feedback">
        <label>密码:</label>
        <input type="password" class="form-control" ${fns:validField("BUS_ENTRANCE_GUARD_CARD", "BEGC_PASSWORD")}
               value="${card.BEGC_PASSWORD}">
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>