package cn.kim.entity;

import java.io.Serializable;

/**
 * Created by 余庚鑫 on 2017/5/18.
 */
public class UserInfo implements Serializable {
    //登录名或者ip
    private String usercode;

    private String username;
    //类型
    private String type;
    //可以是最后访问时间也可以是登录时间
    private String lastTime;

    public UserInfo() {
    }

    public UserInfo(String usercode, String lastTime) {
        this.usercode = usercode;
        this.lastTime = lastTime;
    }

    public UserInfo(String usercode, String username, String type) {
        this.usercode = usercode;
        this.username = username;
        this.type = type;
    }

    public UserInfo(String usercode, String username, String type, String lastTime) {
        this.usercode = usercode;
        this.username = username;
        this.type = type;
        this.lastTime = lastTime;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
