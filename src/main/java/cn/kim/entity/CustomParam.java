package cn.kim.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 余庚鑫 on 2017/3/22.
 */
public class CustomParam {
    private String key;
    private String value;
    private String type;
    private String icon;
    /**
     * 是否是默认选择参数
     */
    private Boolean defaultParam;
    /**
     * key是否加密默认为不加密
     */
    @JSONField(serialize = false)
    private boolean isEncrypt;

    public CustomParam() {
    }

    public CustomParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public CustomParam(String key, String value, Boolean defaultParam) {
        this.key = key;
        this.value = value;
        this.defaultParam = defaultParam;
    }

    public CustomParam(String key, String value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public CustomParam(String key, String value, String type, String icon) {
        this.key = key;
        this.value = value;
        this.type = type;
        this.icon = icon;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getDefaultParam() {
        return defaultParam;
    }

    public void setDefaultParam(Boolean defaultParam) {
        this.defaultParam = defaultParam;
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    /**
     * 返回自身
     *
     * @param encrypt
     * @return
     */
    public CustomParam setEncrypt(boolean encrypt) {
        isEncrypt = encrypt;
        return this;
    }
}
