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
    <input type="hidden" name="ID" value="${SPS.ID}">
    <input type="hidden" name="SPD_ID" value="${fns:trueOrFalse(SPS != null ,SPS.SPD_ID,SPD_ID)}">
    <div class="form-group has-feedback">
        <label>步骤名称:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_PROCESS_STEP", "SPS_NAME")}
               value="${SPS.SPS_NAME}">
    </div>
    <div class="form-group has-feedback">
        <label>办理角色:</label>
        <s:treeBox custom='${fns:validField("SYS_PROCESS_STEP", "SR_ID")}'
                   value="${SPS.SR_ID}" nameValue="${SPS.SR_NAME}"
                   url="${ROLE_TREE_DATA_URL}" title="步骤办理角色"></s:treeBox>
    </div>
    <div class="form-group has-feedback">
        <label>办理类型:</label>
        <s:combobox sdtCode="SYS_STEP_TYPE"
                    custom='${fns:validField("SYS_PROCESS_STEP","SPS_STEP_TYPE")}'
                    value="${SPS.SPS_STEP_TYPE}"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>步骤顺序:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_PROCESS_STEP", "SPS_ORDER")}
               value="${SPS.SPS_ORDER}">
    </div>
    <div class="form-group has-feedback">
        <label>步骤流程状态:</label>
        <s:combobox sdtCode="SYS_PROCESS_STATUS"
                    custom='${fns:validField("SYS_PROCESS_STEP","SPS_PROCESS_STATUS")}'
                    value="${SPS.SPS_PROCESS_STATUS}"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>是否验证超时:</label>
        <s:combobox sdtCode="SYS_YES_NO"
                    custom='${fns:validField("SYS_PROCESS_STEP","SPS_IS_OVER_TIME")}'
                    value="${SPS.SPS_IS_OVER_TIME}" defaultValue="0"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>超时时间(分,默认为24小时、1440分钟):</label>
        <input type="text" class="form-control" ${fns:validField("SYS_PROCESS_STEP", "SPS_OVER_TIME")}
               value="${SPS.SPS_OVER_TIME}">
    </div>
    <div class="form-group has-feedback">
        <label>步骤标记:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_PROCESS_STEP", "SPS_TAB")}
               value="${SPS.SPS_TAB}">
    </div>
    <div class="form-group has-feedback">
        <label>是否前进校验:</label>
        <s:combobox sdtCode="SYS_YES_NO"
                    custom='${fns:validField("SYS_PROCESS_STEP","SPS_IS_ADVANCE_CHECK")}'
                    value="${SPS.SPS_IS_ADVANCE_CHECK}" defaultValue="0"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>是否退回校验:</label>
        <s:combobox sdtCode="SYS_YES_NO"
                    custom='${fns:validField("SYS_PROCESS_STEP","SPS_IS_RETREAT_CHECK")}'
                    value="${SPS.SPS_IS_RETREAT_CHECK}" defaultValue="0"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>是否前进执行:</label>
        <s:combobox sdtCode="SYS_YES_NO"
                    custom='${fns:validField("SYS_PROCESS_STEP","SPS_IS_ADVANCE_EXECUTE")}'
                    value="${SPS.SPS_IS_ADVANCE_EXECUTE}" defaultValue="0"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>是否退回执行:</label>
        <s:combobox sdtCode="SYS_YES_NO"
                    custom='${fns:validField("SYS_PROCESS_STEP","SPS_IS_RETREAT_EXECUTE")}'
                    value="${SPS.SPS_IS_RETREAT_EXECUTE}" defaultValue="0"></s:combobox>
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>