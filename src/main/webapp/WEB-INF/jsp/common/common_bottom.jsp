<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="BASE_URL" value="${pageContext.request.contextPath}/"/>
<%-- jQuery 3.2.1 --%>
<script src="${BASE_URL}resources/static/dist/js/jquery-3.2.1.min.js?ver=${VERSION}"></script>
<%-- jQuery UI 1.11.4 --%>
<%--<script src="${BASE_URL}resources/static/plugins/jquery-ui-1.11.4/jquery-ui.min.js"></script>--%>
<%-- jQuery ajax from--%>
<script src="${BASE_URL}resources/static/plugins/jquery-form/dist/jquery.form.min.js?ver=${VERSION}"></script>
<%-- Bootstrap 3.3.6 --%>
<script src="${BASE_URL}resources/static/bootstrap/js/bootstrap.min.js?ver=${VERSION}"></script>
<%--pjax--%>
<script src="${BASE_URL}resources/reception/plugins/jquery-pjax-master/jquery.pjax.js?ver=${VERSION}"
        type="text/javascript"></script>
<script src="${BASE_URL}resources/reception/plugins/arrival/arrival.min.js?ver=${VERSION}" type="text/javascript"></script>
<%--zoom图片缩放--%>
<script src="${BASE_URL}resources/static/plugins/zoom/dist/zoom.min.js?ver=${VERSION}"></script>
<%-- Sparkline --%>
<%--<script src="${BASE_URL}resources/static/plugins/sparkline/jquery.sparkline.min.js"></script>--%>
<%-- jQuery Knob Chart --%>
<%--<script src="${BASE_URL}resources/static/plugins/knob/jquery.knob.js" async defer></script>--%>
<%-- daterangepicker --%>
<%--<script src="${BASE_URL}resources/static/plugins/moment/2.17.1/moment.min.js" async defer></script>--%>
<%--<script src="${BASE_URL}resources/static/plugins/daterangepicker/daterangepicker.js" async defer></script>--%>
<%-- datepicker --%>
<script src="${BASE_URL}resources/static/plugins/datepicker/bootstrap-datepicker.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/static/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js?ver=${VERSION}"></script>
<%-- AdminLTE App --%>
<script src="${BASE_URL}resources/static/dist/js/adminlte.min.js?ver=${VERSION}"></script>
<%--<script src="${BASE_URL}resources/static/dist/js/app.js?ver=${VERSION}"></script>--%>
<%--<script src="${BASE_URL}resources/static/dist/js/adminlte.min.js"></script>--%>
<%-- AdminLTE dashboard demo (This is only for demo purposes) --%>
<%--<script src="${BASE_URL}resources/static/dist/js/pages/dashboard.js"></script>--%>
<%-- AdminLTE for demo purposes --%>
<script src="${BASE_URL}resources/static/dist/js/demo.js?ver=${VERSION}"></script>
<%--数据表--%>
<script src="${BASE_URL}resources/static/plugins/datatables/js/jquery.dataTables.min.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/static/plugins/datatables/js/dataTables.bootstrap.min.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/static/plugins/datatables/js/dataTables.select.min.js?ver=${VERSION}"></script>
<%--加载条--%>
<script src="${BASE_URL}resources/reception/plugins/nprogress/nprogress.js?ver=${VERSION}" type="text/javascript"></script>
<%--Select2 --%>
<script src="${BASE_URL}resources/static/plugins/select2/select2.full.min.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/static/plugins/select2/i18n/zh-CN.js?ver=${VERSION}"></script>
<%--文件上传--%>
<script src="${BASE_URL}resources/static/plugins/bootstrap-fileinput/js/fileinput.min.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/static/plugins/bootstrap-fileinput/js/locales/zh.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/static/plugins/bootstrap-fileinput/themes/explorer/theme.js?ver=${VERSION}"></script>
<%--裁剪图片--%>
<script src="${BASE_URL}resources/static/plugins/Jcrop-WIP-2.x/0.912/jquery.Jcrop.min.js?ver=${VERSION}"></script>
<%--验证validata--%>
<script src="${BASE_URL}resources/static/plugins/bootstrapvalidator-master/dist/js/bootstrapValidator.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/static/plugins/bootstrapvalidator-master/src/js/language/zh_CN.js?ver=${VERSION}"></script>
<%--编辑器--%>
<script src="${BASE_URL}resources/static/plugins/tinymce/tinymce.min.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/static/plugins/tinymce/jquery.tinymce.min.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/static/plugins/tinymce/langs/zh_CN.js?ver=${VERSION}"></script>
<%--图表--%>
<script src="${BASE_URL}resources/static/plugins/raphael/2.2.1/raphael.min.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/static/plugins/morris/morris.min.js?ver=${VERSION}"></script>
<%--滚动监听--%>
<script src="${BASE_URL}resources/reception/assets/js/jquery.waypoints.min.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/reception/assets/js/infinite.min.js?ver=${VERSION}"></script>
<%--延迟点击--%>
<script src="${BASE_URL}resources/reception/plugins/delay-button/delay-button.js?ver=${VERSION}"></script>
<%--树形菜单--%>
<script src="${BASE_URL}resources/static/plugins/bootstrap-treeview/bootstrap-treeview.js?ver=${VERSION}"></script>
<%--树形列表--%>
<script src="${BASE_URL}resources/static/plugins/bootstrapTable-treeView/bootstrap-table.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/static/plugins/bootstrapTable-treeView/bootstraptable-treeview.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/static/plugins/bootstrapTable-treeView/locale/bootstrap-table-zh-CN.js?ver=${VERSION}"></script>
<%--切换按钮--%>
<script src="${BASE_URL}resources/static/plugins/bootstrap-switch/js/bootstrap-switch.min.js?ver=${VERSION}"></script>
<%--通知--%>
<script src="${BASE_URL}resources/reception/myHome/js/bootstrap-notify.js?ver=${VERSION}"></script>
<script src="${BASE_URL}resources/reception/myHome/js/main.js?ver=${VERSION}"></script>
<%--主页js--%>
<script src="${BASE_URL}resources/static/create/js/first.js?ver=${VERSION}"></script>
<%--通用组件--%>
<script src="${BASE_URL}resources/static/create/js/component.js?ver=${VERSION}"></script>
<%--参数初始化--%>
<script>
    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);
</script>