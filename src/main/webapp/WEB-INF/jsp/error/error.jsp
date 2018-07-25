<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/2/27
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<c:if test="${empty PJAX}">
    <!DOCTYPE html>
    <html>
    <head>
        <title>发现异常</title>
        <%@ include file="/WEB-INF/jsp/common/common_css.jsp" %>
        <%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>
    </head>
    <body class="hold-transition login-page">
    <section class="content">
</c:if>

<div class="error-page">
    <h4 class="headline text-yellow">错误!</h4>

    <div class="error-content">
        <h3><i class="fa fa-warning text-yellow"></i> ${message}</h3>

        <p>
            你可以选择返回上一个页面或者返回主页面
        </p>
    </div>
    <div>
        <button type="button" class="btn btn-block btn-info" onclick="backHtml()">返回上一页</button>
        <button type="button" class="btn btn-block btn-info" onclick="indexHtml()">返回主页面</button>
    </div>
</div>

<c:if test="${empty PJAX}">
    </section>
    <script>
        function backHtml() {
            window.history.back(-1);
        }

        function indexHtml() {
            window.location.href = "${BASE_URL}admin/home";
        }
    </script>
    </body>
    </html>
</c:if>


