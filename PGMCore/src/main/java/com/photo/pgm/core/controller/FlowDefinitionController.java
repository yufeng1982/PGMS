/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.web.controller.entity.AbsCodeEntityController;
import com.photo.pgm.core.model.FlowDefinition;
import com.photo.pgm.core.service.ActivitiFlowService;
import com.photo.pgm.core.service.FlowDefinitionService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/flowDefinition/form")
public class FlowDefinitionController extends AbsCodeEntityController<FlowDefinition> {

	private final static String PAGE_FORM_PATH = "project/flowDefinition/flowDefinition";
	private final static String PATH = "project/flowDefinition/form/";
	
	@Autowired private FlowDefinitionService flowDefinitionService;
	@Autowired private ActivitiFlowService activitiFlowService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("flowDefinition:show")
	public String showFlowDefinition(@ModelAttribute("entity") FlowDefinition flowDefinition, ModelMap modelMap) {
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping("{id}/apply")
	@RequiresPermissions("flowDefinition:apply")
	public String save(HttpServletRequest request,
							@ModelAttribute("entity") FlowDefinition flowDefinition, 
							@RequestParam JSONArray flowDefinitionJsons,
							@RequestParam List<String> flowDefinitionDeleteLines,
							ModelMap modelMap, RedirectAttributes redirectAttr) {
		if (isCanSaveOrdeploy(flowDefinition)) {
			saveFlowDefinition(request, flowDefinition, flowDefinitionJsons, flowDefinitionDeleteLines);
			redirectAttr.addFlashAttribute("infos", "");
		} else {
			redirectAttr.addFlashAttribute("infos", ResourceUtils.getText("Com.VChangeFlow.Cannot"));
		}
		return redirect("pgm", PATH + flowDefinition.getId() + "/show", modelMap, redirectAttr);
	}

	@RequestMapping("{id}/ok")
	@RequiresPermissions("flowDefinition:ok")
	public String ok(HttpServletRequest request,
						@ModelAttribute("entity") FlowDefinition flowDefinition, 
						@RequestParam JSONArray flowDefinitionJsons,
						@RequestParam List<String> flowDefinitionDeleteLines,
						ModelMap modelMap, RedirectAttributes redirectAttr) {
		if (isCanSaveOrdeploy(flowDefinition)) {
			if(saveFlowDefinition(request, flowDefinition, flowDefinitionJsons, flowDefinitionDeleteLines)){
				return closePage(modelMap, redirectAttr);
			}
		} else {
			redirectAttr.addFlashAttribute("infos", ResourceUtils.getText("Com.VChangeFlow.Cannot"));
		}
		return redirect("pgm", PATH + (Strings.isEmpty(flowDefinition.getId()) ? NEW_ENTITY_ID : flowDefinition.getId()) + "/show", modelMap, redirectAttr);
	}
	
	@RequestMapping("{id}/deploy")
	@RequiresPermissions("flowDefinition:deploy")
	public String deploy(HttpServletRequest request,
								@ModelAttribute("entity") FlowDefinition flowDefinition, 
								@RequestParam JSONArray flowDefinitionJsons,
								@RequestParam List<String> flowDefinitionDeleteLines,
								ModelMap modelMap, RedirectAttributes redirectAttr) {
		if (isCanSaveOrdeploy(flowDefinition)) {
			saveFlowDefinition(request, flowDefinition, flowDefinitionJsons, flowDefinitionDeleteLines);
			String fileDir = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/com/photo/pgm/bpmn");
			boolean flag = activitiFlowService.createDynamicBPMN(fileDir, flowDefinition.getCode(), flowDefinition.getName(), flowDefinitionJsons);
			if (flag) {
				modelMap.addAttribute("deployStatus", "successful");
			} 
			redirectAttr.addFlashAttribute("infos", "");
		} else {
			redirectAttr.addFlashAttribute("infos", ResourceUtils.getText("Com.VChangeFlow.Cannot"));
		}
		return redirect("pgm", PATH + flowDefinition.getId() + "/show", modelMap, redirectAttr);
	}
	
	private boolean isCanSaveOrdeploy(FlowDefinition flowDefinition) {
		if (flowDefinition.isNewEntity()) {
			return true;
		} else {
			return activitiFlowService.isCanReDeploy(flowDefinition.getCode());
		}
	}
	
	private boolean saveFlowDefinition(HttpServletRequest request, FlowDefinition flowDefinition,JSONArray flowDefinitionJsons, List<String> flowDefinitionDeleteLines) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			String fileDir = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/com/photo/pgm/bpmn");
			getEntityService().save(flowDefinition,flowDefinitionJsons, flowDefinitionDeleteLines, fileDir);
		}
		return isSucceed;
	}

	@Override
	protected FlowDefinitionService getEntityService() {
		return flowDefinitionService;
	}

	@Override
	protected FlowDefinition newInstance(WebRequest request) {
		return new FlowDefinition();
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC40");
	}
	
}
