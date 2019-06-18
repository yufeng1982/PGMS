package com.photo.bas.core.service.common;

import com.photo.bas.core.dao.common.AbsZoneRepository;
import com.photo.bas.core.model.common.AbsZone;
import com.photo.bas.core.service.entity.AbsMaintenanceService;
import com.photo.bas.core.utils.PageInfo;
/**
 * @author FengYu
 * */
public abstract class AbsZoneService<T extends AbsZone, P extends PageInfo<T>> extends AbsMaintenanceService<T, P>{
	
	protected abstract AbsZoneRepository<T> getRepository();

	@Override
	public boolean isCommonAccess() {
		return true;
	}
}
