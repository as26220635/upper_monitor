package cn.kim.entity;

import cn.kim.listener.LockListener;

import java.io.Serializable;

/**
 * Created by 余庚鑫 on 2018/7/31
 */
public class TCPSendMessage implements Serializable {
    private String clientIp;
    private byte[] data;
    private boolean isSuccess;

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
