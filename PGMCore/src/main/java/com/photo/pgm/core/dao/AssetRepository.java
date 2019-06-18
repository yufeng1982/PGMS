/**
 * 
 */
package com.photo.pgm.core.dao;

import org.springframework.data.jpa.repository.Query;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.pgm.core.model.Asset;

/**
 * @author FengYu
 *
 */
public interface AssetRepository extends AbsCodeNameEntityRepository<Asset> {
	
	@Query(value = "select max(seq) from project.asset where corporation = ?1 and active='T'", nativeQuery = true)
	public Integer findMaxSeq(String corporation);
}
