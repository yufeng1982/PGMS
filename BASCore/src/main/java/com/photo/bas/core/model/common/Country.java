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
@Table(schema = "public") 
public class Country extends AbsCodeNameEntity {
	private static final long serialVersionUID = 8041541601764019965L;

	private String phoneCode;
	private String stateAlias;
	private String postalCodeAlias;
	
	public Country() {
		super();
	}

	public Country(String code, String name) {
		super(code, name);
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getStateAlias() {
		return stateAlias;
	}

	public void setStateAlias(String stateAlias) {
		this.stateAlias = stateAlias;
	}

	public String getPostalCodeAlias() {
		return postalCodeAlias;
	}

	public void setPostalCodeAlias(String postalCodeAlias) {
		this.postalCodeAlias = postalCodeAlias;
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("phoneCode", FormatUtils.stringValue(phoneCode));
    	jo.put("stateAlias", FormatUtils.stringValue(stateAlias));
    	jo.put("postalCodeAlias", FormatUtils.stringValue(postalCodeAlias));
    	return jo;
    }
	
	@Override
	public MaintenanceType getMaintenanceType() {
		return MaintenanceType.Country;
	}

	@Override
	public String getSavePermission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeletePermission() {
		// TODO Auto-generated method stub
		return null;
	}
}
