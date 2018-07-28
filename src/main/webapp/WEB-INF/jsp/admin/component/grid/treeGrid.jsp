<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/4/2
  Time: 11:48
  To change this template use File | Settings | File Templates.
  树形通用列表
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Random" %>
<style>
    .dataTable-column-min-width {
        word-break:break-all;
    }
</style>
<section class="content">
    <div class="row">
        <div class="col-xs-12">
            <div class="box" id="treeGridBox${MENU.ID}">
                <div class="box-header">
                    <div class="col-md-12 btn-group-header">
                        <c:forEach items="${TOP_BUTTON}" var="BUTTON">
                            <button id="${BUTTON.SB_BUTTONID}" type="button" class="${BUTTON.SB_CLASS}"
                                    onclick="${BUTTON.SB_FUNC}"><i class="${BUTTON.SB_ICON}"></i>
                                    ${BUTTON.SB_NAME}
                            </button>
                        </c:forEach>
                    </div>
                </div>
                <div class="box-body">
                    <div class="col-md-12">
                        <div class="box box-default">
                            <div class="box-header">
                                <h3 class="box-title">${MENU.SM_NAME}</h3>
                                <div class="box-tools pull-right">
                                    <button type="button" class="btn btn-box-tool"
                                            id="expandBtn${MENU.ID}"><i
                                            class="mdi mdi-arrow-expand-all"></i>展开
                                    </button>
                                    <button type="button" class="btn btn-box-tool"
                                            id="collapseBtn${MENU.ID}"><i
                                            class="mdi mdi-arrow-collapse-all"></i>合上
                                    </button>
                                    <button type="button" class="btn btn-box-tool" data-widget="refresh"
                                            id="refreshBtn${MENU.ID}"><i
                                            class="fa fa-refresh"></i>刷新
                                    </button>
                                </div>
                            </div>
                            <div class="box-body">
                                <table id="treeGrid${MENU.ID}"
                                       class="table table-bordered table-striped table-overflow-x">
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%--设置列表属性--%>
<%@ include file="/WEB-INF/jsp/admin/component/setTitleParams.jsp" %>
<script>
    $(".select2").select2({language: "zh-CN"});

    var $treeGridTable = $("#treeGrid${MENU.ID}");

    $treeGridTable.bootstrapTable({
        class: 'table table-bordered table-striped table-overflow-x',
        url: '${TREE_GRID_URL}${CONFIGURE.ID}',
        pagination: false,//分页请设置为false
        treeView: true,//是否开启树视图
        uniqueId: 'ID', //每行的id
        treeId: 'ID',//id字段
        treeField: '${treeField}',//展示树的字段
        <c:if test="${CONFIGURE.SC_IS_SEARCH == Attribute.STATUS_SUCCESS}">
        search: true,//是否启用搜索框。
        // searchOnEnterKey: true,//设置为 true时，按回车触发搜索方法，否则自动触发搜索方法。
        // strictSearch: true,//设置为 true启用全匹配搜索，否则为模糊搜索。
        </c:if>
        queryParams: function (params) {
            if (typeof searchParams == 'function') {
                searchParams(params);
            }
            //添加按钮ID
            params['SM_ID'] = '${MENU.ID}';
            return params;
        },
        columns: [
            <c:forEach items="${COLUMN_LIST}" var="COLUMN">
            {
                <%--data: <c:if test="${COLUMN.SCC_IS_OPERATION eq Attribute.STATUS_SUCCESS}">null</c:if><c:if test="${COLUMN.SCC_IS_OPERATION ne Attribute.STATUS_SUCCESS}">'${COLUMN.SCC_FIELD}'</c:if>,--%>
                field: '${fns:trueOrFalse(COLUMN.SCC_IS_OPERATION, null ,COLUMN.SCC_FIELD )}',
                title: '${COLUMN.SCC_NAME}',
                <c:if test="${not empty COLUMN.SCC_WIDTH}">
                width: '${COLUMN.SCC_WIDTH}',
                </c:if>
                align: '${COLUMN.SCC_ALIGN}',
                class: '${fns:trueOrFalse(COLUMN.SCC_IS_OPERATION, "dataTable-column-min-width-operation" , "dataTable-column-min-width" )} ' + '${COLUMN.SCC_CLASS}',
                visible: ${fns:trueOrFalse(COLUMN.SCC_IS_VISIBLE, true ,false )},
                <c:choose>
                <%--格式化函数--%>
                <c:when test="${!fns:isEmpty(COLUMN.SCC_FUNC)}">
                formatter: function (value, row, index) {
                    return ${fns:formatFunc(COLUMN.SCC_FUNC,status.index,COLUMN.SCC_FIELD)};
                }
                </c:when>
                <%--格式化状态列，变成按钮--%>
                <c:when test="${COLUMN.SCC_IS_STATUS eq Attribute.STATUS_SUCCESS}">
                formatter: function (value, row, index) {
                    return '<input type="checkbox" value="' + row.ID + '" data-field="${COLUMN.SCC_FIELD}" data-handle-width="35" name="STATUS_SWITCH" ' + (value == '${Attribute.STATUS_SUCCESS}' ? 'checked' : '') + '>';
                }
                </c:when>
                <%--操作按钮--%>
                <c:when test="${COLUMN.SCC_IS_OPERATION eq Attribute.STATUS_SUCCESS }">
                formatter: function (value, row, index) {
                    var operate = '';
                    <c:choose>
                    <%--合并操作按钮--%>
                    <c:when test="${COLUMN.SCC_IS_MERGE eq Attribute.STATUS_SUCCESS}">
                    operate+='<div class="btn-group">';
                    operate+='<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">操作 <span class="caret"></span></button>';
                    operate+='<ul class="dropdown-menu" role="menu">';
                    <c:forEach items="${LIST_BUTTON}" var="BUTTON" varStatus="status">
                    var btn${status.index} = '<li><a href="javascript:void(0);" data-index="' + index + '"  id="${BUTTON.SB_BUTTONID}" value="' + row.ID + '" onclick="${fns:formatFunc(COLUMN.SCC_FUNC,status.index + 1,COLUMN.SCC_FIELD)}"><i class="${BUTTON.SB_ICON}"></i>${BUTTON.SB_NAME}</button></li>';
                    if (typeof dataGridButtonFormat == 'function') {
                        operate += dataGridButtonFormat(row, '${BUTTON.SB_BUTTONID}', btn${status.index});
                    } else {
                        operate += btn${status.index};
                    }
                    </c:forEach>
                    operate+='</ul>';
                    operate+='</div>';
                    </c:when>
                    <c:otherwise>
                    <c:forEach items="${LIST_BUTTON}" var="BUTTON" varStatus="status">
                    var btn${status.index} = '<button type="button" class="${BUTTON.SB_CLASS} btn-xs" data-index="' + index + '"  id="${BUTTON.SB_BUTTONID}" value="' + row.ID + '" onclick="${fns:formatFunc(COLUMN.SCC_FUNC,status.index + 1,COLUMN.SCC_FIELD)}"><i class="${BUTTON.SB_ICON}"></i>${BUTTON.SB_NAME}</button>';
                    if (typeof dataGridButtonFormat == 'function') {
                        operate += dataGridButtonFormat(row, '${BUTTON.SB_BUTTONID}', btn${status.index});
                    } else {
                        operate += btn${status.index};
                    }
                    </c:forEach>
                    </c:otherwise>
                    </c:choose>

                    return operate;
                }
                </c:when>
                <c:otherwise>
                </c:otherwise>
                </c:choose>
            }
            ,
            </c:forEach>
        ],
        onPostBody: function (data) {
            $('[name=\'STATUS_SWITCH\']').bootstrapSwitch({
                onText: "开启",
                offText: "关闭",
                onColor: "success",
                offColor: "danger",
                size: "mini",
                onSwitchChange: function (el, check) {
                    var $this = $(this);
                    var IS_STATUS = check ? STATUS_SUCCESS : STATUS_ERROR;

                    if (typeof onSwitchChange == 'function') {
                        <%--确认弹出框--%>
                        model.confirm({
                            message: '是否切换状态为:' + getStatusName(IS_STATUS),
                            callback: function (result) {
                                if (result) {
                                    <%--1、按钮自身 2、按钮当前所属字段 3、按钮的状态 true false 4、格式化成 0 1的状态--%>
                                    onSwitchChange($this, $this.attr('data-field'), check, IS_STATUS);
                                } else {
                                    $this.bootstrapSwitch('toggleState', true);
                                }
                            }
                        });
                    }
                }
            });
        },
        onLoadSuccess: function () {
            if (typeof onLoadSuccess == 'function') {
                onLoadSuccess();
            }
            //是否默认展开所有节点
            <c:if test="${MENU.SM_IS_EXPAND == Attribute.STATUS_SUCCESS}">
            $treeGridTable.bootstrapTable('expandAllTree');
            </c:if>
        }
    });
    try{
        <%--设置最小宽度--%>
        var $treeGridColumns = $("#treeGrid${MENU.ID} thead tr th");
        <c:forEach items="${COLUMN_LIST}" var="COLUMN" varStatus="status">
        <c:if test="${not empty COLUMN.SCC_WIDTH}">
        $($treeGridColumns[${status.index}]).css('min-width', '${COLUMN.SCC_WIDTH}');
        </c:if>
        </c:forEach>
    }catch (e) {
    }

    $('#expandBtn${MENU.ID}').on('click', function () {
        $treeGridTable.bootstrapTable('expandAllTree');
    });

    $('#collapseBtn${MENU.ID}').on('click', function () {
        $treeGridTable.bootstrapTable('collapseAllTree');
    });

    $('#refreshBtn${MENU.ID}').on('click', function () {
        refreshTreeView();
    });

    /**
     * 根据ID获取数据
     */
    function getTreeViewData(uniqueId) {
        return $treeGridTable.bootstrapTable('getRowByUniqueId', uniqueId);
    }

    /**
     *  根据this获取
     * */
    function getRowData(row) {
        return getTreeViewData($(row).val());
    }

    /**
     * 刷新
     */
    function refreshTreeView() {
        $treeGridTable.bootstrapTable('refresh', {silent: false});
    }
</script>