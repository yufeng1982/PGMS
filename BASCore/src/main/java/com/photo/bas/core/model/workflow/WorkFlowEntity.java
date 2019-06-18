package com.photo.bas.core.model.workflow;

import com.photo.bas.core.model.entity.SourceEntityType;

/**
 * @author FengYu
 *
 */
public interface WorkFlowEntity {

	public String getOwnerId();
	
	public SourceEntityType getOwnerType();
	
	public boolean isNewEntity();
	
	public boolean isArchive();
	
	public String getBPMNKey();
	
	public String getSourceEntityId();

	public String getWorkFlowId();
		
}
