/**
 * 
 */
package com.photo.bas.core.security.service;

import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.photo.bas.core.dao.security.UserRepository;
import com.photo.bas.core.model.common.Language;
import com.photo.bas.core.model.common.UserType;
import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.model.security.UserDepartment;
import com.photo.bas.core.service.common.DepartmentService;
import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.utils.DateTimeUtils;
import com.photo.bas.core.utils.Digests;
import com.photo.bas.core.utils.EmailUtils;
import com.photo.bas.core.utils.EncodeUtils;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.vo.security.UserQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class UserService extends AbsEntityService<User, UserQueryInfo> {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	
	@Autowired private UserRepository userRepository;
	@Autowired private EmailUtils emailUtils;
	@Autowired private FunctionNodeManager functionNodeManager;
	@Autowired private UserDepartmentService userDepartmentService;
	@Autowired private DepartmentService departmentService;
	
	@Override
	protected UserRepository getRepository() {
		return userRepository;
	}

	@Autowired
	@Qualifier("coreAppSetting") 
	private MessageSource coreAppSetting;

	public User findByLoginName(String loginName) {
		return getRepository().findByLoginNameAndActiveTrueAndEnabledTrue(loginName);
	}
	
	public User findByUserName(String loginName) {
		return getRepository().findByLoginNameAndActiveTrue(loginName);
	}
	
	public User findByEmail(String email) {
		return getRepository().findByEmail(email);
	}

	@Transactional(readOnly = false)
	public void registerUser(User user) {
		user.setRegisterDate(DateTimeUtils.dateTimeNow());
		saveUserAndEncrypt(user);
		try{
			emailUtils.sendMail(user, "mailTemplate.ftl", "Active");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(readOnly = false)
	public boolean sendforgotPasswordMail(String email) {
		User user = userRepository.findByEmail(email);
		
		if(user != null) {
			try{
				emailUtils.sendMail(user, "resetPasswordMailTemplate.ftl", "ResetPassword");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public Boolean updateByEntryptValidationCode(String entryptValidationCode) {
		User user = getRepository().findByEntryptValidationCode(entryptValidationCode);
		if(user != null && !user.isEnabled()) {
			user.setEnabled(Boolean.TRUE);
			userRepository.save(user);
			return true;
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public Boolean activeEmailByValidationCode(String entryptValidationCode) {
		User user = getRepository().findByEntryptValidationCode(entryptValidationCode);
		if(user != null && user.getTempEmail()!=null) {
			user.setEmail(user.getTempEmail());
			userRepository.save(user);
			return true;
		}
		return false;
	}
	@Transactional(readOnly = false)
	public Boolean resetPassword(String entryptValidationCode, String plainPassword) {
		User user = getRepository().findByEntryptValidationCode(entryptValidationCode);
		if(user != null) {
			user.setPlainPassword(plainPassword);
			encryptPassword(user);
			userRepository.save(user);
			return true;
		}
		return false;
	}
	
	@Transactional(readOnly = true)
	public User findByEntryptValidationCode(String entryptValidationCode) {
		return getRepository().findByEntryptValidationCode(entryptValidationCode);
	}
	
	public void encryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(EncodeUtils.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		byte[] entryptValidationCode = Digests.sha1(user.getEmail().getBytes(), salt, HASH_INTERATIONS);
		
		user.setPassword(EncodeUtils.encodeHex(hashPassword));
		user.setEntryptValidationCode(EncodeUtils.encodeHex(entryptValidationCode));
	}
	@Transactional(readOnly = false)
	public void saveUserAndEncrypt(User user) {
		if(!Strings.isEmpty(user.getPlainPassword())) {
			encryptPassword(user);
		}
		if (user.isNewEntity()) {
			User currentUser = ThreadLocalUtils.getCurrentUser();
			if (currentUser == null) {
				user.setUserType(UserType.Supper);
			} else if (currentUser.getUserType().equals(UserType.Supper)) {
				user.setUserType(UserType.Org); // super admin create org admin
			} else if (currentUser.getUserType().equals(UserType.Org)){
				user.setUserType(UserType.Normal); // create normal user
			} else {
				user.setUserType(UserType.Normal);
			}
		}
		userRepository.save(user);
	}
	public String entryptPassword(String password ,byte[] salt) {
		byte[] hashPassword = Digests.sha1(password.getBytes(), salt, HASH_INTERATIONS);
		return EncodeUtils.encodeHex(hashPassword);
	}
	public List<User> findAll(final String strQuery) {
		List<User> users = userRepository.findAll(new Specification<User>() {
			
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = Lists.newArrayList();
				Path<String> loginNameExp = root.get("loginName");
				Path<String> firstNameExp = root.get("firstName");
				Path<String> lastNameExp = root.get("lastName");
			    String q = "%" + strQuery + "%";
			    predicates.add(cb.like(loginNameExp, q));
			    predicates.add(cb.like(firstNameExp, q));
			    predicates.add(cb.like(lastNameExp, q));
				if (predicates.size() > 0) {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
				return cb.conjunction();
			}

		});
		return users;
	}

	public Page<User> getUsersBySearch(final UserQueryInfo queryInfo){
		if(queryInfo.getSf_EQ_corporation() == null) {
			queryInfo.setSf_EQ_corporation(ThreadLocalUtils.getCurrentCorporation());
		}
		return getRepository().findAll(
				new Specification<User>() {
			
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Join<User, Role> join = root.join("roles",JoinType.LEFT);
				List<Predicate> predicates = Lists.newArrayList();
				Path<String> loginNameExp = root.get("loginName");
				Path<String> idExp = root.get("id");
				Path<User> userExp = root.get("createdBy");
				Path<Boolean> enabledExp = root.get("enabled");
				Path<String> roleCorporationExp = join.get("corporation");
				Path<String> userCorporationExp = root.get("corporation");
				Path<String> roleExp = join.get("id");
				if(!Strings.isEmpty(queryInfo.getSf_LIKE_loginName())){
				    predicates.add(cb.like(loginNameExp, "%" + queryInfo.getSf_LIKE_loginName() + "%"));
				}
				if(!Strings.isEmpty(queryInfo.getSf_LIKE_query())){
				    predicates.add(cb.like(loginNameExp, "%" + queryInfo.getSf_LIKE_query() + "%"));
				}
				if (queryInfo.getSf_EQ_createdBy() != null) {
					predicates.add(cb.or(cb.equal(userExp, queryInfo.getSf_EQ_createdBy()), cb.equal(idExp, ThreadLocalUtils.getCurrentUser().getId())));
				}
				if(queryInfo.getSf_EQ_corporation() != null){
				    predicates.add(cb.equal(roleCorporationExp, queryInfo.getSf_EQ_corporation()));
				}
				if(!Strings.isEmpty(queryInfo.getSf_EQ_roleId())){
					predicates.add(cb.equal(roleExp, queryInfo.getSf_EQ_roleId()));
				}
				if(ThreadLocalUtils.getCurrentUser().isSuperAdmin()){
					 predicates.add(cb.isNull(userCorporationExp));
				}
			    predicates.add(cb.equal(enabledExp, queryInfo.getSf_EQ_enabled()));
				if (predicates.size() > 0) {
					query.distinct(true);
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return cb.conjunction();
			}

		}, queryInfo);

	}
	
	@Override
	public boolean isCommonAccess() {
		return false;
	}
	
	public Page<User> findUsers(final UserQueryInfo queryInfo) {
		return getRepository().findAll(byPage(queryInfo), queryInfo);
	}
	
	public List<User> findByLoginName111(String loginName) {
		return userRepository.findAll(
				bySearchFilter(new PropertyFilter("loginName", loginName, MatchType.EQ),
						PropertyFilter.activeFilter())
		);
	}
	
	public SimpleAuthorizationInfo getUerPermissions(){
		List<FunctionNode> nodeList=functionNodeManager.findValidFunctionNodesForCurrentUser();
		SimpleAuthorizationInfo result = new SimpleAuthorizationInfo();
		Iterable<Role> roles = ThreadLocalUtils.getCurrentUser().getRoles();
        for(Role role: roles){
      	  	result.addRole(role.getName());
      	  addResourcePermission(result, role);
        }
        for(FunctionNode node: nodeList){
        	String resourceName = node.getName();
        	if(!Strings.isEmpty(resourceName)){
                result.addStringPermission(resourceName);
        	}
        }
        return result;
	}
	
	@SuppressWarnings("unchecked")
	private void addResourcePermission(SimpleAuthorizationInfo result, Role role){
		JSONObject jo = role.getJSONResources();
		Iterator<String> keys = jo.keys();
		while(keys.hasNext()){
			String key = keys.next();
			String permission = key + ":" + jo.getString(key);
			result.addStringPermission(permission);
		}
	}
	
	public List<User> findUsersByCorporation(String corporation) {
		return userRepository.findUsersByCorporation(corporation);
	}
	
	@Transactional(readOnly = false)
    public User save(User user, JSONArray departmentListJSONArray, List<String> deleteDepartments, JSONArray projectListJSON, List<String> deleteProjects) {
    	this.saveUserAndEncrypt(user);
		for(String id : deleteDepartments){
			userDepartmentService.delete(id);
		}
		for(int i = 0, size = departmentListJSONArray.length(); i < size; i ++) {
			JSONObject jo = departmentListJSONArray.getJSONObject(i);
			String id = jo.getString("id");
			UserDepartment ud = null;
			if (Strings.isEmpty(id)) {
				ud = new UserDepartment();
			} else {
				ud = userDepartmentService.get(id);
			}
			ud.setUsers(user);
			ud.setDepartment(departmentService.get(jo.getString("department")));
			ud.setPrincipal(jo.getBoolean("principal"));
			ud.setBelongTo(jo.getBoolean("belongTo"));
			userDepartmentService.save(ud);
		}
    	return user;
    }
	
	@Transactional(readOnly = false)
	public void initStaticUser(){
		User user = findByLoginName(User.SUPER_ADMIN_NAME);
		if(user == null) {
			user = new User();
			user.setLoginName(User.SUPER_ADMIN_NAME);
			user.setPlainPassword("administrator1!2@3#");
			user.setCorporation(null);
			user.setLanguage(Language.Chinese);
			user.setEmail("yufeng521000@163.com");
			user.setEnabled(Boolean.TRUE);
			user.setUserType(UserType.Supper);
			saveUserAndEncrypt(user);
		}
	}
}
