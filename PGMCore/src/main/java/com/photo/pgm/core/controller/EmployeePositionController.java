/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.EmployeePosition;
import com.photo.bas.core.service.common.EmployeePositionService;
import com.photo.bas.core.service.common.EmployeeService;
import com.photo.bas.core.vo.common.EmployeePositionQueryInfo;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/hr/employeePosition")
public class EmployeePositionController extends AbsQueryPagedListController<EmployeePosition, EmployeePositionQueryInfo> {

	@Autowired private EmployeePositionService employeePositionService;
	@Autowired private EmployeeService employeeService;
	
	@RequestMapping(value = "json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") EmployeePositionQueryInfo queryInfo, @RequestParam String employeeId){
		if (employeeId.equals("NEW")) return toJSONView(new ArrayList<EmployeePosition>());
		queryInfo.setSf_EQ_employee(employeeService.get(employeeId));
		Page<EmployeePosition> list = employeePositionService.findByEmployee(queryInfo);
		return toJSONView(list);
	}
	
	@Override
	protected EmployeePositionService getEntityService() {
		return employeePositionService;
	}

	@Override
	public EmployeePositionQueryInfo newPagedQueryInfo() {
		return new EmployeePositionQueryInfo();
	}

}
