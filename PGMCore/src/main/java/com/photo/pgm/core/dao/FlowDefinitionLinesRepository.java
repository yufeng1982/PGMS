/**
 * 
 */
package com.photo.pgm.core.dao;

import java.util.List;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.pgm.core.model.FlowDefinition;
import com.photo.pgm.core.model.FlowDefinitionLines;

/**
 * @author FengYu
 *
 */
public interface FlowDefinitionLinesRepository extends AbsCodeNameEntityRepository<FlowDefinitionLines> {

	public List<FlowDefinitionLines> findByFlowDefinitionIdAndCorporationAndActiveTrueOrderBySeqNoAsc(String fdId, Corporation corporation);

	public FlowDefinitionLines findByCodeAndFlowDefinitionAndActiveTrue(String code, FlowDefinition flowDefinition);
}
