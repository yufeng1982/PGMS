/**
 * 
 */
package com.photo.pgm.core.vo;

import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.AssetsCategory;

/**
 * @author FengYu
 *
 */
public class AssetsCategoryQueryInfo extends PageInfo<AssetsCategory> {
	
	private String sf_LIKE_code;
	private String sf_LIKE_name;
	private Integer sf_EQ_level;
	
	public String getSf_LIKE_code() {
		return sf_LIKE_code;
	}
	public void setSf_LIKE_code(String sf_LIKE_code) {
		this.sf_LIKE_code = sf_LIKE_code;
	}
	public String getSf_LIKE_name() {
		return sf_LIKE_name;
	}
	public void setSf_LIKE_name(String sf_LIKE_name) {
		this.sf_LIKE_name = sf_LIKE_name;
	}
	public Integer getSf_EQ_level() {
		return sf_EQ_level;
	}
	public void setSf_EQ_level(Integer sf_EQ_level) {
		this.sf_EQ_level = sf_EQ_level;
	}
	
}
