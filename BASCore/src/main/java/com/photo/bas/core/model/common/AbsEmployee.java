/**
 * 
 */
package com.photo.bas.core.model.common;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.jasypt.hibernate4.type.EncryptedStringType;
import org.json.JSONObject;

import com.photo.bas.core.enums.EmployeeClass;
import com.photo.bas.core.enums.EmployeeStatus;
import com.photo.bas.core.enums.InactivityCauseType;
import com.photo.bas.core.enums.TerminationCauseType;
import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.utils.FormatUtils;

@TypeDef(name="corporationEncryptedString", typeClass=EncryptedStringType.class, parameters= {@Parameter(name="encryptorRegisteredName", value="corporationStringEncryptor")})

/**
 * @author FengYu
 *
 */
@MappedSuperclass
public abstract class AbsEmployee extends AbsCodeNameEntity {

	private static final long serialVersionUID = 2652252576221082411L;
	
	@Enumerated(EnumType.STRING)
	@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
	private EmployeeStatus employeeStatus = EmployeeStatus.Active;
	
	@Enumerated(EnumType.STRING)
	private EmployeeClass EmployeeClass;
	
	@Enumerated(EnumType.STRING)
	private InactivityCauseType inactivityCauseType;
	
	@Enumerated(EnumType.STRING)
	private TerminationCauseType terminationCauseType;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Enumerated(EnumType.STRING)
	private Language language = Language.Chinese;
	
	@Type(type = "true_false")
	private Boolean enabled = Boolean.TRUE;

	@Type(type="corporationEncryptedString")
	private String email;
	
	private String phoneNumber;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Position primaryPosition;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Department primaryDepartment;
	
	public AbsEmployee(){
		super();
	}

	public EmployeeStatus getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(EmployeeStatus employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public EmployeeClass getEmployeeClass() {
		return EmployeeClass;
	}

	public void setEmployeeClass(EmployeeClass employeeClass) {
		EmployeeClass = employeeClass;
	}

	public InactivityCauseType getInactivityCauseType() {
		return inactivityCauseType;
	}

	public void setInactivityCauseType(InactivityCauseType inactivityCauseType) {
		this.inactivityCauseType = inactivityCauseType;
	}

	public TerminationCauseType getTerminationCauseType() {
		return terminationCauseType;
	}

	public void setTerminationCauseType(TerminationCauseType terminationCauseType) {
		this.terminationCauseType = terminationCauseType;
	}

	public boolean isEnabled() {
		return enabled == null ? false : enabled.booleanValue();
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Position getPrimaryPosition() {
		return primaryPosition;
	}

	public void setPrimaryPosition(Position primaryPosition) {
		this.primaryPosition = primaryPosition;
	}

	public Department getPrimaryDepartment() {
		return primaryDepartment;
	}

	public void setPrimaryDepartment(Department primaryDepartment) {
		this.primaryDepartment = primaryDepartment;
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("departmnetId", FormatUtils.idString(getPrimaryDepartment()));
    	jo.put("position", FormatUtils.displayString(primaryPosition));
    	jo.put("department", FormatUtils.displayString(primaryDepartment));
    	jo.put("email", FormatUtils.stringValue(email));
    	jo.put("phoneNumber", FormatUtils.stringValue(phoneNumber));
    	jo.put("gender", FormatUtils.displayString(gender));
    	jo.put("language", FormatUtils.displayString(language));
    	jo.put("employeeStatus", FormatUtils.displayString(employeeStatus));
    	jo.put("EmployeeClass", FormatUtils.displayString(EmployeeClass));
    	jo.put("inactivityCauseType", FormatUtils.displayString(inactivityCauseType));
    	jo.put("terminationCauseType", FormatUtils.displayString(terminationCauseType));
    	return jo;
    }
	
}
