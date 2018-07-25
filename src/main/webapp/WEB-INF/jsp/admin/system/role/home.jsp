<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--通用列表--%>
<%@ include file="/WEB-INF/jsp/admin/component/grid/dataGrid.jsp" %>
<script>
    //添加
    $('#addBtn').on('click', function () {
        ajax.getHtml('${ROLE_ADD_URL}', {}, function (html) {
                model.show({
                    title: '添加菜单',
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

                        ajax.post('${ROLE_ADD_URL}', params, function (data) {
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

        ajax.getHtml('${ROLE_UPDATE_URL}/' + id, {}, function (html) {
                model.show({
                    title: '修改角色',
                    content: html,
                    footerModel: model.footerModel.ADMIN,
                    <shiro:hasPermission name="SYSTEM:ROLE_UPDATE_SAVE">
                    isConfirm: true,
                    confirm: function ($model) {
                        var $form = $('#addAndEditForm');
                        //验证
                        if (!validator.formValidate($form)) {
                            demo.showNotify(ALERT_WARNING, VALIDATE_FAIL);
                            return;
                        }
                        var params = packFormParams($form);

                        ajax.put('${ROLE_UPDATE_URL}', params, function (data) {
                            ajaxReturn.data(data, $model, $dataGrid, false);
                        });
                    }
                    </shiro:hasPermission>
                });
            }
        );
    });

    //权限，菜单
    $dataGridTable.find('tbody').on('click', '#setPermission', function () {
        var data = getRowData(this);
        var ID = data.ID;

        ajax.getHtml('${ROLE_PERMISSION_TREE_MENU}' + ID, {}, function (html) {
            model.show({
                title: '设置角色权限',
                content: html,
                size: model.size.LG,
                footerModel: model.footerModel.ADMIN,
                isConfirm: true,
                confirm: function ($model) {
                    var checkeds = $('#menuTree').treeview('getChecked');
                    var menuIds = "";
                    for (var i = 0; i < checkeds.length; i++) {
                        menuIds += (checkeds[i].id + SERVICE_SPLIT);
                    }

                    var params = {};
                    params.ID = ID;
                    params.MENUIDS = menuIds;

                    ajax.put('${ROLE_PERMISSION_TREE_MENU_UPDATE}', params, function (data) {
                        ajaxReturn.data(data, null, null, null);
                    })
                }
            });
        });

    });

    //删除
    $dataGridTable.find('tbody').on('click', '#del', function () {
        var data = getRowData(this);
        var id = data.ID;

        model.show({
            title: '删除角色',
            content: '是否删除角色:' + data.SR_NAME,
            class: model.class.DANGER,
            okBtnName: model.btnName.DEL,
            footerModel: model.footerModel.ADMIN,
            isConfirm: true,
            confirm: function ($model) {
                ajax.del('${ROLE_DELETE_URL}/' + id, {}, function (data) {
                    ajaxReturn.data(data, $model, $dataGrid, false);
                })
            }
        });
    });

    //切换状态
    function onSwitchChange($this, field, check, IS_STATUS) {
        showLoadingContentDiv();
        ajax.put('${ROLE_SWITCH_STATUS_URL}', {ID: $this.val(), IS_STATUS: IS_STATUS}, function (data) {
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