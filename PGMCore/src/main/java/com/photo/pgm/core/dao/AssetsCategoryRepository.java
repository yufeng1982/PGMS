/**
 * 
 */
package com.photo.pgm.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.pgm.core.model.AssetsCategory;

/**
 * @author FengYu
 *
 */
public interface AssetsCategoryRepository extends AbsCodeNameEntityRepository<AssetsCategory> {

	public List<AssetsCategory> findByParentAndCorporationAndActiveTrue(AssetsCategory parent, Corporation corporation);
	
	@Query(value = "select count(*) from project.assets_category ac where ac.parent = ?1 and ac.corporation = ?2 and ac.active = 'T'", nativeQuery = true)
	public Integer findSubNodes(String parent, String corporation);
}
