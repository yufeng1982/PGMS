/**
 * 
 */
package com.photo.bas.core.model.common;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.entity.MaintenanceType;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "hr")
public class Position extends AbsCodeNameEntity {

	private static final long serialVersionUID = -4046603085708985005L;
	
	public Position(){
		super();
	}
	
	@Override
	public String getDisplayString() {
		return getName();
	}
	
	@Override
	public MaintenanceType getMaintenanceType() {
		return MaintenanceType.Position;
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
