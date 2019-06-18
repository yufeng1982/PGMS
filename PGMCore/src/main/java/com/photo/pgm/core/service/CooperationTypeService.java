/**
 * 
 */
package com.photo.pgm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsMaintenanceService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.CooperationTypeRepository;
import com.photo.pgm.core.model.CooperationType;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class CooperationTypeService extends AbsMaintenanceService<CooperationType, PageInfo<CooperationType>> {

	@Autowired CooperationTypeRepository cooperationTypeRepository;
	
	@Override
	protected CooperationTypeRepository getRepository() {
		return cooperationTypeRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
