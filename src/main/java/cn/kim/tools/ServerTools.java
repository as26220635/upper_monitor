package cn.kim.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by 余庚鑫 on 2018/7/31
 */
public class ServerTools {
    private static final int MIN_TIME = 5000;
    /**
     * 数据存取队列Map
     */
    private static final Map<String, LinkedBlockingDeque<byte[]>> queryDatas = new HashMap<>();

    /**
     * 新增一个查询数据队列
     * 设置超时时间小于2000 则默认为2000
     *
     * @param queryId
     * @param time    毫秒
     */
    public static LinkedBlockingDeque<byte[]> addQueryDeque(String queryId, int time) {
        LinkedBlockingDeque<byte[]> deque = new LinkedBlockingDeque<>();
        //新增查询队列
        queryDatas.put(queryId, deque);
        //启动超时定时器
        if (time < MIN_TIME) {
            time = MIN_TIME;
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // 超时处理， 指定时间后 模拟添加一条数据
//                appendQueryDequeData(queryId, new JSONObject());
            }
        }, time);
        return deque;
    }
}
