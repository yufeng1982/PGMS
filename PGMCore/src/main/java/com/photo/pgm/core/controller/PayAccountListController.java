/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.photo.bas.core.utils.JsonUtils;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.AssetsCategory;
import com.photo.pgm.core.model.PayAccount;
import com.photo.pgm.core.service.AssetsCategoryService;
import com.photo.pgm.core.service.PayAccountService;
import com.photo.pgm.core.vo.PayAccountQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value="/pgm/project/payAccount/list")
public class PayAccountListController extends AbsQueryPagedListController<PayAccount, PayAccountQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/payAccount/payAccounts";
	
	@Autowired private PayAccountService payAccountService;
	@Autowired private AssetsCategoryService assetsCategoryService;
	@Autowired private UserDepartmentService userDepartmentService;
	
	@RequestMapping
	@RequiresPermissions("payAccount:list")
	public String show(ModelMap modelMap){
		List<AssetsCategory> acList = (List<AssetsCategory>) assetsCategoryService.getAllByCorporation(ThreadLocalUtils.getCurrentCorporation());
		modelMap.addAttribute("acList", JsonUtils.buildAbsCodeNameEntityCoboxData(acList));
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") PayAccountQueryInfo queryInfo){
		User user = ThreadLocalUtils.getCurrentUser();
		if (user.getUserType().equals(UserType.Normal)) {
			List<UserDepartment> udList = userDepartmentService.findByUsersAndCorporation(user, ThreadLocalUtils.getCurrentCorporation());
			if (!udList.isEmpty()) {
				List<Department> departments = new ArrayList<Department>();
				for (UserDepartment ud : udList) {
					departments.add(ud.getDepartment());
				}
				queryInfo.setSf_IN_department(departments);
			}
		}
		Page<PayAccount> list = getEntityService().findByQueryInfo(queryInfo);
		return toJSONView(list);
	}
	
	@Override
	public PayAccountQueryInfo newPagedQueryInfo() {
		return new PayAccountQueryInfo();
	}

	@Override
	protected PayAccountService getEntityService() {
		return payAccountService;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC31");
	}
}
