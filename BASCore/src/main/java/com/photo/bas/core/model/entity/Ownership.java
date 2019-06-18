/**
 * 
 */
package com.photo.bas.core.model.entity;

import com.photo.bas.core.model.security.Corporation;


/**
 * @author FengYu
 *
 */
public interface Ownership {

	public String getOwnerId();
	
	public SourceEntityType getOwnerType();
	
	public String getOwnerStatus();
	
	public String getDisplayCode();
	
	public String getDisplayName();
	
	public Corporation getCorporation();
	
	public String getDisplayDescription();
	
	public String getSourceEntityId();
	
	public void setSourceEntityId(String sourceEntityId);
}
