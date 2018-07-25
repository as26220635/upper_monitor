package cn.kim.controller.manager;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.annotation.Token;
import cn.kim.common.annotation.Validate;
import cn.kim.common.attr.MagicValue;
import cn.kim.common.eu.UseType;
import cn.kim.entity.ResultState;
import cn.kim.entity.Tree;
import cn.kim.service.DictService;
import cn.kim.util.DictUtil;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/31
 * 字典
 */
@Controller
@RequestMapping("/admin/dict")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;


    @GetMapping("/add")
    @RequiresPermissions("SYSTEM:DICT_TYPE_INSERT")
    @Token(save = true)
    public String addHtml(Model model) throws Exception {
        return "admin/system/dict/addAndEdit";
    }


    @PostMapping("/add")
    @RequiresPermissions("SYSTEM:DICT_TYPE_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加字典")
    @Token(remove = true)
    @Validate("SYS_DICT_TYPE")
    @ResponseBody
    public ResultState add(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = dictService.insertAndUpdateDictType(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/update/{ID}")
    @RequiresPermissions("SYSTEM:DICT_TYPE_UPDATE")
    public String updateHtml(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("DICT", dictService.selectDictType(mapParam));
        return "admin/system/dict/addAndEdit";
    }

    @PutMapping("/update")
    @RequiresPermissions("SYSTEM:DICT_TYPE_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改字典")
    @Validate("SYS_DICT_TYPE")
    @ResponseBody
    public ResultState update(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = dictService.insertAndUpdateDictType(mapParam);
        return resultState(resultMap);
    }

    @PutMapping("/switchStatus")
    @RequiresPermissions("SYSTEM:DICT_TYPE_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改字典状态")
    @ResponseBody
    public ResultState switchStatus(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = dictService.insertAndUpdateDictType(mapParam);
        Map<String, Object> dictType = dictService.selectDictType(mapParam);

        resultMap.put(MagicValue.LOG, "状态切换,字典名称:" + dictType.get("SDT_NAME") + ",状态:" + statusExplain(mapParam));

        return resultState(resultMap);
    }

    @DeleteMapping("/delete/{ID}")
    @RequiresPermissions("SYSTEM:DICT_TYPE_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除字典")
    @ResponseBody
    public ResultState delete(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = dictService.deleteDictType(mapParam);
        return resultState(resultMap);
    }

    @PostMapping("/cache")
    @RequiresPermissions("SYSTEM:DICT_TYPE_CACHE")
    @SystemControllerLog(useType = UseType.USE, event = "刷新字典缓存")
    @ResponseBody
    public ResultState cache() throws Exception {
        try {
            DictUtil.initDictToCache();
            return ResultState.success("刷新缓存成功!");
        } catch (Exception e) {
            return ResultState.error("刷新缓存失败");
        }
    }

    /**********     详细信息    *********/

    @GetMapping("/info/tree")
    @RequiresPermissions("SYSTEM:DICTINFO")
    @ResponseBody
    public List<Tree> getDictInfoTree(String ID, String NOT_ID, String SDT_ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(3);
        mapParam.put("SDI_PARENTID", ID);
        mapParam.put("SDT_ID", SDT_ID);
        mapParam.put("NOT_ID", NOT_ID);
        List<Tree> dictInfos = dictService.selectDictInfoTree(mapParam);
        return dictInfos;
    }

    @GetMapping("/info/add")
    @RequiresPermissions("SYSTEM:DICTINFO_INSERT")
    @Token(save = true)
    public String addHtmlDictInfo(String SDT_ID, Model model) throws Exception {
        model.addAttribute("SDT_ID", SDT_ID);
        return "admin/system/dict/info/addAndEdit";
    }


    @PostMapping("/info/add")
    @RequiresPermissions("SYSTEM:DICTINFO_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加字典信息")
    @Token(remove = true)
    @Validate("SYS_DICT_INFO")
    @ResponseBody
    public ResultState addDictInfo(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = dictService.insertAndUpdateDictInfo(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/info/update/{ID}")
    @RequiresPermissions("SYSTEM:DICTINFO_UPDATE")
    public String updateHtmlDictInfo(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("INFO", dictService.selectDictInfo(mapParam));
        return "admin/system/dict/info/addAndEdit";
    }

    @PutMapping("/info/update")
    @RequiresPermissions("SYSTEM:DICTINFO_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改字典信息")
    @Validate("SYS_DICT_INFO")
    @ResponseBody
    public ResultState updateDictInfo(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = dictService.insertAndUpdateDictInfo(mapParam);
        return resultState(resultMap);
    }

    @PutMapping("/info/switchStatus")
    @RequiresPermissions("SYSTEM:DICTINFO_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改字典状态信息")
    @ResponseBody
    public ResultState switchStatusDictInfo(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = dictService.changeDictInfoStatus(mapParam);
        return resultState(resultMap);
    }

    @DeleteMapping("/info/delete/{ID}")
    @RequiresPermissions("SYSTEM:DICTINFO_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除字典信息")
    @ResponseBody
    public ResultState deleteDictInfo(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = dictService.deleteDictInfo(mapParam);
        return resultState(resultMap);
    }
}
