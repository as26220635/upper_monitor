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
    <input type="hidden" name="ID" value="${SUB.ID}">
    <input type="hidden" name="SO_ID" value="${fns:trueOrFalse(SUB != null ,SUB.SO_ID,SO_ID)}">
    <input type="hidden" name="SOS_USERTYPE" value="${SystemEnum.MANAGER.toString()}">
    <div class="form-group has-feedback">
        <label>登录账号:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_OPERATOR_SUB","SOS_USERNAME")}
               value="${SUB.SOS_USERNAME}">
    </div>
    <div class="form-group has-feedback">
        <label>备注:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_OPERATOR_SUB","SOS_REMARK")}
               value="${SUB.SOS_REMARK}">
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>