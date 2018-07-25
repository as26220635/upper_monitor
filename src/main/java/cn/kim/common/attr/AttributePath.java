package cn.kim.common.attr;

import java.io.File;

/**
 * Created by 余庚鑫 on 2017/3/13.
 */
public class AttributePath {
    /**
     * 文件访问地址
     */
    public static final String FILE_URL = "file/";
    public static final String FILE_DOWNLOAD_URL = FILE_URL + "download/";
    /**
     * 图片删除地址
     */
    public static final String FILE_DEL_URL = FILE_URL + "del/";
    /**
     * 图片访问地址
     */
    public static final String FILE_PREVIEW_URL = FILE_URL + "preview/";
    /**
     * 图片上传地址
     */
    public static final String FILE_UPLOAD_URL = FILE_URL + "upload";
    public static final String FILE_UPLOAD_TEXTAREA_URL = FILE_URL + "uploadTextarea";
    /**
     * office预览访问地址
     */
    public static final String FILE_OFFICE_URL = FILE_URL + "office/";
    /**
     * 服务器文件路径斜杠
     */
    public static final String SERVICE_PATH_SEPARATOR = File.separator;
    /**
     * 服务器真实初始路径
     */
    public static final String SERVICE_PATH_DEFAULT = System.getProperty("user.dir") + SERVICE_PATH_SEPARATOR + "cache" + SERVICE_PATH_SEPARATOR;

    /***************    MYSQL备份文件夹     **************/
    public static final String SERVICE_MYSQL_BACKUP_PATH = SERVICE_PATH_DEFAULT + "mysqlBackup";

}
