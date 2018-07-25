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
    <title>不支持该版本浏览器!</title>
    <%@ include file="/WEB-INF/jsp/reception/common/common_css.jsp" %>
    <style>
        .mdi {
            font-size: 55px;
        }

        .down {
            font-size: 20px;
        }

        .down .mdi {
            font-size: 70px;
        }

        .down-a {
            margin-top: 20px;
        }
    </style>
    <script type="text/javascript">
        var userAgent = navigator.userAgent;
        var isOpera = userAgent.indexOf("Opera") > -1;
        var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera;
        var b_version = navigator.appVersion
        var version = b_version.split(";");
        var trim_Version = version[1].replace(/[ ]/g, "");

        if (!isIE || (trim_Version != "MSIE6.0" && trim_Version != "MSIE7.0" && trim_Version != "MSIE8.0" && trim_Version != "MSIE9.0")) {
            window.location.href = '${BASE_URL}index';
        }
    </script>
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
                            <h1 class="text-info">浏览器版本过低<i class="mdi mdi-alert-circle-outline"></i></h1>
                        </div>
                        <h3>请升级到IE9以上或其他的浏览器!</h3>
                        <div class="row down" style="margin-top: 30px;">
                            <a class="col-md-3" href="http://www.firefox.com.cn/" target="_blank">
                                <div class="icon icon-danger">
                                    <i class="mdi mdi-firefox"></i>
                                </div>
                                <div class="down-a">
                                    <p>FIREFOX</p>
                                </div>
                            </a>
                            <a class="col-md-3"
                               href="http://www.google.cn/intl/zh-CN/chrome/browser/thankyou.html?platform=win&installdataindex=defaultbrowser"
                               target="_blank">
                                <div class="icon icon-success">
                                    <i class="mdi mdi-google-chrome"></i>
                                </div>
                                <div class="down-a">
                                    <p>CHROME</p>
                                </div>
                            </a>
                            <a class="col-md-3" href="http://www.opera.com/zh-cn" target="_blank">
                                <div class="icon icon-warning">
                                    <i class="mdi mdi-opera"></i>
                                </div>
                                <div class="down-a">
                                    <p>OPERA</p>
                                </div>
                            </a>
                            <a class="col-md-3"
                               href="https://support.microsoft.com/zh-cn/help/17621/internet-explorer-downloads"
                               target="_blank">
                                <div class="icon icon-info">
                                    <i class="mdi mdi-internet-explorer"></i>
                                </div>
                                <div class="down-a">
                                    <p>IE11</p>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>