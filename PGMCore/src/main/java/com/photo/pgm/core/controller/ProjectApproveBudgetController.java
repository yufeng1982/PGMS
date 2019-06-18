/**
 * 
 */
package com.photo.pgm.core.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.web.controller.entity.AbsFormController;
import com.photo.pgm.core.model.ProjectApproveBudget;
import com.photo.pgm.core.service.ProjectApproveBudgetService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/projectApproveBudget/form")
public class ProjectApproveBudgetController extends AbsFormController<ProjectApproveBudget> {

	private final static String PAGE_FORM_PATH = "project/projectApproveBudget/projectApproveBudget";
	private final static String PATH = "project/projectApproveBudget/form/";
	
	@Autowired private ProjectApproveBudgetService projectApproveBudgetService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("projectApproveBudget:show")
	public String showProject(@ModelAttribute("entity") ProjectApproveBudget PprojectApproveBudget, ModelMap modelMap) {
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping("{id}/apply")
	@RequiresPermissions("projectApproveBudget:apply")
	public ModelAndView save(HttpServletRequest request, 
								@ModelAttribute("entity") ProjectApproveBudget projectApproveBudget, 
								@RequestParam(value="attachment1" , required = false) MultipartFile attachment1,
			
							 	ModelMap modelMap) {
		saveProjectApproveBudget(projectApproveBudget, attachment1, request);
		
		return redirectTo(PATH + projectApproveBudget.getId() + "/show", modelMap);
	}

	@RequestMapping("{id}/ok")
	@RequiresPermissions("project:ok")
	public ModelAndView ok(HttpServletRequest request, 
								@ModelAttribute("entity") ProjectApproveBudget projectApproveBudget, 
								@RequestParam(value="attachment1" , required = false) MultipartFile attachment1,
							 	ModelMap modelMap) {

		if(saveProjectApproveBudget(projectApproveBudget, attachment1, request)){
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(projectApproveBudget.getId()) ? NEW_ENTITY_ID : projectApproveBudget.getId()) + "/show", modelMap);
	}
	
	private boolean saveProjectApproveBudget(ProjectApproveBudget projectApproveBudget, MultipartFile attachment1,HttpServletRequest request) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/projectApproveBudget");
			getEntityService().save(projectApproveBudget, attachment1, fileDir);
		}
		return isSucceed;
	}
	
	@RequestMapping("{id}/downLoad")
	@RequiresPermissions("project:downLoad")
	@ResponseBody
	public void downLoad(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("entity") ProjectApproveBudget projectApproveBudget) {
		String folder = projectApproveBudget.getId();
		String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/projectApproveBudget");
		String filePath = projectApproveBudget.getFilePath();
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		File file = new File(fileDir + "/" + folder + "/" +fileName);
		downLoadFile(response, fileName, file);
	}

	@ModelAttribute("entity")
	public ProjectApproveBudget populateEntity(@PathVariable(value="id") String id) {
		ProjectApproveBudget projectApproveBudget = null;
		if(isNew(id)) {
			projectApproveBudget = new ProjectApproveBudget();
		} else {
			projectApproveBudget = getEntityService().get(id);
		}
		return projectApproveBudget;
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
