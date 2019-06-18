package com.photo.pgm.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.model.security.UserDepartment;
import com.photo.bas.core.security.service.UserDepartmentService;
import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.vo.security.UserDepartmentQueryInfo;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;

@Controller
@RequestMapping(value="/pgm/security/department")
public class UserDepartmentListController extends AbsQueryPagedListController<UserDepartment, UserDepartmentQueryInfo> {
    
	@Autowired UserDepartmentService userDepartmentService;
	@Autowired UserService userService;

	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") UserDepartmentQueryInfo queryInfo, @RequestParam String userId){
		User user = userService.get(userId);
		queryInfo.setSf_EQ_users(user);
		Page<UserDepartment> list = getEntityService().findByUser(queryInfo);
		return toJSONView(list);
	}
	
	@Override
	public UserDepartmentQueryInfo newPagedQueryInfo() {
		return new UserDepartmentQueryInfo();
	}

	@Override
	protected UserDepartmentService getEntityService() {
		return userDepartmentService;
	}
	
	
}
