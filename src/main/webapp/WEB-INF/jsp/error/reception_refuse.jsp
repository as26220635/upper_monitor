<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/4/16
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="error-page">
    <div class="main main-raised">
        <div class="section section-basic" >
            <div class="text-center">
                <div class="row">
                    <div class="col-md-8 col-md-offset-2 text-center">
                        <div>
                            <h1 class="text-info">权限错误<i class="mdi mdi-alert-outline"></i></h1>
                        </div>
                        <h3>请联系管理员!</h3>
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
