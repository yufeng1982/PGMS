package com.photo.pgm.core.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.model.common.EmployeeDepartment;
import com.photo.bas.core.service.common.EmployeeDepartmentService;
import com.photo.bas.core.web.controller.entity.AbsListController;

@Controller
@RequestMapping("/pgm/hr/employeeDepartment/list")
public class EmployeeDepartmentController extends AbsListController<EmployeeDepartment> {
    
	@Autowired EmployeeDepartmentService employeeDepartmentService;
	
	@Override
	protected EmployeeDepartmentService getEntityService() {
		return employeeDepartmentService;
	}
	
	@RequestMapping(value = "getEmployee")
	public ModelAndView getEmployee(@RequestParam(value = "departmentId", required = false) Department department) {
		if (department == null)return toJSONView(new ArrayList<EmployeeDepartment>());
		return toJSONView(employeeDepartmentService.getEmployee(department));
	}

}
