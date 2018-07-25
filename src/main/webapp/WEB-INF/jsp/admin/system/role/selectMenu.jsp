<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/3/20
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row box not-box" id="permissionDiv">
    <div class="row col-md-8" id="menuDiv">
        <div class="col-sm-12">
            <div class="row  btn-group-header text-center" style="margin-top: 0px;">
                <button type="button" class="btn btn-default btn-sm" id="btn-check-all">全选选择</button>
                <button type="button" class="btn btn-default btn-sm" id="btn-uncheck-all">取消全选</button>
                <button type="button" class="btn btn-default btn-sm" id="btn-expand-all">全部展开</button>
                <button type="button" class="btn btn-default btn-sm" id="btn-collapse-all">取消展开</button>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-md-7">
                <label for="input-check-node" class="sr-only">搜索文本:</label>
                <input type="input" class="form-control" id="input-check-node" placeholder="节点名称">
            </div>
            <div class="col-md-5 btn-group-permission">
                <div class="btn-group">
                    <button type="button" class="btn btn-success btn-sm check-node" id="btn-check-node">选中</button>
                    <button type="button" class="btn btn-warning btn-sm check-node" id="btn-uncheck-node">取消</button>
                    <button type="button" class="btn btn-info btn-sm check-node" id="btn-toggle-checked">切换</button>
                </div>
            </div>
        </div>
        <div class="col-sm-12">
            <h2>菜单列表</h2>
            <div id="menuTree" style="height:400px;overflow:auto;overflow-x:hidden;"></div>
        </div>
    </div>
    <div class="row col-md-4">
        <h2>按钮列表</h2>
        <div id="buttonDiv"></div>
    </div>
</div>
<script>
    addLoadingContentDiv('#permissionDiv');

    var treeData = {};
    var $checkableTree;
    ajax.get('${ROLE_PERMISSION_TREE_MENU_DATA}${ID}', {}, function (data) {
        $checkableTree = treeBox.create({
            tree: '#menuTree',
            data: data,
            showIcon: false,
            showCheckbox: true,
            isSelectCheck: false,
            onNodeSelected: function (event, node) {
                //选中才去查询
                if (node.state.checked == true) {
                    //切换按钮菜单
                    addLoadingContentDiv('#permissionDiv');
                    ajax.getHtml('${ROLE_PERMISSION_TREE_BUTTON}${ID}/' + node.id, {}, function (html) {
                        $('#buttonDiv').html(html);
                        removeLoadingDiv();
                    });
                } else {
                    $('#buttonDiv').html('');
                }
            },
        });
        removeLoadingDiv();
    });

    $checkableTree = $('#menuTree').treeview({
        data: treeData,
        showIcon: false,
        showCheckbox: true,
    });

    var findCheckableNodess = function () {
        return $checkableTree.treeview('search', [$('#input-check-node').val(), {
            ignoreCase: false,
            exactMatch: false
        }]);
    };
    var checkableNodes = findCheckableNodess();

    // 选中/取消/切换节点
    $('#input-check-node').on('keyup', function (e) {
        checkableNodes = findCheckableNodess();
        $('.check-node').prop('disabled', !(checkableNodes.length >= 1));
    });

    $('#btn-check-node.check-node').on('click', function (e) {
        $checkableTree.treeview('checkNode', [checkableNodes, {silent: true}]);
    });

    $('#btn-uncheck-node.check-node').on('click', function (e) {
        $checkableTree.treeview('uncheckNode', [checkableNodes, {silent: true}]);
    });

    $('#btn-toggle-checked.check-node').on('click', function (e) {
        $checkableTree.treeview('toggleNodeChecked', [checkableNodes, {silent: true}]);
    });

    //全选/取消全选
    $('#btn-check-all').on('click', function (e) {
        $checkableTree.treeview('checkAll', {silent: true});
    });

    $('#btn-uncheck-all').on('click', function (e) {
        $checkableTree.treeview('uncheckAll', {silent: true});
    });

    //展开/取消展开节点
    $('#btn-expand-all').on('click', function (e) {
        $checkableTree.treeview('expandAll', {silent: true});
    });

    $('#btn-collapse-all').on('click', function (e) {
        $checkableTree.treeview('collapseAll', {silent: true});
    });

    function getChildNodeIdArr(node) {
        var ts = [];
        if (node.nodes) {
            for (x in node.nodes) {
                ts.push(node.nodes[x].nodeId);
                if (node.nodes[x].nodes) {
                    var getNodeDieDai = getChildNodeIdArr(node.nodes[x]);
                    for (j in getNodeDieDai) {
                        ts.push(getNodeDieDai[j]);
                    }
                }
            }
        } else {
            ts.push(node.nodeId);
        }
        return ts;
    }

    function setParentNodeCheck(node) {
        var parentNode = $("#menuTree").treeview("getNode", node.parentId);
        if (parentNode.nodes) {
            var checkedCount = 0;
            for (x in parentNode.nodes) {
                if (parentNode.nodes[x].state.checked) {
                    checkedCount++;
                } else {
                    break;
                }
            }
            if (checkedCount === parentNode.nodes.length) {
                $("#menuTree").treeview("checkNode", parentNode.nodeId);
                setParentNodeCheck(parentNode);
            }
        }
    }
</script>