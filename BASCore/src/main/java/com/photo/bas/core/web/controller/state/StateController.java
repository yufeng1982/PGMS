package com.photo.bas.core.web.controller.state;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.service.common.StateService;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.view.JSONView;

@Controller
@RequestMapping(value="/*/state")
public class StateController {
	
	@Autowired private StateService stateService;
	
	@RequestMapping("json")
	public ModelAndView json(@RequestParam(value="pageName") String pageName,
			@RequestParam(value="scopeObjectType", required = false) String pageParameter) {
		
		Corporation corporation = ThreadLocalUtils.getCurrentCorporation();
		User currentUser = ThreadLocalUtils.getCurrentUser();
		JSONObject jso = readState(pageName, pageParameter, currentUser, corporation);
		return new ModelAndView(new JSONView(jso.toString()));
	}
	
	@RequestMapping("save")
	public ModelAndView save(@RequestParam(value="pageName") String pageName,
			@RequestParam(value="data") JSONArray data,
			@RequestParam(value="scopeObjectType", required = false) String pageParameter) {
		
		Corporation corporation = ThreadLocalUtils.getCurrentCorporation();
		User currentUser = ThreadLocalUtils.getCurrentUser();
		JSONObject jso = saveState(pageName, pageParameter, data, currentUser, corporation);
		return new ModelAndView(new JSONView(jso.toString()));
	}
	
	private JSONObject saveState(String pageName, String pageParameter, JSONArray data, User currentUser, Corporation corporation){
		JSONObject obj = new JSONObject();
		stateService.saveUserStates(data, pageName, pageParameter, currentUser, corporation);
		obj.put("success", true);
		return obj;
			
	}
	
	private JSONObject readState(String pageName, String pageParameter, User currentUser, Corporation corporation){
		JSONObject obj = new JSONObject();
		obj.put("data", stateService.getCurrentUserState(pageName, pageParameter, currentUser, corporation));
		obj.put("success", true);
		return obj;
	}
}
