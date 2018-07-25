package cn.kim.util;

import cn.kim.common.attr.Attribute;
import cn.kim.service.ValidateService;
import cn.kim.common.attr.Attribute;
import cn.kim.service.MenuService;
import cn.kim.service.ValidateService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.soap.Text;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 余庚鑫 on 2017/3/30.
 * 验证各种方法
 */
@Component
public class ValidateUtil {

    @Autowired
    private ValidateService validateService;
    private static ValidateUtil validateUtil;

    public void setValidateService(ValidateService validateService) {
        this.validateService = validateService;
    }

    @PostConstruct
    public void init() {
        validateUtil = this;
        validateUtil.validateService = this.validateService;

    }

    /**
     * 没有返回true，有返回false
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(Object value) {
        if (value instanceof ArrayList) {
            return isEmpty((ArrayList) value);
        }
        return value == null || "".equals(value) || "null".equals(value.toString().toLowerCase());
    }

    public static boolean isEmpty(CharSequence value) {
        return value == null || value.length() == 0;
    }

    public static boolean isEmpty(Integer value) {
        return value == null || "".equals(value);
    }

    public static boolean isEmpty(Long value) {
        return value == null || "".equals(value);
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    public static boolean isEmpty(String[] value) {
        return value == null || value.length == 0;
    }

    /**
     * string字符串是否可以被格式化，可以返回false，不可以放回true
     *
     * @param date
     * @param format
     * @return
     */
    public static boolean isEmpty(String date, SimpleDateFormat format) {
        if (isEmpty(date)) {
            return true;
        }
        try {
            Date result = format.parse(date);
        } catch (ParseException e) {
            return true;
        }
        return false;
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    /**
     * 是否是json字符串
     *
     * @param json
     * @return
     */
    public static boolean isJson(String json) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * fastJson
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (str.length() == 0) {
            return false;
        } else {
            int sz = str.length();
            boolean hasExp = false;
            boolean hasDecPoint = false;
            boolean allowSigns = false;
            boolean foundDigit = false;
            int start = str.charAt(0) == '-' ? 1 : 0;
            int i;
            char ch;
            if (sz > start + 1 && str.charAt(start) == '0' && str.charAt(start + 1) == 'x') {
                i = start + 2;
                if (i == sz) {
                    return false;
                } else {
                    while (i < str.length()) {
                        ch = str.charAt(i);
                        if ((ch < '0' || ch > '9') && (ch < 'a' || ch > 'f') && (ch < 'A' || ch > 'F')) {
                            return false;
                        }

                        ++i;
                    }

                    return true;
                }
            } else {
                --sz;

                for (i = start; i < sz || i < sz + 1 && allowSigns && !foundDigit; ++i) {
                    ch = str.charAt(i);
                    if (ch >= '0' && ch <= '9') {
                        foundDigit = true;
                        allowSigns = false;
                    } else if (ch == '.') {
                        if (hasDecPoint || hasExp) {
                            return false;
                        }

                        hasDecPoint = true;
                    } else if (ch != 'e' && ch != 'E') {
                        if (ch != '+' && ch != '-') {
                            return false;
                        }

                        if (!allowSigns) {
                            return false;
                        }

                        allowSigns = false;
                        foundDigit = false;
                    } else {
                        if (hasExp) {
                            return false;
                        }

                        if (!foundDigit) {
                            return false;
                        }

                        hasExp = true;
                        allowSigns = true;
                    }
                }

                if (i < str.length()) {
                    ch = str.charAt(i);
                    if (ch >= '0' && ch <= '9') {
                        return true;
                    } else if (ch != 'e' && ch != 'E') {
                        if (!allowSigns && (ch == 'd' || ch == 'D' || ch == 'f' || ch == 'F')) {
                            return foundDigit;
                        } else if (ch != 'l' && ch != 'L') {
                            return false;
                        } else {
                            return foundDigit && !hasExp;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return !allowSigns && foundDigit;
                }
            }
        }
    }

    /**
     * 根据 表名 和字段获取验证信息
     *
     * @param validateTable
     * @param validateField
     * @return
     */
    public static String validField(String validateTable, String validateField) {
        StringBuilder builder = new StringBuilder();
        builder.append(" id='" + validateField + "' name='" + validateField + "' ");

        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(5);
        mapParam.put("SV_TABLE", validateTable);
        mapParam.put("SVF_FIELD", validateField);
        mapParam.put("IS_STATUS", Attribute.STATUS_SUCCESS);
        mapParam.put("SV_IS_STATUS", Attribute.STATUS_SUCCESS);
        mapParam.put("SVR_IS_STATUS", Attribute.STATUS_SUCCESS);
        //查询验证字段
        mapParam = validateUtil.validateService.selectValidateField(mapParam);

        //添加额外属性
        if (!isEmpty(mapParam)) {
            String fieldMinLength = TextUtil.toString(mapParam.get("SVF_MIN_LENGTH"));
            String fieldMaxLength = TextUtil.toString(mapParam.get("SVF_MAX_LENGTH"));
            String regex = TextUtil.toString(mapParam.get("SVR_REGEX"));
            int isRequired = TextUtil.toInt(mapParam.get("SVF_IS_REQUIRED"));

            //最小值
            if (!isEmpty(fieldMinLength)) {
                builder.append(" minlength='" + fieldMinLength + "' ");
            }

            //最大值
            if (!isEmpty(fieldMaxLength)) {
                builder.append(" maxlength='" + fieldMaxLength + "' ");
            }

            //正则验证
            if (!isEmpty(regex)) {
                builder.append(" pattern=\"" + regex.replaceAll("\\\\\\\\", "\\\\") + "\" data-bv-regexp-message='" + TextUtil.toString(mapParam.get("SVR_REGEX_MESSAGE")) + "' ");
            }

            //是否必填
            if (isRequired == Attribute.STATUS_SUCCESS) {
                builder.append(" required ");
            }
        }
        return builder.toString();
    }
}
