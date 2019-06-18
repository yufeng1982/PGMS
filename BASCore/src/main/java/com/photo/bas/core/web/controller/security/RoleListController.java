package com.photo.bas.core.web.controller.security;

import java.util.ArrayList;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.UserType;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.security.service.CorporationService;
import com.photo.bas.core.security.service.RoleService;
import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.vo.security.RoleQueryInfo;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;

/**
 * @author FengYu
 */
@Controller
@RequestMapping(value="/*/role/list")
public class RoleListController extends AbsQueryPagedListController<Role, RoleQueryInfo> {
	
	private static final String PAGE_ROLE_LIST = "security/user/roles";
	@Autowired private RoleService roleService;
	
	@Autowired private UserService userService;
	@Autowired private CorporationService corporationService;

	@Override
	protected RoleService getEntityService() {
		return roleService;
	}
	
	@RequestMapping
	@RequiresPermissions("role:list")
	public String show(ModelMap modelMap) {
		return PAGE_ROLE_LIST;
	}

	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") RoleQueryInfo queryInfo, @RequestParam(required = false) String userId) {
		if(!Strings.isEmpty(userId)) {
			User user = userService.get(userId);
			if(user != null) {
				return toRoleJSONView(user.getRoles());
			} else {
				return toRoleJSONView(new ArrayList<Role>());
			}
		}
		User user = ThreadLocalUtils.getCurrentUser();
		if (user.getUserType().equals(UserType.Normal)) queryInfo.setSf_EQ_createdBy(user); 
		Iterable<Role> roles = getEntityService().search(queryInfo);
		return toRoleJSONView(roles);
	}
	
	private ModelAndView toRoleJSONView(Iterable<Role> list) {
		JSONObject jso = new JSONObject();
		JSONArray ja = new JSONArray();
		for (Role role : list) {
			JSONObject obj = role.toJSONObject();
			Corporation corporation = role.getCorporation();
			obj.put("corporationName", corporation == null ? "" : corporation.getDisplayName());
			ja.put(obj);
			
		}
		jso.put("data", ja);
		return toJSONView(jso.toString());
	}

	@Override
	public RoleQueryInfo newPagedQueryInfo() {
		return new RoleQueryInfo();
	}
}
