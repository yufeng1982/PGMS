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
import com.photo.pgm.core.model.ProjectApproveBudget;
import com.photo.pgm.core.service.ProjectApproveBudgetService;
import com.photo.pgm.core.vo.ProjectApproveBudgetQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value="/pgm/project/projectApproveBudget/list")
public class ProjectApproveBudgeListController extends AbsQueryPagedListController<ProjectApproveBudget, ProjectApproveBudgetQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/projectApproveBudget/projectApproveBudgets";
	
	@Autowired private ProjectApproveBudgetService projectApproveBudgetService;
	
	@RequestMapping
	@RequiresPermissions("projectApproveBudget:list")
	public String show(ModelMap modelMap){
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") ProjectApproveBudgetQueryInfo queryInfo){
		Page<ProjectApproveBudget> list = getEntityService().findLists(queryInfo);
		return toJSONView(list);
	}
	
	
	@Override
	public ProjectApproveBudgetQueryInfo newPagedQueryInfo() {
		return new ProjectApproveBudgetQueryInfo();
	}

	@Override
	protected ProjectApproveBudgetService getEntityService() {
		return projectApproveBudgetService;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("KF02");
	}
}
