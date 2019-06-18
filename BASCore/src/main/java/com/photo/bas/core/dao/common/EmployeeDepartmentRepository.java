package com.photo.bas.core.dao.common;

import java.util.List;

import com.photo.bas.core.dao.entity.AbsEntityRepository;
import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.model.common.EmployeeDepartment;

public interface EmployeeDepartmentRepository extends AbsEntityRepository<EmployeeDepartment> {

	public List<EmployeeDepartment> findByDepartmentAndActiveTrue(Department department);
}
