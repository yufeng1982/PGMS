package com.photo.bas.core.service.common;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.dao.common.EmployeeDepartmentRepository;
import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.model.common.Employee;
import com.photo.bas.core.model.common.EmployeeDepartment;
import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.utils.DateTimeUtils;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;

@Component
@Transactional(readOnly = true)
public class EmployeeDepartmentService extends AbsEntityService<EmployeeDepartment, PageInfo<EmployeeDepartment>> {

	@Autowired private EmployeeDepartmentRepository employeeDepartmentRepository;
	@Autowired private EmployeeService employeeService;
	@Autowired private DepartmentService departmentService;

	@Override
	protected EmployeeDepartmentRepository getRepository() {
		return employeeDepartmentRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

	@Transactional(readOnly = false)
	public void deleteEmployee(Collection<String> deleteEmployee) {
		for (String id : deleteEmployee) {
			EmployeeDepartment employeeDepartment = get(id);
			if (employeeDepartment != null) {
				Employee employee = employeeDepartment.getEmployee();
				Department currentDepartmnet = employeeDepartment.getDepartment();
				Department primaryDepartment = employee.getPrimaryDepartment();
				if (currentDepartmnet.equals(primaryDepartment)) {
					employee.setPrimaryDepartment(null);
					employeeService.save(employee);
				}
				employeeDepartment.setLeaveDate(ThreadLocalUtils.getDefaultAppDate());
				employeeDepartment.setPrincipal(false);
				save(employeeDepartment);
				delete(id);
			}
		}
	}

	public List<EmployeeDepartment> getEmployee(Department department) {
		List<EmployeeDepartment> list = getRepository()
				.findByDepartmentAndActiveTrue(department);
		return list;
	}

	public EmployeeDepartment bulidEmployeeDepartmnet(Department entity, JSONObject jsobObject) {
		EmployeeDepartment ed = null;
		Employee employee = null;
		Department primaryDepartmnet = null;
		if (jsobObject.has("employeeId")) {
			String employeeId = jsobObject.getString("employeeId");
			employee = employeeService.get(employeeId);
			primaryDepartmnet = employee.getPrimaryDepartment();
		}

		if (jsobObject.has("id")) {
			String id = jsobObject.getString("id");
			if (Strings.isEmpty(id)) {
				ed = new EmployeeDepartment();
				if (primaryDepartmnet == null) {
					ed.setPrincipal(true);
					employee.setPrimaryDepartment(entity);
				}
				ed.setEntryDate(ThreadLocalUtils.getDefaultAppDate());
			} else {
				ed = get(id);
			}
			ed.setDepartment(entity);
			ed.setEmployee(employee);
		}

		if (jsobObject.has("entryDate")) {
			Date entryDay = DateTimeUtils.stringToDate(jsobObject.getString("entryDate"));
			if (entryDay != null) {
				ed.setEntryDate(entryDay);
			}
		}
		employeeService.save(employee);
		return ed;
	}
}
