package cn.kim.controller.reception.home;

import cn.kim.common.eu.SystemEnum;
import cn.kim.controller.manager.BaseController;
import cn.kim.controller.manager.BaseDataController;
import cn.kim.entity.ActiveUser;
import cn.kim.util.AuthcUtil;
import cn.kim.util.HttpUtil;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 余庚鑫 on 2018/3/31.
 */
@Controller
public class MyHomeController extends BaseController {
    /**
     * 主界面url
     */
    public static final String LAYOUT_PATH = "/homeLayout";
    /**
     * 主界面拦截url
     */
    public static final String HOME_URL = "/my_home/";

    @GetMapping("/homeLayout")
    public String home(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ActiveUser activeUser = AuthcUtil.getCurrentUser();
        //如果是管理员就跳转到后台去
        if (activeUser != null && activeUser.getType().equals(SystemEnum.MANAGER.toString())) {
            WebUtils.issueRedirect(request, response, HttpUtil.getManagerHomeUrl(), null, true);
        }
        System.out.println(HttpUtil.getUrl(request, false));
        return "reception/home_common/home_main";
    }
}
