/**
 * 
 */
package com.photo.pgm.core.vo;

import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.RepairApproveOpinions;
import com.photo.pgm.core.model.RepairOrder;
import com.photo.pgm.core.model.RepairSettleAccount;

/**
 * @author FengYu
 *
 */
public class RepairApproveOpinionsQueryInfo extends PageInfo<RepairApproveOpinions>{

	private RepairOrder sf_EQ_repairOrder;
	private RepairSettleAccount sf_EQ_repairSettleAccount;
	public RepairOrder getSf_EQ_repairOrder() {
		return sf_EQ_repairOrder;
	}
	public void setSf_EQ_repairOrder(RepairOrder sf_EQ_repairOrder) {
		this.sf_EQ_repairOrder = sf_EQ_repairOrder;
	}
	public RepairSettleAccount getSf_EQ_repairSettleAccount() {
		return sf_EQ_repairSettleAccount;
	}
	public void setSf_EQ_repairSettleAccount(
			RepairSettleAccount sf_EQ_repairSettleAccount) {
		this.sf_EQ_repairSettleAccount = sf_EQ_repairSettleAccount;
	}
	
	
}
