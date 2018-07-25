package cn.kim.common.tag;

import cn.kim.common.BaseData;
import cn.kim.common.attr.MagicValue;
import cn.kim.util.CommonUtil;
import cn.kim.util.HttpUtil;
import cn.kim.util.TextUtil;
import cn.kim.util.ValidateUtil;
import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by 余庚鑫 on 2018/6/3
 * 基础标签
 */
public abstract class BaseTagSupport extends RequestContextAwareTag {

    /**
     * 格式化CUSTOM
     *
     * @param custom
     * @param id
     * @param name
     * @return
     */
    protected String toTagId(String custom, String id, String name) {
        return (isEmpty(id) ? "" : "id = '" + id + "'") + " " + (isEmpty(name) ? "" : "name = '" + name + "'") + " " + custom;
    }

    /**
     * 自定义参数解析
     *
     * @param custom
     * @return
     */
    protected Map<String, Object> customResolve(String custom) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(3);
        if (!isEmpty(custom)) {
            Document document = Jsoup.parse("<custom " + custom + "></custom>");
            Elements elements = document.select(MagicValue.CUSTOM);

            resultMap.put(MagicValue.ID, elements.attr(MagicValue.ID));
            resultMap.put(MagicValue.NAME, elements.attr(MagicValue.NAME));

            if (elements.hasAttr(MagicValue.REQUIRED)) {
                String requiredValue = elements.attr(MagicValue.REQUIRED);
                if (isEmpty(requiredValue)) {
                    resultMap.put(MagicValue.REQUIRED, true);
                } else {
                    if (requiredValue.toLowerCase().equals(MagicValue.TRUE)) {
                        resultMap.put(MagicValue.REQUIRED, true);
                    }
                }
            }
        }
        return resultMap;
    }

    protected boolean isEmpty(Object o) {
        return ValidateUtil.isEmpty(o);
    }

    protected String getBaseUrl() {
        return HttpUtil.getContextPath();
    }

    protected String toString(Object o) {
        return TextUtil.toString(o);
    }

    protected boolean toBoolean(Object o) {
        return TextUtil.toBoolean(o);
    }

    protected String idEncrypt(Object val) {
        try {
            return toString(CommonUtil.idEncrypt(val));
        } catch (InvalidKeyException e) {
            return "";
        }
    }

    protected String uuid() {
        return TextUtil.toUUID();
    }

    protected String random() {
        //重复调用导致id冲突
        //以系统时间作为随机种子
        Random ran = new Random(System.currentTimeMillis());
        return toString(ran.nextInt(Integer.MAX_VALUE));
    }
}
