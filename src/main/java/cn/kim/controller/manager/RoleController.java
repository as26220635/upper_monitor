package cn.kim.controller.manager;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.annotation.Token;
import cn.kim.common.annotation.Validate;
import cn.kim.common.eu.UseType;
import cn.kim.entity.ResultState;
import cn.kim.entity.Tree;
import cn.kim.service.RoleService;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.Logical;
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
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色列表转为 KEY VALUE combobox使用
     *
     * @param mapParam
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    @RequiresPermissions(value = {"SYSTEM:DICT_TYPE_INSERT", "SYSTEM:DICT_TYPE_UPDATE"}, logical = Logical.OR)
    @ResponseBody
    public List<Map<String, Object>> roleList(@RequestParam Map<String, Object> mapParam) throws Exception {
        //只查询状态为开启的
        mapParam.put("IS_STATUS", STATUS_SUCCESS);
        List<Map<String, Object>> roleList = roleService.selectRoleList(mapParam);
        return toComboboxValue(roleList, "ID", "SR_NAME");
    }

    /**
     * 获取角色列表 转为List<Tree>
     *
     * @param ID 已选角色ID
     * @return
     * @throws Exception
     */
    @GetMapping("/tree")
    @RequiresPermissions(value = {"SYSTEM:PROCESS_DEFINITION","SYSTEM:PROCESS_STEP","SYSTEM:PROCESS_START"}, logical = Logical.OR)
    @ResponseBody
    public List<Tree> getTree(String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("SR_ID", ID);
        List<Tree> roles = roleService.selectRoleListTree(mapParam);

        return roles;
    }

    @GetMapping("/add")
    @RequiresPermissions("SYSTEM:ROLE_INSERT")
    @Token(save = true)
    public String addHtml(Model model) throws Exception {
        return "admin/system/role/addAndEdit";
    }


    @PostMapping("/add")
    @RequiresPermissions("SYSTEM:ROLE_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加角色")
    @Token(remove = true)
    @Validate("SYS_ROLE")
    @ResponseBody
    public ResultState add(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = roleService.insertAndUpdateRole(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/update/{ID}")
    @RequiresPermissions("SYSTEM:ROLE_UPDATE")
    public String updateHtml(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("ROLE", roleService.selectRole(mapParam));
        return "admin/system/role/addAndEdit";
    }

    @PutMapping("/update")
    @RequiresPermissions("SYSTEM:ROLE_UPDATE_SAVE")
    @SystemControllerLog(useType = UseType.USE, event = "修改角色")
    @Validate("SYS_ROLE")
    @ResponseBody
    public ResultState update(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = roleService.insertAndUpdateRole(mapParam);
        return resultState(resultMap);
    }


    @PutMapping("/switchStatus")
    @RequiresPermissions("SYSTEM:ROLE_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改角色状态")
    @ResponseBody
    public ResultState switchStatus(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = roleService.changeRoleStatus(mapParam);

        return resultState(resultMap);
    }

    /**
     * 获取菜单列表
     *
     * @param ID
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/menuTree/{ID}")
    @RequiresPermissions("SYSTEM:ROLE_SET_PERMISSION")
    public String menuTree(@PathVariable("ID") String ID, Model model) throws Exception {
        model.addAttribute("ID", ID);
        return "admin/system/role/selectMenu";
    }

    /**
     * 拿到菜单节点数据
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @GetMapping("/getMenuTreeData/{ID}")
    @RequiresPermissions("SYSTEM:ROLE_SET_PERMISSION")
    @ResponseBody
    public List<Tree> getMenuTreeData(@PathVariable("ID") String ID) throws Exception {
        List<Map<String, Object>> menus = roleService.selectMenuTreeByRoleId(ID);
        List<Tree> trees = toMenuTreeData(menus);
        return trees;
    }

    /**
     * 更新菜单权限
     *
     * @param mapParam
     * @return
     * @throws Exception
     */
    @PutMapping("/updateRoleMenuPermission")
    @RequiresPermissions("SYSTEM:ROLE_SET_PERMISSION")
    @SystemControllerLog(useType = UseType.USE, event = "设置角色菜单权限")
    @ResponseBody
    public ResultState updateRoleMenuPermission(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = roleService.updateRoleMenuPermission(mapParam);
        return resultState(resultMap);
    }

    /**
     * 获取按钮列表
     *
     * @param ID
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/buttonTree/{ID}/{SM_ID}")
    @RequiresPermissions("SYSTEM:ROLE_SET_PERMISSION")
    public String buttonTree(@PathVariable("ID") String ID, @PathVariable("SM_ID") String SM_ID, Model model) throws Exception {
        model.addAttribute("ID", ID);
        model.addAttribute("SM_ID", SM_ID);
        return "admin/system/role/selectButton";
    }

    /**
     * 拿到按钮节点数据
     *
     * @param ID 角色id
     * @param ID 菜单id
     * @return
     * @throws Exception
     */
    @GetMapping("/getButtonTreeData/{ID}/{SM_ID}")
    @RequiresPermissions("SYSTEM:ROLE_SET_PERMISSION")
    @ResponseBody
    public List<Tree> getButtonTreeData(@PathVariable("ID") String ID, @PathVariable("SM_ID") String SM_ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(2);
        mapParam.put("SR_ID", ID);
        mapParam.put("SM_ID", SM_ID);
        List<Tree> buttons = roleService.selectRoleTree(mapParam);

        return buttons;
    }

    /**
     * 更新按钮权限
     *
     * @param mapParam
     * @return
     * @throws Exception
     */
    @PutMapping("/updateRoleButtonPermission")
    @RequiresPermissions("SYSTEM:ROLE_SET_PERMISSION")
    @SystemControllerLog(useType = UseType.USE, event = "设置角色菜单按钮权限")
    @ResponseBody
    public ResultState updateRoleButtonPermission(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = roleService.updateRoleButtonPermission(mapParam);
        return resultState(resultMap);
    }


    @DeleteMapping("/delete/{ID}")
    @RequiresPermissions("SYSTEM:ROLE_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除角色")
    @ResponseBody
    public ResultState delete(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = roleService.deleteRole(mapParam);
        return resultState(resultMap);
    }
}
