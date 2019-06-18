/**
 * 
 */
package com.photo.bas.core.service.common;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.dao.common.DepartmentRepository;
import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.model.common.EmployeeDepartment;
import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.vo.common.DepartmentQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class DepartmentService extends AbsCodeNameEntityService<Department, DepartmentQueryInfo> {

	@Autowired private DepartmentRepository departmentRepository;
	@Autowired private EmployeeDepartmentService employeeDeparmentService;
	
	@Override
	protected DepartmentRepository getRepository() {
		return departmentRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}
	
	public Page<Department> findAllDepartments(final DepartmentQueryInfo queryInfo) {
		return getRepository().findAll(byPage(queryInfo), queryInfo);
	}
	
	@Transactional(readOnly = false)
	public Department save(Department entity, JSONArray employeeJsons, Collection<String> deleteEmployee){
		save(entity);
		for (int i = 0; i < employeeJsons.length(); i++) {
			JSONObject jsobObject = employeeJsons.getJSONObject(i);
			EmployeeDepartment ed = employeeDeparmentService.bulidEmployeeDepartmnet(entity, jsobObject);
			employeeDeparmentService.save(ed);
		}
		if (deleteEmployee.size() > 0) {
			employeeDeparmentService.deleteEmployee(deleteEmployee);
		}
		return entity;
	}

}
