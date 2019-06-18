/**
 * 
 */
package com.photo.bas.core.model.entity;

import com.photo.bas.core.model.security.Corporation;


/**
 * @author FengYu
 *
 */
public interface IMaintenance extends IEntity {
	
	public MaintenanceType getMaintenanceType();
	public void setCorporation(Corporation corporation);
	public Corporation getCorporation();
	public String getSavePermission();
	public String getDeletePermission();
}
