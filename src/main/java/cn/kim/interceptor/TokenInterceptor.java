package cn.kim.interceptor;

import cn.kim.common.annotation.Token;
import cn.kim.common.attr.Attribute;
import cn.kim.util.CommonUtil;
import cn.kim.util.TextUtil;
import cn.kim.common.annotation.Token;
import cn.kim.common.attr.Attribute;
import cn.kim.util.CommonUtil;
import cn.kim.util.TextUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.Text;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.util.UUID;

/**
 * Created by 余庚鑫 on 2018/3/1
 * 拦截表单提交UUID防止重复提交
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                    request.getSession(false).setAttribute(Attribute.SUBMIT_TOKEN_NAME, CommonUtil.idEncrypt(UUID.randomUUID().toString()));
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        return false;
                    }
                    request.getSession(false).removeAttribute(Attribute.SUBMIT_TOKEN_NAME);
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }


    private boolean isRepeatSubmit(HttpServletRequest request) throws InvalidKeyException {
        String serverToken = TextUtil.toString(request.getSession(false).getAttribute("token"));

        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter(Attribute.SUBMIT_TOKEN_NAME);
        if (clinetToken == null) {
            return true;
        }
        //不能解密的TOKEN就说明是模拟的
        CommonUtil.idDecrypt(clinetToken);

        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }
}
