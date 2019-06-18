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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsCodeEntityController;
import com.photo.pgm.core.enums.ContractStatus;
import com.photo.pgm.core.enums.ContractType;
import com.photo.pgm.core.model.AssetsCategory;
import com.photo.pgm.core.model.Contract;
import com.photo.pgm.core.model.ContractCategory;
import com.photo.pgm.core.model.CostCenter;
import com.photo.pgm.core.model.FlowDefinitionLines;
import com.photo.pgm.core.model.OurSideCorporation;
import com.photo.pgm.core.service.AssetsCategoryService;
import com.photo.pgm.core.service.ContractCategoryService;
import com.photo.pgm.core.service.ContractPropertyService;
import com.photo.pgm.core.service.ContractService;
import com.photo.pgm.core.service.CostCenterService;
import com.photo.pgm.core.service.FlowDefinitionLinesService;
import com.photo.pgm.core.service.OurSideCorporationService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/contract/form")
public class ContractController extends AbsCodeEntityController<Contract> {

	private final static String PAGE_FORM_PATH = "project/contract/contract";
	private final static String PATH = "project/contract/form/";
	
	@Autowired private ContractService contractService;
	@Autowired private ContractPropertyService contractPropertyService;
	@Autowired private CostCenterService costCenterService;
	@Autowired private ContractCategoryService contractCategoryService;
	@Autowired private AssetsCategoryService assetsCategoryService;
	@Autowired private OurSideCorporationService ourSideCorporationService;
	@Autowired private FlowDefinitionLinesService flowDefinitionLinesService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("contract:show")
	public String showContract(@ModelAttribute("entity") Contract contract, ModelMap modelMap) {
		if (!contract.isNewEntity()) {
			modelMap.addAttribute("isHiddePendingBtn", !contract.getCreatedBy().equals(ThreadLocalUtils.getCurrentUser())); // 如果当前用户不是经办人 隐藏送审按钮
		} else {
			modelMap.addAttribute("isHiddePendingBtn", false); //  如果当前用户不是经办人 隐藏送审按钮
		}
		
		modelMap.addAttribute("isDisabled", contract.isDisable());// 控制全体表单是否可以编辑
		if (Strings.isEmpty(contract.getProcessInstanceId())) {
			modelMap.addAttribute("isEdit", false); // 没有流程实例，可以修改表单合同号
			modelMap.addAttribute("isShowFlowBtn", false); // 没有流程实例，不显示流程审批驳回按钮
		} else {
			Task task = contractService.getActivityTask(contract, ThreadLocalUtils.getCurrentUser().getLoginName()); // 获取当前登录用户的任务
			if (task == null) {
				modelMap.addAttribute("isEdit", true); // 没有任务,不能编辑表单修改合同号
				modelMap.addAttribute("isShowFlowBtn", false); // 没有流程实例，不显示流程审批驳回按钮
			} else {
				String taskDefinitionKey = task.getTaskDefinitionKey();
				String fdCode = taskDefinitionKey.replace("task", "");
				FlowDefinitionLines fdline = flowDefinitionLinesService.findByCodeAndFlowDefinition(fdCode, contract.getFlowDefinition());
				modelMap.addAttribute("isEdit", !fdline.isEdit()); // 有任务存在，判断是否可以编辑表单合同编号
				modelMap.addAttribute("isShowFlowBtn", true); // 有任务存在，显示流程审批驳回按钮
				modelMap.addAttribute("taskName", task.getName()); // 设置按钮名称
				modelMap.addAttribute("taskId", task.getId()); // 设置任务ID用于提交表单时获取任务
			}
		}
		modelMap.addAttribute("seqNo", contract.getSeqNo());
		buildModelMap(modelMap);
		return PAGE_FORM_PATH;
	}

	@RequestMapping("{id}/apply")
	@RequiresPermissions("contract:apply")
	public ModelAndView save(HttpServletRequest request, 
								@ModelAttribute("entity") Contract contract, ModelMap modelMap,
								@RequestParam(value="attachment" , required = false) MultipartFile attachment) {
		saveContract(contract, request, attachment);
		return redirectTo(PATH + contract.getId() + "/show", modelMap);
	}

	@RequestMapping("{id}/ok")
	@RequiresPermissions("contract:ok")
	public ModelAndView ok(HttpServletRequest request, 
							@ModelAttribute("entity") Contract contract, ModelMap modelMap, 
							@RequestParam(value="attachment" , required = false) MultipartFile attachment) {
		if(saveContract(contract, request, attachment)){
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(contract.getId()) ? NEW_ENTITY_ID : contract.getId()) + "/show", modelMap);
	}
	
	@RequestMapping("{id}/pendingSend")
	@RequiresPermissions("contract:pendingSend")
	public String pendingSend(@ModelAttribute("entity") Contract contract, ModelMap modelMap, RedirectAttributes redirectAttr) {
		if(getEntityService().isDeployed(contract)) {
			getEntityService().pendingSend(contract);
			redirectAttr.addFlashAttribute("infos", ResourceUtils.getText("Com.VContract.PendSuccess"));
		} else {
			redirectAttr.addFlashAttribute("infos", ResourceUtils.getText("Com.VContract.Cannot"));
		}
		return redirect("pgm", PATH + contract.getId() + "/show", modelMap, redirectAttr);
	}
	
	@RequestMapping("{id}/{taskId}/approve")
	@RequiresPermissions("contract:approve")
	public ModelAndView approve(HttpServletRequest request, 
								@PathVariable(value="taskId") String taskId,
								@ModelAttribute("entity") Contract contract, ModelMap modelMap,
								@RequestParam(value="attachment" , required = false) MultipartFile attachment) {
		getEntityService().approve(contract, taskId);
		return redirectTo(PATH + contract.getId() + "/show", modelMap);
	}
	
	@RequestMapping("{id}/{taskId}/reject")
	@RequiresPermissions("contract:reject")
	public ModelAndView reject(HttpServletRequest request, 
								@PathVariable(value="taskId") String taskId,
								@ModelAttribute("entity") Contract contract, ModelMap modelMap,
								@RequestParam(value="attachment" , required = false) MultipartFile attachment) {
		getEntityService().reject(contract, taskId);
		return redirectTo(PATH + contract.getId() + "/show", modelMap);
	}
	
	@RequestMapping("{id}/archive")
	@RequiresPermissions("contract:archive")
	public ModelAndView archive(HttpServletRequest request, 
								@ModelAttribute("entity") Contract contract, ModelMap modelMap,
								@RequestParam(value="archive" , required = false) MultipartFile archive) {
		String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/contract");
		getEntityService().archive(contract, archive, fileDir);
		return redirectTo(PATH + contract.getId() + "/show", modelMap);
	} 
	
	@RequestMapping("{id}/downLoad")
	@RequiresPermissions("contract:downLoad")
	@ResponseBody
	public void downLoad(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("entity") Contract contract, @RequestParam Boolean isArchive) {
		String folder = contract.getName() + contract.getId();
		String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/contract");
		String filePath = "";
		if (isArchive) {
			filePath = contract.getArchiveFilePath();
		} else {
			filePath = contract.getFilePath();
		}
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		File file = new File(fileDir + "/" + folder + "/" + fileName);
		downLoadFile(response, fileName, file);
	}
	
	private void buildModelMap(ModelMap modelMap) {
		Corporation corporation = ThreadLocalUtils.getCurrentCorporation();
		
		modelMap.addAttribute("contractPropertys", ContractType.getContractType());
		modelMap.addAttribute("contractStatus", ContractStatus.getContractStatus());
		
//		List<ContractProperty> contractPropertys = (List<ContractProperty>) contractPropertyService.getAllByCorporation(corporation);
//		modelMap.addAttribute("contractPropertys", contractPropertys);
		
		List<CostCenter> costCenters = (List<CostCenter>) costCenterService.getAllByCorporation(corporation);
		modelMap.addAttribute("costCenters", costCenters);
		
		List<ContractCategory> contractCategorys = (List<ContractCategory>) contractCategoryService.getAllByCorporation(corporation);
		modelMap.addAttribute("contractCategorys", contractCategorys);
		
		List<AssetsCategory> assetsCategorys = (List<AssetsCategory>) assetsCategoryService.getAllByCorporation(corporation);
		modelMap.addAttribute("assetsCategorys", assetsCategorys);
		
		List<OurSideCorporation> ourSideCorporations = (List<OurSideCorporation>) ourSideCorporationService.getAllByCorporation(corporation);
		modelMap.addAttribute("ourSideCorporations", ourSideCorporations);
	}
	
	private boolean saveContract(Contract contract, HttpServletRequest request, MultipartFile attachment) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			if (contract.isNewEntity()) {
				// 给合同添加序号
				String seqNo = contractService.getSequeneNumber("project.contract_sequence", contract.getPetrolStation().getProject().getId());
				contract.setSeqNo(seqNo);
				contract.setCode(contract.getCode() + seqNo);
				contract.setContractStatus(ContractStatus.PendingSend);
			}
			String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/contract");
			getEntityService().save(contract, attachment, fileDir);
		}
		return isSucceed;
	}

	@Override
	protected ContractService getEntityService() {
		return contractService;
	}

	@Override
	protected Contract newInstance(WebRequest request) {
		return new Contract();
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC10");
	}
	
}
