/**
 * 
 */
package com.photo.pgm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsMaintenanceService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.ContractCategoryRepository;
import com.photo.pgm.core.model.ContractCategory;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class ContractCategoryService extends AbsMaintenanceService<ContractCategory, PageInfo<ContractCategory>> {

	@Autowired private ContractCategoryRepository contractCategoryRepository;
	
	@Override
	protected ContractCategoryRepository getRepository() {
		return contractCategoryRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
