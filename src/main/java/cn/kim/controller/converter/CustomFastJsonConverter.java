package cn.kim.controller.converter;

import cn.kim.util.CommonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2017/6/3.
 * 自定义json返回的解析，防止XSS攻击
 */
public class CustomFastJsonConverter extends FastJsonHttpMessageConverter {
    /**
     * 日志记录器
     **/
    private static final Logger LOGGER = LogManager.getLogger(CustomFastJsonConverter.class);


    /**
     * 重写writeInternal方法，在返回内容前首先进行HTML字符转义
     * <功能详细描述>
     *
     * @param object
     * @param outputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     * @see [类、类#方法、类#成员]
     */
    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        // 获取输出流
        OutputStream out = outputMessage.getBody();
        //ID加密
        try {
            object = CommonUtil.idEncrypt(object);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        // 获取要输出的文本
        String text = JSON.toJSONString(object, SerializerFeature.WriteNullStringAsEmpty);

        // 对文本做HTML特殊字符转义
//        String result = convertJson(text);

        // 输出转义后的文本
        out.write(text.getBytes(super.getFastJsonConfig().getCharset()));
    }

    /**
     * JSON参数转义
     * <功能详细描述>
     *
     * @param json
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String convertJson(String json) {
        try {
            // 判断是否是JSON对象
            if (json.startsWith("{")) {
                // 将参数转换成JSONObject
                JSONObject jsonObj = JSONObject.parseObject(json, Feature.IgnoreNotMatch);
                System.out.println(jsonObj.toString());
                // 处理参数
                JSONObject myobj = jsonObj(jsonObj);
                System.out.println(myobj.toString());
                return myobj.toString();
            }
            // 判断是否是JSON数组
            else if (json.startsWith("[")) {
                // 将参数转换成JSONArray
                JSONArray jsonArray = JSONArray.parseArray(json);
                //处理参数
                JSONArray array = parseArray(jsonArray);
                return array.toString();
            } else {
                return json;
            }
        } catch (JSONException e) {
            LOGGER.error("Json数据解析处理失败！");
            return "{}";
        }
    }

    /**
     * JSON参数Map（对象）转义
     * <功能详细描述>
     *
     * @param json
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    private JSONObject jsonObj(JSONObject json) {

        for (String key : json.keySet()) {
            // 获取对象的值
            Object obj = json.get(key);

            // 判断对象类型
            if (obj instanceof List) {
                json.put(key, parseArray((JSONArray) obj));

            }
            // 判断是否是对象结构
            else if (obj instanceof Map) {
                // 处理参数
                json.put(key, jsonObj((JSONObject) obj));
            } else if (obj instanceof String) {
                // 处理参数
                json.put(key, convertStr((String) obj));
            }

        }
        return json;
    }

    /**
     * JSON参数List（数组）转义
     * <功能详细描述>
     *
     * @param jsonArray
     * @return
     * @see [类、类#方法、类#成员]
     */
    private JSONArray parseArray(JSONArray jsonArray) {
        // 判空
        if (null == jsonArray || jsonArray.isEmpty() || jsonArray.size() == 0) {
            return jsonArray;
        }
        //
        for (int i = 0, l = jsonArray.size(); i < l; i++) {
            Object obj = jsonArray.get(i);

            // 判断是否是数据结构
            if (obj instanceof List) {
                // 处理数组对象
                parseArray((JSONArray) obj);
            }
            // 判断是否是对象结构
            else if (obj instanceof Map) {
                // 处理参数
                jsonObj((JSONObject) obj);
            }
            // 判断是否是String结构
            else if (obj instanceof String) {
                jsonArray.set(i, convertStr((String) obj));
            }
        }

        return jsonArray;
    }

    /**
     * HTML脚本转义
     * <功能详细描述>
     *
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String convertStr(String str) {
        // TODO &、<、>、"、'、(、)、%、+、\
        return str;
//        return HtmlUtils.htmlEscape(str);
//        return str.replace("&", "&amp;")
//                .replace("<", "&lt;")
//                .replace(">", "&gt;")
//                .replace("\"", "&quot;")
//                .replace("'", "&#x27;")
//                .replace("(", "&#40;")
//                .replace(")", "&#41;")
//                .replace("%", "&#37;")
//                .replace("+", "&#43;")
//                .replace("\\", "&#92;");
    }
}
