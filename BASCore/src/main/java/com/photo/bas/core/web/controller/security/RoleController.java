package com.photo.bas.core.web.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.security.service.CorporationService;
import com.photo.bas.core.security.service.RoleService;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.web.controller.entity.AbsFormController;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value="/*/role/form")
public class RoleController extends AbsFormController<Role> {

	private static final String PAGE_ROLE_FORM = "security/user/role";
	
	@Autowired private RoleService roleService;
	@Autowired private CorporationService corporationService;

	@Override
	protected RoleService getEntityService() {
		return roleService;
	}
	
	@RequestMapping(value = "{id}/cancel", method = {RequestMethod.PUT, RequestMethod.POST})
	public ModelAndView cancel(ModelMap modelMap) {
		return closePage(modelMap);
	}
	
	@RequestMapping(value = "{id}/apply", method = {RequestMethod.PUT, RequestMethod.POST})
	public ModelAndView apply(ModelMap modelMap, @ModelAttribute("entity") Role role, BindingResult result) {
		saveRole(role, result);
		return redirectTo("role/form/" + (Strings.isEmpty(role.getId()) ? NEW_ENTITY_ID : role.getId())  + "/show", modelMap);
	}
	@RequestMapping(value = "{id}/ok", method = {RequestMethod.PUT, RequestMethod.POST})
	public ModelAndView ok(ModelMap modelMap, @ModelAttribute("entity") Role role, BindingResult result) {
		if(saveRole(role, result)) {
			return closePage(modelMap);
		}
		return redirectTo("role/form/" + (Strings.isEmpty(role.getId()) ? NEW_ENTITY_ID : role.getId())  + "/show", modelMap);
	}

	private boolean saveRole(Role role, BindingResult result) {
		boolean isSucceed = !result.hasErrors();
		if (isSucceed) {
			getEntityService().save(role);
		}
		return isSucceed;
	}
	
	@RequestMapping(value = "{id}/show")
	public String showRole(ModelMap modelMap, @ModelAttribute("entity") Role role) {
		//modelMap.addAttribute("corporations", corporationService.getAll());
		return PAGE_ROLE_FORM;
	}
	
	@ModelAttribute("entity")
	public Role populateUser(@PathVariable(value="id") String id) {
		Role role = null;
		if(isNew(id)) {
			role = new Role();
		} else {
			role = getEntityService().get(id);
		}
		
		return role;
	}
}
