package cn.kim.util;

import cn.kim.service.AllocationService;
import cn.kim.service.AllocationService;
import cn.kim.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by 余庚鑫 on 2018/5/23
 * 参数util
 */
@Component
public class AllocationUtil {

    @Autowired
    private AllocationService allocationService;
    private static AllocationUtil allocationUtil;

    public void setAllocationService(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @PostConstruct
    public void init() {
        allocationUtil = this;
        allocationUtil.allocationService = this.allocationService;

    }

    /**
     * 获取参数
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        return allocationUtil.allocationService.selectAllocation(key);
    }

    /**
     * 设置参数
     *
     * @param key
     * @param val
     */
    public static void put(String key, Object val) {
        allocationUtil.allocationService.insertAndUpdateAllocation(key, val);
    }
}
