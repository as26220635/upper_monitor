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
        <label>流程启动角色:</label>
        <s:treeBox custom='${fns:validField("SYS_PROCESS_START", "SR_ID")}'
                   value="${SPS.SR_ID}" nameValue="${SPS.SR_NAME}"
                   url="${ROLE_TREE_DATA_URL}" title="流程启动角色"></s:treeBox>
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>