<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function formatterIcon(value, row, index) {
        return '<i class="' + value + '"></i>';
    }
</script>

<%--树形列表--%>
<%@ include file="/WEB-INF/jsp/admin/component/grid/treeGrid.jsp" %>

<script>
    //添加
    $('#addBtn').on('click', function () {
        ajax.getHtml('${MENU_ADD_URL}', {}, function (html) {
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

                        ajax.post('${MENU_ADD_URL}', params, function (data) {
                            demo.showNotify(ALERT_SUCCESS, data.message);
                            model.hide($model);
                            //刷新菜单
                            // $treeGridTable.bootstrapTable('refresh', {silent: true});
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
        var index = $(this).attr("data-index");

        ajax.getHtml('${MENU_UPDATE_URL}/' + id, {}, function (html) {
                model.show({
                    title: '修改菜单',
                    content: html,
                    footerModel: model.footerModel.ADMIN,
                    <shiro:hasPermission name="SYSTEM:MENU_UPDATE_SAVE">
                    isConfirm: true,
                    confirm: function ($model) {
                        var $form = $('#addAndEditForm');
                        //验证
                        if (!validator.formValidate($form)) {
                            demo.showNotify(ALERT_WARNING, VALIDATE_FAIL);
                            return;
                        }
                        var params = packFormParams($form);

                        ajax.put('${MENU_UPDATE_URL}', params, function (data) {
                            if (data.code == STATUS_SUCCESS) {
                                model.hide($model);
                                demo.showNotify(ALERT_SUCCESS, data.message);
                                //刷新菜单
                                $treeGridTable.bootstrapTable('updateRow', {index: index, row: params});
                            } else {
                                demo.showNotify(ALERT_WARNING, data.message);
                            }
                        });
                    }
                    </shiro:hasPermission>
                });
            }
        );
    });

    //设置按钮
    $treeGridTable.find('tbody').on('click', '#setButton', function () {
        var data = getRowData(this);
        var id = data.ID;

        treeBox.init({
            title: '选择按钮',
            url: '${MENU_TREE_BUTTON_DATA_URL}',
            modelSize: model.size.LG,
            searchParams: {
                ID: id
            },
            isConfirm: true,
            confirm: function ($model, nodes) {
                var buttonIds = "";
                for (var i = 0; i < nodes.length; i++) {
                    buttonIds += (nodes[i].id + SERVICE_SPLIT);
                }
                var params = {};
                params.ID = id;
                params.BUTTONIDS = buttonIds;

                ajax.put('${MENU_TREE_BUTTON_DATA_UPDATE_URL}', params, function (data) {
                    ajaxReturn.tree(data, $model, null, null);
                })
            }
        });
    });


    //删除按钮
    $treeGridTable.find('tbody').on('click', '#del', function () {
        var data = getRowData(this);
        var id = data.ID;

        model.show({
            title: '删除菜单',
            content: '是否删除菜单:' + name + ',会连下面的子菜单一起删除!',
            class: model.class.DANGER,
            okBtnName: model.btnName.DEL,
            footerModel: model.footerModel.ADMIN,
            isConfirm: true,
            confirm: function ($model) {
                ajax.del('${MENU_DELETE_URL}/' + id, {}, function (data) {
                    if (data.code == STATUS_SUCCESS) {
                        model.hide($model);
                        demo.showNotify(ALERT_SUCCESS, data.message);
                        // $treeGridTable.bootstrapTable('refresh', {silent: false});
                    } else {
                        demo.showNotify(ALERT_WARNING, data.message);
                    }
                })
            }
        });
    });

    //切换状态
    function onSwitchChange($this, field, check, IS_STATUS) {
        showLoadingContentDiv();
        ajax.put('${MENU_SWITCH_STATUS_URL}', {ID: $this.val(), IS_STATUS: IS_STATUS}, function (data) {
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