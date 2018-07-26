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
    <input type="hidden" name="ID" value="${COLUMN.ID}">
    <input type="hidden" name="SC_ID" value="${fns:trueOrFalse(COLUMN != null ,COLUMN.SC_ID,SC_ID)}">
    <div class="form-group has-feedback">
        <label>列名称:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE_COLUMN","SCC_NAME")}
               value="${COLUMN.SCC_NAME}">
    </div>
    <div class="form-group has-feedback">
        <label>查询字段:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE_COLUMN","SCC_FIELD")}
               value="${COLUMN.SCC_FIELD}">
    </div>
    <div class="form-group has-feedback">
        <label>对齐方式:</label>
        <s:combobox sdtCode="SYS_ALIGN" custom='${fns:validField("SYS_CONFIGURE_COLUMN","SCC_ALIGN")}'
                    value="${COLUMN.SCC_ALIGN}" defaultValue="center"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>宽度:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE_COLUMN","SCC_WIDTH")}
               value="${COLUMN.SCC_WIDTH}">
    </div>
    <div class="form-group has-feedback">
        <label>样式CLASS:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE_COLUMN","SCC_CLASS")}
               value="${COLUMN.SCC_CLASS}">
    </div>
    <div class="form-group has-feedback">
        <label>格式化函数:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE_COLUMN","SCC_FUNC")}
               value="${COLUMN.SCC_FUNC}">
    </div>
    <div class="form-group has-feedback">
        <label>字典SDT_CODE:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE_COLUMN","SCC_SDT_CODE")}
               value="${COLUMN.SCC_SDT_CODE}">
    </div>
    <div class="form-group has-feedback">
        <label>是否是操作列:</label>
        <s:combobox sdtCode="SYS_YES_NO" custom='${fns:validField("SYS_CONFIGURE_COLUMN","SCC_IS_OPERATION")}'
                    value="${COLUMN.SCC_IS_OPERATION}" defaultValue="0"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>是否合并操作列:</label>
        <s:combobox sdtCode="SYS_YES_NO" custom='${fns:validField("SYS_CONFIGURE_COLUMN","SCC_IS_MERGE")}'
                    value="${COLUMN.SCC_IS_MERGE}"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>是否是状态列:</label>
        <s:combobox sdtCode="SYS_YES_NO" custom='${fns:validField("SYS_CONFIGURE_COLUMN","SCC_IS_STATUS")}'
                    value="${COLUMN.SCC_IS_STATUS}" defaultValue="0"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>是否显示:</label>
        <s:combobox sdtCode="SYS_YES_NO" custom='${fns:validField("SYS_CONFIGURE_COLUMN","SCC_IS_VISIBLE")}'
                    value="${COLUMN.SCC_IS_VISIBLE}" defaultValue="1"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>排序:</label>
        <input type="text" class="form-control"${fns:validField("SYS_CONFIGURE_COLUMN","SCC_ORDER")}
               value="${COLUMN.SCC_ORDER}">
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>