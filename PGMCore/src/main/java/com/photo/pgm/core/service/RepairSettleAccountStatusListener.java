/**
 * 
 */
package com.photo.pgm.core.service;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.photo.bas.core.utils.DateTimeUtils;
import com.photo.bas.core.utils.SpringWebApplicationContextUtils;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.pgm.core.enums.RepairSettleAccountStatus;
import com.photo.pgm.core.model.RepairSettleAccount;

/**
 * @author FengYu
 *
 */
public class RepairSettleAccountStatusListener extends SpringWebApplicationContextUtils implements TaskListener {

	
	private static final long serialVersionUID = -6453057605942997756L;

	// 自动设置审批状态
	@Override
	public void notify(DelegateTask delegateTask) {
		RepairSettleAccountService repairSettleAccountService = (RepairSettleAccountService) getApplicationContextBean("repairSettleAccountService");
		ActivitiFlowService activitiFlowService = (ActivitiFlowService) getApplicationContextBean("activitiFlowService");
		String entityId = (String) activitiFlowService.getProcessVariables(delegateTask.getExecutionId()).get("entityId");
		RepairSettleAccount repairSettleAccount = repairSettleAccountService.get(entityId);
		if (delegateTask.getTaskDefinitionKey().equals("A000001")) {
			repairSettleAccount.setRepairSettleAccountStatus(RepairSettleAccountStatus.Approving);
		}  else if (delegateTask.getTaskDefinitionKey().equals("A000005")) {
			repairSettleAccount.setRepairSettleAccountStatus(RepairSettleAccountStatus.Closed);
		} 
		// 设置响应人和响应时间
		switch (delegateTask.getTaskDefinitionKey()) {
			case "A000001": 
				repairSettleAccount.setResponseTime1(DateTimeUtils.dateTimeNow());
				repairSettleAccount.setResponseUser1(ThreadLocalUtils.getCurrentUser());
				break;
			case "A000002": 
				repairSettleAccount.setResponseTime2(DateTimeUtils.dateTimeNow());
				repairSettleAccount.setResponseUser2(ThreadLocalUtils.getCurrentUser());
				break;
			case "A000003": 
				repairSettleAccount.setResponseTime3(DateTimeUtils.dateTimeNow());
				repairSettleAccount.setResponseUser3(ThreadLocalUtils.getCurrentUser());
				break;
			case "A000004": 
				repairSettleAccount.setResponseTime4(DateTimeUtils.dateTimeNow());
				repairSettleAccount.setResponseUser4(ThreadLocalUtils.getCurrentUser());
				break;
			case "A000005": 
				repairSettleAccount.setResponseTime5(DateTimeUtils.dateTimeNow());
				repairSettleAccount.setResponseUser5(ThreadLocalUtils.getCurrentUser());
				break;
			default:
				break;
		}
		repairSettleAccountService.save(repairSettleAccount);
	}

}
