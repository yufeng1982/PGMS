/**
 * 
 */
package com.photo.pgm.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.dao.entity.AbsRepository;
import com.photo.bas.core.service.entity.AbsService;

/**
 * @author FengYu
 *
 */
@SuppressWarnings("rawtypes")
@Service
@Transactional(readOnly = true)
public class ReportsService extends AbsService {

	@Override
	protected AbsRepository getRepository() {
		return null;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
