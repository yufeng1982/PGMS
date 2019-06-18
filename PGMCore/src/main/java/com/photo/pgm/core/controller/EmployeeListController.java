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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.enums.EmployeeType;
import com.photo.bas.core.model.common.Employee;
import com.photo.bas.core.model.common.UserType;
import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.service.common.EmployeeService;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.vo.common.EmployeeQueryInfo;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/hr/employee/list")
public class EmployeeListController extends AbsQueryPagedListController<Employee,EmployeeQueryInfo> {

	private final static String PAGE_LIST_PATH = "hr/employee/employees";
	
	@Autowired private EmployeeService employeeService;
	
	@RequestMapping
	@RequiresPermissions("employee:list")
	public String show(ModelMap modelMap){
		modelMap.addAttribute("isNormal", ThreadLocalUtils.getCurrentUser().getUserType().equals(UserType.Normal));
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") EmployeeQueryInfo queryInfo){
		Page<Employee> list = getEntityService().findEmployees(queryInfo);
		return toJSONView(list);
	}

	@RequestMapping("codeCheck")
	@ResponseBody
	public String codeCheck(@RequestParam String code, @RequestParam EmployeeType employeeType){
		Employee employee = getEntityService().findByCodeAndCorporationAndType(code, ThreadLocalUtils.getCurrentCorporation(), employeeType);
		if (employee != null) {
			return "true";
		} 
		return "false";
	}
	
	@RequestMapping("emailCheck")
	@ResponseBody
	public String emailCheck(@RequestParam String email, @RequestParam EmployeeType employeeType){
		Employee employee = getEntityService().findByEmailAndCorporationAndType(email, ThreadLocalUtils.getCurrentCorporation(), employeeType);
		if (employee != null) {
			return "true";
		} 
		return "false";
	}
	
	@Override
	protected EmployeeService getEntityService() {
		return employeeService;
	}

	@Override
	public EmployeeQueryInfo newPagedQueryInfo() {
		return new EmployeeQueryInfo();
	}
	
	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("HR01");
	}

}
