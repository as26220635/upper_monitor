<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/3/30
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="content">
    <div class="row">
        <div class="col-xs-12">
            <div class="box" id="mainBox">
                <div class="box-header">
                    <div class="col-md-12 btn-group-header">
                        <s:button></s:button>
                    </div>
                </div>
                <div class="box-body">
                    <div class="col-md-12">
                        <div class="box box-default">
                            <div class="box-body">
                                <table id="mainTable" class="table table-bordered table-striped table-overflow-x">
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
    var mainTable = $('#mainTable');

    var table = tableView.init({
        //table对象
        object: mainTable,
        //后台加载div
        adminLoadingDiv: $('#mainBox'),
        //查询URL链接
        url: STUDENT_LIST,
        //对应上面thead里面的序列
        columns: [
            {
                data: null,//此列不绑定数据源，用来显示序号
                title: '序号',
                align: 'center',
                width: '25px',
                className: 'text-center dataTable-column-min-width-sort'
            },
            {
                data: 'SCC_NAME',
                title: '名称',
                className: 'text-center dataTable-column-min-width',
            },
            {
                data: 'SCC_FIELD',
                title: '字段',
                className: 'text-center dataTable-column-min-width',
            },
            {
                data: 'SCC_ALIGN',
                title: '对齐方式',
                className: 'text-center dataTable-column-min-width',
            },
            {
                data: 'SCC_WIDTH',
                title: '宽度',
                className: 'text-center dataTable-column-min-width',
            },
            {
                data: 'SCC_SDT_CODE',
                title: '字典CODE',
                className: 'text-center dataTable-column-min-width',
            },
        ],
        endCallback: function (api) {
            // 序号
            api.column(0, {
                search: 'applied',
                order: 'applied'
            }).nodes().each(function (cell, i) {
                cell.innerHTML = i + 1;
            });
        }
    });
</script>