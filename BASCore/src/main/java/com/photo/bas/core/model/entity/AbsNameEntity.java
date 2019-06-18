/**
 * 
 */
package com.photo.bas.core.model.entity;

import javax.persistence.MappedSuperclass;

import org.json.JSONObject;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@MappedSuperclass
public class AbsNameEntity extends AbsEntity {
	private static final long serialVersionUID = -8279514109555324926L;

	private String name;
	
	public AbsNameEntity() {
		super();
	}

	public AbsNameEntity(String name) {
		super();
		this.name = name;
	}

	public AbsNameEntity(String name, Corporation corporation) {
		super(corporation);
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
		return getName();
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("name", FormatUtils.stringValue(name));
    	return jo;
    }

}
