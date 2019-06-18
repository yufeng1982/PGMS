/**
 * 
 */
package com.photo.pgm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsMaintenanceService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.CostCenterRepository;
import com.photo.pgm.core.model.CostCenter;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class CostCenterService extends AbsMaintenanceService<CostCenter, PageInfo<CostCenter>> {

	@Autowired private CostCenterRepository costCenterRepository;
	
	@Override
	protected CostCenterRepository getRepository() {
		return costCenterRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
