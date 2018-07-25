<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/2/27
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/jsp/common/common_meta.jsp" %>
    <title>500</title>
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
                            <h1 class="text-danger"><i class="mdi mdi-alert-outline" style="font-size: 45px;"></i>500
                            </h1>
                            <h2 class="text-warning">内部服务器出错</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>

