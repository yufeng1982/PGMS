/**
 * 
 */
package com.photo.pgm.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.pgm.core.enums.PayType;

/**
 * @author FengYu
 * 保存确认的时候更新PayAccount, contract 上的相应字段 
 */
@Entity
@Table(schema = "project")
@Audited
public class PayItem extends AbsCodeNameEntity {

	private static final long serialVersionUID = 2180022460270023457L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private PetrolStation petrolStation; // 工程管理-油站信息
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Contract contract; // 合同
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private CooperationAccount cooperationAccount; // 付款账号
	
	private Double requestAmount = new Double(0); // 申请付款金额
	
	private String assetNo; // 资产编号
	
	private Double payAmount = new Double(0); // 付款金额（次字段需更新到合同上）
	
	private Double adjustAmount = new Double(0); // 调整金额
	private Double settleAccounts = new Double(0); // 结算金额
	
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User payUser; // 付款人
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Department department; // 申请部门
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Cooperation cooperation; // 对方单位
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date payDate; // 付款日期
	
	private String filePath1; // 附件1地址
	private String filePath2; // 附件2地址
	
	private String filePath3; // 附件1地址
	private String filePath4; // 附件2地址
	
	@Column(columnDefinition = "text")
	private String payContents;
	
	@Enumerated(EnumType.STRING)
	private PayType payType = PayType.Draft;
	
	private Integer payCount = new Integer(0);// 记录是第几次申请
	

	public PetrolStation getPetrolStation() {
		return petrolStation;
	}

	public void setPetrolStation(PetrolStation petrolStation) {
		this.petrolStation = petrolStation;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Double getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(Double requestAmount) {
		this.requestAmount = requestAmount;
	}

	public String getAssetNo() {
		return assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public User getPayUser() {
		return payUser;
	}

	public void setPayUser(User payUser) {
		this.payUser = payUser;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getFilePath1() {
		return filePath1;
	}

	public void setFilePath1(String filePath1) {
		this.filePath1 = filePath1;
	}

	public String getFilePath2() {
		return filePath2;
	}

	public void setFilePath2(String filePath2) {
		this.filePath2 = filePath2;
	}

	public String getFilePath3() {
		return filePath3;
	}

	public void setFilePath3(String filePath3) {
		this.filePath3 = filePath3;
	}

	public String getFilePath4() {
		return filePath4;
	}

	public void setFilePath4(String filePath4) {
		this.filePath4 = filePath4;
	}

	public String getPayContents() {
		return payContents;
	}

	public void setPayContents(String payContents) {
		this.payContents = payContents;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public Integer getPayCount() {
		return payCount;
	}

	public void setPayCount(Integer payCount) {
		this.payCount = payCount;
	}

	public String getFilePath(int i) {
		if (i == 1) {
			return getFilePath1();
		} else if (i == 2) {
			return getFilePath2();
		} else if (i == 3) {
			return getFilePath3();
		} else {
			return getFilePath4();
		}
	}
	
	public void setFilePath(int i, String path) {
		if (i == 1) {
			setFilePath1(path);
		} else if (i == 2) {
			setFilePath2(path);
		} else if (i == 3) {
			setFilePath3(path);
		} else {
			setFilePath4(path);
		}
	}
	
	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Cooperation getCooperation() {
		return cooperation;
	}

	public void setCooperation(Cooperation cooperation) {
		this.cooperation = cooperation;
	}


	public Double getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(Double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public Double getSettleAccounts() {
		return settleAccounts;
	}

	public void setSettleAccounts(Double settleAccounts) {
		this.settleAccounts = settleAccounts;
	}


	public CooperationAccount getCooperationAccount() {
		return cooperationAccount;
	}

	public void setCooperationAccount(CooperationAccount cooperationAccount) {
		this.cooperationAccount = cooperationAccount;
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("project", FormatUtils.idString(petrolStation));
    	jo.put("projectText", FormatUtils.displayString(petrolStation));
    	jo.put("contract", FormatUtils.idString(contract));
    	jo.put("contractCode", FormatUtils.stringValue(contract.getCode()));
    	jo.put("contractText", FormatUtils.stringValue(contract.getName()));
    	jo.put("requestAmount", FormatUtils.doubleValue(requestAmount));
    	jo.put("createUer", FormatUtils.stringValue(getCreatedBy().getRealName()));
    	jo.put("department", FormatUtils.displayString(department));
    	jo.put("status", FormatUtils.displayString(payType));
    	jo.put("payUser", FormatUtils.displayString(payUser));
    	jo.put("payDate", FormatUtils.dateTimeValue(payDate));
    	jo.put("cooperationText", FormatUtils.displayString(cooperation));
    	jo.put("cooperationAccount", FormatUtils.idString(cooperationAccount));
    	jo.put("receiveNo", FormatUtils.stringValue(cooperationAccount.getReceiveNo()));
    	jo.put("amount", FormatUtils.doubleValue(contract.getAmount()));
    	jo.put("adjustAmount", FormatUtils.doubleValue(adjustAmount));
    	jo.put("settleAccounts", FormatUtils.doubleValue(settleAccounts));
    	jo.put("payCount", FormatUtils.intValue(payCount));
    	jo.put("assetNo", FormatUtils.stringValue(contract.getContractAssetNo()));
    	return jo;
    }
	
}
