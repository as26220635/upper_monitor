package cn.kim.common.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * Created by 余庚鑫 on 2017/4/26.
 * SESSION 监听
 */
public class CustomSessionListener implements SessionListener {
    @Override
    public void onStart(Session session) {
//        System.out.println("onStart");
    }

    @Override
    public void onStop(Session session) {
//        System.out.println("onStop");
    }

    @Override
    public void onExpiration(Session session) {
//        System.out.println("onExpiration");
    }
}
