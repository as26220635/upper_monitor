<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/11/15
  Time: 16:43
  To change this template use File | Settings | File Templates.
   年段选择
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--计算出一共有多少学期--%>
<div class="profile-tabs">
    <div class="nav-align-center">
        <ul class="nav nav-pills nav-pills-info nav-collegeInfo-service" role="tablist">
            <c:forEach items="${semesterList}" var="semester">
                <li <c:if test="${semester.defaultParam}">class="active"</c:if>>
                    <a href="javascript:void(0);"
                       onclick="switchSemester('${semester.key}')">${semester.value}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<script>
    //切换tab
    $('.profile-tabs ul.nav li a').on('click', function () {
        $('.profile-tabs ul.nav li').removeClass('active');
        $(this).parent().addClass('active');
    });
    <%--拿到默认的参数--%>
    <c:forEach items="${semesterList}" var="semester">
    <c:if test="${semester.defaultParam}">
    var semesterInSection = '${semester.key.split(Attribute.SERVICE_SPLIT)[0]}';
    var semesterCode = '${semester.key.split(Attribute.SERVICE_SPLIT)[1]}';
    </c:if>
    </c:forEach>

    var oldSemesterVal;

    //切换
    function switchSemester(val) {
        if (oldSemesterVal == val) {
            return;
        }
        semesterInSection = val.split('${Attribute.SERVICE_SPLIT}')[0];
        semesterCode = val.split('${Attribute.SERVICE_SPLIT}')[1];
        if (!isEmpty(tableView)) {
            tableView.reload(table);
        }
        if (typeof(callbackSemester) == 'function') {
            callbackSemester();
        }
        oldSemesterVal = val;
    }
</script>