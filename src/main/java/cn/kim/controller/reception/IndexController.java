package cn.kim.controller.reception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by 余庚鑫 on 2018/4/10
 * 主页
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(Model model) {
        return "reception/index";
    }
}
