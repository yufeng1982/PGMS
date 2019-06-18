/**
 * 
 */
package com.photo.pgm.core.vo;

import java.util.List;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.PetrolStation;
import com.photo.pgm.core.model.Project;
import com.photo.pgm.core.model.SettleAccounts;

/**
 * @author FengYu
 *
 */
public class SettleAccountsQueryInfo extends PageInfo<SettleAccounts> {
	
	private PetrolStation sf_EQ_petrolStation;
	private String sf_LIKE_contractCode;
	private List<Department> sf_IN_department;
	
	public PetrolStation getSf_EQ_petrolStation() {
		return sf_EQ_petrolStation;
	}
	public void setSf_EQ_petrolStation(PetrolStation sf_EQ_petrolStation) {
		this.sf_EQ_petrolStation = sf_EQ_petrolStation;
	}
	public String getSf_LIKE_contractCode() {
		return sf_LIKE_contractCode;
	}
	public void setSf_LIKE_contractCode(String sf_LIKE_contractCode) {
		this.sf_LIKE_contractCode = sf_LIKE_contractCode;
	}
	public List<Department> getSf_IN_department() {
		return sf_IN_department;
	}
	public void setSf_IN_department(List<Department> sf_IN_department) {
		this.sf_IN_department = sf_IN_department;
	} 
	
}
