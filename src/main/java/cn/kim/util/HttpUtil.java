package cn.kim.util;

import cn.kim.common.attr.ConfigProperties;
import cn.kim.common.attr.MagicValue;
import cn.kim.controller.ManagerController;
import cn.kim.controller.reception.home.MyHomeController;
import cn.kim.tools.HttpClient;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.shiro.cache.Cache;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2017/4/15.
 */
public class HttpUtil {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取访问者IP
     *
     * @return
     */
    public static String getIpAddr() {
        return getIpAddr(getRequest());
    }

    /**
     * 获取访问者IP
     * <p>
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * <p>
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!ValidateUtil.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!ValidateUtil.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    /**
     * 拿到完整URL
     *
     * @param request
     * @param isQuery 是否带URL参数
     * @return
     */
    public static String getUrl(HttpServletRequest request, boolean isQuery) {
        return request.getRequestURL() + (ValidateUtil.isEmpty(request.getQueryString()) ? "" : isQuery ? "?" + request.getQueryString() : "");
    }

    /**
     * 拿到服务器路径
     *
     * @return
     */
    public static String getContextPath() {
        return getRequest().getContextPath() + "/";
    }

    public static String getContextPath(HttpServletRequest request) {
        return request.getContextPath() + "/";
    }

    /**
     * 根据IP获取IP地址的信息
     *
     * @param ip
     * @return
     */
    public static Map<String, String> getIpAddressName(String ip) {
        Map<String, String> result = Maps.newHashMapWithExpectedSize(12);

        Map<String, String> params = Maps.newHashMapWithExpectedSize(1);
        params.put("ip", ip);
        HttpClient httpClient = new HttpClient();
        Map<String, Object> getMap = httpClient.get(ConfigProperties.TAOBAO_IP, params);
        JSONObject jsonObject = JSONObject.parseObject(TextUtil.toString(getMap.get(MagicValue.DESC)));

        if (ValidateUtil.isEmpty(jsonObject) || ValidateUtil.isEmpty(jsonObject.get("data"))) {
            result.put("code", "1");
            return result;
        }

        JSONObject data = jsonObject.getJSONObject("data");

        result.put("code", jsonObject.getString("code"));
        //0成功1失败
        if (MagicValue.ZERO.equals(jsonObject.getString("code"))) {
            result.put("country", TextUtil.toString(data.get("country")));
            result.put("area", TextUtil.toString(data.get("area")));
            result.put("region", TextUtil.toString(data.get("region")));
            result.put("city", TextUtil.toString(data.get("city")));
            result.put("isp", TextUtil.toString(data.get("isp")));
            result.put("county", TextUtil.toString(data.get("county")));
            result.put("region_id", TextUtil.toString(data.get("region_id")));
            result.put("country_id", TextUtil.toString(data.get("country_id")));
            result.put("area_id", TextUtil.toString(data.get("area_id")));
            result.put("county_id", TextUtil.toString(data.get("county_id")));
            result.put("isp_id", TextUtil.toString(data.get("isp_id")));
        }

        return result;
    }

    /**
     * 判断缓存中是否存在这个东西,没有就存入
     *
     * @param cacheName
     * @param key
     * @return
     */
    public static boolean isExistenceInCache(String cacheName, String key) {
        Cache cache = CacheUtil.getCache(cacheName);
        boolean isFlag = true;
        try {
            isFlag = cache.get(key) != null;
            if (!isFlag) {
                cache.put(key, key);
            }
        } catch (Exception e) {
        }
        return isFlag;
    }

    /**
     * 拿到ajax中的传来的参数
     *
     * @param req
     * @return
     */
    public static String getRequestPayload(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        ServletInputStream reader = null;
        try {
            reader = req.getInputStream();
            byte[] buff = new byte[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {

                sb.append(new String(buff), 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 转发
     */
    public static void forward(HttpServletRequest request, HttpServletResponse response, String url, Map<String, Object> params) {
        try {
            if (!ValidateUtil.isEmpty(params)) {
                for (String key : params.keySet()) {
                    request.setAttribute(key, params.get(key));
                }
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重定向
     *
     * @param response
     * @param url
     */
    public static void sendRedirect(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取后台管理的url
     *
     * @return
     */
    public static String getManagerHomeUrl() {
        return ManagerController.MANAGER_URL + "home";
    }

    /**
     * 获取前台管理的url
     *
     * @return
     */
    public static String getMyHomeUrl() {
        return MyHomeController.HOME_URL + "home";
    }
}
