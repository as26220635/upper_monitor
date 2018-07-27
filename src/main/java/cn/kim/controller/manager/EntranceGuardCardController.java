package cn.kim.controller.manager;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.annotation.Token;
import cn.kim.common.annotation.Validate;
import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.MagicValue;
import cn.kim.common.eu.UseType;
import cn.kim.entity.ResultState;
import cn.kim.service.EntranceGuardCardService;
import cn.kim.tools.EntranceGuardCardTool;
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

    @GetMapping("/detail/{ID}")
    @RequiresPermissions("BUS:ENTRANCE_GUARD_CARD_DETAIL")
    public String detailHtml(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("card", entranceGuardCardService.selectEntranceGuardCard(mapParam));
        return "admin/bus/entranceGuardCard/detail";
    }

    /**
     * 控制界面
     *
     * @param model
     * @param ID
     * @return
     * @throws Exception
     */
    @GetMapping("/control/{ID}")
    @RequiresPermissions("BUS:ENTRANCE_GUARD_CARD_CONTROL")
    public String controlHtml(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("card", entranceGuardCardService.selectEntranceGuardCard(mapParam));
        return "admin/bus/entranceGuardCard/control";
    }

    /**
     * 操控
     *
     * @param ID
     * @param action
     * @return
     * @throws Exception
     */
    @PostMapping("/control/{ID}/{action}")
    @RequiresPermissions("BUS:ENTRANCE_GUARD_CARD_CONTROL")
    @SystemControllerLog(useType = UseType.USE, event = "遥控门禁卡")
    @ResponseBody
    public ResultState control(@PathVariable("ID") String ID, @PathVariable("action") String action) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> card = entranceGuardCardService.selectEntranceGuardCard(mapParam);
        String ip = toString(card.get("BEGC_IP"));
        String username = toString(card.get("BEGC_USERNAME"));
        String password = toString(card.get("BEGC_PASSWORD"));

        boolean isSuccess = false;
        String desc = STATUS_ERROR_MESSAGE;
        //发送包操控
        if (!isEmpty(ip) && !isEmpty(username) && !isEmpty(password)) {
            Map<String, Object> controlMap = EntranceGuardCardTool.control(ip, action, username, password);
            if (toInt(controlMap.get(MagicValue.STATUS)) == STATUS_SUCCESS) {
                isSuccess = true;
            } else {
                desc = toString(controlMap.get(MagicValue.DESC));
            }
        } else {
            isSuccess = false;
        }

        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(1);
        if (isSuccess) {
            resultMap.put(MagicValue.STATUS, STATUS_SUCCESS);
            resultMap.put(MagicValue.DESC, STATUS_SUCCESS_MESSAGE);
        } else {
            resultMap.put(MagicValue.STATUS, STATUS_ERROR);
            if (!isEmpty(ip)) {
                resultMap.put(MagicValue.DESC, desc);
            } else {
                resultMap.put(MagicValue.DESC, "门禁IP地址没有数据!");
            }
        }
        resultMap.put(MagicValue.LOG, "遥控门禁:" + toString(card.get("BEGC_ID")) + ",动作:" + action);
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
