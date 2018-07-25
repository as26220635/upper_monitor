package cn.kim.common.attr;

import cn.kim.util.TextUtil;

/**
 * Created by 余庚鑫 on 2018/3/21
 * 类型解析
 */
public class ParamTypeResolve {
    /**
     * 根据登录类型获取名称
     *
     * @param tyep
     * @return
     */
    public static String getOpeatorTypeName(String tyep) {
        if ("1".equals(tyep)) {
            return "管理员";
        } else if ("2".equals(tyep)) {
            return "会员";
        } else {
            return "未知!";
        }
    }

    /**
     * 获取状态解释
     *
     * @param val
     * @return
     */
    public static String statusExplain(Object val) {
        try {
            int status = TextUtil.toInt(val);
            if (status == Attribute.STATUS_SUCCESS) {
                return "开启";
            } else {
                return "关闭";
            }
        } catch (Exception e) {
            return "未知";
        }
    }

}
