package com.photo.bas.core.model.workflow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FengYu
 *
 */
public class WorkFlowActionsInfo {

	private List<WorkFlowTransition> transitions = new ArrayList<WorkFlowTransition>();
	public WorkFlowActionsInfo() {
		super();
	}

	public List<WorkFlowTransition> getTransitions() {
		return transitions;
	}

	public void addTransitions(String taskId, List<String> outcomes) {
		for (String transitionName : outcomes) {
			addTransition(new WorkFlowTransition(taskId, transitionName));
		}
	}
	public void addTransition(WorkFlowTransition transition) {
		this.transitions.add(transition);
	}
	
	public boolean isActive() {
		return !transitions.isEmpty();
	}
}
