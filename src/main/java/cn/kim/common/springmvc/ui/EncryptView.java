package cn.kim.common.springmvc.ui;

import cn.kim.util.CommonUtil;
import cn.kim.util.ValidateUtil;
import org.springframework.web.servlet.view.JstlView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2017/11/7.
 * 自定义ID加密视图
 */
public class EncryptView extends JstlView {
    /**
     * "cn.kim.entity.CustomParam"
     */
    private static final String[] ENCRYPT_OBJ = {"java.lang.String", "java.lang.Object"};

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //ID加密
        for (String key : map.keySet()) {
            Object obj = map.get(key);
            if (!validateClass(obj)) {
                CommonUtil.idEncrypt(obj);
            } else if (CommonUtil.isEncrypt(key, obj)) {
                map.put(key, CommonUtil.idEncrypt(obj));
            }
        }
        super.renderMergedOutputModel(map, httpServletRequest, httpServletResponse);
    }

    /**
     * 验证类型是否是
     *
     * @param obj
     * @return
     */
    public <T> boolean validateClass(T obj) {
        if (ValidateUtil.isEmpty(obj)) {
            return true;
        }
        for (String cls : ENCRYPT_OBJ) {
            if (obj.getClass().getName().equals(cls)) {
                return true;
            }
        }
        return false;
    }
}
