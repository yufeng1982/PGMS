/**
 * 
 */
package com.photo.pgm.core.dao;

import java.util.List;

import com.photo.bas.core.dao.entity.AbsEntityRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.pgm.core.model.ApproveOpinions;

/**
 * @author FengYu
 *
 */
public interface ApproveOpinionsRepository extends AbsEntityRepository<ApproveOpinions> {

	public List<ApproveOpinions> findByContractIdAndCorporationAndActiveTrueOrderByComleteTimeAsc(String entityId, Corporation corporation);
}
