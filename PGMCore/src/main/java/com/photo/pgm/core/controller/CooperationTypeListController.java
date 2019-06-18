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
import com.photo.pgm.core.model.CooperationType;
import com.photo.pgm.core.service.CooperationTypeService;
import com.photo.pgm.core.vo.CooperationTypeQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/cooperationType")
public class CooperationTypeListController extends AbsMaintenanceController<CooperationType, PageInfo<CooperationType>> {

	@Autowired private CooperationTypeService cooperationTypeService;
	
	@Override
	protected CooperationTypeService getEntityService() {
		return cooperationTypeService;
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
	@RequiresPermissions("cooperationType:list")
	@RequestMapping("list")
	public String list(ModelMap modelMap, HttpServletRequest request, @ModelAttribute("model") CooperationType cooperationType,
			@RequestParam(value="parameterNames", required=false) String parameterNames) {
		return super.listCommon(modelMap, request, cooperationType, parameterNames);
	}

	@Override
	@RequiresPermissions("cooperationType:save")
	@RequestMapping("apply")
	public String apply(@RequestParam(value = "modifiedRecords") JSONArray modifiedRecords, 
			@RequestParam(value = "lineToDelete") String lineToDelete,
			@RequestParam(value = "parameterNames", required=false) String parameterNames, 
			@ModelAttribute("model") CooperationType cooperationType, ModelMap modelMap,
			HttpServletRequest request) {
		return super.applyCommon(modifiedRecords, lineToDelete, parameterNames, cooperationType, modelMap, request);
	}

	@Override
	public CooperationTypeQueryInfo newPagedQueryInfo() {
		return new CooperationTypeQueryInfo();
	}
	
	@Override
	public boolean isInfinite() {
		return false;
	}
}
