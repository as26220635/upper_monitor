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
    <input type="hidden" name="ID" value="${BUTTON.ID}">
    <div class="form-group has-feedback">
        <label>按钮名称:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_BUTTON", "SB_NAME")}
               value="${BUTTON.SB_NAME}">
    </div>
    <div class="form-group has-feedback">
        <label>按钮ID:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_BUTTON", "SB_BUTTONID")}
               value="${BUTTON.SB_BUTTONID}">
    </div>
    <div class="form-group has-feedback">
        <label>按钮方法:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_BUTTON", "SB_FUNC")}
               value="${BUTTON.SB_FUNC}">
    </div>
    <div class="form-group has-feedback">
        <label>按钮样式:</label>
        <s:combobox sdtCode="SYS_BUTTON_CLASS" custom='${fns:validField("SYS_BUTTON", "SB_CLASS")}'
                    value="${BUTTON.SB_CLASS}"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>按钮类型:</label>
        <s:combobox sdtCode="SYS_BUTTON_TYPE" custom='${fns:validField("SYS_BUTTON", "SB_TYPE")}'
                    value="${BUTTON.SB_TYPE}"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>图标:</label>
        <s:iconSelect id="SB_ICON" name="SB_ICON" value="${BUTTON.SB_ICON}"></s:iconSelect>
    </div>
    <div class="form-group has-feedback">
        <label>按钮编码:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_BUTTON", "SB_CODE")}
               value="${BUTTON.SB_CODE}">
    </div>
    <div class="form-group has-feedback">
        <label>排序:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_BUTTON", "SB_ORDER")} value="${BUTTON.SB_ORDER}">
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>