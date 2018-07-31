package cn.kim.tools;

import cn.kim.common.BaseData;
import cn.kim.common.attr.MagicValue;
import cn.kim.common.netty.TCPServerNetty;
import cn.kim.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/7/27
 * 门禁卡操控
 */
@Component
public class EntranceGuardCardTool {

    @Autowired
    private TCPServerNetty tcpServerNetty;
    private static EntranceGuardCardTool entranceGuardCardTool;

    public void setTcpServerNetty(TCPServerNetty tcpServerNetty) {
        this.tcpServerNetty = tcpServerNetty;
    }

    @PostConstruct
    public void init() {
        entranceGuardCardTool = this;
        entranceGuardCardTool.tcpServerNetty = this.tcpServerNetty;
    }

    /**
     * 操控门禁
     *
     * @param ip
     * @param command
     * @param paramMap
     * @return
     */
    public static boolean control(String ip, int command, Map<String, Object> paramMap) {
        boolean isSuccess = false;
        //数据包
        byte[] data = null;
        //门
        int door = 0;
        //填充数据
        switch (command) {
            //开门
            case TCPServerNetty.OPEN_DOOR: {
                //门地址Door表示继电器，1-3分别表示3个继电器
                door = TextUtil.toInt(paramMap.get(MagicValue.DOOR));
            }
            break;
            //门常开
            case TCPServerNetty.OPEN_DOORS: {
                //门地址Door表示继电器，1-3分别表示3个继电器
                door = TextUtil.toInt(paramMap.get(MagicValue.DOOR));
            }
            break;
            //关门
            case TCPServerNetty.CLOSE_DOOR: {
                //门地址Door表示继电器，1-3分别表示3个继电器
                door = TextUtil.toInt(paramMap.get(MagicValue.DOOR));
            }
            break;
            //锁门
            case TCPServerNetty.LOCK_DOOR: {
                //门地址Door表示继电器，1-3分别表示3个继电器
                door = TextUtil.toInt(paramMap.get(MagicValue.DOOR));
                //非0表示锁，0表示解锁
                int action = TextUtil.toInt(paramMap.get(MagicValue.STATUS));
                data = new byte[]{(byte) action};
            }
            break;
            //火警
            case TCPServerNetty.FIRE_ALARM: {
                //大于0关闭火警，0输出火警；报警时间为控制器参数中的火警时间
                int action = TextUtil.toInt(paramMap.get(MagicValue.STATUS));
                //打开后是否一直保持火警输出
                int desc = TextUtil.toInt(paramMap.get(MagicValue.DESC));
                data = new byte[]{(byte) action, (byte) desc};
            }
            break;
            //报警
            case TCPServerNetty.POLICE_ALARM: {
                //非0关闭报警，0 输出报警；报警时间为控制器参数中的报警时间。
                int action = TextUtil.toInt(paramMap.get(MagicValue.STATUS));
                //打开后是否一直保持报警输出.
                int desc = TextUtil.toInt(paramMap.get(MagicValue.DESC));
                data = new byte[]{(byte) action, (byte) desc};
            }
            break;
            //时间同步
            case TCPServerNetty.TIME_ASYNC: {
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
            }
            break;
            default:
                break;
        }

        return entranceGuardCardTool.tcpServerNetty.send(ip, command, 1, door, data);
    }
}
