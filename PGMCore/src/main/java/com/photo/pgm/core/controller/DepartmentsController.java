package com.photo.pgm.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.service.common.DepartmentsService;
import com.photo.bas.core.vo.common.DepartmentQueryInfo;
import com.photo.bas.core.web.controller.entity.AbsMaintenanceController;

@Controller
@RequestMapping(value="/pgm/hr/department")
public class DepartmentsController extends AbsMaintenanceController<Department,DepartmentQueryInfo> {
    
	@Autowired DepartmentsService departmentsService;
	@Autowired UserService userService;
	
	protected DepartmentsService getEntityService() {
		return departmentsService;
	}
	
	@Override
	public String getPropUrl() {
		return null;
	}

	@Override
	public boolean isPaged() {
		return true;
	}
	
	@Override
	@RequiresPermissions("department:list")
	@RequestMapping("list")
	public String list(ModelMap modelMap, HttpServletRequest request, @ModelAttribute("model") Department department,
			@RequestParam(value="parameterNames", required=false) String parameterNames) {
		return super.listCommon(modelMap, request, department, parameterNames);
	}

	@Override
	@RequiresPermissions("department:save")
	@RequestMapping("apply")
	public String apply(@RequestParam(value = "modifiedRecords") JSONArray modifiedRecords, 
			@RequestParam(value = "lineToDelete") String lineToDelete,
			@RequestParam(value = "parameterNames", required=false) String parameterNames, 
			@ModelAttribute("model") Department department, ModelMap modelMap,
			HttpServletRequest request) {
		return super.applyCommon(modifiedRecords, lineToDelete, parameterNames, department, modelMap, request);
	}

	@Override
	public DepartmentQueryInfo newPagedQueryInfo() {
		return new DepartmentQueryInfo();
	}
	
	@Override
	public boolean isInfinite() {
		return false;
	}
}
