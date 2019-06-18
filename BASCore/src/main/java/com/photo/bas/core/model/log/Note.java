package com.photo.bas.core.model.log;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONObject;

import com.photo.bas.core.model.common.Resource;
import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.entity.Ownership;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.utils.FormatUtils;


/**
 * 
 * @author FengYu
 */
@Entity
@Table(schema = "public")
public class Note extends AbsCodeNameEntity {
	private static final long serialVersionUID = 8215968854726974176L;

	@Enumerated(EnumType.STRING) private SourceEntityType sourceType;
	
	private String sourceId;
	
	@Enumerated(EnumType.STRING) private NoteType type;
    
	@ManyToOne(optional=true)
	private Resource resource;
	
	public Note(){
		super();
	}
	public Note(String sourceId, SourceEntityType sourceType, NoteType type) {
		super();
		setSourceId(sourceId);
		setSourceType(sourceType);
		setType(type);
	}
	
	public Note(Ownership ownership, NoteType type, String description) {
		this(ownership.getOwnerId(), ownership.getOwnerType(), type);
		setDescription(description);
	}
	
	public Note(Ownership ownership, NoteType type) {
		this(ownership.getOwnerId(), ownership.getOwnerType(), type);
	}
	
	public Note(String sourceId, SourceEntityType sourceType, NoteType type, String description) {
		this(sourceId,sourceType,type);
		setDescription(description);
	}

	public NoteType getType() {
		return type;
	}
	
	public void setType(NoteType type) {
		this.type = type;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public SourceEntityType getSourceType() {
		return sourceType;
	}
	public void setSourceType(SourceEntityType sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject =  super.toJSONObject();
		jsonObject.put("type", FormatUtils.displayString(getType()));
		jsonObject.put("typeCode", FormatUtils.nameString(getType()));
		jsonObject.put("createdBy",  FormatUtils.displayString(getCreatedBy()));
		return jsonObject;
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
