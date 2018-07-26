package cn.kim.controller.manager;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.annotation.Token;
import cn.kim.common.annotation.Validate;
import cn.kim.common.eu.UseType;
import cn.kim.entity.ResultState;
import cn.kim.service.EntranceGuardCardService;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/31
 * 门禁卡管理
 */
@Controller
@RequestMapping("/admin/entranceGuardCard")
public class EntranceGuardCardController extends BaseController {

    @Autowired
    private EntranceGuardCardService entranceGuardCardService;


    @GetMapping("/add")
    @RequiresPermissions("BUS:ENTRANCE_GUARD_CARD_INSERT")
    @Token(save = true)
    public String addHtml(Model model) throws Exception {
        return "admin/bus/entranceGuardCard/addAndEdit";
    }


    @PostMapping("/add")
    @RequiresPermissions("BUS:ENTRANCE_GUARD_CARD_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加门禁卡")
    @Token(remove = true)
    @Validate("SYS_VALIDATE")
    @ResponseBody
    public ResultState add(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = entranceGuardCardService.insertAndUpdateEntranceGuardCard(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/update/{ID}")
    @RequiresPermissions("BUS:ENTRANCE_GUARD_CARD_UPDATE")
    public String updateHtml(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("card", entranceGuardCardService.selectEntranceGuardCard(mapParam));
        return "admin/bus/entranceGuardCard/addAndEdit";
    }

    @PutMapping("/update")
    @RequiresPermissions("BUS:ENTRANCE_GUARD_CARD_UPDATE_SAVE")
    @SystemControllerLog(useType = UseType.USE, event = "修改门禁卡")
    @Validate("SYS_VALIDATE")
    @ResponseBody
    public ResultState update(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = entranceGuardCardService.insertAndUpdateEntranceGuardCard(mapParam);
        return resultState(resultMap);
    }

    @DeleteMapping("/delete/{ID}")
    @RequiresPermissions("BUS:ENTRANCE_GUARD_CARD_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除门禁卡")
    @ResponseBody
    public ResultState delete(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = entranceGuardCardService.deleteEntranceGuardCard(mapParam);
        return resultState(resultMap);
    }

}
