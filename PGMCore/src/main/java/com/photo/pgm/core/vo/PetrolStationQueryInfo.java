/**
 * 
 */
package com.photo.pgm.core.vo;

import java.util.List;

import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.PetrolStation;

/**
 * @author FengYu
 *
 */
public class PetrolStationQueryInfo extends PageInfo<PetrolStation> {
	private List<String> sf_IN_id;
	private String sf_EQ_code;
	private String sf_EQ_shortName;

	public List<String> getSf_IN_id() {
		return sf_IN_id;
	}

	public void setSf_IN_id(List<String> sf_IN_id) {
		this.sf_IN_id = sf_IN_id;
	}

	public String getSf_EQ_code() {
		return sf_EQ_code;
	}

	public void setSf_EQ_code(String sf_EQ_code) {
		this.sf_EQ_code = sf_EQ_code;
	}

	public String getSf_EQ_shortName() {
		return sf_EQ_shortName;
	}

	public void setSf_EQ_shortName(String sf_EQ_shortName) {
		this.sf_EQ_shortName = sf_EQ_shortName;
	}

}
