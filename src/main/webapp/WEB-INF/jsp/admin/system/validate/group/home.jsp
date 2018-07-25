<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    clearOrderColor();

    //查询额外参数
    function searchParams(param) {
        param.SV_ID = '${EXTRA.SV_ID}';
    }

    //格式化列ID
    function formatterValidateFields(targets, field) {
        return {
            targets: targets,
            data: field,
            render: function (data, type, full, meta) {
                var btns = '<div class="btn-list">';
                var names = data.split(SERVICE_SPLIT);
                for (var i in names) {
                    var name = names[i];
                    var color = getOrderColor(name);
                    btns += '<button class="btn btn-xs ' + color + '">' + name + '</button>';
                }
                btns +="</div>";
                return btns;
            }
        };
    }
</script>
<%--通用列表--%>
<%@ include file="/WEB-INF/jsp/admin/component/grid/dataGrid.jsp" %>
<script>
    setTimeout(function () {
        setMenuActive('admin-dataGrid-${MENU.SM_PARENTID}');
        editNavbarBrand(getParentName(), "组");
        editMenuTitle('组');
        editMenuTitle('${EXTRA.SV_TABLE}-' + getMenuTitle());
    }, 50);

    //添加
    $('#addBtn').on('click', function () {
        ajax.getHtml('${VALIDATE_GROUP_ADD_URL}', {SV_ID: '${EXTRA.SV_ID}'}, function (html) {
                model.show({
                    title: '添加验证组',
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

                        var SVF_IDS = '';
                        $("#addAndEditForm #SVF_IDS option:selected").each(function (index) {
                            SVF_IDS += $(this).val() + SERVICE_SPLIT;
                        })

                        var params = packFormParams($form);
                        params.SVF_IDS = SVF_IDS;

                        ajax.post('${VALIDATE_GROUP_ADD_URL}', params, function (data) {
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

        ajax.getHtml('${VALIDATE_GROUP_UPDATE_URL}/' + id, {}, function (html) {
                model.show({
                    title: '修改验证组',
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

                        var SVF_IDS = '';
                        $("#addAndEditForm #SVF_IDS option:selected").each(function (index) {
                            SVF_IDS += $(this).val() + SERVICE_SPLIT;
                        })

                        var params = packFormParams($form);
                        params.SVF_IDS = SVF_IDS;

                        ajax.put('${VALIDATE_GROUP_UPDATE_URL}', params, function (data) {
                            ajaxReturn.data(data, $model, $dataGrid, false);
                        });
                    }
                });
            }
        );
    });

    //删除
    $dataGridTable.find('tbody').on('click', '#del', function () {
        var data = getRowData(this);
        var id = data.ID;

        model.show({
            title: '删除验证组',
            content: '是否删除验证组:' + data.SVG_GROUP,
            class: model.class.DANGER,
            okBtnName: model.btnName.DEL,
            footerModel: model.footerModel.ADMIN,
            isConfirm: true,
            confirm: function ($model) {
                ajax.del('${VALIDATE_GROUP_DELETE_URL}/' + id, {}, function (data) {
                    ajaxReturn.data(data, $model, $dataGrid, false);
                })
            }
        });
    });
</script>