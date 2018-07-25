package cn.kim.entity;

import cn.kim.common.annotation.Validate;
import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.MagicValue;
import cn.kim.util.CommonUtil;
import cn.kim.util.HttpUtil;
import cn.kim.util.TextUtil;
import cn.kim.util.ValidateUtil;
import com.alibaba.fastjson.annotation.JSONField;
import org.w3c.dom.Attr;

import javax.xml.soap.Text;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 余庚鑫 on 2018/3/21.
 * ajax返回json状态
 */
public class ResultState {

    public static ResultState create() {
        return new ResultState();
    }

    public static ResultState to(int statuc, String message, String logMessage) {
        return new ResultState(statuc, message, logMessage);
    }

    public static ResultState to(int statuc, String message, String logMessage, String id) {
        return new ResultState(statuc, message, logMessage, id);
    }

    public static ResultState to(Map<String, Object> resultMap) {
        ResultState r = new ResultState();
        r.setCode(TextUtil.toInt(resultMap.get(MagicValue.STATUS)));
        r.setMessage(TextUtil.toString(resultMap.get(MagicValue.DESC)));
        r.setLogMessage(TextUtil.toString(resultMap.get(MagicValue.LOG)));
        r.setId(TextUtil.toString(resultMap.get(MagicValue.ID.toUpperCase())));
        r.setToken(TextUtil.toString(resultMap.get(Attribute.SUBMIT_TOKEN_NAME)));
        if (!ValidateUtil.isEmpty(r.getToken())) {
            HttpUtil.getRequest().getSession(false).setAttribute(Attribute.SUBMIT_TOKEN_NAME, r.getToken());
        }
        return r;
    }

    /**
     * 成功
     *
     * @param message
     * @return
     */
    public static ResultState success(String message) {
        return new ResultState(Attribute.STATUS_SUCCESS, message);
    }

    public static ResultState success(String message, String logMessage) {
        return new ResultState(Attribute.STATUS_SUCCESS, message, logMessage);
    }

    /**
     * 失败
     *
     * @param message
     * @return
     */
    public static ResultState error(String message) {
        return new ResultState(Attribute.STATUS_ERROR, message);
    }

    public static ResultState error(String message, String logMessage) {
        return new ResultState(Attribute.STATUS_ERROR, message, logMessage);
    }

    public ResultState() {
    }

    public ResultState(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultState(Integer code, String message, String logMessage) {
        this.code = code;
        this.message = message;
        this.logMessage = logMessage;
    }

    public ResultState(Integer code, String message, String logMessage, String id) {
        this.code = code;
        this.message = message;
        this.logMessage = logMessage;
        this.id = id;
    }

    /**
     * id
     */
    private String id;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 是否锁定
     */
    private String locked;
    /**
     * token
     */
    private String token;

    /**
     * 日志消息，该字段不输出到页面上
     */
    @JSONField(serialize = false)
    private String logMessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
