<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/3/27
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row btn-group" id="controlDiv">
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="0">开进</button>
    </div>
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="1">关进</button>
    </div>
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="2">开出</button>
    </div>
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="3">关出</button>
    </div>
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="4">锁进</button>
    </div>
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="5">解锁进</button>
    </div>
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="6">锁出</button>
    </div>
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="7">解锁出</button>
    </div>
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="8">火警输出</button>
    </div>
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="9">关闭火警输出</button>
    </div>
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="a">报警输出</button>
    </div>
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="b">关闭报警输出</button>
    </div>
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="c">长开进</button>
    </div>
    <div class="col-md-3">
        <button type="button" class="btn btn-default" value="d">长开出</button>
    </div>

</div>

<script>
    //按钮点击
    $('#controlDiv button').on('click', function () {
        controlAction($(this).val());
    });

    /**
     * 操控门禁
     * @param action
     */
    function controlAction(action) {
        ajax.post('${ENTRANCE_GUARD_CARD_CONTROL_URL}/${card.ID}/' + action, {}, function (data) {
            ajaxReturn.data(data);
        });
    }
</script>