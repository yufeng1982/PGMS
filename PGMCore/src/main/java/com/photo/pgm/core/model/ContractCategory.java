/**
 * 
 */
package com.photo.pgm.core.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.entity.MaintenanceType;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.pgm.core.enums.ContractCategoryType;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class ContractCategory extends AbsCodeNameEntity {

	private static final long serialVersionUID = -5749136259285402229L;

	@Enumerated(EnumType.STRING)
	private ContractCategoryType contractCategoryType;
	
	public ContractCategoryType getContractCategoryType() {
		return contractCategoryType;
	}

	public void setContractCategoryType(ContractCategoryType contractCategoryType) {
		this.contractCategoryType = contractCategoryType;
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
	public String getDisplayString() {
		return getCode() + "-" + getName();
	}
	
	@Override
	public MaintenanceType getMaintenanceType() {
		return MaintenanceType.ContractCategory;
	}
	
	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("contractCategoryType", FormatUtils.displayString(contractCategoryType));
    	return jo;
    }
}
