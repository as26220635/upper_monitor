package cn.kim.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 余庚鑫 on 2017/4/26.
 */
public class CookieUtil {

    /**
     * 日
     */
    public static final int DAY = 24 * 60 * 60;
    /**
     * 月
     */
    public static final int MONTH = 30 * 24 * 60 * 60;
    /**
     * 年
     */
    public static final int YEAR = 12 * 30 * 24 * 60 * 60;

    /**
     * 拿到cookies参数
     *
     * @param cookies
     * @param name
     * @return
     */
    public static String get(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 拿到cookies参数
     *
     * @param request
     * @param name
     * @return
     */
    public static String get(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    /**
     * 添加的方法
     *
     * @param httpServletReqrest
     * @param httpServletResponse
     * @param name
     * @param value
     * @param expiry
     */
    public static void add(HttpServletRequest httpServletReqrest, HttpServletResponse httpServletResponse, String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        //30天
        cookie.setMaxAge(expiry);
        httpServletResponse.addCookie(cookie);
    }

    /**
     * 添加的方法
     *
     * @param httpServletReqrest
     * @param httpServletResponse
     * @param name
     * @param value
     * @param expiry
     */
    public static void add(ServletRequest request, ServletResponse response, String name, String value, int expiry) {
        HttpServletRequest httpServletReqrest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Cookie cookie = new Cookie(name, value);
        //30天
        cookie.setMaxAge(expiry);
        httpServletResponse.addCookie(cookie);
    }

    /**
     * 删除cookie
     * @param httpServletReqrest
     * @param httpServletResponse
     * @param names
     */
    public static void remove(HttpServletRequest httpServletReqrest, HttpServletResponse httpServletResponse, String... names) {
        Cookie[] cookies = httpServletReqrest.getCookies();
        for (Cookie cookie : cookies) {
            for (String name : names) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    httpServletResponse.addCookie(cookie);
                    break;
                }
            }
        }
    }

    /***
     * 删除cookie
     * @param request
     * @param response
     * @param names
     */
    public static void remove(ServletRequest request, ServletResponse response, String... names) {

        HttpServletRequest httpServletReqrest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpServletReqrest.getCookies();
        for (Cookie cookie : cookies) {
            for (String name : names) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    httpServletResponse.addCookie(cookie);
                    break;
                }
            }
        }
    }

}
