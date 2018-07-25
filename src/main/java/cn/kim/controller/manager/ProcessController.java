package cn.kim.controller.manager;

import cn.kim.common.annotation.NotEmptyLogin;
import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.annotation.Token;
import cn.kim.common.annotation.Validate;
import cn.kim.common.attr.MagicValue;
import cn.kim.common.eu.ProcessType;
import cn.kim.common.eu.UseType;
import cn.kim.entity.DataTablesView;
import cn.kim.entity.ResultState;
import cn.kim.entity.Tree;
import cn.kim.exception.CustomException;
import cn.kim.service.MenuService;
import cn.kim.service.OperatorService;
import cn.kim.service.ProcessService;
import cn.kim.service.RoleService;
import cn.kim.tools.ProcessTool;
import cn.kim.util.DictUtil;
import cn.kim.util.FuncUtil;
import cn.kim.util.TextUtil;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/5/22
 * 流程管理
 */
@Controller
@RequestMapping("/admin/process")
public class ProcessController extends BaseController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OperatorService operatorService;

    /********   流程    ********/
    /**
     * 获取当前流程拥有的按钮
     *
     * @param ID
     * @param SPD_ID
     * @return
     * @throws Exception
     */
    @GetMapping("/showDataGridBtn")
    @NotEmptyLogin
    @ResponseBody
    public Map<String, Object> showDataGridBtn(String ID, String SPD_ID) throws Exception {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(1);
        //查询当前角色拥有的按钮
        String btnTypes = processService.showDataGridProcessBtn(ID, SPD_ID);
        resultMap.put("html", ProcessTool.getProcessButtonListHtml(btnTypes, false));
        return resultMap;
    }

    /**
     * 获取流程提交退回界面
     *
     * @param ID
     * @param SPD_ID
     * @param PROCESS_TYPE 办理类型
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/showDataGrid")
    @NotEmptyLogin
    @Token(save = true)
    public String showDataGrid(String ID, String SPD_ID, int PROCESS_TYPE, Model model) throws Exception {
        try {
            String processBtnType = ProcessType.SUBMIT.toString();
            List<Map<String, Object>> transactorList = new ArrayList<>();

            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
            paramMap.put("ID", SPD_ID);
            Map<String, Object> definition = processService.selectProcessDefinition(paramMap);
            //查询当前流程办理步骤
            paramMap.clear();
            paramMap.put("SPS_TABLE_ID", ID);
            paramMap.put("SPD_ID", SPD_ID);
            paramMap.put("SPS_IS_CANCEL", toString(STATUS_ERROR));
            Map<String, Object> schedule = processService.selectProcessSchedule(paramMap);
            //审核状态
            String SPS_AUDIT_STATUS;
            if (!isEmpty(schedule)) {
                SPS_AUDIT_STATUS = toString(schedule.get("SPS_AUDIT_STATUS"));
                if (toInt(SPS_AUDIT_STATUS) == BACK_CODE) {
                    SPS_AUDIT_STATUS = toString(schedule.get("SPS_BACK_STATUS_TRANSACTOR"));
                }
            } else {
                //查询角色拥有的最高的申报角色
                Map<String, Object> topStep = processService.processStepStartRoleTop(SPD_ID, activeUser().getRoleIds());
                if (!isEmpty(topStep)) {
                    SPS_AUDIT_STATUS = toString(topStep.get("SPS_PROCESS_STATUS"));
                } else {
                    throw new CustomException("当前用户没有提交审核权限!");
                }
            }

            paramMap.clear();
            paramMap.put("SPD_ID", SPD_ID);
            paramMap.put("SPS_PROCESS_STATUS", SPS_AUDIT_STATUS);
            Map<String, Object> step = processService.selectProcessStep(paramMap);

            //默认意见
            String DEFAULT_OPINION = "";

            Map<String, Object> nextStep = null;
            //查询下一步骤
            if (PROCESS_TYPE == ProcessType.SUBMIT.getType()) {
                //流程为提交
                nextStep = processService.processNextStep(SPD_ID, SPS_AUDIT_STATUS);
                if (isEmpty(nextStep)) {
                    throw new CustomException("没有找到下一流程步骤！");
                }
                String SR_ID = toString(nextStep.get("SR_ID"));
                //下一步是否为结束
                if (toInt(nextStep.get("SPS_PROCESS_STATUS")) != COMPLETE_CODE) {
                    //查询下一步办理人
                    if (MagicValue.ONE.equals(toString(nextStep.get("SPS_STEP_TYPE")))) {
                        //下一步为角色
                        paramMap.clear();
                        paramMap.put("ID", SR_ID);
                        Map<String, Object> role = roleService.selectRole(paramMap);

                        Map<String, Object> transactor = Maps.newHashMapWithExpectedSize(2);
                        transactor.put("KEY", role.get("ID"));
                        transactor.put("VALUE", role.get("SR_NAME"));
                        transactorList.add(transactor);
                    } else if (MagicValue.TWO.equals(toString(nextStep.get("SPS_STEP_TYPE")))) {
                        //下一步为人员
                        paramMap.clear();
                        paramMap.put("ID", nextStep.get("SR_ID"));
                        List<Map<String, Object>> operatorList = operatorService.selectOperatorByRoleId(SR_ID);
                        operatorList.forEach(map -> {
                            Map<String, Object> transactor = Maps.newHashMapWithExpectedSize(2);
                            transactor.put("KEY", map.get("ID"));
                            transactor.put("VALUE", map.get("SAI_NAME"));
                            transactorList.add(transactor);
                        });
                    }
                } else {
                    //下一步为结束
                    Map<String, Object> transactor = Maps.newHashMapWithExpectedSize(2);
                    transactor.put("KEY", "0");
                    transactor.put("VALUE", "结束");
                    transactorList.add(transactor);
                }

                DEFAULT_OPINION = "通过";
                processBtnType = ProcessType.SUBMIT.toString();
            } else if (PROCESS_TYPE == ProcessType.BACK.getType()) {
                //是否允许多级退回
                if (toInt(definition.get("IS_MULTISTAGE_BACK")) == STATUS_ERROR) {
                    //查询上一步骤办理人
                    nextStep = processService.processPrevStep(SPD_ID, SPS_AUDIT_STATUS);
                    if (isEmpty(nextStep)) {
                        throw new CustomException("没有找到下一流程步骤！");
                    }
                    //查询日志查询流程对应状态办理人
                    paramMap.clear();
                    paramMap.put("SPL_TABLE_ID", ID);
                    paramMap.put("SPS_PROCESS_STATUS", nextStep.get("SPS_PROCESS_STATUS"));
                    Map<String, Object> processLog = processService.selectProcessLog(paramMap);
                    if (isEmpty(processLog)) {
                        throw new CustomException("流程变动，请联系管理员！");
                    }
                    Map<String, Object> transactor = Maps.newHashMapWithExpectedSize(2);
                    transactor.put("KEY", processLog.get("SPL_PROCESS_STATUS") + SERVICE_SPLIT + processLog.get("SPL_SO_ID"));
                    transactor.put("VALUE", processLog.get("SPL_PROCESS_STATUS_NAME") + ":" + processLog.get("SPL_TRANSACTOR"));
                    transactorList.add(transactor);
                } else {
                    //多级退回
                    List<Map<String, Object>> nextSteps = processService.processPrevStepList(SPD_ID, SPS_AUDIT_STATUS);
                    //查询步骤对应日志的办理人,只查询提交的
                    StringBuilder SPS_PROCESS_STATUS_ARRAY = new StringBuilder();
                    nextSteps.forEach(map -> {
                        SPS_PROCESS_STATUS_ARRAY.append(map.get("SPS_PROCESS_STATUS") + ",");
                    });
                    paramMap.clear();
                    paramMap.put("SPS_ID", schedule.get("ID"));
                    paramMap.put("SPL_TABLE_ID", ID);
                    paramMap.put("SPL_PROCESS_STATUS_ARRAY", TextUtil.interceptSymbol(SPS_PROCESS_STATUS_ARRAY.toString(), ","));
                    paramMap.put("SPL_TYPE", "0");
                    paramMap.put("IS_GROUP", true);
                    List<Map<String, Object>> processLogList = processService.selectProcessLogList(paramMap);
                    if (isEmpty(processLogList)) {
                        throw new CustomException("流程变动，请联系管理员！");
                    }

                    processLogList.forEach(processLog -> {
                        Map<String, Object> transactor = Maps.newHashMapWithExpectedSize(2);
                        transactor.put("KEY", processLog.get("SPL_PROCESS_STATUS") + SERVICE_SPLIT + processLog.get("SPL_SO_ID"));
                        transactor.put("VALUE", processLog.get("SPL_PROCESS_STATUS_NAME") + ":" + processLog.get("SPL_TRANSACTOR"));
                        transactorList.add(transactor);
                    });
                }
                DEFAULT_OPINION = "退回";
                processBtnType = ProcessType.BACK.toString();
            }
            //查询流程步骤
            paramMap.clear();
            paramMap.put("SPD_ID", SPD_ID);
            List<Map<String, Object>> stepList = processService.selectProcessStepList(paramMap);
            //设置流程步骤
            String stepId = toString(step.get("ID"));
            String nextStepId = toString(nextStep.get("ID"));
            StringBuilder stepGroupName = new StringBuilder();
            stepGroupName.append(TextUtil.greaterThanHtml("开始", 3));
            FuncUtil.forEach(stepList, (index, map) -> {
                Object SPS_NAME = map.get("SPS_NAME");
                if (toString(map.get("ID")).equals(stepId)) {
                    stepGroupName.append(TextUtil.joinFirstTextSymbol("<span style='color:blue;'>" + TextUtil.greaterThanHtml(SPS_NAME + "(当前步骤)", 3) + "</span>", MagicValue.NBSP, 1));
                } else if (toString(map.get("ID")).equals(nextStepId)) {
                    if (index + 1 == stepList.size()) {
                        stepGroupName.append(TextUtil.joinFirstTextSymbol("<span style='color:red;'>" + SPS_NAME + "(下一步骤)" + "</span>", MagicValue.NBSP, 1));
                    } else {
                        stepGroupName.append(TextUtil.joinFirstTextSymbol("<span style='color:red;'>" + TextUtil.greaterThanHtml(SPS_NAME + "(下一步骤)", 3) + "</span>", MagicValue.NBSP, 1));
                    }
                } else {
                    stepGroupName.append(TextUtil.joinFirstTextSymbol(TextUtil.greaterThanHtml(SPS_NAME, 3), MagicValue.NBSP, 1));
                }
            });

            //流程步骤
            model.addAttribute("SPS_GROUP_NAME", TextUtil.interceptSymbol(stepGroupName.toString(), MagicValue.GREATER_THAN + MagicValue.GREATER_THAN + MagicValue.GREATER_THAN));
            //办理表ID
            model.addAttribute("SPS_TABLE_ID", ID);
            //下一步办理人
            model.addAttribute("TRANSACTOR_LIST", transactorList);
            //流程定义
            model.addAttribute("SPD", definition);
            //当前步骤
            model.addAttribute("STEP", step);
            //下一步步骤
            model.addAttribute("NEXT_STEP", nextStep);
            //流程办理类型
            model.addAttribute("PROCESS_TYPE", PROCESS_TYPE);
            //默认办理意见
            model.addAttribute("DEFAULT_OPINION", DEFAULT_OPINION);
            //查询项目名称
            model.addAttribute("SPS_TABLE_NAME", processService.selectProcessTableName(ID, toString(definition.get("SPD_UPDATE_TABLE")), toString(definition.get("SPD_UPDATE_NAME"))));
            //步骤名称
            model.addAttribute("STEP_NAME", DictUtil.getDictName("SYS_PROCESS_STATUS", SPS_AUDIT_STATUS));
            //下一步步骤
            model.addAttribute("processBtnType", processBtnType);
        } catch (CustomException e) {
            model.addAttribute("message", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            //错误消息
            model.addAttribute("message", "流程查询出错!");
        }
        return "admin/component/process/processHome";
    }

    /**
     * 提交流程
     *
     * @param mapParam
     * @return
     */
    @PutMapping("/submit")
    @NotEmptyLogin
    @Token(remove = true)
    @Validate(value = {"SYS_PROCESS_LOG", "SYS_PROCESS_SCHEDULE"})
    @ResponseBody
    public ResultState submit(@RequestParam Map<String, Object> mapParam) throws Exception {
        //流程办理ID
        String SPS_TABLE_ID = toString(mapParam.get("SPS_TABLE_ID"));
        //公平锁进行等待
        Map<String, Object> resultMap = ProcessTool.submitProcess(mapParam);

        return resultState(resultMap);
    }

    /**
     * 撤回流程
     *
     * @param mapParam
     * @return
     */
    @PutMapping("/withdraw")
    @NotEmptyLogin
    @Validate(value = {"SYS_PROCESS_LOG", "SYS_PROCESS_SCHEDULE"})
    @ResponseBody
    public ResultState withdraw(@RequestParam Map<String, Object> mapParam) throws Exception {
        //流程办理ID
        String SPS_TABLE_ID = toString(mapParam.get("SPS_TABLE_ID"));
        //公平锁进行等待
        Map<String, Object> resultMap = ProcessTool.withdrawProcess(mapParam);

        return resultState(resultMap);
    }

    /**
     * 流程日志
     *
     * @param ID
     * @param SPD_ID
     * @param SPS_ID
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/log")
    @NotEmptyLogin
    public String log(String ID, String SPD_ID, String SPS_ID, Model model) throws Exception {
        if (isEmpty(SPS_ID)) {
            Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(3);
            mapParam.put("SPS_TABLE_ID", ID);
            mapParam.put("SPD_ID", SPD_ID);
            mapParam.put("SPS_IS_CANCEL", toString(STATUS_ERROR));
            Map<String, Object> schedule = processService.selectProcessSchedule(mapParam);
            //流程进度ID
            SPS_ID = toString(schedule.get("ID"));
        }
        //流程进度ID
        model.addAttribute("SPS_ID", SPS_ID);

        return "admin/component/process/processLog";
    }

    /**
     * 流程日志数据
     *
     * @param SPS_ID
     * @return
     * @throws Exception
     */
    @GetMapping("/log/list")
    @NotEmptyLogin
    @ResponseBody
    public DataTablesView<?> logList(String SPS_ID) throws Exception {
        DataTablesView<Map<String, Object>> dataTablesView = new DataTablesView<>();
        //查询日志数据
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("SPS_ID", SPS_ID);
        List<Map<String, Object>> logList = processService.selectProcessLogList(mapParam);
        dataTablesView.setData(logList);
        return dataTablesView;
    }
    /********   流程定义    ********/

    /**
     * 获取流程定义列表 转为List<Tree>
     *
     * @param ID 已选角色ID
     * @return
     * @throws Exception
     */
    @GetMapping("/definition/tree")
    @RequiresPermissions(value = {"SYSTEM:MENU"}, logical = Logical.OR)
    @ResponseBody
    public List<Tree> definitionTree(String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        List<Tree> definitionTreeList = processService.selectProcessDefinitionTreeList(mapParam);

        return definitionTreeList;
    }

    @GetMapping("/definition/add")
    @RequiresPermissions("SYSTEM:PROCESS_DEFINITION_INSERT")
    @Token(save = true)
    public String addHtmlDefinition(Model model) throws Exception {
        return "admin/system/process/definition/add";
    }


    @PostMapping("/definition/add")
    @RequiresPermissions("SYSTEM:PROCESS_DEFINITION_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加流程定义")
    @Token(remove = true)
    @Validate("SYS_PROCESS_DEFINITION")
    @ResponseBody
    public ResultState addDefinition(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = processService.insertAndUpdateProcessDefinition(mapParam);

        return resultState(resultMap);
    }


    @GetMapping("/definition/update")
    @RequiresPermissions("SYSTEM:PROCESS_DEFINITION_UPDATE")
    public String updateHtmlDefinition(String SPD_ID, String SM_ID, Model model) throws Exception {
        isNotFound(SPD_ID);
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", SPD_ID);
        model.addAttribute("SPD", processService.selectProcessDefinition(mapParam));
        //查询菜单
        model.addAttribute("MENU", menuService.queryMenuById(SM_ID));
        return "admin/system/process/definition/edit";
    }

    @PutMapping("/definition/update")
    @RequiresPermissions("SYSTEM:PROCESS_DEFINITION_UPDATE_SAVE")
    @SystemControllerLog(useType = UseType.USE, event = "修改流程定义")
    @Validate("SYS_PROCESS_DEFINITION")
    @ResponseBody
    public ResultState updateDefinition(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = processService.insertAndUpdateProcessDefinition(mapParam);
        return resultState(resultMap);
    }

    @PutMapping("/definition/switchStatus")
    @RequiresPermissions("SYSTEM:PROCESS_DEFINITION_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改流程定义状态")
    @ResponseBody
    public ResultState switchStatusDefinition(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = processService.changeProcessDefinitionStatus(mapParam);

        return resultState(resultMap);
    }

    /********   流程步骤    ********/

    @GetMapping("/step/add")
    @RequiresPermissions("SYSTEM:PROCESS_STEP_INSERT")
    @Token(save = true)
    public String addHtmlProcessStep(String SPD_ID, Model model) throws Exception {
        model.addAttribute("SPD_ID", SPD_ID);
        return "admin/system/process/step/addAndEdit";
    }

    @PostMapping("/step/add")
    @RequiresPermissions("SYSTEM:PROCESS_STEP_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加流程步骤")
    @Token(remove = true)
    @Validate("SYS_PROCESS_STEP")
    @ResponseBody
    public ResultState addProcessStep(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = processService.insertAndUpdateProcessStep(mapParam);

        return resultState(resultMap);
    }

    @GetMapping("/step/update/{ID}")
    @RequiresPermissions("SYSTEM:PROCESS_STEP_UPDATE")
    public String updateHtmlProcessStep(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("SPS", processService.selectProcessStep(mapParam));
        return "admin/system/process/step/addAndEdit";
    }

    @PutMapping("/step/update")
    @RequiresPermissions("SYSTEM:PROCESS_STEP_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改流程步骤")
    @Validate("SYS_PROCESS_STEP")
    @ResponseBody
    public ResultState updateProcessStep(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = processService.insertAndUpdateProcessStep(mapParam);
        return resultState(resultMap);
    }

    @DeleteMapping("/step/delete/{ID}")
    @RequiresPermissions("SYSTEM:PROCESS_STEP_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除流程步骤")
    @ResponseBody
    public ResultState deleteProcessStep(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = processService.deleteProcessStep(mapParam);
        return resultState(resultMap);
    }

    /********   流程启动角色    ********/

    @GetMapping("/start/add")
    @RequiresPermissions("SYSTEM:PROCESS_STEP_INSERT")
    @Token(save = true)
    public String addHtmlProcessStart(String SPD_ID, Model model) throws Exception {
        model.addAttribute("SPD_ID", SPD_ID);
        return "admin/system/process/start/addAndEdit";
    }

    @PostMapping("/start/add")
    @RequiresPermissions("SYSTEM:PROCESS_STEP_INSERT")
    @SystemControllerLog(useType = UseType.USE, event = "添加流程启动角色")
    @Token(remove = true)
    @Validate("SYS_PROCESS_STEP")
    @ResponseBody
    public ResultState addProcessStart(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = processService.insertAndUpdateProcessStart(mapParam);

        return resultState(resultMap);
    }

    @GetMapping("/start/update/{ID}")
    @RequiresPermissions("SYSTEM:PROCESS_STEP_UPDATE")
    public String updateHtmlProcessStart(Model model, @PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        model.addAttribute("SPS", processService.selectProcessStart(mapParam));
        return "admin/system/process/start/addAndEdit";
    }

    @PutMapping("/start/update")
    @RequiresPermissions("SYSTEM:PROCESS_STEP_UPDATE")
    @SystemControllerLog(useType = UseType.USE, event = "修改流程启动角色")
    @Validate("SYS_PROCESS_STEP")
    @ResponseBody
    public ResultState updateProcessStart(@RequestParam Map<String, Object> mapParam) throws Exception {
        Map<String, Object> resultMap = processService.insertAndUpdateProcessStart(mapParam);
        return resultState(resultMap);
    }

    @DeleteMapping("/start/delete/{ID}")
    @RequiresPermissions("SYSTEM:PROCESS_STEP_DELETE")
    @SystemControllerLog(useType = UseType.USE, event = "删除流程启动角色")
    @ResponseBody
    public ResultState deleteProcessStart(@PathVariable("ID") String ID) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> resultMap = processService.deleteProcessStart(mapParam);
        return resultState(resultMap);
    }

    /********   流程进度    ********/

    @GetMapping("/schedule/{ID}")
    @RequiresPermissions("SYSTEM:PROCESS_SCHEDULE")
    @Token(save = true)
    public String cancelHtmlProcessSchedule(@PathVariable("ID") String ID, Model model) throws Exception {
        model.addAttribute("ID", ID);
        return "admin/system/process/schedule/addAndEdit";
    }

    @PutMapping("/schedule/cancel")
    @RequiresPermissions("SYSTEM:PROCESS_SCHEDULE_CANCEL")
    @SystemControllerLog(useType = UseType.USE, event = "作废流程进度")
    @Token(remove = true)
    @Validate("SYS_PROCESS_SCHEDULE_CANCEL")
    @ResponseBody
    public ResultState cancelProcessSchedule(@RequestParam Map<String, Object> mapParam) throws Exception {
        String ID = toString(mapParam.get("ID"));

        Map<String, Object> resultMap = ProcessTool.cancelProcess(ID, toString(mapParam.get("SPSC_REASON")));
        return resultState(resultMap);
    }

    /**
     * 流程进度作废信息
     *
     * @param ID
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/schedule/cancel/{ID}")
    @RequiresPermissions("SYSTEM:PROCESS_SCHEDULE_CANCEL_INFO")
    @Token(save = true)
    public String cancelHtmlProcessScheduleInfo(@PathVariable("ID") String ID, Model model) throws Exception {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(1);
        mapParam.put("ID", ID);
        Map<String, Object> cancel = processService.selectProcessScheduleCancel(mapParam);

        model.addAttribute("SPSC", cancel);
        return "admin/system/process/schedule/addAndEdit";
    }
}
