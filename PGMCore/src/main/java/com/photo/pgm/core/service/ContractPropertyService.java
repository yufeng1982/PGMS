/**
 * 
 */
package com.photo.pgm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsMaintenanceService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.ContractPropertyRepository;
import com.photo.pgm.core.model.ContractProperty;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class ContractPropertyService extends AbsMaintenanceService<ContractProperty, PageInfo<ContractProperty>> {

	@Autowired private ContractPropertyRepository contractPropertyRepository;
	
	@Override
	protected ContractPropertyRepository getRepository() {
		return contractPropertyRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
