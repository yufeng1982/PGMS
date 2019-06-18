package com.photo.bas.core.web.controller.common;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.Organization;
import com.photo.bas.core.service.common.OrganizationService;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.vo.common.OrganizationQueryInfo;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;

@Controller
@RequestMapping(value="/*/organization/list")
public class OrganizationListController extends AbsQueryPagedListController<Organization,OrganizationQueryInfo>{
	
	private static final String PAGE_LIST = "security/organization/organizations";
	
	@Autowired private OrganizationService organizationService;
	
	@Override
	protected OrganizationService getEntityService() {
		return organizationService;
	}
	
	@RequestMapping
	@RequiresPermissions("organization:list")
	public String show(ModelMap modelMap) {
		String user = ThreadLocalUtils.getCurrentUser().getLoginName();
		modelMap.addAttribute("isSuperAdmin", user.equals("admin"));
		return PAGE_LIST;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") OrganizationQueryInfo queryInfo) {
		Page<Organization> pagedOrganizations = getEntityService().findOrganizations(queryInfo);
		return toJSONView(pagedOrganizations);
	}

	@Override
	public OrganizationQueryInfo newPagedQueryInfo() {
		return new OrganizationQueryInfo();
	}
}
