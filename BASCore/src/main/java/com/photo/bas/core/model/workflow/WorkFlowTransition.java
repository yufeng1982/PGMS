package com.photo.bas.core.model.workflow;

import com.photo.bas.core.service.workflow.ActivitiManagerImpl;
import com.photo.bas.core.utils.ResourceUtils;

/**
 * @author FengYu
 *
 */
public class WorkFlowTransition {
	private String taskId;
	private String name;
	private static String prefix = "WorkFlow.";
	public WorkFlowTransition(String taskId, String name) {
		super();
		this.taskId = taskId;
		this.name = name;
	}

	public String getText() {
		String name = getName();
		if(name.startsWith(ActivitiManagerImpl.WITH_ROLE) && name.contains("-")){
			name = name.substring(0, name.indexOf("-"));
		}
		return ResourceUtils.getText(prefix + name);
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
