/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.ArrayList;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.UserType;
import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.utils.JsonUtils;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.enums.GcCatagory;
import com.photo.pgm.core.enums.HzWay;
import com.photo.pgm.core.enums.OilLevel;
import com.photo.pgm.core.model.Project;
import com.photo.pgm.core.service.ProjectService;
import com.photo.pgm.core.vo.ProjectQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value="/pgm/project/project/list")
public class ProjectListController extends AbsQueryPagedListController<Project, ProjectQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/project/projects";
	
	@Autowired private ProjectService projectService;
	
	@RequestMapping
	@RequiresPermissions("project:list")
	public String show(ModelMap modelMap){
		modelMap.addAttribute("isNormal", ThreadLocalUtils.getCurrentUser().getUserType().equals(UserType.Normal));
		modelMap.addAttribute("oilLevel", JsonUtils.buildEnmuCoboxData(OilLevel.getOilLevel()));
		modelMap.addAttribute("hzWay", JsonUtils.buildEnmuCoboxData(HzWay.getHzWay()));
		modelMap.addAttribute("gcCatagory", JsonUtils.buildEnmuCoboxData(GcCatagory.getGcCatagory()));
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") ProjectQueryInfo queryInfo){
		Sort sort = new Sort(Direction.ASC, "code");
		queryInfo.setSort(sort);
		Page<Project> list = getEntityService().findLists(queryInfo);
		return toJSONView(list);
	}
	
	@RequestMapping("oilJson")
	public ModelAndView oilJson(@ModelAttribute("pageQueryInfo") ProjectQueryInfo queryInfo){
		if (ThreadLocalUtils.getCurrentUser().getUserType().equals(UserType.Org)) {
			queryInfo.setSf_IN_id(null);
			Page<Project> list = getEntityService().findLists(queryInfo);
			return toJSONView(list);
		} 
		if (queryInfo.getSf_IN_id() == null || queryInfo.getSf_IN_id().isEmpty()) {
			Page<Project> list = new PageImpl<Project>(new ArrayList<Project>());
			return toJSONView(list);
		}
		Page<Project> list = getEntityService().findLists(queryInfo);
		return toJSONView(list);
	}
	
	@Override
	public ProjectQueryInfo newPagedQueryInfo() {
		return new ProjectQueryInfo();
	}

	@Override
	protected ProjectService getEntityService() {
		return projectService;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("KF01");
	}
}
