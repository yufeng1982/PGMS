/**
 * 
 */
package com.photo.pgm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.PetrolStationRepository;
import com.photo.pgm.core.model.CostCenter;
import com.photo.pgm.core.model.PetrolStation;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class PetrolStationService extends AbsCodeNameEntityService<PetrolStation, PageInfo<PetrolStation>> {

	@Autowired private PetrolStationRepository petrolStationRepository;
	@Autowired private CostCenterService costCenterService;
	
	@Transactional(readOnly = false)
	public PetrolStation save(PetrolStation t) {
		// 新建开发管理油站项目信息时，自动创建一个成本中心，该油站项目就是一个成本中心
		CostCenter costCenter = costCenterService.findByCodeAndCorporation(t.getCode(), t.getCorporation());
		if (costCenter == null) {
			costCenter = new CostCenter();
			costCenter.setCode(t.getCode());
			costCenter.setName(t.getName());
			costCenterService.save(costCenter);
		}
		super.save(t);
		return t;
	}
	
	public PetrolStation findByCode(String code, Corporation corporation) {
		return getRepository().findByCodeAndCorporationAndActiveTrue(code, corporation);
	}
	public PetrolStation findBySapCode(String sapcode, Corporation corporation) {
		return getRepository().findBySapCodeAndCorporationAndActiveTrue(sapcode, corporation);
	}
	public PetrolStation findByCnCode(String cncode, Corporation corporation) {
		return getRepository().findByCnCodeAndCorporationAndActiveTrue(cncode, corporation);
	}
	
	@Override
	protected PetrolStationRepository getRepository() {
		return petrolStationRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
