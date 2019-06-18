/**
 * 
 */
package com.photo.pgm.core.service;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.photo.bas.core.utils.SpringWebApplicationContextUtils;
import com.photo.pgm.core.enums.RepairStatus;
import com.photo.pgm.core.model.RepairOrder;

/**
 * @author FengYu
 *
 */
public class RepairOrderStatusListener extends SpringWebApplicationContextUtils implements TaskListener {

	
	private static final long serialVersionUID = -6453057605942997756L;

	// 自动设置审批状态
	@Override
	public void notify(DelegateTask delegateTask) {
		RepairOrderService repairOrderService = (RepairOrderService) getApplicationContextBean("repairOrderService");
		ActivitiFlowService activitiFlowService = (ActivitiFlowService) getApplicationContextBean("activitiFlowService");
		String entityId = (String) activitiFlowService.getProcessVariables(delegateTask.getExecutionId()).get("entityId");
		RepairOrder repairOrder = repairOrderService.get(entityId);
		if (delegateTask.getTaskDefinitionKey().equals("S000001") || delegateTask.getTaskDefinitionKey().equals("G000001")) {
			repairOrder.setRepairStatus(RepairStatus.Approving);
		} else if (delegateTask.getTaskDefinitionKey().equals("S000002") || delegateTask.getTaskDefinitionKey().equals("G000008")) {
			repairOrder.setRepairStatus(RepairStatus.Approved);
		} else if (delegateTask.getTaskDefinitionKey().equals("S000003") || delegateTask.getTaskDefinitionKey().equals("G000010")) {
			repairOrder.setRepairStatus(RepairStatus.Repaired);
		} else if (delegateTask.getTaskDefinitionKey().equals("G000009")) {
			repairOrder.setRepairStatus(RepairStatus.Repairing);
		} else if (delegateTask.getTaskDefinitionKey().equals("G000012")) {
			if(repairOrder.isAdjust()){
				repairOrder.setRepairStatus(RepairStatus.Adjusting);
			} else {
				repairOrder.setRepairStatus(RepairStatus.Closed);
			}
		} else if (delegateTask.getTaskDefinitionKey().equals("S000004") || delegateTask.getTaskDefinitionKey().equals("G000015")) {
			repairOrder.setRepairStatus(RepairStatus.Closed);
		} else if (delegateTask.getTaskDefinitionKey().equals("G000011")) {
			repairOrder.setRepairStatus(RepairStatus.Confirm);
		}
		// 是否审批设置
		switch (delegateTask.getTaskDefinitionKey()) {
			case "S000001":
				repairOrder.setTk1approve(true);
				break;
			case "S000002":
				repairOrder.setTk2approve(true);
				break;
			case "S000003":
				repairOrder.setTk3approve(true);
				break;
			case "S000004":
				repairOrder.setTk4approve(true);
				break;	
			case "G000001":
				repairOrder.setTk1approve(true);
				break;
			case "G000002":
				repairOrder.setTk2approve(true);
				break;
			case "G000003":
				repairOrder.setTk3approve(true);
				break;
			case "G000004":
				repairOrder.setTk4approve(true);
				break;
			case "G0000041":
				repairOrder.setTk41approve(true);
				break;
			case "G000005":
				repairOrder.setTk5approve(true);
				break;
			case "G000006":
				repairOrder.setTk6approve(true);
				break;
			case "G000007":
				repairOrder.setTk7approve(true);
				break;
			case "G000008":
				repairOrder.setTk8approve(true);
				break;
			case "G000009":
				repairOrder.setTk9approve(true);
				break;
			case "G000010":
				repairOrder.setTk10approve(true);
				break;
			case "G000011":
				repairOrder.setTk11approve(true);
				break;
			case "G000012":
				repairOrder.setTk12approve(true);
				break;
			case "G000013":
				repairOrder.setTk13approve(true);
				break;
			case "G000014":
				repairOrder.setTk14approve(true);
				break;
			case "G000015":
				repairOrder.setTk15approve(true);
				break;
			case "G000016":
				repairOrder.setTk16approve(true);
				break;
			default:
				break;
		}
		repairOrderService.save(repairOrder);
	}

}
