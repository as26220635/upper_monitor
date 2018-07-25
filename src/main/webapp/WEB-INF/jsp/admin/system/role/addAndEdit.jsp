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
    <input type="hidden" name="ID" value="${ROLE.ID}">
    <div class="form-group has-feedback">
        <label>角色名称:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_ROLE","SR_NAME")}
               value="${ROLE.SR_NAME}">
    </div>
    <div class="form-group has-feedback">
        <label>角色编码:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_ROLE","SR_CODE")}
               value="${ROLE.SR_CODE}">
    </div>
    <div class="form-group has-feedback">
        <label>说明:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_ROLE","SR_EXPLAIN")}
               value="${ROLE.SR_EXPLAIN}">
    </div>
    <div class="form-group has-feedback">
        <label>备注:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_ROLE","SR_REMARK")}
               value="${ROLE.SR_REMARK}">
    </div>
    <div class="form-group has-feedback">
        <label>类型:</label>
        <s:combobox sdtCode="SYS_ROLE_TYPE" custom='${fns:validField("SYS_ROLE","SR_TYPE")}'
                    value="${ROLE.SR_TYPE}" defaultValue="1"></s:combobox>
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>