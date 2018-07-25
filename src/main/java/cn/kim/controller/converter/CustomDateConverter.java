package cn.kim.controller.converter;

import cn.kim.util.ValidateUtil;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换
 */
public class CustomDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {

        if (ValidateUtil.isEmpty(source)) {
            return null;
        }

        try {
            //进行日期转换
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(source);
            } catch (ParseException e) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
