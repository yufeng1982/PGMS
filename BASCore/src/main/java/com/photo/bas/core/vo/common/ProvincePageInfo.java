/**
 * 
 */
package com.photo.bas.core.vo.common;

import com.photo.bas.core.model.common.Country;
import com.photo.bas.core.model.common.Province;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public class ProvincePageInfo extends PageInfo<Province> {
	private Country sf_EQ_country;

	public Country getSf_EQ_country() {
		return sf_EQ_country;
	}

	public void setSf_EQ_country(Country sf_EQ_country) {
		this.sf_EQ_country = sf_EQ_country;
	}
	
	
}
