/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.enums.EmployeeStatus;
import com.photo.bas.core.enums.EmployeeType;
import com.photo.bas.core.enums.InactivityCauseType;
import com.photo.bas.core.enums.TerminationCauseType;
import com.photo.bas.core.model.common.Employee;
import com.photo.bas.core.model.common.Gender;
import com.photo.bas.core.model.common.Language;
import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.service.common.EmployeeService;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.web.controller.entity.AbsCodeEntityController;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/hr/employee/form")
public class EmployeeController extends AbsCodeEntityController<Employee> {

	private final static String PAGE_FORM_PATH = "hr/employee/employee";
	private final static String PATH = "hr/employee/form/";
	private final static String PAGE_FORM_PATH_TEMPLATE = "hr/employee/employeeTemplate";
	
	@Autowired private EmployeeService employeeService;
	@Autowired private UserService userService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("employee:show")
	public String showEmployee(@ModelAttribute("entity") Employee employee, ModelMap modelMap) {
		modelMap.addAttribute("employeeStatus", EmployeeStatus.getEmployeeStatuses());
		modelMap.addAttribute("inactivityCauseType", InactivityCauseType.getInactivityCauses());
		modelMap.addAttribute("terminationCauseType", TerminationCauseType.getTerminationCauses());
		modelMap.addAttribute("languages", Language.getLanguages());
		modelMap.addAttribute("gender", Gender.getGenders());
		return employee.getEmployeeType().equals(EmployeeType.Employee) ? PAGE_FORM_PATH : PAGE_FORM_PATH_TEMPLATE;
	}
	
	@RequestMapping("{id}/apply")
	@RequiresPermissions("employee:apply")
	public ModelAndView save(@ModelAttribute("entity") Employee employee, 
							 	@RequestParam JSONArray positionJsons,
							 	@RequestParam List<String> positionDeleteLines,
							 	ModelMap modelMap) {
		saveEmployee(employee, positionJsons, positionDeleteLines);
		return redirectTo(PATH + employee.getId() + "/show", modelMap);
	}
	
	@RequestMapping("{id}/ok")
	@RequiresPermissions("employee:ok")
	public ModelAndView ok(@ModelAttribute("entity") Employee employee, 
							 	@RequestParam JSONArray positionJsons,
							 	@RequestParam List<String> positionDeleteLines,
							 	ModelMap modelMap) {
		if(saveEmployee(employee, positionJsons, positionDeleteLines)){
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(employee.getId()) ? NEW_ENTITY_ID : employee.getId()) + "/show", modelMap);
	}
	
	@RequestMapping("{id}/resetPassword")
	@ResponseBody
	@RequiresPermissions("employee:resetPassword")
	public String resetPassword(@ModelAttribute("entity") Employee employee, 
							 	@RequestParam String plainPassword,
							 	ModelMap modelMap) {
		User user = employee.getLoginUser();
		if(user!=null){
			user.setPlainPassword(plainPassword);
			userService.saveUserAndEncrypt(user);
			return "true";
		} 
		return "false";
	}
	
	@RequestMapping("{id}/resetUserName")
	@ResponseBody
	@RequiresPermissions("employee:resetUserName")
	public String resetUserName(@ModelAttribute("entity") Employee employee, 
							 	@RequestParam String userName,
							 	ModelMap modelMap) {
		User checkUser = userService.findByUserName(userName);
		if (checkUser != null) return "false";
		User user = employee.getLoginUser();
		user.setLoginName(userName);
		userService.save(user);
		return "true";
	}
	
	private boolean saveEmployee(Employee employee, JSONArray positionJsons, List<String> positionDeleteLines) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			getEntityService().save(employee, positionJsons, positionDeleteLines);
		}
		return isSucceed;
	}
	
	@Override
	protected EmployeeService getEntityService() {
		return employeeService;
	}

	@Override
	protected Employee newInstance(WebRequest request) {
		return new Employee();
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("HR01");
	}
	
}
