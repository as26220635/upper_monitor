package cn.kim.entity;

import cn.kim.common.attr.Constants;

import java.io.Serializable;

/**
 * Created by 余庚鑫 on 2018/7/30
 * 门禁心跳数据
 */
public class CardStatusProtocol implements Serializable {
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
    public int n1;
    public byte[] time;
    /**
     *设备时间
     */
    public String date;

    public int doorStatus;
    public int n2;
    public int dirPass;
    public int n3;
    public int controlType;
    public int relayOut;
    public byte[] output;
    public int o1;
    public int o2;
    public byte[] spare;
    public int alarmOut;

    public int ver;
    public int oemCode;
    public String serial;

    public byte[] input;

    public String id;
    public float t1;
    public float t2;
    public float h1;
    public float h2;
    /**
     * 版本，用于自动升级
     */
    public String version;
    /**
     * 备用功能 剩余通过人数
     */
    public int nextNum;
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

    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public byte[] getTime() {
        return time;
    }

    public void setTime(byte[] time) {
        this.time = time;
    }

    public int getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(int doorStatus) {
        this.doorStatus = doorStatus;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public int getDirPass() {
        return dirPass;
    }

    public void setDirPass(int dirPass) {
        this.dirPass = dirPass;
    }

    public int getN3() {
        return n3;
    }

    public void setN3(int n3) {
        this.n3 = n3;
    }

    public int getControlType() {
        return controlType;
    }

    public void setControlType(int controlType) {
        this.controlType = controlType;
    }

    public int getRelayOut() {
        return relayOut;
    }

    public void setRelayOut(int relayOut) {
        this.relayOut = relayOut;
    }

    public byte[] getOutput() {
        return output;
    }

    public void setOutput(byte[] output) {
        this.output = output;
    }

    public int getO1() {
        return o1;
    }

    public void setO1(int o1) {
        this.o1 = o1;
    }

    public int getO2() {
        return o2;
    }

    public void setO2(int o2) {
        this.o2 = o2;
    }

    public byte[] getSpare() {
        return spare;
    }

    public void setSpare(byte[] spare) {
        this.spare = spare;
    }

    public int getAlarmOut() {
        return alarmOut;
    }

    public void setAlarmOut(int alarmOut) {
        this.alarmOut = alarmOut;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public int getOemCode() {
        return oemCode;
    }

    public void setOemCode(int oemCode) {
        this.oemCode = oemCode;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public byte[] getInput() {
        return input;
    }

    public void setInput(byte[] input) {
        this.input = input;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getT1() {
        return t1;
    }

    public void setT1(float t1) {
        this.t1 = t1;
    }

    public float getT2() {
        return t2;
    }

    public void setT2(float t2) {
        this.t2 = t2;
    }

    public float getH1() {
        return h1;
    }

    public void setH1(float h1) {
        this.h1 = h1;
    }

    public float getH2() {
        return h2;
    }

    public void setH2(float h2) {
        this.h2 = h2;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getNextNum() {
        return nextNum;
    }

    public void setNextNum(int nextNum) {
        this.nextNum = nextNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
