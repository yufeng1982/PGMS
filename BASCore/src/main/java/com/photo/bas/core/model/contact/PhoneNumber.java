/**
 * 
 */
package com.photo.bas.core.model.contact;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsContactEntity;

/**
 * @author FengYu
 *
 */
@Entity
@Table(name = "phone_number", schema = "public")
public class PhoneNumber extends AbsContactEntity {

	private static final long serialVersionUID = -8051698013577073781L;
	
	public PhoneNumber(){
		super();
	}
	
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	return jo;
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
