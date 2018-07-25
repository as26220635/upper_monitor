<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/5/16
  Time: 8:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%--递归菜单--%>
<c:forEach var="menu" items="${treeList}" varStatus="vs">
    <%--状态为1 --%>
    <c:if test="${menu.IS_STATUS == Attribute.STATUS_SUCCESS}">
        <li class="treeview">
                <%--菜单href为空--%>
            <c:if test="${menu.SM_URL == null || menu.SM_URL.equals('')}">
                <a href="#">
                    <i class="${menu.SM_CLASSICON}"></i>
                    <span>${menu.SM_NAME }</span>
                    <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>
                </a>
            </c:if>
                <%--菜单href不为空--%>
            <c:if test="${menu.SM_URL != null && !menu.SM_URL.equals('')}">
                <a href="${BASE_URL}${menu.SM_URL}" id="${menu.SM_URL.replace("/","-")}" data-pjax="#${container}">
                    <i class="${menu.SM_CLASSICON}"></i>
                    <span>${menu.SM_NAME }</span>
                </a>
            </c:if>
                <%--判断子类里面是否有菜单存在--%>
            <c:if test="${menu.SM_IS_LEAF == Attribute.STATUS_SUCCESS && menu.CHILDREN_MENU ne null}">
                <%--设置父类--%>
                <c:set var="parentMenu" value="${menu}" scope="request"/>
                <c:set var="treeList" value="${menu.CHILDREN_MENU}" scope="request"/>
                <ul class="treeview-menu">
                        <%--递归--%>
                    <c:import url="/WEB-INF/jsp/admin/myMenuTree.jsp"/>
                </ul>
            </c:if>
        </li>
    </c:if>
</c:forEach>