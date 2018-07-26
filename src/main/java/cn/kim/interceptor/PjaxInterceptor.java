package cn.kim.interceptor;

import cn.kim.common.attr.Constants;
import cn.kim.controller.ManagerController;
import cn.kim.controller.reception.LayoutController;
import cn.kim.controller.reception.home.MyHomeController;
import cn.kim.entity.ActiveUser;
import cn.kim.service.ManagerService;
import cn.kim.util.AuthcUtil;
import cn.kim.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2017/4/15.
 */
public class PjaxInterceptor implements HandlerInterceptor {

    public static final String PJAX = "X-PJAX";
    private static final String DESC = "desc";
    private static final String HTML = "html";

    @Autowired
    private ManagerService managerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accept = request.getHeader("Accept");
        if (accept == null || accept.contains("json")) {
            return true;
        }

        String path = request.getServletPath();
        //不是主界面url，就返回
        if (path.contains(LayoutController.LAYOUT_PATH) || path.contains(MyHomeController.LAYOUT_PATH) || path.contains(ManagerController.LAYOUT_PATH) || path.contains("error/")) {
            return true;
        }

        String header = request.getHeader(PJAX);
        String html = request.getHeader(HTML);
        String desc = request.getParameter(DESC);
        //判断是不是PJAX请求
        if (header == null && desc == null && html == null) {

            Enumeration<String> names = request.getParameterNames();
            StringBuilder sb = new StringBuilder();
            while (names.hasMoreElements()) {
                String s = names.nextElement();
                String param = request.getParameter(s);
                if (param == null || param.length() < 1) {
                    continue;
                }
                sb.append(s).append("=").append(param).append("&");
            }
            String params = sb.length() > 1 ? sb.substring(0, sb.length() - 1) : "";
            request.setAttribute("params", params);
            request.setAttribute("path", path);
            //request.getContextPath()
            String url = "";
            try {
                //判断是哪里的pjax请求
                if (path.contains(ManagerController.MANAGER_URL)) {
                    //设置管理员菜单按钮
//                    ActiveUser activeUser = AuthcUtil.getCurrentUser();
//                    if (!ValidateUtil.isEmpty(activeUser)) {
//                        List<Map<String, Object>> menus = managerService.queryOperatorMenuTree(activeUser.getId());
//                        activeUser.setMenus(menus);
//                        AuthcUtil.setCurrentUser(activeUser);
//                    }
                    //后台管理
                    url += ManagerController.LAYOUT_PATH;
                    request.getRequestDispatcher(url).forward(request, response);
                } else if (path.contains(MyHomeController.HOME_URL)) {
                    //前台管理
                    url += MyHomeController.LAYOUT_PATH;
                    request.getRequestDispatcher(url).forward(request, response);
                } else {
                    //前台
                    url += LayoutController.LAYOUT_PATH;
                    request.getRequestDispatcher(url).forward(request, response);
                }
            } catch (Exception e) {
                throw e;
            }
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}