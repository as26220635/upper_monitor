package cn.kim.util;

import cn.kim.common.DaoSession;
import cn.kim.common.eu.NameSpace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 余庚鑫 on 2017/4/15.
 */
public class DateUtil {

    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT2 = "yyyy-MM-dd";
    public static final String FORMAT3 = "yyyyMMdd";
    public static final String FORMAT4 = "HH:mm:ss";

    /**
     * 获取数据库日期
     *
     * @return
     */
    public static String getSqlDate() {
        return DaoSession.daoSession.baseDao.selectOne(NameSpace.ManagerMapper, "selectNowDate");
    }

    public static String getDate() {
        return getDate(FORMAT);
    }

    public static String getDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static Date getDateTime(String format) {
        try {
            return new SimpleDateFormat(format).parse(getDate());
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getDateTime(String format, String time) {
        try {
            return new SimpleDateFormat(format).parse(time);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDate(String format, Date time) {
        return new SimpleDateFormat(format).format(time);
    }

    /**
     * 将时间转换为时间戳
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    public static String dateToStamp(String s, String format) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 将时间戳转换为时间
     *
     * @param s
     * @return
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToDate(String s, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 移动分钟
     *
     * @param isForward  是否前进
     * @param nowDate
     * @param moveMinute
     * @return
     * @throws ParseException
     */
    public static Date moveMinuteDate(boolean isForward, Date nowDate, int moveMinute) throws ParseException {
        return moveDate(Calendar.MINUTE, isForward, nowDate, moveMinute);
    }

    /**
     * 移动天数
     *
     * @param isForward 是否前进
     * @param nowDate
     * @param moveDay
     * @return
     * @throws ParseException
     */
    public static Date moveDayDate(boolean isForward, Date nowDate, int moveDay) throws ParseException {
        return moveDate(Calendar.DAY_OF_MONTH, isForward, nowDate, moveDay);
    }

    /**
     * 移动时间
     *
     * @param field     字段
     * @param isForward 前进还是后退
     * @param nowDate   时间
     * @param moveTime  移动多少时间
     * @return
     */
    public static Date moveDate(int field, boolean isForward, Date nowDate, int moveTime) {
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        Date date = null;
        date = nowDate;
        c.setTime(date);// 设置日历时间
        c.add(field, isForward ? moveTime : -moveTime);
        return c.getTime();
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param beforeDate
     * @param nowDate
     * @return
     */
    public static int differentDaysByMillisecond(Date beforeDate, Date nowDate) {
        int days = (int) ((nowDate.getTime() - beforeDate.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 拿到今天的时间
     *
     * @return
     * @throws ParseException
     */
    public static Date getTodayDate() throws ParseException {
        return getDateTime(FORMAT2);
    }

    /**
     * 拿到年
     *
     * @return
     */
    public static int getYear() {
        Calendar a = Calendar.getInstance();
        return a.get(Calendar.YEAR);
    }

    /**
     * 拿到月
     *
     * @return
     */
    public static int getMonth() {
        Calendar a = Calendar.getInstance();
        return a.get(Calendar.MONTH) + 1;
    }

    public static String getNowCardDate() {
        return getDate("yyyyMMddHHmmss") + "0" + getWeekOfDate(new Date());
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK);
        return w;
    }
}
