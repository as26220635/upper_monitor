package cn.kim.controller;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.attr.MagicValue;
import cn.kim.common.eu.UseType;
import cn.kim.controller.manager.BaseController;
import cn.kim.entity.ActiveUser;
import cn.kim.entity.CxfFileWrapper;
import cn.kim.entity.CxfState;
import cn.kim.exception.CustomException;
import cn.kim.service.FileService;
import cn.kim.util.*;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 余庚鑫 on 2018/5/28.
 * 查看下载文件都放在这里
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    private FileService fileService;

    /**
     * 预览图片
     *
     * @param ID
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/preview/{ID}")
    public void preview(@PathVariable("ID") String ID, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");

        OutputStream os = null;
        InputStream is = null;
        byte[] b = null;
        BufferedInputStream bis = null;
        InputStream inputStream = null;
        try {
            Map<String, Object> file = fileService.selectFile(ID);

            if (ValidateUtil.isEmpty(file)) {
                throw new CustomException("没有找到文件");
            }
            ActiveUser activeUser = activeUser();

            String SO_ID = TextUtil.toString(file.get("SO_ID"));
            String SF_NAME = TextUtil.toString(file.get("SF_NAME"));
            String SF_TABLE_NAME = TextUtil.toString(file.get("SF_TABLE_NAME"));
            String SDT_ROLE_DOWN = TextUtil.toString(file.get("SDT_ROLE_DOWN"));
            Integer SF_SEE_TYPE = TextUtil.toInt(file.get("SF_SEE_TYPE"));
            String SF_PATH = TextUtil.toString(file.get("SF_PATH"));

            //判断权限
            if (SF_SEE_TYPE == STATUS_SUCCESS && (isEmpty(activeUser) ? true : !activeUser.getId().equals(SO_ID) || !containsRole(SDT_ROLE_DOWN))) {
                throw new UnauthorizedException("你没有预览这个文件的权限!");
            }

            CxfState cxfState = FileUtil.isCxfOnline();
            //服务器是否在线
            if (!cxfState.isOnline()) {
                throw new CustomException("服务器连接失败");
            }
            CxfFileWrapper downloadFile = FileUtil.getCxfFileWrapper(cxfState.getUrl(), TokenUtil.baseKey(ID, SF_TABLE_NAME), SF_NAME, SF_PATH);
            //判断是否成功
            if (downloadFile == null || downloadFile.getFileToken() == null || MagicValue.FALSE.equals(downloadFile.getFileToken())) {
                throw new CustomException("没有找到文件");
            }
            //获取文件流
            inputStream = downloadFile.getFile().getInputStream();

            byte[] bt = FileUtil.toByteArray(inputStream);

            InputStream ins = new ByteArrayInputStream(bt);
            bis = new BufferedInputStream(ins);
            b = new byte[1024];
            int len = 0;
            os = response.getOutputStream();
            while ((len = bis.read(b)) != -1) {
                os.write(b, 0, len);
            }
            os.flush();
        } catch (Exception e) {
            if (e instanceof UnauthorizedException) {
                throw e;
            }
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e1) {
            }
        }
    }

    /**
     * 图片上传
     *
     * @param request
     * @param SF_TABLE_ID
     * @param SF_TABLE_NAME 表名
     * @param SF_TYPE_CODE  typecode
     * @param SF_SEE_TYPE   是否可以查看
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public JSONObject upload(HttpServletRequest request, String SF_TABLE_ID, String SF_TABLE_NAME, String SF_TYPE_CODE, String SF_SEE_TYPE, String SF_SDT_CODE, String SF_SDI_CODE) throws IOException {
        JSONObject json = new JSONObject();
        try {
            if (isEmpty(activeUser())) {
                throw new CustomException("未登录不允许上传文件!");
            }
            MultipartFile imgUpload = CommonUtil.getMultipartFile(request);

            Map<String, Object> configure = Maps.newHashMapWithExpectedSize(7);
            configure.put("SF_TABLE_ID", SF_TABLE_ID);
            configure.put("SF_TABLE_NAME", toString(CommonUtil.idDecrypt(SF_TABLE_NAME)));
            configure.put("SF_TYPE_CODE", toString(CommonUtil.idDecrypt(SF_TYPE_CODE)));
            configure.put("SF_SEE_TYPE", toString(CommonUtil.idDecrypt(SF_SEE_TYPE)));
            configure.put("SF_SDT_CODE", toString(CommonUtil.idDecrypt(SF_SDT_CODE)));
            configure.put("SF_SDI_CODE", toString(CommonUtil.idDecrypt(SF_SDI_CODE)));

            Map<String, Object> result = FileUtil.saveFile(imgUpload, configure);

            if (!result.get("code").equals(STATUS_SUCCESS)) {
                json.put("error", result.get("message"));
            }
        } catch (Exception e) {
            json.put("error", "上传文件失败,请重试!");
        }

        return json;
    }

    /**
     * 富文本图片上传
     *
     * @param SF_TABLE_ID
     * @param SF_TABLE_NAME
     * @param SF_TYPE_CODE
     * @param SF_SEE_TYPE
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadTextarea")
    @ResponseBody
    public String uploadTextarea(String SF_TABLE_ID, String SF_TABLE_NAME, String SF_TYPE_CODE, String SF_SEE_TYPE, HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        try {
            if (isEmpty(activeUser())) {
                throw new CustomException("未登录不允许上传文件!");
            }

            MultipartFile imgUpload = CommonUtil.getMultipartFile(request);

            Map<String, Object> configure = Maps.newHashMapWithExpectedSize(6);
            configure.put("SF_TABLE_ID", toString(CommonUtil.idDecrypt(SF_TABLE_ID)));
            configure.put("SF_TABLE_NAME", toString(CommonUtil.idDecrypt(SF_TABLE_NAME)));
            configure.put("SF_TYPE_CODE", toString(CommonUtil.idDecrypt(SF_TYPE_CODE)));
            configure.put("SF_SEE_TYPE", toString(CommonUtil.idDecrypt(SF_SEE_TYPE)));

            Map<String, Object> result = FileUtil.saveImgFile(imgUpload, configure);

            if (result.get("code").equals(STATUS_SUCCESS)) {
                json.put("code", STATUS_SUCCESS);
                json.put("defaultUrl", result.get("encryptId"));
                json.put("originName", result.get("originName"));
            } else {
                json.put("code", result.get("code"));
                json.put("message", result.get("message"));
            }
        } catch (Exception e) {
            json.put("code", STATUS_ERROR);
            json.put("message", "上传文件失败,请重试!");
        }
        return json.toString();
    }

    /**
     * 下载文件
     *
     * @param ID
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/download/{ID}")
    public ResponseEntity<byte[]> download(@PathVariable("ID") String ID, HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream = null;
        try {
            Map<String, Object> file = fileService.selectFile(ID);

            if (ValidateUtil.isEmpty(file)) {
                throw new CustomException("没有找到文件");
            }
            ActiveUser activeUser = activeUser();

            String SO_ID = TextUtil.toString(file.get("SO_ID"));
            String SF_NAME = TextUtil.toString(file.get("SF_NAME"));
            String SF_TABLE_NAME = TextUtil.toString(file.get("SF_TABLE_NAME"));
            String SF_ORIGINAL_NAME = TextUtil.toString(file.get("SF_ORIGINAL_NAME"));
            String SDT_ROLE_DOWN = TextUtil.toString(file.get("SDT_ROLE_DOWN"));
            Integer SF_SEE_TYPE = TextUtil.toInt(file.get("SF_SEE_TYPE"));
            String SF_PATH = TextUtil.toString(file.get("SF_PATH"));

            //判断权限
            if (SF_SEE_TYPE == STATUS_ERROR && (isEmpty(activeUser) ? true : !activeUser.getId().equals(SO_ID) || !containsRole(SDT_ROLE_DOWN))) {
                throw new UnauthorizedException("你没有下载这个文件的权限!");
            }

            CxfState cxfState = FileUtil.isCxfOnline();
            //服务器是否在线
            if (!cxfState.isOnline()) {
                throw new CustomException("服务器连接失败");
            }
            CxfFileWrapper downloadFile = FileUtil.getCxfFileWrapper(cxfState.getUrl(), TokenUtil.baseKey(ID, SF_TABLE_NAME), SF_NAME, SF_PATH);
            //判断是否成功
            if (downloadFile == null || downloadFile.getFileToken() == null || MagicValue.FALSE.equals(downloadFile.getFileToken())) {
                throw new CustomException("没有找到文件");
            }
            //获取缓存文件
            inputStream = downloadFile.getFile().getInputStream();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData(SF_TABLE_NAME, new String(SF_ORIGINAL_NAME.getBytes("UTF-8"), "ISO8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtil.toByteArray(inputStream), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            if (e instanceof UnauthorizedException) {
                throw e;
            }
            return null;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 删除文件
     *
     * @param key      id
     * @param title    删除文件来源
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/del")
    @SystemControllerLog(useType = UseType.USE, event = "删除附件")
    @ResponseBody
    public JSONObject del(String key, String title, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", STATUS_ERROR);

        ActiveUser activeUser = activeUser();

        Map<String, Object> file = fileService.selectFile(key);

        if (ValidateUtil.isEmpty(file)) {
            return null;
        }

        String SO_ID = TextUtil.toString(file.get("SO_ID"));
        String SF_NAME = TextUtil.toString(file.get("SF_NAME"));
        String SF_TABLE_NAME = TextUtil.toString(file.get("SF_TABLE_NAME"));
        String SF_ORIGINAL_NAME = TextUtil.toString(file.get("SF_ORIGINAL_NAME"));
        String SDT_ROLE_DEL = TextUtil.toString(file.get("SDT_ROLE_DEL"));
        String SF_PATH = TextUtil.toString(file.get("SF_PATH"));

        //判断权限
        if (isEmpty(activeUser) || !activeUser.getId().equals(SO_ID) || !containsRole(SDT_ROLE_DEL)) {
            throw new UnauthorizedException("你没有删除这个文件的权限!");
        }

        try {
            CxfState cxfState = FileUtil.isCxfOnline();
            //服务器是否在线
            if (!cxfState.isOnline()) {
                return null;
            }

            boolean isSuccess = FileUtil.delServerFile(cxfState.getUrl(), TokenUtil.baseKey(key, SF_TABLE_NAME), SF_NAME, SF_PATH);

            if (isSuccess) {
                Map<String, Object> resultMap = fileService.deleteFile(key);

                resultJson.put("code", STATUS_SUCCESS);
                resultJson.put("logMessage", "删除文件:" + SF_ORIGINAL_NAME + ",来源:" + title + ",成功!");
                return resultJson;
            }
        } catch (Exception e) {
        }

        resultJson.put("code", STATUS_ERROR);
        resultJson.put("logMessage", "删除文件:" + SF_ORIGINAL_NAME + ",失败!");
        resultJson.put("error", "删除文件:" + SF_ORIGINAL_NAME + ",失败!");
        return resultJson;
    }

    /**
     * OFFICE预览
     *
     * @param ID
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/office/{ID}")
    public String fileInputTest(@PathVariable("ID") String ID, Model model) throws Exception {
        InputStream inputStream = null;
        try {
            Map<String, Object> file = fileService.selectFile(ID);

            if (ValidateUtil.isEmpty(file)) {
                throw new CustomException("没有找到文件");
            }
            ActiveUser activeUser = activeUser();

            String SO_ID = TextUtil.toString(file.get("SO_ID"));
            String SF_NAME = TextUtil.toString(file.get("SF_NAME"));
            String SF_NAME_OFFICE = TextUtil.toString(file.get("SF_NAME")) + "OFFICE";
            String SF_TABLE_NAME = TextUtil.toString(file.get("SF_TABLE_NAME"));
            String SDT_ROLE_DOWN = TextUtil.toString(file.get("SDT_ROLE_DOWN"));
            Integer SF_SEE_TYPE = TextUtil.toInt(file.get("SF_SEE_TYPE"));
            String SF_PATH = TextUtil.toString(file.get("SF_PATH"));
            String SF_SUFFIX = TextUtil.toString(file.get("SF_SUFFIX"));

            //判断权限
            if (SF_SEE_TYPE == STATUS_ERROR && (isEmpty(activeUser) ? true : !activeUser.getId().equals(SO_ID) || !containsRole(SDT_ROLE_DOWN))) {
                throw new UnauthorizedException("你没有预览这个文件的权限!");
            }

            CxfState cxfState = FileUtil.isCxfOnline();
            //服务器是否在线
            if (!cxfState.isOnline()) {
                throw new CustomException("服务器连接失败");
            }
            //查找缓存OFFICE文件
            CxfFileWrapper downloadFile = FileUtil.getCxfFileWrapper(cxfState.getUrl(), TokenUtil.baseKey(ID, SF_TABLE_NAME), SF_NAME_OFFICE, SF_PATH);
            //判断是否成功
            if (downloadFile == null || downloadFile.getFileToken() == null || MagicValue.FALSE.equals(downloadFile.getFileToken())) {
                //读取原文件转换为OFFICE文件上传文件服务器
                downloadFile = FileUtil.getCxfFileWrapper(cxfState.getUrl(), TokenUtil.baseKey(ID, SF_TABLE_NAME), SF_NAME, SF_PATH);
                //判断是否成功
                if (downloadFile == null || downloadFile.getFileToken() == null || MagicValue.FALSE.equals(downloadFile.getFileToken())) {
                    throw new CustomException("没有找到文件");
                }
                //获取缓存文件
                inputStream = downloadFile.getFile().getInputStream();

                //吧OFFICE转为HTML
                String OFFICE_HTML = PoiUtil.officeToHtml(SF_SUFFIX, inputStream);
                //上传文件服务器
                FileUtil.uploadCxfFile(TokenUtil.baseKey(UUID.randomUUID(), SF_TABLE_NAME), SF_NAME_OFFICE, SF_PATH, OFFICE_HTML.getBytes("UTF-8"));

                model.addAttribute("OFFICE_CONTENT", OFFICE_HTML);
            } else {
                //获取缓存文件
                inputStream = downloadFile.getFile().getInputStream();

                //吧OFFICE转为HTML
                model.addAttribute("OFFICE_CONTENT", FileUtil.convertToString(inputStream));
            }
        } catch (Exception e) {
            model.addAttribute("OFFICE_CONTENT", "OFFICE文件预览失败!");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return "admin/component/officePreview";
    }
}
