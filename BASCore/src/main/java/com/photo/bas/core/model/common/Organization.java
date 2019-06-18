package com.photo.bas.core.model.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.json.JSONObject;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.utils.FormatUtils;

@Entity
@DiscriminatorValue("_ORG_")
public class Organization extends Corporation {

	private static final long serialVersionUID = 2632017662796640334L;
    
	@Column(columnDefinition = "text")
	private String note;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date establishDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date inactiveDate;
	
	private String primaryPhone;
	private String primaryEmail;
	private String primaryLanague;
	
	private String printerName;
	private String firstPhone;
	private String secondPhone;
	private String city;

	public Organization() {
		super();
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("note",  FormatUtils.stringValue(note));
    	jo.put("establishDate", FormatUtils.dateValue(establishDate));
    	jo.put("inactiveDate", FormatUtils.dateValue(inactiveDate));
    	jo.put("printerName", FormatUtils.stringValue(printerName));
    	jo.put("primaryEmail", FormatUtils.stringValue(primaryEmail));
    	jo.put("primaryPhone", FormatUtils.stringValue(primaryPhone));
    	jo.put("primaryLanague", FormatUtils.stringValue(primaryLanague));
    	jo.put("firstPhone", FormatUtils.stringValue(primaryPhone));
    	jo.put("secondPhone", FormatUtils.stringValue(primaryLanague));
    	jo.put("city", FormatUtils.stringValue(city));
    	return jo;
	}

	public Date getEstablishDate() {
		return establishDate;
	}

	public void setEstablishDate(Date establishDate) {
		this.establishDate = establishDate;
	}

	public Date getInactiveDate() {
		return inactiveDate;
	}

	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEma1il(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public String getPrimaryLanague() {
		return primaryLanague;
	}

	public void setPrimaryLanague(String primaryLanague) {
		this.primaryLanague = primaryLanague;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public String getFirstPhone() {
		return firstPhone;
	}

	public void setFirstPhone(String firstPhone) {
		this.firstPhone = firstPhone;
	}

	public String getSecondPhone() {
		return secondPhone;
	}

	public void setSecondPhone(String secondPhone) {
		this.secondPhone = secondPhone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
