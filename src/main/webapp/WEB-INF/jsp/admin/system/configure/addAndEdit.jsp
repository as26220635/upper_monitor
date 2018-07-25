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
    <input type="hidden" name="ID" value="${CONFIGURE.ID}">
    <div class="form-group has-feedback">
        <label>配置列表名称:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE","SC_NAME")}
               value="${CONFIGURE.SC_NAME}">
    </div>
    <div class="form-group has-feedback">
        <label>数据库视图:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE","SC_VIEW")}
               value="${CONFIGURE.SC_VIEW}">
    </div>
    <div class="form-group has-feedback">
        <label>是否开启选择:</label>
        <s:combobox sdtCode="SYS_YES_NO" custom='${fns:validField("SYS_CONFIGURE","SC_IS_SELECT")}'
                    value="${CONFIGURE.SC_IS_SELECT}"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>是否单选:</label>
        <s:combobox sdtCode="SYS_YES_NO" custom='${fns:validField("SYS_CONFIGURE","SC_IS_SINGLE")}'
                    value="${CONFIGURE.SC_IS_SINGLE}"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>是否分页:</label>
        <s:combobox sdtCode="SYS_YES_NO" custom='${fns:validField("SYS_CONFIGURE","SC_IS_PAGING")}'
                    value="${CONFIGURE.SC_IS_PAGING}" defaultValue="1"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>是否开启搜索:</label>
        <s:combobox sdtCode="SYS_YES_NO" custom='${fns:validField("SYS_CONFIGURE","SC_IS_SEARCH")}'
                    value="${CONFIGURE.SC_IS_SEARCH}" defaultValue="1"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>是否开启自定义过滤:</label>
        <s:combobox sdtCode="SYS_YES_NO" custom='${fns:validField("SYS_CONFIGURE","SC_IS_FILTER")}'
                    value="${CONFIGURE.SC_IS_FILTER}"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>SQL排序语句(ORDER BY):</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE","SC_ORDER_BY")}
               value="${CONFIGURE.SC_ORDER_BY}">
    </div>
    <div class="form-group has-feedback">
        <label>JSP地址:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_CONFIGURE","SC_JSP")}
               value="${CONFIGURE.SC_JSP}">
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>