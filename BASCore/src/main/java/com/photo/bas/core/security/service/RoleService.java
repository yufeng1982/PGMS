/**
 * 
 */
package com.photo.bas.core.security.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.dao.security.RoleRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.service.workflow.ActivitiManager;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.vo.security.RoleQueryInfo;

/**
 * @author FengYu
 *
 */
@SuppressWarnings("rawtypes")
@Component
@Transactional(readOnly = true)
public class RoleService extends AbsCodeNameEntityService<Role, RoleQueryInfo> {
	public static final String ADMIN = "admin";
	public static final String COMMONROLE = "CommonRole";
	
	@Autowired private RoleRepository roleRepository;
	@Autowired private ActivitiManager activitiManager;
	@Autowired private CorporationService corporationService;

	@Override
	protected RoleRepository getRepository() {
		return roleRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}
	
	@Transactional(readOnly = false)
	public Role save(Role role){
		if(Strings.isEmpty(role.getCode())){
			role.setCode(role.getName());
		}
		if(role.isAdminRole()){
			//Save function nodes to each corporation, when user change role setup for each corporation's admin role;
			Corporation corporation = role.getCorporation();
			corporation.setFunctionNodes(role.getFunctionNodeIds());
			corporationService.save(corporation);
		}
		return super.save(role);
	}
	
	public Iterable<Role> findRolesByFunctionNodeId(String fnId) {
		return getRepository().findAllByFunctionNodeIdsLike(fnId);
	}
	
	@Transactional(readOnly = false)
	public List<Role> initAdminRoles() {
		List<Role> roles = new ArrayList<Role>();
		Iterable<Corporation> corporations = corporationService.getAll();
		for (Corporation c : corporations) {
			String corCode = c.getCode();
			String roleCode = ADMIN + "_" + corCode;
			Role role = getUniqueByName(roleCode, c);
			if(role == null){
				role = new Role(roleCode, roleCode, c);
				role.setIsAdminRole(true);
				role.setDescription("The default admin's role for " + corCode);
				save(role);
			}
			roles.add(role);
		}
		return roles;		
	}
	
	public Role getAdminRole(Corporation corporation) {
		PropertyFilter adminRolePF = new PropertyFilter("isAdminRole", true, MatchType.EQ);
		PropertyFilter corporationPF = new PropertyFilter("corporation", corporation, MatchType.EQ);
		return findOne(adminRolePF, corporationPF);
	}
	
//	public Iterable<Role> getDefaultRoles4User(String corporation) {
////		List<String> ids = new ArrayList<String>();
////		ids.add("CommonRole");
////		return getEntityDAO().findByProperty("id", ids, MatchType.IN);
//		// TODO use admin role for testing
//		return getAdminRoles(corporation);
//	}
	
	public Page<Role> search(RoleQueryInfo page) {
		if(ThreadLocalUtils.getCurrentCorporation() != null){
			page.setSf_EQ_isAdminRole(false);
		} else{
			page.setSf_EQ_isAdminRole(true);
		}
		return super.search(page);
	}
	
	public Iterable<Role> findRoles(String query, String corporation) {
		PropertyFilter namePF = new PropertyFilter("name", query, MatchType.LIKE);
		PropertyFilter adminRolePF = new PropertyFilter("isAdminRole", Boolean.FALSE, MatchType.EQ);
		PropertyFilter corporationPF = new PropertyFilter("corporation", corporation, MatchType.EQ);
		return search(namePF, adminRolePF, corporationPF);
	}
	
//	@Override
//	public Role save(Role role){
//		super.save(role);
//		activitiManager.updateActivitiCandidates(role);
//		return role;
//	}
//	
	public Role getUniqueByName(String name, Corporation corporation) {
		PropertyFilter namePF = new PropertyFilter("name", name, MatchType.EQ);
		PropertyFilter corporationPF = new PropertyFilter("corporation", corporation, MatchType.EQ);
		Role r = findOne(namePF, corporationPF);
		return r;
	}
	
	public void initSystemRoles() {
		Iterable<Corporation> corporations = corporationService.getAll();
		for (Corporation c : corporations) {
			List<String> rolesCode = getAllRoleFromXML();
			for (String roleCode : rolesCode) {
				Role exsitRole = getUniqueByName(roleCode, c);
				if(exsitRole == null){
					Role role = new Role(roleCode, roleCode, c);
					role.setDescription(roleCode + " for " + c.getCode());
					save(role);
				}
			}
		}
	}
	
	private List<String> getAllRoleFromXML(){
		List<String> rolesCode = new ArrayList<String>();
		SAXReader reader = new SAXReader();
		try {
			Document functionNodeDocument = reader.read(getClass().getResourceAsStream("/META-INF/resources/roles-steel.xml"));
			Element root = functionNodeDocument.getRootElement();
			Iterator elementIterator = root.elementIterator();
			while (elementIterator.hasNext()) {
				Element element = (Element) elementIterator.next();
				String code = element.attributeValue("code");
				if(!rolesCode.contains(code)) rolesCode.add(code);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return rolesCode;
	}

}
