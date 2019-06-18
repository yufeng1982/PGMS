/**
 * 
 */
package com.photo.pgm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.OilCanLineRepository;
import com.photo.pgm.core.model.OilCanLine;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class OilCanLineService extends AbsEntityService<OilCanLine, PageInfo<OilCanLine>> {

	@Autowired private OilCanLineRepository oilCanLineRepository;
	
	@Override
	protected OilCanLineRepository getRepository() {
		return oilCanLineRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
