/**
 * 
 */
package com.photo.bas.core.dao.security;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.photo.bas.core.dao.entity.AbsEntityRepository;
import com.photo.bas.core.model.common.Language;
import com.photo.bas.core.model.security.User;

/**
 * @author FengYu
 *
 */
public interface UserRepository extends AbsEntityRepository<User> {
	
	
//	@Query("select u from User u where u.firstName = :firstName or u.lastName = :lastName")
//	public User findByLastNameOrFirstName(@Param("lastName") String lastname, @Param("firstName") String firstName);
	
	@Query("select u from User u where u.email = ?1")
	public User findByEmail(String email);
	public User findByLoginNameAndActiveTrue(String loginName);
	public User findByLoginNameAndActiveTrueAndEnabledTrue(String loginName);
	public User  findByLoginNameOrEmail(String loginName, String email);
	@Modifying
	@Query("update User u set u.language = ?1 where u.corporation = ?2")
	public int setUserLanguage(Language language, String corporation);
	
	public User findByEntryptValidationCode(String entryptPassword);
	
	@Query("select u from User u left join u.roles role left join role.corporation cor where cor.code = ?1")
	public List<User> findUsersByCorporation(String corporation);
	
}
