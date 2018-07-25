package cn.kim.controller.manager;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.annotation.Token;
import cn.kim.common.annotation.Validate;
import cn.kim.common.eu.UseType;
import cn.kim.entity.ResultState;
import cn.kim.entity.Tree;
import cn.kim.entity.TreeState;
import cn.kim.service.ValidateService;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/3/31
 * 验证管理
 */
@Controller
@RequestMapping("/admin/validate")
public class ValidateController extends BaseController {

    @Autowired
    private ValidateService validateService;


    @GetMapping("/add")
    @RequiresPermissions("SYSTEM:VALIDATE_INSERT")
    @Token(save = true)
    public String addHtml(Model model) throws Exception {
        return "admin/system/validate/addAndEdit";
    }


    @PostMapping("/add")
    @RequiresPermissions("SYSTEM:VALIDATE_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加验证")
    @Token(remove = true)
    @Validate("SYS_VALIDATE")
    @ResponseBody
    public ResultState add(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = validateService.insertAndUpdateValidate(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/update/{ID}")
    @RequiresPermissions("SYSTEM:VALIDATE_UPDATE")
    public String updateHtml(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("VALIDATE", validateService.selectValidate(mapParam));
        return "admin/system/validate/addAndEdit";
    }

    @PutMapping("/update")
    @RequiresPermissions("SYSTEM:VALIDATE_UPDATE_SAVE")
    @SystemControllerLog(useType = UseType.USE, event = "修改验证")
    @Validate("SYS_VALIDATE")
    @ResponseBody
    public ResultState update(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = validateService.insertAndUpdateValidate(mapParam);
        return resultState(resultMap);
    }

    @PutMapping("/switchStatus")
    @RequiresPermissions("SYSTEM:VALIDATE_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改验证状态")
    @ResponseBody
    public ResultState switchStatus(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = validateService.changeValidateStatus(mapParam);

        return resultState(resultMap);
    }

    @DeleteMapping("/delete/{ID}")
    @RequiresPermissions("SYSTEM:VALIDATE_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除验证")
    @ResponseBody
    public ResultState delete(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = validateService.deleteValidate(mapParam);
        return resultState(resultMap);
    }

    /*******    字段      ******/
    @GetMapping("/field/list")
    @RequiresPermissions("SYSTEM:VALIDATE_GROUP")
    @ResponseBody
    public List<Map<String, Object>> listField(@RequestParam Map<String, Object> mapParam) throws Exception {
        List<Map<String, Object>> fields = validateService.selectValidateFieldList(mapParam);
        return toComboboxValue(fields, "ID", "SVF_NAME");
    }

    @GetMapping("/field/add")
    @RequiresPermissions("SYSTEM:VALIDATE_SET_FIELD_INSERT")
    @Token(save = true)
    public String addHtmlField(String SV_ID, Model model) throws Exception {
        model.addAttribute("SV_ID", SV_ID);
        return "admin/system/validate/field/addAndEdit";
    }


    @PostMapping("/field/add")
    @RequiresPermissions("SYSTEM:VALIDATE_SET_FIELD_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加验证")
    @Token(remove = true)
    @Validate("SYS_VALIDATE_FIELD")
    @ResponseBody
    public ResultState addField(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = validateService.insertAndUpdateValidateField(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/field/update/{ID}")
    @RequiresPermissions("SYSTEM:VALIDATE_SET_FIELD_UPDATE")
    public String updateHtmlField(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("FILED", validateService.selectValidateField(mapParam));
        return "admin/system/validate/field/addAndEdit";
    }

    @PutMapping("/field/update")
    @RequiresPermissions("SYSTEM:VALIDATE_SET_FIELD_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改验证字段")
    @Validate("SYS_VALIDATE_FIELD")
    @ResponseBody
    public ResultState updateField(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = validateService.insertAndUpdateValidateField(mapParam);
        return resultState(resultMap);
    }

    @PutMapping("/field/switchStatus")
    @RequiresPermissions("SYSTEM:VALIDATE_SET_FIELD_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改验证字段状态")
    @ResponseBody
    public ResultState switchStatusField(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = validateService.changeValidateFieldStatus(mapParam);

        return resultState(resultMap);
    }

    @DeleteMapping("/field/delete/{ID}")
    @RequiresPermissions("SYSTEM:VALIDATE_SET_FIELD_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除验证字段")
    @ResponseBody
    public ResultState deleteField(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = validateService.deleteValidateField(mapParam);
        return resultState(resultMap);
    }

    /*******    正则管理      ******/
    @GetMapping("/regex/tree")
    @RequiresPermissions("SYSTEM:VALIDATE_SET_FIELD")
    @ResponseBody
    public List<Tree> getRegexTree(String ID) throws Exception {
        List<Map<String, Object>> regexs = validateService.selectValidateRegexList(new HashMap<>(0));
        List<Tree> trees = toRegexTree(regexs, ID);
        return trees;
    }

    @GetMapping("/regex/add")
    @RequiresPermissions("SYSTEM:VALIDATE_REGEX_INSERT")
    @Token(save = true)
    public String addHtmlRegex(String SV_ID, Model model) throws Exception {
        model.addAttribute("SV_ID", SV_ID);
        return "admin/system/validate/regex/addAndEdit";
    }


    @PostMapping("/regex/add")
    @RequiresPermissions("SYSTEM:VALIDATE_REGEX_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加验证正则")
    @Token(remove = true)
    @Validate("SYS_VALIDATE_REGEX")
    @ResponseBody
    public ResultState addRegex(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = validateService.insertAndUpdateValidateRegex(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/regex/update/{ID}")
    @RequiresPermissions("SYSTEM:VALIDATE_REGEX_UPDATE")
    public String updateHtmlRegex(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("REGEX", validateService.selectValidateRegex(mapParam));
        return "admin/system/validate/regex/addAndEdit";
    }

    @PutMapping("/regex/update")
    @RequiresPermissions("SYSTEM:VALIDATE_REGEX_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改验证正则")
    @Validate("SYS_VALIDATE_REGEX")
    @ResponseBody
    public ResultState updateRegex(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = validateService.insertAndUpdateValidateRegex(mapParam);
        return resultState(resultMap);
    }

    @PutMapping("/regex/switchStatus")
    @RequiresPermissions("SYSTEM:VALIDATE_SET_FIELD_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改验证正则状态")
    @ResponseBody
    public ResultState switchStatusRegex(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = validateService.changeValidateRegexStatus(mapParam);

        return resultState(resultMap);
    }

    @DeleteMapping("/regex/delete/{ID}")
    @RequiresPermissions("SYSTEM:VALIDATE_REGEX_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除验证正则")
    @ResponseBody
    public ResultState deleteRegex(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = validateService.deleteValidateRegex(mapParam);
        return resultState(resultMap);
    }

    /*******    组管理      ******/
    @GetMapping("/group/add")
    @RequiresPermissions("SYSTEM:VALIDATE_GROUP_INSERT")
    @Token(save = true)
    public String addHtmlGroup(String SV_ID, Model model) throws Exception {
        model.addAttribute("SV_ID", SV_ID);
        return "admin/system/validate/group/addAndEdit";
    }


    @PostMapping("/group/add")
    @RequiresPermissions("SYSTEM:VALIDATE_GROUP_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加验证组")
    @Token(remove = true)
    @Validate("SYS_VALIDATE_GROUP")
    @ResponseBody
    public ResultState addGroup(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = validateService.insertAndUpdateValidateGroup(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/group/update/{ID}")
    @RequiresPermissions("SYSTEM:VALIDATE_GROUP_UPDATE")
    public String updateHtmlGroup(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("GROUP", validateService.selectValidateGroup(mapParam));
        return "admin/system/validate/group/addAndEdit";
    }

    @PutMapping("/group/update")
    @RequiresPermissions("SYSTEM:VALIDATE_GROUP_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改验证组")
    @Validate("SYS_VALIDATE_GROUP")
    @ResponseBody
    public ResultState updateGroup(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = validateService.insertAndUpdateValidateGroup(mapParam);
        return resultState(resultMap);
    }

    @DeleteMapping("/group/delete/{ID}")
    @RequiresPermissions("SYSTEM:VALIDATE_GROUP_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除验证组")
    @ResponseBody
    public ResultState deleteGroup(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = validateService.deleteValidateGroup(mapParam);
        return resultState(resultMap);
    }

    /**
     * 吧正则map转为tree
     *
     * @param regexList
     * @param regexId
     * @return
     */
    public List<Tree> toRegexTree(List<Map<String, Object>> regexList, String regexId) {
        List<Tree> resultTrees = new ArrayList<>();
        for (Map<String, Object> regexMap : regexList) {
            String id = toString(regexMap.get("ID"));
            String regex = toString(regexMap.get("SVR_REGEX"));

            Tree tree = new Tree();
            tree.setId(id);
            tree.setText(toString(regexMap.get("SVR_NAME")));
            tree.setTags(new String[]{regex.length() > 65 ? regex.substring(0, 62) + "..." : regex});

            TreeState state = new TreeState();

            if (id.equals(regexId)) {
                //是否选中
                state.setChecked(true);
                state.setExpanded(true);
            }

            //禁止选择
            Integer isStatus = toInt(regexMap.get("IS_STATUS"));
            if (!isEmpty(isStatus) && isStatus == STATUS_ERROR) {
                state.setDisabled(true);
            }
            //设置状态
            tree.setState(state);

            resultTrees.add(tree);
        }
        return resultTrees;
    }
}
