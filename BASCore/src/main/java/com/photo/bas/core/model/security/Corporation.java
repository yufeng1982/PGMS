/**
 * 
 */
package com.photo.bas.core.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.entity.Ownership;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(schema = "public", name = "organization")
public class Corporation extends AbsCodeNameEntity implements Ownership, Comparable<Corporation> {
	private static final long serialVersionUID = -8283036857642742506L;

	private String shortName;
	private String keyWords;
	private String functionNodes;
	
	@Type(type="commonEncryptedString")
	private String saltSource;
	
	@Column(columnDefinition = "text")
	private String address;
	
	public Corporation() {
		this("", "");
	}

	public Corporation(String code, String name) {
		super(code, name);
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getFunctionNodes() {
		return functionNodes;
	}

	public void setFunctionNodes(String functionNodes) {
		this.functionNodes = functionNodes;
	}

	public String getSaltSource() {
		return saltSource;
	}

	public void setSaltSource(String saltSource) {
		this.saltSource = saltSource;
	}

	@Override
	public int compareTo(Corporation o) {
		if(o == null) return 1;
		
		Corporation cor = (Corporation) o;
		return this.getName().compareToIgnoreCase(cor.getName());
	}

	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("shortName", FormatUtils.stringValue(shortName));
    	jo.put("keyWords", FormatUtils.stringValue(keyWords));
    	jo.put("saltSource", FormatUtils.stringValue(saltSource));
    	jo.put("address", FormatUtils.stringValue(address));
    	return jo;
	}

	@Override
	public String getOwnerId() {
		return getId();
	}

	@Override
	public SourceEntityType getOwnerType() {
		return SourceEntityType.Corporation;
	}

	@Override
	public String getOwnerStatus() {
		return null;
	}

	@Override
	public String getDisplayCode() {
		return getShortName();
	}

	@Override
	public String getDisplayName() {
		return getName();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getSourceEntityId() {
		return null;
	}

	@Override
	public void setSourceEntityId(String sourceEntityId) {
		
	}

	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}
}
