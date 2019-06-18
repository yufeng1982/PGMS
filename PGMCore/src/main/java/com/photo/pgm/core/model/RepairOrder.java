/**
 * 
 */
package com.photo.pgm.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.pgm.core.enums.AdjustType;
import com.photo.pgm.core.enums.BreakdownReason;
import com.photo.pgm.core.enums.RepairStatus;
import com.photo.pgm.core.enums.RepairType;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class RepairOrder extends BPMNWorkFlow {
	
	private static final long serialVersionUID = 985281866497964135L;

	//------------------------------------- 维修单信息-----------------------------------------------
	private Integer seq = new Integer(0);
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private PetrolStation petrolStation; // 工程管理-油站信息
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Asset asset; // 资产信息
	
	@Enumerated(EnumType.STRING)
	private RepairType repairType; // 维修类型
	
	private String contact; // 联系方式
	
	private Double budget = new Double(0); // 维修预算
	
	private Double actualAmount = new Double(0); // 实际维修金额
	
	@Column(columnDefinition = "text")
	private String comment;
	
	@Enumerated(EnumType.STRING)
	private BreakdownReason breakdownReason;// 故障原因
	
	@Enumerated(EnumType.STRING)
	private RepairStatus repairStatus = RepairStatus.Draft; // 维修状态
	
	private String imagePath1;
	private String imagePath2;
	private String imagePath3;
	
	//------------------------------------- 审批意见信息-----------------------------------------------
	
	private Double currentBudget = new Double(0); // 本次维修预算
	
	@Transient
	private Double yearBudget = new Double(0); // 年度预算
	@Transient
	private Double totalRepairAmount = new Double(0); // 累计历次维修费用
	
	private String repairSettleAccountFilePath; // 维修预算附件
	
	@Column(columnDefinition = "text")
	private String repairSolutions; // 维修方案
	
	private String repairSolutionsFilePath; // 维修方案附件
	private Double repairTime = new Double(0); // 维修耗时
	
	@Type(type = "true_false")
	@Audited
	private Boolean lgyxk = Boolean.FALSE;// 冷工作业许可
	
	@Type(type = "true_false")
	@Audited
	private Boolean rgyxk = Boolean.FALSE;// 热工作业许可
	
	@Type(type = "true_false")
	@Audited
	private Boolean dqxk = Boolean.FALSE;// 电气作业许可
	
	@Type(type = "true_false")
	@Audited
	private Boolean dgxk = Boolean.FALSE;// 登高作业许可
	
	@Type(type = "true_false")
	@Audited
	private Boolean sxxk = Boolean.FALSE;// 受限空间作业许可
	
	@Column(columnDefinition = "text")
	private String opinions1; // 维修工程师响应意见
	
	@Column(columnDefinition = "text")
	private String opinions2; // 维修工程师审核意见
	
	@Column(columnDefinition = "text")
	private String opinions21; // 区域经理维修技师响应
	
	@Column(columnDefinition = "text")
	private String opinions3; // 造价工程师
	
	@Column(columnDefinition = "text")
	private String opinions4; // 部门经理意见
	
	@Column(columnDefinition = "text")
	private String opinions41; // 运营经理意见
	
	@Column(columnDefinition = "text")
	private String opinions5; // HSE意见
	
	@Column(columnDefinition = "text")
	private String opinions6; // 分管副总意见
	
	@Column(columnDefinition = "text")
	private String opinions7; // 总经理意见
	
	@Column(columnDefinition = "text")
	private String opinions8; // 财务意见
	
	@Column(columnDefinition = "text")
	private String opinions9; // 维修工程师派单
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User zdUser;// 指定用户
	
	@Column(columnDefinition = "text")
	private String opinions10; // 工程师维修技师意见
	
	@Column(columnDefinition = "text")
	private String opinions11; // 工程师维修技师意见
	
	private String repairedImagePath1; // 维修后图片路径
	private String repairedImagePath2; // 维修后图片路径
	private String repairedImagePath3; // 维修后图片路径
	
	@ManyToOne(fetch = FetchType.LAZY)
	private YearBudgets yearBudgets;
	
	//===============================================结算=======================================
	
	private Boolean adjust = Boolean.FALSE;
	
	private Double changeAmount = new Double(0); // 维修变更金额

	@Enumerated(EnumType.STRING)
	private AdjustType adjustType = AdjustType.Add;
	
	private String imagePath9; // 维修变更附件
	private Double settleAccount = new Double(0); // 本次维修结算
	private String thisBudgetUpper;
	
	@Column(columnDefinition = "text")
	private String opinions12; // 结算 调整意见
	@Column(columnDefinition = "text")
	private String opinions13; // 结算 部门经理意见
	@Column(columnDefinition = "text")
	private String opinions14; // 结算 分管副总意见
	@Column(columnDefinition = "text")
	private String opinions15; // 结算 总经理意见
	
	@Type(type = "true_false")
	@Audited
	private Boolean tk1approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk2approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk3approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk4approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk41approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk5approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk6approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk7approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk8approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk9approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk10approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk11approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk12approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk13approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk14approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk15approve = Boolean.FALSE;
	@Type(type = "true_false")
	@Audited
	private Boolean tk16approve = Boolean.FALSE;
	
	// 以下用于记录审批意见信息
	@Transient
	private String opinions;
	@Transient
	private String taskName;
	
	
	
	public boolean isTk1approve() {
		return tk1approve == null ? false : tk1approve;
	}

	public void setTk1approve(boolean tk1approve) {
		this.tk1approve = tk1approve;
	}

	public boolean isTk2approve() {
		return tk2approve == null ? false : tk2approve;
	}

	public void setTk2approve(boolean tk2approve) {
		this.tk2approve = tk2approve;
	}

	public boolean isTk3approve() {
		return tk3approve == null ? false : tk3approve;
	}

	public void setTk3approve(boolean tk3approve) {
		this.tk3approve = tk3approve;
	}

	public boolean isTk4approve() {
		return tk4approve == null ? false : tk4approve;
	}

	public void setTk4approve(boolean tk4approve) {
		this.tk4approve = tk4approve;
	}

	public boolean isTk41approve() {
		return tk41approve == null ? false : tk41approve;
	}

	public void setTk41approve(boolean tk41approve) {
		this.tk41approve = tk41approve;
	}

	public boolean isTk5approve() {
		return tk5approve == null ? false : tk5approve;
	}

	public void setTk5approve(boolean tk5approve) {
		this.tk5approve = tk5approve;
	}

	public boolean isTk6approve() {
		return tk6approve == null ? false : tk6approve;
	}

	public void setTk6approve(boolean tk6approve) {
		this.tk6approve = tk6approve;
	}

	public boolean isTk7approve() {
		return tk7approve == null ? false : tk7approve;
	}

	public void setTk7approve(boolean tk7approve) {
		this.tk7approve = tk7approve;
	}

	public boolean isTk8approve() {
		return tk8approve == null ? false : tk8approve;
	}

	public void setTk8approve(boolean tk8approve) {
		this.tk8approve = tk8approve;
	}

	public boolean isTk9approve() {
		return tk9approve == null ? false : tk9approve;
	}

	public void setTk9approve(boolean tk9approve) {
		this.tk9approve = tk9approve;
	}

	public boolean isTk10approve() {
		return tk10approve == null ? false : tk10approve;
	}

	public void setTk10approve(boolean tk10approve) {
		this.tk10approve = tk10approve;
	}

	public boolean isTk11approve() {
		return tk11approve == null ? false : tk11approve;
	}

	public void setTk11approve(boolean tk11approve) {
		this.tk11approve = tk11approve;
	}

	public boolean isTk12approve() {
		return tk12approve == null ? false : tk12approve;
	}

	public void setTk12approve(boolean tk12approve) {
		this.tk12approve = tk12approve;
	}

	public boolean isTk13approve() {
		return tk13approve == null ? false : tk13approve;
	}

	public void setTk13approve(boolean tk13approve) {
		this.tk13approve = tk13approve;
	}

	public boolean isTk14approve() {
		return tk14approve == null ? false : tk14approve;
	}

	public void setTk14approve(boolean tk14approve) {
		this.tk14approve = tk14approve;
	}

	public boolean isTk15approve() {
		return tk15approve == null ? false : tk15approve;
	}

	public void setTk15approve(boolean tk15approve) {
		this.tk15approve = tk15approve;
	}

	public boolean isTk16approve() {
		return tk16approve == null ? false : tk16approve;
	}

	public void setTk16approve(boolean tk16approve) {
		this.tk16approve = tk16approve;
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("seq", FormatUtils.intValue(seq));
    	jo.put("projectId", FormatUtils.idString(petrolStation));
    	jo.put("project", FormatUtils.displayString(petrolStation));
    	jo.put("assetId", FormatUtils.idString(asset));
    	jo.put("asset", FormatUtils.stringValue(asset.getName()));
    	jo.put("budget", getRepairType().equals(RepairType.Small) ? FormatUtils.doubleValue(budget) : FormatUtils.doubleValue(currentBudget));
    	jo.put("actualAmount", FormatUtils.doubleValue(actualAmount));
    	jo.put("repairType", FormatUtils.displayString(repairType));
    	jo.put("repairTypeCode", FormatUtils.nameString(repairType));
    	jo.put("createUser", FormatUtils.stringValue(getCreatedBy().getRealName()));
    	jo.put("repairStatus", FormatUtils.displayString(repairStatus));
    	jo.put("repairStatusCode", FormatUtils.nameString(repairStatus));
    	jo.put("contact", FormatUtils.stringValue(contact));
    	jo.put("breakdownReason", FormatUtils.displayString(breakdownReason));
    	jo.put("breakdownReasonCode", FormatUtils.nameString(breakdownReason));
    	return jo;
    }
	
	public String getOpinions41() {
		return opinions41;
	}

	public void setOpinions41(String opinions41) {
		this.opinions41 = opinions41;
	}

	public String getOpinions12() {
		return opinions12;
	}

	public void setOpinions12(String opinions12) {
		this.opinions12 = opinions12;
	}

	public String getOpinions14() {
		return opinions14;
	}

	public void setOpinions14(String opinions14) {
		this.opinions14 = opinions14;
	}

	public String getOpinions15() {
		return opinions15;
	}

	public void setOpinions15(String opinions15) {
		this.opinions15 = opinions15;
	}

	public Double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Double changeAmount) {
		this.changeAmount = changeAmount;
	}

	public AdjustType getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(AdjustType adjustType) {
		this.adjustType = adjustType;
	}

	public String getImagePath9() {
		return imagePath9;
	}

	public void setImagePath9(String imagePath9) {
		this.imagePath9 = imagePath9;
	}

	public Double getSettleAccount() {
		return settleAccount;
	}

	public void setSettleAccount(Double settleAccount) {
		this.settleAccount = settleAccount;
	}

	public String getThisBudgetUpper() {
		return thisBudgetUpper;
	}

	public void setThisBudgetUpper(String thisBudgetUpper) {
		this.thisBudgetUpper = thisBudgetUpper;
	}

	public String getOpinions13() {
		return opinions13;
	}

	public void setOpinions13(String opinions13) {
		this.opinions13 = opinions13;
	}

	public boolean isAdjust() {
		return adjust;
	}

	public void setAdjust(boolean adjust) {
		this.adjust = adjust;
	}

	public YearBudgets getYearBudgets() {
		return yearBudgets;
	}

	public void setYearBudgets(YearBudgets yearBudgets) {
		this.yearBudgets = yearBudgets;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public PetrolStation getPetrolStation() {
		return petrolStation;
	}

	public void setPetrolStation(PetrolStation petrolStation) {
		this.petrolStation = petrolStation;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public RepairType getRepairType() {
		return repairType;
	}

	public void setRepairType(RepairType repairType) {
		this.repairType = repairType;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getImagePath3() {
		return imagePath3;
	}

	public void setImagePath3(String imagePath3) {
		this.imagePath3 = imagePath3;
	}

	public void setFilePath(int i, String path) {
		if (i == 1) {
			setImagePath1(path);
		} else if (i == 2) {
			setImagePath2(path);
		} else if (i == 3) {
			setImagePath3(path);
		} else if (i == 4) {
			setRepairedImagePath1(path);
		} else if (i == 5) {
			setRepairedImagePath2(path);
		} else if (i == 6) {
			setRepairedImagePath3(path);
		} else if (i == 7) {
			setRepairSettleAccountFilePath(path);
		} else if (i == 8) {
			setRepairSolutionsFilePath(path);
		} else if (i == 9) {
			setImagePath9(path);
		} 
	}
	
	public String getFilePath(int i) {
		if (i == 1) {
			return getImagePath1();
		} else if (i == 2) {
			return getImagePath2();
		} else if (i == 3) {
			return getImagePath3();
		} else if (i == 4) {
			return getRepairedImagePath1();
		} else if (i == 5) {
			return getRepairedImagePath2();
		} else if (i == 6) {
			return getRepairedImagePath3();
		} else if (i == 7) {
			return getRepairSettleAccountFilePath();
		} else if (i == 8) {
			return getRepairSolutionsFilePath();
		} else if (i == 9) {
			return getImagePath9();
		} else {
			return "";
		}
	}
	
	public BreakdownReason getBreakdownReason() {
		return breakdownReason;
	}

	public void setBreakdownReason(BreakdownReason breakdownReason) {
		this.breakdownReason = breakdownReason;
	}

	public RepairStatus getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(RepairStatus repairStatus) {
		this.repairStatus = repairStatus;
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

	public String getOpinions1() {
		return opinions1;
	}

	public void setOpinions1(String opinions1) {
		this.opinions1 = opinions1;
	}

	public Double getCurrentBudget() {
		return currentBudget;
	}

	public void setCurrentBudget(Double currentBudget) {
		this.currentBudget = currentBudget;
	}

	public Double getYearBudget() {
		return yearBudget;
	}

	public void setYearBudget(Double yearBudget) {
		this.yearBudget = yearBudget;
	}

	public Double getTotalRepairAmount() {
		return totalRepairAmount;
	}

	public void setTotalRepairAmount(Double totalRepairAmount) {
		this.totalRepairAmount = totalRepairAmount;
	}

	public String getRepairSettleAccountFilePath() {
		return repairSettleAccountFilePath;
	}

	public void setRepairSettleAccountFilePath(String repairSettleAccountFilePath) {
		this.repairSettleAccountFilePath = repairSettleAccountFilePath;
	}

	public String getRepairSolutions() {
		return repairSolutions;
	}

	public void setRepairSolutions(String repairSolutions) {
		this.repairSolutions = repairSolutions;
	}

	public String getRepairSolutionsFilePath() {
		return repairSolutionsFilePath;
	}

	public void setRepairSolutionsFilePath(String repairSolutionsFilePath) {
		this.repairSolutionsFilePath = repairSolutionsFilePath;
	}

	public Double getRepairTime() {
		return repairTime;
	}

	public void setRepairTime(Double repairTime) {
		this.repairTime = repairTime;
	}

	public boolean isLgyxk() {
		return lgyxk;
	}

	public void setLgyxk(boolean lgyxk) {
		this.lgyxk = lgyxk;
	}

	public boolean isRgyxk() {
		return rgyxk;
	}

	public void setRgyxk(boolean rgyxk) {
		this.rgyxk = rgyxk;
	}

	public boolean isDqxk() {
		return dqxk;
	}

	public void setDqxk(boolean dqxk) {
		this.dqxk = dqxk;
	}

	public boolean isDgxk() {
		return dgxk;
	}

	public void setDgxk(boolean dgxk) {
		this.dgxk = dgxk;
	}

	public boolean isSxxk() {
		return sxxk;
	}

	public void setSxxk(boolean sxxk) {
		this.sxxk = sxxk;
	}


	public String getOpinions2() {
		return opinions2;
	}

	public void setOpinions2(String opinions2) {
		this.opinions2 = opinions2;
	}


	public String getOpinions3() {
		return opinions3;
	}

	public void setOpinions3(String opinions3) {
		this.opinions3 = opinions3;
	}


	public String getOpinions4() {
		return opinions4;
	}

	public void setOpinions4(String opinions4) {
		this.opinions4 = opinions4;
	}


	public String getOpinions5() {
		return opinions5;
	}

	public void setOpinions5(String opinions5) {
		this.opinions5 = opinions5;
	}


	public String getOpinions6() {
		return opinions6;
	}

	public void setOpinions6(String opinions6) {
		this.opinions6 = opinions6;
	}


	public String getOpinions7() {
		return opinions7;
	}

	public void setOpinions7(String opinions7) {
		this.opinions7 = opinions7;
	}


	public String getOpinions8() {
		return opinions8;
	}

	public void setOpinions8(String opinions8) {
		this.opinions8 = opinions8;
	}


	public String getOpinions9() {
		return opinions9;
	}

	public void setOpinions9(String opinions9) {
		this.opinions9 = opinions9;
	}


	public String getOpinions10() {
		return opinions10;
	}

	public void setOpinions10(String opinions10) {
		this.opinions10 = opinions10;
	}


	public String getOpinions11() {
		return opinions11;
	}

	public void setOpinions11(String opinions11) {
		this.opinions11 = opinions11;
	}

	public String getRepairedImagePath1() {
		return repairedImagePath1;
	}

	public void setRepairedImagePath1(String repairedImagePath1) {
		this.repairedImagePath1 = repairedImagePath1;
	}

	public String getRepairedImagePath2() {
		return repairedImagePath2;
	}

	public void setRepairedImagePath2(String repairedImagePath2) {
		this.repairedImagePath2 = repairedImagePath2;
	}

	public String getRepairedImagePath3() {
		return repairedImagePath3;
	}

	public void setRepairedImagePath3(String repairedImagePath3) {
		this.repairedImagePath3 = repairedImagePath3;
	}

	public String getOpinions21() {
		return opinions21;
	}

	public void setOpinions21(String opinions21) {
		this.opinions21 = opinions21;
	}


	public User getZdUser() {
		return zdUser;
	}

	public void setZdUser(User zdUser) {
		this.zdUser = zdUser;
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
