/**
 * 
 */
package com.photo.pgm.core.model;

import javax.persistence.Table;
import javax.persistence.Entity;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.entity.MaintenanceType;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
public class CostCenter extends AbsCodeNameEntity {

	private static final long serialVersionUID = 2662535368268342121L;

	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}

	@Override
	public MaintenanceType getMaintenanceType() {
		return MaintenanceType.CostCenter;
	}
}
