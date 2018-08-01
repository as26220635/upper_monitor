package cn.kim.common.quartz;

import cn.kim.common.BaseData;
import cn.kim.common.eu.NameSpace;
import cn.kim.dao.BaseDao;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by 余庚鑫 on 2018/8/1
 * TCP设备是否离线
 */
@Component
public class TCPClientIsOnlineScheduled extends BaseData {

    @Autowired
    private BaseDao baseDao;

    /**
     * 30秒一次 刷新是否在线
     */
    @Scheduled(cron = "*/30 * * * * ?")
    public void refreshOnline() throws Exception {
        baseDao.update(NameSpace.EntranceGuardCardMapper, "refreshEntranceGuardCardIsOnline", Maps.newHashMapWithExpectedSize(0));
    }
}
