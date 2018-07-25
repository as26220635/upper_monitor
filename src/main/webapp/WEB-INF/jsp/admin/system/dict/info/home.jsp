<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    //查询额外参数
    function searchParams(param) {
        param.SDT_ID = '${EXTRA.SDT_ID}';
    }
</script>
<%--树形列表--%>
<%@ include file="/WEB-INF/jsp/admin/component/grid/treeGrid.jsp" %>
<script>
    setTimeout(function () {
        setMenuActive('admin-dataGrid-${MENU.SM_PARENTID}');
        editMenuTitle('${EXTRA.SDT_NAME}-' + getMenuTitle());
    }, 50);

    //添加
    $('#addBtn').on('click', function () {
        ajax.getHtml('${DICT_INFO_ADD_URL}', {SDT_ID: '${EXTRA.SDT_ID}'}, function (html) {
                model.show({
                    title: '添加字典信息',
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

                        ajax.post('${DICT_INFO_ADD_URL}', params, function (data) {
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

        ajax.getHtml('${DICT_INFO_UPDATE_URL}/' + id, {}, function (html) {
                model.show({
                    title: '修改字典信息',
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

                        ajax.put('${DICT_INFO_UPDATE_URL}', params, function (data) {
                            ajaxReturn.tree(data, $model, $treeGridTable);
                        });
                    }
                });
            }
        );
    });

    //删除
    $treeGridTable.find('tbody').on('click', '#del', function () {
        var data = getRowData(this);
        var id = data.ID;

        model.show({
            title: '删除字典信息',
            content: '是否删除字典信息:' + data.SDI_NAME,
            class: model.class.DANGER,
            okBtnName: model.btnName.DEL,
            footerModel: model.footerModel.ADMIN,
            isConfirm: true,
            confirm: function ($model) {
                ajax.del('${DICT_INFO_DELETE_URL}/' + id, {}, function (data) {
                    ajaxReturn.tree(data, $model, $treeGridTable);
                })
            }
        });
    });

    //切换状态
    function onSwitchChange($this, field, check, IS_STATUS) {
        showLoadingContentDiv();
        ajax.put('${DICT_INFO_SWITCH_STATUS_URL}', {ID: $this.val(), IS_STATUS: IS_STATUS}, function (data) {
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