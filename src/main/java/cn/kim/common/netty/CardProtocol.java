package cn.kim.common.netty;

import cn.kim.common.attr.Constants;

/**
 * Created by 余庚鑫 on 2018/7/30
 */
public class CardProtocol {
    private int stx = Constants.TCP_HEAD_DATA;

    private int rand;
    private int command;
    private int address;
    private int door;
    private int lengthL;
    private int lengthH;
    /**
     * 消息的长度
     */
    private int contentLength;
    /**
     * 消息的内容
     */
    private byte[] content;
    private int cs;
    private int etx = Constants.TCP_END_DATA;

    /**
     * 用于初始化，CardProtocol
     *
     * @param contentLength 协议里面，消息数据的长度
     * @param content       协议里面，消息的数据
     */
    public CardProtocol(int contentLength, byte[] content) {
        this.contentLength = contentLength;
        this.content = content;
    }

    public int getStx() {
        return stx;
    }

    public void setStx(int stx) {
        this.stx = stx;
    }

    public int getRand() {
        return rand;
    }

    public void setRand(int rand) {
        this.rand = rand;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getDoor() {
        return door;
    }

    public void setDoor(int door) {
        this.door = door;
    }

    public int getLengthL() {
        return lengthL;
    }

    public void setLengthL(int lengthL) {
        this.lengthL = lengthL;
    }

    public int getLengthH() {
        return lengthH;
    }

    public void setLengthH(int lengthH) {
        this.lengthH = lengthH;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }

    public int getEtx() {
        return etx;
    }

    public void setEtx(int etx) {
        this.etx = etx;
    }
}
