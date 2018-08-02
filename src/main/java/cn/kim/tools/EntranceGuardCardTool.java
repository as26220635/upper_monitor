package cn.kim.tools;

import cn.kim.common.BaseData;
import cn.kim.common.attr.MagicValue;
import cn.kim.common.eu.TCPCommand;
import cn.kim.common.netty.TCPServerNetty;
import cn.kim.util.TCPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/7/27
 * 门禁卡操控
 */
@Component
public class EntranceGuardCardTool extends BaseData {

    @Autowired
    private TCPServerNetty tcpServerNetty;

    /**
     * 操控门禁
     *
     * @param ip       客户端IP
     * @param command  指令
     * @param paramMap 参数
     * @return
     */
    public boolean control(String ip, int command, Map<String, Object> paramMap) {
        boolean isSuccess = false;
        //数据包
        byte[] data = null;
        //门
        int door = 0;
        //表示发送的485口
        int address = 0;
        //填充数据
        if (command == TCPCommand.OPEN_DOOR.getType()) {
            //开门
            //门地址Door表示继电器，1-3分别表示3个继电器
            door = toInt(paramMap.get(MagicValue.DOOR));
        } else if (command == TCPCommand.OPEN_DOORS.getType()) {
            //门常开
            //门地址Door表示继电器，1-3分别表示3个继电器
            door = toInt(paramMap.get(MagicValue.DOOR));
        } else if (command == TCPCommand.CLOSE_DOOR.getType()) {
            //关门
            //门地址Door表示继电器，1-3分别表示3个继电器
            door = toInt(paramMap.get(MagicValue.DOOR));
        } else if (command == TCPCommand.LOCK_DOOR.getType()) {
            //锁门
            //门地址Door表示继电器，1-3分别表示3个继电器
            door = toInt(paramMap.get(MagicValue.DOOR));
            //非0表示锁，0表示解锁
            int action = toInt(paramMap.get(MagicValue.STATUS));
            data = new byte[]{(byte) action};
        } else if (command == TCPCommand.FIRE_ALARM.getType()) {
            //火警
            //大于0关闭火警，0输出火警；报警时间为控制器参数中的火警时间
            int action = toInt(paramMap.get(MagicValue.STATUS));
            //打开后是否一直保持火警输出
            int desc = toInt(paramMap.get(MagicValue.DESC));
            data = new byte[]{(byte) action, (byte) desc};
        } else if (command == TCPCommand.MAST_ALARM.getType()) {
            //报警
            //非0关闭报警，0 输出报警；报警时间为控制器参数中的报警时间。
            int action = toInt(paramMap.get(MagicValue.STATUS));
            //打开后是否一直保持报警输出.
            int desc = toInt(paramMap.get(MagicValue.DESC));
            data = new byte[]{(byte) action, (byte) desc};
        } else if (command == TCPCommand.TIME_ASYNC.getType()) {
            //时间同步
            Calendar cal = Calendar.getInstance();
            //获取年份
            int year = cal.get(Calendar.YEAR);
            //获取月份
            int month = cal.get(Calendar.MONTH) + 1;
            //获取日
            int day = cal.get(Calendar.DATE);
            //小时
            int hour = cal.get(Calendar.HOUR);
            //分
            int minute = cal.get(Calendar.MINUTE);
            //秒
            int second = cal.get(Calendar.SECOND);
            //一周的第几天
            int weekOfYear = cal.get(Calendar.DAY_OF_WEEK);
            //顺序：秒分时周日月年，秒在前，10进制; 7  表示星期六；年10 表示2010年，24小时制
            data = new byte[]{(byte) second, (byte) minute, (byte) hour, (byte) weekOfYear, (byte) day, (byte) month, (byte) (year - 2000)};
        } else if (command == TCPCommand.SET_PARAM.getType()) {
            //设置参数
            //开门时间	2	开门时间，低位在前
            int openTime = toInt(paramMap.get("openTime"));
            //开门超时	1	门关闭后开门超时检测的时间范围
            int openOutTime = toInt(paramMap.get("openOutTime"));
            //开门太长报警	1	门开超出时间后，产生报警记录。
            int tooLongAlarm = toInt(paramMap.get("tooLongAlarm"));
            //报警类型	1	报警类型，那些报警产生报警输出；可以多选
            //0x01门报警
            //0x02 开门时间太长报警
            int alarmMast = toInt(paramMap.get("alarmMast"));
            //触发报警时间	2	报警项目触发时输出的报警时间，低位在前
            int alarmTime = toInt(paramMap.get("alarmTime"));
            //火警时间	2	火警输入触发输出的持续时间，低位在前
            int fireTime = toInt(paramMap.get("fireTime"));
            //输入报警时间	2	报警输入触发报警输出，输出的持续时间
            int alarmInTime = toInt(paramMap.get("alarmInTime"));
            //任意输入开门	1	1任意输入则开门
            int everyCard = toInt(paramMap.get("everyCard"));
            //防尾随	1	1关闭门磁立即关门
            int closeAPass = toInt(paramMap.get("closeAPass"));
            //应急密码	8	字符串，0结尾，输入密码为该字符串则开门。
            String duressPIN = toString(paramMap.get("duressPIN"));
            //应急身份证	18	字符串，0结尾，身份证号码为该字符串则开门
            String chinaCard = toString(paramMap.get("chinaCard"));
            //——	10	移除本功能保留位置。
            //应急字符串	20	字符串，0结尾，输入字符串的前面包括该字符串则立即开门，如二维码扫描
            String emergencyStr = toString(paramMap.get("emergencyStr"));
            //应急卡	40	5个紧急卡，每个4字节整数。
            int[] emergencyCard = (int[]) paramMap.get("emergencyCard");

            data = new byte[109];

            int byteIndex = 0;
            data[byteIndex++] = (byte) openTime;
            data[byteIndex++] = (byte) (openTime >> 8);
            data[byteIndex++] = (byte) openOutTime;
            data[byteIndex++] = (byte) tooLongAlarm;
            data[byteIndex++] = (byte) alarmMast;
            data[byteIndex++] = (byte) alarmTime;
            data[byteIndex++] = (byte) (alarmTime >> 8);
            data[byteIndex++] = (byte) fireTime;
            data[byteIndex++] = (byte) (fireTime >> 8);
            data[byteIndex++] = (byte) alarmInTime;
            data[byteIndex++] = (byte) (alarmInTime >> 8);
            data[byteIndex++] = (byte) everyCard;
            data[byteIndex++] = (byte) closeAPass;

            byte[] duressPINBytes = TCPUtil.strToByte(duressPIN, 8);
            System.arraycopy(duressPINBytes, 0, data, byteIndex, 8);
            byteIndex += 8;

            byte[] chinaCardBytes = TCPUtil.strToByte(chinaCard, 18);
            System.arraycopy(chinaCardBytes, 0, data, byteIndex, 18);
            byteIndex += 18;

            byte[] removeBytes = TCPUtil.strToByte("", 10);
            System.arraycopy(removeBytes, 0, data, byteIndex, 10);
            byteIndex += 10;

            byte[] emergencyStrBytes = TCPUtil.strToByte(emergencyStr, 20);
            System.arraycopy(emergencyStrBytes, 0, data, byteIndex, 20);
            byteIndex += 20;

            byte[] emergencyCardBytes = TCPUtil.toByte(emergencyCard, 5);
            System.arraycopy(emergencyCardBytes, 0, data, byteIndex, 40);
        }

        return tcpServerNetty.send(ip, command, address, door, data);
    }
}
