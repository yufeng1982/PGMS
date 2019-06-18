/**
 * 
 */
package com.photo.bas.core.service.entity;

import java.util.ArrayList;
import java.util.List;

import com.photo.bas.core.dao.entity.AbsContactRepository;
import com.photo.bas.core.model.entity.AbsContactEntity;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public abstract class AbsContactService<T extends AbsContactEntity, P extends PageInfo<T>> extends AbsCodeNameEntityService<T, P> {
	
	public List<T> findBySourceOwnerId(String sourceOwnerId){
		List<T> list = getRepository().findBySourceOwnerIdAndActiveTrue(sourceOwnerId);
		return list == null ? new ArrayList<T>() : list;
	}
	
	@Override
	protected abstract AbsContactRepository<T> getRepository();
}
