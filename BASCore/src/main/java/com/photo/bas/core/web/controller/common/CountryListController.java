/**
 * 
 */
package com.photo.bas.core.web.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.photo.bas.core.model.common.Country;
import com.photo.bas.core.service.common.CountryService;
import com.photo.bas.core.vo.common.CountryPageInfo;
import com.photo.bas.core.web.controller.entity.AbsMaintenanceController;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value = "/*/country")
public class CountryListController extends AbsMaintenanceController<Country, CountryPageInfo> {

	@Autowired CountryService countryService;
	
	@Override
	protected CountryService getEntityService() {
		return countryService;
	}

	@Override
//	@RequiresPermissions("country:list")
	@RequestMapping("list")
	public String list(ModelMap modelMap, HttpServletRequest request, @ModelAttribute("model") Country country,
			@RequestParam(value="parameterNames", required=false) String parameterNames) {
		return super.listCommon(modelMap, request, country, parameterNames);
	}

	@Override
//	@RequiresPermissions("country:save")
	@RequestMapping("apply")
	public String apply(JSONArray records, String lineToDelete,
			String parameterNames, Country model, ModelMap modelMap,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.applyCommon(records, lineToDelete, parameterNames, model, modelMap, request);
	}

	@Override
	public String getPropUrl() {
		return null;
	}
	@Override
	public boolean isPaged() {
		return false;
	}
	
	@Override
	public CountryPageInfo newPagedQueryInfo() {
		return new CountryPageInfo();
	}

	@Override
	public boolean isInfinite() {
		return false;
	}

}
