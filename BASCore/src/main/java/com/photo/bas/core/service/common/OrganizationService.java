package com.photo.bas.core.service.common;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.dao.common.OrganizationRepository;
import com.photo.bas.core.model.common.Language;
import com.photo.bas.core.model.common.Organization;
import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.security.service.AbsCorporationService;
import com.photo.bas.core.security.service.RoleService;
import com.photo.bas.core.service.utils.SequenceService;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.vo.common.OrganizationQueryInfo;

@Component
@Transactional(readOnly = true)
public class OrganizationService extends AbsCorporationService<Organization, OrganizationQueryInfo> {
	
	@Autowired OrganizationRepository organizationRepository;
	@Autowired RoleService roleService;
	@Autowired SequenceService sequenceService;
	
	@Override
	protected OrganizationRepository getRepository() {
		return organizationRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}
	
	public Page<Organization> findOrganizations(final OrganizationQueryInfo queryInfo) {
		PropertyFilter codePF = new PropertyFilter("code", queryInfo.getSf_LIKE_code(), MatchType.LIKE);
		PropertyFilter shortNamePF = new PropertyFilter("shortName", queryInfo.getSf_LIKE_shortName(), MatchType.LIKE);
		PropertyFilter notePF = new PropertyFilter("note", queryInfo.getSf_LIKE_note(), MatchType.LIKE);
		if (!ThreadLocalUtils.getCurrentUser().isSuperAdmin()) {
			PropertyFilter idPF = new PropertyFilter("id", queryInfo.getSf_EQ_corporation().getId(), MatchType.EQ);
			return getRepository().findAll(bySearchFilter(idPF, codePF, shortNamePF, notePF), queryInfo);
		}
		return getRepository().findAll(bySearchFilter(codePF, shortNamePF, notePF), queryInfo);
	}
	
	@Transactional(readOnly = false)
	public Organization save(Organization entity, JSONArray phoneJsons) {
		if(entity.isNewEntity()){
			getRepository().save(entity);
			processAfterNewOrganization(entity);
		} else {
			getRepository().save(entity);
		}
		return entity;
	}
	
	private void processAfterNewOrganization(Organization entity){
		String corCode = entity.getCode();
		String orgRole = RoleService.ADMIN + "_" + corCode;
		String commonRole = RoleService.COMMONROLE;
		//Init organization admin role right after new org;
		saveOrgSystemRole(entity, orgRole, true);
		//Init organization common role right after new org for normal user;
		saveOrgSystemRole(entity, commonRole, false);
		
		sequenceService.initSequenceByOrgnization(entity.getId());
	}
	
	private void saveOrgSystemRole(Organization entity, String roleCode, boolean isAdminRole){
		String corCode = entity.getCode();
		Role role = roleService.getUniqueByName(roleCode, entity);
		if(role == null){
			role = new Role(roleCode, roleCode, entity);
			role.setIsAdminRole(isAdminRole);
			if(isAdminRole){ 
				role.setDescription("The default admin's role for " + corCode);
			}else{
				if (ThreadLocalUtils.getCurrentUser().getLanguage().equals(Language.Chinese)) {
					role.setDescription(ResourceUtils.getText("Com.CommonRoleForOrg"));
				} else {
					role.setDescription(ResourceUtils.getText("Com.CommonRoleForOrg") + corCode);
				}
			}
			roleService.save(role);
		}
	}
}
