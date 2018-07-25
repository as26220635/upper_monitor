<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/6/10
  Time: 22:56
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    //设置列表属性
    editTitle('${MENU_TITLE}${MENU.SM_NAME}');
    editNavbarBrand('${MENU.SM_PARENT_NAME}', '${MENU.SM_NAME}');
    editMenuTitle('${MENU_TITLE}${MENU.SM_NAME}');
    editMenuIcon('${MENU.SM_CLASSICON}');
</script>
