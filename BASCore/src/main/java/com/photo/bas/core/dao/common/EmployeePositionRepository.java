/**
 * 
 */
package com.photo.bas.core.dao.common;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.photo.bas.core.dao.entity.AbsEntityRepository;
import com.photo.bas.core.model.common.EmployeePosition;

/**
 * @author FengYu
 *
 */
public interface EmployeePositionRepository extends AbsEntityRepository<EmployeePosition> {

	@Query("select ep from EmployeePosition ep where ep.employee.id = ?1 and ep.active = 'T'")
	public List<EmployeePosition> findByEmployee(String employeeId);
}
