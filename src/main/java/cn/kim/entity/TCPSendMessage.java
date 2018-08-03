package cn.kim.entity;

import cn.kim.listener.LockListener;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 余庚鑫 on 2018/7/31
 * TCP发送消息集合
 */
public class TCPSendMessage implements Serializable {
    /**
     * 客户端IP
     */
    private String clientIp;
    /**
     * 传输的数据
     */
    private byte[] data;
    /**
     * 是否成功
     */
    private boolean isSuccess;
    /**
     * 同步锁
     */
    private CountDownLatch countDownLatch;

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

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
}
