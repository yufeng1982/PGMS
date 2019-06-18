package com.photo.bas.core.web.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.UserType;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.vo.security.UserQueryInfo;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;

/**
 * @author FengYu
 */
@Controller
@RequestMapping(value="/*/user/list")
public class UserListController extends AbsQueryPagedListController<User, UserQueryInfo> {
	
	private static final String PAGE_USER_LIST = "security/user/users";
	
	@Autowired private UserService userService;

	@Override
	protected UserService getEntityService() {
		return userService;
	}
	
	@RequestMapping
	public String show(ModelMap modelMap) {
		return PAGE_USER_LIST;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") UserQueryInfo queryInfo) {
		Sort sort = new Sort(Direction.ASC, "loginName");
		queryInfo.setSort(sort);
		User user = ThreadLocalUtils.getCurrentUser();
		if (user.getUserType().equals(UserType.Normal)) queryInfo.setSf_EQ_createdBy(user); 
		Page<User> pagedUsers = getEntityService().getUsersBySearch(queryInfo);
		return toJSONView(pagedUsers);
	}

	@RequestMapping("json4RO")
	public ModelAndView json4RO(@ModelAttribute("pageQueryInfo") UserQueryInfo queryInfo) {
		Sort sort = new Sort(Direction.ASC, "loginName");
		queryInfo.setSort(sort);
		Page<User> pagedUsers = getEntityService().getUsersBySearch(queryInfo);
		return toJSONView(pagedUsers);
	}
	
	@RequestMapping("isNameExsit")
	@ResponseBody
	public Boolean isNameExsit(@RequestParam String name){
		User user = getEntityService().findByUserName(name);
		if (user != null) {
			 return Boolean.TRUE;
		} 
		return Boolean.FALSE;
	}
	
	@RequestMapping("isEmailExsit")
	@ResponseBody
	public Boolean isEmailExsit(@RequestParam String email){
		User user = getEntityService().findByEmail(email);
		if (user != null) {
			return Boolean.TRUE;
		} 
		return Boolean.FALSE;
	}
	
	@Override
	public UserQueryInfo newPagedQueryInfo() {
		return new UserQueryInfo();
	}
	
}
