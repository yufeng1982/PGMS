/**
 * 
 */
package com.photo.bas.core.vo.common;

import org.json.JSONArray;

import com.photo.bas.core.enums.EmployeeType;
import com.photo.bas.core.model.common.Employee;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public class EmployeeQueryInfo extends PageInfo<Employee> {

	private String sf_LIKE_name;
	private String sf_LIKE_code;
	private JSONArray employeeJsons;
	private EmployeeType sf_EQ_employeeType;
	private User sf_EQ_loginUser;
	private Boolean sf_EQ_enabled = Boolean.TRUE;
	
	public EmployeeQueryInfo(){
		super();
		setPageSize(300);
	}

	public String getSf_LIKE_code() {
		return sf_LIKE_code;
	}

	public void setSf_LIKE_code(String sf_LIKE_code) {
		this.sf_LIKE_code = sf_LIKE_code;
	}

	public String getSf_LIKE_name() {
		return sf_LIKE_name;
	}

	public void setSf_LIKE_name(String sf_LIKE_name) {
		this.sf_LIKE_name = sf_LIKE_name;
	}

	public JSONArray getEmployeeJsons() {
		return employeeJsons;
	}

	public void setEmployeeJsons(JSONArray employeeJsons) {
		this.employeeJsons = employeeJsons;
	}

	public EmployeeType getSf_EQ_employeeType() {
		return sf_EQ_employeeType;
	}

	public void setSf_EQ_employeeType(EmployeeType sf_EQ_employeeType) {
		this.sf_EQ_employeeType = sf_EQ_employeeType;
	}

	public User getSf_EQ_loginUser() {
		return sf_EQ_loginUser;
	}

	public void setSf_EQ_loginUser(User sf_EQ_loginUser) {
		this.sf_EQ_loginUser = sf_EQ_loginUser;
	}

	public Boolean isSf_EQ_enabled() {
		return sf_EQ_enabled;
	}

	public void setSf_EQ_enabled(Boolean sf_EQ_enabled) {
		this.sf_EQ_enabled = sf_EQ_enabled;
	}
	
}
