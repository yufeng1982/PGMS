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
public class ApproveOpinions extends AbsEntity {

	private static final long serialVersionUID = -6632773853139096270L;

	@ManyToOne
	private Contract contract;
	
	private String processInstanceId;
	
	private String taskId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date comleteTime;
	
	private String taskName;
	
	@Column(columnDefinition = "text")
	private String opinions;
	
	@Enumerated(EnumType.STRING)
	private ApproveResult approveResult = ApproveResult.Passed;
	
	public Contract getContract() {
		return contract;
	}


	public void setContract(Contract contract) {
		this.contract = contract;
	}


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


	@Override
	public JSONObject toJSONObject() {
		JSONObject jo = super.toJSONObject();
		jo.put("entityId", FormatUtils.idString(contract));
		jo.put("comleteTime", FormatUtils.dateTimeValue(comleteTime));
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
