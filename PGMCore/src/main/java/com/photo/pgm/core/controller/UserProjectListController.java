package com.photo.pgm.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.UserProject;
import com.photo.pgm.core.service.UserProjectService;
import com.photo.pgm.core.vo.UserProjectQueryInfo;

@Controller
@RequestMapping(value="/pgm/security/project")
public class UserProjectListController extends AbsQueryPagedListController<UserProject, UserProjectQueryInfo> {
    
	@Autowired UserProjectService userProjectService;
	@Autowired UserService userService;

	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") UserProjectQueryInfo queryInfo, @RequestParam String userId){
		User user = userService.get(userId);
		queryInfo.setSf_EQ_users(user);
		Page<UserProject> list = getEntityService().findByUser(queryInfo);
		return toJSONView(list);
	}
	
	@Override
	public UserProjectQueryInfo newPagedQueryInfo() {
		return new UserProjectQueryInfo();
	}

	@Override
	protected UserProjectService getEntityService() {
		return userProjectService;
	}
	
	
}
