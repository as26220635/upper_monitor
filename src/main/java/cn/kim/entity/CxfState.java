package cn.kim.entity;

import java.net.URL;

/**
 * Created by 余庚鑫 on 2017/10/31.
 */
public class CxfState {
    private boolean isOnline;
    private URL url;

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
