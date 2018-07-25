<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/4/16
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>

<c:if test="${empty PJAX}">
    <html>
    <head>
        <%@ include file="/WEB-INF/jsp/common/common_meta.jsp" %>
        <title>404</title>
        <%@ include file="/WEB-INF/jsp/reception/common/common_css.jsp" %>
        <%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>
    </head>
    <body class="index-page">

    <%@ include file="/WEB-INF/jsp/reception/common/common_top.jspf" %>
</c:if>


<div class="error-page">
    <div class="main main-raised">
        <div class="section section-basic" id="404Content">
            <div class="text-center">
                <div class="row">
                    <div class="col-md-8 col-md-offset-2 text-center">
                        <div>
                            <h1 class="text-info">404<i class="mdi mdi-help-circle-outline"></i></h1>
                        </div>
                        <h3>没有找到页面</h3>
                        <div class="btn-group">
                            <button class="btn btn-info col-md-6" onclick="backHtml()">上一页</button>
                            <button class="btn btn-primary col-md-6" onclick="indexHtml()">主页</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<c:if test="${empty PJAX}">
    <script type="text/javascript">
        function indexHtml() {
            window.location.href = "${BASE_URL}index";
        }

        function backHtml() {
            window.history.back();
        }
    </script>
</c:if>

<c:if test="${not empty PJAX}">
    <script>
        if (window.location.pathname.indexOf("/my_home/") == -1) {
            $(".error-page").css("padding-top", "150px");
        }
    </script>
</c:if>
