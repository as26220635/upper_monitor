package cn.kim.entity;

import cn.kim.common.attr.Constants;

import java.io.Serializable;

/**
 * Created by 余庚鑫 on 2018/7/30
 * 门禁心跳数据
 */
public class CardRequestProtocol implements Serializable {
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
    public String serial;
    public String id;
    public int readHead;
    public byte[] n1;
    public int type;
    public byte[] time;
    /**
     * 系统时间
     */
    public String date;
    /**
     * 卡号
     */
    public String card;

    public String data;
    /**
     * 校验
     */
    private int cs;
    /**
     * 结束
     */
    private int etx = Constants.TCP_END_DATA;

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

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getN1() {
        return n1;
    }

    public void setN1(byte[] n1) {
        this.n1 = n1;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte[] getTime() {
        return time;
    }

    public void setTime(byte[] time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public int getReadHead() {
        return readHead;
    }

    public void setReadHead(int readHead) {
        this.readHead = readHead;
    }
}
