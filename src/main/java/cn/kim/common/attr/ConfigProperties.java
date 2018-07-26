package cn.kim.common.attr;

import cn.kim.common.ConfigLoad;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by 余庚鑫 on 2017/10/13.
 * 配置文件
 */
@Component
public class ConfigProperties {
    //服务器路径
    public static String ROOT_PATH = ConfigLoad.ROOT_PATH;
    //数据库名
    public static String DB_USERNAME;
    //数据库密码
    public static String DB_PASSWORD;
    //数据库
    public static String DB_DBNAME;
    //数据库ip
    public static String DB_HOSTIP;
    //淘宝查询IP地址的方法
    public static String TAOBAO_IP;
    /**
     * 上传可以支持类型
     */
    //图片
    public static String[] ALLOW_SUFFIX_IMG;
    //文件
    public static String[] ALLOW_SUFFIX_FILE;
    //文件服务器地址
    public static String AFFIX_FILE_SERVER_URL;
    /**
     * token
     */
    public static String JWT_SECRET;
    //mapper刷新文件
    public static String mappingPath;

    /**
     * 门禁卡接口AES秘钥
     */
    public static String CARD_AES_KEY = "";

    @Value("#{config['jdbc.username']}")
    public void setDbUsername(String dbUsername) {
        DB_USERNAME = dbUsername;
    }

    @Value("#{config['jdbc.password']}")
    public void setDbPassword(String dbPassword) {
        DB_PASSWORD = dbPassword;
    }

    @Value("#{config['jdbc.dbname']}")
    public void setDbDbname(String dbDbname) {
        DB_DBNAME = dbDbname;
    }

    @Value("#{config['jdbc.hostip']}")
    public void setDbHostip(String dbHostip) {
        DB_HOSTIP = dbHostip;
    }

    @Value("#{config['taobao.ip.url']}")
    public void setTaobaoIp(String taobaoIp) {
        TAOBAO_IP = taobaoIp;
    }

    @Value("#{config['allow.suffix.img']}")
    public void setAllowSuffixImg(String allowSuffixImg) {
        ALLOW_SUFFIX_IMG = allowSuffixImg.split(Attribute.SERVICE_SPLIT);
    }

    @Value("#{config['allow.suffix.file']}")
    public void setAllowSuffixFile(String allowSuffixFile) {
        ALLOW_SUFFIX_FILE = allowSuffixFile.split(Attribute.SERVICE_SPLIT);
    }

    @Value("#{config['jwt.secret']}")
    public void setJwtSecret(String jwtSecret) {
        JWT_SECRET = jwtSecret;
    }

    @Value("#{config['affix.file.server.url']}")
    public void setAffixFileServerUrl(String affixFileServerUrl) {
        AFFIX_FILE_SERVER_URL = affixFileServerUrl;
    }

    @Value("#{config['mappingPath']}")
    public static void setMappingPath(String mappingPath) {
        ConfigProperties.mappingPath = mappingPath;
    }

    @Value("#{config['card.aesKey']}")
    public static void setCardAesKey(String cardAesKey) {
        CARD_AES_KEY = cardAesKey;
    }
}
