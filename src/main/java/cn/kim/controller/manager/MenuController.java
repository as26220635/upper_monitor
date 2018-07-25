package cn.kim.controller.manager;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.annotation.Token;
import cn.kim.common.annotation.Validate;
import cn.kim.common.eu.UseType;
import cn.kim.entity.ResultState;
import cn.kim.entity.Tree;
import cn.kim.service.ButtonService;
import cn.kim.service.MenuService;
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
 * Created by 余庚鑫 on 2018/3/23
 */
@Controller
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private ButtonService buttonService;

    /**
     * 菜单主页
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/")
    @RequiresPermissions("SYSTEM:MENU")
    public String home(Model model) throws Exception {
        model.addAttribute("accountInfo", getAccountInfo());
        return "admin/system/menu/home";
    }

    @GetMapping("/list")
    @RequiresPermissions("SYSTEM:MENU")
    @ResponseBody
    public List<Map<String, Object>> list(Model model) throws Exception {
        List<Map<String, Object>> menus = menuService.queryMenuList();
        for (Map<String, Object> menu : menus) {
            if ("0".equals(toString(menu.get("SM_PARENTID")))) {
                menu.put("parentId", null);
            } else {
                menu.put("parentId", menu.get("SM_PARENTID"));
            }
        }
        return menus;
    }

    /**
     * 拿到选中父菜单
     *
     * @param ID
     * @param NOT_ID 不显示菜单ID
     * @return
     * @throws Exception
     */
    @GetMapping("/getMenuTreeData")
    @RequiresPermissions("SYSTEM:MENU")
    @ResponseBody
    public List<Tree> getMenuTreeData(String ID, @Nullable String NOT_ID) throws Exception {
        List<Map<String, Object>> menus = menuService.selectMenuTreeSelectID(ID, NOT_ID);
        List<Tree> trees = toMenuTreeData(menus);
        return trees;
    }

    @GetMapping("/add")
    @RequiresPermissions("SYSTEM:MENU_INSERT")
    @Token(save = true)
    public String addHtml(Model model) throws Exception {
        return "admin/system/menu/addAndEdit";
    }


    @PostMapping("/add")
    @RequiresPermissions("SYSTEM:MENU_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加菜单")
    @Token(remove = true)
    @Validate("SYS_MENU")
    @ResponseBody
    public ResultState add(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = menuService.insertAndUpdateMenu(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/update/{ID}")
    @RequiresPermissions("SYSTEM:MENU_UPDATE")
    public String updateHtml(Model model, @PathVariable("ID") String ID) throws Exception {
        model.addAttribute("MENU", menuService.queryMenuById(ID));
        return "admin/system/menu/addAndEdit";
    }

    @PutMapping("/update")
    @RequiresPermissions("SYSTEM:MENU_UPDATE_SAVE")
    @SystemControllerLog(useType = UseType.USE, event = "修改菜单")
    @Validate("SYS_MENU")
    @ResponseBody
    public ResultState update(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = menuService.insertAndUpdateMenu(mapParam);
        return resultState(resultMap);
    }

    @PutMapping("/switchStatus")
    @RequiresPermissions("SYSTEM:MENU_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改菜单状态")
    @ResponseBody
    public ResultState switchStatus(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = menuService.changeMenuStatus(mapParam);

        return resultState(resultMap);
    }

    /**
     * 拿到菜单BUTTON菜单
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @GetMapping("/getMenuButtonTreeData")
    @RequiresPermissions("SYSTEM:MENU_SET_MENU")
    @ResponseBody
    public List<Tree> getMenuButtonTreeData(String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("SM_ID", ID);
        List<Tree> buttons = buttonService.selectButtonTree(mapParam);
        return buttons;
    }

    /**
     * 更新按钮权限
     *
     * @param mapParam
     * @return
     * @throws Exception
     */
    @PutMapping("/updateMenuButton")
    @RequiresPermissions("SYSTEM:MENU_SET_MENU")
    @SystemControllerLog(useType = UseType.USE, event = "设置菜单按钮")
    @ResponseBody
    public ResultState updateMenuButton(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = menuService.updateMenuButton(mapParam);
        return resultState(resultMap);
    }

    @DeleteMapping("/delete/{ID}")
    @RequiresPermissions("SYSTEM:MENU_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除菜单")
    @ResponseBody
    public ResultState delete(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = menuService.deleteMenu(mapParam);
        return resultState(resultMap);
    }

}
