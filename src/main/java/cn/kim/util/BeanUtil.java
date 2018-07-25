package cn.kim.util;

import cn.kim.common.attr.MagicValue;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.util.HtmlUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by 余庚鑫 on 2017/5/31.
 */
public class BeanUtil {

    public static <T> void testReflect(T model) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (ValidateUtil.isEmpty(model)) {
            return;
        }
        List<Field> fields = getBeanFields(model.getClass());
        for (Field field : fields) {
            //获取属性的名字
            String name = field.getName();
            //将属性的首字符大写，方便构造get，set方法
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            //获取属性的类型
            String type = field.getGenericType().toString();
            //如果type是类类型，则前面包含"class "，后面跟类名
            if ("class java.lang.String".equals(type)) {
                Method m = model.getClass().getMethod("get" + name);
                //调用getter方法获取属性值
                String value = (String) m.invoke(model);
                if (value != null) {
                    System.out.println("attribute value:" + value);
                }
            }
        }
    }

    /**
     * 通过反射修改String类型的值 防止XSS攻击
     *
     * @param model
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static <T> void stringXssReflect(T model) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (ValidateUtil.isEmpty(model)) {
            return;
        }
        List<Field> fields = getBeanFields(model.getClass());
        String name = null;
        String type = null;
        Method m = null;
        for (Field field : fields) {
            //获取属性的名字
            name = field.getName();
            //将属性的首字符大写，方便构造get，set方法
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            //获取属性的类型
            type = field.getGenericType().toString();
            //如果type是类类型，则前面包含"class "，后面跟类名
            if ("class java.lang.String".equals(type)) {
                m = model.getClass().getMethod("get" + name);
                //调用getter方法获取属性值
                String value = (String) m.invoke(model);
                if (!ValidateUtil.isEmpty(value)) {
                    //防止xss 攻击
                    field.setAccessible(true);
                    field.set(model, HtmlUtils.htmlEscape(HtmlUtils.htmlUnescape(value)).trim());
                }
            }
        }
    }

    /**
     * 通过反射加密ID字段
     *
     * @param model
     * @throws Exception
     */
    public static <T> void idEncryptReflect(T model) throws Exception {
        if (ValidateUtil.isEmpty(model)) {
            return;
        }
        List<Field> fields = getBeanFields(model.getClass());
        String name = null;
        String type = null;
        Method m = null;
        for (Field field : fields) {
            name = field.getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            type = field.getGenericType().toString();
            //加密id字段
            if (CommonUtil.isEncrypt(name, type)) {
                m = model.getClass().getMethod("get" + name);
                String value = (String) m.invoke(model);
//                if (value != null && ValidateUtil.isNumber(value)) {
                if (!ValidateUtil.isEmpty(value)) {
                    field.setAccessible(true);
                    field.set(model, AESUtil.encode(value));
                }
            }
        }
    }

    /**
     * 通过反射解密ID字段
     *
     * @param model
     * @throws Exception
     */
    public static <T> void idDecryptReflect(T model) throws Exception {
        if (ValidateUtil.isEmpty(model)) {
            return;
        }
        List<Field> fields = getBeanFields(model.getClass());
        String name = null;
        String type = null;
        Method m = null;
        for (Field field : fields) {
            name = field.getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            type = field.getGenericType().toString();
            //解密id字段
            if (CommonUtil.isEncrypt(name, type)) {
                m = model.getClass().getMethod("get" + name);
                String value = (String) m.invoke(model);
//                if (value != null && !ValidateUtil.isNumber(value)) {
                if (!ValidateUtil.isEmpty(value)) {
                    field.setAccessible(true);
                    field.set(model, AESUtil.dncode(value));
                }
            }
        }
    }

    /**
     * 遍历拿到全部字段
     *
     * @param cls
     * @return
     */
    public static List<Field> getBeanFields(Class cls) {
        List<Field> fields = new ArrayList<Field>();
        for (Field field : cls.getDeclaredFields()) {
            fields.add(field);
        }
        if (cls.getSuperclass() != null && !MagicValue.JAVA_LANG_OBJECT.equals(cls.getSuperclass().getName().toLowerCase())) {
            fields.addAll(getBeanFields(cls.getSuperclass()));
        }
        return fields;
    }

    /**
     * 连接数组
     *
     * @param first
     * @param second
     * @param <T>
     * @return
     */
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * 连接多个数组
     *
     * @param first
     * @param rest
     * @param <T>
     * @return
     */
    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 不要复制空值
     *
     * @param src
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * 全部复制
     *
     * @param src
     * @param target
     */
    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target);
    }
}
