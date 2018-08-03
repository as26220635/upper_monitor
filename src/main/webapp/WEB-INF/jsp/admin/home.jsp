<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/3/22
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>

<html>
<head>
    <%@ include file="/WEB-INF/jsp/common/common_meta.jsp" %>
    <title>${HEAD_TITLE}管理系统</title>
    <%@ include file="/WEB-INF/jsp/common/common_css.jsp" %>
    <%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">

<div class="wrapper">

    <header class="main-header">
        <%--<!-- Logo -->--%>
        <a href="javascript:void(0)" class="logo">
            <%--<!-- mini logo for sidebar mini 50x50 pixels -->--%>
            <span class="logo-mini">${MANAGER_HTML_MENU_SMALL_TITLE}</span>
            <%--<!-- logo for regular state and mobile devices -->--%>
            <span class="logo-lg">${HEAD_TITLE}</span>
        </a>
        <%--<!-- Header Navbar: style can be found in header.less -->--%>
        <nav class="navbar navbar-static-top">
            <%--<!-- Sidebar toggle button-->--%>
            <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                <span class="sr-only">切换菜单</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>

            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <%--消息以后可以添加--%>
                    <li class="dropdown notifications-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-bell-o"></i>
                            <span class="label label-warning" id="notifyNumSpan"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header" id="notifyNumLi"></li>
                            <li>
                                <ul class="menu">
                                    <li>
                                        <a href="${BASE_URL}admin/msg/home" data-pjax="#${container}"
                                           onclick="notifyMsg.obtainCount();">
                                            <i class="fa fa-bell-o text-aqua"></i>通知
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <%--<!-- User Account: style can be found in dropdown.less -->--%>
                    <li class="dropdown user user-menu">
                        <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="${BASE_URL}resources/static/dist/img/user2-160x160.jpg" class="user-image"
                                 alt="User Image">
                            <span class="hidden-xs">${activeUser.username}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <%--<!-- User image -->--%>
                            <li class="user-header">
                                <img src="${BASE_URL}resources/static/dist/img/user2-160x160.jpg" class="img-circle"
                                     alt="User Image">
                                <p>
                                    ${activeUser.username}
                                    <small>${activeUser.role}</small>
                                </p>
                            </li>
                            <%--<!-- Menu Body -->--%>
                            <li class="user-body">
                                <div class="row">
                                    <div class="col-xs-6 text-center">
                                        <a href="javascript:void(0)" id="editPasswordBtn">修改密码</a>
                                    </div>
                                    <%--<div class="col-xs-6 text-center">--%>
                                    <%--<a href="javascript:void(0)" id="editHeadPortraitBtn">修改头像</a>--%>
                                    <%--</div>--%>
                                    <%--<div class="col-xs-4 text-center">--%>
                                    <%--<a href="javascript:void(0)">Friends</a>--%>
                                    <%--</div>--%>
                                </div>
                                <%--<!-- /.row -->--%>
                            </li>
                            <%--<!-- Menu Footer-->--%>
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="javascript:void(0)" class="btn btn-default btn-flat"
                                       id="editActiveUserBtn">修改</a>
                                </div>
                                <div class="pull-right">
                                    <a href="${BASE_URL}admin/logout" class="btn btn-default btn-flat">注销</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <%--<!-- Control Sidebar Toggle Button -->--%>
                    <li>
                        <a href="javascript:void(0)" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <%--<!-- Left side column. contains the logo and sidebar -->--%>
    <aside class="main-sidebar">
        <%--<!-- sidebar: style can be found in sidebar.less -->--%>
        <section class="sidebar">
            <%--<!-- Sidebar user panel -->--%>
            <div class="user-panel" style="height: 60px">
                <%--<div class="pull-left image">--%>
                <%--<img class="img-circle">--%>
                <%--</div>--%>
                <div class="pull-left info" style="left: 0px;">
                    <p>尊敬的管理员: ${activeUser.username}</p>
                    <p>角色:${activeUser.role}</p>
                </div>
            </div>
            <%--<!--菜单-->--%>
            <ul class="sidebar-menu" data-widget="tree">
                <li class="header">${MANAGER_HTML_MENU_NAME}</li>
                <li class="treeview" id="homeTreeview">
                    <a href="${BASE_URL}admin/home" id="admin-home" data-pjax="#${container}">
                        <i class="fa fa-dashboard"></i> <span>首页</span>
                    </a>
                </li>
                <%--递归菜单--%>
                <c:set var="treeList" value="${activeUser.menus}" scope="request"></c:set>
                <c:import url="/WEB-INF/jsp/admin/myMenuTree.jsp"/>
            </ul>
        </section>
        <%--<!-- /.sidebar -->--%>
    </aside>

    <%--<!-- Content Wrapper. Contains page content -->--%>
    <div class="content-wrapper">
        <%--<!-- Content Header (Page header) -->--%>
        <section class="content-header">
            <h1 id="contentTitle">
            </h1>
            <ol class="breadcrumb">
                <li><a href="javascript:void(0)"><i id="parentIcon" class="fa fa-dashboard"></i> <span
                        id="parentName">首页</span></a></li>
                <li id="childrenName">首页</li>
            </ol>
        </section>

        <section class="content" id="contentSection">
            <div class="row" id="${container}">
            </div>
        </section>
    </div>
    <%--<!-- /.content-wrapper -->--%>
    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 0.0.1
        </div>
        <strong>CREATE BY YGX DATE:2018/3/22</strong>
    </footer>

    <aside class="control-sidebar control-sidebar-dark">
        <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
            <li></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane" id="control-sidebar-home-tab">
            </div>
        </div>
    </aside>
    <div class="control-sidebar-bg"></div>
</div>


<%--悬浮菜单--%>
<div class="right-menu text-center">
    <div id="upMenu">
        <button class="btn btn-info" onclick=" $('body,html').stop().animate({scrollTop: 0}, 250);">
            <svg style="width:24px;height:24px" viewBox="0 0 24 24">
                <path d="M13,20H11V8L5.5,13.5L4.08,12.08L12,4.16L19.92,12.08L18.5,13.5L13,8V20Z"></path>
            </svg>
        </button>
    </div>

    <%--<div class="fadeInUp animated">--%>
    <%--<button class="btn btn-info" onclick="refreshMain()">--%>
    <%--<svg style="width:24px;height:24px" viewBox="0 0 24 24">--%>
    <%--<path d="M17.65,6.35C16.2,4.9 14.21,4 12,4A8,8 0 0,0 4,12A8,8 0 0,0 12,20C15.73,20 18.84,17.45 19.73,14H17.65C16.83,16.33 14.61,18 12,18A6,6 0 0,1 6,12A6,6 0 0,1 12,6C13.66,6 15.14,6.69 16.22,7.78L13,11H20V4L17.65,6.35Z"/>--%>
    <%--</svg>--%>
    <%--</button>--%>
    <%--</div>--%>
</div>

<%@ include file="/WEB-INF/jsp/common/common_params.jsp" %>
<%@ include file="/WEB-INF/jsp/common/common_bottom.jsp" %>
<%--url--%>
<%--<script src="${BASE_URL}resources/static/create/js/commonUrl.js?ver=${VERSION}"></script>--%>
<script>
    <%--菜单 递归--%>
    var menus = {};
    menus['admin-home'] = {
        title: '首页',
        parentName: '首页',
        classicon: 'fa fa-dashboard'
    };
    menus['admin-msg-home'] = {
        title: '通知',
        parentName: '通知',
        classicon: 'fa fa-bell-o text-aqua'
    };
    <c:set var="treeList" value="${activeUser.menus}" scope="request"></c:set>
    <c:import url="/WEB-INF/jsp/admin/menuParams.jsp"/>
</script>
<script>
    <%-- 设置全局ajax请求判断 --%>
    ajax.init();
    ajax.setup({
        logoutUrl: '${BASE_URL}logout'
    });

    mainInit.initPjax();
    // 加载内容到指定容器
    $.pjax({url: this.href, container: '#${container}', replace: true});

    //修改用户信息
    $('#editActiveUserBtn').on('click', function () {

        ajax.getHtml(FIRST_QUERYUSER, {}, function (html) {
            model.show({
                title: '修改用户信息',
                content: html,
                footerModel: model.footerModel.ADMIN,
                confirm: function (model) {
                    var $form = $('#editUserForm');
                    //验证
                    if (!validator.formValidate($form)) {
                        demo.showNotify(ALERT_WARNING, VALIDATE_FAIL);
                        return;
                    }

                    var params = packFormParams($form);

                    ajax.post(FIRST_EDITUSER, params, function (data) {
                        ajaxReturn.data(data, model, null, null);
                    })
                }
            });
        });
    });
    //修改密码
    $('#editPasswordBtn').on('click', function () {

        ajax.getHtml(FIRST_PASSWORD_HTML, {}, function (html) {
            model.show({
                title: '修改密码',
                content: html,
                footerModel: model.footerModel.ADMIN,
                confirm: function (model) {
                    var $form = $('#editPasswordForm');
                    //验证
                    if (!validator.formValidate($form)) {
                        demo.showNotify(ALERT_WARNING, VALIDATE_FAIL);
                        return;
                    }

                    var params = packFormParams($form);

                    ajax.post(FIRST_PASSWORD, params, function (data) {
                        ajaxReturn.data(data, model, null, null);
                    })
                }
            });
        });
    });

    <%-- 菜单点击 --%>
    $(document).on('ready pjax:end', function (event) {
        try {
            setMenuSetting(getUrlAnalysis());
        } catch (e) {
        }
        //pjax
        mainInit.initPjax();
    });

    //设置标题等参数
    function setMenuSetting(menuId) {
        //菜单切换
        setMenuActive(menuId);
        //切换属性
        var menu = menus[menuId];
        if (!isEmpty(menu)) {
            editTitle(menu.title);
            editNavbarBrand(menu.parentName, menu.title);
            editMenuTitle(menu.title);
            editMenuIcon(menu.classicon);
        }
    }

    //菜单切换
    function setMenuActive(menuId) {
        var $selectMenu = $('#' + menuId);
        var $menuTree = $('.sidebar-menu');
        if ($menuTree.find('li.active').length == 0) {
            $selectMenu.parents('ul').css('display', 'block');
        }
        $menuTree.find('li').removeClass('active').removeClass('menu-open');
        $selectMenu.parents('li').addClass('active').addClass('menu-open');
    }

    //获得标题
    function getTitle() {
        return $('title').text();
    }

    //获得父标题
    function getParentName() {
        return $('#parentName').text();
    }
    
    //修改标题
    function editTitle(content) {
        $('title').html(content);
    }

    //修改标题栏
    function editNavbarBrand(pName, cName) {
        $('#parentName').text(pName);
        $('#childrenName').text(cName);

    }

    function getMenuTitle() {
        return $('#contentTitle').text();
    }

    function editMenuTitle(title) {
        $('#contentTitle').html(title);
    }

    function editMenuIcon(classicon) {
        $('#parentIcon').prop('class', classicon);
    }

    //解析URL
    function getUrlAnalysis() {
        var wUrl = window.location.pathname;
        return wUrl.substring(BASE_URL.length, wUrl.length).replace(/\//g, '-');
    }

    function getUrlAnalysisByTag(tag) {
        var mId = getUrlAnalysis();
        return mId.substring(0, mId.indexOf(tag) + tag.length) + '-home';
    }

    //pjax加载
    function loadUrl(url) {
        $.pjax({url: url, container: '#${container}'});
    }

    //回到主页
    function indexHtml() {
        $.pjax({url: MANAGER_URL + 'home', container: '#${container}'});
    }

    //后退
    function backHtml() {
        window.history.back();
    }

    //刷新
    function refresh() {
        $.pjax.reload('#${container}');
    }

</script>
</body>
</html>
