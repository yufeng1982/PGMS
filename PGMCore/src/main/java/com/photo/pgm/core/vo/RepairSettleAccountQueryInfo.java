/**
 * 
 */
package com.photo.pgm.core.vo;

import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.RepairSettleAccount;

/**
 * @author FengYu
 *
 */
public class RepairSettleAccountQueryInfo extends PageInfo<RepairSettleAccount> {
	
	private String sf_EQ_repairOrder;
	private String sf_EQ_petrolStation;
	
	public String getSf_EQ_repairOrder() {
		return sf_EQ_repairOrder;
	}
	public void setSf_EQ_repairOrder(String sf_EQ_repairOrder) {
		this.sf_EQ_repairOrder = sf_EQ_repairOrder;
	}
	public String getSf_EQ_petrolStation() {
		return sf_EQ_petrolStation;
	}
	public void setSf_EQ_petrolStation(String sf_EQ_petrolStation) {
		this.sf_EQ_petrolStation = sf_EQ_petrolStation;
	}
	
}
