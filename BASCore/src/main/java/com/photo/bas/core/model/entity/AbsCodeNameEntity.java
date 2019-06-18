/**
 * 
 */
package com.photo.bas.core.model.entity;

import javax.persistence.MappedSuperclass;

import org.hibernate.envers.Audited;
import org.json.JSONObject;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@MappedSuperclass
public abstract class AbsCodeNameEntity extends AbsCodeEntity implements IMaintenance {
	private static final long serialVersionUID = 5035040484773248500L;
	
	@Audited
	private String name;
	
	private String sequence; // 订单编号
	
	public AbsCodeNameEntity() {
		super();
	}

	public AbsCodeNameEntity(String code, String name) {
		super(code);
		this.name = name;
	}

	public AbsCodeNameEntity(String code, String name, Corporation corporation) {
		super(code, corporation);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDisplayString() {
		return getCode() + " " + getName();
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("sequence", FormatUtils.stringValue(sequence));
    	jo.put("name", FormatUtils.stringValue(name));
    	return jo;
    }

	@Override
	public MaintenanceType getMaintenanceType() {
		return null;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
		this.setCode(sequence);
	}
}
