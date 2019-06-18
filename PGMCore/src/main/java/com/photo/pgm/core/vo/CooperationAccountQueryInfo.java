/**
 * 
 */
package com.photo.pgm.core.vo;

import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.Cooperation;
import com.photo.pgm.core.model.CooperationAccount;

/**
 * @author FengYu
 *
 */
public class CooperationAccountQueryInfo extends PageInfo<CooperationAccount> {

	private Cooperation sf_EQ_cooperation;

	public Cooperation getSf_EQ_cooperation() {
		return sf_EQ_cooperation;
	}

	public void setSf_EQ_cooperation(Cooperation sf_EQ_cooperation) {
		this.sf_EQ_cooperation = sf_EQ_cooperation;
	}
	
}
