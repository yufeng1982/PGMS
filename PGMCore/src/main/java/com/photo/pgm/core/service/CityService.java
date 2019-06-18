/**
 * 
 */
package com.photo.pgm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsMaintenanceService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.CityRepository;
import com.photo.pgm.core.model.City;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class CityService extends AbsMaintenanceService<City, PageInfo<City>> {

	@Autowired private CityRepository cityRepository;
	
	@Override
	protected CityRepository getRepository() {
		return cityRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
