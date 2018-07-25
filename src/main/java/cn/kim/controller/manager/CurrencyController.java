package cn.kim.controller.manager;

import cn.kim.interceptor.PjaxInterceptor;
import cn.kim.common.attr.Attribute;
import cn.kim.controller.ManagerController;
import cn.kim.entity.ActiveUser;
import cn.kim.interceptor.PjaxInterceptor;
import cn.kim.util.AuthcUtil;
import cn.kim.util.ValidateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 余庚鑫 on 2017/8/14.
 */
@Controller
public class CurrencyController {

    /**
     * 404
     *
     * @return
     */
    @RequestMapping("/error/reception_404")
    public ModelAndView error404(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //不是PJAX的情况下显示完整的页面
        if (!ValidateUtil.isEmpty(request.getHeader(PjaxInterceptor.PJAX))) {
            modelAndView.addObject("PJAX", true);
        }
        modelAndView.setViewName(Attribute.RECEPTION_404);
        return modelAndView;
    }

    /**
     * 404
     *
     * @param request
     * @return
     */
    @RequestMapping("/error/404")
    public ModelAndView error404Admin(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //不是PJAX的情况下显示完整的页面
        if (!ValidateUtil.isEmpty(request.getHeader(PjaxInterceptor.PJAX))) {
            modelAndView.addObject("PJAX", true);
        }
        modelAndView.setViewName(Attribute.MANAGER_404);
        return modelAndView;
    }

    /**
     * 浏览器版本过低
     *
     * @return
     */
    @RequestMapping("/error/errorNonsupport")
    public String errorNonsupport() {
        return "error/nonsupport";
    }
}
