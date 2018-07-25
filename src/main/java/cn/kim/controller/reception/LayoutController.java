package cn.kim.controller.reception;

import cn.kim.controller.manager.BaseController;
import cn.kim.controller.manager.BaseDataController;
import cn.kim.util.HttpUtil;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 余庚鑫 on 2017/4/15.
 */
@Controller
public class LayoutController extends BaseController {

    /**
     * 主界面url
     */
    public static final String LAYOUT_PATH = "/layout";

    @GetMapping("/layout")
    public String layout(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
        WebUtils.issueRedirect(request, response, HttpUtil.getManagerHomeUrl(), null, true);
        return "reception/common/main";
    }

}
