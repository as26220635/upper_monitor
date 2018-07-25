<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/3/27
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="content">
    <div class="row">
        <div class="col-xs-12">
            <div class="box" id="boxContentDiv">
                <div class="box-header">
                    <div class="col-md-12 btn-group-header">
                        <s:button back="false"></s:button>
                    </div>
                </div>
                <div class="box-body">
                    <div>
                        <b>创建人:${SPD.SAI_NAME}</b>
                        <br/>
                        <b>创建时间:${SPD.SDP_ENTRY_TIME}</b>
                    </div>
                    <br/>
                    <jsp:include page="add.jsp"></jsp:include>
                </div>
            </div>
        </div>
    </div>
</section>
<%--设置列表属性--%>
<c:set scope="request" var="MENU_TITLE" value="${SPD.SPD_NAME}-"></c:set>
<%@ include file="/WEB-INF/jsp/admin/component/setTitleParams.jsp" %>
<script>
    $('#save').on('click', function () {
        var $form = $('#addAndEditForm');
        //验证
        if (!validator.formValidate($form)) {
            demo.showNotify(ALERT_WARNING, VALIDATE_FAIL);
            return;
        }
        var params = packFormParams($form);

        ajax.put('${PROCESS_DEFINITION_UPDATE_URL}', params, function (data) {
            ajaxReturn.data(data, null, null, null);
        })
    });
</script>