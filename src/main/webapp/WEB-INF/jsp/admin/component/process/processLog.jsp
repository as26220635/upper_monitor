<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/6/21
  Time: 9:12
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<table id="processLogTable"
       class="table table-bordered table-striped table-overflow-x">
    <thead>
    <tr>
        <th style="min-width: 30px;">序号</th>
        <th style="min-width: 120px;">流程状态</th>
        <th style="min-width: 100px;">办理人</th>
        <th style="min-width: 250px;">办理意见</th>
        <th style="min-width: 150px;">办理时间</th>
    </tr>
    </thead>
</table>


<script>
    var objTable = $('#processLogTable');

    tableView.init({
        //table对象
        object: objTable,
        paging: false,
        pageLength: -1,
        cache: false,
        info: false,
        //查询URL链接
        url: '${PROCESS_LOG_LIST}',
        createdRow: function (row, data, dataIndex) {
            //提交
            if (data.SPL_TYPE == '0') {
                $(row).addClass('text-green');
            } else if (data.SPL_TYPE == '1') {
                //退回
                $(row).addClass('text-red');
            } else if (data.SPL_TYPE == '2') {
                //撤回
                $(row).addClass('text-yellow');
            }
        },
        //对应上面thead里面的序列
        columns: [
            {
                data: null,//此列不绑定数据源，用来显示序号
                title: '序号',
                width: '25px',
                className: 'text-center dataTable-column-min-width-sort'
            },
            {
                data: 'SPL_TRANSACTOR',
                title: '办理人 ',
                width: '100px',
                className: 'text-center dataTable-column-min-width'
            },
            {
                data: 'SPL_PROCESS_STATUS_NAME',
                title: '流程办理状态',
                width: '110px',
                className: 'text-center dataTable-column-min-width'
            },
            {
                data: 'SPL_OPINION',
                title: '办理意见',
                width: '250px',
                className: 'text-left dataTable-column-min-width'
            },
            {
                data: 'SPL_ENTRY_TIME',
                title: '办理时间',
                width: '130px',
                className: 'text-center dataTable-column-min-width'
            },
        ],
        <%--搜索额外参数--%>
        searchParams: function (params) {
            params['SPS_ID'] = '${SPS_ID}';
        },
        <%--列表刷新完回调--%>
        endCallback: function (api) {
            <%--序号--%>
            tableView.orderNumber(api, 0);
        }
    });
</script>