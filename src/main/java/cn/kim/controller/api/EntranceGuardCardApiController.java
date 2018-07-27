package cn.kim.controller.api;

import cn.kim.common.attr.ConfigProperties;
import cn.kim.common.eu.CardType;
import cn.kim.common.eu.UseType;
import cn.kim.controller.manager.BaseController;
import cn.kim.exception.CustomException;
import cn.kim.service.EntranceGuardCardService;
import cn.kim.util.*;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * @return
     * @throws Exception
     */
    @RequestMapping("/GetStatus")
    @ResponseBody
    public String getStatus(HttpServletRequest request) throws Exception {
        try {
            Map<String, String> dataMap = null;
            String data = request.getParameter("DATAS");
            //参数转为Map
            if (isEmpty(data)) {
                //没有参数说明是未加密数据
                dataMap = urlParamsToMap(request.getParameterMap());
            } else {
                dataMap = decryptParamsToMap(data.replaceAll(" ", "+"));
            }

            String serial = dataMap.get("Serial");
            String id = dataMap.get("ID");
            String key = dataMap.get("Key");
            if (isEmpty(key)) {
                throw new CustomException("参数错误!");
            }
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
            String ip = dataMap.get("IP");

            status = hexConvert16(status);

            if (!isEmpty(t1) && !"00".equals(t1)) {
                t1 = toString(TextUtil.toDouble(t1) / 10);
            }
            if (!isEmpty(t2) && !"00".equals(t2)) {
                t2 = toString(TextUtil.toDouble(t2) / 10);
            }
            if (!isEmpty(h1) && !"00".equals(h1)) {
                h1 = toString(TextUtil.toDouble(h1) / 10);
            }
            if (!isEmpty(h2) && !"00".equals(h2)) {
                h2 = toString(TextUtil.toDouble(h2) / 10);
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
            if (isEmpty(ip)) {
                paramMap.put("BEGC_IP", HttpUtil.getIpAddr(request));
            } else {
                paramMap.put("BEGC_IP", ip);
            }
            //更新门禁卡信息和插入心跳日志
            entranceGuardCardService.updateEntranceGuardCard(paramMap);
            //解析NOW日期 判断日期是否有误 有误返回当前服务器日期


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Key", key);
            jsonObject.put("Now", DateUtil.getNowCardDate());
            //返回加密字符串
            return resultEncryptJson(jsonObject);
        } catch (Exception e) {
            return resultErrorJson(-1, e.getMessage());
        }
    }

    /**
     * 刷卡请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/SearchCardAcs")
    @ResponseBody
    public String searchCardAcs(HttpServletRequest request) throws Exception {
        try {
            Map<String, String> dataMap = null;
            String data = request.getParameter("DATAS");
            //参数转为Map
            if (isEmpty(data)) {
                //没有参数说明是未加密数据
                dataMap = urlParamsToMap(request.getParameterMap());
            } else {
                dataMap = decryptParamsToMap(data.replaceAll(" ", "+"));
            }

            //接收到的卡号、密码、二维码、身份证号码等
            String card = dataMap.get("Card");
            if (isEmpty(card)) {
                throw new CustomException("参数错误！");
            }
//            接收到的数据类型
//            0 = 卡；
//            1 = 串口232接口输入字符串,如二维码等；
//            2 = 密码；
//            3 = 按钮请求；
//            6 = 二代证数据；
//            9 = Base64编码数据，用于串口输入；
//            10 = 指纹数据；
//            11 = 指静脉数据；
//            12 = RFID卡；
//            13 = 人脸；
            int type = toInt(dataMap.get("type"));
            String serial = dataMap.get("Serial");
            String id = dataMap.get("ID");
            String reader = dataMap.get("Reader");
            String status = dataMap.get("Status");
            String index = dataMap.get("Index");
            String mac = dataMap.get("MAC");

            //base64解密
            if (CardType.ORCODE.getType() == type || CardType.CHINA.getType() == type || CardType.BASE64.getType() == type || CardType.VIEN_FINGER.getType() == type || CardType.FACE.getType() == type) {
                card = TextUtil.base64Decrypt(card);
            }
            status = hexConvert16(status);

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
            return resultErrorJson(-1, e.getMessage());
        }
    }


    /**
     * 返回错误信息
     *
     * @param code
     * @param message
     * @return
     */
    private String resultErrorJson(int code, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", code);
        jsonObject.put("message", message);
        return jsonObject.toJSONString();
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
        return urlParamsToMap(AESUtil.decrypt(data, ConfigProperties.CARD_AES_KEY, false));
    }

    /**
     * 返回结果的json
     *
     * @param jsonObject
     * @return
     * @throws Exception
     */
    private String resultEncryptJson(JSONObject jsonObject) throws Exception {
        return "DATAS={" + AESUtil.encrypt(jsonObject.toJSONString(), ConfigProperties.CARD_AES_KEY, true) + "}";
    }

    /**
     * 16进制转换
     *
     * @param status
     * @return
     */
    public String hexConvert16(String status) {
        int s = Integer.parseInt(status, 16);
        return toString((s & 0xfc) | ((~s) & 0x03));
    }
}
