/**
 * 
 */
package com.photo.bas.core.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.dao.common.DepartmentRepository;
import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.service.entity.AbsMaintenanceService;
import com.photo.bas.core.vo.common.DepartmentQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class DepartmentsService extends AbsMaintenanceService<Department, DepartmentQueryInfo> {

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
	
	public Department findOne(String id){
		return departmentRepository.findOne(id);
	}

}
