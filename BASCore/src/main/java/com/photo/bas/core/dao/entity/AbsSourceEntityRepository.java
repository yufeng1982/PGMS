/**
 * 
 */
package com.photo.bas.core.dao.entity;

import org.springframework.data.repository.NoRepositoryBean;

import com.photo.bas.core.model.entity.AbsSourceEntity;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.model.security.Corporation;

/**
 * @author FengYu
 *
 */
@NoRepositoryBean
public interface AbsSourceEntityRepository<T extends AbsSourceEntity> extends AbsRepository<T> {

	public T findByOwnerTypeAndOwnerId(SourceEntityType ownerType, String ownerId);

	public T findByCodeAndOwnerTypeAndCorporation(String code, SourceEntityType ownerType, Corporation corporation);
}
