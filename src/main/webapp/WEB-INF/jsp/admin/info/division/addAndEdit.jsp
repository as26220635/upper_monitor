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
    <input type="hidden" name="ID" value="${division.ID}">
    <div class="form-group has-feedback">
        <label>部门名称:</label>
        <input type="text" class="form-control" ${fns:validField("BUS_DIVISION", "BD_NAME")}
               value="${division.BD_NAME}">
    </div>
    <div class="form-group has-feedback">
        <label>上级部门:</label>
        <s:treeBox custom='${fns:validField("BUS_DIVISION","BD_PARENT_ID")}'
                   value="${division.BD_PARENT_ID}" nameValue="${division.BD_PARENT_NAME}"
                   notId="${division.ID}"
                   url="${DIVISION_TREE_DATA_URL}" title="选择上级部门"></s:treeBox>
    </div>
    <div class="form-group has-feedback form-group-md-6">
        <label>联系人:</label>
        <input type="text" class="form-control" ${fns:validField("BUS_DIVISION", "BD_CONTACTS")}
               value="${division.BD_CONTACTS}">
    </div>
    <div class="form-group has-feedback form-group-md-6">
        <label>手机:</label>
        <input type="text" class="form-control" ${fns:validField("BUS_DIVISION", "BD_PHONE")}
               value="${division.BD_PHONE}">
    </div>
    <div class="form-group has-feedback form-group-md-6">
        <label>固定电话:</label>
        <input type="text" class="form-control" ${fns:validField("BUS_DIVISION", "BD_FIXED_PHONE")}
               value="${division.BD_FIXED_PHONE}">
    </div>
    <div class="form-group has-feedback form-group-md-6">
        <label>邮箱:</label>
        <input type="text" class="form-control" ${fns:validField("BUS_DIVISION", "BD_EMAIL")}
               value="${division.BD_EMAIL}">
    </div>
    <div class="form-group has-feedback">
        <label>地址:</label>
        <input type="text" class="form-control" ${fns:validField("BUS_DIVISION", "BD_ADDRESS")}
               value="${division.BD_ADDRESS}">
    </div>
    <div class="form-group has-feedback">
        <label>描述:</label>
        <textarea ${fns:validField("BUS_DIVISION","BD_DESCRIBE")}
                class="form-control form-textarea"
                rows="3">${division.BD_DESCRIBE}</textarea>
    </div>
    <div class="form-group has-feedback ">
        <label>排序:</label>
        <input type="text" class="form-control" ${fns:validField("BUS_DIVISION", "BD_ORDER")}
               value="${division.BD_ORDER}">
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>