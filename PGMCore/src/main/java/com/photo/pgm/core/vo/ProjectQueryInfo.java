/**
 * 
 */
package com.photo.pgm.core.vo;

import java.util.Date;
import java.util.List;

import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.enums.GcCatagory;
import com.photo.pgm.core.enums.HzWay;
import com.photo.pgm.core.enums.OilLevel;
import com.photo.pgm.core.model.Project;

/**
 * @author FengYu
 *
 */
public class ProjectQueryInfo extends PageInfo<Project> {
	
	private String sf_LIKE_code;
	private Boolean sf_EQ_enabled = Boolean.TRUE;
	private String sf_LIKE_pct;
	private HzWay sf_EQ_hzWay;
	private GcCatagory sf_EQ_gcCatagory;
	private OilLevel sf_EQ_oilLevel;
	private List<String> sf_IN_id;
	private Double sf_GTE_salesForecast;
	private Double sf_LTE_salesForecast;
	private Date sf_GTE_addDate;
	private Date sf_LTE_addDate;
	
	public String getSf_LIKE_code() {
		return sf_LIKE_code;
	}
	public void setSf_LIKE_code(String sf_LIKE_code) {
		this.sf_LIKE_code = sf_LIKE_code;
	}
	public Boolean isSf_EQ_enabled() {
		return sf_EQ_enabled;
	}
	public void setSf_EQ_enabled(Boolean sf_EQ_enabled) {
		this.sf_EQ_enabled = sf_EQ_enabled;
	}
	public String getSf_LIKE_pct() {
		return sf_LIKE_pct;
	}
	public void setSf_LIKE_pct(String sf_LIKE_pct) {
		this.sf_LIKE_pct = sf_LIKE_pct;
	}
	public HzWay getSf_EQ_hzWay() {
		return sf_EQ_hzWay;
	}
	public void setSf_EQ_hzWay(HzWay sf_EQ_hzWay) {
		this.sf_EQ_hzWay = sf_EQ_hzWay;
	}
	public GcCatagory getSf_EQ_gcCatagory() {
		return sf_EQ_gcCatagory;
	}
	public void setSf_EQ_gcCatagory(GcCatagory sf_EQ_gcCatagory) {
		this.sf_EQ_gcCatagory = sf_EQ_gcCatagory;
	}
	public OilLevel getSf_EQ_oilLevel() {
		return sf_EQ_oilLevel;
	}
	public void setSf_EQ_oilLevel(OilLevel sf_EQ_oilLevel) {
		this.sf_EQ_oilLevel = sf_EQ_oilLevel;
	}
	public List<String> getSf_IN_id() {
		return sf_IN_id;
	}
	public void setSf_IN_id(List<String> sf_IN_id) {
		this.sf_IN_id = sf_IN_id;
	}
	public Double getSf_GTE_salesForecast() {
		return sf_GTE_salesForecast;
	}
	public void setSf_GTE_salesForecast(Double sf_GTE_salesForecast) {
		this.sf_GTE_salesForecast = sf_GTE_salesForecast;
	}
	public Double getSf_LTE_salesForecast() {
		return sf_LTE_salesForecast;
	}
	public void setSf_LTE_salesForecast(Double sf_LTE_salesForecast) {
		this.sf_LTE_salesForecast = sf_LTE_salesForecast;
	}
	public Date getSf_GTE_addDate() {
		return sf_GTE_addDate;
	}
	public void setSf_GTE_addDate(Date sf_GTE_addDate) {
		this.sf_GTE_addDate = sf_GTE_addDate;
	}
	public Date getSf_LTE_addDate() {
		return sf_LTE_addDate;
	}
	public void setSf_LTE_addDate(Date sf_LTE_addDate) {
		this.sf_LTE_addDate = sf_LTE_addDate;
	}
	
}
