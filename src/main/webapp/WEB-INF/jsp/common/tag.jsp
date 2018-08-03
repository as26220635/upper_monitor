<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="cn.kim.common.attr.Attribute " %>
<%@ page import="cn.kim.common.attr.AttributePath" %>
<%@ page import="cn.kim.common.attr.Tips" %>
<%@ page import="cn.kim.common.eu.SystemEnum" %>
<%@ page import="cn.kim.common.eu.ProcessType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%--自定义--%>
<%@ taglib uri="/WEB-INF/tlds/fns.tld" prefix="fns" %>
<%@ taglib uri="/WEB-INF/tlds/tag.tld" prefix="s" %>
<%--标题参数--%>
<c:set var="HEAD_TITLE" value="XXXXXXXX"/>
<c:set var="LOGIN_TIPS_TITLE" value="XX"/>
<c:set var="MANAGER_HTML_MENU_SMALL_TITLE" value="<b>X</b>X"/>
<c:set var="MANAGER_HTML_MENU_NAME" value="菜单栏"/>
<%--js css版本--%>
<c:set var="VERSION" value="201802241536"/>
<%--pjax地址--%>
<c:set var="container" value="container"/>
<%--URL地址--%>
<c:set var="BASE_URL" value="${pageContext.request.contextPath}/"/>
<c:set var="MANAGER_URL" value="${BASE_URL}admin/"/>
<%--图片地址url--%>
<c:set var="IMG_URL" value="${BASE_URL}${AttributePath.FILE_PREVIEW_URL}"/>
<c:set var="DOWN_URL" value="${BASE_URL}${AttributePath.FILE_DOWNLOAD_URL}"/>
<%--列表地址--%>
<c:set var="DATA_GRID_URL" value="${MANAGER_URL}dataGrid/data/"/>
<c:set var="TREE_GRID_URL" value="${MANAGER_URL}treeGrid/data/"/>
<%--系统配置管理--%>
<c:set var="ALLOCATION_BASE_URL" value="${MANAGER_URL}allocation/"/>
<%--邮箱配置管理--%>
<c:set var="EMAIL_BASE_URL" value="${ALLOCATION_BASE_URL}email"/>
<c:set var="EMAIL_CACHE_URL" value="${EMAIL_BASE_URL}/cache"/>
<%--菜单管理--%>
<c:set var="MENU_BASE_URL" value="${MANAGER_URL}menu/"/>
<c:set var="MENU_LIST_URL" value="${MENU_BASE_URL}list"/>
<c:set var="MENU_TREE_DATA_URL" value="${MENU_BASE_URL}getMenuTreeData"/>
<c:set var="MENU_TREE_BUTTON_DATA_URL" value="${MENU_BASE_URL}getMenuButtonTreeData"/>
<c:set var="MENU_TREE_BUTTON_DATA_UPDATE_URL" value="${MENU_BASE_URL}updateMenuButton"/>
<c:set var="MENU_ADD_URL" value="${MENU_BASE_URL}add"/>
<c:set var="MENU_UPDATE_URL" value="${MENU_BASE_URL}update"/>
<c:set var="MENU_SWITCH_STATUS_URL" value="${MENU_BASE_URL}switchStatus"/>
<c:set var="MENU_DELETE_URL" value="${MENU_BASE_URL}delete"/>
<%--配置列表--%>
<c:set var="CONFIGURE_BASE_URL" value="${MANAGER_URL}configure/"/>
<c:set var="CONFIGURE_TREE_DATA_URL" value="${CONFIGURE_BASE_URL}getConfigureTreeData"/>
<c:set var="CONFIGURE_ADD_URL" value="${CONFIGURE_BASE_URL}add"/>
<c:set var="CONFIGURE_UPDATE_URL" value="${CONFIGURE_BASE_URL}update"/>
<c:set var="CONFIGURE_DELETE_URL" value="${CONFIGURE_BASE_URL}delete"/>
<%--配置列表列--%>
<c:set var="CONFIGURE_COLUMN_BASE_URL" value="${CONFIGURE_BASE_URL}column/"/>
<c:set var="CONFIGURE_COLUMN_ADD_URL" value="${CONFIGURE_COLUMN_BASE_URL}add"/>
<c:set var="CONFIGURE_COLUMN_UPDATE_URL" value="${CONFIGURE_COLUMN_BASE_URL}update"/>
<c:set var="CONFIGURE_COLUMN_DELETE_URL" value="${CONFIGURE_COLUMN_BASE_URL}delete"/>
<%--配置列表搜索--%>
<c:set var="CONFIGURE_SEARCH_BASE_URL" value="${CONFIGURE_BASE_URL}search/"/>
<c:set var="CONFIGURE_SEARCH_ADD_URL" value="${CONFIGURE_SEARCH_BASE_URL}add"/>
<c:set var="CONFIGURE_SEARCH_UPDATE_URL" value="${CONFIGURE_SEARCH_BASE_URL}update"/>
<c:set var="CONFIGURE_SEARCH_DELETE_URL" value="${CONFIGURE_SEARCH_BASE_URL}delete"/>
<%--按钮列表--%>
<c:set var="BUTTON_BASE_URL" value="${MANAGER_URL}button/"/>
<c:set var="BUTTON_ADD_URL" value="${BUTTON_BASE_URL}add"/>
<c:set var="BUTTON_UPDATE_URL" value="${BUTTON_BASE_URL}update"/>
<c:set var="BUTTON_DELETE_URL" value="${BUTTON_BASE_URL}delete"/>
<%--角色--%>
<c:set var="ROLE_BASE_URL" value="${MANAGER_URL}role/"/>
<c:set var="ROLE_LIST_URL" value="${ROLE_BASE_URL}list"/>
<c:set var="ROLE_TREE_DATA_URL" value="${ROLE_BASE_URL}tree"/>
<c:set var="ROLE_ADD_URL" value="${ROLE_BASE_URL}add"/>
<c:set var="ROLE_UPDATE_URL" value="${ROLE_BASE_URL}update"/>
<c:set var="ROLE_PERMISSION_TREE_MENU" value="${ROLE_BASE_URL}menuTree/"/>
<c:set var="ROLE_PERMISSION_TREE_MENU_DATA" value="${ROLE_BASE_URL}getMenuTreeData/"/>
<c:set var="ROLE_PERMISSION_TREE_MENU_UPDATE" value="${ROLE_BASE_URL}updateRoleMenuPermission"/>
<c:set var="ROLE_PERMISSION_TREE_BUTTON" value="${ROLE_BASE_URL}buttonTree/"/>
<c:set var="ROLE_PERMISSION_TREE_BUTTON_DATA" value="${ROLE_BASE_URL}getButtonTreeData/"/>
<c:set var="ROLE_PERMISSION_TREE_BUTTON_UPDATE" value="${ROLE_BASE_URL}updateRoleButtonPermission"/>
<c:set var="ROLE_SWITCH_STATUS_URL" value="${ROLE_BASE_URL}switchStatus"/>
<c:set var="ROLE_DELETE_URL" value="${ROLE_BASE_URL}delete"/>
<%--验证--%>
<c:set var="VALIDATE_BASE_URL" value="${MANAGER_URL}validate/"/>
<c:set var="VALIDATE_ADD_URL" value="${VALIDATE_BASE_URL}add"/>
<c:set var="VALIDATE_UPDATE_URL" value="${VALIDATE_BASE_URL}update"/>
<c:set var="VALIDATE_SWITCH_STATUS_URL" value="${VALIDATE_BASE_URL}switchStatus"/>
<c:set var="VALIDATE_DELETE_URL" value="${VALIDATE_BASE_URL}delete"/>
<%--验证字段--%>
<c:set var="VALIDATE_FIELD_BASE_URL" value="${VALIDATE_BASE_URL}field/"/>
<c:set var="VALIDATE_FIELD_LIST_URL" value="${VALIDATE_FIELD_BASE_URL}list"/>
<c:set var="VALIDATE_FIELD_ADD_URL" value="${VALIDATE_FIELD_BASE_URL}add"/>
<c:set var="VALIDATE_FIELD_UPDATE_URL" value="${VALIDATE_FIELD_BASE_URL}update"/>
<c:set var="VALIDATE_FIELD_SWITCH_STATUS_URL" value="${VALIDATE_FIELD_BASE_URL}switchStatus"/>
<c:set var="VALIDATE_FIELD_DELETE_URL" value="${VALIDATE_FIELD_BASE_URL}delete"/>
<%--验证组--%>
<c:set var="VALIDATE_GROUP_BASE_URL" value="${VALIDATE_BASE_URL}group/"/>
<c:set var="VALIDATE_GROUP_ADD_URL" value="${VALIDATE_GROUP_BASE_URL}add"/>
<c:set var="VALIDATE_GROUP_UPDATE_URL" value="${VALIDATE_GROUP_BASE_URL}update"/>
<c:set var="VALIDATE_GROUP_DELETE_URL" value="${VALIDATE_GROUP_BASE_URL}delete"/>
<%--验证正则--%>
<c:set var="VALIDATE_REGEX_BASE_URL" value="${VALIDATE_BASE_URL}regex/"/>
<c:set var="VALIDATE_REGEX_TREE_DATA_URL" value="${VALIDATE_REGEX_BASE_URL}tree"/>
<c:set var="VALIDATE_REGEX_ADD_URL" value="${VALIDATE_REGEX_BASE_URL}add"/>
<c:set var="VALIDATE_REGEX_UPDATE_URL" value="${VALIDATE_REGEX_BASE_URL}update"/>
<c:set var="VALIDATE_REGEX_SWITCH_STATUS_URL" value="${VALIDATE_REGEX_BASE_URL}switchStatus"/>
<c:set var="VALIDATE_REGEX_DELETE_URL" value="${VALIDATE_REGEX_BASE_URL}delete"/>
<%--字典--%>
<c:set var="DICT_BASE_URL" value="${MANAGER_URL}dict/"/>
<c:set var="DICT_ADD_URL" value="${DICT_BASE_URL}add"/>
<c:set var="DICT_UPDATE_URL" value="${DICT_BASE_URL}update"/>
<c:set var="DICT_SWITCH_STATUS_URL" value="${DICT_BASE_URL}switchStatus"/>
<c:set var="DICT_DELETE_URL" value="${DICT_BASE_URL}delete"/>
<c:set var="DICT_CACHE_URL" value="${DICT_BASE_URL}cache"/>
<%--字典信息--%>
<c:set var="DICT_INFO_BASE_URL" value="${DICT_BASE_URL}info/"/>
<c:set var="DICT_INFO_TREE_URL" value="${DICT_INFO_BASE_URL}tree"/>
<c:set var="DICT_INFO_ADD_URL" value="${DICT_INFO_BASE_URL}add"/>
<c:set var="DICT_INFO_UPDATE_URL" value="${DICT_INFO_BASE_URL}update"/>
<c:set var="DICT_INFO_SWITCH_STATUS_URL" value="${DICT_INFO_BASE_URL}switchStatus"/>
<c:set var="DICT_INFO_DELETE_URL" value="${DICT_INFO_BASE_URL}delete"/>
<%--操作员列表--%>
<c:set var="OPERATOR_BASE_URL" value="${MANAGER_URL}operator/"/>
<c:set var="OPERATOR_ADD_URL" value="${OPERATOR_BASE_URL}add"/>
<c:set var="OPERATOR_UPDATE_URL" value="${OPERATOR_BASE_URL}update"/>
<c:set var="OPERATOR_SWITCH_STATUS_URL" value="${OPERATOR_BASE_URL}switchStatus"/>
<c:set var="OPERATOR_RESET_PWD_URL" value="${OPERATOR_BASE_URL}resetPwd"/>
<c:set var="OPERATOR_DELETE_URL" value="${OPERATOR_BASE_URL}delete"/>
<c:set var="OPERATOR_TREE_ROLE_DATA_URL" value="${OPERATOR_BASE_URL}roles"/>
<c:set var="OPERATOR_TREE_ROLE_DATA_UPDATE_URL" value="${OPERATOR_BASE_URL}updateOperatorRoles"/>
<%--操作员账号列表--%>
<c:set var="OPERATOR_SUB_BASE_URL" value="${OPERATOR_BASE_URL}sub/"/>
<c:set var="OPERATOR_SUB_ADD_URL" value="${OPERATOR_SUB_BASE_URL}add"/>
<c:set var="OPERATOR_SUB_UPDATE_URL" value="${OPERATOR_SUB_BASE_URL}update"/>
<c:set var="OPERATOR_SUB_SWITCH_STATUS_URL" value="${OPERATOR_SUB_BASE_URL}switchStatus"/>
<c:set var="OPERATOR_SUB_DELETE_URL" value="${OPERATOR_SUB_BASE_URL}delete"/>
<%--格式管理--%>
<c:set var="FORMAT_BASE_URL" value="${MANAGER_URL}format/"/>
<c:set var="FORMAT_ADD_URL" value="${FORMAT_BASE_URL}add"/>
<c:set var="FORMAT_UPDATE_URL" value="${FORMAT_BASE_URL}update"/>
<c:set var="FORMAT_DELETE_URL" value="${FORMAT_BASE_URL}delete"/>
<%--格式详细管理--%>
<c:set var="FORMAT_DETAIL_BASE_URL" value="${FORMAT_BASE_URL}detail/"/>
<c:set var="FORMAT_DETAIL_TREE_URL" value="${FORMAT_DETAIL_BASE_URL}tree"/>
<c:set var="FORMAT_DETAIL_ADD_URL" value="${FORMAT_DETAIL_BASE_URL}add"/>
<c:set var="FORMAT_DETAIL_UPDATE_URL" value="${FORMAT_DETAIL_BASE_URL}update"/>
<c:set var="FORMAT_DETAIL_SWITCH_STATUS_URL" value="${FORMAT_DETAIL_BASE_URL}switchStatus"/>
<c:set var="FORMAT_DETAIL_DELETE_URL" value="${FORMAT_DETAIL_BASE_URL}delete"/>
<%--门禁卡--%>
<c:set var="ENTRANCE_GUARD_CARD_BASE_URL" value="${MANAGER_URL}entranceGuardCard/"/>
<c:set var="ENTRANCE_GUARD_CARD_ADD_URL" value="${ENTRANCE_GUARD_CARD_BASE_URL}add"/>
<c:set var="ENTRANCE_GUARD_CARD_DETAIL_URL" value="${ENTRANCE_GUARD_CARD_BASE_URL}detail"/>
<c:set var="ENTRANCE_GUARD_CARD_STATUS_URL" value="${ENTRANCE_GUARD_CARD_BASE_URL}status"/>
<c:set var="ENTRANCE_GUARD_CARD_CONTROL_URL" value="${ENTRANCE_GUARD_CARD_BASE_URL}control"/>
<c:set var="ENTRANCE_GUARD_CARD_UPDATE_URL" value="${ENTRANCE_GUARD_CARD_BASE_URL}update"/>
<c:set var="ENTRANCE_GUARD_CARD_DELETE_URL" value="${ENTRANCE_GUARD_CARD_BASE_URL}delete"/>
<c:set var="ENTRANCE_GUARD_CARD_TREE_DATA_URL" value="${ENTRANCE_GUARD_CARD_BASE_URL}tree"/>