package com.photo.bas.core.web.controller.entity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.entity.IEntity;

/**
 * @author FengYu
 */
public abstract class AbsPagedListController<T extends IEntity> extends AbsListController<T> {
	
	protected ModelAndView toJSONView(Page<? extends IEntity> page, JSONArray jsonArray) {
		JSONObject jso = new JSONObject();
		jso.put("total", page.getTotalElements());
		jso.put("totalRecordSize", page.getTotalElements());
		jso.put("totalPageAmount",  page.getTotalPages());
		jso.put("data", jsonArray);
		return toJSONView(jso);
	}
	
	protected ModelAndView toJSONView(Page<? extends IEntity> page) {
		JSONObject jso = new JSONObject();
		jso.put("total", page.getTotalElements());
		jso.put("totalRecordSize", page.getTotalElements());
		jso.put("totalPageAmount",  page.getTotalPages());
		jso.put("data", buildJSONArray(page.getContent()));
		return toJSONView(jso);
	}
	
	
	protected ModelAndView toPagedJSONView(List<T> entityList) {
		JSONObject jso = new JSONObject();
		PageImpl<T> page = new PageImpl<T>(entityList);
		jso.put("total", page.getTotalElements());
		jso.put("totalRecordSize", page.getTotalElements());
		jso.put("totalPageAmount",  page.getTotalPages());
		jso.put("data", buildJSONArray(page.getContent()));
		return toJSONView(jso);
	}
	
}
