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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.UserType;
import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.PetrolStation;
import com.photo.pgm.core.model.RepairSettleAccount;
import com.photo.pgm.core.model.UserProject;
import com.photo.pgm.core.service.RepairSettleAccountService;
import com.photo.pgm.core.service.UserProjectService;
import com.photo.pgm.core.vo.RepairSettleAccountQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/repairSettleAccount/list")
public class RepairSettleAccountListController extends AbsQueryPagedListController<RepairSettleAccount, RepairSettleAccountQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/repairSettleAccount/repairSettleAccounts";
	
	@Autowired private RepairSettleAccountService repairSettleAccountService;
	@Autowired private UserProjectService userProjectService;
	
	
	@RequestMapping
	@RequiresPermissions("repairSettleAccount:list")
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
	public ModelAndView json(@ModelAttribute("pageQueryInfo") RepairSettleAccountQueryInfo queryInfo){
//		Page<RepairSettleAccount> repairSettleAccountList =  repairSettleAccountService.findLists(queryInfo);
		User user = ThreadLocalUtils.getCurrentUser();
		PropertyFilter codeF = new PropertyFilter("repairOrder.code", queryInfo.getSf_EQ_repairOrder(), MatchType.LIKE);
		PropertyFilter projectF = new PropertyFilter("repairOrder.petrolStation.id", queryInfo.getSf_EQ_petrolStation(), MatchType.EQ);
		PropertyFilter corF = new PropertyFilter("corporation",ThreadLocalUtils.getCurrentCorporation(), MatchType.EQ);
		if ((user.getUserType().equals(UserType.Org))) {
			Page<RepairSettleAccount> repairSettleAccountList = repairSettleAccountService.search(queryInfo, codeF, projectF, corF, PropertyFilter.activeFilter());
			return toJSONView(repairSettleAccountList);
		} else {
			List<UserProject> upList = userProjectService.findByUsersAndCorporation(user, ThreadLocalUtils.getCurrentCorporation());
			List<PetrolStation> pList = new ArrayList<PetrolStation>();
			for (UserProject up : upList) {
				pList.add(up.getPetrolStation());
			}
			if (pList.isEmpty()) {
				Page<RepairSettleAccount> repairSettleAccountList = new PageImpl<RepairSettleAccount>(new ArrayList<RepairSettleAccount>());
				return toJSONView(repairSettleAccountList);
			} else {
				PropertyFilter projectsF = new PropertyFilter("repairOrder.petrolStation", pList, MatchType.IN);
				Page<RepairSettleAccount> repairSettleAccountList = repairSettleAccountService.search(queryInfo, codeF, projectF, projectsF, corF, PropertyFilter.activeFilter());
				return toJSONView(repairSettleAccountList);
			}
		}
	}
	
	@Override
	public RepairSettleAccountQueryInfo newPagedQueryInfo() {
		return new RepairSettleAccountQueryInfo();
	}

	@Override
	protected RepairSettleAccountService getEntityService() {
		return repairSettleAccountService;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC21");
	}
}
