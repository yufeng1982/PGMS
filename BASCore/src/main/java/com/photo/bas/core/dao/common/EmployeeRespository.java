/**
 * 
 */
package com.photo.bas.core.dao.common;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.enums.EmployeeType;
import com.photo.bas.core.model.common.Employee;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.User;

/**
 * @author FengYu
 *
 */
public interface EmployeeRespository extends AbsCodeNameEntityRepository<Employee> {
	public Employee findByCodeAndCorporationAndEmployeeTypeAndActiveTrue(String code, Corporation corporation, EmployeeType employeeType);
	public Employee findByEmailAndCorporationAndEmployeeTypeAndActiveTrue(String email, Corporation corporation, EmployeeType employeeType);
	public Employee findByLoginUserAndCorporationAndActiveTrue(User loginUser, Corporation corporation);
}
