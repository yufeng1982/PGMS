/**
 * 
 */
package com.photo.bas.core.service.entity;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public abstract class AbsCodeNameEntityService<T extends AbsCodeNameEntity, P extends PageInfo<T>> extends AbsCodeEntityService<T, P> {

	protected abstract AbsCodeNameEntityRepository<T> getRepository();

	public abstract boolean isCommonAccess();

}
