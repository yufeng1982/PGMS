/**
 * 
 */
package com.photo.bas.core.vo.crm;

import com.photo.bas.core.model.crm.Customer;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public class CustomerQueryInfo extends PageInfo<Customer> {

	private String sf_LIKE_sequence; 
	private String sf_LIKE_name;
	private String sf_EQ_phone; 
	private Boolean sf_EQ_vip;
	
	public String getSf_LIKE_sequence() {
		return sf_LIKE_sequence;
	}
	public void setSf_LIKE_sequence(String sf_LIKE_sequence) {
		this.sf_LIKE_sequence = sf_LIKE_sequence;
	}
	public String getSf_LIKE_name() {
		return sf_LIKE_name;
	}
	public void setSf_LIKE_name(String sf_LIKE_name) {
		this.sf_LIKE_name = sf_LIKE_name;
	}
	public String getSf_EQ_phone() {
		return sf_EQ_phone;
	}
	public void setSf_EQ_phone(String sf_EQ_phone) {
		this.sf_EQ_phone = sf_EQ_phone;
	}
	public Boolean isSf_EQ_vip() {
		return sf_EQ_vip;
	}
	public void setSf_EQ_vip(Boolean sf_EQ_vip) {
		this.sf_EQ_vip = sf_EQ_vip;
	} 
	
}
