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

import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.utils.EmailUtils;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.SpringWebApplicationContextUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.vo.security.UserQueryInfo;
import com.photo.pgm.core.model.RepairApproveSetup;

/**
 * @author FengYu
 *
 */
public class RepairSettleAccountListener extends SpringWebApplicationContextUtils implements TaskListener {

	
	private static final long serialVersionUID = 3052487241665986567L;

	// 自动设置审批人
	@Override
	public void notify(DelegateTask delegateTask) {
		RepairApproveSetupService repairApproveSetupService = (RepairApproveSetupService) getApplicationContextBean("repairApproveSetupService");
		UserService userService = (UserService) getApplicationContextBean("userService");
		RepairApproveSetup pas = repairApproveSetupService.findByCode(delegateTask.getTaskDefinitionKey());
		
		EmailUtils emailUtils = (EmailUtils) getApplicationContextBean("emailUtils");
		
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
		emailUtils.sendMail2ApproveUsers(userService.search(pfList));
	}

}
