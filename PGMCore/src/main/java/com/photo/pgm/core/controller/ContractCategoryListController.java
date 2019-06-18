/**
 * 
 */
package com.photo.pgm.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.web.controller.entity.AbsMaintenanceController;
import com.photo.pgm.core.model.ContractCategory;
import com.photo.pgm.core.service.ContractCategoryService;
import com.photo.pgm.core.vo.ContractCategoryQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/contractCategory")
public class ContractCategoryListController extends AbsMaintenanceController<ContractCategory, PageInfo<ContractCategory>> {

	@Autowired private ContractCategoryService contractCategoryService;
	
	@Override
	protected ContractCategoryService getEntityService() {
		return contractCategoryService;
	}

	@Override
	public String getPropUrl() {
		return null;
	}

	@Override
	public boolean isPaged() {
		return true;
	}

	@Override
	public boolean isInfinite() {
		return false;
	}

	@Override
	@RequiresPermissions("contractCategory:list")
	@RequestMapping("list")
	public String list(ModelMap modelMap, HttpServletRequest request, 
			@ModelAttribute("model") ContractCategory model, 
			@RequestParam(value="parameterNames", required=false) String parameterNames) {
		return super.listCommon(modelMap, request, model, parameterNames);
	}

	@Override
	@RequiresPermissions("contractCategory:save")
	@RequestMapping("apply")
	public String apply(@RequestParam(value = "modifiedRecords") JSONArray modifiedRecords, 
			@RequestParam(value = "lineToDelete") String lineToDelete,
			@RequestParam(value = "parameterNames", required=false) String parameterNames, 
			@ModelAttribute("model") ContractCategory model, ModelMap modelMap,
			HttpServletRequest request) {
		return super.applyCommon(modifiedRecords, lineToDelete, parameterNames, model, modelMap, request);
	}

	@Override
	public ContractCategoryQueryInfo newPagedQueryInfo() {
		return new ContractCategoryQueryInfo();
	}

}
