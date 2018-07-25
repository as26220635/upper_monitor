<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    //格式化按钮
    function dataGridButtonFormat(row, btnId, btnStr) {
        if (btnId == 'cancel') {
            //如果可以作废的话放回ID
            if (row.SPS_IS_CANCEL == '否') {
                return btnStr;
            }
        } else if (btnId == 'cancelInfo') {
            //如果作废了的话可以查看作废信息
            if (row.SPS_IS_CANCEL == '是') {
                return btnStr;
            }
        } else {
            return btnStr;
        }
        return '';
    }
</script>
<%--通用列表--%>
<%@ include file="/WEB-INF/jsp/admin/component/grid/dataGrid.jsp" %>
<script>
    //作废
    $dataGridTable.find('tbody').on('click', '#cancel', function () {
        var data = getRowData(this);
        var id = data.ID;

        ajax.getHtml('${PROCESS_SCHEDULE_BASE_URL}/' + id, {}, function (html) {
                model.show({
                    title: '作废流程进度(项目名称:' + data.SPS_TABLE_NAME + ')',
                    content: html,
                    footerModel: model.footerModel.ADMIN,
                    isConfirm: true,
                    confirm: function ($model) {
                        var $form = $('#addAndEditForm');
                        //流程步骤
                        if (!validator.formValidate($form)) {
                            demo.showNotify(ALERT_WARNING, VALIDATE_FAIL);
                            return;
                        }

                        var params = packFormParams($form);

                        ajax.put('${PROCESS_SCHEDULE_CANCEL_URL}', params, function (data) {
                            ajaxReturn.data(data, $model, $dataGrid, false);
                        });
                    }
                });
            }
        );
    });

    //作废信息
    $dataGridTable.find('tbody').on('click', '#cancelInfo', function () {
        var data = getRowData(this);
        var SPSC_ID = data.SPSC_ID;

        ajax.getHtml('${PROCESS_SCHEDULE_CANCEL_URL}/' + SPSC_ID, {}, function (html) {
                model.show({
                    title: '作废流程进度信息(项目名称:' + data.SPS_TABLE_NAME + ')',
                    content: html,
                    footerModel: model.footerModel.ADMIN,
                });
            }
        );
    });

    //流程日志
    $dataGridTable.find('tbody').on('click', '#PROCESS_LOG', function () {
        var data = getRowData(this);
        var ID = data.ID;

        process.processLog({
            SPS_ID: ID,
        });
    });
</script>