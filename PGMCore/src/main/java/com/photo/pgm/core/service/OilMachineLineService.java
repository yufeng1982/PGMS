/**
 * 
 */
package com.photo.pgm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.OilMachineLineRepository;
import com.photo.pgm.core.model.OilMachineLine;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class OilMachineLineService extends AbsEntityService<OilMachineLine, PageInfo<OilMachineLine>> {

	@Autowired private OilMachineLineRepository oilMachineLineRepository;
	
	@Override
	protected OilMachineLineRepository getRepository() {
		return oilMachineLineRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
