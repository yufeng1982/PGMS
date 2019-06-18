/**
 * 
 */
package com.photo.bas.core.model.common;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.entity.MaintenanceType;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "hr")
public class Department extends AbsCodeNameEntity {

	private static final long serialVersionUID = -3495206720158377628L;
	
	public Department(){
		super();
	}

	@Override
	public MaintenanceType getMaintenanceType() {
		return MaintenanceType.Department;
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
    	jo.put("department", FormatUtils.stringValue(getId()));
    	jo.put("departmentText", FormatUtils.stringValue(getName()));
    	return jo;
    }
}
