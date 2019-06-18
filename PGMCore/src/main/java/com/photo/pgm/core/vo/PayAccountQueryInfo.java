/**
 * 
 */
package com.photo.pgm.core.vo;

import java.util.List;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.AssetsCategory;
import com.photo.pgm.core.model.Cooperation;
import com.photo.pgm.core.model.PayAccount;
import com.photo.pgm.core.model.PetrolStation;

/**
 * @author FengYu
 *
 */
public class PayAccountQueryInfo extends PageInfo<PayAccount> {

	private PetrolStation sf_EQ_petrolStation;
	private String sf_LIKE_contractCode;
	private AssetsCategory sf_EQ_assetsCategory;
	private Cooperation sf_EQ_cooperation;
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
	public AssetsCategory getSf_EQ_assetsCategory() {
		return sf_EQ_assetsCategory;
	}
	public void setSf_EQ_assetsCategory(AssetsCategory sf_EQ_assetsCategory) {
		this.sf_EQ_assetsCategory = sf_EQ_assetsCategory;
	}
	public Cooperation getSf_EQ_cooperation() {
		return sf_EQ_cooperation;
	}
	public void setSf_EQ_cooperation(Cooperation sf_EQ_cooperation) {
		this.sf_EQ_cooperation = sf_EQ_cooperation;
	}
	public List<Department> getSf_IN_department() {
		return sf_IN_department;
	}
	public void setSf_IN_department(List<Department> sf_IN_department) {
		this.sf_IN_department = sf_IN_department;
	}
	
}
