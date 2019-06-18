/**
 * 
 */
package com.photo.pgm.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.ApproveOpinions;
import com.photo.pgm.core.service.ApproveOpinionsService;
import com.photo.pgm.core.service.ContractService;
import com.photo.pgm.core.vo.ApproveOpinionsQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/approveResult/list")
public class ApproveOpinionsListController extends AbsQueryPagedListController<ApproveOpinions, ApproveOpinionsQueryInfo> {

	@Autowired private ApproveOpinionsService approveOpinionsService;
	@Autowired private ContractService contractService;
	
	@RequestMapping("json")
	@ResponseBody
	public ModelAndView json(@ModelAttribute("pageQueryInfo") ApproveOpinionsQueryInfo queryInfo, @RequestParam String entityId, ModelMap modelMap){
		queryInfo.setSort(new Sort(Direction.ASC, "comleteTime"));
		queryInfo.setSf_EQ_contract(contractService.get(entityId));
		Page<ApproveOpinions> aoList = approveOpinionsService.findByContract(queryInfo);
		return toJSONView(aoList);
	}
	
	@Override
	public ApproveOpinionsQueryInfo newPagedQueryInfo() {
		return new ApproveOpinionsQueryInfo();
	}

	@Override
	protected ApproveOpinionsService getEntityService() {
		return approveOpinionsService;
	}

}
