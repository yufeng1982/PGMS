/**
 * 
 */
package com.photo.pgm.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsEntity;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.Calc;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class PayAccount extends AbsEntity {
	
	private static final long serialVersionUID = 5189781967079101450L;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Contract contract;
	
	private Double onePayAmount = new Double(0);
	private Double oneRequestAmount = new Double(0);
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User onePayUser;
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User oneRequestUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date onePayDate;
	
	private String onePayFile1;
	private String onePayFile2;
	
	@Column(columnDefinition = "text")
	private String onePayContents;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private PayItem onePayItem;
	
	private Double twoPayAmount = new Double(0);
	private Double twoRequestAmount = new Double(0);
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User twoPayUser;
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User twoRequestUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date twoPayDate;
	
	private String twoPayFile1;
	private String twoPayFile2;
	@Column(columnDefinition = "text")
	private String twoPayContents;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private PayItem twoPayItem;
	
	private Double threePayAmount = new Double(0);
	private Double threeRequestAmount = new Double(0);
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User threePayUser;
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User threeRequestUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date threePayDate;
	
	private String threePayFile1;
	private String threePayFile2;
	@Column(columnDefinition = "text")
	private String threePayContents;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private PayItem threePayItem;
	
	private Double fourPayAmount = new Double(0);
	private Double fourRequestAmount = new Double(0);
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User fourPayUser;
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User fourRequestUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date fourPayDate;

	private String fourPayFile1;
	private String fourPayFile2;
	@Column(columnDefinition = "text")
	private String fourPayContents;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private PayItem fourPayItem;
	
	private Double finishAmount = new Double(0); // 已付款金额
	private Double blanceAmount = new Double(0); // 剩余金额
	
	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Double getOnePayAmount() {
		return onePayAmount;
	}

	public void setOnePayAmount(Double onePayAmount) {
		this.onePayAmount = onePayAmount;
	}

	public Date getOnePayDate() {
		return onePayDate;
	}

	public void setOnePayDate(Date onePayDate) {
		this.onePayDate = onePayDate;
	}

	public Double getTwoPayAmount() {
		return twoPayAmount;
	}

	public void setTwoPayAmount(Double twoPayAmount) {
		this.twoPayAmount = twoPayAmount;
	}

	public Date getTwoPayDate() {
		return twoPayDate;
	}

	public void setTwoPayDate(Date twoPayDate) {
		this.twoPayDate = twoPayDate;
	}

	public Double getThreePayAmount() {
		return threePayAmount;
	}

	public void setThreePayAmount(Double threePayAmount) {
		this.threePayAmount = threePayAmount;
	}

	public Date getThreePayDate() {
		return threePayDate;
	}

	public void setThreePayDate(Date threePayDate) {
		this.threePayDate = threePayDate;
	}

	public Double getFourPayAmount() {
		return fourPayAmount;
	}

	public void setFourPayAmount(Double fourPayAmount) {
		this.fourPayAmount = fourPayAmount;
	}

	public Date getFourPayDate() {
		return fourPayDate;
	}

	public void setFourPayDate(Date fourPayDate) {
		this.fourPayDate = fourPayDate;
	}

	public Double getFinishAmount() {
		return finishAmount;
	}

	public void setFinishAmount(Double finishAmount) {
		this.finishAmount = finishAmount;
	}

	public Double getBlanceAmount() {
		return blanceAmount;
	}

	public void setBlanceAmount(Double blanceAmount) {
		this.blanceAmount = blanceAmount;
	}


	public String getOnePayFile1() {
		return onePayFile1;
	}

	public void setOnePayFile1(String onePayFile1) {
		this.onePayFile1 = onePayFile1;
	}

	public String getOnePayFile2() {
		return onePayFile2;
	}

	public void setOnePayFile2(String onePayFile2) {
		this.onePayFile2 = onePayFile2;
	}

	public String getTwoPayFile1() {
		return twoPayFile1;
	}

	public void setTwoPayFile1(String twoPayFile1) {
		this.twoPayFile1 = twoPayFile1;
	}

	public String getTwoPayFile2() {
		return twoPayFile2;
	}

	public void setTwoPayFile2(String twoPayFile2) {
		this.twoPayFile2 = twoPayFile2;
	}

	public String getThreePayFile1() {
		return threePayFile1;
	}

	public void setThreePayFile1(String threePayFile1) {
		this.threePayFile1 = threePayFile1;
	}

	public String getThreePayFile2() {
		return threePayFile2;
	}

	public void setThreePayFile2(String threePayFile2) {
		this.threePayFile2 = threePayFile2;
	}

	public String getFourPayFile1() {
		return fourPayFile1;
	}

	public void setFourPayFile1(String fourPayFile1) {
		this.fourPayFile1 = fourPayFile1;
	}

	public String getFourPayFile2() {
		return fourPayFile2;
	}

	public void setFourPayFile2(String fourPayFile2) {
		this.fourPayFile2 = fourPayFile2;
	}

	public String getOnePayContents() {
		return onePayContents;
	}

	public void setOnePayContents(String onePayContents) {
		this.onePayContents = onePayContents;
	}

	public String getTwoPayContents() {
		return twoPayContents;
	}

	public void setTwoPayContents(String twoPayContents) {
		this.twoPayContents = twoPayContents;
	}

	public String getThreePayContents() {
		return threePayContents;
	}

	public void setThreePayContents(String threePayContents) {
		this.threePayContents = threePayContents;
	}

	public String getFourPayContents() {
		return fourPayContents;
	}

	public void setFourPayContents(String fourPayContents) {
		this.fourPayContents = fourPayContents;
	}

	public Double getOneRequestAmount() {
		return oneRequestAmount;
	}

	public void setOneRequestAmount(Double oneRequestAmount) {
		this.oneRequestAmount = oneRequestAmount;
	}

	public User getOnePayUser() {
		return onePayUser;
	}

	public void setOnePayUser(User onePayUser) {
		this.onePayUser = onePayUser;
	}

	public User getOneRequestUser() {
		return oneRequestUser;
	}

	public void setOneRequestUser(User oneRequestUser) {
		this.oneRequestUser = oneRequestUser;
	}

	public Double getTwoRequestAmount() {
		return twoRequestAmount;
	}

	public void setTwoRequestAmount(Double twoRequestAmount) {
		this.twoRequestAmount = twoRequestAmount;
	}

	public User getTwoPayUser() {
		return twoPayUser;
	}

	public void setTwoPayUser(User twoPayUser) {
		this.twoPayUser = twoPayUser;
	}

	public User getTwoRequestUser() {
		return twoRequestUser;
	}

	public void setTwoRequestUser(User twoRequestUser) {
		this.twoRequestUser = twoRequestUser;
	}

	public Double getThreeRequestAmount() {
		return threeRequestAmount;
	}

	public void setThreeRequestAmount(Double threeRequestAmount) {
		this.threeRequestAmount = threeRequestAmount;
	}

	public User getThreePayUser() {
		return threePayUser;
	}

	public void setThreePayUser(User threePayUser) {
		this.threePayUser = threePayUser;
	}

	public User getThreeRequestUser() {
		return threeRequestUser;
	}

	public void setThreeRequestUser(User threeRequestUser) {
		this.threeRequestUser = threeRequestUser;
	}

	public Double getFourRequestAmount() {
		return fourRequestAmount;
	}

	public void setFourRequestAmount(Double fourRequestAmount) {
		this.fourRequestAmount = fourRequestAmount;
	}

	public User getFourPayUser() {
		return fourPayUser;
	}

	public void setFourPayUser(User fourPayUser) {
		this.fourPayUser = fourPayUser;
	}

	public User getFourRequestUser() {
		return fourRequestUser;
	}

	public void setFourRequestUser(User fourRequestUser) {
		this.fourRequestUser = fourRequestUser;
	}

	public PayItem getOnePayItem() {
		return onePayItem;
	}

	public void setOnePayItem(PayItem onePayItem) {
		this.onePayItem = onePayItem;
	}

	public PayItem getTwoPayItem() {
		return twoPayItem;
	}

	public void setTwoPayItem(PayItem twoPayItem) {
		this.twoPayItem = twoPayItem;
	}

	public PayItem getThreePayItem() {
		return threePayItem;
	}

	public void setThreePayItem(PayItem threePayItem) {
		this.threePayItem = threePayItem;
	}

	public PayItem getFourPayItem() {
		return fourPayItem;
	}

	public void setFourPayItem(PayItem fourPayItem) {
		this.fourPayItem = fourPayItem;
	}

	@Override
	public String getDisplayString() {
		return null;
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("assetsCategory", FormatUtils.displayString(contract.getAssetsCategory()));
    	jo.put("contractId", FormatUtils.idString(contract));
    	jo.put("contractCode", FormatUtils.stringValue(contract.getCode()));
    	jo.put("ourSideCorText", FormatUtils.displayString(contract.getOurSideCorporation()));
    	jo.put("cooperationText", FormatUtils.displayString(contract.getCooperation()));
    	jo.put("amount", FormatUtils.doubleValue(contract.getAmount()));
    	jo.put("adjustAmount", FormatUtils.doubleValue(contract.getAdjustAmount()));
    	jo.put("settleAccounts", FormatUtils.doubleValue(Calc.add(contract.getAmount(), contract.getAdjustAmount())));
    	jo.put("onePayAmount", FormatUtils.doubleValue(onePayAmount));
    	jo.put("onePayDate", FormatUtils.dateTimeValue(onePayDate));
    	jo.put("twoPayAmount", FormatUtils.doubleValue(twoPayAmount));
    	jo.put("twoPayDate", FormatUtils.dateTimeValue(twoPayDate));
    	jo.put("threePayAmount", FormatUtils.doubleValue(threePayAmount));
    	jo.put("threePayDate", FormatUtils.dateTimeValue(threePayDate));
    	jo.put("fourPayAmount", FormatUtils.doubleValue(fourPayAmount));
    	jo.put("fourPayDate", FormatUtils.dateTimeValue(fourPayDate));
    	jo.put("finishAmount", FormatUtils.doubleValue(finishAmount));
    	jo.put("blanceAmount", FormatUtils.doubleValue(blanceAmount));
    	return jo;
	}
}
