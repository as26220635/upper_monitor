package cn.kim.entity;

import cn.kim.util.DateUtil;
import cn.kim.util.ValidateUtil;
import cn.kim.util.DateUtil;
import cn.kim.util.TextUtil;
import cn.kim.util.ValidateUtil;
import com.google.common.collect.Maps;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Created by 余庚鑫 on 2018/2/22.
 * 查询条件类
 */
public class QuerySet implements Serializable {
    /**
     * 等于
     */
    public static final String EQ = "eq";
    /**
     * 不等于
     */
    public static final String NE = "ne";
    /**
     * 在其中
     */
    public static final String IN = "in";
    /**
     * 模糊查询
     */
    public static final String LIKE = "like";
    /**
     * 小于
     */
    public static final String LESS = "less";
    /**
     * 小于等于
     */
    public static final String LESS_THAN = "lessThan";
    /**
     * 大于
     */
    public static final String GREATER = "greater";
    /**
     * 大于等于
     */
    public static final String GREATER_THAN = "greaterThan";
    /**
     * 分隔符
     */
    private static final String SEPARATOR = "_";

    private String view;
    private Map<String, Object> paramsMap;
    private Map<String, Object> finalMap;
    /**
     * 扩展的WHERE 条件
     */
    private List<String> extendWhere;
    /**
     * 流程使用
     */
    private String processSelect;
    private String processJoin;
    private String processWhere;
    /**
     * 流程定义id
     */
    private String processDefinitionId;

    public QuerySet() {
        this.paramsMap = Maps.newHashMapWithExpectedSize(16);
        this.finalMap = Maps.newHashMapWithExpectedSize(16);
        this.extendWhere = new ArrayList<>();
    }

    /**
     * 设置值的查询方式
     *
     * @param method 查询方式
     * @param key
     * @param value
     */
    public void set(String method, String key, Object value) {
        if (!ValidateUtil.isEmpty(value)) {
            if (LIKE.equals(method)) {
                value = "%" + value + "%";
            }
            paramsMap.put((ValidateUtil.isEmpty(method) ? "" : TextUtil.toString(method) + SEPARATOR) + key, value);
        }
    }

    /**
     * between
     *
     * @param key
     * @param val1
     * @param val2
     */
    public void setBetween(String key, Object val1, Object val2) {
        this.set(LESS_THAN, key, val1);
        this.set(GREATER_THAN, key, val1);
    }

    public void setOrderByClause(String orderByClause) {
        if (ValidateUtil.isEmpty(orderByClause)) {
            return;
        }
        this.finalMap.put("orderByClause", orderByClause);
    }

    /**
     * 添加扩展where条件
     *
     * @param whereSql
     */
    public void setWhere(String whereSql) {
        this.extendWhere.add(whereSql);
    }

    public void setProcessSelect(String processSelect) {
        this.processSelect = processSelect;
    }

    public void setProcessJoin(String processJoin) {
        this.processJoin = processJoin;
    }

    public void setProcessWhere(String processWhere) {
        this.processWhere = processWhere;
    }

    public void setLimit(Integer limit) {
        this.finalMap.put("limit", limit);
    }

    public void setOffset(Integer offset) {
        this.finalMap.put("offset", offset);
    }

    /**
     * 吧Map转为WHERESQL语句
     */
    public Map<String, Object> getWhereMap() {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(16);

        StringBuilder resultWhereSql = new StringBuilder();
        for (String key : this.paramsMap.keySet()) {
            if (key.contains(SEPARATOR)) {
                String method = key.substring(0, key.indexOf("_"));
                String field = key.substring(key.indexOf("_") + 1, key.length());

                resultWhereSql.append(getPackParam(method, field, this.paramsMap.get(key)));
            }
        }
        //添加扩展条件
        extendWhere.forEach(where -> {
            resultWhereSql.append(where);
        });

        resultMap.putAll(this.finalMap);
        resultMap.put("view", getView());
        resultMap.put("whereClause", resultWhereSql.toString());
        //流程使用
        resultMap.put("processSelect", processSelect);
        resultMap.put("processJoin", processJoin);
        resultMap.put("processWhere", processWhere);
        resultMap.put("processDefinitionId", processDefinitionId);

        return resultMap;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(16);
        resultMap.putAll(this.paramsMap);
        resultMap.putAll(this.finalMap);
        return resultMap;
    }

    /**
     * 拿到打包参数
     *
     * @return
     */
    private static String getPackParam(String method, String key, Object val) {
        //sql过滤
        val = TextUtil.sqlValidate(val);
        if (ValidateUtil.isEmpty(val)) {
            return "";
        }

        StringBuffer sb = new StringBuffer();

        sb.append(" AND " + key + getMethonMeaning(method) + " ");

        //List集合
        if (method.equals(IN)) {
            List<Object> list = toObject((List) val);

            String params = "";
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                String keyValue = TextUtil.toString(obj);
                if (ValidateUtil.isEmpty(keyValue)) {
                    continue;
                }
                params += obj instanceof String ? "'" + keyValue + "'" : keyValue;
                if (i + 1 != list.size()) {
                    params += ",";
                }
            }
            if (!ValidateUtil.isEmpty(params)) {
                sb.append("(");
                sb.append(params);
                sb.append(")");
            }
        } else {
            if (val instanceof String) {
                if (method.equals(LIKE)) {
                    sb.append(" '%" + TextUtil.toString(val) + "%' ");
                } else {
                    sb.append(" '" + TextUtil.toString(val) + "' ");
                }
            } else if (val instanceof Date) {
                sb.append(" '" + DateUtil.getDate(DateUtil.FORMAT, (Date) val) + "' ");
            } else {
                sb.append(" " + TextUtil.toString(val) + " ");
            }
        }

        return sb.toString();
    }

    /**
     * 拿到动作的符号
     *
     * @param method
     * @return
     */
    private static String getMethonMeaning(String method) {
        String result = " ";
        if (method.equals(EQ)) {
            result = " = ";
        } else if (method.equals(NE)) {
            result = " != ";
        } else if (method.equals(IN)) {
            result = " in ";
        } else if (method.equals(LIKE)) {
            result = " like ";
        } else if (method.equals(LESS)) {
            result = " < ";
        } else if (method.equals(LESS_THAN)) {
            result = " <= ";
        } else if (method.equals(GREATER)) {
            result = " > ";
        } else if (method.equals(GREATER_THAN)) {
            result = " >= ";
        }
        return result;
    }

    private static <E> List<Object> toObject(List<E> list) {
        List<Object> objlist = new ArrayList<Object>();
        for (Object e : list) {
            Object obj = (Object) e;
            objlist.add(obj);
        }
        return objlist;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }
}
