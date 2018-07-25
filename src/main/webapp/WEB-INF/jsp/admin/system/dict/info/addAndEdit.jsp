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
    <input type="hidden" name="ID" value="${INFO.ID}">
    <input type="hidden" name="SDT_ID" value="${fns:trueOrFalse(INFO != null ,INFO.SDT_ID,SDT_ID)}">
    <div class="form-group has-feedback">
        <label>名称:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_DICT_INFO","SDI_NAME")}
               value="${INFO.SDI_NAME}">
    </div>
    <div class="form-group has-feedback">
        <label>编码:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_DICT_INFO","SDI_CODE")}
               value="${INFO.SDI_CODE}">
    </div>
    <div class="form-group has-feedback">
        <label>连接编码:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_DICT_INFO","SDI_INNERCODE")}
               value="${INFO.SDI_INNERCODE}">
    </div>
    <div class="form-group has-feedback">
        <label>父节点:</label>
        <s:treeBox id="SDI_PARENTID" name="SDI_PARENT_NAME" value="${MENU.SDI_PARENTID}"
                   nameValue="${MENU.SDI_PARENT_NAME}" sdtId="${fns:trueOrFalse(INFO != null ,INFO.SDT_ID,SDT_ID)}"
                   notId="${INFO.ID}"
                   url="${DICT_INFO_TREE_URL}" title="选择字典父信息节点"></s:treeBox>
    </div>
    <div class="form-group has-feedback">
        <label>是否必填:</label>
        <s:combobox sdtCode="SYS_YES_NO"
                    custom='${fns:validField("SYS_DICT_INFO","SDI_REQUIRED")}'
                    value="${INFO.SDI_REQUIRED}" defaultValue="0"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>备注:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_DICT_INFO","SDI_REMARK")}
               value="${INFO.SDI_REMARK}">
    </div>
    <div class="form-group has-feedback">
        <label>排序:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_DICT_INFO","SDI_ORDER")}
               value="${INFO.SDI_ORDER}">
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>