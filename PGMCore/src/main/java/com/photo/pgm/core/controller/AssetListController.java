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

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.model.common.UserType;
import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.service.common.DepartmentsService;
import com.photo.bas.core.utils.JsonUtils;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.Asset;
import com.photo.pgm.core.model.PetrolStation;
import com.photo.pgm.core.model.UserProject;
import com.photo.pgm.core.service.AssetService;
import com.photo.pgm.core.service.UserProjectService;
import com.photo.pgm.core.vo.AssetQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/asset/list")
public class AssetListController extends AbsQueryPagedListController<Asset, AssetQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/asset/assets";
	
	@Autowired private AssetService assetService;
	@Autowired private DepartmentsService departmentsService;
	@Autowired private UserProjectService userProjectService;
	
	@RequestMapping
	@RequiresPermissions("asset:list")
	public String show(ModelMap modelMap){
		List<Department> dpList = (List<Department>) departmentsService.getAllByCorporation(ThreadLocalUtils.getCurrentCorporation());
		modelMap.addAttribute("dpList", JsonUtils.buildAbsCodeNameEntityCoboxData(dpList));
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
	public ModelAndView json(@ModelAttribute("pageQueryInfo") AssetQueryInfo queryInfo){
		User user = ThreadLocalUtils.getCurrentUser();
		Sort sort = new Sort(Direction.ASC, "seq");
		queryInfo.setSort(sort);
		if (user.getUserType().equals(UserType.Org)) {
			Page<Asset> assetList =  assetService.findLists(queryInfo);
			return toJSONView(assetList);
		} else {
			List<UserProject> upList = userProjectService.findByUsersAndCorporation(user, ThreadLocalUtils.getCurrentCorporation());
			List<PetrolStation> pList = new ArrayList<PetrolStation>();
			for (UserProject up : upList) {
				pList.add(up.getPetrolStation());
			}
			if (pList.isEmpty()) {
				Page<Asset> assetList = new PageImpl<Asset>(new ArrayList<Asset>());
				return toJSONView(assetList);
			} else {
				queryInfo.setSf_IN_petrolStation(pList);
			}
			Page<Asset> assetList =  assetService.findLists(queryInfo);
			return toJSONView(assetList);
		}
	}
	
	@Override
	public AssetQueryInfo newPagedQueryInfo() {
		return new AssetQueryInfo();
	}

	@Override
	protected AssetService getEntityService() {
		return assetService;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("ZC1");
	}
}
