<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/3/27
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="form-group has-feedback col-md-6">
        <label>序列号:</label>
        <span>${card.BEGC_SERIAL}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>自定义标识符:</label>
        <span>${card.BEGC_ID}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>状态:</label>
        <span>${card.BEGC_STATUS_NAME}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>输入状态:</label>
        <span>${card.BEGC_INPUT}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>当前设备时间:</label>
        <span>${card.BEGC_NOW}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>继电器动作时间:</label>
        <span>${card.BEGC_TIME}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>IP:</label>
        <span>${card.BEGC_IP}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>温度计1:</label>
        <span>${card.BEGC_T1}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>湿度计1:</label>
        <span>${card.BEGC_H1}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>温度计2:</label>
        <span>${card.BEGC_T2}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>湿度计2:</label>
        <span>${card.BEGC_H2}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>固件详细版本:</label>
        <span>${card.BEGC_VER}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>单次刷卡几个人需要通过:</label>
        <span>${card.BEGC_NEXT_NUM}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>MAC:</label>
        <span>${card.BEGC_MAC}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>用户名:</label>
        <span>${card.BEGC_USERNAME}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>密码:</label>
        <span>${card.BEGC_PASSWORD}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>录入时间:</label>
        <span>${card.BEGC_ENTRY_TIME}</span>
    </div>
    <div class="form-group has-feedback col-md-6">
        <label>更新时间:</label>
        <span>${card.BEGC_UPDATE_TIME}</span>
    </div>
</div>
