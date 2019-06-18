/**
 * 
 */
package com.photo.bas.core.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.photo.bas.core.dao.common.CountryRepository;
import com.photo.bas.core.model.common.Country;
import com.photo.bas.core.service.entity.AbsMaintenanceService;
import com.photo.bas.core.vo.common.CountryPageInfo;

/**
 * @author FengYu
 *
 */
@Component
public class CountryService extends AbsMaintenanceService<Country, CountryPageInfo> {
	@Autowired CountryRepository countryRepository;
	
	protected CountryRepository getRepository() {
		return countryRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return true;
	}

}
