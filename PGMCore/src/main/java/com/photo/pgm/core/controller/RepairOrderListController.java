/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.UserType;
import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.PetrolStation;
import com.photo.pgm.core.model.RepairOrder;
import com.photo.pgm.core.model.UserProject;
import com.photo.pgm.core.service.RepairOrderService;
import com.photo.pgm.core.service.UserProjectService;
import com.photo.pgm.core.vo.RepairOrderQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/repairOrder/list")
public class RepairOrderListController extends AbsQueryPagedListController<RepairOrder, RepairOrderQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/repairOrder/repairOrders";
	
	@Autowired private RepairOrderService repairOrderService;
	@Autowired private UserProjectService userProjectService;
	
	@RequestMapping
	@RequiresPermissions("repairOrder:list")
	public String show(ModelMap modelMap){
		List<UserProject> upList = userProjectService.findByUsersAndCorporation(ThreadLocalUtils.getCurrentUser(), ThreadLocalUtils.getCurrentCorporation());
		StringBuffer ids = new StringBuffer();
		for (UserProject up : upList) {
			if (ids.length() == 0) {
				ids.append(up.getPetrolStation().getId());
			} else {
				ids.append(",").append(up.getPetrolStation().getId());
			}
		}
		modelMap.addAttribute("ids", ids.toString());
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") RepairOrderQueryInfo queryInfo){
		User user = ThreadLocalUtils.getCurrentUser();
		Sort sort = new Sort(Direction.ASC, "seq");
		queryInfo.setSort(sort);
		if (user.getUserType().equals(UserType.Org)) {
			Page<RepairOrder> repairOrderList =  repairOrderService.findLists(queryInfo);
			return toJSONView(repairOrderList);
		} else {
			List<UserProject> upList = userProjectService.findByUsersAndCorporation(user, ThreadLocalUtils.getCurrentCorporation());
			List<PetrolStation> pList = new ArrayList<PetrolStation>();
			for (UserProject up : upList) {
				pList.add(up.getPetrolStation());
			}
			if (pList.isEmpty()) {
				Page<RepairOrder> repairOrderList = new PageImpl<RepairOrder>(new ArrayList<RepairOrder>());
				return toJSONView(repairOrderList);
			} else {
				queryInfo.setSf_IN_petrolStation(pList);
			}
			Page<RepairOrder> repairOrderList =  repairOrderService.findLists(queryInfo);
			return toJSONView(repairOrderList);
		}
	}
	
	@Override
	public RepairOrderQueryInfo newPagedQueryInfo() {
		return new RepairOrderQueryInfo();
	}

	@Override
	protected RepairOrderService getEntityService() {
		return repairOrderService;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC20");
	}
}
