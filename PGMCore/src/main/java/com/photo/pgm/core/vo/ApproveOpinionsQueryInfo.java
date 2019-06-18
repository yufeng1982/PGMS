/**
 * 
 */
package com.photo.pgm.core.vo;

import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.ApproveOpinions;
import com.photo.pgm.core.model.Contract;

/**
 * @author FengYu
 *
 */
public class ApproveOpinionsQueryInfo extends PageInfo<ApproveOpinions>{

	private Contract sf_EQ_contract;

	public Contract getSf_EQ_contract() {
		return sf_EQ_contract;
	}

	public void setSf_EQ_contract(Contract sf_EQ_contract) {
		this.sf_EQ_contract = sf_EQ_contract;
	}
	
}
