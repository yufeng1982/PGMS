/**
 * 
 */
package com.photo.pgm.core.dao;

import java.util.List;

import com.photo.bas.core.dao.entity.AbsEntityRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.pgm.core.model.RepairApproveOpinions;

/**
 * @author FengYu
 *
 */
public interface RepairApproveOpinionsRepository extends AbsEntityRepository<RepairApproveOpinions> {

	public List<RepairApproveOpinions> findByRepairOrderIdAndCorporationAndActiveTrueOrderByComleteTimeAsc(String entityId, Corporation corporation);
	public List<RepairApproveOpinions> findByRepairSettleAccountIdAndCorporationAndActiveTrueOrderByComleteTimeAsc(String entityId, Corporation corporation);
}
