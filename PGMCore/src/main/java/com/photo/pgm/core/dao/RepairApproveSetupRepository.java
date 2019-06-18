/**
 * 
 */
package com.photo.pgm.core.dao;

import java.util.List;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.pgm.core.enums.RepairApproveType;
import com.photo.pgm.core.model.RepairApproveSetup;

/**
 * @author FengYu
 *
 */
public interface RepairApproveSetupRepository extends AbsCodeNameEntityRepository<RepairApproveSetup> {

	public List<RepairApproveSetup> findByPatAndActiveTrueOrderBySeqAsc(RepairApproveType pat);
	
	public RepairApproveSetup findByCodeAndActiveTrue(String code);
}
