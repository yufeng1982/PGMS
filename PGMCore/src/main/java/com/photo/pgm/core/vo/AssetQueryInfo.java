/**
 * 
 */
package com.photo.pgm.core.vo;

import java.util.List;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.Asset;
import com.photo.pgm.core.model.PetrolStation;

/**
 * @author FengYu
 *
 */
public class AssetQueryInfo extends PageInfo<Asset> {
	
	private String sf_LIKE_code;
	private PetrolStation sf_EQ_petrolStation;
	private Department sf_EQ_department;
	private List<PetrolStation> sf_IN_petrolStation;
	
	public String getSf_LIKE_code() {
		return sf_LIKE_code;
	}

	public void setSf_LIKE_code(String sf_LIKE_code) {
		this.sf_LIKE_code = sf_LIKE_code;
	}

	public Department getSf_EQ_department() {
		return sf_EQ_department;
	}

	public void setSf_EQ_department(Department sf_EQ_department) {
		this.sf_EQ_department = sf_EQ_department;
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

}
