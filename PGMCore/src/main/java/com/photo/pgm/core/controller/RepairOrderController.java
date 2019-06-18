/**
 * 
 */
package com.photo.pgm.core.controller;

import java.io.File;
import java.util.List;

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
import com.photo.pgm.core.enums.AdjustType;
import com.photo.pgm.core.enums.BreakdownReason;
import com.photo.pgm.core.enums.RepairStatus;
import com.photo.pgm.core.enums.RepairType;
import com.photo.pgm.core.model.RepairOrder;
import com.photo.pgm.core.model.UserProject;
import com.photo.pgm.core.service.RepairOrderService;
import com.photo.pgm.core.service.RepairSettleAccountService;
import com.photo.pgm.core.service.UserProjectService;
import com.photo.pgm.core.service.YearBudgetsService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/repairOrder/form")
public class RepairOrderController extends AbsCodeEntityController<RepairOrder> {

	private final static String PAGE_FORM_PATH = "project/repairOrder/repairOrder";
	private final static String PATH = "project/repairOrder/form/";
	
	@Autowired private RepairOrderService repairOrderService;
	@Autowired private YearBudgetsService yearBudgetsService;
	@Autowired private RepairSettleAccountService repairSettleAccountService;
	@Autowired private UserProjectService userProjectService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("repairOrder:show")
	public String show(@ModelAttribute("entity") RepairOrder repairOrder, ModelMap modelMap) {
//		YearBudgets yb = yearBudgetsService.findByYears(Calendar.getInstance().get(Calendar.YEAR));
//		repairOrder.setYearBudget(yb == null ? 0 : yb.getBudgets());
//		Double totalActualAmount = repairOrderService.findActualAmount4Small(repairOrder.getCorporation().getId());
//		Double totalSettleAmount = repairSettleAccountService.findTotalSettleAccount4(repairOrder.getCorporation().getId());
//		repairOrder.setTotalRepairAmount(totalActualAmount + totalSettleAmount);
		if (!repairOrder.isNewEntity()) {
			modelMap.addAttribute("isHiddePendingBtn", !repairOrder.getCreatedBy().equals(ThreadLocalUtils.getCurrentUser())); // 如果当前用户不是经办人 隐藏送审按钮
		} else {
			modelMap.addAttribute("isHiddePendingBtn", true); //  如果当前用户不是经办人 隐藏送审按钮
		}
		if (Strings.isEmpty(repairOrder.getProcessInstanceId())) {
			modelMap.addAttribute("isEdit", false); // 没有流程实例，可以修改表单合同号
			modelMap.addAttribute("isShowFlowBtn", false); // 没有流程实例，不显示流程审批驳回按钮
		} else {
			Task task = repairOrderService.getActivityTask(repairOrder, ThreadLocalUtils.getCurrentUser().getLoginName()); // 获取当前登录用户的任务
			if (task == null) {
//				modelMap.addAttribute("isEdit", true); // 没有任务,不能编辑表单修改合同号
				modelMap.addAttribute("isShowFlowBtn", false); // 没有流程实例，不显示流程审批驳回按钮
			} else {
//				modelMap.addAttribute("isEdit", true); // 有任务存在，判断是否可以编辑表单合同编号
				modelMap.addAttribute("isShowFlowBtn", true); // 有任务存在，显示流程审批驳回按钮
				modelMap.addAttribute("taskName", task.getName()); // 设置按钮名称
				modelMap.addAttribute("taskCode", task.getTaskDefinitionKey()); // 设置按钮名称
				modelMap.addAttribute("taskId", task.getId()); // 设置任务ID用于提交表单时获取任务
			}
		}
		User user = ThreadLocalUtils.getCurrentUser();
		modelMap.addAttribute("repairStatus", RepairStatus.getRepairStatus());
		modelMap.addAttribute("breakdownReason", BreakdownReason.getBreakdownReason());
		modelMap.addAttribute("repairType", RepairType.getRepairType());
		modelMap.addAttribute("adjustType", AdjustType.getAdjustTypes());
		List<UserProject> upList = userProjectService.findByUsersAndCorporation(user, ThreadLocalUtils.getCurrentCorporation());
		StringBuffer ids = new StringBuffer();
		for (UserProject up : upList) {
			if (ids.length() == 0) {
				ids.append(up.getPetrolStation().getId());
			} else {
				ids.append(",").append(up.getPetrolStation().getId());
			}
		}
		modelMap.addAttribute("ids", ids.toString());
		if (repairOrder.isNewEntity()) {
			modelMap.addAttribute("disabled", false);
		} else {
			if (RepairStatus.Draft.equals(repairOrder.getRepairStatus())) {
				if (!user.getLoginName().equals(repairOrder.getCreatedBy().getLoginName())) {
					modelMap.addAttribute("disabled", true);
				} else {
					modelMap.addAttribute("disabled", false);
				}
			} else {
				modelMap.addAttribute("disabled", true);
			}
		}
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping("{id}/apply")
	@RequiresPermissions("repairOrder:apply")
	public ModelAndView save(HttpServletRequest request, @ModelAttribute("entity") RepairOrder repairOrder, 
								@RequestParam(value="archive1" , required = false) MultipartFile archive1,
								@RequestParam(value="archive2" , required = false) MultipartFile archive2,
								@RequestParam(value="archive3" , required = false) MultipartFile archive3,
								ModelMap modelMap) {
		saveRepairOrder(repairOrder, archive1, archive2, archive3, request);
		return redirectTo(PATH + repairOrder.getId() + "/show", modelMap);
	}

	@RequestMapping("{id}/ok")
	@RequiresPermissions("repairOrder:ok")
	public ModelAndView ok(HttpServletRequest request, @ModelAttribute("entity") RepairOrder repairOrder, 
							@RequestParam(value="archive1" , required = false) MultipartFile archive1,
							@RequestParam(value="archive2" , required = false) MultipartFile archive2,
							@RequestParam(value="archive3" , required = false) MultipartFile archive3,
							ModelMap modelMap) {
		if(saveRepairOrder(repairOrder, archive1, archive2, archive3, request)){
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(repairOrder.getId()) ? NEW_ENTITY_ID : repairOrder.getId()) + "/show", modelMap);
	}
	
	@RequestMapping("{id}/send")
	@RequiresPermissions("repairOrder:send")
	public ModelAndView send(HttpServletRequest request, @ModelAttribute("entity") RepairOrder repairOrder, ModelMap modelMap) {
		repairOrderService.send(repairOrder);
		return redirectTo(PATH + repairOrder.getId() + "/show", modelMap);
	}
	
	@RequestMapping("{id}/{taskId}/approve")
	@RequiresPermissions("repairOrder:approve")
	public ModelAndView approve(HttpServletRequest request, 
								@PathVariable(value="taskId") String taskId,
								@ModelAttribute("entity") RepairOrder repairOrder, 
								@RequestParam(value="archive4" , required = false) MultipartFile archive4,
								@RequestParam(value="archive5" , required = false) MultipartFile archive5,
								@RequestParam(value="archive6" , required = false) MultipartFile archive6,
								@RequestParam(value="archive7" , required = false) MultipartFile archive7,
								@RequestParam(value="archive8" , required = false) MultipartFile archive8,
								@RequestParam(value="archive9" , required = false) MultipartFile archive9,
								ModelMap modelMap) {
		String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/repairOrder");
		getEntityService().approve(repairOrder, taskId,archive4, archive5, archive6, archive7, archive8, archive9, fileDir);
		return redirectTo(PATH + repairOrder.getId() + "/show", modelMap);
	}
	
	@RequestMapping("{id}/{taskId}/reject")
	@RequiresPermissions("repairOrder:reject")
	public ModelAndView reject(HttpServletRequest request, 
								@PathVariable(value="taskId") String taskId,
								@ModelAttribute("entity") RepairOrder repairOrder, ModelMap modelMap) {
		getEntityService().reject(repairOrder, taskId);
		return redirectTo(PATH + repairOrder.getId() + "/show", modelMap);
	}
	
	@RequestMapping("{id}/downLoad")
	@RequiresPermissions("repairOrder:downLoad")
	@ResponseBody
	public void downLoad(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("entity") RepairOrder repairOrder, @RequestParam Integer index) {
		String folder = "repairOrder" + repairOrder.getId();
		String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/repairOrder");
		String filePath = repairOrder.getFilePath(index);
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		File file = new File(fileDir + "/" + folder + "/" +fileName);
		downLoadFile(response, fileName, file);
	}
	
	private boolean saveRepairOrder(RepairOrder repairOrder, MultipartFile archive1, MultipartFile archive2, MultipartFile archive3, HttpServletRequest request) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			if (repairOrder.isNewEntity()) {
				// 给维修单添加序号
				String seqNo = repairOrderService.getSequeneNumber("project.repairOrder_sequence");
				repairOrder.setSeq(repairOrderService.findMaxSeq(repairOrder.getCorporation().getId()) + 1);
				repairOrder.setCode(repairOrder.getPetrolStation().getCode() + "-" + repairOrder.getRepairType().getText() + "-" + seqNo);
			}
			String code = repairOrder.getCode();
			repairOrder.setCode(code.substring(0, code.indexOf("-") + 1) + repairOrder.getRepairType().getText() + code.substring(code.lastIndexOf("-")));
			String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/repairOrder");
			getEntityService().save(repairOrder, archive1, archive2, archive3, fileDir);
		}
		return isSucceed;
	}
	
	@Override
	protected RepairOrderService getEntityService() {
		return repairOrderService;
	}

	@Override
	protected RepairOrder newInstance(WebRequest request) {
		return new RepairOrder();
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC20");
	}
}
