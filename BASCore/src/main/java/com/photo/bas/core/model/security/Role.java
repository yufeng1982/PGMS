/**
 * 
 */
package com.photo.bas.core.model.security;

import java.text.ParseException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.entity.MaintenanceType;
import com.photo.bas.core.utils.ThreadLocalUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "public")
public class Role extends AbsCodeNameEntity {
	private static final long serialVersionUID = -7558841459726750931L;

	@Column(columnDefinition = "text")
	private String functionNodeIds;
	
	@Column(columnDefinition = "text")
	private String resources; // {fnID1:[{arID1:operation, arID2:operation, ...},{}], fnID2:[],...}

	@Type(type = "true_false")
	private Boolean isAdminRole = Boolean.FALSE;
	
	public Role() {
		this("", "", ThreadLocalUtils.getCurrentCorporation());
	}
	public Role(String code, String name) {
		this(code, name, ThreadLocalUtils.getCurrentCorporation());
	}
	public Role(String code, String name, Corporation corporation) {
		super(code, name, corporation);
		this.isAdminRole = false;
		resources = new JSONObject().toString();
	}
	
	public String getFunctionNodeIds() {
		return functionNodeIds;
	}
	public void setFunctionNodeIds(String functionNodeIds) {
		this.functionNodeIds = functionNodeIds;
	}
	
	public String getResources() {
		if(resources == null) resources = new JSONObject().toString();
		return resources;
	}
	public void setResources(String resources) {
		this.resources = resources;
	}
	public void setResources(JSONObject json) {
		jsonResources = json;
		this.resources = json.toString();
	}
	
	public Boolean isAdminRole() {
		return isAdminRole;
	}
	public void setIsAdminRole(Boolean isAdminRole) {
		this.isAdminRole = isAdminRole;
	}
	
	@Transient
	private JSONObject jsonResources;
	public JSONObject getJSONResources() {
		if(jsonResources == null) {
			try {
				jsonResources = new JSONObject(this.getResources());
			} catch (ParseException e) {
				jsonResources = new JSONObject();
			}
		}
		return jsonResources;
	}
	public String getRoleName() {
		return "ROLE_" + getName();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public MaintenanceType getMaintenanceType() {
		return null;
	}
	@Override
	public String getSavePermission() {
		return null;
	}
	@Override
	public String getDeletePermission() {
		return null;
	}
	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	return jo;
    }
}