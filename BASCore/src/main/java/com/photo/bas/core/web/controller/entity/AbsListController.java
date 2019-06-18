package com.photo.bas.core.web.controller.entity;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.entity.IEntity;
import com.photo.bas.core.web.view.ListExcelView;

/**
 * @author FengYu
 */
public abstract class AbsListController<T extends IEntity> extends AbsController<T> {
	
	@SuppressWarnings("unchecked")
	@RequestMapping("searchList")
	public ModelAndView searchList(@RequestParam(value="query", required=false) String queryStr) {
		Iterable<T> listResult = getEntityService().search(new Sort("code"), queryStr, getSearchByPropertyNames());
		return toJSONView(listResult);
	}
	
	@RequestMapping("find")
	public ModelAndView find(String code, String corporation) {
		T t = this.getByCode(code, corporation);
		return toJSONView(t == null ? new JSONObject() : t.toJSONObject());
	}
	
	protected T getByCode(String code, String corporation) {
		// TODO
		return null;
	}
	
	/**
	 * over write this method, if you want to search by other properties
	 * @return
	 */
	protected String[] getSearchByPropertyNames() {
		return new String[]{"name"};
	}
	
	protected ModelAndView toJSONView(Iterable<? extends IEntity> list) {
		JSONArray ja = buildJSONArray(list);
		return toJSONView(ja, "");
	}

	protected ModelAndView toJSONView(JSONArray jsonArray) {
		return toJSONView(jsonArray, "");
	}
	protected ModelAndView toJSONView(JSONArray jsonArray, String warningMsg) {
		JSONObject jso = new JSONObject();
		jso.put("data", jsonArray);
		jso.put("warningMsg", warningMsg);
		return toJSONView(jso);
	}
	
	protected ModelAndView generateExcelView(String columnConfigStr, ModelMap modelMap, JSONArray dataJSONArray, String fileName) {
		Assert.notNull(fileName);
		JSONArray ja =  null;
		try {
			ja = new JSONArray(columnConfigStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String title = fileName + ".xls";
		title = title.replace(" ", "_");
		modelMap.put("fileName", title); 
		ListExcelView view = new ListExcelView(ja, dataJSONArray);
		return new ModelAndView(view , modelMap);
	}
}
