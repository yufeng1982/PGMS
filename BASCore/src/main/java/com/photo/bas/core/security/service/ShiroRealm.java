package com.photo.bas.core.security.service;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.model.security.Verification;
import com.photo.bas.core.utils.EncodeUtils;
import com.photo.bas.core.utils.ResourceUtils;


public class ShiroRealm extends AuthorizingRealm {

	private UserService userService;

	public ShiroRealm() {
	     super();
	     //设置认证token的实现类
	     setAuthenticationTokenClass(UsernamePasswordToken.class);
	     //设置加密算法
	     HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(UserService.HASH_ALGORITHM);
		 matcher.setHashIterations(UserService.HASH_INTERATIONS);
		 setCredentialsMatcher(matcher);
	}

	//授权
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
	     String loginName = (String) principalCollection.fromRealm(getName()).iterator().next();
	     User user = userService.findByLoginName(loginName);
	     if (null == user) {
	          return null;
	     } else {
	          SimpleAuthorizationInfo result = userService.getUerPermissions();
	          return result;
	     }
	}
	//认证
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//	    if (Verification.isVerification()) return null;
		if (Verification.isVerification(ResourceUtils.getAppSetting("verification.date"))) return null;
		 UsernamePasswordToken upToken = (UsernamePasswordToken) token;
	     User user = userService.findByLoginName(upToken.getUsername());
	     if (user != null) {
	    	  byte[] salt = EncodeUtils.decodeHex(user.getSalt());
	          return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(salt), getName());
	     }
	     return null;
	}
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
