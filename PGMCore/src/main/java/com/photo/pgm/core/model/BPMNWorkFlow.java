/**
 * 
 */
package com.photo.pgm.core.model;

import javax.persistence.MappedSuperclass;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;

/**
 * @author FengYu
 *
 */
@MappedSuperclass
public abstract class BPMNWorkFlow extends AbsCodeNameEntity {

	private static final long serialVersionUID = -476414446597358956L;
	
	public String processInstanceId;
	
	public String taskCode;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	
}
