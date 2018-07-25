<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--通用列表--%>
<%@ include file="/WEB-INF/jsp/admin/component/grid/dataGrid.jsp" %>
<script>
    //添加
    $('#addBtn').on('click', function () {
        ajax.getHtml('${CONFIGURE_ADD_URL}', {}, function (html) {
                model.show({
                    title: '添加配置列表',
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

                        ajax.post('${CONFIGURE_ADD_URL}', params, function (data) {
                            ajaxReturn.data(data, $model, $dataGrid, true);
                        })
                    }
                });
            }
        );
    });

    //修改
    $dataGridTable.find('tbody').on('click', '#edit', function () {
        var data = getRowData(this);
        var id = data.ID;

        ajax.getHtml('${CONFIGURE_UPDATE_URL}/' + id, {}, function (html) {
                model.show({
                    title: '修改配置列表',
                    content: html,
                    footerModel: model.footerModel.ADMIN,
                    <shiro:hasPermission name="SYSTEM:CONFIGURE_UPDATE_SAVE">
                    isConfirm: true,
                    confirm: function ($model) {
                        var $form = $('#addAndEditForm');
                        //验证
                        if (!validator.formValidate($form)) {
                            demo.showNotify(ALERT_WARNING, VALIDATE_FAIL);
                            return;
                        }
                        var params = packFormParams($form);

                        ajax.put('${CONFIGURE_UPDATE_URL}', params, function (data) {
                            ajaxReturn.data(data, $model, $dataGrid, false);
                        });
                    }
                    </shiro:hasPermission>
                });
            }
        );
    });

    //设置字段
    $dataGridTable.find('tbody').on('click', '#setColumn', function () {
        var data = getRowData(this);
        var id = data.ID;
        //切换主界面
        loadUrl('${BASE_URL}${fns:getUrlByMenuCode("SYSTEM:CONFIGURE_SET_COLUMN")}&SC_ID=' + id + '&SC_NAME=' + encodeURIComponent(data.SC_NAME));
    });

    //设置搜索
    $dataGridTable.find('tbody').on('click', '#setSearch', function () {
        var data = getRowData(this);
        var id = data.ID;
        //切换主界面
        loadUrl('${BASE_URL}${fns:getUrlByMenuCode("SYSTEM:CONFIGURE_SET_SEARCH")}&SC_ID=' + id + '&SC_NAME=' + encodeURIComponent(data.SC_NAME));
    });

    //删除
    $dataGridTable.find('tbody').on('click', '#del', function () {
        var data = getRowData(this);
        var id = data.ID;

        model.show({
            title: '删除配置列表',
            content: '是否删除配置列表:' + data.SC_NAME,
            class: model.class.DANGER,
            okBtnName: model.btnName.DEL,
            footerModel: model.footerModel.ADMIN,
            isConfirm: true,
            confirm: function ($model) {
                ajax.del('${CONFIGURE_DELETE_URL}/' + id, {}, function (data) {
                    ajaxReturn.data(data, $model, $dataGrid, false);
                })
            }
        });
    });
</script>