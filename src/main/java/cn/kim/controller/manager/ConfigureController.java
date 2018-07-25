package cn.kim.controller.manager;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.annotation.Token;
import cn.kim.common.annotation.Validate;
import cn.kim.common.eu.UseType;
import cn.kim.entity.ResultState;
import cn.kim.entity.Tree;
import cn.kim.entity.TreeState;
import cn.kim.service.ConfigureService;
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
 * Created by 余庚鑫 on 2018/3/30
 */
@Controller
@RequestMapping("/admin/configure")
public class ConfigureController extends BaseController {

    @Autowired
    private ConfigureService configureService;

    /**
     * 拿到配置列表
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @GetMapping("/getConfigureTreeData")
    @RequiresPermissions("SYSTEM:CONFIGURE")
    @ResponseBody
    public List<Tree> getConfigureTreeData(String ID) throws Exception {
        List<Map<String, Object>> configures = configureService.selectConfigureList(new HashMap<>(0));
        List<Tree> treeList = toConfigureTreeData(configures, ID);
        return treeList;
    }


    @GetMapping("/add")
    @RequiresPermissions("SYSTEM:CONFIGURE_INSERT")
    @Token(save = true)
    public String addHtml(Model model) throws Exception {
        return "admin/system/configure/addAndEdit";
    }


    @PostMapping("/add")
    @RequiresPermissions("SYSTEM:CONFIGURE_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加配置列表")
    @Token(remove = true)
    @Validate("SYS_CONFIGURE")
    @ResponseBody
    public ResultState add(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = configureService.insertAndUpdateConfigure(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/update/{ID}")
    @RequiresPermissions("SYSTEM:CONFIGURE_UPDATE")
    public String updateHtml(@PathVariable("ID") String ID, Model model) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("CONFIGURE", configureService.selectConfigure(mapParam));
        return "admin/system/configure/addAndEdit";
    }

    @PutMapping("/update")
    @RequiresPermissions("SYSTEM:CONFIGURE_UPDATE_SAVE")
    @SystemControllerLog(useType = UseType.USE, event = "修改配置列表")
    @Validate("SYS_CONFIGURE")
    @ResponseBody
    public ResultState update(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = configureService.insertAndUpdateConfigure(mapParam);
        return resultState(resultMap);
    }

    @DeleteMapping("/delete/{ID}")
    @RequiresPermissions("SYSTEM:CONFIGURE_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除配置列表")
    @ResponseBody
    public ResultState delete(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = configureService.deleteConfigure(mapParam);
        return resultState(resultMap);
    }

    /**********     字段      *******/
    @GetMapping("/column/add")
    @RequiresPermissions("SYSTEM:CONFIGURE_SET_COLUMN_INSERT")
    @Token(save = true)
    public String addHtmlColumn(String SC_ID, Model model) throws Exception {
        model.addAttribute("SC_ID", SC_ID);
        return "admin/system/configure/column/addAndEdit";
    }


    @PostMapping("/column/add")
    @RequiresPermissions("SYSTEM:CONFIGURE_SET_COLUMN_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加配置列表列")
    @Token(remove = true)
    @Validate("SYS_CONFIGURE_COLUMN")
    @ResponseBody
    public ResultState addColumn(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = configureService.insertAndUpdateConfigureColumn(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/column/update/{ID}")
    @RequiresPermissions("SYSTEM:CONFIGURE_SET_COLUMN_UPDATE")
    public String updateHtmlColumn(@PathVariable("ID") String ID, Model model) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("COLUMN", configureService.selectConfigureColumn(mapParam));
        return "admin/system/configure/column/addAndEdit";
    }

    @PutMapping("/column/update")
    @RequiresPermissions("SYSTEM:CONFIGURE_SET_COLUMN_UPDATE_SAVE")
    @SystemControllerLog(useType = UseType.USE, event = "修改配置列表列")
    @Validate("SYS_CONFIGURE_COLUMN")
    @ResponseBody
    public ResultState updateColumn(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = configureService.insertAndUpdateConfigureColumn(mapParam);
        return resultState(resultMap);
    }

    @DeleteMapping("/column/delete/{ID}")
    @RequiresPermissions("SYSTEM:CONFIGURE_SET_COLUMN_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除配置列表列")
    @ResponseBody
    public ResultState deleteColumn(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = configureService.deleteConfigureColumn(mapParam);
        return resultState(resultMap);
    }

    /**********     搜索     *******/
    @GetMapping("/search/add")
    @RequiresPermissions("SYSTEM:CONFIGURE_SET_SEARCH_INSERT")
    @Token(save = true)
    public String addHtmlSearch(String SC_ID, Model model) throws Exception {
        model.addAttribute("SC_ID", SC_ID);
        return "admin/system/configure/search/addAndEdit";
    }


    @PostMapping("/search/add")
    @RequiresPermissions("SYSTEM:CONFIGURE_SET_SEARCH_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加配置列表搜索")
    @Token(remove = true)
    @Validate("SYS_CONFIGURE_SEARCH")
    @ResponseBody
    public ResultState addSearch(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = configureService.insertAndUpdateConfigureSearch(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/search/update/{ID}")
    @RequiresPermissions("SYSTEM:CONFIGURE_SET_SEARCH_UPDATE")
    public String updateHtmlSearch(@PathVariable("ID") String ID, Model model) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("SEARCH", configureService.selectConfigureSearch(mapParam));
        return "admin/system/configure/search/addAndEdit";
    }

    @PutMapping("/search/update")
    @RequiresPermissions("SYSTEM:CONFIGURE_SET_SEARCH_UPDATE_SAVE")
    @SystemControllerLog(useType = UseType.USE, event = "修改配置列表搜索")
    @Validate("SYS_CONFIGURE_SEARCH")
    @ResponseBody
    public ResultState updateSearch(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = configureService.insertAndUpdateConfigureSearch(mapParam);
        return resultState(resultMap);
    }

    @DeleteMapping("/search/delete/{ID}")
    @RequiresPermissions("SYSTEM:CONFIGURE_SET_SEARCH_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除配置列表搜索")
    @ResponseBody
    public ResultState deleteSearch(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = configureService.deleteConfigureSearch(mapParam);
        return resultState(resultMap);
    }

    /**
     * 吧配置列表哦转为树
     *
     * @param configures
     * @param id
     * @return
     */
    private List<Tree> toConfigureTreeData(List<Map<String, Object>> configures, String id) {
        List<Tree> results = new ArrayList<>();
        configures.forEach(configure -> {
            String configureId = toString(configure.get("ID"));

            Tree tree = new Tree();
            tree.setId(configureId);
            tree.setText(toString(configure.get("SC_NAME")));
            tree.setTags(new String[]{"视图:" + toHtmlBColor(configure.get("SC_VIEW"), "yellow")});

            TreeState state = new TreeState();
            if (!isEmpty(id) && configureId.equals(id)) {
                //是否选中
                state.setChecked(true);
                //选中的设置打开
                if (state.isChecked()) {
                    state.setExpanded(true);
                }
            }
            //设置状态
            tree.setState(state);
            results.add(tree);
        });
        return results;
    }
}
