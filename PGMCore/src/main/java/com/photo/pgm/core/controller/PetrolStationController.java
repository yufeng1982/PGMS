/**
 * 
 */
package com.photo.pgm.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.web.controller.entity.AbsCodeEntityController;
import com.photo.pgm.core.model.PetrolStation;
import com.photo.pgm.core.service.PetrolStationService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/petrolStation/form")
public class PetrolStationController extends AbsCodeEntityController<PetrolStation> {

	private final static String PAGE_FORM_PATH = "project/petrolStation/petrolStation";
	private final static String PATH = "project/petrolStation/form/";
	
	@Autowired private PetrolStationService petrolStationService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("project:show")
	public String showProject(@ModelAttribute("entity") PetrolStation petrolStation, ModelMap modelMap) {
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping("{id}/apply")
	@RequiresPermissions("project:apply")
	public ModelAndView save(HttpServletRequest request, @ModelAttribute("entity") PetrolStation petrolStation, ModelMap modelMap) {
		savePetrolStation(petrolStation);
		return redirectTo(PATH + petrolStation.getId() + "/show", modelMap);
	}

	@RequestMapping("{id}/ok")
	@RequiresPermissions("project:ok")
	public ModelAndView ok(HttpServletRequest request, @ModelAttribute("entity") PetrolStation petrolStation, ModelMap modelMap) {
		if(savePetrolStation(petrolStation)){
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(petrolStation.getId()) ? NEW_ENTITY_ID : petrolStation.getId()) + "/show", modelMap);
	}
	
	private boolean savePetrolStation(PetrolStation petrolStation) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			getEntityService().save(petrolStation);
		}
		return isSucceed;
	}

	@Override
	protected PetrolStationService getEntityService() {
		return petrolStationService;
	}

	@Override
	protected PetrolStation newInstance(WebRequest request) {
		return new PetrolStation();
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC0");
	}
	
}
