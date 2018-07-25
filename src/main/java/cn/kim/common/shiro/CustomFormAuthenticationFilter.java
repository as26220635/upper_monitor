package cn.kim.common.shiro;

import cn.kim.exception.IncorrectCaptchaException;
import cn.kim.common.eu.SystemEnum;
import cn.kim.exception.IncorrectCaptchaException;
import cn.kim.util.AuthcUtil;
import cn.kim.util.HttpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by 余庚鑫 on 2017/2/21.
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger LOG = LogManager.getLogger(CustomFormAuthenticationFilter.class.getName());

    public CustomFormAuthenticationFilter() {
    }

    @Override
    /**
     * 登录验证
     */
    protected boolean executeLogin(ServletRequest request,
                                   ServletResponse response) throws Exception {
        CaptchaUsernamePasswordToken token = createToken(request, response);
        try {
            //判断是否同时登陆
//            doLogin(token.getUsername());
            /*图形验证码验证*/
            doCaptchaValidate((HttpServletRequest) request, token);
            Subject subject = getSubject(request, response);
            subject.login(token);//正常验证
            LOG.info(token.getUsername() + "登录成功");
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            LOG.info(token.getUsername() + "登录失败--" + e);
            return onLoginFailure(token, e, request, response);
        }
    }

    // 验证码校验
    protected void doCaptchaValidate(HttpServletRequest request,
                                     CaptchaUsernamePasswordToken token) {
        //session中的图形码字符串
        String captcha = (String) request.getSession().getAttribute("validateCode");
        if (captcha == null || !captcha.equalsIgnoreCase(token.getCaptcha())) {
            throw new IncorrectCaptchaException("验证码错误！");
        }
    }


    @Override
    protected CaptchaUsernamePasswordToken createToken(ServletRequest request,
                                                       ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);

        return new CaptchaUsernamePasswordToken(username,
                password.toCharArray(), rememberMe, host, captcha, HttpUtil.getIpAddr((HttpServletRequest) request));
    }

    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }


    //成功后转跳的地址
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        //管理员跳转至主页 其他都默认返回登录前页面
        if (AuthcUtil.getCurrentUser().getType().equals(SystemEnum.MANAGER.toString())) {
            WebUtils.issueRedirect(request, response, HttpUtil.getManagerHomeUrl(), null, true);
        } else {
            super.issueSuccessRedirect(request, response);
        }
    }

    //保存异常对象到request
    @Override
    protected void setFailureAttribute(ServletRequest request,
                                       AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }

}
