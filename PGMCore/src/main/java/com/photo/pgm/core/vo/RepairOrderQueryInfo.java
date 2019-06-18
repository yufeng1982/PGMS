/**
 * 
 */
package com.photo.pgm.core.vo;

import java.util.List;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.enums.RepairStatus;
import com.photo.pgm.core.model.PetrolStation;
import com.photo.pgm.core.model.RepairOrder;

/**
 * @author FengYu
 *
 */
public class RepairOrderQueryInfo extends PageInfo<RepairOrder> {

	private String sf_LIKE_code;
	private PetrolStation sf_EQ_petrolStation;
	private RepairStatus sf_EQ_repairStatus;
	private List<PetrolStation> sf_IN_petrolStation;
	private User sf_EQ_createdBy;
	
	public String getSf_LIKE_code() {
		return sf_LIKE_code;
	}
	public void setSf_LIKE_code(String sf_LIKE_code) {
		this.sf_LIKE_code = sf_LIKE_code;
	}
	
	public RepairStatus getSf_EQ_repairStatus() {
		return sf_EQ_repairStatus;
	}
	public void setSf_EQ_repairStatus(RepairStatus sf_EQ_repairStatus) {
		this.sf_EQ_repairStatus = sf_EQ_repairStatus;
	}
	
	public PetrolStation getSf_EQ_petrolStation() {
		return sf_EQ_petrolStation;
	}
	public void setSf_EQ_petrolStation(PetrolStation sf_EQ_petrolStation) {
		this.sf_EQ_petrolStation = sf_EQ_petrolStation;
	}
	public List<PetrolStation> getSf_IN_petrolStation() {
		return sf_IN_petrolStation;
	}
	public void setSf_IN_petrolStation(List<PetrolStation> sf_IN_petrolStation) {
		this.sf_IN_petrolStation = sf_IN_petrolStation;
	}
	public User getSf_EQ_createdBy() {
		return sf_EQ_createdBy;
	}
	public void setSf_EQ_createdBy(User sf_EQ_createdBy) {
		this.sf_EQ_createdBy = sf_EQ_createdBy;
	}
	
}
