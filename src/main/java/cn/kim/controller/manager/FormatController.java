package cn.kim.controller.manager;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.annotation.Token;
import cn.kim.common.annotation.Validate;
import cn.kim.common.eu.UseType;
import cn.kim.entity.ResultState;
import cn.kim.entity.Tree;
import cn.kim.service.FormatService;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/23
 * 格式管理
 */
@Controller
@RequestMapping("/admin/format")
public class FormatController extends BaseController {

    @Autowired
    private FormatService formatService;


    @GetMapping("/add")
    @RequiresPermissions("SYSTEM:FORMAT_INSERT")
    @Token(save = true)
    public String addHtml(Model model) throws Exception {
        return "admin/system/format/addAndEdit";
    }


    @PostMapping("/add")
    @RequiresPermissions("SYSTEM:FORMAT_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加格式")
    @Token(remove = true)
    @Validate("SYS_FORMAT")
    @ResponseBody
    public ResultState add(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = formatService.insertAndUpdateFormat(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/update/{ID}")
    @RequiresPermissions("SYSTEM:FORMAT_UPDATE")
    public String updateHtml(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("FORMAT", formatService.selectFormat(mapParam));
        return "admin/system/format/addAndEdit";
    }

    @PutMapping("/update")
    @RequiresPermissions("SYSTEM:FORMAT_UPDATE_SAVE")
    @SystemControllerLog(useType = UseType.USE, event = "修改格式")
    @Validate("SYS_FORMAT")
    @ResponseBody
    public ResultState update(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = formatService.insertAndUpdateFormat(mapParam);
        return resultState(resultMap);
    }

    @DeleteMapping("/delete/{ID}")
    @RequiresPermissions("SYSTEM:FORMAT_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除格式")
    @ResponseBody
    public ResultState delete(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = formatService.deleteFormat(mapParam);
        return resultState(resultMap);
    }

    /***    格式详细    ***/
    @GetMapping("/detail/tree")
    @RequiresPermissions("SYSTEM:FORMAT_DETAIL")
    @ResponseBody
    public List<Tree> getFormatDetailTree(String ID, String NOT_ID, String SDT_ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(3);
        mapParam.put("SFD_PARENT_ID", ID);
        mapParam.put("SF_ID", SDT_ID);
        mapParam.put("NOT_ID", NOT_ID);
        List<Tree> formatDetailTree = formatService.selectFormatDetailTree(mapParam);
        return formatDetailTree;
    }

    @GetMapping("/detail/add")
    @RequiresPermissions("SYSTEM:FORMAT_DETAIL_INSERT")
    @Token(save = true)
    public String addDetailHtml(String SF_ID, Model model) throws Exception {
        model.addAttribute("SF_ID", SF_ID);
        return "admin/system/format/detail/addAndEdit";
    }


    @PostMapping("/detail/add")
    @RequiresPermissions("SYSTEM:FORMAT_DETAIL_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加格式详细")
    @Token(remove = true)
    @Validate("SYS_FORMAT_DETAIL")
    @ResponseBody
    public ResultState addDetail(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = formatService.insertAndUpdateFormatDetail(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/detail/update/{ID}")
    @RequiresPermissions("SYSTEM:FORMAT_DETAIL_UPDATE")
    public String updateDetailHtml(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("SFD", formatService.selectFormatDetail(mapParam));
        return "admin/system/format/detail/addAndEdit";
    }

    @PutMapping("/detail/update")
    @RequiresPermissions("SYSTEM:FORMAT_DETAIL_UPDATE_SAVE")
    @SystemControllerLog(useType = UseType.USE, event = "修改格式详细")
    @Validate("SYS_FORMAT_DETAIL")
    @ResponseBody
    public ResultState updateDetail(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = formatService.insertAndUpdateFormatDetail(mapParam);
        return resultState(resultMap);
    }

    @PutMapping("/detail/switchStatus")
    @RequiresPermissions("SYSTEM:FORMAT_DETAIL_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改格式详细状态")
    @ResponseBody
    public ResultState switchStatusDetail(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = formatService.changeFormatDetailStatus(mapParam);

        return resultState(resultMap);
    }

    @DeleteMapping("/detail/delete/{ID}")
    @RequiresPermissions("SYSTEM:FORMAT_DETAIL_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除格式详细")
    @ResponseBody
    public ResultState deleteDetail(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = formatService.deleteFormatDetail(mapParam);
        return resultState(resultMap);
    }
}
