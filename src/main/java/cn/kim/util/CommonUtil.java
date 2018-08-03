package cn.kim.util;

import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.AttributePath;
import cn.kim.common.attr.MagicValue;
import cn.kim.common.eu.SystemEnum;
import cn.kim.entity.ActiveUser;
import cn.kim.entity.CustomParam;
import cn.kim.entity.DataTablesView;
import cn.kim.entity.Tree;
import cn.kim.service.MenuService;
import com.google.common.collect.Maps;
import com.sun.org.apache.xml.internal.security.keys.content.MgmtData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 余庚鑫 on 2017/3/25.
 */
@Component
public class CommonUtil {
    /**
     * 加密解密字段
     */
    public static String[] CONTAINS_ENCRYPT_FIELDS = {"id", "key", "sps_step_transactor"};
    /**
     * URL转义字段
     */
    public static String[] CONTAINS_URL_PARAMS_FIELDS = {"name", "title", "table"};

    /**
     * 不加密字段
     */
    public static Map<String, String> NO_ENCRYPT_FIELDS = Maps.newHashMapWithExpectedSize(4);

    static {
        NO_ENCRYPT_FIELDS.put("SCC_WIDTH", "SCC_WIDTH");
        NO_ENCRYPT_FIELDS.put("SB_BUTTONID", "SB_BUTTONID");
        NO_ENCRYPT_FIELDS.put("BEGC_ID", "BEGC_ID");
        NO_ENCRYPT_FIELDS.put("BEGC_KEY", "BEGC_KEY");
    }

    @Autowired
    private MenuService menuService;
    private static CommonUtil commonUtil;

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostConstruct
    public void init() {
        commonUtil = this;
        commonUtil.menuService = this.menuService;

    }

    /**
     * 是否成功
     *
     * @param resultMap
     * @return
     */
    public static boolean isSuccess(Map<String, Object> resultMap) {
        return TextUtil.toString(resultMap.get("status")).equals(Attribute.STATUS_SUCCESS) ? true : false;
    }

    /**
     * 返回上传文件list
     *
     * @param request
     * @return
     */
    public static List<MultipartFile> commonsMultipartResolver(HttpServletRequest request, String filesName) {
        List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();

        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            multipartFiles = multiRequest.getFiles(filesName);
//            //取得request中的所有文件名
//            Iterator<String> iter = multiRequest.getFileNames();
//            while (iter.hasNext()) {
//                multipartFiles.add(multiRequest.getFile(iter.next()));
//            }

        }
        return multipartFiles;
    }

    /**
     * 获取上传单个文件
     *
     * @param request
     * @return
     */
    public static MultipartFile getMultipartFile(HttpServletRequest request) {
        MultipartFile imgUpload = null;

        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                imgUpload = multiRequest.getFile(iter.next());
            }
        }

        return imgUpload;
    }

    /**
     * 返回单个文件解析器
     *
     * @param request
     * @param filesName
     * @return
     */
    public static MultipartFile commonsMultipartResolverOne(HttpServletRequest request, String filesName) {
        List<MultipartFile> multipartFiles = CommonUtil.commonsMultipartResolver(request, filesName);
        if (!ValidateUtil.isEmpty(multipartFiles)) {
            return multipartFiles.get(0);
        }
        return null;
    }

    /**
     * 根据start返回到底从哪里开始
     * 如果start是字符串就返回0 如果是数字 是0 就返回0  其他数字都是 返回-1后的数字
     *
     * @param start
     * @return
     */
    public static Integer getStart(Integer start) {
        return start == 0 ? 0 : start - 1;
    }

    /**
     * 分页
     *
     * @param count
     * @param length
     * @return
     */
    public static long getPage(long count, int length) {
        return count % length == 0 ? count / length : ((count - (count % length)) / length) + 1;
    }

    /**
     * 添加&nbsp;
     *
     * @param size 添加几个
     * @return
     */
    public static String addNbsp(Integer size) {
        String result = "";
        for (int i = 0; i < size; i++) {
            result += "&nbsp;";
        }
        return result;
    }

    /**
     * 显示时间，如果与当前时间差别小于一天，则自动用**秒(分，小时)前，如果大于一天则用format规定的格式显示
     * jstl可以使用fns:showTime
     *
     * @param ctime  时间
     * @param format 格式 格式描述:例如:yyyy-MM-dd yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String showTime(Date ctime, String format) {
        String r = "";
        if (ctime == null) {
            return r;
        }
        if (format == null) {
            format = "yyyy-MM-dd HH:mm";
        }
        long nowtimelong = System.currentTimeMillis();
        long ctimelong = ctime.getTime();
        long result = Math.abs(nowtimelong - ctimelong);
        if (result < 60000)// 一分钟内
        {
            long seconds = result / 1000;
            r = seconds + "秒钟前";
        } else if (result >= 60000 && result < 3600000)// 一小时内
        {
            long seconds = result / 60000;
            r = seconds + "分钟前";
        } else if (result >= 3600000 && result < 86400000)// 一天内
        {
            long seconds = result / 3600000;
            r = seconds + "小时前";
        } else if (result >= 86400000 && result < 172800000)// 昨天
        {
            r = "昨天";
        } else if (result >= 86400000 && result < 604800000)// 7天内
        {
            long seconds = result / 86400000;
            r = seconds + "天前";
        } else// 日期格式
        {
            r = new SimpleDateFormat(format).format(ctime);
        }
        return r;
    }

    /**
     * 转为int
     * jstl可以使用fns:toInt
     *
     * @param str
     * @return
     */
    public static Integer toInt(String str) {
        return Integer.parseInt(str);
    }

    /**
     * 拿到拼音首字母转为小写
     * jstl可以使用fns:getPinYinHeadCharLowCase
     *
     * @param str
     * @return
     */
    public static String getPinYinHeadCharLowCase(String str) {
        return TextUtil.getPinYinHeadChar(str).toLowerCase();
    }

    /**
     * 根据文件后缀变换图标
     * jstl可以使用fns:getFileIcon
     *
     * @param str
     * @return
     */
    public static String getFileIcon(String str) {
        String fileName = str.toLowerCase();
        if (fileName.endsWith(".jpg") || fileName.endsWith("jpeg") || fileName.endsWith(".png") || fileName.endsWith("gif")) {
            return "mdi mdi-file-image";
        } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
            return "mdi mdi-file-excel";
        } else if (fileName.endsWith("doc") || fileName.endsWith("docx")) {
            return "mdi mdi-file-word";
        } else if (fileName.endsWith("rar") || fileName.endsWith("zip") || fileName.endsWith("7z")) {
            return "mdi mdi-package";
        } else if (fileName.endsWith("pdf")) {
            return "mdi mdi-file-pdf";
        } else if (fileName.endsWith("html")) {
            return "mdi mdi-language-html5";
        } else if (fileName.endsWith("js")) {
            return "mdi mdi-language-javascript";
        } else if (fileName.endsWith("video")) {
            return "mdi mdi-file-video";
        } else {
            return "mdi mdi-file";
        }
    }

    /**
     * 是否参数为1 yes
     * jstl可以使用fns:isYes
     *
     * @param val
     * @return
     */
    public static boolean isYes(Object val) {
        return TextUtil.toString(val).equals(TextUtil.toString(Attribute.STATUS_SUCCESS)) ? true : false;
    }

    /**
     * 是否真假 真返回参数1 假返回参数2
     * jstl可以使用fns:trueOrFalse
     *
     * @param status
     * @param val1
     * @param val2
     * @return
     */
    public static Object trueOrFalse(Object status, Object val1, Object val2) {
        if (status instanceof Boolean) {
            return TextUtil.toBoolean(status) ? val1 : val2;
        } else {
            return TextUtil.toString(status).equals(TextUtil.toString(Attribute.STATUS_SUCCESS)) ? val1 : val2;
        }
    }

    /**
     * 格式化函数
     *
     * @param func
     * @param index
     * @param field
     * @return
     */
    public static String formatFunc(String func, int index, String field) {
        return func.replace("targets", TextUtil.toString(index)).replace("field", "'" + field + "'").replaceAll(";", "");
    }

    /**
     * 获取随机的颜色
     *
     * @return
     */
    public static String getRandomIconColor() {
        String[] colors = new String[]{"icon icon-rose", "icon icon-primary", "icon icon-info",
                "icon icon-success", "icon icon-warning", "icon icon-danger"};
        return colors[new Random().nextInt(colors.length - 1)];
    }

    /**
     * 判断是否存在数组中
     *
     * @param checks
     * @param check
     * @return
     */
    public static boolean isCheckArray(String[] checks, String check) {
        if (ValidateUtil.isEmpty(checks)) {
            return false;
        }

        for (String s : checks) {
            if (check.equalsIgnoreCase(s)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 图片的src id解密
     *
     * @param content
     * @return
     */
    public static String setImgDecrypt(String content) throws InvalidKeyException {
        if (ValidateUtil.isEmpty(content)) {
            return "";
        }
        //HTML解除转义
        content = HtmlUtils.htmlUnescape(content);

        Document document = Jsoup.parse(content);
        Elements imgs = document.select("img");
        for (Element img : imgs) {
            String src = img.attr("src");
            if (src.indexOf(AttributePath.FILE_PREVIEW_URL) != -1) {
                //ID解密
                String key = AESUtil.dncode(src.substring(src.lastIndexOf("/") + 1, src.length()));
                String url = src.substring(0, src.lastIndexOf("/") + 1);
                img.attr("src", url + key);
            }
        }
        System.out.println(document.body().toString());
        return document.body().toString();
    }

    /**
     * 图片的src id加密
     *
     * @param content
     * @return
     */
    public static String setImgEncrypt(String content) throws InvalidKeyException {
        if (ValidateUtil.isEmpty(content)) {
            return "";
        }

        Document document = Jsoup.parse(content);
        Elements imgs = document.select("img");
        for (Element img : imgs) {
            String src = clearPoint(img.attr("src"));
            if (src.indexOf(AttributePath.FILE_PREVIEW_URL) != -1) {
                //ID加密
                String key = TextUtil.toString(idEncrypt(src.substring(src.lastIndexOf("/") + 1, src.length())));
                String url = src.substring(0, src.lastIndexOf("/") + 1);

                img.attr("src", url + key);
            }
        }
        return document.body().toString();
    }

    /**
     * 操作img图片设置属性(延迟加载，zoom缩放)在界面上面显示 ID加密
     *
     * @param content
     * @return
     */
    public static String setImgProperty(String content) throws InvalidKeyException {

        if (ValidateUtil.isEmpty(content)) {
            return "";
        }

        Document document = Jsoup.parse(content);
        Elements imgs = document.select("img");
        for (Element img : imgs) {
            if (img.attr(MagicValue.DATA_SRC) == null || "".equals(img.attr(MagicValue.DATA_SRC))) {
                String defaultUrl = clearPoint(img.attr("src"));
                //ID加密
                if (defaultUrl.indexOf(AttributePath.FILE_PREVIEW_URL) != -1) {
                    String key = TextUtil.toString(idEncrypt(defaultUrl.substring(defaultUrl.lastIndexOf("/") + 1, defaultUrl.length())));
                    String url = defaultUrl.substring(0, defaultUrl.lastIndexOf("/") + 1);
                    defaultUrl = url + key;
                }
                //判断是否是该服务器图片，是的话显示loding显示小图，不是的话直接显示URL
                String loadingSrc = HttpUtil.getContextPath() + Attribute.LOADING_IMAGE;

                img.attr("data-sizes", "auto");
                img.attr("data-src", defaultUrl);
                img.attr("data-action", "zoom");
                img.attr("data-parent-fit", "contain");
                img.attr("style", img.attr("style") + " max-width: 100%; max-height: 100%;");
                img.attr("src", loadingSrc);
                img.attr("height", "auto");
                img.addClass("lazyload");
            }
        }
        return document.body().toString();
    }

    /**
     * 去掉html标签
     *
     * @param htmlStr
     * @return
     */
    public static String getTextFromHTML(String htmlStr) {

        if (ValidateUtil.isEmpty(htmlStr)) {
            return "";
        }

        Document doc = Jsoup.parse(htmlStr);
        //删除图片标签
        Elements imgs = doc.select("img");
        imgs.removeAll(imgs);

        String text = doc.text();
        // 去除多余的空白s
        StringBuilder builder = new StringBuilder(text);
        int index = 0;
        while (builder.length() > index) {
            char tmp = builder.charAt(index);
            if (Character.isSpaceChar(tmp) || Character.isWhitespace(tmp)) {
                builder.setCharAt(index, ' ');
            }
            index++;
        }
        text = builder.toString().replaceAll(" +", " ").trim();

        return text;
    }

    /**
     * 去掉开头的编辑器加的../
     */
    public static String clearPoint(String str) {
        if (ValidateUtil.isEmpty(str)) {
            return "";
        }
        if (str.startsWith("../")) {
            return HttpUtil.getContextPath() + str.substring(TextUtil.subCount(str, "../") * "../".length(), str.length());
        }
        return str;
    }

    /**
     * 获取完整Url
     *
     * @param baseUrl
     * @return
     */
    public static String getCompleteUrl(String baseUrl) {
        return HttpUtil.getContextPath() + baseUrl;
//        return BASE_URL;
    }

    /**
     * 过滤xss攻击
     *
     * @param value
     * @return
     */
    public static String cleanXSS(String value) {
        //You'll need to remove the spaces from the html entities below
//        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
//        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
//        value = value.replaceAll("'", "& #39;");
//        value = value.replaceAll("eval\\((.*)\\)", "");
//        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
//        value = value.replaceAll("script", "");
//        return HtmlUtils.htmlUnescape(HtmlUtils.htmlEscape(value));
        return ValidateUtil.isEmpty(value) ? value : HtmlUtils.htmlEscape(HtmlUtils.htmlUnescape(value)).trim();
    }

    /**
     * ID加密
     *
     * @param obj
     * @return
     */
    public static Object idEncrypt(Object obj) throws InvalidKeyException {
        if (ValidateUtil.isEmpty(obj)) {
            return obj;
        }
        try {
            if (obj instanceof String || obj instanceof Integer || obj instanceof Long) {
                String str = TextUtil.toString(obj);
                if (str.contains(Attribute.SERVICE_SPLIT)) {
                    String p = "";
                    for (String c : str.split(Attribute.SERVICE_SPLIT)) {
                        p = p + AESUtil.encode(c) + Attribute.SERVICE_SPLIT;
                    }
                    return TextUtil.interceptSymbol(p, Attribute.SERVICE_SPLIT);
                } else {
                    return AESUtil.encode(str);
                }
            } else if (obj instanceof DataTablesView) {
                DataTablesView<?> datatablesView = (DataTablesView<?>) obj;
                if (datatablesView.isEncrypt()) {
                    for (Object object : datatablesView.getData()) {
                        if (object instanceof Map) {
                            idEncrypt(object);
                        } else {
                            BeanUtil.idEncryptReflect(object);
                        }
                    }
                }
            } else if (obj instanceof ArrayList || obj instanceof LinkedList) {
                for (Object object : (List<Object>) obj) {
                    if (object instanceof CustomParam) {
                        CustomParam param = (CustomParam) object;
                        if (param.isEncrypt()) {
                            String key = param.getKey();
                            param.setKey(ValidateUtil.isNumber(key) ? AESUtil.encode(key) : key);
                        }
                    } else if (object instanceof Tree) {
                        Tree tree = (Tree) object;
                        if (!ValidateUtil.isEmpty(tree.getId())) {
                            tree.setId(TextUtil.toString(idEncrypt(tree.getId())));
                        }
                        if (!ValidateUtil.isEmpty(tree.getNodes())) {
                            idEncrypt(tree.getNodes());
                        }
                    } else if (object instanceof Map) {
                        Map<String, Object> map = (Map<String, Object>) object;
                        for (String key : map.keySet()) {
                            if (isEncrypt(key, map.get(key))) {
                                map.put(key, idEncrypt(map.get(key)));
                            }
                        }
                    } else {
                        BeanUtil.idEncryptReflect(object);
                    }
                }
            } else if (obj instanceof Tree) {
                Tree tree = ((Tree) obj);
                if (!ValidateUtil.isEmpty(tree.getId())) {
                    tree.setId(TextUtil.toString(idEncrypt(tree.getId())));
                }
                if (!ValidateUtil.isEmpty(tree.getNodes())) {
                    idEncrypt(tree.getNodes());
                }
            } else if (obj instanceof Map) {
                Map map = ((Map) obj);
                for (Object key : map.keySet()) {
                    if (isEncrypt(key, map.get(key))) {
                        map.put(key, idEncrypt(map.get(key)));
                    }
                }
            } else if (obj instanceof Set) {
                Set set = ((Set) obj);
                for (Object object : set) {
                    object = idEncrypt(obj);
                }
            } else {
                BeanUtil.idEncryptReflect(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidKeyException("无效的key");
        }

        return obj;
    }

    /**
     * ID解密
     *
     * @param obj
     * @return
     */
    public static Object idDecrypt(Object obj) throws InvalidKeyException {
        if (ValidateUtil.isEmpty(obj)) {
            return obj;
        }
        try {
            if (obj instanceof String || obj instanceof Integer || obj instanceof Long) {
                String str = TextUtil.toString(obj);
                if (str.contains(Attribute.SERVICE_SPLIT)) {
                    String p = "";
                    for (String c : str.split(Attribute.SERVICE_SPLIT)) {
                        p = p + AESUtil.dncode(c) + Attribute.SERVICE_SPLIT;
                    }
                    return TextUtil.interceptSymbol(p, Attribute.SERVICE_SPLIT);
                } else {
                    return AESUtil.dncode(str);
                }
            } else if (obj instanceof DataTablesView) {
                DataTablesView<?> datatablesView = (DataTablesView<?>) obj;
                for (Object object : datatablesView.getData()) {
                    if (object instanceof Map) {
                        idDecrypt(object);
                    } else {
                        BeanUtil.idDecryptReflect(object);
                    }
                }
            } else if (obj instanceof ArrayList || obj instanceof LinkedList) {
                for (Object object : (List<Object>) obj) {
                    if (object instanceof CustomParam) {
                        CustomParam param = (CustomParam) object;
                        if (param.isEncrypt()) {
                            String key = param.getKey();
                            param.setKey(ValidateUtil.isNumber(key) ? key : AESUtil.dncode(key));
                        }
                    } else if (object instanceof Tree) {
                        Tree tree = (Tree) object;
                        if (!ValidateUtil.isEmpty(tree.getId())) {
                            tree.setId(TextUtil.toString(idDecrypt(tree.getId())));
                        }
                        if (!ValidateUtil.isEmpty(tree.getNodes())) {
                            idDecrypt(tree.getNodes());
                        }
                    } else if (object instanceof Map) {
                        Map<String, Object> map = (Map<String, Object>) object;
                        for (String key : map.keySet()) {
                            if (isEncrypt(key, map.get(key))) {
                                map.put(key, idDecrypt(map.get(key)));
                            }
                        }
                    } else {
                        BeanUtil.idDecryptReflect(object);
                    }
                }
            } else if (obj instanceof Tree) {
                Tree tree = ((Tree) obj);
                if (!ValidateUtil.isEmpty(tree.getId())) {
                    tree.setId(TextUtil.toString(idDecrypt(tree.getId())));
                }
                if (!ValidateUtil.isEmpty(tree.getNodes())) {
                    idDecrypt(tree.getNodes());
                }
            } else if (obj instanceof Map) {
                Map map = ((Map) obj);
                for (Object key : map.keySet()) {
                    if (isEncrypt(key, map.get(key))) {
                        map.put(key, idDecrypt(map.get(key)));
                    }
                }
            } else if (obj instanceof Set) {
                Set set = ((Set) obj);
                for (Object object : set) {
                    object = idDecrypt(obj);
                }
            } else {
                BeanUtil.idDecryptReflect(obj);
            }
        } catch (Exception e) {
            throw new InvalidKeyException("无效的KEY!");
        }
        return obj;
    }

    /**
     * 把一个list转换成另一个list
     *
     * @param list
     * @param c    传入的转换后集合的类型
     * @param <T>  传入的转换后集合的类型
     * @return
     */
    public static <T> List<T> listToList(List<?> list, Class<T> c) {

        if (list == null) {
            return null;
        } else if (list.size() == 0) {
            return (List<T>) list;
        }

        List<T> reusltList = new ArrayList<T>();

        for (Object obj : list) {
            try {
                T newInstance = c.newInstance();
                BeanUtil.copyProperties(obj, newInstance);
                reusltList.add(newInstance);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return reusltList;
    }

    /**
     * 验证key是否模糊存在数组中
     *
     * @param array
     * @param containsKey
     * @return
     */
    public static boolean isContains(String[] array, String containsKey) {
        for (String key : array) {
            if (containsKey.toLowerCase().contains(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否加密
     *
     * @param key
     * @param val
     * @return
     */
    public static boolean isEncrypt(Object key, Object val) {
        if (ValidateUtil.isEmpty(val)) {
            return false;
        }
        return isContains(CONTAINS_ENCRYPT_FIELDS, key.toString()) && !NO_ENCRYPT_FIELDS.containsKey(key.toString()) && MagicValue.JAVA_LANG_STRING.equals(val.getClass().getName());
    }

    public static boolean isEncrypt(Object key, Class<?> type) {
        if (ValidateUtil.isEmpty(type)) {
            return false;
        }
        return isContains(CONTAINS_ENCRYPT_FIELDS, key.toString()) && !NO_ENCRYPT_FIELDS.containsKey(key.toString()) && type == String.class;
    }

    /**
     * 是否加密
     *
     * @param key
     * @param type className
     * @return
     */
    public static boolean isEncrypt(Object key, String type) {
        if (ValidateUtil.isEmpty(type)) {
            return false;
        }
        return isContains(CONTAINS_ENCRYPT_FIELDS, key.toString()) && !NO_ENCRYPT_FIELDS.containsKey(key.toString()) && MagicValue.CLASS_JAVA_LANG_STRING.equals(type);
    }

    public static boolean isEncrypt(Object key, boolean isString) {
        if (!isString) {
            return false;
        }
        return isContains(CONTAINS_ENCRYPT_FIELDS, key.toString()) && !NO_ENCRYPT_FIELDS.containsKey(key.toString());
    }

    /**
     * 吧MAP中的一个参数作为KEY
     *
     * @param list
     * @param key
     * @return
     */
    public static Map<String, String> toMapKey(List<Map<String, Object>> list, String key) {
        if (ValidateUtil.isEmpty(list)) {
            return null;
        }
        Map<String, String> maps = Maps.newHashMapWithExpectedSize(16);
        list.forEach(map -> {
            String nKey = TextUtil.toString(map.get(key));
            if (!ValidateUtil.isEmpty(nKey)) {
                maps.put(nKey, nKey);
            }
        });
        return maps;
    }

    /**
     * 吧map中的一个参数作为KEY 自身作为VALUE
     *
     * @param list
     * @param key
     * @return
     */
    public static Map<String, Map<String, Object>> toMapsKey(List<Map<String, Object>> list, String key) {
        if (ValidateUtil.isEmpty(list)) {
            return null;
        }
        Map<String, Map<String, Object>> maps = Maps.newHashMapWithExpectedSize(16);
        list.forEach(map -> {
            String nKey = TextUtil.toString(map.get(key));
            maps.put(nKey, map);
        });
        return maps;
    }

    /**
     * URL参数转为map
     *
     * @param smUrlParams
     * @return
     */
    public static Map<String, Object> urlParamsToMap(String smUrlParams) {
        //去掉前后参数连接符
        smUrlParams = TextUtil.interceptSymbol(smUrlParams, MagicValue.AND);
        //把URL附带参数解析是否需要加密
        Map<String, Object> paramsMap = Maps.newHashMapWithExpectedSize(16);
        for (String param : smUrlParams.split(MagicValue.AND)) {
            String[] kV = param.split(MagicValue.EQUAL);
            paramsMap.put(kV[0], kV[1]);
        }
        return paramsMap;
    }

    /**
     * map转为URL参数
     *
     * @param map
     * @return
     */
    public static String mapToUrlParams(Map<String, Object> map) {
        StringBuilder builder = new StringBuilder();
        map.keySet().forEach(key -> {
            Object val = map.get(key);
            try {
                builder.append(key + "=" + (isEncrypt(key, val) ? TextUtil.toString(idEncrypt(val)) : TextUtil.toString(val)) + "&");
            } catch (Exception e) {
            }
        });
        return TextUtil.interceptSymbol(builder.toString(), "&");
    }

    /**
     * 连接 URL 和参数
     *
     * @param smUrl
     * @param smUrlParams
     * @return
     */
    public static String getUrlParamsJoin(String smUrl, String smUrlParams) {
        return javaScriptStringEnc(smUrl.contains("?") ? smUrl + smUrlParams : smUrl + "?" + smUrlParams);
    }

    /**
     * 连接菜单参数
     *
     * @param id
     * @param smUrl
     * @param smUrlParams
     * @return
     */
    public static String getMenuUrlJoin(String id, String smUrl, String smUrlParams) {
        smUrl = smUrl.trim();
        if (TextUtil.toString(smUrl).contains(MagicValue.DATA_GRID)) {
            try {
                id = TextUtil.toString(idEncrypt(id));
                smUrl = smUrl.endsWith("/") ? smUrl + id : smUrl + "/" + id;
            } catch (InvalidKeyException e) {
            }
        }
        //额外参数
        if (!ValidateUtil.isEmpty(smUrlParams)) {
            //把MAP转为参数
            smUrlParams = mapToUrlParams(urlParamsToMap(smUrlParams));
            //连接URL和参数
            smUrl = getUrlParamsJoin(smUrl, smUrlParams);
        }
        return smUrl;
    }

    /**
     * 获取按钮地址
     *
     * @param smCode
     * @return
     * @throws InvalidKeyException
     */
    public static String getUrlByMenuCode(String smCode) throws InvalidKeyException {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("SM_CODE", smCode);
        Map<String, Object> menu = commonUtil.menuService.selectMenu(mapParam);

        if (ValidateUtil.isEmpty(menu)) {
            if (SystemEnum.MANAGER.toString().equals(AuthcUtil.getCurrentUser().getType())) {
                return Attribute.MANAGER_404;
            } else {
                return Attribute.RECEPTION_404;
            }
        }

        String id = TextUtil.toString(menu.get("ID"));
        String smUrl = TextUtil.toString(menu.get("SM_URL"));
        String smUrlParams = TextUtil.toString(menu.get("SM_URL_PARAMS")) + "&SM_ID=" + id;

        return getMenuUrlJoin(id, smUrl, smUrlParams);
    }

    /**
     * 防止java值传到js区域太长导致换行
     *
     * @param s
     * @return
     */
    public static String javaScriptStringEnc(String s) {
        int ln = s.length();
        for (int i = 0; i < ln; i++) {
            char c = s.charAt(i);
            if ((c == '"') || (c == '\'') || (c == '\\') || (c == '>') || (c < ' ')) {
                StringBuffer b = new StringBuffer(ln + 4);
                b.append(s.substring(0, i));
                while (true) {
                    if (c == '"') {
                        b.append("\\\"");
                    } else if (c == '\'') {
                        b.append("\\'");
                    } else if (c == '\\') {
                        b.append("\\\\");
                    } else if (c == '>') {
                        b.append("\\>");
                    } else if (c < ' ') {
                        if (c == '\n') {
                            b.append("");
                        } else if (c == '\r') {
                            b.append("");
                        } else if (c == '\f') {
                            b.append("");
                        } else if (c == '\b') {
                            b.append("");
                        } else if (c == '\t') {
                            b.append("");
                        } else {
                            b.append("\\x");
                            int x = c / '\020';
                            b.append((char) (x < 10 ? x + 48 : x - 10 + 65));

                            x = c & 0xF;
                            b.append((char) (x < 10 ? x + 48 : x - 10 + 65));
                        }
                    } else {
                        b.append(c);
                    }
                    i++;
                    if (i >= ln) {
                        return b.toString();
                    }
                    c = s.charAt(i);
                }
            }
        }
        return s;
    }

    /**
     * 吧map中带NAME和TITLE的字段URL转义
     *
     * @param map
     * @return
     */
    public static Map<String, Object> urlEncode(Map<String, Object> map) {
        if (!ValidateUtil.isEmpty(map)) {
            map.keySet().forEach(key -> {
                if (isContains(CONTAINS_URL_PARAMS_FIELDS, key)) {
                    try {
                        map.put(key, URLEncoder.encode(TextUtil.toString(map.get(key)), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                    }
                }
            });
        }
        return map;
    }

    /**
     * 吧map中带NAME和TITLE的字段URL解除转义
     *
     * @param map
     * @return
     */
    public static Map<String, Object> urlDecode(Map<String, Object> map) {
        if (!ValidateUtil.isEmpty(map)) {
            map.keySet().forEach(key -> {
                if (isContains(CONTAINS_URL_PARAMS_FIELDS, key)) {
                    try {
                        map.put(key, URLDecoder.decode(TextUtil.toString(map.get(key)), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                    }
                }
            });
        }

        return map;
    }

}
