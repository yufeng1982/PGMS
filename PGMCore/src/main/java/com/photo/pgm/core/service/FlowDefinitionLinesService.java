/**
 * 
 */
package com.photo.pgm.core.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.FlowDefinitionLinesRepository;
import com.photo.pgm.core.model.FlowDefinition;
import com.photo.pgm.core.model.FlowDefinitionLines;
import com.photo.pgm.core.vo.FlowDefinitionLinesQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class FlowDefinitionLinesService extends AbsCodeNameEntityService<FlowDefinitionLines, PageInfo<FlowDefinitionLines>> {

	@Autowired private FlowDefinitionLinesRepository flowDefinitionLinesRepository;
	
	public Page<FlowDefinitionLines> findByFlowDefinition(final FlowDefinitionLinesQueryInfo queryInfo) {
		if (queryInfo.getSf_EQ_flowDefinition() == null) return new PageImpl<FlowDefinitionLines>(new ArrayList<FlowDefinitionLines>());
		return getRepository().findAll(byPage(queryInfo), queryInfo);
	}
	
	public FlowDefinitionLines findByCodeAndFlowDefinition(String code, FlowDefinition flowDefinition){
		return getRepository().findByCodeAndFlowDefinitionAndActiveTrue(code, flowDefinition);
	}
	
	@Override
	protected FlowDefinitionLinesRepository getRepository() {
		return flowDefinitionLinesRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
