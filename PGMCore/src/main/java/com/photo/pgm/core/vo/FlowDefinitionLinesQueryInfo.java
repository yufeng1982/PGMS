/**
 * 
 */
package com.photo.pgm.core.vo;

import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.FlowDefinition;
import com.photo.pgm.core.model.FlowDefinitionLines;

/**
 * @author FengYu
 *
 */
public class FlowDefinitionLinesQueryInfo extends PageInfo<FlowDefinitionLines> {

	private FlowDefinition sf_EQ_flowDefinition;

	public FlowDefinition getSf_EQ_flowDefinition() {
		return sf_EQ_flowDefinition;
	}

	public void setSf_EQ_flowDefinition(FlowDefinition sf_EQ_flowDefinition) {
		this.sf_EQ_flowDefinition = sf_EQ_flowDefinition;
	}
	
	
}
