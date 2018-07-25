<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/3/31
  Time: 21:52
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="addAndEditForm">
    <input type="hidden" name="${SUBMIT_TOKEN_NAME}" value="${token}">
    <input type="hidden" name="ID" value="${DICT.ID}">
    <div class="form-group has-feedback">
        <label>字典名称:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_DICT_TYPE", "SDT_NAME")}
               value="${DICT.SDT_NAME}" required>
    </div>
    <div class="form-group has-feedback">
        <label>字典编码:</label>
        <input type="text" class="form-control" ${fns:validField("SYS_DICT_TYPE", "SDT_CODE")}
               value="${DICT.SDT_CODE}" required>
    </div>
    <div class="form-group has-feedback">
        <label>下载权限:</label>
        <s:combobox url="${ROLE_LIST_URL}"
                    custom='${fns:validField("SYS_DICT_TYPE","SDT_ROLE_DOWN")}'
                    value="${DICT.SDT_ROLE_DOWN}" single="false"></s:combobox>
    </div>
    <div class="form-group has-feedback">
        <label>删除权限:</label>
        <s:combobox url="${ROLE_LIST_URL}"
                    custom='${fns:validField("SYS_DICT_TYPE","SDT_ROLE_DEL")}'
                    value="${DICT.SDT_ROLE_DEL}" single="false"></s:combobox>
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>