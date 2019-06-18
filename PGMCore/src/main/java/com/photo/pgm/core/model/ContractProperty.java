/**
 * 
 */
package com.photo.pgm.core.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.entity.MaintenanceType;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
public class ContractProperty extends AbsCodeNameEntity {

	private static final long serialVersionUID = 8131657203790642209L;

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
		return MaintenanceType.ContractProperty;
	}
}
