/**
 * 
 */
package com.photo.pgm.core.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsEntity;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
public class UserProject extends AbsEntity {

	private static final long serialVersionUID = 8921236757425671315L;

	@ManyToOne
	private User users;
	
	@ManyToOne
	private PetrolStation petrolStation; // 工程管理-油站信息
	
	@Type(type = "true_false")
	private Boolean principal = Boolean.FALSE;
	
	@Type(type = "true_false")
	private Boolean belongTo = Boolean.FALSE;;
	

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


	public PetrolStation getPetrolStation() {
		return petrolStation;
	}

	public void setPetrolStation(PetrolStation petrolStation) {
		this.petrolStation = petrolStation;
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
		jo.put("project", FormatUtils.idString(petrolStation));
    	jo.put("projectText", FormatUtils.displayString(petrolStation));
    	jo.put("principal", FormatUtils.booleanValue(principal));
    	jo.put("belongTo", FormatUtils.booleanValue(belongTo));
//    	jo.put("description", FormatUtils.stringValue(project.getDescription()));
		return jo;
	}
	
}
