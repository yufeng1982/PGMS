package com.photo.bas.core.web.controller.ui;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.FunctionNodeType;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.security.service.CorporationService;
import com.photo.bas.core.security.service.FunctionNodeManager;
import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.service.common.StateService;
import com.photo.bas.core.service.entity.AbsService;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsController;

/**
 * @author FengYu
 */
@Controller
@RequestMapping("/*/ui")
public class MainFrameController extends AbsController<Object> {
	
	@Autowired FunctionNodeManager functionNodeManager;
	@Autowired UserService userService;
	@Autowired private CorporationService corporationService;
	@Autowired private StateService stateService;

	@SuppressWarnings("rawtypes")
	@Override
	protected AbsService getEntityService() {
		return null;
	}

	@RequestMapping("mainFrame")
	public String mainFrame(ModelMap modelMap, WebRequest request, HttpServletRequest r, HttpServletResponse response) {
		
		User currentUser = ThreadLocalUtils.getCurrentUser();
		Iterable<Corporation> cs = currentUser.getAvailableCorporations();
		JSONArray ja = new JSONArray();
		for (Corporation c : cs) {
			JSONObject v = new JSONObject();
			v.put("id", c.getId());
			v.put("shortName", c.getShortName());
			ja.put(v);
		}
		try {
			modelMap.addAttribute("cs", java.net.URLEncoder.encode(ja.toString(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("currentUser", currentUser);
		modelMap.addAttribute("isSuperAdmin", User.SUPER_ADMIN_NAME.equals(currentUser.getLoginName()));
		Corporation corporation = ThreadLocalUtils.getCurrentCorporation();
		modelMap.addAttribute("currentCorporation", corporation);
		modelMap.addAttribute("workDate", FormatUtils.formatDate(ThreadLocalUtils.getCurrentWorkDate()));
		modelMap.addAttribute("language", ThreadLocalUtils.getCurrentLocale().getLanguage());
		
		modelMap.addAttribute("rootTree", getRootLevelMenuJSON().toString());
		return "ui/main";
	}
	
	@RequestMapping(value = "resetPassword")
	@ResponseBody
	public String resetPassword(@RequestParam String userId, @RequestParam("plainPassword") String plainPassword,ModelMap modelMap){
		User user =userService.get(userId);
		if(user!=null){
			user.setPlainPassword(plainPassword);
			userService.saveUserAndEncrypt(user);
			return "true";
		} 
		return "false";
	}
	
	private JSONArray getRootLevelMenuJSON() {
		List<FunctionNodeType> functionTypeList = functionNodeManager.findValidFunctionNodeTypesForCurrentUser();
		JSONArray menuPanelItemsArray = new JSONArray();
		for (FunctionNodeType eachType : functionTypeList) {
			menuPanelItemsArray.put(eachType.toJson());
		}
		return menuPanelItemsArray;
	}

}
