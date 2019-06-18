/**
 * 
 */
package com.photo.bas.core.model.entity;



/**
 * @author Ming Lei
 *
 */
public interface IDelete {
	/**
	 * entity is going to delete or update to inactive
	 * @return boolean
	 */
	public boolean isRealDelete();
	
	/**
	 * update to inactive when delete
	 */
	public void setToInactive();
}
