/**
 * 
 */
package com.photo.pgm.core.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.entity.MaintenanceType;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class CooperationType extends AbsCodeNameEntity {

	private static final long serialVersionUID = -6367012724916984021L;

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
		return MaintenanceType.CooperationType;
	}
	
}
