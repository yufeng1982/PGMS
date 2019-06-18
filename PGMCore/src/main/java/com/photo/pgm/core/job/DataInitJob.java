/**
 * 
 */
package com.photo.pgm.core.job;

import javax.annotation.PostConstruct;

import org.jasypt.hibernate4.encryptor.HibernatePBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author FengYu
 *
 */
@Component
@Lazy(false)
public class DataInitJob {
	
//	@Autowired private UserService userService;
//	@Autowired private RoleService roleService;
	
	// THIS IS A HACK, DON'T KNOW WHY THE HELL HAVE TO ANNOUNCE IT BEFORE THE ENTITY USE IT.....
	@Autowired 
	@Qualifier("commonHibernateStringEncryptor")
	private HibernatePBEStringEncryptor commonHibernateStringEncryptor;
	@Autowired 
	@Qualifier("corporationHibernateStringEncryptor")
	private HibernatePBEStringEncryptor corporationHibernateStringEncryptor;
	
	@PostConstruct
	public void init() throws Exception {
		System.out.println(commonHibernateStringEncryptor.getRegisteredName());
		System.out.println(corporationHibernateStringEncryptor.getRegisteredName());
	}
}
