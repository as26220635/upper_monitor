<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--通用列表--%>
<%@ include file="/WEB-INF/jsp/admin/component/grid/dataGrid.jsp" %>
<script>
    var timeout;
    //防止重复执行
    clearTimeout(timeout);
    //5秒刷新一次列表
    loopRefresh();

    function loopRefresh() {
        timeout = setTimeout(function () {
            var length = $("#dataGrid${MENU.ID}").length;
            if (length != 0 && !isOpenStatus) {
                tableView.reload($dataGrid, false);
            }
            if (length != 0) {
                loopRefresh();
            }
        }, 10000);
    }

    //切换门禁状态
    function onSwitchChange($this, field, check, IS_STATUS) {
        showLoadingContentDiv();
        var action = check ? '0' : '1';
        ajax.post('${ENTRANCE_GUARD_CARD_CONTROL_URL}/' + $this.val() + '/' + action, {}, function (data) {
            if (data.code == STATUS_SUCCESS) {
                demo.showNotify(ALERT_SUCCESS, data.message);
            } else {
                $this.bootstrapSwitch('toggleState', true);
                demo.showNotify(ALERT_WARNING, data.message);
            }
            removeLoadingDiv();
        });
    }

    //添加
    $('#addBtn').on('click', function () {
        ajax.getHtml('${ENTRANCE_GUARD_CARD_ADD_URL}', {}, function (html) {
                model.show({
                    title: '添加门禁卡',
                    content: html,
                    footerModel: model.footerModel.ADMIN,
                    isConfirm: true,
                    confirm: function ($model) {
                        var $form = $('#addAndEditForm');
                        //验证
                        if (!validator.formValidate($form)) {
                            demo.showNotify(ALERT_WARNING, VALIDATE_FAIL);
                            return;
                        }
                        var params = packFormParams($form);

                        ajax.post('${ENTRANCE_GUARD_CARD_ADD_URL}', params, function (data) {
                            ajaxReturn.data(data, $model, $dataGrid, true);
                        })
                    }
                });
            }
        );
    });

    //详细
    $dataGridTable.find('tbody').on('click', '#detail', function () {
        var data = getRowData(this);
        var id = data.ID;

        ajax.getHtml('${ENTRANCE_GUARD_CARD_DETAIL_URL}/' + id, {}, function (html) {
                model.show({
                    title: '查看门禁卡详细',
                    content: html,
                    footerModel: model.footerModel.ADMIN,
                });
            }
        );
    });

    //操控
    $dataGridTable.find('tbody').on('click', '#control', function () {
        var data = getRowData(this);
        var id = data.ID;

        ajax.getHtml('${ENTRANCE_GUARD_CARD_CONTROL_URL}/' + id, {}, function (html) {
                model.show({
                    title: '遥控门禁:' + data.BEGC_ID,
                    content: html,
                    footerModel: model.footerModel.ADMIN,
                });
            }
        );
    });

    //修改
    $dataGridTable.find('tbody').on('click', '#edit', function () {
        var data = getRowData(this);
        var id = data.ID;

        ajax.getHtml('${ENTRANCE_GUARD_CARD_UPDATE_URL}/' + id, {}, function (html) {
                model.show({
                    title: '修改门禁卡',
                    content: html,
                    footerModel: model.footerModel.ADMIN,
                    <shiro:hasPermission name="BUS:ENTRANCE_GUARD_CARD_UPDATE_SAVE">
                    isConfirm: true,
                    confirm: function ($model) {
                        var $form = $('#addAndEditForm');
                        //验证
                        if (!validator.formValidate($form)) {
                            demo.showNotify(ALERT_WARNING, VALIDATE_FAIL);
                            return;
                        }
                        var params = packFormParams($form);

                        ajax.put('${ENTRANCE_GUARD_CARD_UPDATE_URL}', params, function (data) {
                            ajaxReturn.data(data, $model, $dataGrid, false);
                        });
                    }
                    </shiro:hasPermission>
                });
            }
        );
    });

    //删除
    $dataGridTable.find('tbody').on('click', '#del', function () {
        var data = getRowData(this);
        var id = data.ID;

        model.show({
            title: '删除门禁卡',
            content: '是否删除门禁卡:' + data.BEGC_ID,
            class: model.class.DANGER,
            okBtnName: model.btnName.DEL,
            footerModel: model.footerModel.ADMIN,
            isConfirm: true,
            confirm: function ($model) {
                ajax.del('${ENTRANCE_GUARD_CARD_DELETE_URL}/' + id, {}, function (data) {
                    ajaxReturn.data(data, $model, $dataGrid, false);
                })
            }
        });
    });
</script>