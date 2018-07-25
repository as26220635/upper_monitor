package cn.kim.controller.reception.home;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 余庚鑫 on 2017/4/23.
 */
@Controller
@RequestMapping("/my_home")
public class HomeController {

    @GetMapping("/home")
    public String hoem(){
        return "reception/myhome/home";
    }
}
