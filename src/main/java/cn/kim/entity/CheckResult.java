package cn.kim.entity;

import io.jsonwebtoken.Claims;

/**
 * Created by 余庚鑫 on 2017/10/31.
 */
public class CheckResult {
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * token
     */
    private Claims claims;
    /**
     * 错误码
     */
    private String errCode;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Claims getClaims() {
        return claims;
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
