package cn.kim.controller.api;

import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.ConfigProperties;
import cn.kim.common.eu.UseType;
import cn.kim.controller.manager.BaseController;
import cn.kim.exception.CustomException;
import cn.kim.service.EntranceGuardCardService;
import cn.kim.util.AESUtil;
import cn.kim.util.HttpUtil;
import cn.kim.util.LogUtil;
import cn.kim.util.TextUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    @ResponseBody
    public String getStatus(@RequestParam("DATAS") String data, HttpServletRequest request) throws Exception {
        try {
            if (isEmpty(data)) {
                throw new CustomException("参数为空!");
            }
            //参数转为Map
            Map<String, String> dataMap = decryptParamsToMap(data);

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

            if(isEmpty(key)){
                throw new CustomException("参数错误!");
            }

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
            //获取设备所在的公网IP
            paramMap.put("BEGC_IP", HttpUtil.getIpAddr(request));
            //更新门禁卡信息和插入心跳日志
            entranceGuardCardService.updateEntranceGuardCard(paramMap);
            //解析NOW日期 判断日期是否有误 有误返回当前服务器日期

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Key", key);
            //返回加密字符串
            return resultEncryptJson(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", "-1");
            jsonObject.put("message", e.getMessage());
            return jsonObject.toJSONString();
        }
    }

    /**
     * 刷卡请求
     *
     * @param data 加密后的参数主体
     * @return
     * @throws Exception
     */
    @RequestMapping("/SearchCard")
    @ResponseBody
    public String searchCard(@RequestParam("DATAS") String data, HttpServletRequest request) throws Exception {
        try {
            if (isEmpty(data)) {
                throw new CustomException("参数为空");
            }
            //参数转为Map
            Map<String, String> dataMap = decryptParamsToMap(data);

            //接收到的卡号、密码、二维码、身份证号码等
            String card = dataMap.get("Card");
//            接收到的数据类型
//            0=普通卡
//            1=串口232接口输入字符串,如二维码等
//            2=密码
//            6=二代证数据
//            7=二进制数据（BCD），用于串口输入
//            8=大数据包，用于串口输入
//            9=base64编码，用于串口输入
//            10=指纹数据
            String type = dataMap.get("type");
            String serial = dataMap.get("Serial");
            String id = dataMap.get("ID");
            String reader = dataMap.get("Reader");
            String status = dataMap.get("Status");
            String index = dataMap.get("Index");
            String mac = dataMap.get("MAC");

            //根据ID找到门禁卡
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            paramMap.put("BEGC_ID", id);
            Map<String, Object> entrance = entranceGuardCardService.selectEntranceGuardCard(paramMap);

            //处理结果，1打开，2报警，3关闭，0拒绝
            int acsRes = 0;
            //动作位置，继电器位置, 0-2，0:进，1:出，2:报警
            int actIndex = 1;
            //继电器动作时间,也就是继电器保持动作多少秒。
            int time = toInt(entrance.get("BEGC_TIME"));
            //验证二维码是否正确


            //AcsRes ActIndex Time Name Note Card Voice Systime Times
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("AcsRes", acsRes);
            jsonObject.put("ActIndex", actIndex);
            jsonObject.put("Time", time);

            //记录刷卡日志
            request.setAttribute("isSave", true);
            LogUtil.recordLog(request, "刷卡请求", UseType.SYSTEM.getType(), UseType.SYSTEM.toString(),
                    "刷卡请求,接收数据:" + TextUtil.toJSONString(dataMap) + ",返回参数:" + jsonObject.toJSONString(), acsRes == 1 ? STATUS_SUCCESS : STATUS_ERROR);
            //返回加密字符串
            return resultEncryptJson(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", "-1");
            jsonObject.put("message", e.getMessage());
            return jsonObject.toJSONString();
        }
    }

    /**
     * 解密后吧参数转为map
     *
     * @param data
     * @return
     * @throws Exception
     */
    private Map<String, String> decryptParamsToMap(String data) throws Exception {
        //解密
        //参数转为Map
        return urlParamsToMap(AESUtil.decrypt(data, ConfigProperties.CARD_AES_KEY));
    }

    /**
     * 返回结果的json
     *
     * @param jsonObject
     * @return
     * @throws Exception
     */
    private String resultEncryptJson(JSONObject jsonObject) throws Exception {
        return "DATAS={" + AESUtil.encrypt(jsonObject.toJSONString(), ConfigProperties.CARD_AES_KEY) + "}";
    }
}
