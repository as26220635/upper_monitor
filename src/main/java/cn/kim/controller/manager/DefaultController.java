package cn.kim.controller.manager;

import cn.kim.interceptor.PjaxInterceptor;
import cn.kim.common.attr.Attribute;
import cn.kim.controller.ManagerController;
import cn.kim.interceptor.PjaxInterceptor;
import cn.kim.util.ValidateUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 余庚鑫 on 2017/2/27.
 * 404 错误拦截跳转
 */

public class DefaultController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        //不是PJAX的情况下显示完整的页面
        if (!ValidateUtil.isEmpty(request.getHeader(PjaxInterceptor.PJAX))) {
            response.setHeader(PjaxInterceptor.PJAX, "true");
            modelAndView.addObject("PJAX", true);
        }
        if (request.getRequestURI().contains(ManagerController.MANAGER_URL)) {
            modelAndView.setViewName(Attribute.MANAGER_404);
        } else {
            modelAndView.setViewName(Attribute.RECEPTION_404);
        }
        return modelAndView;
    }

}