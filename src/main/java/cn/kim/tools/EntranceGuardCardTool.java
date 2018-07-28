package cn.kim.tools;

import cn.kim.util.TextUtil;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/7/27
 * 门禁卡操控
 */
public class EntranceGuardCardTool {

    /**
     * 操控门禁
     *
     * @param ip       IP地址
     * @param action   动作
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static Map<String, Object> control(String ip, String action, String username, String password) {

        Map<String, String> headerMap = Maps.newHashMapWithExpectedSize(1);
        //添加认证
        headerMap.put("Authorization", "Basic " + TextUtil.base64Encrypt(username + ":" + password));
        HttpClient httpClient = new HttpClient();
        Map<String, Object> resultMap = httpClient.get("http://" + ip + "/cdor.cgi?open=" + action, headerMap, null);
        
        return resultMap;
    }
}
