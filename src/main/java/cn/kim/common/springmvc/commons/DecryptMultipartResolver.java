package cn.kim.common.springmvc.commons;

import cn.kim.common.attr.MagicValue;
import cn.kim.util.CommonUtil;
import cn.kim.util.TextUtil;
import cn.kim.util.ValidateUtil;
import cn.kim.util.CommonUtil;
import cn.kim.util.TextUtil;
import cn.kim.util.ValidateUtil;
import org.jsoup.helper.Validate;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsFileUploadSupport;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import sun.misc.Cache;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2017/11/27.
 * 文件解析器 id解密配置和参数XSS过滤
 */
public class DecryptMultipartResolver extends CommonsMultipartResolver {

    /**
     * 重写解析器 解密ID
     *
     * @param request
     * @return
     * @throws MultipartException
     */
    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
        MultipartParsingResult parsingResult = super.parseRequest(request);
        Map<String, String[]> parameters = parsingResult.getMultipartParameters();
        if (ValidateUtil.isEmpty(parameters)) {
            return parsingResult;
        }
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            if (values.length > 0) {
                if (key.toLowerCase().contains(MagicValue.ID) || MagicValue.KEY.equals(key.toLowerCase())) {
                    //解密ID
                    for (int i = 0; i < values.length; i++) {
                        try {
                            values[i] = TextUtil.toString(CommonUtil.idDecrypt(values[i]));
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    //过滤XSS攻击
                    for (int i = 0; i < values.length; i++) {
                        values[i] = CommonUtil.cleanXSS(values[i]);
                    }
                }
                parameters.put(key, values);
            }
        }
        return parsingResult;
    }
}
