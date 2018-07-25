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
    <input type="hidden" name="ID" value="${SEARCH.ID}">
    <input type="hidden" name="SC_ID" value="${fns:trueOrFalse(SEARCH != null ,SEARCH.SC_ID,SC_ID)}">
    <div class="form-group has-feedback">
        <label>搜索名称:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE_SEARCH","SCS_NAME")}
               value="${SEARCH.SCS_NAME}" required>
    </div>
    <div class="form-group has-feedback">
        <label>查询字段:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE_SEARCH","SCS_FIELD")}
               value="${SEARCH.SCS_FIELD}">
    </div>
    <div class="form-group has-feedback">
        <label>字典SDT_CODE:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE_SEARCH","SCS_SDT_CODE")}
               value="${SEARCH.SCS_SDT_CODE}">
    </div>
    <div class="form-group has-feedback">
        <label>类型:</label>
        <s:combobox sdtCode="SYS_SEARCH_TYPE" custom='${fns:validField("SYS_CONFIGURE_SEARCH","SCS_TYPE")}'
                    value="${SEARCH.SCS_TYPE}" defaultValue="1"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>查询条件:</label>
        <s:combobox sdtCode="SYS_SEARCH_METHOD" custom='${fns:validField("SYS_CONFIGURE_SEARCH","SCS_METHOD_TYPE")}'
                    value="${SEARCH.SCS_METHOD_TYPE}" defaultValue="like"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>是否显示:</label>
        <s:combobox sdtCode="SYS_YES_NO" custom='${fns:validField("SYS_CONFIGURE_SEARCH","SCC_IS_VISIBLE")}'
                    value="${SEARCH.SCC_IS_VISIBLE}" defaultValue="1"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>备注:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE_SEARCH","SCS_REMARK")}
               value="${SEARCH.SCS_REMARK}">
    </div>
    <div class="form-group has-feedback">
        <label>排序:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE_SEARCH","SCS_ORDER")}
               value="${SEARCH.SCS_ORDER}">
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>