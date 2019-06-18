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
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.pgm.core.enums.RepairSettleAccountStatus;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class RepairSettleAccount extends BPMNWorkFlow {
	
	private static final long serialVersionUID = 985281866497964135L;

	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private RepairOrder repairOrder; // 维修订单
	
	private Double changeAmount = new Double(0); // 维修变更金额
	
	private Double hseAmount = new Double(0); // HSE奖惩金额
	
	private Double settleAccount = new Double(0); // 本次维修结算
	
	private String imagePath1; // 维修变更附件
	private String imagePath2; // HSE奖惩附件
	
	@Enumerated(EnumType.STRING)
	private RepairSettleAccountStatus repairSettleAccountStatus = RepairSettleAccountStatus.Draft;
	
	private String thisBudgetUpper;
	private String settleAccountUpper;
	
	@Column(columnDefinition = "text")
	private String opinions1; // 维修工程师
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User responseUser1; // 维修工程师 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")// 维修工程师
	private Date responseTime1;
	
	@Column(columnDefinition = "text")
	private String opinions2; // HSE
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User responseUser2; // HSE 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")// HSE
	private Date responseTime2;
	
	@Column(columnDefinition = "text")
	private String opinions3; // 部门经理
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User responseUser3; // 部门经理 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")// 部门经理
	private Date responseTime3;
	
	@Column(columnDefinition = "text")
	private String opinions4; // 分管副总响应/意见
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User responseUser4; // 分管副总响应 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")// 分管副总
	private Date responseTime4;
	
	@Column(columnDefinition = "text")
	private String opinions5; //// 总经理
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User responseUser5; // // 总经理 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")// 总经理
	private Date responseTime5;
	
	@Transient
	private Double historySettle = new Double(0); //　历次维修费
	@Transient
	private Double yearBudget = new Double(0); // 年度预算
	// 以下用于记录审批意见信息
	@Transient
	private String opinions;
	@Transient
	private String taskName;
	
	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("code", FormatUtils.stringValue(repairOrder.getCode()));
    	jo.put("project", FormatUtils.displayString(repairOrder.getPetrolStation()));
    	jo.put("asset", FormatUtils.stringValue(repairOrder.getAsset().getName()));
    	jo.put("budget", FormatUtils.doubleValue(repairOrder.getBudget()));
    	jo.put("repairType", FormatUtils.displayString(repairOrder.getRepairType()));
    	jo.put("requestUser", FormatUtils.stringValue(repairOrder.getCreatedBy().getRealName()));
    	jo.put("repairSettleAccountStatus", FormatUtils.displayString(repairSettleAccountStatus));
    	return jo;
    }

	public String getOpinions1() {
		return opinions1;
	}

	public void setOpinions1(String opinions1) {
		this.opinions1 = opinions1;
	}

	public User getResponseUser1() {
		return responseUser1;
	}

	public void setResponseUser1(User responseUser1) {
		this.responseUser1 = responseUser1;
	}

	public Date getResponseTime1() {
		return responseTime1;
	}

	public void setResponseTime1(Date responseTime1) {
		this.responseTime1 = responseTime1;
	}

	public String getOpinions2() {
		return opinions2;
	}

	public void setOpinions2(String opinions2) {
		this.opinions2 = opinions2;
	}

	public User getResponseUser2() {
		return responseUser2;
	}

	public void setResponseUser2(User responseUser2) {
		this.responseUser2 = responseUser2;
	}

	public Date getResponseTime2() {
		return responseTime2;
	}

	public void setResponseTime2(Date responseTime2) {
		this.responseTime2 = responseTime2;
	}

	public String getOpinions3() {
		return opinions3;
	}

	public void setOpinions3(String opinions3) {
		this.opinions3 = opinions3;
	}

	public User getResponseUser3() {
		return responseUser3;
	}

	public void setResponseUser3(User responseUser3) {
		this.responseUser3 = responseUser3;
	}

	public Date getResponseTime3() {
		return responseTime3;
	}

	public void setResponseTime3(Date responseTime3) {
		this.responseTime3 = responseTime3;
	}

	public String getOpinions4() {
		return opinions4;
	}

	public void setOpinions4(String opinions4) {
		this.opinions4 = opinions4;
	}

	public User getResponseUser4() {
		return responseUser4;
	}

	public void setResponseUser4(User responseUser4) {
		this.responseUser4 = responseUser4;
	}

	public Date getResponseTime4() {
		return responseTime4;
	}

	public void setResponseTime4(Date responseTime4) {
		this.responseTime4 = responseTime4;
	}

	public String getOpinions5() {
		return opinions5;
	}

	public void setOpinions5(String opinions5) {
		this.opinions5 = opinions5;
	}

	public User getResponseUser5() {
		return responseUser5;
	}

	public void setResponseUser5(User responseUser5) {
		this.responseUser5 = responseUser5;
	}

	public Date getResponseTime5() {
		return responseTime5;
	}

	public void setResponseTime5(Date responseTime5) {
		this.responseTime5 = responseTime5;
	}

	public RepairOrder getRepairOrder() {
		return repairOrder;
	}

	public void setRepairOrder(RepairOrder repairOrder) {
		this.repairOrder = repairOrder;
	}

	public Double getYearBudget() {
		return yearBudget;
	}

	public void setYearBudget(Double yearBudget) {
		this.yearBudget = yearBudget;
	}

	public Double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Double changeAmount) {
		this.changeAmount = changeAmount;
	}

	public Double getHseAmount() {
		return hseAmount;
	}

	public void setHseAmount(Double hseAmount) {
		this.hseAmount = hseAmount;
	}

	public Double getSettleAccount() {
		return settleAccount;
	}

	public void setSettleAccount(Double settleAccount) {
		this.settleAccount = settleAccount;
	}

	public String getImagePath1() {
		return imagePath1;
	}

	public void setImagePath1(String imagePath1) {
		this.imagePath1 = imagePath1;
	}

	public String getImagePath2() {
		return imagePath2;
	}

	public void setImagePath2(String imagePath2) {
		this.imagePath2 = imagePath2;
	}

	public void setFilePath(int i, String path) {
		if (i == 1) {
			setImagePath1(path);
		} else  {
			setImagePath2(path);
		} 
	}
	
	public String getFilePath(int i) {
		if (i == 1) {
			return getImagePath1();
		} else {
			return getImagePath2();
		} 
	}
	
	public Double getHistorySettle() {
		return historySettle;
	}

	public void setHistorySettle(Double historySettle) {
		this.historySettle = historySettle;
	}


	public RepairSettleAccountStatus getRepairSettleAccountStatus() {
		return repairSettleAccountStatus;
	}

	public void setRepairSettleAccountStatus(
			RepairSettleAccountStatus repairSettleAccountStatus) {
		this.repairSettleAccountStatus = repairSettleAccountStatus;
	}

	public String getThisBudgetUpper() {
		return thisBudgetUpper;
	}

	public void setThisBudgetUpper(String thisBudgetUpper) {
		this.thisBudgetUpper = thisBudgetUpper;
	}

	public String getSettleAccountUpper() {
		return settleAccountUpper;
	}

	public void setSettleAccountUpper(String settleAccountUpper) {
		this.settleAccountUpper = settleAccountUpper;
	}

	public String getOpinions() {
		return opinions;
	}

	public void setOpinions(String opinions) {
		this.opinions = opinions;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}

}
