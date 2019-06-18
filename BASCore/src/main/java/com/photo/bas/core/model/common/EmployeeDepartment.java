/**
 * 
 */
package com.photo.bas.core.model.common;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONObject;

import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "hr")
public class EmployeeDepartment extends HrBaseEntity {

	private static final long serialVersionUID = -3588678066993322192L;

	@ManyToOne
	private Employee employee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Department department;
	
	public EmployeeDepartment() {
		super();
	}
	
	@Override
	public String getDisplayString() {
		return null;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("employeeId", getEmployee() == null ? "" : FormatUtils.stringValue(getEmployee().getId()));
		jo.put("employeeCode", getEmployee() == null ? "" : FormatUtils.stringValue(getEmployee().getCode()));
		jo.put("employeeName", getEmployee() == null ? "" : FormatUtils.stringValue(getEmployee().getName()));
		jo.put("departmentId", getDepartment() == null ? "" : FormatUtils.stringValue(getDepartment().getId()));
    	return jo;
    }

}
