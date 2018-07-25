package cn.kim.exception;

import cn.kim.common.attr.Tips;
import cn.kim.common.eu.UseType;
import cn.kim.common.eu.SystemEnum;
import cn.kim.controller.ManagerController;
import cn.kim.common.attr.Attribute;
import cn.kim.util.LogUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

/**
 * 自定义异常处理器
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {

    private static Logger logger = LogManager.getLogger(CustomExceptionResolver.class.getName());

    //前端控制器DispatcherServlet在进行HandlerMapping、调用HandlerAdapter执行Handler过程中，如果遇到异常就会执行此方法
    //handler最终要执行的Handler，它的真实身份是HandlerMethod
    //Exception ex就是接收到异常信息
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
        //自定义异常不需要记录
        boolean isSave = true;
        String url = request.getRequestURI();

        //输出异常
        ex.printStackTrace();

        //统一异常处理代码
        //针对系统自定义的CustomException异常，就可以直接从异常类中获取异常信息，将异常处理在错误页面展示
        //异常信息
        String message = null;
        CustomException customException = null;
        //如果ex是系统 自定义的异常，直接取出异常信息
        if (ex instanceof CustomException) {
            isSave = false;
            customException = (CustomException) ex;
        } else if (ex instanceof UnknownSessionException) {
            try {
                request.getRequestDispatcher("/login").forward(request, response);
            } catch (Exception e) {
            }
            return new ModelAndView();
        } else if (ex instanceof FileNotFoundException) {
            customException = new CustomException("没有找到文件!");
        } else {
            //针对非CustomException异常，对这类重新构造成一个CustomException
            customException = new CustomException(ex.getMessage() != null ? ex.getMessage() : ex.getCause() != null ? ex.getCause().getMessage() : ex.toString());
        }
        //错误 信息
        message = customException.getMessage();

        //记录日志
        if (isSave) {
            try {
                //拿到错误的栈
                StackTraceElement stackTraceElement = ex.getStackTrace()[0];

                String error = "文件：" + stackTraceElement.getFileName() + "，错误行数：" + stackTraceElement.getLineNumber() +
                        "，方法：" + stackTraceElement.getMethodName() + "，错误内容：" + message;

                LogUtil.recordLog(request, "系统发现异常", UseType.SYSTEM.getType(), SystemEnum.SYSTEM.toString(), error, Attribute.STATUS_ERROR);
                logger.error(error);
            } catch (Exception e) {
            }
        }

        request.setAttribute("message", message);
//        request.setAttribute("message", "发生错误,请重试!");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", message);
        //转向到错误 页面
        if (url.contains(ManagerController.MANAGER_URL)) {
            modelAndView.setViewName(Attribute.MANAGER_ERROR);
//                request.getRequestDispatcher(Attribute.MANAGER_ERROR).forward(request, response);
        } else {
            modelAndView.setViewName(Attribute.RECEPTION_ERROR);
//                request.getRequestDispatcher(Attribute.RECEPTION_ERROR).forward(request, response);
        }

        return modelAndView;
    }

}
