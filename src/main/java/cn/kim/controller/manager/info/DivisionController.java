package cn.kim.controller.manager.info;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.annotation.Token;
import cn.kim.common.annotation.Validate;
import cn.kim.common.eu.UseType;
import cn.kim.controller.manager.BaseController;
import cn.kim.entity.ResultState;
import cn.kim.entity.Tree;
import cn.kim.service.DivisionService;
import com.google.common.collect.Maps;
import com.sun.istack.internal.Nullable;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/7/8
 * 部门管理
 */
@Controller
@RequestMapping("/admin/division")
public class DivisionController extends BaseController {

    @Autowired
    private DivisionService divisionService;

    /**
     * 获取部门树
     *
     * @param ID
     * @param NOT_ID 不显示的自身和父类ID
     * @return
     * @throws Exception
     */
    @GetMapping("/tree")
    @RequiresPermissions("INFO:DIVISION")
    @ResponseBody
    public List<Tree> treeList(String ID, @Nullable String NOT_ID) throws Exception {
        List<Tree> treeList = divisionService.selectDivisionTreeList(ID, NOT_ID);
        return treeList;
    }

    @GetMapping("/add")
    @RequiresPermissions("INFO:DIVISION_INSERT")
    @Token(save = true)
    public String addHtml(Model model) throws Exception {
        return "admin/info/division/addAndEdit";
    }


    @PostMapping("/add")
    @RequiresPermissions("INFO:DIVISION_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加部门")
    @Token(remove = true)
    @Validate(value = "BUS_DIVISION", required = true)
    @ResponseBody
    public ResultState add(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = divisionService.insertAndUpdateDivision(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/update/{ID}")
    @RequiresPermissions("INFO:DIVISION_UPDATE")
    public String updateHtml(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("division", divisionService.selectDivision(mapParam));
        return "admin/info/division/addAndEdit";
    }

    @PutMapping("/update")
    @RequiresPermissions("INFO:DIVISION_UPDATE_SAVE")
    @SystemControllerLog(useType = UseType.USE, event = "修改部门")
    @Validate(value = "BUS_DIVISION", required = true)
    @ResponseBody
    public ResultState update(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = divisionService.insertAndUpdateDivision(mapParam);
        return resultState(resultMap);
    }

    @DeleteMapping("/delete/{ID}")
    @RequiresPermissions("INFO:DIVISION_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除部门")
    @ResponseBody
    public ResultState delete(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = divisionService.deleteDivision(mapParam);
        return resultState(resultMap);
    }
}
