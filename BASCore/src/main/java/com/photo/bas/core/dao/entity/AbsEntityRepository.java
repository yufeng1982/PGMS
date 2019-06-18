/**
 * 
 */
package com.photo.bas.core.dao.entity;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import com.photo.bas.core.model.entity.AbsEntity;
import com.photo.bas.core.model.security.Corporation;

/**
 * @author FengYu
 *
 */
@NoRepositoryBean
public interface AbsEntityRepository<T extends AbsEntity> extends AbsRepository<T> {
	
	public List<T> findAllByActiveTrue();
	
	public List<T> findAllByActiveTrue(Sort sort);
	
	public List<T> findAllByCorporationAndActiveTrue(Corporation corporation);
	
	@Modifying
	@Query(value = "SELECT nextval( ?1 )", nativeQuery = true)
	public List<BigInteger> getSerialNumber(String sequenceName);
	
}
