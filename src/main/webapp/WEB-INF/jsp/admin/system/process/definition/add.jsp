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
    <input type="hidden" name="ID" value="${SPD.ID}">
    <div class="form-group has-feedback">
        <label>流程名称:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_PROCESS_DEFINITION", "SPD_NAME")}
               value="${SPD.SPD_NAME}">
    </div>
    <div class="form-group has-feedback">
        <label>流程版本:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_PROCESS_DEFINITION", "SPD_VERSION")}
               value="${SPD.SPD_VERSION}">
    </div>
    <div class="form-group has-feedback">
        <label>流程更新表名:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_PROCESS_DEFINITION", "SPD_UPDATE_TABLE")}
               value="${SPD.SPD_UPDATE_TABLE}">
    </div>
    <div class="form-group has-feedback">
        <label>流程更新表名称字段:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_PROCESS_DEFINITION", "SPD_UPDATE_NAME")}
               value="${SPD.SPD_UPDATE_NAME}">
    </div>
    <div class="form-group has-feedback">
        <label>流程描述:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_PROCESS_DEFINITION", "SPD_DESCRIBE")}
               value="${SPD.SPD_DESCRIBE}">
    </div>
    <div class="form-group has-feedback">
        <label>查看全部记录角色:</label>
        <s:treeBox id="SR_ID" name="SR_NAME" value="${SPD.SR_ID}" nameValue="${SPD.SR_NAME}"
                   url="${ROLE_TREE_DATA_URL}" title="选择查看记录角色"></s:treeBox>
    </div>
    <div class="form-group has-feedback">
        <label>是否多级退回:</label>
        <s:combobox sdtCode="SYS_YES_NO"
                    custom='${fns:validField("SYS_PROCESS_DEFINITION","IS_MULTISTAGE_BACK")}'
                    value="${SPD.IS_MULTISTAGE_BACK}" defaultValue="0"></s:combobox>
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>