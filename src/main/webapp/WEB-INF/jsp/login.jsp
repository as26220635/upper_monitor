<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/4/6
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
%>
<html>
<head>
    <%@ include file="/WEB-INF/jsp/common/common_meta.jsp" %>
    <title>${HEAD_TITLE}</title>
    <%@ include file="/WEB-INF/jsp/reception/common/common_css.jsp" %>
    <%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>
    <style>
        .form-group .help-block {
            display: block;
        }
    </style>
</head>
<body class="index-page">

<%@ include file="/WEB-INF/jsp/reception/common/common_top.jspf" %>

<div class="wrapper">
    <%--内容界面--%>
    <div class="index-login" id="wrapper">
        <div id="bg"></div>
        <div id="overlay"></div>
        <div style="background: rgba(0, 0, 0, 0.5);height: 100%;">
            <div class="container">
                <%--animated bounceIn--%>
                <div class="row">
                    <div class="col-md-4 col-md-offset-4" style="margin-top: 125px;">
                        <div class="card card-signup">
                            <form class="form" method="post" action="${BASE_URL}login" id="submitForm">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="header header-info text-center">
                                    <h4>登录到${LOGIN_TIPS_TITLE}</h4>
                                </div>
                                <div class="content">

                                    <div class="form-group input-group">
										<span class="input-group-addon" data-toggle="tooltip" data-placement="left"
                                              title="请输入2~16位的用户名" data-container="body">
											<i class="mdi mdi-face"></i>
										</span>
                                        <input type="text" class="form-control" placeholder="账号..." id="username"
                                               name="username" value="<c:out value="${username}" escapeXml="true" />"
                                               minlength="2" maxlength="16" required>
                                    </div>

                                    <div class="form-group input-group">
										<span class="input-group-addon" data-toggle="tooltip" data-placement="left"
                                              title="请输入6~16位的密码" data-container="body">
											<i class="mdi mdi-lock-outline"></i>
										</span>
                                        <input type="password" class="form-control" placeholder="密码..." id="password"
                                               name="password" minlength="6" maxlength="16" required/>
                                    </div>
                                    <div class="form-group input-group captcha-input-group">
                                        <span class="input-group-addon" data-toggle="tooltip" data-placement="left"
                                              title="请输入5位的验证码" data-container="body">
                                            <img id="validateCode" src="${BASE_URL}check" onclick="refresh()">
										</span>
                                        <input type="text" class="form-control captcha-input" placeholder="验证码..." id="captcha"
                                               name="captcha"/>
                                    </div>
                                    <c:if test="${loginError != null}">
                                        <div class="row" style="text-align: center;color: red;margin-left: 15px;"
                                             id="errorTipsAuto">
                                            <div class="col-xs-12" style="margin-top: 5px;">
                                                    ${loginError}
                                            </div>
                                        </div>
                                    </c:if>
                                    <div class="row" style="text-align: center;color: red;margin-left: 15px;">
                                        <div class="col-xs-12" style="margin-top: 5px;display:none;" id="errprTips">
                                        </div>
                                    </div>

                                </div>
                                <div class="footer text-center">
                                    <button type="submit" class="btn btn-simple btn-info btn-lg" id="subBtn">
                                        <i class="mdi mdi-login-variant"></i>登录
                                    </button>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
<%@ include file="/WEB-INF/jsp/reception/common/common_bottom.jsp" %>
<script type="text/javascript">

    NProgress.start();

    document.onreadystatechange = subSomething;//当页面加载状态改变的时候执行这个方法.
    function subSomething() {
        if (document.readyState == 'complete') //当页面加载状态
            NProgress.done();
    }


    if ('${loginError}' != '') {
        $('#19957202-dfd0-4009-9218-1a379a9e7e2e').text(Number($('#19957202-dfd0-4009-9218-1a379a9e7e2e').text()) - 1);
        demo.showNotify(ALERT_WARNING, '${loginError}');
    }
    var $form = $("#submitForm");

    var loadingId = uuid();

    $form.submit(function (e) {
        loading.show({id: loadingId, tips: '登录中'});
    });

    $('#subBtn').on('click', function (e) {
        validate(e);
    });

    $(document).keypress(function (e) {
        // 回车键事件
        if (e.which == 13) {
            validate(e);
        }
    });

    function validate(e) {
        if ($('#captcha').val().length != 5) {
            demo.showNotify(ALERT_WARNING, '请输入5位验证码!');
            if (e && e.preventDefault) {
                e.preventDefault(); //阻止默认浏览器动作(W3C)
            } else {
                window.event.returnValue = false; //IE中阻止函数器默认动作的方式
            }
            return false;
        }
    }

    // 验证码
    function refresh() {
        NProgress.start();
        var url = '${BASE_URL}check?number=' + Math.random();
        $('#validateCode').prop('src', url).on('load', function () {
            NProgress.done();
        });
    }

    validator.init({
        form: $form,
    }).on('error.form.bv', function (e) {
        loading.hide();
    });

    <c:if test="${activeUser != null}">
    window.location.href = '${BASE_URL}my_home/home';
    </c:if>
</script>
</html>