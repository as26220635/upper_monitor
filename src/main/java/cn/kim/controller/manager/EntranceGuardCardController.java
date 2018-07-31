package cn.kim.controller.manager;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.annotation.Token;
import cn.kim.common.annotation.Validate;
import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.MagicValue;
import cn.kim.common.eu.UseType;
import cn.kim.common.netty.TCPServerNetty;
import cn.kim.entity.ResultState;
import cn.kim.service.EntranceGuardCardService;
import cn.kim.tools.EntranceGuardCardTool;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.checkerframework.checker.units.qual.A;
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

    @Autowired
    private TCPServerNetty tcpServerNetty;

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


    /**
     * 门禁详细信息
     *
     * @param model
     * @param ID
     * @return
     * @throws Exception
     */
    @GetMapping("/detail/{ID}")
    @RequiresPermissions("BUS:ENTRANCE_GUARD_CARD_DETAIL")
    public String detailHtml(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("card", entranceGuardCardService.selectEntranceGuardCard(mapParam));
        return "admin/bus/entranceGuardCard/detail";
    }

    /**
     * 获取门禁状态
     *
     * @param model
     * @param ID
     * @return
     * @throws Exception
     */
    @GetMapping("/status/{ID}")
    @RequiresPermissions("BUS:ENTRANCE_GUARD_CARD_CONTROL")
    @ResponseBody
    public JSONObject status(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> card = entranceGuardCardService.selectEntranceGuardCard(mapParam);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("BEGC_STATUS_NAME", card.get("BEGC_STATUS_NAME"));
        return jsonObject;
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

        mapParam.clear();
        int command = 0x00;
        if ("0".equals(action)) {
            //开进
            command = TCPServerNetty.OPEN_DOOR;
            mapParam.put(MagicValue.DOOR, 1);
        } else if ("1".equals(action)) {
            //关进
            command = TCPServerNetty.CLOSE_DOOR;
            mapParam.put(MagicValue.DOOR, 1);
        } else if ("2".equals(action)) {
            //开出
            command = TCPServerNetty.OPEN_DOOR;
            mapParam.put(MagicValue.DOOR, 2);
        } else if ("3".equals(action)) {
            //关出
            command = TCPServerNetty.CLOSE_DOOR;
            mapParam.put(MagicValue.DOOR, 2);
        } else if ("4".equals(action)) {
            //锁进
            command = TCPServerNetty.LOCK_DOOR;
            mapParam.put(MagicValue.DOOR, 1);
            mapParam.put(MagicValue.STATUS, 1);
        } else if ("5".equals(action)) {
            //解锁进
            command = TCPServerNetty.LOCK_DOOR;
            mapParam.put(MagicValue.DOOR, 1);
            mapParam.put(MagicValue.STATUS, 0);
        } else if ("6".equals(action)) {
            //锁出
            command = TCPServerNetty.LOCK_DOOR;
            mapParam.put(MagicValue.DOOR, 2);
            mapParam.put(MagicValue.STATUS, 1);
        } else if ("7".equals(action)) {
            //解锁出
            command = TCPServerNetty.LOCK_DOOR;
            mapParam.put(MagicValue.DOOR, 2);
            mapParam.put(MagicValue.STATUS, 0);
        } else if ("8".equals(action)) {
            //火警输出
            command = TCPServerNetty.FIRE_ALARM;
            mapParam.put(MagicValue.DESC, 1);
            mapParam.put(MagicValue.STATUS, 0);
        } else if ("9".equals(action)) {
            //关闭火警输出
            command = TCPServerNetty.FIRE_ALARM;
            mapParam.put(MagicValue.DESC, 1);
            mapParam.put(MagicValue.STATUS, 1);
        } else if ("a".equals(action)) {
            //报警输出
            command = TCPServerNetty.POLICE_ALARM;
            mapParam.put(MagicValue.DESC, 1);
            mapParam.put(MagicValue.STATUS, 0);
        } else if ("b".equals(action)) {
            //关闭报警输出
            command = TCPServerNetty.POLICE_ALARM;
            mapParam.put(MagicValue.DESC, 1);
            mapParam.put(MagicValue.STATUS, 1);
        } else if ("c".equals(action)) {
            //长开进
            command = TCPServerNetty.OPEN_DOORS;
            mapParam.put(MagicValue.DOOR, 1);
        } else if ("d".equals(action)) {
            //长开出
            command = TCPServerNetty.OPEN_DOORS;
            mapParam.put(MagicValue.DOOR, 2);
        } else if ("f".equals(action)) {
            //时间同步
            command = TCPServerNetty.TIME_ASYNC;
        }

        //操控
        boolean isSuccess = EntranceGuardCardTool.control(ip, command, mapParam);

        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(1);
        if (isSuccess) {
            resultMap.put(MagicValue.STATUS, STATUS_SUCCESS);
            resultMap.put(MagicValue.DESC, STATUS_SUCCESS_MESSAGE);
        } else {
            resultMap.put(MagicValue.STATUS, STATUS_ERROR);
            if (!isEmpty(ip)) {
                resultMap.put(MagicValue.DESC, "客户端连接失败!");
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
