/**
 * 
 */
package com.photo.bas.core.model.entity;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.json.JSONObject;

import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@MappedSuperclass
public abstract class AbsContactEntity extends AbsCodeNameEntity {

	private static final long serialVersionUID = -6815913035538728056L;

	public AbsContactEntity(){
		super();
	}
	
	@Type(type = "true_false")
	private Boolean primaryKey = Boolean.FALSE;
	
	private Integer sortNo = new Integer(1);
	
	private String sourceOwnerId;
	
	private String sourceOwnerType;

	public Boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("primaryKey", FormatUtils.booleanValue(primaryKey));
    	jo.put("sortNo", FormatUtils.intValue(sortNo));
    	jo.put("sourceOwnerId", FormatUtils.stringValue(sourceOwnerId));
    	jo.put("sourceOwnerType", FormatUtils.stringValue(sourceOwnerType));
    	return jo;
	}

	public String getSourceOwnerId() {
		return sourceOwnerId;
	}

	public void setSourceOwnerId(String sourceOwnerId) {
		this.sourceOwnerId = sourceOwnerId;
	}

	public String getSourceOwnerType() {
		return sourceOwnerType;
	}

	public void setSourceOwnerType(String sourceOwnerType) {
		this.sourceOwnerType = sourceOwnerType;
	}
}
