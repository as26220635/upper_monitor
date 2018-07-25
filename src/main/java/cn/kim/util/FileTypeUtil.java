package cn.kim.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 拿到文件的原始类型
 */
public class FileTypeUtil {

    private final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

    private FileTypeUtil() {
    }

    static {
        getAllFileType();  //初始化文件类型信息  
    }

    private static void getAllFileType() {
        FILE_TYPE_MAP.put("ffd8ffe", "jpg");
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png");
        FILE_TYPE_MAP.put("474946383961", "gif");
        FILE_TYPE_MAP.put("49492a00227105008037", "tif");
        FILE_TYPE_MAP.put("424d", "bmp");
        FILE_TYPE_MAP.put("41433130313500000000", "dwg");
        FILE_TYPE_MAP.put("3c21444f435459504520", "html");
        FILE_TYPE_MAP.put("3c21646f637479706520", "htm");
        FILE_TYPE_MAP.put("48544d4c207b0d0a0942", "css");
        FILE_TYPE_MAP.put("696b2e71623d696b2e71", "js");
        FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf");
        FILE_TYPE_MAP.put("38425053000100000000", "psd");
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml");
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "doc/xls"); // MS Excel、Word、Msi
        //FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "vsd");
        FILE_TYPE_MAP.put("5374616E64617264204A", "mdb");
        FILE_TYPE_MAP.put("25504446", "pdf");
        FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb"); // rmvb、rm
        FILE_TYPE_MAP.put("464c5601050000000900", "flv"); // flv、f4v
        FILE_TYPE_MAP.put("00000020667479706d70", "mp4");
        FILE_TYPE_MAP.put("49443303000000002176", "mp3");
        FILE_TYPE_MAP.put("000001ba210001000180", "mpg");
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv"); // wmv、asf
        FILE_TYPE_MAP.put("52494646e27807005741", "wav");
        FILE_TYPE_MAP.put("52494646d07d60074156", "avi");
        FILE_TYPE_MAP.put("4d546864000000060001", "mid");
        FILE_TYPE_MAP.put("504b0304140000000800", "zip");
        FILE_TYPE_MAP.put("526172211a0700cf9073", "rar");
        FILE_TYPE_MAP.put("235468697320636f6e66", "ini");
        FILE_TYPE_MAP.put("504b03040a0000000000778efe3200", "jar");
        FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");
        FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");
        FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");
        FILE_TYPE_MAP.put("3c3f786d6c2076657273", "xml");
        FILE_TYPE_MAP.put("494e5345525420494e54", "sql");
        FILE_TYPE_MAP.put("7061636b616765207765", "java");
        FILE_TYPE_MAP.put("406563686f206f66660d", "bat");
        FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");
        FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", "Properties");
        FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");
        FILE_TYPE_MAP.put("49545346030000006000", "chm");
        FILE_TYPE_MAP.put("04000000010000001300", "mxp");
        FILE_TYPE_MAP.put("504b0304140006000800", "docx/xlsx");
        FILE_TYPE_MAP.put("504b03040a0000000000874ee24000", "docx");
        FILE_TYPE_MAP.put("6431303a637265617465", "torrent");
        FILE_TYPE_MAP.put("6d6f6f76", "mov");
        FILE_TYPE_MAP.put("ff575043", "wpd");
        FILE_TYPE_MAP.put("cfad12fec5fd746f", "dbx");
        FILE_TYPE_MAP.put("2142444E", "pst");
        FILE_TYPE_MAP.put("ac9ebd8f", "qdf");
        FILE_TYPE_MAP.put("e3828596", "pwl");
        FILE_TYPE_MAP.put("2e7261fd", "ram");
    }

    /**
     * 得到上传文件的文件头
     *
     * @param src
     * @return
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (null == src || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 是否是图片
     *
     * @param multipartFile
     * @return
     */
    public static boolean isImage(MultipartFile multipartFile) {
        Image img = null;
        try {
            img = ImageIO.read(multipartFile.getInputStream());
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            img = null;
        }
    }

    /**
     * 获取文件类型
     *
     * @param multipartFile
     * @return
     */
    public static String getFileType(MultipartFile multipartFile) {
        String res = null;
        InputStream is = null;
        try {
            is = multipartFile.getInputStream();
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            for (int i = 0; i < 15; i++) {
                int a = is.read();
                bytestream.write(a);
            }
            byte[] b = bytestream.toByteArray();
            String fileHead = bytesToHexString(b).toLowerCase();

            Iterator<String> keyIter = FILE_TYPE_MAP.keySet().iterator();
            while (keyIter.hasNext()) {
                String key = keyIter.next().toLowerCase();
                if (key.startsWith(fileHead) || fileHead.startsWith(key)) {
                    res = FILE_TYPE_MAP.get(key);
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

} 
