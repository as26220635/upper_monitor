package cn.kim.controller.manager;

import cn.kim.common.attr.Tips;
import cn.kim.common.eu.UseType;
import cn.kim.exception.NotFoundException;
import cn.kim.interceptor.PjaxInterceptor;
import cn.kim.common.attr.Attribute;
import cn.kim.common.eu.SystemEnum;
import cn.kim.controller.ManagerController;
import cn.kim.entity.ActiveUser;
import cn.kim.exception.CsrfException;
import cn.kim.util.AuthcUtil;
import cn.kim.util.LogUtil;
import cn.kim.util.SessionUtil;
import cn.kim.util.ValidateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.sql.SQLException;

/**
 * Created by 余庚鑫 on 2017/2/26.
 * 全局异常控制器拦截器
 */
@ControllerAdvice
public class ExceptionController extends BaseController {

    private static Logger logger = LogManager.getLogger(ExceptionController.class.getName());

    /**
     * 权限不足跳转页面
     *
     * @return
     */
    @ExceptionHandler(value = {UnauthorizedException.class})
    public ModelAndView unauthorizedException(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        ActiveUser activeUser = AuthcUtil.getCurrentUser();
        //不是PJAX的情况下显示完整的页面
        if (!ValidateUtil.isEmpty(request.getHeader(PjaxInterceptor.PJAX))) {
            response.setHeader(PjaxInterceptor.PJAX, "true");
            modelAndView.addObject("PJAX", true);
        }
        if (activeUser.getType().equals(SystemEnum.MANAGER.toString())) {
            modelAndView.setViewName(Attribute.MANAGER_REFUSE);
        } else {
            modelAndView.setViewName(Attribute.RECEPTION_REFUSE);
        }
        return modelAndView;
    }

    /**
     * csrf错误
     *
     * @param ex
     * @param request
     * @param model
     * @return
     */
    @ExceptionHandler(value = {CsrfException.class})
    public ModelAndView csrfException(CsrfException ex, HttpServletRequest request, RedirectAttributes model) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("redirect:" + Attribute.LOGIN_URL);

        model.addFlashAttribute("loginError", ex.getMessage());
        model.addFlashAttribute("username", request.getParameter("username"));

        return modelAndView;
    }

    /**
     * SQL运行错误
     *
     * @param ex
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @ExceptionHandler(value = {SQLException.class})
    public ModelAndView sqlException(SQLException ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ModelAndView modelAndView = new ModelAndView();

        if (!ValidateUtil.isEmpty(AuthcUtil.getCurrent()) && SessionUtil.sqlException()) {
            //记录SQL错误日志
            LogUtil.recordLog(request, "SQL运行错误", UseType.SYSTEM.getType(), SystemEnum.SYSTEM.toString(), "SQL运行错误超过上限错误,已被退出系统!", Attribute.STATUS_ERROR);

            logger.error("SQL运行错误:" + ex.getMessage(), Tips.LOG_ERROR);
            //SQL错误超出次数
            AuthcUtil.getCurrent().logout();
        } else {
            request.setAttribute("error", "数据库连接错误");

            if (request.getRequestURI().contains(ManagerController.MANAGER_URL)) {
                request.getRequestDispatcher(Attribute.MANAGER_ERROR).forward(request, response);
            } else {
                request.getRequestDispatcher(Attribute.RECEPTION_ERROR).forward(request, response);
            }
            //记录SQL错误日志
            LogUtil.recordLog(request, "SQL运行错误", UseType.SYSTEM.getType(), SystemEnum.SYSTEM.toString(), "SQL运行错误:" + ex.getMessage(), Attribute.STATUS_ERROR);

            logger.error("SQL运行错误:" + ex.getMessage(), Tips.LOG_ERROR);
        }

        return modelAndView;
    }

    /**
     * 参数错误跳转页面
     *
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ModelAndView methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request, HttpServletResponse response) {
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
        logger.error("参数传入错误:" + ex.getMessage(), Tips.LOG_ERROR);
        return modelAndView;
    }

    /**
     * 没有找到页面跳转
     *
     * @return
     */
    @ExceptionHandler(value = {NotFoundException.class})
    public ModelAndView notFoundException(NotFoundException ex, HttpServletRequest request, HttpServletResponse response) {
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
        logger.error("没有找到页面:" + ex.getMessage(), Tips.LOG_ERROR);
        return modelAndView;
    }

    @ExceptionHandler(value = {InvalidKeyException.class})
    public ModelAndView invalidKeyException(InvalidKeyException ex, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        ActiveUser activeUser = AuthcUtil.getCurrentUser();
        //不是PJAX的情况下显示完整的页面
        if (!ValidateUtil.isEmpty(request.getHeader(PjaxInterceptor.PJAX))) {
            response.setHeader(PjaxInterceptor.PJAX, "true");
            modelAndView.addObject("PJAX", true);
        }
        if (request.getRequestURI().contains(ManagerController.MANAGER_URL)) {
            modelAndView.setViewName(Attribute.MANAGER_ERROR);
        } else {
            modelAndView.setViewName(Attribute.RECEPTION_ERROR);
        }
        modelAndView.addObject("message", "无效的key");
        logger.error("解密错误:无效的key", Tips.LOG_ERROR);
        return modelAndView;
    }
}
