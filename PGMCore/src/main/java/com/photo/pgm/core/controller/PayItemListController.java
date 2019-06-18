/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.photo.bas.core.model.security.UserDepartment;
import com.photo.bas.core.security.service.UserDepartmentService;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.PayItem;
import com.photo.pgm.core.service.PayItemService;
import com.photo.pgm.core.vo.PayItemQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/payItem/list")
public class PayItemListController extends AbsQueryPagedListController<PayItem, PayItemQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/payItem/payItems";
	
	@Autowired private PayItemService payItemService;
	@Autowired private UserDepartmentService userDepartmentService;
	
	@RequestMapping
	@RequiresPermissions("payItem:list")
	public String show(ModelMap modelMap){
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") PayItemQueryInfo queryInfo){
		Sort sort = new Sort(Direction.ASC, "contract.code").and(new Sort(Direction.ASC, "payCount"));
		queryInfo.setSort(sort);
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
		Page<PayItem> list = getEntityService().findLists(queryInfo);
		return toJSONView(list);
	}
	
	@Override
	public PayItemQueryInfo newPagedQueryInfo() {
		return new PayItemQueryInfo();
	}

	@Override
	protected PayItemService getEntityService() {
		return payItemService;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC30");
	}
	
}
