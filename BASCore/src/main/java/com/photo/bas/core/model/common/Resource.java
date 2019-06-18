/**
 * 
 */
package com.photo.bas.core.model.common;

import java.io.File;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.entity.Ownership;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.utils.FileUtils;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "public")
public class Resource extends AbsCodeNameEntity implements Ownership {
	private static final long serialVersionUID = 943446605058411118L;

	public static final String uploadDir = "/resources";
	
	private String sourceEntityId;
	
	private String relativeURL;
	
	private long size;
	
	@Enumerated(EnumType.STRING) private ResourceType contentType;
	
	@Enumerated(EnumType.STRING) private ResourceType type;	
	private String internalName;
	
	private String searchKey;
	
	@Transient
	private String relativeURLRoot;
	
	@Type(type = "true_false")
	private Boolean primaryResource = Boolean.FALSE;
	
	@Type(type = "true_false")
	private Boolean secondaryResource = Boolean.FALSE;
	
	@Type(type = "true_false")
	private Boolean alternate = Boolean.FALSE;
	
	private String comment;

	@Enumerated(EnumType.STRING) private SourceEntityType sourceType;
	
	private String sourceId;
	
	
	Resource() {
		this(null);
	}
	
	public Resource(String displayName) {
		this(displayName, ThreadLocalUtils.getCurrentCorporation());
	}
	
	public Resource(String displayName, String sourceId, SourceEntityType sourceType) {
		this(displayName, ThreadLocalUtils.getCurrentCorporation());
		setSourceId(sourceId);
		setSourceType(sourceType);
		setRelativeURL(sourceId);
	}

	public Resource(String displayName, Corporation corporation) {
		super("", "", corporation);
		setDisplayName(displayName);
		setInternalName(UUID.randomUUID().toString() + "." + FileUtils.getFileExtension(getDisplayName()));
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getRelativeURL() {
		if(Strings.isEmpty(getRelativeURLRoot())) return relativeURL;
		return getRelativeURLRoot() + relativeURL;
	}
	
	public String getRealRelativeURL(){
		return relativeURL;
	}

	public void setRelativeURL(String sourceId) {
		this.relativeURL = sourceId + File.separator + getInternalName();
	}

	public String getInternalName() {
		return this.internalName;
	}	
	
	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}	
	
	public String getDisplayName() {
		return getName();
	}

	private void setDisplayName(String fileName) {
		setName(fileName);
	}
	
	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	
	public String toString(){
		StringBuffer string = new StringBuffer("[");
			string.append("\n   displayName : " + getDisplayName());
			string.append("\n   internalName: " + getInternalName());
			string.append("\n   relativeURL : " + getRelativeURL());
			string.append("\n   contentType : " + getContentType());
			string.append("\n]");
		return string.toString();
	}

	public void setRelativeURLRoot(String relativeURLRoot) {
		this.relativeURLRoot = relativeURLRoot;
		
	}
	
	public String getRelativeURLRoot() {
		return this.relativeURLRoot;
	}

	public boolean isAlternate() {
		return alternate.booleanValue();
	}

	public void setAlternate(boolean alternate) {
		if(alternate) {
			setPrimaryResource(!alternate);
			setSecondaryResource(!alternate);
		}
		this.alternate = Boolean.valueOf(alternate);
	}

	public boolean isPrimaryResource() {
		return primaryResource.booleanValue();
	}

	public void setPrimaryResource(boolean primaryResource) {
		if(primaryResource) {
			setAlternate(!primaryResource);
			setSecondaryResource(!primaryResource);
		}
		this.primaryResource = Boolean.valueOf(primaryResource);
	}

	public boolean isSecondaryResource() {
		return secondaryResource.booleanValue();
	}

	public void setSecondaryResource(boolean secondaryResource) {
		if(secondaryResource) {
			setAlternate(!secondaryResource);
			setPrimaryResource(!secondaryResource);
		}
		this.secondaryResource = Boolean.valueOf(secondaryResource);
	}
	
	public boolean isImage() {
		return getType().equals(ResourceType.IMAGE);
	}
	
	public boolean isTextFile() {
		return getType().equals(ResourceType.TEXT_FILE);
	}
	
	public boolean isBinaryFile() {
		return getType().equals(ResourceType.BINARY_FILE);
	}
	
	public boolean isProductBrochure() {
		return getType().equals(ResourceType.PRODUCT_BROCHURE);
	}
	
	public boolean isAssemblyList() {
		return getType().equals(ResourceType.ASSEMBLY_LIST);
	}
	
	public boolean isQualityAssurance() {
		return getType().equals(ResourceType.QUALITY_ASSURANCE);
	}
	public boolean isCSV() {
		return getType().equals(ResourceType.CSV_FILE);
	}
	public boolean isEDI() {
		return getType().equals(ResourceType.EDI_FILE);
	}
	public Resource clone(){
		Resource clone = new Resource(this.getDisplayName());
		clone.setContentType(this.getContentType());
		clone.setDescription(this.getDescription());
//		clone.setOwnerId(this.getOwnerId());
		clone.setRelativeURL(this.getRelativeURL());
		clone.setRelativeURLRoot(this.getRelativeURLRoot());
		clone.setSearchKey(this.getSearchKey());
		clone.setSize(this.getSize());
		clone.setType(this.getType());
		clone.setAlternate(this.isAlternate());
		clone.setPrimaryResource(this.isPrimaryResource());
		clone.setSecondaryResource(this.isSecondaryResource());
		return clone;
	}
	
	@Override
	public JSONObject toJSONObject() {
    	JSONObject jsonObject = super.toJSONObject();
    	jsonObject.put("type", FormatUtils.displayString(getType()));
    	jsonObject.put("typeCode", FormatUtils.nameString(getType()));
    	jsonObject.put("contentType", FormatUtils.displayString(getContentType()));
    	jsonObject.put("contentTypeCode", FormatUtils.nameString(getContentType()));
    	jsonObject.put("size", FormatUtils.longValue(getSize()));
    	jsonObject.put("url",  Resource.uploadDir + "/" + getInternalName());
    	jsonObject.put("sourceEntityId", FormatUtils.stringValue(sourceId));
    	jsonObject.put("primaryResource", FormatUtils.booleanValue(isPrimaryResource()));
    	jsonObject.put("comment", FormatUtils.stringValue(getComment()));
    	return jsonObject;
    }

	public void setContentType(ResourceType contentType) {
		this.contentType = contentType;
	}

	public ResourceType getContentType() {
		return contentType;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}
	
	public String getUploadPath(){
		String path = ResourceUtils.getAppSetting("system.uploadFolder");
		path += File.separator;
		path += getOwnerId();
		path += File.separator;
		path += getInternalName();
		return path;
	}
	
	public String getFileName(){
		return getName() + "." + FileUtils.getFileExtension(getInternalName());
	}

	@Override
	public String getDisplayCode() {
		return getCode();
	}

	@Override
	public SourceEntityType getOwnerType() {
		return SourceEntityType.ImageAndAttachment;
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
	public String getOwnerId() {
		return getId();
	}

	@Override
	public String getOwnerStatus() {
		return this.isActive() ? "Active" : "Inactive";
	}

	public void setSourceEntityId(String sourceEntityId) {
		this.sourceEntityId = sourceEntityId;
	}

	public String getSourceEntityId() {
		return sourceEntityId;
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
