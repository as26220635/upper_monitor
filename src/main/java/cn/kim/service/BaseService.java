package cn.kim.service;

import org.springframework.stereotype.Service;

/**
 * Created by 余庚鑫 on 2017/11/1.
 */
@Service
public abstract interface BaseService {

    public static String toLike(String str) {
        return '%' + str + '%';
    }
    
}
