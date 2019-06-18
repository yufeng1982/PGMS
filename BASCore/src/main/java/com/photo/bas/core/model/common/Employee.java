/**
 * 
 */
package com.photo.bas.core.model.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.enums.EmployeeType;
import com.photo.bas.core.model.entity.Ownership;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "hr")
public class Employee extends AbsEmployee implements Ownership {

	private static final long serialVersionUID = 4295009132118250612L;
	
	@Enumerated(EnumType.STRING)
	private EmployeeType employeeType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date birthday;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date hireDate;
	
	private String photoPath;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Employee employeeTempate;
	
	@OneToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User loginUser;
	
	private String userName;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Department department;
	
	public Employee(){
		super();
	}
	
	@Override
	public SourceEntityType getOwnerType() {
		return SourceEntityType.Employee;
	}
	
	@Override
	public String getOwnerStatus() {
		return null;
	}

	@Override
	public String getDisplayCode() {
		return getCode();
	}

	@Override
	public String getDisplayName() {
		return getName();
	}

	@Override
	public String getSourceEntityId() {
		return null;
	}

	@Override
	public void setSourceEntityId(String sourceEntityId) {
		
	}

	@Override
	public String getOwnerId() {
		return getId();
	}

	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}	

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public EmployeeType getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(EmployeeType employeeType) {
		this.employeeType = employeeType;
	}

	public Employee getEmployeeTempate() {
		return employeeTempate;
	}

	public void setEmployeeTempate(Employee employeeTempate) {
		this.employeeTempate = employeeTempate;
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("birthday", FormatUtils.dateValue(birthday));
    	jo.put("hireDate", FormatUtils.dateValue(hireDate));
    	jo.put("employeeType", FormatUtils.nameString(employeeType));
    	jo.put("loginUserId", FormatUtils.idString(loginUser));
    	jo.put("loginUserText", FormatUtils.stringValue(userName));
    	jo.put("department", FormatUtils.displayString(department));
    	return jo;
    }
}
