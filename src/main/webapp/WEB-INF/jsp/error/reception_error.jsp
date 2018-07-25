<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/4/16
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/jsp/common/common_meta.jsp" %>
    <title>异常错误</title>
    <%@ include file="/WEB-INF/jsp/reception/common/common_css.jsp" %>
    <%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>
</head>
<body class="index-page">

<%@ include file="/WEB-INF/jsp/reception/common/common_top.jspf" %>


<div class="wrapper">
    <%--<div class="header header-filter" style="background-image: url('/reception/assets/img/bg2.jpeg');height: 100px;"></div>--%>
    <%--内容界面--%>
    <div class="main main-raised" id="container" style="margin-top: 150px;min-height: auto;">
        <div class="section section-basic">
            <div class="container text-center">
                <div class="row">
                    <div class="col-md-8 col-md-offset-2 text-center">
                        <div>
                            <h1 class="text-info">异常错误<i class="mdi mdi-help-circle-outline"></i></h1>
                        </div>
                        <h3>${message}</h3>
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
<script type="text/javascript">
    function indexHtml() {
        window.location.href = "${BASE_URL}index";
    }

    function backHtml() {
        window.history.back();
    }
</script>
</body>
</html>