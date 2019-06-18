package com.photo.bas.core.web.controller.entity;

import org.json.JSONArray;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.entity.IEntity;
import com.photo.bas.core.utils.PageInfo;
/**
 * 
 * @author FengYu
 *
 * @param <T>
 * @param <P>
 */
public abstract class AbsPopupSelectController<T extends IEntity, P extends PageInfo<T>> extends AbsFormController<T> {
	
	protected abstract String getPageURL();
	protected abstract void populateModelMap(ModelMap modelMap, P p);
	
	@RequestMapping(value = "{id}/show")
	public String show(@ModelAttribute("pageQueryInfo") P queryInfo,
						@RequestParam(value="filterType",required=false) String filterType,
						ModelMap modelMap) {
		modelMap.addAttribute("filterType",filterType);
		modelMap.put("queryInfo", queryInfo.toJSONObject().toString());
		populateModelMap(modelMap, queryInfo);
		return getPageURL();
	}
	
	@RequestMapping(value = "{id}/ok")
	public ModelAndView ok(ModelMap modelMap,
			@RequestParam(value="selectedRecord") JSONArray selectedRecord) {
		return closePage(modelMap, selectedRecord);
	}
	
	@RequestMapping(value = "{id}/cancel")
	public ModelAndView cancel(ModelMap modelMap) {
		return closePage(modelMap);
	}
	
	@ModelAttribute("gridConfigName")
    public String getGridConfigName(@RequestParam(value="gridConfigName", required=false) String gridConfigName) {
    	return gridConfigName;
    }
	
	@ModelAttribute("pageQueryInfo")
	public P populateData() {
		return newPagedQueryInfo();
	}
	
	public abstract P newPagedQueryInfo();
}
