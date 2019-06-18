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
public class AbsCodeEntity extends AbsEntity {
	private static final long serialVersionUID = 7487113646045545686L;

	@Audited
	private String code;

	public AbsCodeEntity(String code) {
		super();
		this.code = code;
	}

	public AbsCodeEntity(String code, Corporation corporation) {
		super(corporation);
		this.code = code;
	}

	public AbsCodeEntity() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getDisplayString() {
		return getCode();
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("code", FormatUtils.stringValue(code));
    	return jo;
    }
}
