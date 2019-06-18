/**
 * 
 */
package com.photo.pgm.core.vo;

import java.util.Date;

import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.ProjectApproveBudget;

/**
 * @author FengYu
 *
 */
public class ProjectApproveBudgetQueryInfo extends PageInfo<ProjectApproveBudget> {

	private String sf_EQ_oilLevel;
	private String sf_EQ_approveLevel;
	private Date sf_GTE_approveDate;
	private Date sf_LTE_approveDate;
	
	public String getSf_EQ_oilLevel() {
		return sf_EQ_oilLevel;
	}
	public void setSf_EQ_oilLevel(String sf_EQ_oilLevel) {
		this.sf_EQ_oilLevel = sf_EQ_oilLevel;
	}
	public String getSf_EQ_approveLevel() {
		return sf_EQ_approveLevel;
	}
	public void setSf_EQ_approveLevel(String sf_EQ_approveLevel) {
		this.sf_EQ_approveLevel = sf_EQ_approveLevel;
	}
	public Date getSf_GTE_approveDate() {
		return sf_GTE_approveDate;
	}
	public void setSf_GTE_approveDate(Date sf_GTE_approveDate) {
		this.sf_GTE_approveDate = sf_GTE_approveDate;
	}
	public Date getSf_LTE_approveDate() {
		return sf_LTE_approveDate;
	}
	public void setSf_LTE_approveDate(Date sf_LTE_approveDate) {
		this.sf_LTE_approveDate = sf_LTE_approveDate;
	}
	
}
