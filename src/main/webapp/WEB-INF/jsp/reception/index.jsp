<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/4/6
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>

<style>
    .info {
        padding: 50px 0 0px;
    }

    .info .info-title {
        height: 50px;
    }

    .head-loading {
        width: 100%;
        height: 400px;
        text-align: center;
    }

    .head-loading .mdi {
        margin-top: 170px;
    }

    .owl-stage-outer {
        z-index: -1;
    }

    .owl-dots {
        margin-top: -35px !important;
    }
</style>

<%--内容界面--%>
<div class="main main-raised">
    <%--轮播器--%>
    <div id="main-carousel" class="card card-raised owl-carousel owl-theme">
        <c:forEach items="${sysCarousels}" var="img" varStatus="status">
            <a class="item" <c:if test="${img.href !=null && !img.href.equals('')}">href="${img.href}"
               target="_blank"</c:if>>
                <img src="${imgurl}${img.uploadFileId}" alt="${img.alt}">
                    <%--加载图片--%>
                <div id="jqthumb_loading" class="head-loading">
                    <i class='mdi mdi-48px mdi-spin mdi-loading'></i>
                </div>
                <div class="carousel-caption">
                    <h4>${img.title}</h4>
                </div>
            </a>
        </c:forEach>
        <a class="item">
            <img src="${BASEURL}resources/reception/images/bg.jpg" alt="">
            <%--加载图片--%>
            <div id="jqthumb_loading" class="head-loading">
                <i class='mdi mdi-48px mdi-spin mdi-loading'></i>
            </div>
            <div class="carousel-caption">
                <h4>bg</h4>
            </div>
        </a>
        <a class="item">
            <img src="${BASEURL}resources/reception/images/bg2.jpeg" alt="">
            <%--加载图片--%>
            <div id="jqthumb_loading" class="head-loading">
                <i class='mdi mdi-48px mdi-spin mdi-loading'></i>
            </div>
            <div class="carousel-caption">
                <h4>bg</h4>
            </div>
        </a>
        <a class="item">
            <img src="${BASEURL}resources/reception/images/bg3.jpeg" alt="">
            <%--加载图片--%>
            <div id="jqthumb_loading" class="head-loading">
                <i class='mdi mdi-48px mdi-spin mdi-loading'></i>
            </div>
            <div class="carousel-caption">
                <h4>bg</h4>
            </div>
        </a>
        <a class="item">
            <img src="${BASEURL}resources/reception/images/bg4.jpeg" alt="">
            <%--加载图片--%>
            <div id="jqthumb_loading" class="head-loading">
                <i class='mdi mdi-48px mdi-spin mdi-loading'></i>
            </div>
            <div class="carousel-caption">
                <h4>bg</h4>
            </div>
        </a>
        <a class="item">
            <img src="${BASEURL}resources/reception/images/bg5.jpg" alt="">
            <%--加载图片--%>
            <div id="jqthumb_loading" class="head-loading">
                <i class='mdi mdi-48px mdi-spin mdi-loading'></i>
            </div>
            <div class="carousel-caption">
                <h4>bg</h4>
            </div>
        </a>
    </div>

    <div class="section section-basic">
        <div class="container">

        </div>
    </div>
</div>
<script>
    mainInit.initPjax();
    //初始化轮播器
    mainInit.initCarousel("#main-carousel", 7000);
</script>