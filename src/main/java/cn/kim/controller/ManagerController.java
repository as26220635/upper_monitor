package cn.kim.controller;

import cn.kim.controller.manager.BaseController;
import cn.kim.common.attr.Constants;
import cn.kim.common.eu.SystemEnum;
import cn.kim.controller.manager.BaseController;
import cn.kim.controller.manager.BaseDataController;
import cn.kim.entity.ActiveUser;
import cn.kim.service.ManagerService;
import cn.kim.util.AuthcUtil;
import cn.kim.util.HttpUtil;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/21.
 */
@Controller
public class ManagerController extends BaseController {
    //主界面url
    public static final String LAYOUT_PATH = "/adminLayout";
    //主界面拦截url
    public static final String MANAGER_URL = "/admin/";

    @GetMapping("/adminLayout")
    public String manager(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ActiveUser activeUser = AuthcUtil.getCurrentUser();
        //如果是管理员就跳转到前台台去
        if (activeUser != null && !activeUser.getType().equals(SystemEnum.MANAGER.toString())) {
            WebUtils.issueRedirect(request, response, HttpUtil.getMyHomeUrl(), null, true);
        }

        return "admin/home";
    }
}
