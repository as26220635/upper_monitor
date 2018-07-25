package cn.kim.controller.manager;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.annotation.Token;
import cn.kim.common.annotation.Validate;
import cn.kim.common.eu.UseType;
import cn.kim.entity.ResultState;
import cn.kim.service.ButtonService;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/23
 * 按钮管理
 */
@Controller
@RequestMapping("/admin/button")
public class ButtonController extends BaseController {

    @Autowired
    private ButtonService buttonService;


    @GetMapping("/add")
    @RequiresPermissions("SYSTEM:BUTTON_INSERT")
    @Token(save = true)
    public String addHtml(Model model) throws Exception {
        return "admin/system/button/addAndEdit";
    }


    @PostMapping("/add")
    @RequiresPermissions("SYSTEM:BUTTON_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加按钮")
    @Token(remove = true)
    @Validate("SYS_BUTTON")
    @ResponseBody
    public ResultState add(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = buttonService.insertAndUpdateButton(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/update/{ID}")
    @RequiresPermissions("SYSTEM:BUTTON_UPDATE")
    public String updateHtml(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("BUTTON", buttonService.selectButton(mapParam));
        return "admin/system/button/addAndEdit";
    }

    @PutMapping("/update")
    @RequiresPermissions("SYSTEM:BUTTON_UPDATE_SAVE")
    @SystemControllerLog(useType = UseType.USE, event = "修改按钮")
    @Validate("SYS_BUTTON")
    @ResponseBody
    public ResultState update(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = buttonService.insertAndUpdateButton(mapParam);
        return resultState(resultMap);
    }

    @DeleteMapping("/delete/{ID}")
    @RequiresPermissions("SYSTEM:BUTTON_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除按钮")
    @ResponseBody
    public ResultState delete(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = buttonService.deleteButton(mapParam);
        return resultState(resultMap);
    }
}
