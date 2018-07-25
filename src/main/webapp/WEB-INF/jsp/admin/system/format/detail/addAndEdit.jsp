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
    <input type="hidden" name="ID" value="${SFD.ID}">
    <input type="hidden" name="SF_ID" value="${fns:trueOrFalse(SFD != null ,SFD.SF_ID,SF_ID)}">
    <div class="form-group has-feedback">
        <label>格式详细名称:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_FORMAT_DETAIL", "SFD_NAME")}
               value="${SFD.SFD_NAME}">
    </div>
    <div class="form-group has-feedback">
        <label>菜单:</label>
        <s:treeBox id="SM_ID" name="SM_NAME" value="${SFD.SM_ID}" nameValue="${SFD.SM_NAME}"
                   url="${MENU_TREE_DATA_URL}" title="选择菜单"></s:treeBox>
    </div>
    <div class="form-group has-feedback">
        <label>父节点:</label>
        <s:treeBox id="SFD_PARENT_ID" name="SFD_PARENT_NAME" value="${SFD.SFD_PARENT_ID}"
                   nameValue="${SFD.SFD_PARENT_NAME}" sdtId="${fns:trueOrFalse(SFD != null ,SFD.SF_ID,SF_ID)}"
                   notId="${SFD.ID}"
                   url="${FORMAT_DETAIL_TREE_URL}" title="选择格式详细父节点"></s:treeBox>
    </div>
    <div class="form-group has-feedback">
        <label>排序:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_FORMAT_DETAIL", "SFD_ORDER")}
               value="${SFD.SFD_ORDER}">
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>