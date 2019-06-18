/**
 * 
 */
package com.photo.bas.core.service.common;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.dao.common.EmployeeRespository;
import com.photo.bas.core.enums.EmployeeType;
import com.photo.bas.core.model.common.Employee;
import com.photo.bas.core.model.common.EmployeePosition;
import com.photo.bas.core.model.common.Position;
import com.photo.bas.core.model.common.UserType;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.model.security.UserDepartment;
import com.photo.bas.core.security.service.RoleService;
import com.photo.bas.core.security.service.UserDepartmentService;
import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.DateTimeUtils;
import com.photo.bas.core.utils.EmailUtils;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.vo.common.EmployeeQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class EmployeeService extends AbsCodeNameEntityService<Employee, PageInfo<Employee>> {

	@Autowired private EmployeeRespository employeeRespository;
	@Autowired private PositionService positionService;
	@Autowired private EmployeePositionService employeePositionService;
	@Autowired private UserService userService;
	@Autowired private RoleService roleService;
	@Autowired private EmailUtils emailUtils;
	@Autowired private UserDepartmentService userDepartmentService;
	
	@Transactional(readOnly = false)
	public Employee save(Employee employee, JSONArray positionJsons, List<String> positionDeleteLines) {
		boolean createUserFlag = employee.isNewEntity();
		for(int i = 0; i < positionJsons.length(); i++){
			Position position = null;
			JSONObject jsonObj = positionJsons.getJSONObject(i);
			Boolean primary = jsonObj.getBoolean("primary");
			if(primary){
				String positionId = jsonObj.getString("positionId");
				position = positionService.get(positionId);
				employee.setPrimaryPosition(position);
				break;
			}
		}
		
		save(employee);
		if(createUserFlag && EmployeeType.Employee.equals(employee.getEmployeeType())) {
			User user = createUser(employee);
			employee.setLoginUser(user);
		}
		saveEmployeePosition(employee, positionJsons, positionDeleteLines);
		return employee;
	}
	
	
	private void saveEmployeePosition(Employee employee, JSONArray epJsonArray, List<String> positionDeleteLines){
		for(String id : positionDeleteLines){
			if(epJsonArray == null || epJsonArray.length() == 0){
				employee.setPrimaryPosition(null);
			}
			employeePositionService.delete(id);
		}
		for(int i = 0; i < epJsonArray.length(); i++){
			EmployeePosition ep = null;
			JSONObject jsonObj = epJsonArray.getJSONObject(i);
			String id = jsonObj.getString("id");
			if(Strings.isEmpty(id)){
				ep = new EmployeePosition();
			} else {
				ep = employeePositionService.get(id);
			}
			builProperties(ep, employee, jsonObj);
			employeePositionService.save(ep);
		}
	}
	
	private EmployeePosition builProperties(EmployeePosition ep, Employee employee, JSONObject jsonObj){
		ep.setEmployee(employee);
		ep.setCorporation(employee.getCorporation());
		ep.setPosition(positionService.findOne(jsonObj.getString("positionId")));
		ep.setEntryDate(jsonObj.isNull("entryDate") ? null : DateTimeUtils.stringToDate(jsonObj.getString("entryDate")));
		ep.setLeaveDate(jsonObj.isNull("leaveDate") ? null : DateTimeUtils.stringToDate(jsonObj.getString("leaveDate")));
		ep.setDescription(jsonObj.getString("description"));
		ep.setPrincipal(jsonObj.getBoolean("primary"));
		return ep;
	}
	
	private User createUser(Employee employee) {
		User user = setUserInfo(employee);
		user.setUserType(UserType.Normal);
		userService.saveUserAndEncrypt(user);
		saveDefaultUserDepartment(user);
//		try{
//			emailUtils.sendMail(user, "mailTemplate.ftl", "Register");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return user;
	}


	private void saveDefaultUserDepartment(User user) {
		UserDepartment ud = new UserDepartment();
		ud.setBelongTo(true);
		ud.setPrincipal(true);
		ud.setUsers(user);
		ud.setDepartment(user.getDepartment());
		userDepartmentService.save(ud);
	}
	
	private User setUserInfo(Employee employee) {
		User user = new User();
		String userName = Strings.isEmpty(employee.getUserName()) ? employee.getCode() : employee.getUserName();
		user.setLoginName(userName);
		// generate random password
		user.setPlainPassword("12345678");
		user.setCorporation(employee.getCorporation());
		user.setLanguage(employee.getLanguage());
		user.setEmail(employee.getEmail());
		user.setDescription(employee.getDescription());
		user.setEnabled(Boolean.TRUE);
		user.setEmployeeNo(employee.getCode());
		user.setDepartment(employee.getDepartment());
		user.setRealName(employee.getName());
		user.setPhone(employee.getPhoneNumber());
		if(user.getRoles() == null || user.getRoles().isEmpty()) {
			user.addRole(roleService.getUniqueByName(RoleService.COMMONROLE, user.getCorporation()));
		}
		return user;
	}
	
	public Page<Employee> findEmployees(final EmployeeQueryInfo queryInfo) {
		User user = ThreadLocalUtils.getCurrentUser();
		if (user.getUserType().equals(UserType.Normal)) queryInfo.setSf_EQ_loginUser(user);
		return getRepository().findAll(byPage(queryInfo), queryInfo);
	}
	
	public Employee findByLoginUserAndCorporation(User loginUser, Corporation corporation) {
		return getRepository().findByLoginUserAndCorporationAndActiveTrue(loginUser, corporation);
	}
	
	@Override
	protected EmployeeRespository getRepository() {
		return employeeRespository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}
	
	public Employee findByCodeAndCorporationAndType(String code, Corporation corporation, EmployeeType employeeType) {
		return getRepository().findByCodeAndCorporationAndEmployeeTypeAndActiveTrue(code, corporation, employeeType);
	}
	
	public Employee findByEmailAndCorporationAndType(String email, Corporation corporation, EmployeeType employeeType) {
		return getRepository().findByEmailAndCorporationAndEmployeeTypeAndActiveTrue(email, corporation, employeeType);
	}
}
