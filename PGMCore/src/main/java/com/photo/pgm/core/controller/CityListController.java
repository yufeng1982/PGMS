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
import com.photo.pgm.core.model.City;
import com.photo.pgm.core.service.CityService;
import com.photo.pgm.core.vo.CityQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/city")
public class CityListController extends AbsMaintenanceController<City, PageInfo<City>> {

	@Autowired private CityService cityService;
	
	
	@Override
	protected CityService getEntityService() {
		return cityService;
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
	@RequiresPermissions("city:list")
	@RequestMapping("list")
	public String list(ModelMap modelMap, HttpServletRequest request, 
			@ModelAttribute("model") City model, 
			@RequestParam(value="parameterNames", required=false)String parameterNames) {
		return super.listCommon(modelMap, request, model, parameterNames);
	}

	@Override
	@RequiresPermissions("city:save")
	@RequestMapping("apply")
	public String apply(@RequestParam(value = "modifiedRecords") JSONArray modifiedRecords, 
			@RequestParam(value = "lineToDelete") String lineToDelete,
			@RequestParam(value = "parameterNames", required=false) String parameterNames, 
			@ModelAttribute("model") City model, ModelMap modelMap,
			HttpServletRequest request) {
		return super.applyCommon(modifiedRecords, lineToDelete, parameterNames, model, modelMap, request);
	}

	@Override
	public CityQueryInfo newPagedQueryInfo() {
		return new CityQueryInfo();
	}

}
