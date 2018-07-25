package cn.kim.controller;

import cn.kim.controller.manager.BaseController;
import cn.kim.controller.reception.home.MyHomeController;
import cn.kim.common.attr.Constants;
import cn.kim.common.csrf.CsrfToken;
import cn.kim.controller.manager.BaseController;
import cn.kim.controller.reception.home.MyHomeController;
import cn.kim.exception.*;
import cn.kim.util.AuthcUtil;
import cn.kim.util.CreateImageCode;
import cn.kim.util.SessionUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 余庚鑫 on 2017/2/25.
 * 统一登录
 */
@Controller
public class LoginController extends BaseController {

    /**
     * 验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("check")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 通知浏览器不要缓存
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        CreateImageCode vCode = new CreateImageCode(100, 30, 5, 0);
        request.getSession().setAttribute("validateCode", vCode.getCode());
        vCode.write(response.getOutputStream());
    }

    /**********************     防止csrf攻击    ********************/

    @GetMapping("/login")
    @CsrfToken(create = true)
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("login");

        String username = request.getParameter("username");
        if (username != null) {
            modelAndView.addObject("username", username);
        }

        if (AuthcUtil.getCurrent().isAuthenticated()) {
            if (request.getRequestURI().contains(MyHomeController.HOME_URL)) {
                modelAndView.setViewName("redirect:" + MyHomeController.HOME_URL);
            } else if (request.getRequestURI().contains(ManagerController.MANAGER_URL)) {
                modelAndView.setViewName("redirect:" + ManagerController.MANAGER_URL);
            } else {
                modelAndView.setViewName("redirect:/index");
            }
        }

        return modelAndView;
    }

    @PostMapping(value = "/login")
    @CsrfToken(remove = true)
    public ModelAndView login(HttpServletRequest request, RedirectAttributes model) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        //如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
        if (request.getAttribute("shiroLoginFailure") != null) {
            Object exceptionClass = request.getAttribute("shiroLoginFailure");
            String errorTips = "";
            //根据shiro返回的异常类判断，抛出指定异常信息
            if (exceptionClass != null) {
                if (exceptionClass instanceof UnknownAccountException) {
                    //最终会抛给异常处理器
                    errorTips = "账号不存在";
                } else if (exceptionClass instanceof IncorrectCredentialsException) {
                    errorTips = "用户名/密码错误";
                } else if (exceptionClass instanceof IncorrectCaptchaException) {
                    errorTips = "验证码错误";
                } else if (exceptionClass instanceof FrozenException) {
                    errorTips = "用户被冻结,请联系管理员";
                } else if (exceptionClass instanceof RoleFrozenException) {
                    errorTips = "用户角色被冻结,请联系管理员";
                } else if (exceptionClass instanceof NullRoleFrozenException) {
                    errorTips = "用户角色查询异常";
                } else if (exceptionClass instanceof RepeatLoginException) {
                    errorTips = "用户已经登陆,不能同时登陆";
                } else if (exceptionClass instanceof ExcessiveAttemptsException) {
                    errorTips = "密码错误次数过多,请等待10分钟后尝试!";
                } else if(exceptionClass instanceof AuthenticationException){
                    ((AuthenticationException) exceptionClass).printStackTrace();
                    errorTips = "服务器内部错误!";
                } else if (exceptionClass instanceof UnknownTypeException) {
                    errorTips = "未知类型异常";
                } else {
                    throw (Exception) exceptionClass;//最终在异常处理器生成未知错误
                }

                SessionUtil.remove(Constants.SESSION_USERNAME);
                modelAndView.setViewName("redirect:/login");
                model.addFlashAttribute("loginError", errorTips);
                model.addFlashAttribute("username", request.getParameter("username"));
                model.addFlashAttribute("type", request.getParameter("type"));
            }
        }

        return modelAndView;
    }

}
