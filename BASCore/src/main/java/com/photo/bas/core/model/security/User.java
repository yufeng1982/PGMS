/**
 * 
 */
package com.photo.bas.core.model.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.NotBlank;
import org.jasypt.hibernate4.type.EncryptedStringType;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.model.common.Language;
import com.photo.bas.core.model.common.Question;
import com.photo.bas.core.model.common.UserType;
import com.photo.bas.core.model.entity.AbsEntity;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */

@TypeDef(name="commonEncryptedString", typeClass=EncryptedStringType.class, parameters= {@Parameter(name="encryptorRegisteredName", value="commonStringEncryptor")})

@Entity
@Table(name = "users", schema = "public")
public class User extends AbsEntity {
	private static final long serialVersionUID = 1471402486466125719L;
	
	public static final String SUPER_ADMIN_NAME = "admin";
	
	private String realName;
	
	private String phone;
	
	private String loginName;

	@Type(type="commonEncryptedString")
	private String email;
	
	@Type(type="commonEncryptedString")
	private String tempEmail;
	
	@Enumerated(EnumType.STRING) 
	private Language language = Language.Chinese;
	
	@Enumerated(EnumType.STRING) 
	private Question question;
	
	@Transient private String plainPassword;
	private String password;
	private String entryptValidationCode;
	private String salt;
	private Date registerDate;

	private String answer;
	
	@Type(type = "true_false")
	private Boolean enabled = Boolean.FALSE;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "user_role")
	private Set<Role> roles = new LinkedHashSet<Role>();
	
//	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
//	@JoinTable(name = "user_department")
//	private Set<Department> departments = new LinkedHashSet<Department>(); // 用户所属部门
	
	private String employeeNo;
	
	@ManyToOne
	private Department department; // 主要部门

	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	protected User(Corporation corporation) {
		super(corporation);
	}
	
	public User() {
		super();
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@NotBlank
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUsername() {
		return loginName;
	}

	@JsonIgnore
	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Boolean isEnabled() {
		return enabled == null ? false : enabled.booleanValue();
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

//	public Set<Department> getDepartments() {
//		return departments;
//	}
//
//	public void setDepartments(Set<Department> departments) {
//		this.departments = departments;
//	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Role> getAvailableRoles(Corporation corporation) {
		Set<Role> availableRoles = new HashSet<>();
		if(corporation != null){
			for(Role role : roles){
				if(role.getCorporation().equals(corporation)){
					availableRoles.add(role);
				}
			}
		}else{
			return getRoles();
		}
		return availableRoles;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
	public void clearRoles() {
		this.roles.clear();
	}

//	public void addDepartment(Department department) {
//		this.departments.add(department);
//	}
//	
//	public void clearDepartments() {
//		this.departments.clear();
//	}
	
	@Transient
	@JsonIgnore
	public List<String> getRoleList() {
		List<String> rList = new ArrayList<>();
		Set<Role> list = getRoles();
		
		if(list == null) {
			return rList;
		}
		
		for (Role role : list) {
			rList.add(role.getCode());
		}
		return rList;
	}
	
	public Set<Corporation> getAvailableCorporations() {
		Set<Corporation> corporations = new TreeSet<Corporation>();
		Set<Role> roles = this.getRoles();
		if(roles != null && !roles.isEmpty()) {
			for (Role role : roles) {
				Corporation corporation = role.getCorporation();
				if(corporation == null){
					continue;
				}
				corporations.add(corporation);
			}
		}
		return corporations;

	}
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public boolean isAvailableCorporation(Corporation corporation) {
		return getAvailableCorporations().contains(corporation);
	}
	public boolean isSingleCorporation() {
		return getAvailableCorporations().size() == 1;
	}
	public Corporation getSingleCorporation() {
		return isSingleCorporation() ? getAvailableCorporations().iterator().next() : null;
	}
	
	@Transient
	public boolean isSuperAdmin() {
		return this.getLoginName() != null && this.getLoginName().equals(SUPER_ADMIN_NAME);
	}
	
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getEmail() {
		return email;
	}

	public String getTempEmail() {
		return tempEmail;
	}

	public void setTempEmail(String tempEmail) {
		this.tempEmail = tempEmail;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("code", FormatUtils.stringValue(loginName));
    	jo.put("name", FormatUtils.stringValue(loginName));
    	jo.put("corporation", FormatUtils.displayString(getCorporation()));
    	jo.put("email", FormatUtils.stringValue(email));
    	jo.put("enabled", FormatUtils.booleanValue(enabled));
    	jo.put("employeeNo", FormatUtils.stringValue(employeeNo));
    	jo.put("department", FormatUtils.displayString(department));
    	jo.put("realName", FormatUtils.stringValue(realName));
    	jo.put("phone", FormatUtils.stringValue(phone));
    	return jo;
	}
	
	@Override
	public String getDisplayString() {
		return getLoginName() + "-" + getRealName();
	}

	public String getEntryptValidationCode() {
		return entryptValidationCode;
	}

	public void setEntryptValidationCode(String entryptValidationCode) {
		this.entryptValidationCode = entryptValidationCode;
	}

	public Boolean getEnabled() {
		return enabled;
	}

}
