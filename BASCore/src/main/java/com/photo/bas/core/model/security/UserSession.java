/**
 * 
 */
package com.photo.bas.core.model.security;

import java.io.Serializable;

import org.json.JSONObject;

import com.photo.bas.core.model.common.Language;
import com.photo.bas.core.model.entity.IEntity;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.bas.core.utils.Strings;

/**
 * @author FengYu
 *
 */
public class UserSession implements IEntity, Serializable {

	private static final long serialVersionUID = -7013098812040723488L;
	
	private String id;
	private String name;
	private Language language;
	private Corporation corporation;
	
	public void updateUserSession(User user) {
		this.id = user.getId();
		this.name = user.getLoginName();
//		this.language = user.getLanguage();
		this.corporation = user.getCorporation();
	}
	
	public UserSession() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Corporation getCorporation() {
		return corporation;
	}

	public void setCorporation(Corporation corporation) {
		this.corporation = corporation;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
    	jsonObject.put("id", FormatUtils.stringValue(id));
    	jsonObject.put("name", FormatUtils.stringValue(name));
    	jsonObject.put("language", FormatUtils.displayString(language));
    	return jsonObject;
	}

	@Override
	public boolean isNewEntity() {
		return Strings.isEmpty(id);
	}

	@Override
	public String getDisplayString() {
		return null;
	}

	@Override
	public JSONObject toJSONObjectAll() {
		// TODO Auto-generated method stub
		return toJSONObject();
	}
}
