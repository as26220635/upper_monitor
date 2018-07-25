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
    <input type="hidden" name="ID" value="${FILED.ID}">
    <input type="hidden" name="SV_ID" value="${fns:trueOrFalse(FILED != null ,FILED.SV_ID,SV_ID)}">
    <div class="form-group has-feedback">
        <label>字段名称:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_VALIDATE_FIELD","SVF_NAME")}
               value="${FILED.SVF_NAME}" required>
    </div>
    <div class="form-group has-feedback">
        <label>查询字段:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_VALIDATE_FIELD","SVF_FIELD")}
               value="${FILED.SVF_FIELD}">
    </div>
    <div class="form-group has-feedback">
        <label>是否必填:</label>
        <s:combobox sdtCode="SYS_YES_NO" custom='${fns:validField("SYS_VALIDATE_FIELD","SVF_IS_REQUIRED")}'
                    value="${FILED.SVF_IS_REQUIRED}" defaultValue="0"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>最小字数:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_VALIDATE_FIELD","SVF_MIN_LENGTH")}
               value="${FILED.SVF_MIN_LENGTH}">
    </div>
    <div class="form-group has-feedback">
        <label>最大字数:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_VALIDATE_FIELD","SVF_MAX_LENGTH")}
               value="${FILED.SVF_MAX_LENGTH}">
    </div>
    <div class="form-group has-feedback">
        <label>正则:</label>
        <s:treeBox id="SVR_ID" name="SVR_NAME" value="${FILED.SVR_ID}" nameValue="${FILED.SVR_NAME}"
                   url="${VALIDATE_REGEX_TREE_DATA_URL}" title="选择正则"></s:treeBox>
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>