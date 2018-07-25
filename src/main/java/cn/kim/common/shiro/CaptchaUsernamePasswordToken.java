package cn.kim.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by 余庚鑫 on 2017/2/26.
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
    //验证码字符串
    private String captcha;
    private String type;
    private String ip;
    private boolean isRememberMe;
    private boolean isAuto;

    public CaptchaUsernamePasswordToken(String username, char[] password,
                                        boolean rememberMe, String host, String captcha,String ip) {
        super(username, password, rememberMe, host);
        this.setRememberMe(rememberMe);
        this.captcha = captcha;
        this.type = type;
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean isRememberMe() {
        return isRememberMe;
    }

    @Override
    public void setRememberMe(boolean rememberMe) {
        isRememberMe = rememberMe;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void setAuto(boolean auto) {
        isAuto = auto;
    }
}
