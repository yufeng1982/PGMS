/**
 * 
 */
package com.photo.pgm.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.FlowDefinitionLines;
import com.photo.pgm.core.service.FlowDefinitionLinesService;
import com.photo.pgm.core.service.FlowDefinitionService;
import com.photo.pgm.core.vo.FlowDefinitionLinesQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value="/pgm/project/flowDefinitionLine/list")
public class FlowDefinitionLineListController extends AbsQueryPagedListController<FlowDefinitionLines, FlowDefinitionLinesQueryInfo> {

	
	@Autowired private FlowDefinitionLinesService flowDefinitionLinesService;
	@Autowired private FlowDefinitionService flowDefinitionService;
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") FlowDefinitionLinesQueryInfo queryInfo, @RequestParam String flowDefinitionId){
		queryInfo.setSort(new Sort(Direction.ASC, "seqNo"));
		queryInfo.setSf_EQ_flowDefinition(flowDefinitionService.get(flowDefinitionId));
		Page<FlowDefinitionLines> list = getEntityService().findByFlowDefinition(queryInfo);
		return toJSONView(list);
	}

	@Override
	protected FlowDefinitionLinesService getEntityService() {
		return flowDefinitionLinesService;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("XZ20");
	}

	@Override
	public FlowDefinitionLinesQueryInfo newPagedQueryInfo() {
		return new FlowDefinitionLinesQueryInfo();
	}
}
