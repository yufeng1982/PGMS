/**
 * 
 */
package com.photo.pgm.core.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;

import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.utils.EmailUtils;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.utils.SpringWebApplicationContextUtils;

/**
 * @author FengYu
 *
 */
public class ContractApproveListener extends SpringWebApplicationContextUtils implements TaskListener {

	
	private static final long serialVersionUID = 3052487241665986567L;

	// 自动设置审批人
	@Override
	public void notify(DelegateTask delegateTask) {
		
		UserService userService = (UserService) getApplicationContextBean("userService");
		EmailUtils emailUtils = (EmailUtils) getApplicationContextBean("emailUtils");
		
		// 邮件通知审批人
		List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
		Set<IdentityLink> ilSet = delegateTask.getCandidates();
		Iterator<IdentityLink> it = ilSet.iterator();
		List<String> userList = new ArrayList<String>();
		while(it.hasNext()) {
			IdentityLink il = it.next();
			userList.add(il.getUserId());
		}
		pfList.add(new PropertyFilter("loginName", userList, MatchType.IN));
		emailUtils.sendMail2ApproveUsers(userService.search(pfList));
	}

}
