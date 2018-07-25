<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/5/16
  Time: 8:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>

<%--递归菜单参数--%>
<c:forEach var="menu" items="${treeList}">
    <%--状态为1与类型是菜单才显示 --%>
    <c:if test="${menu.IS_STATUS == Attribute.STATUS_SUCCESS}">
        <c:if test="${not empty menu.SM_URL}">
            menus['${menu.SM_URL.replace("/","-")}'] = {
            title: '${menu.SM_NAME}',
            parentName: '${menu.SM_PARENT_NAME}',
            classicon: '${menu.SM_CLASSICON}'
            };
        </c:if>
        <%--判断子类里面是否有菜单存在--%>
        <c:if test="${menu.CHILDREN_MENU ne null && menu.CHILDREN_MENU.size() > 0}">
            <%--设置父类--%>
            <c:set var="parentMenu" value="${menu}" scope="request"/>
            <c:set var="treeList" value="${menu.CHILDREN_MENU}" scope="request"/>
            <%--递归--%>
            <c:import url="/WEB-INF/jsp/admin/menuParams.jsp"/>
        </c:if>
    </c:if>
</c:forEach>