/**
 * 
 */
package com.photo.bas.core.model.common;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("_Z_")
@Table(name="zone", schema = "public")
@Audited
public class AbsZone extends AbsCodeNameEntity {
	private static final long serialVersionUID = -7157188809667263549L;

	@ManyToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Country country;

	@ManyToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Province province;
    
	private Double estimateCostCwt;
	
	public AbsZone() {this("", "");}
	public AbsZone(String code, String name) {
		this(code, name, null, null);
	}
	public AbsZone(String code, String name, Country country, Province province) {
		super(code, name);
		this.country = country;
		this.province = province;
	}
	
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	
	public JSONObject toJSONObject() {
    	JSONObject jsonObject = super.toJSONObject();
    	jsonObject.put("country",  FormatUtils.idString(getCountry()));
    	jsonObject.put("countryText", FormatUtils.displayString(getCountry()));
    	jsonObject.put("province", FormatUtils.idString(getProvince()));
    	jsonObject.put("provinceText", FormatUtils.displayString(getProvince()));
    	return jsonObject;
    }
	public void setEstimateCostCwt(Double estimateCostCwt) {
		this.estimateCostCwt = estimateCostCwt;
	}
	public Double getEstimateCostCwt() {
		return estimateCostCwt;
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
