package cn.kim.controller.api;

import cn.kim.common.attr.ConfigProperties;
import cn.kim.controller.manager.BaseController;
import cn.kim.service.EntranceGuardCardService;
import cn.kim.util.AESUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/7/26
 * 门禁卡API
 */
@Controller
@RequestMapping("/api")
public class EntranceGuardCardApiController extends BaseController {

    @Autowired
    private EntranceGuardCardService entranceGuardCardService;

    /**
     * 门禁心跳请求
     *
     * @param data 加密后的参数主体
     * @return
     * @throws Exception
     */
    @RequestMapping("/GetStatus")
    public String getStatus(@RequestParam("DATAS") String data) throws Exception {
        JSONObject jsonObject = new JSONObject();
        try {
            //解密
            data = AESUtil.decrypt(data, ConfigProperties.CARD_AES_KEY);
            //参数转为Map
            Map<String, String> dataMap = urlParamsToMap(data);

            String serial = dataMap.get("Serial");
            String id = dataMap.get("ID");
            String key = dataMap.get("Key");
            String status = dataMap.get("Status");
            String input = dataMap.get("Input");
            String now = dataMap.get("Now");
            String t1 = dataMap.get("T1");
            String h1 = dataMap.get("H1");
            String t2 = dataMap.get("T2");
            String h2 = dataMap.get("H2");
            String index = dataMap.get("Index");
            String ver = dataMap.get("Ver");
            String nextNum = dataMap.get("NextNum");
            String mac = dataMap.get("MAC");

            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(16);
            paramMap.put("BEGC_SERIAL", serial);
            paramMap.put("BEGC_ID", id);
            paramMap.put("BEGC_KEY", key);
            paramMap.put("BEGC_STATUS", status);
            paramMap.put("BEGC_INPUT", input);
            paramMap.put("BEGC_NOW", now);
            paramMap.put("BEGC_T1", t1);
            paramMap.put("BEGC_H1", h1);
            paramMap.put("BEGC_T2", t2);
            paramMap.put("BEGC_H2", h2);
            paramMap.put("BEGC_INDEX", index);
            paramMap.put("BEGC_VER", ver);
            paramMap.put("BEGC_NEXT_NUM", nextNum);
            paramMap.put("BEGC_MAC", mac);
            //更新门禁卡信息和插入心跳日志
            entranceGuardCardService.updateEntranceGuardCard(paramMap);
            //解析NOW日期 判断日期是否有误 有误返回当前服务器日期

            jsonObject.put("Key", key);
            //返回加密字符串
            return "DATAS={" + AESUtil.encrypt(jsonObject.toJSONString(), ConfigProperties.CARD_AES_KEY) + "}";
        } catch (Exception e) {
            jsonObject.put("ERROR", "解密出错!");
            return jsonObject.toJSONString();
        }
    }
}
