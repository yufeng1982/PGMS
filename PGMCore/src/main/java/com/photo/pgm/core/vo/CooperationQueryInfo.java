/**
 * 
 */
package com.photo.pgm.core.vo;

import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.Cooperation;

/**
 * @author FengYu
 *
 */
public class CooperationQueryInfo extends PageInfo<Cooperation> {
	
	private String sf_LIKE_name;

	public String getSf_LIKE_name() {
		return sf_LIKE_name;
	}

	public void setSf_LIKE_name(String sf_LIKE_name) {
		this.sf_LIKE_name = sf_LIKE_name;
	}
}
