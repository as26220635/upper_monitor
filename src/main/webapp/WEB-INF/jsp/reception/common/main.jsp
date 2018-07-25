<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/4/6
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/jsp/common/common_meta.jsp" %>
    <title>${HEAD_TITLE}</title>
    <%@ include file="/WEB-INF/jsp/reception/common/common_css.jsp" %>
    <%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>
    <style>
        @media (max-width: 992px) {
            .navbar .collapse.navbar-collapse ul li .dropdown-menu li a {
                height: auto;
                min-height: 45px;
            }

            .dropdown-menu > li > a {
                white-space: normal;
            }
        }
    </style>
</head>
<body class="index-page presentation-page" id="body-main">
<%--<!-- Navbar -->--%>
<nav class="navbar navbar-info navbar-transparent navbar-fixed-top navbar-color-on-scroll" id="sectionsNav">
    <div class="container">
        <%--logo--%>
        <%--第一标题--%>
        <div class="navbar-header" style="position: absolute;" id="navbarHeader1">
            <c:if test="${activeUser == null}">
                <a href="${BASEURL}login">
                    <div class="logo-container">
                        <div class="logo">
                            <img
                                    data-sizes="auto"
                                    data-src="${BASEURL}${Attribute.LOGO}"
                                    class="lazyload"
                                    src="${BASEURL}${Attribute.LOADING_IMAGE_100w}"
                                    style="width: 48px;height: 48px;"
                                    alt="宁职学院Logo" rel="tooltip"
                                    title="<b>点击登录${LOGIN_TIPS_TITLE}</b>"
                                    data-placement="bottom" data-html="true">
                        </div>
                        <div class="brand">
                            登录<br>${LOGIN_TIPS_TITLE}
                        </div>
                    </div>
                </a>
            </c:if>
            <c:if test="${activeUser != null}">
                <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown"/>
                <div class="logo">
                        ${activeUser.username}
                </div>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <div class="logo-username">
                                ${activeUser.username}
                        </div>
                    </li>
                    <li>
                        <a href="${baseurl}my_home/home" class="btn btn-simple btn-success logo-home"><i
                                class="mdi mdi-home"></i>我的主页</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="${baseurl}logout"
                           class="btn btn-simple btn-danger btn-sm pull-right logo-logout"><i
                                class="mdi mdi-logout-variant"></i>退出</a>
                    </li>
                </ul>
            </c:if>
        </div>
        <%--第二标题--%>
        <div class="navbar-header navbar-header-title col-md-6" id="navbarHeader2">
            <a>
                <h3 id="navbarHeaderTitle">标题2</h3>
            </a>
        </div>

        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    id="navbar-toggle-btn">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

        <div class="collapse navbar-collapse" id="nvabar_menu">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="${BASEURL}index" data-pjax="#${container}">
                        <i class="mdi mdi-home"></i> 主页
                    </a>
                </li>
                <%--学院信息--%>
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="mdi mdi-school"></i>学院信息
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <%--<li>--%>
                        <%--<c:forEach items="${collegeInfoList}" var="info">--%>
                        <%--<a href="${BASEURL}college_info/${info.key}"--%>
                        <%--data-pjax="#${container}">${info.value}</a>--%>
                        <%--</c:forEach>--%>
                        <%--</li>--%>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
<%--<!-- End Navbar -->--%>

<%--主界面--%>
<div class="wrapper custom-container" id="${container}">


</div>

<%--页脚--%>
<footer class="footer">
    <div class="container">
        <nav class="pull-left">
            <ul>
            </ul>
        </nav>
        <div class="copyright pull-right">&copy;<script>document.write(new Date().getFullYear())</script>
            ,
        </div>
    </div>
</footer>

<%@ include file="/WEB-INF/jsp/common/common_params.jsp" %>
<%@ include file="/WEB-INF/jsp/reception/common/common_bottom.jsp" %>
<script type="text/javascript">
    mainInit.initPjax();
    mainInit.initMenuAnimated({
        jquery: "#upMenu"
    });

    // 加载内容到指定容器
    $.pjax({url: this.href, container: '#${container}', replace: true});

    //pjax完成回调后的操作
    $(document).on('ready pjax:end', function (event) {
        //pjax
        mainInit.initPjax();
        //图片监听
        mainInit.initImagesLoaded();
        //移除标题切换
        if ($(".header-out").length > 0) {
            $('#navbarHeader1').removeClass("header-out");
            $('#navbarHeader2').removeClass("header-up");
        }
        //解除标题事件
        $(window).off('scroll', mainInit.scrollHead);
    });


    function indexHtml() {
        $.pjax({url: "${BASEURL}index", container: '#${container}'});
    }

    function backHtml() {
        window.history.back();
    }
</script>
</body>
</html>
