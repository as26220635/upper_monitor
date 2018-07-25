<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/3/20
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-sm-12">
        <div class="row  btn-group-header text-center" style="margin-top: 0px;">
            <button type="button" class="btn btn-success btn-sm" id="buttonSave">保存</button>
        </div>
    </div>
    <div class="col-sm-12">
        <div id="buttonTree" class=""></div>
    </div>
</div>
<script>
    ajax.get('${ROLE_PERMISSION_TREE_BUTTON_DATA}${ID}/${SM_ID}', {}, function (data) {
        //创建按钮选择
        treeBox.create({
            tree: '#buttonTree',
            data: data,
            showIcon: false,
            showCheckbox: true,
        });
    });

    //保存操作
    $('#buttonSave').on('click', function () {
        var checkeds = $('#buttonTree').treeview('getChecked');
        var buttonIds = "";
        for (var i = 0; i < checkeds.length; i++) {
            buttonIds += (checkeds[i].id + SERVICE_SPLIT);
        }

        var params = {};
        params.ID = '${ID}';
        params.SM_ID = '${SM_ID}';
        params.BUTTONIDS = buttonIds;

        ajax.put('${ROLE_PERMISSION_TREE_BUTTON_UPDATE}', params, function (data) {
            ajaxReturn.data(data, null, null, null);
        })
    });
</script>