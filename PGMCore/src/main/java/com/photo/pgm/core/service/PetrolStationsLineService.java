/**
 * 
 */
package com.photo.pgm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsNameEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.PetrolStationsLineRepository;
import com.photo.pgm.core.model.PetrolStationsLine;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class PetrolStationsLineService extends AbsNameEntityService<PetrolStationsLine, PageInfo<PetrolStationsLine>> {

	@Autowired private PetrolStationsLineRepository petrolStationsLineRepository;
	
	@Override
	protected PetrolStationsLineRepository getRepository() {
		return petrolStationsLineRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
