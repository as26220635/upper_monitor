package cn.kim.interceptor;

import cn.kim.common.attr.Constants;
import cn.kim.util.AuthcUtil;
import cn.kim.util.SessionUtil;
import cn.kim.common.attr.Constants;
import cn.kim.util.AuthcUtil;
import cn.kim.util.SessionUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 余庚鑫 on 2017/7/15.
 * ajax请求判断session是否失效
 */
public class SessionExpireInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //未登录
        if (AuthcUtil.getCurrentUser() == null) {
            //如果是ajax请求响应头会有，x-requested-with
            if (request.getHeader("x-requested-with") != null && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
                //在响应头设置session状态
                response.setHeader("sessionstatus", "timeout");
                //session超时，ajax访问返回false
                return false;
            }
        } else {
            SessionUtil.set(Constants.SESSION_SERVLET_PATH, request.getServletPath());
        }
        return super.preHandle(request, response, handler);
    }

}