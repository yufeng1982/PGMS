/**
 * 
 */
package com.photo.bas.core.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.photo.bas.core.dao.common.ProvinceRepository;
import com.photo.bas.core.model.common.Province;
import com.photo.bas.core.service.entity.AbsMaintenanceService;
import com.photo.bas.core.vo.common.ProvincePageInfo;

/**
 * @author FengYu
 *
 */
@Component
public class ProvinceService extends AbsMaintenanceService<Province, ProvincePageInfo> {
	@Autowired private ProvinceRepository provinceRepository;
	
	@Override
	protected ProvinceRepository getRepository() {
		return provinceRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return true;
	}

	public List<Province> findByCountryId(String countryId) {
		return provinceRepository.findByCountryIdAndActiveTrue(countryId);
	}
}
