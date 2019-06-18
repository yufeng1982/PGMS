/**
 * 
 */
package com.photo.bas.core.model.security;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.json.JSONObject;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.model.entity.AbsEntity;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "public")
public class UserDepartment extends AbsEntity {

	private static final long serialVersionUID = 8921236757425671315L;

	@ManyToOne
	private User users;
	
	@ManyToOne
	private Department department;
	
	@Type(type = "true_false")
	private Boolean principal = Boolean.FALSE;
	
	@Type(type = "true_false")
	private Boolean belongTo = Boolean.FALSE;;
	

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}


	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public boolean getPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public boolean isBelongTo() {
		return belongTo;
	}

	public void setBelongTo(boolean belongTo) {
		this.belongTo = belongTo;
	}

	@Override
	public String getDisplayString() {
		return null;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jo = super.toJSONObject();
		jo.put("user", FormatUtils.idString(users));
    	jo.put("userText", FormatUtils.displayString(users));
		jo.put("department", FormatUtils.idString(department));
    	jo.put("departmentText", FormatUtils.displayString(department));
    	jo.put("principal", FormatUtils.booleanValue(principal));
    	jo.put("belongTo", FormatUtils.booleanValue(belongTo));
    	jo.put("description", FormatUtils.stringValue(department.getDescription()));
		return jo;
	}
	
}
