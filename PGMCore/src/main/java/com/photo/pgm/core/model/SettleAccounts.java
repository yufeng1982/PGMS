/**
 * 
 */
package com.photo.pgm.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.model.entity.AbsEntity;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.pgm.core.enums.AdjustType;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class SettleAccounts extends AbsEntity {

	private static final long serialVersionUID = 1949420385772064743L;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Contract contract;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Department department;
	
	private Double adjustAmount = new Double(0); // 调整金额
	private Double settleAmount = new Double(0); // 结算金额

	@Enumerated(EnumType.STRING)
	private AdjustType adjustType = AdjustType.Add;
	
	@Column(columnDefinition = "text")
	private String adjustReason;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date approveDate;
	
	private String attachmentName;
	private String filePath;
	
	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Double getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(Double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Double getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(Double settleAmount) {
		this.settleAmount = settleAmount;
	}

	public AdjustType getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(AdjustType adjustType) {
		this.adjustType = adjustType;
	}

	@Override
	public String getDisplayString() {
		return null;
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("contractText", FormatUtils.displayString(contract));
    	jo.put("contractCode", FormatUtils.stringValue(contract.getCode()));
    	jo.put("projectText", FormatUtils.displayString(contract.getPetrolStation()));
    	jo.put("cooperation", FormatUtils.displayString(contract.getCooperation()));
    	jo.put("adjustType", FormatUtils.displayString(adjustType));
    	jo.put("amount", FormatUtils.doubleValue(contract.getAmount()));
    	jo.put("adjustAmount", FormatUtils.doubleValue(adjustAmount));
    	jo.put("settleAmount", FormatUtils.doubleValue(settleAmount));
    	jo.put("department", FormatUtils.displayString(department));
    	return jo;
    }
	
}
