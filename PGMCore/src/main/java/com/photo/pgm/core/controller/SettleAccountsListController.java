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
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsListController;
import com.photo.pgm.core.model.SettleAccounts;
import com.photo.pgm.core.service.SettleAccountsService;
import com.photo.pgm.core.vo.SettleAccountsQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/settleAccounts/list")
public class SettleAccountsListController extends AbsListController<SettleAccounts> {

	private final static String PAGE_LIST_PATH = "project/settleAccounts/settleAccountss";
	
	@Autowired private SettleAccountsService settleAccountsService;
	@Autowired private UserDepartmentService userDepartmentService;
	
	@RequestMapping
	@RequiresPermissions("settleAccounts:list")
	public String show(ModelMap modelMap){
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") SettleAccountsQueryInfo queryInfo){
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
		Page<SettleAccounts> list = getEntityService().findByQueryInfo(queryInfo);
		return toJSONView(list);
	}
	
	@Override
	protected SettleAccountsService getEntityService() {
		return settleAccountsService;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC12");
	}
	
}
