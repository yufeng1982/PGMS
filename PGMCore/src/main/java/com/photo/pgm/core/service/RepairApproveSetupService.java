/**
 * 
 */
package com.photo.pgm.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.RepairApproveSetupRepository;
import com.photo.pgm.core.enums.RepairApproveType;
import com.photo.pgm.core.model.RepairApproveSetup;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class RepairApproveSetupService extends AbsCodeNameEntityService<RepairApproveSetup, PageInfo<RepairApproveSetup>> {

	@Autowired private RepairApproveSetupRepository repairApproveSetupRepository;
	
	public List<RepairApproveSetup> findByPat(RepairApproveType pat) {
		return getRepository().findByPatAndActiveTrueOrderBySeqAsc(pat);
	}
	
	public RepairApproveSetup findByCode(String code) {
		return getRepository().findByCodeAndActiveTrue(code);
	}
	
	@Override
	protected RepairApproveSetupRepository getRepository() {
		return repairApproveSetupRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
