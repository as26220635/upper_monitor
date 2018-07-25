package cn.kim.entity;

import java.lang.annotation.Annotation;

/**
 * Created by 余庚鑫 on 2018/4/1.
 * 注解参数
 */
public class AnnotationParam {
    /**
     * 简单名字
     */
    private String simpleName;
    /**
     * 名字
     */
    private String name;
    /**
     * 类型
     */
    private Class<?> type;
    /**
     * 值
     */
    private Object value;
    /**
     * 注解
     */
    private Annotation anno;
    /**
     * 参数位置
     */
    private int index;

    public AnnotationParam() {
        super();
    }

    public AnnotationParam(String simpleName, String name, Class<?> type, Object value, Annotation anno, int index) {
        super();
        this.simpleName = simpleName;
        this.name = name;
        this.type = type;
        this.value = value;
        this.anno = anno;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Annotation getAnno() {
        return anno;
    }

    public void setAnno(Annotation anno) {
        this.anno = anno;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Param [simpleName=" + simpleName + ", name=" + name + ", type=" + type + ", value=" + value + ", anno="
                + anno + "]";
    }
}
