/**
 * 
 */
package com.photo.pgm.core.vo;

import java.util.List;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.Contract;
import com.photo.pgm.core.model.PayItem;
import com.photo.pgm.core.model.PetrolStation;

/**
 * @author FengYu
 *
 */
public class PayItemQueryInfo extends PageInfo<PayItem> {
	
	private PetrolStation sf_EQ_petrolStation;
	private Contract sf_EQ_contract;
	private List<Department> sf_IN_department;

	public PetrolStation getSf_EQ_petrolStation() {
		return sf_EQ_petrolStation;
	}

	public void setSf_EQ_petrolStation(PetrolStation sf_EQ_petrolStation) {
		this.sf_EQ_petrolStation = sf_EQ_petrolStation;
	}

	public Contract getSf_EQ_contract() {
		return sf_EQ_contract;
	}

	public void setSf_EQ_contract(Contract sf_EQ_contract) {
		this.sf_EQ_contract = sf_EQ_contract;
	}

	public List<Department> getSf_IN_department() {
		return sf_IN_department;
	}

	public void setSf_IN_department(List<Department> sf_IN_department) {
		this.sf_IN_department = sf_IN_department;
	}
	
}
