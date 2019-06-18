/**
 * 
 */
package com.photo.pgm.core.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.data.domain.Page;

import akka.actor.ActorRef;

import com.photo.bas.core.model.common.Akka;
import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.utils.EmailEntity;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.utils.SpringWebApplicationContextUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.vo.security.UserQueryInfo;
import com.photo.pgm.core.model.RepairApproveSetup;
import com.photo.pgm.core.model.RepairOrder;

/**
 * @author FengYu
 *
 */
public class RepairOrderListener extends SpringWebApplicationContextUtils implements TaskListener {

	
	private static final long serialVersionUID = 3052487241665986567L;

	// 自动设置审批人
	@Override
	public void notify(DelegateTask delegateTask) {
		RepairOrderService repairOrderService = (RepairOrderService) getApplicationContextBean("repairOrderService");
		ActivitiFlowService activitiFlowService = (ActivitiFlowService) getApplicationContextBean("activitiFlowService");
		UserService userService = (UserService) getApplicationContextBean("userService");
		String entityId = (String) activitiFlowService.getProcessVariables(delegateTask.getExecutionId()).get("entityId");
		RepairOrder repairOrder = repairOrderService.get(entityId);
		RepairApproveSetupService repairApproveSetupService = (RepairApproveSetupService) getApplicationContextBean("repairApproveSetupService");
		RepairApproveSetup pas = repairApproveSetupService.findByCode(delegateTask.getTaskDefinitionKey());
		
//		EmailUtils emailUtils = (EmailUtils) getApplicationContextBean("emailUtils");
		
//		if (delegateTask.getTaskDefinitionKey().equals("G000002")) {
//			repairOrder.setOpinions1(null);
//			repairOrder.setRepairSettleAccountFilePath(null);
//			repairOrder.setCurrentBudget(0.0);
//			repairOrder.setRepairTime(0.0);
//			repairOrder.setRepairSolutions(null);
//			repairOrder.setRepairSolutionsFilePath(null);
//			repairOrderService.save(repairOrder);
//		} else if (delegateTask.getTaskDefinitionKey().equals("G000012")) {
//			repairOrder.setImagePath9(null);
//			repairOrder.setOpinions12(null);
//			repairOrderService.save(repairOrder);
//		}
		
		// 设置油站经理确认审批人-- 自动设置为维修单申请人
		if (delegateTask.getTaskDefinitionKey().equals("G000011") || delegateTask.getTaskDefinitionKey().equals("S000004")) {
			delegateTask.addCandidateUser(repairOrder.getCreatedBy().getLoginName());
			return;
		} else if (delegateTask.getTaskDefinitionKey().equals("G000010")) { // 工程师/技师响应 需指定固定用户进行审批
			if (repairOrder.getZdUser() != null) {
				delegateTask.addCandidateUser(repairOrder.getZdUser().getLoginName());
			}
		} else {
			// 通用设置--从审批设置中读取审批人
			String candidateUser = pas.getUsersText();
			Role role = pas.getRole();
			if (role != null) {
				UserQueryInfo queryInfo = new UserQueryInfo();
				queryInfo.setSf_EQ_roleId(role.getId());
				Page<User> users = userService.getUsersBySearch(queryInfo);
				for (User user : users.getContent()) {
					String loginName = user.getLoginName();
					if (!candidateUser.contains(loginName)) {
						if (Strings.isEmpty(candidateUser)) {
							candidateUser = loginName;
						} else {
							candidateUser += "," + loginName;
						}
					}
				}
			}
			
			List<String> candidateUserList = Arrays.asList(candidateUser.split(","));
			delegateTask.addCandidateUsers(candidateUserList);
			
			// 邮件通知审批人
			List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
			pfList.add(new PropertyFilter("loginName", candidateUserList, MatchType.IN));
			EmailEntity emailEntity = new EmailEntity();
			emailEntity.setApproveUsers(userService.search(pfList));
			ActorRef emailRef = Akka.getEmailActor();
			emailRef.tell(emailEntity, emailRef);
		}
	}

}
