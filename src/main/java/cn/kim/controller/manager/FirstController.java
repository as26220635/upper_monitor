package cn.kim.controller.manager;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.eu.UseType;
import cn.kim.entity.ResultState;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class FirstController extends BaseController {

    @GetMapping("/queryUser")
    public String queryUser(Model model) throws Exception {
        model.addAttribute("accountInfo", getAccountInfo());
        return "admin/system/first/editActiveUser";
    }

    /**
     * 后台修改密码
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/editPswd")
    public String editPswd(Model model) throws Exception {
        return "admin/system/first/editPswd";
    }

    /**
     * 前台修改密码
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/editPswdR")
    public String editPswdR(Model model) throws Exception {
        return "admin/system/first/editPswdR";
    }

    @PostMapping("/editUser")
    @SystemControllerLog(useType = UseType.USE, event = "修改信息")
    @ResponseBody
    public ResultState editUser() throws Exception {
        return ResultState.success("修改个人信息成功！", "修改个人信息成功！");
    }

    @PostMapping("/password")
    @SystemControllerLog(useType = UseType.USE, event = "修改密码")
    @ResponseBody
    public ResultState password(HttpServletRequest request, String password, String oldPassword) throws Exception {
        return null;
    }

    /**
     * 主页
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/home")
    public String home(Model model) throws Exception {
        return "admin/system/first/home";
    }
}
