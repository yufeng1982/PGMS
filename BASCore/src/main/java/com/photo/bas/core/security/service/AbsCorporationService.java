/**
 * 
 */
package com.photo.bas.core.security.service;

import com.photo.bas.core.dao.security.AbsCorporationRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.vo.security.AbsCorporationPageInfo;

/**
 * @author FengYu
 *
 */
public abstract class AbsCorporationService<T extends Corporation, P extends AbsCorporationPageInfo<T>> extends AbsCodeNameEntityService<T, P> {

	@Override
	protected abstract AbsCorporationRepository<T> getRepository();

	@Override
	public boolean isCommonAccess() {
		return true;
	}

}
