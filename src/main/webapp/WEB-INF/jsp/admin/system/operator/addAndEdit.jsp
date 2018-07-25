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
    <input type="hidden" name="ID" value="${OPERATOR.ID}">
    <input type="hidden" name="SOS_USERTYPE" value="${SystemEnum.MANAGER}">
    <div class="form-group has-feedback">
        <label>姓名:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_ACCOUNT_INFO", "SAI_NAME")}
               value="${OPERATOR.SAI_NAME}">
    </div>
    <div class="form-group has-feedback">
        <label>手机:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_ACCOUNT_INFO", "SAI_PHONE")}
               value="${OPERATOR.SAI_PHONE}">
    </div>
    <div class="form-group has-feedback">
        <label>邮箱:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_ACCOUNT_INFO", "SAI_EMAIL")}
               value="${OPERATOR.SAI_EMAIL}">
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>