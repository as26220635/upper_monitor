package cn.kim.common.xss;

import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.Tips;
import cn.kim.util.AESUtil;
import cn.kim.util.CommonUtil;
import cn.kim.util.TextUtil;
import cn.kim.util.ValidateUtil;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.InvalidKeyException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2017/6/3.
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        //ID解密
        if (CommonUtil.isEncrypt(parameter, true)) {
            for (int i = 0; i < count; i++) {
                try {
                    encodedValues[i] = TextUtil.toString(CommonUtil.idDecrypt(values[i]));
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (int i = 0; i < count; i++) {
                encodedValues[i] = CommonUtil.cleanXSS(values[i]);
            }
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        //ID解密
        if (CommonUtil.isEncrypt(parameter, value.getClass().toString())) {
            try {
                return TextUtil.toString(CommonUtil.idDecrypt(value));
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }
        return CommonUtil.cleanXSS(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return CommonUtil.cleanXSS(value);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        HashMap<String, String[]> paramMap = new HashMap(super.getParameterMap());
        paramMap = (HashMap<String, String[]>) paramMap.clone();

        for (Iterator iterator = paramMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) iterator.next();
            String[] values = entry.getValue();
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof String) {
                    if (CommonUtil.isEncrypt(entry.getKey().toString(), true)) {
                        try {
                            values[i] = TextUtil.toString(CommonUtil.idDecrypt(values[i]));
                        } catch (InvalidKeyException e) {
                        }
                    } else {
                        values[i] = CommonUtil.cleanXSS(values[i]);
                    }
                }
            }
            entry.setValue(values);
        }
        return paramMap;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return super.getParameterNames();
    }
}