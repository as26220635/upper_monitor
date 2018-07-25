package cn.kim.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

/**
 * Created by 余庚鑫 on 2017/3/5.
 */
public class ImageUtil {
    /**
     * 截图工具，根据截取的比例进行缩放裁剪
     *
     * @param path        图片路径
     * @param zoomX       缩放后的X坐标
     * @param zoomY       缩放后的Y坐标
     * @param zoomW       缩放后的截取宽度
     * @param zoomH       缩放后的截取高度
     * @param scaleWidth  缩放后图片的宽度
     * @param scaleHeight 缩放后的图片高度
     * @return 是否成功
     * @throws IOException
     */
    public static boolean imgCut(String path, int zoomX, int zoomY, int zoomW,
                                 int zoomH, int scaleWidth, int scaleHeight) throws IOException {
        Image img;
        ImageFilter cropFilter;
        BufferedImage bi = ImageIO.read(new File(path));
        int fileWidth = bi.getWidth();
        int fileHeight = bi.getHeight();
        double scale = (double) fileWidth / (double) scaleWidth;

        double realX = zoomX * scale;
        double realY = zoomY * scale;
        double realW = zoomW * scale;
        double realH = zoomH * scale;

        if (fileWidth >= realW && fileHeight >= realH) {
            Image image = bi.getScaledInstance(fileWidth, fileHeight, Image.SCALE_DEFAULT);
            cropFilter = new CropImageFilter((int) realX, (int) realY, (int) realW, (int) realH);
            img = Toolkit.getDefaultToolkit().createImage(
                    new FilteredImageSource(image.getSource(), cropFilter));
            BufferedImage bufferedImage = new BufferedImage((int) realW, (int) realH, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            //输出文件
            return ImageIO.write(bufferedImage, "JPEG", new File(path));
        } else {
            return true;
        }
    }

    /**
     * 添加图片水印
     * 可以过滤正常图片恶意代码
     *
     * @param srcImg   目标图片路径，如：C:\\kutuku.jpg
     * @param waterImg 水印图片路径，如：C:\\kutuku.png
     * @param x        水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y        水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha    透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     * @throws IOException
     */
    public static void addWaterMark(String srcImg, String waterImg, int x, int y, float alpha) throws IOException {
        // 加载目标图片
        File file = new File(srcImg);
        String ext = srcImg.substring(srcImg.lastIndexOf(".") + 1);
        Image image = ImageIO.read(file);
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        // 将目标图片加载到内存。
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);

        // 加载水印图片。
        Image waterImage = ImageIO.read(new File(waterImg));
        int widthOne = waterImage.getWidth(null);
        int heightOne = waterImage.getHeight(null);
        // 设置水印图片的透明度。
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

        // 设置水印图片的位置。
        int widthDiff = width - widthOne;
        int heightDiff = height - heightOne;
        if (x < 0) {
            x = widthDiff / 2;
        } else if (x > widthDiff) {
            x = widthDiff;
        }
        if (y < 0) {
            y = heightDiff / 2;
        } else if (y > heightDiff) {
            y = heightDiff;
        }

        // 将水印图片“画”在原有的图片的制定位置。
        g.drawImage(waterImage, x, y, widthOne, heightOne, null);
        // 关闭画笔。
        g.dispose();

        // 保存目标图片。
        ImageIO.write(bufferedImage, ext, file);
    }

    /**
     * 将图片转换成Base64编码
     *
     * @param imgPath 待处理图片
     * @return
     */
    public static String imgToBase64(String imgPath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    /**
     * 将图片转换成Base64编码
     *
     * @param data
     * @return
     */
    public static String imgToBase64(byte[] data) {
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }
}
