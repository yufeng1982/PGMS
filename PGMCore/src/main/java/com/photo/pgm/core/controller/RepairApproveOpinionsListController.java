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
import com.photo.pgm.core.model.RepairApproveOpinions;
import com.photo.pgm.core.service.RepairApproveOpinionsService;
import com.photo.pgm.core.service.RepairOrderService;
import com.photo.pgm.core.service.RepairSettleAccountService;
import com.photo.pgm.core.vo.RepairApproveOpinionsQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/repairApprove/list")
public class RepairApproveOpinionsListController extends AbsQueryPagedListController<RepairApproveOpinions, RepairApproveOpinionsQueryInfo> {

	@Autowired private RepairApproveOpinionsService repairApproveOpinionsService;
	@Autowired private RepairOrderService repairOrderService;
	@Autowired private RepairSettleAccountService repairSettleAccountService;
	
	@RequestMapping("json4ro")
	@ResponseBody
	public ModelAndView json4ro(@ModelAttribute("pageQueryInfo") RepairApproveOpinionsQueryInfo queryInfo, @RequestParam String entityId, ModelMap modelMap){
		queryInfo.setSort(new Sort(Direction.ASC, "comleteTime"));
		queryInfo.setSf_EQ_repairOrder(repairOrderService.get(entityId));
		Page<RepairApproveOpinions> raoList = repairApproveOpinionsService.findByRepairOrder(queryInfo);
		return toJSONView(raoList);
	}
	
	@RequestMapping("json4rsa")
	@ResponseBody
	public ModelAndView json4rsa(@ModelAttribute("pageQueryInfo") RepairApproveOpinionsQueryInfo queryInfo, @RequestParam String entityId, ModelMap modelMap){
		queryInfo.setSort(new Sort(Direction.ASC, "comleteTime"));
		queryInfo.setSf_EQ_repairSettleAccount(repairSettleAccountService.get(entityId));
		Page<RepairApproveOpinions> raoList = repairApproveOpinionsService.findByRepairSettleAccount(queryInfo);
		return toJSONView(raoList);
	}
	
	@Override
	public RepairApproveOpinionsQueryInfo newPagedQueryInfo() {
		return new RepairApproveOpinionsQueryInfo();
	}

	@Override
	protected RepairApproveOpinionsService getEntityService() {
		return repairApproveOpinionsService;
	}

}
