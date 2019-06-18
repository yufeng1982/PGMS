/**
 * 
 */
package com.photo.pgm.core.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsCodeEntityController;
import com.photo.pgm.core.enums.BreakdownReason;
import com.photo.pgm.core.enums.RepairSettleAccountStatus;
import com.photo.pgm.core.enums.RepairStatus;
import com.photo.pgm.core.enums.RepairType;
import com.photo.pgm.core.model.RepairSettleAccount;
import com.photo.pgm.core.service.RepairOrderService;
import com.photo.pgm.core.service.RepairSettleAccountService;
import com.photo.pgm.core.service.YearBudgetsService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/repairSettleAccount/form")
public class RepairSettleAccountController extends AbsCodeEntityController<RepairSettleAccount> {

	private final static String PAGE_FORM_PATH = "project/repairSettleAccount/repairSettleAccount";
	private final static String PATH = "project/repairSettleAccount/form/";
	
	@Autowired private RepairSettleAccountService repairSettleAccountService;
	@Autowired private RepairOrderService repairOrderService;
	@Autowired private YearBudgetsService yearBudgetsService;
	
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("repairSettleAccount:show")
	public String show(@ModelAttribute("entity") RepairSettleAccount repairSettleAccount,ModelMap modelMap) {
		User user = ThreadLocalUtils.getCurrentUser();
		modelMap.addAttribute("user", user.getId()); 
//		YearBudgets yb = yearBudgetsService.findByYears(Calendar.getInstance().get(Calendar.YEAR));
//		repairSettleAccount.setYearBudget(yb == null ? 0 : yb.getBudgets());
//		Double totalActualAmount = repairOrderService.findActualAmount4Small(repairSettleAccount.getCorporation().getId());
//		Double totalSettleAmount = repairSettleAccountService.findTotalSettleAccount4(repairSettleAccount.getCorporation().getId());
//		repairSettleAccount.setHistorySettle(totalActualAmount + totalSettleAmount);
		
		if (!repairSettleAccount.isNewEntity()) {
			modelMap.addAttribute("isHiddePendingBtn", !repairSettleAccount.getCreatedBy().equals(user)); // 如果当前用户不是经办人 隐藏送审按钮
		} else {
			modelMap.addAttribute("isHiddePendingBtn", true); //  如果当前用户不是经办人 隐藏送审按钮
		}
		if (Strings.isEmpty(repairSettleAccount.getProcessInstanceId())) {
			modelMap.addAttribute("isShowFlowBtn", false); // 没有流程实例，不显示流程审批驳回按钮
		} else {
			Task task = repairSettleAccountService.getActivityTask(repairSettleAccount, user.getLoginName()); // 获取当前登录用户的任务
			if (task == null) {
				modelMap.addAttribute("isShowFlowBtn", false); // 没有流程实例，不显示流程审批驳回按钮
			} else {
				modelMap.addAttribute("isShowFlowBtn", true); // 有任务存在，显示流程审批驳回按钮
				modelMap.addAttribute("taskName", task.getName()); // 设置按钮名称
				modelMap.addAttribute("taskCode", task.getTaskDefinitionKey()); // 设置按钮名称
				modelMap.addAttribute("taskId", task.getId()); // 设置任务ID用于提交表单时获取任务
			}
		}
		
		if (repairSettleAccount.isNewEntity()) {
			modelMap.addAttribute("disabled", false);
		} else {
			if (RepairSettleAccountStatus.Draft.equals(repairSettleAccount.getRepairSettleAccountStatus())) {
				if (!user.getLoginName().equals(repairSettleAccount.getCreatedBy().getLoginName())) {
					modelMap.addAttribute("disabled", true);
				} else {
					modelMap.addAttribute("disabled", false);
				}
			} else {
				modelMap.addAttribute("disabled", true);
			}
		}
		
		modelMap.addAttribute("repairStatus", RepairStatus.getRepairStatus());
		modelMap.addAttribute("breakdownReason", BreakdownReason.getBreakdownReason());
		modelMap.addAttribute("repairType", RepairType.getRepairType());
		modelMap.addAttribute("repairSettleAccountStatus", RepairSettleAccountStatus.getRepairSettleAccountStatus());
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping("{id}/apply")
	@RequiresPermissions("repairSettleAccount:apply")
	public ModelAndView save(HttpServletRequest request, @ModelAttribute("entity") RepairSettleAccount repairSettleAccount, 
								@RequestParam(value="archive1" , required = false) MultipartFile archive1,
								@RequestParam(value="archive2" , required = false) MultipartFile archive2,
								ModelMap modelMap) {
		saveRepairSettleAccount(repairSettleAccount, archive1, archive2, request);
		return redirectTo(PATH + repairSettleAccount.getId() + "/show", modelMap);
	}
	
	@RequestMapping("{id}/ok")
	@RequiresPermissions("repairSettleAccount:ok")
	public ModelAndView ok(HttpServletRequest request, 
							@ModelAttribute("entity") RepairSettleAccount repairSettleAccount, 
							@RequestParam(value="archive1" , required = false) MultipartFile archive1,
							@RequestParam(value="archive2" , required = false) MultipartFile archive2,
							ModelMap modelMap) {
		if(saveRepairSettleAccount(repairSettleAccount, archive1, archive2, request)){
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(repairSettleAccount.getId()) ? NEW_ENTITY_ID : repairSettleAccount.getId()) + "/show", modelMap);
	}
	
	@RequestMapping("{id}/send")
	@RequiresPermissions("repairOrder:send")
	public ModelAndView send(HttpServletRequest request, @ModelAttribute("entity") RepairSettleAccount repairSettleAccount, ModelMap modelMap) {
		getEntityService().send(repairSettleAccount);
		return redirectTo(PATH + repairSettleAccount.getId() + "/show", modelMap);
	}
	
	@RequestMapping("{id}/{taskId}/approve")
	@RequiresPermissions("repairOrder:approve")
	public ModelAndView approve(HttpServletRequest request, 
								@PathVariable(value="taskId") String taskId,
								@ModelAttribute("entity") RepairSettleAccount repairSettleAccount, 
								ModelMap modelMap) {
		getEntityService().approve(repairSettleAccount, taskId);
		return redirectTo(PATH + repairSettleAccount.getId() + "/show", modelMap);
	}
	
	@RequestMapping("{id}/{taskId}/reject")
	@RequiresPermissions("repairOrder:reject")
	public ModelAndView reject(HttpServletRequest request, 
								@PathVariable(value="taskId") String taskId,
								@ModelAttribute("entity") RepairSettleAccount repairSettleAccount, ModelMap modelMap) {
		getEntityService().reject(repairSettleAccount, taskId);
		return redirectTo(PATH + repairSettleAccount.getId() + "/show", modelMap);
	}
	
	@RequestMapping("{id}/downLoad")
	@RequiresPermissions("repairSettleAccount:downLoad")
	@ResponseBody
	public void downLoad(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("entity") RepairSettleAccount repairSettleAccount, @RequestParam Integer index) {
		String folder = "repairSettleAccount" + repairSettleAccount.getId();
		String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/repairSettleAccount");
		String filePath = repairSettleAccount.getFilePath(index);
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		File file = new File(fileDir + "/" + folder + "/" +fileName);
		downLoadFile(response, fileName, file);
	}
	
	private boolean saveRepairSettleAccount(RepairSettleAccount repairSettleAccount, MultipartFile archive1, MultipartFile archive2, HttpServletRequest request) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/repairSettleAccount");
			getEntityService().save(repairSettleAccount, archive1, archive2, fileDir);
		}
		return isSucceed;
	}
	
	@Override
	protected RepairSettleAccountService getEntityService() {
		return repairSettleAccountService;
	}

	@Override
	protected RepairSettleAccount newInstance(WebRequest request) {
		return new RepairSettleAccount();
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC21");
	}
	
}
