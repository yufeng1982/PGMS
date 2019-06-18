/**
 * 
 */
package com.photo.bas.core.service.entity;

import com.photo.bas.core.dao.entity.AbsCodeEntityRepository;
import com.photo.bas.core.model.entity.AbsCodeEntity;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public abstract class AbsCodeEntityService<T extends AbsCodeEntity, P extends PageInfo<T>> extends AbsEntityService<T, P> {

	protected abstract AbsCodeEntityRepository<T> getRepository();
	
	public abstract boolean isCommonAccess();

	
	public T findByCode(String code) {
		return getRepository().findByCodeAndActiveTrue(code);
	}
	
	public T findByCodeAndCorporation(String code, Corporation corporation) {
		return getRepository().findByCodeAndCorporationAndActiveTrue(code, corporation);
	}
}
