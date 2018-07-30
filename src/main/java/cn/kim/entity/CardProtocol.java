package cn.kim.entity;

import cn.kim.common.attr.Constants;

import java.io.Serializable;

/**
 * Created by 余庚鑫 on 2018/7/30
 */
public class CardProtocol implements Serializable {
    /**
     * 开始位
     */
    private int stx = Constants.TCP_HEAD_DATA;
    /**
     * 随机数
     */
    private int rand;
    /**
     * 指令
     */
    private int command;
    /**
     * 地址
     */
    private int address;
    /**
     * 门编号
     */
    private int door;
    /**
     * 数据长度低位
     */
    private int lengthL;
    /**
     * 数据长度高位
     */
    private int lengthH;
    /**
     * 消息的长度
     */
    private int dataLength;
    /**
     * 消息的内容
     */
    private byte[] data;
    /**
     * 校验
     */
    private int cs;
    /**
     * 结束
     */
    private int etx = Constants.TCP_END_DATA;

    /**
     * 用于初始化，CardProtocol
     *
     * @param dataLength 协议里面，消息数据的长度
     * @param data       协议里面，消息的数据
     */
    public CardProtocol(int dataLength, byte[] data) {
        this.dataLength = dataLength;
        this.data = data;
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

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
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
