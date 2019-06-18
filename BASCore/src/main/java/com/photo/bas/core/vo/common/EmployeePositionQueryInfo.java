/**
 * 
 */
package com.photo.bas.core.vo.common;

import com.photo.bas.core.model.common.Employee;
import com.photo.bas.core.model.common.EmployeePosition;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public class EmployeePositionQueryInfo extends PageInfo<EmployeePosition> {

	private Employee sf_EQ_employee;

	public Employee getSf_EQ_employee() {
		return sf_EQ_employee;
	}

	public void setSf_EQ_employee(Employee sf_EQ_employee) {
		this.sf_EQ_employee = sf_EQ_employee;
	}
	
}
