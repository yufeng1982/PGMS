/**
 * 
 */
package com.photo.pgm.core.dao;

import java.util.List;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.pgm.core.model.FlowDefinition;

/**
 * @author FengYu
 *
 */
public interface FlowDefinitionRepository extends AbsCodeNameEntityRepository<FlowDefinition> {

	public List<FlowDefinition> findByCorporationAndActiveTrue(Corporation corporaton);
	
}
