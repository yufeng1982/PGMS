/**
 * 
 */
package com.photo.pgm.core.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.FlowDefinition;
import com.photo.pgm.core.service.FlowDefinitionService;
import com.photo.pgm.core.vo.FlowDefinitionQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value="/pgm/project/flowDefinition/list")
public class FlowDefinitionListController extends AbsQueryPagedListController<FlowDefinition, FlowDefinitionQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/flowDefinition/flowDefinitions";
	
	@Autowired private FlowDefinitionService flowDefinitionService;
	
	@RequestMapping
	@RequiresPermissions("flowDefinition:list")
	public String show(ModelMap modelMap){
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") FlowDefinitionQueryInfo queryInfo){
		Page<FlowDefinition> list = getEntityService().findLists(queryInfo);
		return toJSONView(list);
	}
	
	@Override
	public FlowDefinitionQueryInfo newPagedQueryInfo() {
		return new FlowDefinitionQueryInfo();
	}

	@Override
	protected FlowDefinitionService getEntityService() {
		return flowDefinitionService;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC40");
	}
}
