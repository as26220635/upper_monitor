package cn.kim.controller.converter;

import cn.kim.util.ValidateUtil;
import org.springframework.core.convert.converter.Converter;

/**
 * 去掉空格
 */
public class StringTrimConverter implements Converter<String, String> {
    @Override
    public String convert(String source) {

        try {
            //去掉字符串两边空格，如果去除后为空设置为null
            if (source != null) {
                source = source.trim();
                if (ValidateUtil.isEmpty(source)) {
                    return null;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return source;
    }

}
