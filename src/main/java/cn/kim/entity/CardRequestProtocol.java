package cn.kim.entity;

import cn.kim.common.attr.Constants;
import cn.kim.util.TCPUtil;

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
     * 继电器	1	输出的继电器编号，从0开始。0进门、1出门、2报警。
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

    /***
     * 是否开门	1	是否通过验证，通过则开锁。1开，0不开
     */
    private int isOpen = 0;
    /**
     * 开门时间	2	输出继电器动作的时间，秒为单位。
     */
    private int openTime = 5;
    /**
     * 读头	1	读头，0进门 1出门。
     */
    private int reader = 0;
    /**
     * 保持时间	1	显示保持多少秒后显示默认首页。为0则不切换。
     */
    private int delay = 0;
    /**
     * 卡号值类型	1	1表示下面的卡号为4字节整数，0则为字符串。建议为0
     */
    private int cardIsDWord = 0;
    /**
     * 卡号	18	用于显示卡号字符串，为0表示无。
     */
    private byte[] returnCard = TCPUtil.strToByte("", 18);
    /**
     * 声音	40	用于语音播报的声音字符串，为0表示无。
     */
    private byte[] voice = TCPUtil.strToByte("", 40);
    /**
     * 姓名	16	用于显示的姓名字符串，为0表示无。
     */
    private byte[] name = TCPUtil.strToByte("", 16);
    /**
     * 事件	32	用于显示的事件字符串，为0表示无。
     */
    private byte[] note = TCPUtil.strToByte("", 32);
    /**
     * 时间	20	用于显示的时间字符串，为0表示无。
     */
    private byte[] returnTime = TCPUtil.strToByte("", 20);

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

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public int getOpenTime() {
        return openTime;
    }

    public void setOpenTime(int openTime) {
        this.openTime = openTime;
    }

    public int getReader() {
        return reader;
    }

    public void setReader(int reader) {
        this.reader = reader;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getCardIsDWord() {
        return cardIsDWord;
    }

    public void setCardIsDWord(int cardIsDWord) {
        this.cardIsDWord = cardIsDWord;
    }

    public byte[] getReturnCard() {
        return returnCard;
    }

    public void setReturnCard(byte[] returnCard) {
        this.returnCard = returnCard;
    }

    public byte[] getVoice() {
        return voice;
    }

    public void setVoice(byte[] voice) {
        this.voice = voice;
    }

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    public byte[] getNote() {
        return note;
    }

    public void setNote(byte[] note) {
        this.note = note;
    }

    public byte[] getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(byte[] returnTime) {
        this.returnTime = returnTime;
    }
}
