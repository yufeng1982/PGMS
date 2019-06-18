/**
 * 
 */
package com.photo.pgm.core.model;

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
@Table(schema = "project")
public class OurSideCorporation extends AbsCodeNameEntity {

	private static final long serialVersionUID = -8991510564786354253L;

	private String pct;
	private String province;
	private String city;
	private String town;
	

	public String getPct() {
		return pct;
	}

	public void setPct(String pct) {
		this.pct = pct;
	}


	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
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
	public MaintenanceType getMaintenanceType() {
		return MaintenanceType.OurSideCorporation;
	}
	
	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("pct", FormatUtils.stringValue(pct));
    	jo.put("province", FormatUtils.stringValue(province));
    	jo.put("city", FormatUtils.stringValue(city));
    	jo.put("town", FormatUtils.stringValue(town));
    	return jo;
    }

}
