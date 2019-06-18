package com.photo.bas.core.web.controller.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.Province;
import com.photo.bas.core.service.common.ProvinceService;
import com.photo.bas.core.vo.common.ProvincePageInfo;
import com.photo.bas.core.web.controller.entity.AbsMaintenanceController;
import com.photo.bas.core.web.view.JSONView;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value = "/*/province")
public class ProvinceListController extends AbsMaintenanceController<Province, ProvincePageInfo>{

	@Autowired ProvinceService provinceService;
	
	@RequestMapping(value = "listByCountry/{country}")
	public ModelAndView getProvincesList(@PathVariable(value = "country") String country) {
		List<Province> provinceList = provinceService.findByCountryId(country);
		JSONArray provinceArray = new JSONArray();
		if(provinceList != null && !provinceList.isEmpty()) {		
			for (Province province : provinceList) {
				JSONObject jo = new JSONObject();
				jo.put("id", province.getId());
				jo.put("text", province.getName());
				provinceArray.put(jo);
			}
		}
		JSONObject jso = new JSONObject();
		jso.put("data", provinceArray);
		return new ModelAndView(new JSONView(jso.toString()));

	}
	
	@Override
//	@RequiresPermissions("province:list")
	@RequestMapping("list")
	public String list(ModelMap modelMap, HttpServletRequest request, @ModelAttribute("model") Province province,
			@RequestParam(value="parameterNames", required=false) String parameterNames) {
		return super.listCommon(modelMap, request, province, parameterNames);
	}

	@Override
//	@RequiresPermissions("province:save")
	@RequestMapping("apply")
	public String apply(JSONArray records, String lineToDelete,
			String parameterNames, Province province, ModelMap modelMap,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.applyCommon(records, lineToDelete, parameterNames, province, modelMap, request);
	}

	@Override
	protected ProvinceService getEntityService() {
		return provinceService;
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
	public ProvincePageInfo newPagedQueryInfo()  {
		return new ProvincePageInfo();
	}
	
	@Override
	public boolean isInfinite() {
		return false;
	}
}
