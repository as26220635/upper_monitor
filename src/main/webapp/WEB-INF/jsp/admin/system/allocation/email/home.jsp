<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/5/22
  Time: 23:48
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
                    <div class="col-md-12">
                        <div class="box box-default">
                            <div class="box-body">
                                <form id="addAndEditForm">
                                    <input type="hidden" name="${SUBMIT_TOKEN_NAME}" value="${token}">
                                    <div class="form-group has-feedback">
                                        <label>用户名:</label>
                                        <input type="text"
                                               class="form-control" ${fns:validField("SYS_ALLOCATION_EMAIL", "EMAIL_USER")}
                                               value="${EMAIL_USER}">
                                    </div>
                                    <div class="form-group has-feedback">
                                        <label>授权码:</label>
                                        <input type="password"
                                               class="form-control" ${fns:validField("SYS_ALLOCATION_EMAIL", "EMAIL_PASSWORD")}
                                               value="${EMAIL_PASSWORD}">
                                    </div>
                                    <div class="form-group has-feedback">
                                        <label>邮件协议:</label>
                                        <s:combobox sdtCode="SYS_EMAIL_PROTOCOL"
                                                    custom='${fns:validField("SYS_ALLOCATION_EMAIL","EMAIL_PROTOCOL")}'
                                                    value="${EMAIL_PROTOCOL}" defaultValue="smtp"></s:combobox>
                                    </div>
                                    <div class="form-group has-feedback">
                                        <label>是否开启SSL加密:</label>
                                        <s:combobox sdtCode="SYS_YES_NO"
                                                    custom='${fns:validField("SYS_ALLOCATION_EMAIL","EMAIL_SSL_ENABLE")}'
                                                    value="${EMAIL_SSL_ENABLE}" defaultValue="1"></s:combobox>
                                    </div>
                                    <div class="form-group has-feedback">
                                        <label>服务器地址:</label>
                                        <input type="text"
                                               class="form-control" ${fns:validField("SYS_ALLOCATION_EMAIL", "EMAIL_HOST")}
                                               value="${EMAIL_HOST}">
                                    </div>
                                    <div class="form-group has-feedback">
                                        <label>端口:</label>
                                        <input type="text"
                                               class="form-control" ${fns:validField("SYS_ALLOCATION_EMAIL", "EMAIL_PORT")}
                                               value="${EMAIL_PORT}">
                                    </div>
                                    <div class="form-group has-feedback">
                                        <label>是否需要身份验证:</label>
                                        <s:combobox sdtCode="SYS_YES_NO"
                                                    custom='${fns:validField("SYS_ALLOCATION_EMAIL","EMAIL_AUTH")}'
                                                    value="${EMAIL_AUTH}" defaultValue="1"></s:combobox>
                                    </div>
                                    <div class="form-group has-feedback">
                                        <label>是否启用:</label>
                                        <s:combobox sdtCode="SYS_YES_NO"
                                                    custom='${fns:validField("SYS_ALLOCATION_EMAIL","EMAIL_STATUS")}'
                                                    value="${EMAIL_STATUS}" defaultValue="1"></s:combobox>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>


<script>
    //保存邮件配置
    $('#save').click(function () {
        var $form = $('#addAndEditForm');
        //验证
        if (!validator.formValidate($form)) {
            demo.showNotify(ALERT_WARNING, VALIDATE_FAIL);
            return;
        }
        var params = packFormParams($form);

        ajax.put('${EMAIL_BASE_URL}', params, function (data) {
            ajaxReturn.data(data, null, null, null);
        })
    });

    //刷新缓存
    $('#cache').on('click', function () {
        ajax.post('${EMAIL_CACHE_URL}', {}, function (data) {
                ajaxReturn.data(data, null, null, null);
            }
        );
    });

    validator.init({
        //验证表单
        form: $('#addAndEditForm'),
    });
</script>