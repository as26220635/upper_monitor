package cn.kim.util;

import cn.kim.common.attr.AttributePath;
import cn.kim.common.attr.MagicValue;
import com.google.common.collect.Maps;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hslf.usermodel.HSLFAutoShape;
import org.apache.poi.hslf.usermodel.HSLFTable;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.sl.usermodel.SlideShowFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.Color;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/5/30.
 * office 通过poi转为html
 */
public class PoiUtil {

    /**
     * poi office文件转html，支持doc,docx,excel,ppt,pptx
     *
     * @param suffix
     * @param inputStream
     * @return
     */
    public static String officeToHtml(String suffix, InputStream inputStream) {
        try {
            if (checkFile(suffix, "doc")) {
                return wordToHtml03(inputStream);
            } else if (checkFile(suffix, "docx")) {
                return wordToHtml07(inputStream);
            } else if (checkFile(suffix, "xls") || checkFile(suffix, "xlsx")) {
                return excelToHtml(inputStream);
            } else if (checkFile(suffix, "ppt")) {
                return pptToHtml03(inputStream);
            } else if (checkFile(suffix, "pptx")) {
                return pptToHtml07(inputStream);
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Word03 转为 HTML
     *
     * @param inputStream
     * @return
     */
    public static String wordToHtml03(InputStream inputStream) {
        HWPFDocument wordDoc = null;
        WordToHtmlConverter wthc = null;
        try {
            wordDoc = new HWPFDocument(inputStream);
            wthc = new WordToHtmlConverter(
                    DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        } catch (Exception e) {
            return "";
        }
        //html引用图片位置
        wthc.setPicturesManager((bytes, pt, string, f, f1) -> string);
        wthc.processDocument(wordDoc);
        List<Picture> pics = wordDoc.getPicturesTable().getAllPictures();
        fileExists(getImageSavePath());
        if (null != pics && pics.size() > 0) {
            for (Picture pic : pics) {
                try {
                    //生成图片位置
                    pic.writeImageContent(new FileOutputStream(getImageSavePath() + pic.suggestFullFileName()));
                } catch (IOException e) {
                    return "";
                }
            }
        }
        Document htmlDocument = wthc.getDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(out);

        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            return "";
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            }
        }
        String htmlStr = "";
        try {
            htmlStr = new String(out.toString("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return htmlImgTobase64(htmlStr);
    }

    /**
     * Word07 转为 HTML
     *
     * @param inputStream
     * @return
     */
    public static String wordToHtml07(InputStream inputStream) {
        //读取文档内容
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(inputStream);
        } catch (IOException e) {
            return "";
        }

        //加载html页面时图片路径
        XHTMLOptions options = XHTMLOptions.create().URIResolver(new BasicURIResolver(""));
        //图片保存文件夹路径
        fileExists(getImageSavePath());
        options.setExtractor(new FileImageExtractor(new File(getImageSavePath())));
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            XHTMLConverter.getInstance().convert(document, out, options);
            String htmlStr = new String(out.toByteArray());

            return htmlImgTobase64(htmlStr);
        } catch (IOException e) {
            return "";
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            }
        }

    }

    /**
     * excel to html
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static String excelToHtml(InputStream inputStream) throws Exception {
        Workbook wb = WorkbookFactory.create(inputStream);
        if (wb instanceof HSSFWorkbook) {
            HSSFWorkbook hWb = (HSSFWorkbook) wb;
            return getExcelInfo(hWb, true);
        } else {
            XSSFWorkbook xWb = (XSSFWorkbook) wb;
            return getExcelInfo(xWb, true);
        }
    }

    /**
     * ppt03转html
     * filepath:源文件
     * htmlname:生成html名称
     */
    public static String pptToHtml03(InputStream inputStream) {
        SlideShow ppt;
        try {
            ppt = SlideShowFactory.create(inputStream);
        } catch (IOException e) {
            return "";
        }

        Dimension pgsize = ppt.getPageSize();
        List<Slide> slide = ppt.getSlides();
        FileOutputStream out = null;
        String imghtml = "";
        //保存图片位置
        fileExists(getImageSavePath());
        for (int i = 0; i < slide.size(); i++) {
            for (Object o : slide.get(i).getShapes()) {
                if (o instanceof HSLFAutoShape) {
                    HSLFAutoShape shapes = (HSLFAutoShape) o;
                    List<HSLFTextParagraph> list = shapes.getTextParagraphs();
                    for (HSLFTextParagraph hslfTextRuns : list) {
                        for (HSLFTextRun hslfTextRun : hslfTextRuns.getTextRuns()) {
                            hslfTextRun.setFontFamily("宋体");
                        }
                    }
                } else if (o instanceof HSLFTable) {
                    HSLFTable hslfTable = (HSLFTable) o;
                    int rowSize = hslfTable.getNumberOfRows();
                    int columnSize = hslfTable.getNumberOfColumns();
                    for (int j = 0; j < rowSize; j++) {
                        for (int k = 0; k < columnSize; k++) {
                            for (int l = 0; l < hslfTable.getCell(j, k).getTextParagraphs().size(); l++) {
                                HSLFTextParagraph hslfTextRuns = hslfTable.getCell(j, k).getTextParagraphs().get(l);
                                for (int m = 0; m < hslfTextRuns.getTextRuns().size(); m++) {
                                    HSLFTextRun textRun = hslfTextRuns.getTextRuns().get(m);
                                    //todo 设置字体失败，输出html依旧会乱码
                                    textRun.setFontFamily("宋体");
                                }
                            }
                        }
                    }
                }
            }
            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics = img.createGraphics();
            graphics.setPaint(Color.BLUE);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
            slide.get(i).draw(graphics);
            //设置图片存放位置
            String imgPath = getImageSavePath() + TextUtil.toUUID() + ".jpeg";
            try {
                out = new FileOutputStream(imgPath);
                javax.imageio.ImageIO.write(img, "jpeg", out);

                //转为base64图片
                imghtml += "<img src=\"data:image/jpeg;base64," + ImageUtil.imgToBase64(imgPath) + "\" style=\'width:100%;height:auto;vertical-align:text-bottom;\'><br><br><br><br>";
                FileUtil.deleteFile(imgPath);
            } catch (IOException e) {
                return "";
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e1) {
                    }
                }
                FileUtil.deleteFile(imgPath);
            }
        }
        try {
            DOMSource domSource = new DOMSource();
            StreamResult streamResult = new StreamResult();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
        } catch (Exception e) {
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                }
            }
        }

        String ppthtml = "<html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>" + imghtml + "</body></html>";
        return ppthtml;
    }

    /**
     * ppt07转html
     * filepath:源文件
     * outputFile:生成html名称
     */
    public static String pptToHtml07(InputStream inputStream) {
        SlideShow ppt;
        try {
            ppt = SlideShowFactory.create(inputStream);
        } catch (IOException e) {
            return "";
        }
        Dimension pgsize = ppt.getPageSize();
        List<XSLFSlide> pptPageXSLFSLiseList = ppt.getSlides();
        FileOutputStream out = null;
        String imghtml = "";
        //保存图片位置
        fileExists(getImageSavePath());
        for (int i = 0; i < pptPageXSLFSLiseList.size(); i++) {
            for (XSLFShape shape : pptPageXSLFSLiseList.get(i).getShapes()) {
                //设置文字字体
                if (shape instanceof XSLFTextShape) {
                    XSLFTextShape tsh = (XSLFTextShape) shape;
                    for (XSLFTextParagraph p : tsh) {
                        for (XSLFTextRun r : p) {
                            r.setFontFamily("宋体");
                        }
                    }
                    //设置表格字体
                } else if (shape instanceof XSLFTable) {
                    XSLFTable table = (XSLFTable) shape;
                    int rowSize = table.getNumberOfRows();
                    int columnSize = table.getNumberOfColumns();
                    for (int j = 0; j < rowSize; j++) {
                        for (int k = 0; k < columnSize; k++) {
                            for (int l = 0; l < table.getCell(j, k).getTextParagraphs().size(); l++) {
                                XSLFTextParagraph xslfTextRuns = table.getCell(j, k).getTextParagraphs().get(l);
                                for (int m = 0; m < xslfTextRuns.getTextRuns().size(); m++) {
                                    xslfTextRuns.getTextRuns().get(m).setFontFamily("宋体");
                                }
                            }
                        }
                    }
                }
            }
            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();
            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
            pptPageXSLFSLiseList.get(i).draw(graphics);
            //设置图片存放位置
            String imgPath = getImageSavePath() + TextUtil.toUUID() + ".jpg";
            try {
                out = new FileOutputStream(imgPath);
                javax.imageio.ImageIO.write(img, "jpg", out);
                //转为base64图片
                imghtml += "<img src=\"data:image/jpg;base64," + ImageUtil.imgToBase64(imgPath) + "\" style=\'width:100%;height:auto;vertical-align:text-bottom;\'><br><br><br><br>";
            } catch (IOException e) {
                return "";
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e1) {
                    }
                }
                FileUtil.deleteFile(imgPath);
            }

        }

        DOMSource domSource = new DOMSource();
        StreamResult streamResult = new StreamResult();
        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
        } catch (Exception e) {
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                }
            }
        }

        String ppthtml = "<html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>" + imghtml + "</body></html>";
        return ppthtml;
    }

    public static String getExcelInfo(Workbook wb, boolean isWithStyle) {

        StringBuffer sb = new StringBuffer();
        Sheet sheet = wb.getSheetAt(0);//获取第一个Sheet的内容
        int lastRowNum = sheet.getLastRowNum();
        Map<String, String>[] map = getRowSpanColSpanMap(sheet);
        sb.append("<table style='border-collapse:collapse;' width='100%'>");
        Row row = null;        //兼容
        Cell cell = null;    //兼容

        for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {
            row = sheet.getRow(rowNum);
            if (row == null) {
                sb.append("<tr><td > &nbsp;</td></tr>");
                continue;
            }
            sb.append("<tr>");
            int lastColNum = row.getLastCellNum();
            for (int colNum = 0; colNum < lastColNum; colNum++) {
                cell = row.getCell(colNum);
                if (cell == null) {    //特殊情况 空白的单元格会返回null
                    sb.append("<td>&nbsp;</td>");
                    continue;
                }

                String stringValue = getCellValue(cell);
                if (map[0].containsKey(rowNum + "," + colNum)) {
                    String pointString = map[0].get(rowNum + "," + colNum);
                    map[0].remove(rowNum + "," + colNum);
                    int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
                    int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
                    int rowSpan = bottomeRow - rowNum + 1;
                    int colSpan = bottomeCol - colNum + 1;
                    sb.append("<td rowspan= '" + rowSpan + "' colspan= '" + colSpan + "' ");
                } else if (map[1].containsKey(rowNum + "," + colNum)) {
                    map[1].remove(rowNum + "," + colNum);
                    continue;
                } else {
                    sb.append("<td ");
                }

                //判断是否需要样式
                if (isWithStyle) {
                    dealExcelStyle(wb, sheet, cell, sb);//处理单元格样式
                }

                sb.append(">");
                if (stringValue == null || "".equals(stringValue.trim())) {
                    sb.append(" &nbsp; ");
                } else {
                    // 将ascii码为160的空格转换为html下的空格（&nbsp;）
                    sb.append(stringValue.replace(String.valueOf((char) 160), "&nbsp;"));
                }
                sb.append("</td>");
            }
            sb.append("</tr>");
        }

        sb.append("</table>");
        return sb.toString();
    }

    private static Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {

        Map<String, String> map0 =  Maps.newHashMapWithExpectedSize(16);
        Map<String, String> map1 =  Maps.newHashMapWithExpectedSize(16);
        int mergedNum = sheet.getNumMergedRegions();
        CellRangeAddress range = null;
        for (int i = 0; i < mergedNum; i++) {
            range = sheet.getMergedRegion(i);
            int topRow = range.getFirstRow();
            int topCol = range.getFirstColumn();
            int bottomRow = range.getLastRow();
            int bottomCol = range.getLastColumn();
            map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);
            // System.out.println(topRow + "," + topCol + "," + bottomRow + "," + bottomCol);
            int tempRow = topRow;
            while (tempRow <= bottomRow) {
                int tempCol = topCol;
                while (tempCol <= bottomCol) {
                    map1.put(tempRow + "," + tempCol, "");
                    tempCol++;
                }
                tempRow++;
            }
            map1.remove(topRow + "," + topCol);
        }
        Map[] map = {map0, map1};
        return map;
    }


    /**
     * 获取表格单元格Cell内容
     *
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell) {

        String result = new String();
        switch (cell.getCellType()) {
            // 数字类型
            case Cell.CELL_TYPE_NUMERIC:
                // 处理日期格式、时间格式
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = null;
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {// 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date date = cell.getDateCellValue();
                    result = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date date = DateUtil
                            .getJavaDate(value);
                    result = sdf.format(date);
                } else {
                    double value = cell.getNumericCellValue();
                    CellStyle style = cell.getCellStyle();
                    DecimalFormat format = new DecimalFormat();
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if (MagicValue.GENERAL.equals(temp)) {
                        format.applyPattern("#");
                    }
                    result = format.format(value);
                }
                break;
            // String类型
            case Cell.CELL_TYPE_STRING:
                result = cell.getRichStringCellValue().toString();
                break;
            case Cell.CELL_TYPE_BLANK:
                result = "";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    /**
     * 处理表格样式
     *
     * @param wb
     * @param sheet
     * @param cell
     * @param sb
     */
    private static void dealExcelStyle(Workbook wb, Sheet sheet, Cell cell, StringBuffer sb) {

        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle != null) {
            short alignment = cellStyle.getAlignment();
            //单元格内容的水平对齐方式
            sb.append("align='" + convertAlignToHtml(alignment) + "' ");
            short verticalAlignment = cellStyle.getVerticalAlignment();
            //单元格中内容的垂直排列方式
            sb.append("valign='" + convertVerticalAlignToHtml(verticalAlignment) + "' ");

            if (wb instanceof XSSFWorkbook) {

                XSSFFont xf = ((XSSFCellStyle) cellStyle).getFont();
                short boldWeight = xf.getBoldweight();
                sb.append("style='");
                // 字体加粗
                sb.append("font-weight:" + boldWeight + ";");
                // 字体大小
                sb.append("font-size: " + xf.getFontHeight() / 2 + "%;");
                int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
                sb.append("width:" + columnWidth + "px;");

                XSSFColor xc = xf.getXSSFColor();
                if (xc != null && !"".equals(xc)) {
                    // 字体颜色
                    sb.append("color:#" + xc.getARGBHex().substring(2) + ";");
                }

                XSSFColor bgColor = (XSSFColor) cellStyle.getFillForegroundColorColor();
                //System.out.println("************************************");
                //System.out.println("BackgroundColorColor: "+cellStyle.getFillBackgroundColorColor());
                //System.out.println("ForegroundColor: "+cellStyle.getFillForegroundColor());//0
                //System.out.println("BackgroundColorColor: "+cellStyle.getFillBackgroundColorColor());
                //System.out.println("ForegroundColorColor: "+cellStyle.getFillForegroundColorColor());
                //String bgColorStr = bgColor.getARGBHex();
                //System.out.println("bgColorStr: "+bgColorStr);
                if (bgColor != null && !"".equals(bgColor)) {
                    sb.append("background-color:#" + bgColor.getARGBHex().substring(2) + ";"); // 背景颜色
                }
                sb.append(getBorderStyle(0, cellStyle.getBorderTop(), ((XSSFCellStyle) cellStyle).getTopBorderXSSFColor()));
                sb.append(getBorderStyle(1, cellStyle.getBorderRight(), ((XSSFCellStyle) cellStyle).getRightBorderXSSFColor()));
                sb.append(getBorderStyle(2, cellStyle.getBorderBottom(), ((XSSFCellStyle) cellStyle).getBottomBorderXSSFColor()));
                sb.append(getBorderStyle(3, cellStyle.getBorderLeft(), ((XSSFCellStyle) cellStyle).getLeftBorderXSSFColor()));

            } else if (wb instanceof HSSFWorkbook) {

                HSSFFont hf = ((HSSFCellStyle) cellStyle).getFont(wb);
                short boldWeight = hf.getBoldweight();
                short fontColor = hf.getColor();
                sb.append("style='");
                // 类HSSFPalette用于求的颜色的国际标准形式
                HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette();
                HSSFColor hc = palette.getColor(fontColor);
                // 字体加粗
                sb.append("font-weight:" + boldWeight + ";");
                // 字体大小
                sb.append("font-size: " + hf.getFontHeight() / 2 + "%;");
                String fontColorStr = convertToStardColor(hc);
                if (fontColorStr != null && !"".equals(fontColorStr.trim())) {
                    // 字体颜色
                    sb.append("color:" + fontColorStr + ";");
                }
                int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
                sb.append("width:" + columnWidth + "px;");
                short bgColor = cellStyle.getFillForegroundColor();
                hc = palette.getColor(bgColor);
                String bgColorStr = convertToStardColor(hc);
                if (bgColorStr != null && !"".equals(bgColorStr.trim())) {
                    // 背景颜色
                    sb.append("background-color:" + bgColorStr + ";");
                }
                sb.append(getBorderStyle(palette, 0, cellStyle.getBorderTop(), cellStyle.getTopBorderColor()));
                sb.append(getBorderStyle(palette, 1, cellStyle.getBorderRight(), cellStyle.getRightBorderColor()));
                sb.append(getBorderStyle(palette, 3, cellStyle.getBorderLeft(), cellStyle.getLeftBorderColor()));
                sb.append(getBorderStyle(palette, 2, cellStyle.getBorderBottom(), cellStyle.getBottomBorderColor()));
            }

            sb.append("' ");
        }
    }

    /**
     * 单元格内容的水平对齐方式
     *
     * @param alignment
     * @return
     */
    private static String convertAlignToHtml(short alignment) {

        String align = "left";
        switch (alignment) {
            case CellStyle.ALIGN_LEFT:
                align = "left";
                break;
            case CellStyle.ALIGN_CENTER:
                align = "center";
                break;
            case CellStyle.ALIGN_RIGHT:
                align = "right";
                break;
            default:
                break;
        }
        return align;
    }

    /**
     * 单元格中内容的垂直排列方式
     *
     * @param verticalAlignment
     * @return
     */
    private static String convertVerticalAlignToHtml(short verticalAlignment) {

        String valign = "middle";
        switch (verticalAlignment) {
            case CellStyle.VERTICAL_BOTTOM:
                valign = "bottom";
                break;
            case CellStyle.VERTICAL_CENTER:
                valign = "center";
                break;
            case CellStyle.VERTICAL_TOP:
                valign = "top";
                break;
            default:
                break;
        }
        return valign;
    }

    private static String convertToStardColor(HSSFColor hc) {

        StringBuffer sb = new StringBuffer("");
        if (hc != null) {
            if (HSSFColor.AUTOMATIC.index == hc.getIndex()) {
                return null;
            }
            sb.append("#");
            for (int i = 0; i < hc.getTriplet().length; i++) {
                sb.append(fillWithZero(Integer.toHexString(hc.getTriplet()[i])));
            }
        }

        return sb.toString();
    }

    private static String fillWithZero(String str) {
        if (str != null && str.length() < 2) {
            return "0" + str;
        }
        return str;
    }

    static String[] bordesr = {"border-top:", "border-right:", "border-bottom:", "border-left:"};
    static String[] borderStyles = {"solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid", "solid", "solid", "solid", "solid"};

    private static String getBorderStyle(HSSFPalette palette, int b, short s, short t) {

        if (s == 0) {
            return bordesr[b] + borderStyles[s] + "#d0d7e5 1px;";
        }
        String borderColorStr = convertToStardColor(palette.getColor(t));
        borderColorStr = borderColorStr == null || borderColorStr.length() < 1 ? "#000000" : borderColorStr;
        return bordesr[b] + borderStyles[s] + borderColorStr + " 1px;";

    }

    private static String getBorderStyle(int b, short s, XSSFColor xc) {

        if (s == 0) {
            return bordesr[b] + borderStyles[s] + "#d0d7e5 1px;";
        }
        ;
        if (xc != null && !"".equals(xc)) {
            String borderColorStr = xc.getARGBHex();//t.getARGBHex();
            borderColorStr = borderColorStr == null || borderColorStr.length() < 1 ? "#000000" : borderColorStr.substring(2);
            return bordesr[b] + borderStyles[s] + borderColorStr + " 1px;";
        }

        return "";
    }

    /**
     * 输出文件流
     *
     * @param content
     * @param path
     * @return
     */
    public static boolean writeFile(String content, String path) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;

        File file = new File(path);

        try {
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            bw.write(content);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (null != bw) {
                    bw.close();
                }
                if (null != fos) {
                    fos.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 吧html中的图片转为base64
     *
     * @param htmlStr
     * @return
     */
    private static String htmlImgTobase64(String htmlStr) {
        org.jsoup.nodes.Document jsoupDocument = Jsoup.parse(htmlStr);
        jsoupDocument.select("img").forEach(img -> {
            String localPath = getImageSavePath() + img.attr("src");
            try {
                img.attr("src", "data:image/jpg;base64," + ImageUtil.imgToBase64(localPath));
            } catch (Exception e) {
            } finally {
                FileUtil.deleteFile(localPath);
            }
        });
        jsoupDocument.select("a").forEach(a -> {
            a.attr("href", "javascript:void(0);");
        });
        return jsoupDocument.html();
    }

    /**
     * 判断文件夹是否存在，不存在则新建
     *
     * @param path
     */
    private static void fileExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 检查文件类型
     *
     * @param suffix
     * @return
     */
    public static boolean checkFile(String suffix, String type) {
        boolean flag = false;
        if (suffix != null && suffix.endsWith(type)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据文件全路径获取文件所在路径
     *
     * @param fileFullName
     * @return
     */
    public static String getFilePath(String fileFullName) {
        File file = new File(fileFullName);
        String filePath = fileFullName.replace(file.getName(), "");
        return filePath;
    }


    private static String getImageUrl() {
        return AttributePath.FILE_PREVIEW_URL;
    }

    private static String getImageSavePath() {
        return AttributePath.SERVICE_PATH_DEFAULT;
    }

}