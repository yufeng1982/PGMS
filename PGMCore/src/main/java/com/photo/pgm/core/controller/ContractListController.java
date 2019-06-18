/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.model.common.UserType;
import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.model.security.UserDepartment;
import com.photo.bas.core.security.service.UserDepartmentService;
import com.photo.bas.core.utils.DateTimeUtils;
import com.photo.bas.core.utils.JsonUtils;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.enums.ContractType;
import com.photo.pgm.core.model.AssetsCategory;
import com.photo.pgm.core.model.Contract;
import com.photo.pgm.core.model.ContractCategory;
import com.photo.pgm.core.model.OurSideCorporation;
import com.photo.pgm.core.service.AssetsCategoryService;
import com.photo.pgm.core.service.ContractCategoryService;
import com.photo.pgm.core.service.ContractService;
import com.photo.pgm.core.service.OurSideCorporationService;
import com.photo.pgm.core.vo.ContractQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value="/pgm/project/contract/list")
public class ContractListController extends AbsQueryPagedListController<Contract,  ContractQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/contract/contracts";
	
	@Autowired private ContractService contractService;
	@Autowired private OurSideCorporationService ourSideCorporationService;
	@Autowired private AssetsCategoryService assetsCategoryService;
	@Autowired private ContractCategoryService contractCategoryService;
	@Autowired private UserDepartmentService userDepartmentService;
	
	@RequestMapping
	@RequiresPermissions("contract:list")
	public String show(ModelMap modelMap){
		modelMap.addAttribute("contractProperty", JsonUtils.buildEnmuCoboxData(ContractType.getContractType()));
		List<OurSideCorporation> oscList = (List<OurSideCorporation>) ourSideCorporationService.getAllByCorporation(ThreadLocalUtils.getCurrentCorporation());
		modelMap.addAttribute("oscList", JsonUtils.buildAbsCodeNameEntityCoboxData(oscList));
		List<AssetsCategory> acList = (List<AssetsCategory>) assetsCategoryService.getAllByCorporation(ThreadLocalUtils.getCurrentCorporation());
		modelMap.addAttribute("acList", JsonUtils.buildAbsCodeNameEntityCoboxData(acList));
		List<ContractCategory> ccList = (List<ContractCategory>) contractCategoryService.getAllByCorporation(ThreadLocalUtils.getCurrentCorporation());
		modelMap.addAttribute("ccList", JsonUtils.buildAbsCodeNameEntityCoboxData(ccList));
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") ContractQueryInfo queryInfo){
		User user = ThreadLocalUtils.getCurrentUser();
		List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
		PropertyFilter deprartmentPF = null;
		if (user.getUserType().equals(UserType.Normal)) {
			List<UserDepartment> udList = userDepartmentService.findByUsersAndCorporation(user, ThreadLocalUtils.getCurrentCorporation());
			if (!udList.isEmpty()) {
				List<Department> departments = new ArrayList<Department>();
				for (UserDepartment ud : udList) {
					departments.add(ud.getDepartment());
				}
				queryInfo.setSf_IN_department(departments);
				deprartmentPF = new PropertyFilter("department", departments, MatchType.IN);
				pfList.add(deprartmentPF);
			}
		}
		PropertyFilter corPF = new PropertyFilter("corporation", queryInfo.getSf_EQ_corporation(), MatchType.EQ);
		pfList.add(corPF);
		PropertyFilter projectPF = new PropertyFilter("petrolStation", queryInfo.getSf_EQ_petrolStation(), MatchType.EQ);
		pfList.add(projectPF);
		PropertyFilter contractCategoryPF = new PropertyFilter("contractCategory", queryInfo.getSf_EQ_contractCategory(), MatchType.EQ);
		pfList.add(contractCategoryPF);
		PropertyFilter assetsCategoryPF = new PropertyFilter("assetsCategory", queryInfo.getSf_EQ_assetsCategory(), MatchType.EQ);
		pfList.add(assetsCategoryPF);
		PropertyFilter cCodePF = new PropertyFilter("code", queryInfo.getSf_LIKE_code(), MatchType.LIKE);
		pfList.add(cCodePF);
		PropertyFilter cNamePF = new PropertyFilter("name", queryInfo.getSf_LIKE_name(), MatchType.LIKE);
		pfList.add(cNamePF);
		PropertyFilter cmindatePF = new PropertyFilter("creationDate", queryInfo.getSf_EQ_creationDate(), MatchType.GT);
		pfList.add(cmindatePF);
		PropertyFilter cmaxdatePF = new PropertyFilter("creationDate", DateTimeUtils.plusDays(queryInfo.getSf_EQ_creationDate(), 1), MatchType.LT);
		pfList.add(cmaxdatePF);
		PropertyFilter cstatusPF = new PropertyFilter("contractStatus", queryInfo.getSf_EQ_contractStatus(), MatchType.IN);
		pfList.add(cstatusPF);
		pfList.add(PropertyFilter.activeFilter());
		List<Contract> list = getEntityService().search(pfList);
		return toPagedJSONView(list);
	}
	
	@Override
	public ContractQueryInfo newPagedQueryInfo() {
		return new ContractQueryInfo();
	}

	@Override
	protected ContractService getEntityService() {
		return contractService;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC10");
	}
}
