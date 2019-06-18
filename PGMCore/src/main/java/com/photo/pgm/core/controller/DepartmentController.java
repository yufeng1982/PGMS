package com.photo.pgm.core.controller;

import java.util.Collection;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.service.common.DepartmentService;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.web.controller.entity.AbsFormController;

@Controller
@RequestMapping(value = "/pgm/department/form")
public class DepartmentController extends AbsFormController<Department> {
   
    private String PAGE_FORM_PATH = "hr/department/department";
    private String path = "department/form/";
    
	@Autowired DepartmentService departmentService;

	@Override
	protected DepartmentService getEntityService() {
		return departmentService;
	}

	@ModelAttribute("entity")
	public Department populateDepartment(@PathVariable(value = "id") String id) {
		Department department = null;
		if (isNew(id)) {
			department = new Department();
		} else {
			department = departmentService.get(id);
		}
		return department;
	}

	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("department:show")
	public String show(@ModelAttribute("entity") Department department, ModelMap modelMap) {
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping(value = "{id}/apply", method = { RequestMethod.PUT, RequestMethod.POST })
	@RequiresPermissions("department:apply")
	public ModelAndView apply(@ModelAttribute("entity") Department entity,
			@RequestParam(value = "employeeJsons") JSONArray employeeJsons,
			@RequestParam(value = "deleteEmployee") Collection<String> deleteEmployee,
			ModelMap modelMap, BindingResult result) {
		saveDepartment(entity, employeeJsons, deleteEmployee);
		return redirectTo(path + (Strings.isEmpty(entity.getId()) ? NEW_ENTITY_ID : entity.getId()) + "/show", modelMap);
	}
	
	@RequestMapping(value = "{id}/ok", method = { RequestMethod.PUT, RequestMethod.POST })
	@RequiresPermissions("department:ok")
	public ModelAndView ok(@ModelAttribute("entity") Department entity,
			@RequestParam(value = "employeeJsons") JSONArray employeeJsons,
			@RequestParam(value = "deleteEmployee") Collection<String> deleteEmployee,
			ModelMap modelMap, BindingResult result) {
		if (saveDepartment(entity, employeeJsons, deleteEmployee)) {
			return closePage(modelMap);
		}
		return redirectTo(path + (Strings.isEmpty(entity.getId()) ? NEW_ENTITY_ID : entity.getId()) + "/show", modelMap);
	}
	
	private boolean saveDepartment(Department entity, JSONArray employeeJsons, Collection<String> deleteEmployee) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			departmentService.save(entity, employeeJsons, deleteEmployee);
		}
		return isSucceed;
	}

}
