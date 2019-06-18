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
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.utils.Calc;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.pgm.core.enums.ContractStatus;
import com.photo.pgm.core.enums.ContractType;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class Contract extends BPMNWorkFlow {

	private static final long serialVersionUID = -3120151296609522387L;

//	@ManyToOne
//	private ContractProperty contractProperty; // 合同属性
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private FlowDefinition flowDefinition;
	
	@Enumerated(EnumType.STRING)
	private ContractStatus contractStatus = ContractStatus.Draft; // 合同状态
	
	@Enumerated(EnumType.STRING)
	private ContractType contractType; // 合同属性
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Contract ownerContract; // 隶属合同（合同属性不是主合同的时候存在隶属关系）
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private ContractCategory contractCategory; // 合同类别
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private AssetsCategory assetsCategory; // 物资类别（资产类别）
	
	private Double amount = new Double(0); // 合同金额
	
	private Double adjustAmount = new Double(0); // 调整金额
	
	private Double payAmount = new Double(0); // 已付款金额
	
	private Integer payCounts = new Integer(0);// 付款次数 最多4次
	
	private Double onePercent = new Double(35); // 第一次付款比例
	private Double onePayAmount = new Double(0); // 第一次付款金额
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date onePayDate; // 第一次付款时间
	
	private Double twoPercent = new Double(35); // 第二次付款比例
	private Double twoPayAmount = new Double(0); // 第二次付款金额
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date twoPayDate; // 第二次付款时间
	
	private Double threePercent = new Double(25); // 第三次付款比例
	private Double threePayAmount = new Double(0); // 第三次付款金额
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date threePayDate; // 第三次付款时间
	
	private Double fourPercent = new Double(5); // 第四次付款比例
	private Double fourPayAmount = new Double(0); // 第四次付款金额
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date fourPayDate; // 第四次付款时间
	
	@Column(columnDefinition = "text")
	private String payCondition; // 付款条款
	
	@Column(columnDefinition = "text")
	private String contents; // 商务条款
	
	private String filePath; // 合同附件存放路径
	
	private String archiveFilePath; // 归档文件路径
	
	private String signingReason; // 签约事由
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private OurSideCorporation ourSideCorporation; // 我方签约主体
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private PetrolStation petrolStation; // 工程管理-油站信息
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private CostCenter costCenter; // 成本中心
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Cooperation cooperation;// 合作单位（对方单位）名称
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private CooperationAccount cooperationAccount;// 合作单位（对方单位）账号
	
	private Integer qualityGuaranteePeriod; // 保质期
	
	private String seqNo;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Department department;
	
	@Type(type = "true_false")
	private Boolean reject = Boolean.FALSE;
	
	
	private String contractAssetNo;// 合同资产编号
	
	@Type(type = "true_false")
	private Boolean continueRequest = Boolean.TRUE;// 是否可以继续申请付款
	@Type(type = "true_false")
	private Boolean rejectRequest = Boolean.FALSE; // 是否驳回付款申请 	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private YearBudgets yearBudgets;
	
	// 以下用于记录审批意见信息
	@Transient
	private String opinions;
	@Transient
	private String taskName;
	
//	public ContractProperty getContractProperty() {
//		return contractProperty;
//	}
//
//	public void setContractProperty(ContractProperty contractProperty) {
//		this.contractProperty = contractProperty;
//	}

	public ContractType getContractType() {
		return contractType;
	}

	public FlowDefinition getFlowDefinition() {
		return flowDefinition;
	}

	public void setFlowDefinition(FlowDefinition flowDefinition) {
		this.flowDefinition = flowDefinition;
	}

	public ContractStatus getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(ContractStatus contractStatus) {
		this.contractStatus = contractStatus;
	}

	public void setContractType(ContractType contractType) {
		this.contractType = contractType;
	}
	
	public Contract getOwnerContract() {
		return ownerContract;
	}

	public void setOwnerContract(Contract ownerContract) {
		this.ownerContract = ownerContract;
	}

	public ContractCategory getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(ContractCategory contractCategory) {
		this.contractCategory = contractCategory;
	}

	public AssetsCategory getAssetsCategory() {
		return assetsCategory;
	}

	public void setAssetsCategory(AssetsCategory assetsCategory) {
		this.assetsCategory = assetsCategory;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(Double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getOnePercent() {
		return onePercent;
	}

	public void setOnePercent(Double onePercent) {
		this.onePercent = onePercent;
	}

	public Double getTwoPercent() {
		return twoPercent;
	}

	public void setTwoPercent(Double twoPercent) {
		this.twoPercent = twoPercent;
	}

	public Double getThreePercent() {
		return threePercent;
	}

	public void setThreePercent(Double threePercent) {
		this.threePercent = threePercent;
	}

	public Double getFourPercent() {
		return fourPercent;
	}

	public void setFourPercent(Double fourPercent) {
		this.fourPercent = fourPercent;
	}

	public String getPayCondition() {
		return payCondition;
	}

	public void setPayCondition(String payCondition) {
		this.payCondition = payCondition;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public OurSideCorporation getOurSideCorporation() {
		return ourSideCorporation;
	}

	public void setOurSideCorporation(OurSideCorporation ourSideCorporation) {
		this.ourSideCorporation = ourSideCorporation;
	}


	public PetrolStation getPetrolStation() {
		return petrolStation;
	}

	public void setPetrolStation(PetrolStation petrolStation) {
		this.petrolStation = petrolStation;
	}

	public CostCenter getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(CostCenter costCenter) {
		this.costCenter = costCenter;
	}

	public Cooperation getCooperation() {
		return cooperation;
	}

	public void setCooperation(Cooperation cooperation) {
		this.cooperation = cooperation;
	}

	public Integer getQualityGuaranteePeriod() {
		return qualityGuaranteePeriod;
	}

	public void setQualityGuaranteePeriod(Integer qualityGuaranteePeriod) {
		this.qualityGuaranteePeriod = qualityGuaranteePeriod;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getArchiveFilePath() {
		return archiveFilePath;
	}

	public void setArchiveFilePath(String archiveFilePath) {
		this.archiveFilePath = archiveFilePath;
	}

	public boolean isReject() {
		return reject;
	}

	public void setReject(boolean reject) {
		this.reject = reject;
	}

	public Integer getPayCounts() {
		return payCounts;
	}

	public void setPayCounts(Integer payCounts) {
		this.payCounts = payCounts;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getContractAssetNo() {
		return contractAssetNo;
	}

	public void setContractAssetNo(String contractAssetNo) {
		this.contractAssetNo = contractAssetNo;
	}

	public boolean isContinueRequest() {
		return continueRequest;
	}

	public void setContinueRequest(boolean continueRequest) {
		this.continueRequest = continueRequest;
	}

	public boolean isRejectRequest() {
		return rejectRequest;
	}

	public void setRejectRequest(boolean rejectRequest) {
		this.rejectRequest = rejectRequest;
	}

	public String getSigningReason() {
		return signingReason;
	}

	public void setSigningReason(String signingReason) {
		this.signingReason = signingReason;
	}

	public CooperationAccount getCooperationAccount() {
		return cooperationAccount;
	}

	public void setCooperationAccount(CooperationAccount cooperationAccount) {
		this.cooperationAccount = cooperationAccount;
	}

	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}

	public boolean isDisable() {
		if (getContractStatus().equals(ContractStatus.Draft) 
				|| getContractStatus().equals(ContractStatus.PendingSend) ) {
			return false;
		}
		return true;
	}
	
	public YearBudgets getYearBudgets() {
		return yearBudgets;
	}

	public void setYearBudgets(YearBudgets yearBudgets) {
		this.yearBudgets = yearBudgets;
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("contractPropertyText", FormatUtils.displayString(contractType));
    	jo.put("ownerContract", FormatUtils.idString(ownerContract));
    	jo.put("ownerContractText", FormatUtils.displayString(ownerContract));
    	jo.put("contractCategory", FormatUtils.idString(contractCategory));
    	jo.put("contractCategoryText", FormatUtils.displayString(contractCategory));
    	jo.put("assetsCategory", FormatUtils.idString(assetsCategory));
    	jo.put("assetsCategoryText", FormatUtils.displayString(assetsCategory));
    	jo.put("ourSideCorporation", FormatUtils.idString(ourSideCorporation));
    	jo.put("ourSideCorporationText", FormatUtils.displayString(ourSideCorporation));
    	jo.put("project", FormatUtils.idString(petrolStation));
    	jo.put("projectText", FormatUtils.displayString(petrolStation));
    	jo.put("cooperation", FormatUtils.idString(cooperation));
    	jo.put("cooperationText", FormatUtils.displayString(cooperation));
    	jo.put("cooperationAccount", FormatUtils.idString(cooperationAccount));
    	jo.put("receiveNo", FormatUtils.stringValue(cooperationAccount.getReceiveNo()));
    	jo.put("costCenter", FormatUtils.idString(costCenter));
    	jo.put("costCenterText", FormatUtils.displayString(costCenter));
    	jo.put("amount", FormatUtils.doubleValue(amount));
    	jo.put("onePercent", FormatUtils.doubleValue(onePercent));
    	jo.put("twoPercent", FormatUtils.doubleValue(twoPercent));
    	jo.put("threePercent", FormatUtils.doubleValue(threePercent));
    	jo.put("fourPercent", FormatUtils.doubleValue(fourPercent));
    	jo.put("payCondition", FormatUtils.stringValue(payCondition));
    	jo.put("contents", FormatUtils.stringValue(contents));
    	jo.put("qualityGuaranteePeriod", FormatUtils.intValue(qualityGuaranteePeriod));
    	jo.put("createUer", FormatUtils.stringValue(getCreatedBy().getRealName()));
    	jo.put("signingDate", FormatUtils.dateValue(getCreationDate()));
    	jo.put("department", FormatUtils.displayString(department));
    	jo.put("departmentId", FormatUtils.idString(department));
    	jo.put("contractStatus", FormatUtils.displayString(contractStatus));
    	jo.put("adjustAmount", FormatUtils.doubleValue(adjustAmount));
    	jo.put("settleAccounts", FormatUtils.doubleValue(Calc.add(amount, adjustAmount)));
    	jo.put("payAmount", FormatUtils.doubleValue(payAmount));
    	jo.put("payAmountPercent", "已付款 " + FormatUtils.doubleValue(Calc.div(payAmount, Calc.add(amount, adjustAmount), 2) * 100) + "%");
    	jo.put("payCounts", FormatUtils.intValue(payCounts));
    	jo.put("continueRequest", FormatUtils.booleanValue(continueRequest));
    	return jo;
    }
	
}
