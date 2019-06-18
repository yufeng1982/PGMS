package com.photo.pgm.core.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.service.common.DepartmentService;
import com.photo.bas.core.vo.common.DepartmentQueryInfo;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;

@Controller
@RequestMapping(value="/pgm/department/list")
public class DepartmentListController extends AbsQueryPagedListController<Department,DepartmentQueryInfo> {
    
	@Autowired DepartmentService departmentService;
	private final static String  PAGE_LIST_PATH ="hr/department/departments";
	
	protected DepartmentService getEntityService() {
		return departmentService;
	}
	
	@RequestMapping
	@RequiresPermissions("department:list")
	public String show(ModelMap modelMap){
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") DepartmentQueryInfo queryInfo, ModelMap modelMap){
		Page<Department> page =  getEntityService().findAllDepartments(queryInfo);
        return toJSONView(page);
    }
	
	@Override
	public DepartmentQueryInfo newPagedQueryInfo() {
		return new DepartmentQueryInfo();
	}
}
