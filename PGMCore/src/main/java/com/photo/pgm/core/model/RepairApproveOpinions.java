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

import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsEntity;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.pgm.core.enums.ApproveResult;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
public class RepairApproveOpinions extends AbsEntity {

	private static final long serialVersionUID = -6632773853139096270L;

	@ManyToOne
	private RepairOrder repairOrder;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private RepairSettleAccount repairSettleAccount;
	
	
	private String processInstanceId;
	
	private String taskId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date comleteTime;
	
	private String taskName;
	
	private String responsePeople;
	
	@Column(columnDefinition = "text")
	private String opinions;
	
	@Enumerated(EnumType.STRING)
	private ApproveResult approveResult = ApproveResult.Passed;


	public String getProcessInstanceId() {
		return processInstanceId;
	}


	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}


	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public Date getComleteTime() {
		return comleteTime;
	}


	public void setComleteTime(Date comleteTime) {
		this.comleteTime = comleteTime;
	}


	public String getTaskName() {
		return taskName;
	}


	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public String getOpinions() {
		return opinions;
	}


	public void setOpinions(String opinions) {
		this.opinions = opinions;
	}

	public ApproveResult getApproveResult() {
		return approveResult;
	}


	public void setApproveResult(ApproveResult approveResult) {
		this.approveResult = approveResult;
	}

	public RepairOrder getRepairOrder() {
		return repairOrder;
	}

	public void setRepairOrder(RepairOrder repairOrder) {
		this.repairOrder = repairOrder;
	}

	public RepairSettleAccount getRepairSettleAccount() {
		return repairSettleAccount;
	}


	public void setRepairSettleAccount(RepairSettleAccount repairSettleAccount) {
		this.repairSettleAccount = repairSettleAccount;
	}

	public String getResponsePeople() {
		return responsePeople;
	}


	public void setResponsePeople(String responsePeople) {
		this.responsePeople = responsePeople;
	}


	@Override
	public JSONObject toJSONObject() {
		JSONObject jo = super.toJSONObject();
		jo.put("repairOrderId", FormatUtils.idString(repairOrder));
		jo.put("repairSettleAccountId", FormatUtils.idString(repairSettleAccount));
		jo.put("comleteTime", FormatUtils.dateTimeValue(comleteTime));
		jo.put("responsePeople", FormatUtils.stringValue(responsePeople));
		jo.put("taskId", FormatUtils.stringValue(taskId));
		jo.put("taskName", FormatUtils.stringValue(taskName));
		jo.put("processInstanceId", FormatUtils.stringValue(processInstanceId));
		jo.put("opinions", FormatUtils.stringValue(opinions));
		jo.put("approveResult", FormatUtils.displayString(approveResult));
		return jo;
	}

	@Override
	public String getDisplayString() {
		return null;
	}

}
