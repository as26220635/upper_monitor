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
    <input type="hidden" name="ID" value="${ID}">
    <c:if test="${not empty SPSC}">
        <div class="form-group has-feedback">
            <label>项目名称:</label>
            <span>${SPSC.SPS_TABLE_NAME}</span>
        </div>
        <div class="form-group has-feedback">
            <label>作废人员:</label>
            <span>${SPSC.CANCEL_NAME}</span>
        </div>
        <div class="form-group has-feedback">
            <label>作废时间:</label>
            <span>${SPSC.SPSC_ENTRY_TIME}</span>
        </div>
    </c:if>
    <div class="form-group has-feedback">
        <label>作废原因:</label>
        <textarea ${fns:validField("SYS_PROCESS_SCHEDULE_CANCEL","SPSC_REASON")}
                class="form-control form-textarea"
                rows="5">${SPSC.SPSC_REASON}</textarea>
    </div>
</form>

<script>
    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>