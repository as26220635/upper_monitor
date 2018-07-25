<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/4/16
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/jsp/common/common_meta.jsp" %>
    <title>400</title>
    <%@ include file="/WEB-INF/jsp/reception/common/common_css.jsp" %>
</head>
<body class="index-page">

<div class="wrapper">
    <%--内容界面--%>
    <div class="main main-raised" id="container" style="margin-top: 150px;min-height: auto;">
        <div class="section section-basic">
            <div class="container text-center">
                <div class="row">
                    <div class="col-md-8 col-md-offset-2 text-center">
                        <div>
                            <h1 class="text-danger"><i class="mdi mdi-alert-outline" style="font-size: 45px;"></i>400&nbsp;请求出错
                            </h1>
                        </div>
                        <h3>由于语法格式有误，服务器无法理解此请求。</h3>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>

