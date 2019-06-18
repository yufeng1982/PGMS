/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.service.entity.AbsService;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsController;
import com.photo.bas.core.web.view.JSONView;
import com.photo.pgm.core.model.Contract;
import com.photo.pgm.core.model.RepairOrder;
import com.photo.pgm.core.model.RepairSettleAccount;
import com.photo.pgm.core.service.ActivitiFlowService;
import com.photo.pgm.core.service.ContractService;
import com.photo.pgm.core.service.RepairOrderService;
import com.photo.pgm.core.service.RepairSettleAccountService;


/**
 * @author FengYu
 *
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/pgm/project/todotask/list")
public class ActivivtiFlowListController extends AbsController {

	@Autowired private ActivitiFlowService activitiFlowService;
	@Autowired private ContractService contractService;
	@Autowired private RepairOrderService repairOrderService;
	@Autowired private RepairSettleAccountService repairSettleAccountService;
	
	@RequestMapping
	public String show(ModelMap modelMap) {
		modelMap.addAttribute("isHistory", false);
		return "project/task/toDoTasks";
	}
	
	@RequestMapping("/showHistory")
	public String showHistory(ModelMap modelMap){
		modelMap.addAttribute("isHistory", true);
		return "project/task/doneTasks";
	}
	
	@RequestMapping("/repair")
	public String repair(ModelMap modelMap) {
		modelMap.addAttribute("isHistory", false);
		return "project/task/toDoReapirTasks";
	}
	
	@RequestMapping("/showRepairHistory")
	public String showRepairHistor(ModelMap modelMap){
		modelMap.addAttribute("isHistory", true);
		return "project/task/doneRepairTasks";
	}
	
	@RequestMapping("/repairSettleAccount")
	public String repairSettleAccount(ModelMap modelMap) {
		modelMap.addAttribute("isHistory", false);
		return "project/task/toDoRSATasks";
	}
	
	@RequestMapping("/showRepairSettleAccountHistory")
	public String showRepairSettleAccountHistory(ModelMap modelMap){
		modelMap.addAttribute("isHistory", true);
		return "project/task/doneRSATasks";
	}
	
	@RequestMapping("json")
	@ResponseBody
	public ModelAndView json(ModelMap modelMap, @RequestParam Integer beginIndex){
		List<Task> totaltTaskList = activitiFlowService.getTaskList4currentUser4Contract(ThreadLocalUtils.getCurrentUser().getLoginName());
		List<Task> pageTaskList = activitiFlowService.getTaskList4currentUser4Contract(ThreadLocalUtils.getCurrentUser().getLoginName(), beginIndex);
		return toJSONView(pageTaskList, totaltTaskList);
	} 
	
	@RequestMapping("historyJson")
	@ResponseBody
	public ModelAndView historyJson(ModelMap modelMap,@RequestParam Integer beginIndex){
		List<HistoricTaskInstance> totalHistoryTaskList = activitiFlowService.getHistoryTaskList4currentUser4Contract(ThreadLocalUtils.getCurrentUser().getLoginName());
		List<HistoricTaskInstance> pageHistoryTaskList = activitiFlowService.getHistoryTaskList4currentUser4Contract(ThreadLocalUtils.getCurrentUser().getLoginName(), beginIndex);
		return toJSONView4Hitory(pageHistoryTaskList, totalHistoryTaskList);
	} 
	
	@RequestMapping("history4PIJson")
	@ResponseBody
	public ModelAndView history4PIJson(@RequestParam String entityId, ModelMap modelMap){
		Contract contract = contractService.get(entityId);
		if (contract == null) return toJSONView4HitoryPI(new ArrayList<HistoricTaskInstance>());
		List<HistoricTaskInstance> historyTaskList = activitiFlowService.getHistoryTaskList4CurrentPI(entityId);
		return toJSONView4HitoryPI(historyTaskList);
	} 
	
	@RequestMapping("repairJson")
	@ResponseBody
	public ModelAndView repairJson(ModelMap modelMap, @RequestParam Integer beginIndex){
		List<Task> totaltTaskList = activitiFlowService.getTaskList4currentUser4Repair(ThreadLocalUtils.getCurrentUser().getLoginName());
		List<Task> pageTaskList = activitiFlowService.getTaskList4currentUser4Repair(ThreadLocalUtils.getCurrentUser().getLoginName(), beginIndex);
		return toJSONView4Repair(pageTaskList, totaltTaskList);
	} 
	
	@RequestMapping("historyRepairJson")
	@ResponseBody
	public ModelAndView historyRepairJson(ModelMap modelMap,@RequestParam Integer beginIndex){
		List<HistoricTaskInstance> totalHistoryTaskList = activitiFlowService.getHistoryTaskList4currentUser4Repair(ThreadLocalUtils.getCurrentUser().getLoginName());
		List<HistoricTaskInstance> pageHistoryTaskList = activitiFlowService.getHistoryTaskList4currentUser4Repair(ThreadLocalUtils.getCurrentUser().getLoginName(), beginIndex);
		return toJSONView4Hitory4Repair(pageHistoryTaskList, totalHistoryTaskList);
	} 
	
	@RequestMapping("repairSettleAccountJson")
	@ResponseBody
	public ModelAndView repairSettleAccountJson(ModelMap modelMap, @RequestParam Integer beginIndex){
		List<Task> totaltTaskList = activitiFlowService.getTaskList4currentUser4RSA(ThreadLocalUtils.getCurrentUser().getLoginName());
		List<Task> pageTaskList = activitiFlowService.getTaskList4currentUser4RSA(ThreadLocalUtils.getCurrentUser().getLoginName(), beginIndex);
		return toJSONView4RSA(pageTaskList, totaltTaskList);
	} 
	
	@RequestMapping("historyRepairSettleAccountJson")
	@ResponseBody
	public ModelAndView historyRepairSettleAccountJson(ModelMap modelMap,@RequestParam Integer beginIndex){
		List<HistoricTaskInstance> totalHistoryTaskList = activitiFlowService.getHistoryTaskList4currentUser4RSA(ThreadLocalUtils.getCurrentUser().getLoginName());
		List<HistoricTaskInstance> pageHistoryTaskList = activitiFlowService.getHistoryTaskList4currentUser4RSA(ThreadLocalUtils.getCurrentUser().getLoginName(), beginIndex);
		return toJSONView4Hitory4RSA(pageHistoryTaskList, totalHistoryTaskList);
	} 
	
	private ModelAndView toJSONView4HitoryPI(List<HistoricTaskInstance> historyTaskList) {
		JSONObject jso = new JSONObject();
		jso.put("total", historyTaskList.size());
		jso.put("data", buildJSONArray4HistroyPI(historyTaskList));
		return new ModelAndView(new JSONView(jso.toString()));
	}
	
	private ModelAndView toJSONView4Hitory(List<HistoricTaskInstance> pagehistoryTaskList, List<HistoricTaskInstance> totalhistoryTaskList) {
		JSONObject jso = new JSONObject();
		jso.put("total", totalhistoryTaskList.size());
		jso.put("data", buildJSONArray4Histroy(pagehistoryTaskList));
		return new ModelAndView(new JSONView(jso.toString()));
	}
	
	private ModelAndView toJSONView(List<Task> pageTaskList,List<Task> totalTaskList) {
		JSONObject jso = new JSONObject();
		jso.put("total", totalTaskList.size());
		jso.put("data", buildJSONArray(pageTaskList));
		return new ModelAndView(new JSONView(jso.toString()));
	}
	
	private ModelAndView toJSONView4Repair(List<Task> pageTaskList,List<Task> totalTaskList) {
		JSONObject jso = new JSONObject();
		jso.put("total", totalTaskList.size());
		jso.put("data", buildJSONArray4Repair(pageTaskList));
		return new ModelAndView(new JSONView(jso.toString()));
	}
	
	private ModelAndView toJSONView4Hitory4Repair(List<HistoricTaskInstance> pagehistoryTaskList, List<HistoricTaskInstance> totalhistoryTaskList) {
		JSONObject jso = new JSONObject();
		jso.put("total", totalhistoryTaskList.size());
		jso.put("data", buildJSONArray4Histroy4Repair(pagehistoryTaskList));
		return new ModelAndView(new JSONView(jso.toString()));
	}
	
	private ModelAndView toJSONView4RSA(List<Task> pageTaskList,List<Task> totalTaskList) {
		JSONObject jso = new JSONObject();
		jso.put("total", totalTaskList.size());
		jso.put("data", buildJSONArray4RSA(pageTaskList));
		return new ModelAndView(new JSONView(jso.toString()));
	}
	
	private ModelAndView toJSONView4Hitory4RSA(List<HistoricTaskInstance> pagehistoryTaskList, List<HistoricTaskInstance> totalhistoryTaskList) {
		JSONObject jso = new JSONObject();
		jso.put("total", totalhistoryTaskList.size());
		jso.put("data", buildJSONArray4Histroy4RSA(pagehistoryTaskList));
		return new ModelAndView(new JSONView(jso.toString()));
	}
	
	private JSONArray buildJSONArray(List<Task> taskList) {
		JSONArray ja = new JSONArray();
		for (Task task : taskList) {
			JSONObject jo = new JSONObject();
			ProcessInstance pi = activitiFlowService.getProcessInstance(task.getProcessInstanceId());
			String entityId = (String) activitiFlowService.getProcessVariables(pi.getId()).get("entityId");
			Contract contract = contractService.get(entityId);
			if (contract == null) continue;
			jo.put("taskId",task.getId());
			jo.put("taskName",task.getName());
			jo.put("entityId", entityId);
			jo.put("entityCode", contract.getCode());
			jo.put("entityName", contract.getName());
			jo.put("projectName", contract.getPetrolStation().getName());
			jo.put("piId", pi.getId());
			jo.put("status", ResourceUtils.getText("Com.Ready"));
			jo.put("completeTime", FormatUtils.dateTimeValue(task.getCreateTime()));
			ja.put(jo);
		}
		return ja;
	}

	private JSONArray buildJSONArray4Histroy(List<HistoricTaskInstance> historyTaskList) {
		JSONArray ja = new JSONArray();
		for (HistoricTaskInstance task : historyTaskList) {
			JSONObject jo = new JSONObject();
			HistoricProcessInstance hpi = activitiFlowService.getHistoricProcessInstance(task.getProcessInstanceId());
			String entityId = (String) activitiFlowService.getHistoryProcessVariables(hpi.getId()).get("entityId");
			Contract contract = contractService.get(entityId);
			if (contract == null) continue;
			jo.put("taskId",task.getId());
			jo.put("taskName",task.getName());
			jo.put("entityId", entityId);
			jo.put("entityCode", contract.getCode());
			jo.put("entityName", contract.getName());
			jo.put("projectName", contract.getPetrolStation().getName());
			jo.put("piId", hpi.getId());
			jo.put("status", task.getDeleteReason().equals("completed") ? ResourceUtils.getText("Com.Completed") : task.getDeleteReason());
			jo.put("completeTime", FormatUtils.dateTimeValue(task.getEndTime()));
			ja.put(jo);
		}
		return ja;
	}
	
	private JSONArray buildJSONArray4HistroyPI(List<HistoricTaskInstance> historyTaskList) {
		JSONArray ja = new JSONArray();
		for (HistoricTaskInstance task : historyTaskList) {
			JSONObject jo = new JSONObject();
			jo.put("taskId",task.getId());
			jo.put("taskName",task.getName());
			jo.put("result", task.getDeleteReason().equals("completed") ? ResourceUtils.getText("Com.Passed") : ResourceUtils.getText("Com.Reject"));
			jo.put("completeTime", FormatUtils.dateTimeValue(task.getEndTime()));
			ja.put(jo);
		}
		return ja;
	}
	
	
	private JSONArray buildJSONArray4Repair(List<Task> taskList) {
		JSONArray ja = new JSONArray();
		for (Task task : taskList) {
			JSONObject jo = new JSONObject();
			ProcessInstance pi = activitiFlowService.getProcessInstance(task.getProcessInstanceId());
			String entityId = (String) activitiFlowService.getProcessVariables(pi.getId()).get("entityId");
			RepairOrder repairOrder = repairOrderService.get(entityId);
			if (repairOrder == null) continue;
			jo.put("taskId",task.getId());
			jo.put("taskName",task.getName());
			jo.put("entityId", entityId);
			jo.put("entityCode", repairOrder.getCode());
			jo.put("repairType", FormatUtils.displayString(repairOrder.getRepairType()));
			jo.put("repairItemName", repairOrder.getAsset().getName());
			jo.put("piId", pi.getId());
			jo.put("status", ResourceUtils.getText("Com.Ready"));
			jo.put("completeTime", FormatUtils.dateTimeValue(task.getCreateTime()));
			ja.put(jo);
		}
		return ja;
	}
	
	private JSONArray buildJSONArray4Histroy4Repair(List<HistoricTaskInstance> historyTaskList) {
		JSONArray ja = new JSONArray();
		for (HistoricTaskInstance task : historyTaskList) {
			JSONObject jo = new JSONObject();
			HistoricProcessInstance hpi = activitiFlowService.getHistoricProcessInstance(task.getProcessInstanceId());
			String entityId = (String) activitiFlowService.getHistoryProcessVariables(hpi.getId()).get("entityId");
			RepairOrder repairOrder = repairOrderService.get(entityId);
			if (repairOrder == null) continue;
			jo.put("taskId",task.getId());
			jo.put("taskName",task.getName());
			jo.put("entityId", entityId);
			jo.put("entityCode", repairOrder.getCode());
			jo.put("repairType", FormatUtils.displayString(repairOrder.getRepairType()));
			jo.put("repairItemName", repairOrder.getAsset().getName());
			jo.put("piId", hpi.getId());
			jo.put("status", task.getDeleteReason().equals("completed") ? ResourceUtils.getText("Com.Completed") : task.getDeleteReason());
			jo.put("completeTime", FormatUtils.dateTimeValue(task.getEndTime()));
			ja.put(jo);
		}
		return ja;
	}
	
	private JSONArray buildJSONArray4RSA(List<Task> taskList) {
		JSONArray ja = new JSONArray();
		for (Task task : taskList) {
			JSONObject jo = new JSONObject();
			ProcessInstance pi = activitiFlowService.getProcessInstance(task.getProcessInstanceId());
			String entityId = (String) activitiFlowService.getProcessVariables(pi.getId()).get("entityId");
			RepairSettleAccount repairSettleAccount = repairSettleAccountService.get(entityId);
			if (repairSettleAccount == null) continue;
			jo.put("taskId",task.getId());
			jo.put("taskName",task.getName());
			jo.put("entityId", entityId);
			jo.put("entityCode", repairSettleAccount.getRepairOrder().getCode());
			jo.put("repairType", FormatUtils.displayString(repairSettleAccount.getRepairOrder().getRepairType()));
			jo.put("repairItemName", repairSettleAccount.getRepairOrder().getAsset().getName());
			jo.put("piId", pi.getId());
			jo.put("status", ResourceUtils.getText("Com.Ready"));
			jo.put("completeTime", FormatUtils.dateTimeValue(task.getCreateTime()));
			ja.put(jo);
		}
		return ja;
	}
	
	private JSONArray buildJSONArray4Histroy4RSA(List<HistoricTaskInstance> historyTaskList) {
		JSONArray ja = new JSONArray();
		for (HistoricTaskInstance task : historyTaskList) {
			JSONObject jo = new JSONObject();
			HistoricProcessInstance hpi = activitiFlowService.getHistoricProcessInstance(task.getProcessInstanceId());
			String entityId = (String) activitiFlowService.getHistoryProcessVariables(hpi.getId()).get("entityId");
			RepairSettleAccount repairSettleAccount = repairSettleAccountService.get(entityId);
			if (repairSettleAccount == null) continue;
			jo.put("taskId",task.getId());
			jo.put("taskName",task.getName());
			jo.put("entityId", entityId);
			jo.put("entityCode", repairSettleAccount.getRepairOrder().getCode());
			jo.put("repairType", FormatUtils.displayString(repairSettleAccount.getRepairOrder().getRepairType()));
			jo.put("repairItemName", repairSettleAccount.getRepairOrder().getAsset().getName());
			jo.put("piId", hpi.getId());
			jo.put("status", task.getDeleteReason().equals("completed") ? ResourceUtils.getText("Com.Completed") : task.getDeleteReason());
			jo.put("completeTime", FormatUtils.dateTimeValue(task.getEndTime()));
			ja.put(jo);
		}
		return ja;
	}
	
	@Override
	protected AbsService getEntityService() {
		return null;
	}
}
