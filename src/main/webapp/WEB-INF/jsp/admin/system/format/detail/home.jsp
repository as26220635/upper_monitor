<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    //查询额外参数
    function searchParams(param) {
        param.SF_ID = '${EXTRA.SF_ID}';
    }
</script>
<%--通用列表--%>
<%@ include file="/WEB-INF/jsp/admin/component/grid/treeGrid.jsp" %>
<script>
    setTimeout(function () {
        setMenuActive('admin-dataGrid-${MENU.SM_PARENTID}');
        editMenuTitle('${EXTRA.SF_NAME}-' + getMenuTitle());
    }, 50);

    //添加
    $('#addBtn').on('click', function () {
        ajax.getHtml('${FORMAT_DETAIL_ADD_URL}', {SF_ID: '${EXTRA.SF_ID}'}, function (html) {
                model.show({
                    title: '添加格式详细',
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

                        ajax.post('${FORMAT_DETAIL_ADD_URL}', params, function (data) {
                            ajaxReturn.tree(data, $model, $treeGridTable);
                        })
                    }
                });
            }
        );
    });

    //修改
    $treeGridTable.find('tbody').on('click', '#edit', function () {
        var data = getRowData(this);
        var id = data.ID;

        ajax.getHtml('${FORMAT_DETAIL_UPDATE_URL}/' + id, {}, function (html) {
                model.show({
                    title: '修改格式详细',
                    content: html,
                    footerModel: model.footerModel.ADMIN,
                    <shiro:hasPermission name="SYSTEM:FORMAT_DETAIL_UPDATE_SAVE">
                    isConfirm: true,
                    confirm: function ($model) {
                        var $form = $('#addAndEditForm');
                        //验证
                        if (!validator.formValidate($form)) {
                            demo.showNotify(ALERT_WARNING, VALIDATE_FAIL);
                            return;
                        }
                        var params = packFormParams($form);

                        ajax.put('${FORMAT_DETAIL_UPDATE_URL}', params, function (data) {
                            ajaxReturn.tree(data, $model, $treeGridTable);
                        });
                    }
                    </shiro:hasPermission>
                });
            }
        );
    });

    //删除
    $treeGridTable.find('tbody').on('click', '#del', function () {
        var data = getRowData(this);
        var id = data.ID;

        model.show({
            title: '删除格式详细',
            content: '是否删除格式详细:' + data.SFD_NAME,
            class: model.class.DANGER,
            okBtnName: model.btnName.DEL,
            footerModel: model.footerModel.ADMIN,
            isConfirm: true,
            confirm: function ($model) {
                ajax.del('${FORMAT_DETAIL_DELETE_URL}/' + id, {}, function (data) {
                    ajaxReturn.tree(data, $model, $treeGridTable);
                })
            }
        });
    });

    //切换状态
    function onSwitchChange($this, field, check, IS_STATUS) {
        showLoadingContentDiv();
        ajax.put('${FORMAT_DETAIL_SWITCH_STATUS_URL}', {ID: $this.val(), IS_STATUS: IS_STATUS}, function (data) {
            if (data.code == STATUS_SUCCESS) {
                demo.showNotify(ALERT_SUCCESS, '状态修改成功!');
            } else {
                $this.bootstrapSwitch('toggleState', true);
                demo.showNotify(ALERT_WARNING, '状态修改失败!');
            }
            removeLoadingDiv();
        });
    }
</script>