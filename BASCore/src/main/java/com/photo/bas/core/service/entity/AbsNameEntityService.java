/**
 * 
 */
package com.photo.bas.core.service.entity;

import com.photo.bas.core.dao.entity.AbsNameEntityRepository;
import com.photo.bas.core.model.entity.AbsNameEntity;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public abstract class AbsNameEntityService<T extends AbsNameEntity, P extends PageInfo<T>> extends AbsEntityService<T, P> {

	protected abstract AbsNameEntityRepository<T> getRepository();
	
	public abstract boolean isCommonAccess();

}
