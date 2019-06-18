/**
 * 
 */
package com.photo.bas.core.model.common;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
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
public class Province extends AbsCodeNameEntity {
	private static final long serialVersionUID = -7559666431002473408L;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Country country;

	public Province() {
		super();
	}

	public Province(String code, String name) {
		super(code, name);
	}

	public Province(String code, String name, Country country) {
		this(code, name);
		setCountry(country);
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public JSONObject toJSONObject() {
    	JSONObject jsonObject = super.toJSONObject();
    	jsonObject.put("country",  FormatUtils.idString(getCountry()));
    	jsonObject.put("countryText", FormatUtils.displayString(getCountry()));
    	ColumnConfig.putBrowserRecord("country", jsonObject, FormatUtils.idString(getCountry()), FormatUtils.displayString(getCountry()));
    	return jsonObject;
    }
	
	@Override
	public MaintenanceType getMaintenanceType() {
		return MaintenanceType.Province;
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
