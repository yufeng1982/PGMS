/**
 * 
 */
package com.photo.pgm.core.vo;

import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.YearBudgets;

/**
 * @author FengYu
 *
 */
public class YearBudgetsQueryInfo extends PageInfo<YearBudgets> {

	private Integer sf_EQ_years;

	public Integer getSf_EQ_years() {
		return sf_EQ_years;
	}

	public void setSf_EQ_years(Integer sf_EQ_years) {
		this.sf_EQ_years = sf_EQ_years;
	}
	
}
