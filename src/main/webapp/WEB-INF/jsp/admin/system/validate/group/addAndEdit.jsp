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
    <input type="hidden" name="ID" value="${GROUP.ID}">
    <input type="hidden" name="SV_ID" value="${fns:trueOrFalse(GROUP != null ,GROUP.SV_ID,SV_ID)}">
    <div class="form-group has-feedback">
        <label>组:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_VALIDATE_GROUP","SVG_GROUP")}
               value="${GROUP.SVG_GROUP}" required>
    </div>
    <div class="form-group has-feedback">
        <label>组字段:</label>
        <s:combobox url="${VALIDATE_FIELD_LIST_URL}?SV_ID=${fns:trueOrFalse(GROUP != null ,GROUP.SV_ID,SV_ID)}"
                    custom='${fns:validField("SYS_VALIDATE_GROUP","SVF_IDS")}'
                    value="${GROUP.SVF_IDS}" single="false"></s:combobox>
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>