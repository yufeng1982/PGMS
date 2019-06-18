/**
 * 
 */
package com.photo.pgm.core.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.photo.bas.core.utils.SpringWebApplicationContextUtils;
import com.photo.pgm.core.enums.RepairStatus;
import com.photo.pgm.core.model.RepairOrder;

/**
 * @author FengYu
 *
 */
public class CompleteStatusListener extends SpringWebApplicationContextUtils implements ExecutionListener {

	
	private static final long serialVersionUID = -6453057605942997756L;

	// 自动设置审批状态
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		RepairOrderService repairOrderService = (RepairOrderService) getApplicationContextBean("repairOrderService");
		ActivitiFlowService activitiFlowService = (ActivitiFlowService) getApplicationContextBean("activitiFlowService");
		String entityId = (String) activitiFlowService.getProcessVariables(execution.getId()).get("entityId");
		RepairOrder repairOrder = repairOrderService.get(entityId);
		repairOrder.setRepairStatus(RepairStatus.Closed);
		repairOrderService.save(repairOrder);
	}

}
