package com.photo.bas.core.web.controller.common;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.Organization;
import com.photo.bas.core.service.common.OrganizationService;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.web.controller.entity.AbsFormController;

@Controller
@RequestMapping(value="/*/organization/form")
public class OrganizationController extends AbsFormController<Organization> {
    
	private String PAGE_FORM_PATH = "security/organization/organization";
	@Autowired private OrganizationService organizationService;
	
	@Override
	protected OrganizationService getEntityService() {
		return organizationService;
	}
	
	@RequestMapping(value = "{id}/apply", method = {RequestMethod.PUT, RequestMethod.POST})
	public ModelAndView apply(@ModelAttribute("entity") Organization entity,  @RequestParam(value="phoneJsons") JSONArray phoneJsons, ModelMap modelMap,BindingResult result) {
		saveOrganization(entity, phoneJsons);
		return redirectTo("organization/form/" + (Strings.isEmpty(entity.getId()) ? NEW_ENTITY_ID : entity.getId())  + "/show", modelMap);
	}
	
	@RequestMapping(value = "{id}/ok", method = {RequestMethod.PUT, RequestMethod.POST})
	public ModelAndView ok(@ModelAttribute("entity") Organization entity,  @RequestParam(value="phoneJsons") JSONArray phoneJsons, ModelMap modelMap, BindingResult result) {
		if(saveOrganization(entity,phoneJsons)) {
			return closePage(modelMap);
		}
		return redirectTo("organization/form/" + (Strings.isEmpty(entity.getId()) ? NEW_ENTITY_ID : entity.getId())  + "/show", modelMap);
	}
	
	private boolean saveOrganization(Organization entity, JSONArray phoneJsons) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			organizationService.save(entity,phoneJsons);
		}
		return isSucceed;
	}
	
	@RequestMapping(value = "{id}/show")
	public String showOrganization(@ModelAttribute("entity") Organization organization, ModelMap modelMap) {
		return PAGE_FORM_PATH;
	}
	
	@ModelAttribute("entity")
	public Organization populateOrganization(@PathVariable(value="id") String id) {
		Organization organization = null;
		if(isNew(id)) {
			organization = new Organization();
		} else {
			organization = organizationService.get(id);
		}
		
		return organization;
	}
}
