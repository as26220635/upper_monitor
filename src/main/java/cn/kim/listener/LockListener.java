package cn.kim.listener;

import cn.kim.entity.ResultState;

import java.security.InvalidKeyException;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/5/21.
 * 锁回调
 */
public interface LockListener {
    ResultState lock() throws Exception;
}
