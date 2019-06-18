/**
 * 
 */
package com.photo.pgm.core.vo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.enums.ContractStatus;
import com.photo.pgm.core.enums.ContractType;
import com.photo.pgm.core.model.AssetsCategory;
import com.photo.pgm.core.model.Contract;
import com.photo.pgm.core.model.ContractCategory;
import com.photo.pgm.core.model.Cooperation;
import com.photo.pgm.core.model.OurSideCorporation;
import com.photo.pgm.core.model.PetrolStation;

/**
 * @author FengYu
 *
 */
public class ContractQueryInfo extends PageInfo<Contract> {

	private ContractType sf_EQ_contractType;
	private String sf_LIKE_code;
	private String sf_LIKE_name;
	private Date sf_EQ_creationDate;
	private PetrolStation sf_EQ_petrolStation;
	private ContractCategory sf_EQ_contractCategory;
	private OurSideCorporation sf_EQ_ourSideCorporation;
	private Cooperation sf_EQ_cooperation;
	private AssetsCategory sf_EQ_assetsCategory;
	private List<ContractStatus> sf_EQ_contractStatus;
	private List<Department> sf_IN_department;
	
	public ContractType getSf_EQ_contractType() {
		return sf_EQ_contractType;
	}

	public void setSf_EQ_contractType(ContractType sf_EQ_contractType) {
		this.sf_EQ_contractType = sf_EQ_contractType;
	}

	public String getSf_LIKE_code() {
		return sf_LIKE_code;
	}

	public void setSf_LIKE_code(String sf_LIKE_code) {
		this.sf_LIKE_code = sf_LIKE_code;
	}


	public Date getSf_EQ_creationDate() {
		if (sf_EQ_creationDate == null) return sf_EQ_creationDate;
		Calendar cal = Calendar.getInstance();
		cal.setTime(sf_EQ_creationDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public void setSf_EQ_creationDate(Date sf_EQ_creationDate) {
		this.sf_EQ_creationDate = sf_EQ_creationDate;
	}

	public PetrolStation getSf_EQ_petrolStation() {
		return sf_EQ_petrolStation;
	}

	public void setSf_EQ_petrolStation(PetrolStation sf_EQ_petrolStation) {
		this.sf_EQ_petrolStation = sf_EQ_petrolStation;
	}

	public ContractCategory getSf_EQ_contractCategory() {
		return sf_EQ_contractCategory;
	}

	public void setSf_EQ_contractCategory(ContractCategory sf_EQ_contractCategory) {
		this.sf_EQ_contractCategory = sf_EQ_contractCategory;
	}

	public OurSideCorporation getSf_EQ_ourSideCorporation() {
		return sf_EQ_ourSideCorporation;
	}

	public void setSf_EQ_ourSideCorporation(
			OurSideCorporation sf_EQ_ourSideCorporation) {
		this.sf_EQ_ourSideCorporation = sf_EQ_ourSideCorporation;
	}

	public Cooperation getSf_EQ_cooperation() {
		return sf_EQ_cooperation;
	}

	public void setSf_EQ_cooperation(Cooperation sf_EQ_cooperation) {
		this.sf_EQ_cooperation = sf_EQ_cooperation;
	}

	public AssetsCategory getSf_EQ_assetsCategory() {
		return sf_EQ_assetsCategory;
	}

	public void setSf_EQ_assetsCategory(AssetsCategory sf_EQ_assetsCategory) {
		this.sf_EQ_assetsCategory = sf_EQ_assetsCategory;
	}

	public List<ContractStatus> getSf_EQ_contractStatus() {
		return sf_EQ_contractStatus;
	}

	public void setSf_EQ_contractStatus(List<ContractStatus> sf_EQ_contractStatus) {
		this.sf_EQ_contractStatus = sf_EQ_contractStatus;
	}

	public List<Department> getSf_IN_department() {
		return sf_IN_department;
	}

	public void setSf_IN_department(List<Department> sf_IN_department) {
		this.sf_IN_department = sf_IN_department;
	}

	public String getSf_LIKE_name() {
		return sf_LIKE_name;
	}

	public void setSf_LIKE_name(String sf_LIKE_name) {
		this.sf_LIKE_name = sf_LIKE_name;
	}

}
