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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.web.controller.entity.AbsCodeEntityController;
import com.photo.pgm.core.model.AssetsCategory;
import com.photo.pgm.core.service.AssetsCategoryService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/assetsCategory/form")
public class AssetsCategoryController extends AbsCodeEntityController<AssetsCategory> {

	private final static String PAGE_FORM_PATH = "project/assetsCategory/assetsCategory";
	private final static String PATH = "project/assetsCategory/form/";
	
	@Autowired private AssetsCategoryService assetsCategoryService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("assetsCategory:show")
	public String show(@ModelAttribute("entity") AssetsCategory assetsCategory, ModelMap modelMap) {
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping("{id}/apply")
	@RequiresPermissions("assetsCategory:apply")
	public ModelAndView save(HttpServletRequest request, 
								@ModelAttribute("entity") AssetsCategory assetsCategory, 
								@RequestParam(required = false) Boolean parentIsChange, 
								@RequestParam(required = false) AssetsCategory oldParent,
								@RequestParam(required = false) String oldName,
							 	ModelMap modelMap) {
		saveAssetsCategory(assetsCategory, parentIsChange, oldParent, oldName);
		
		return redirectTo(PATH + assetsCategory.getId() + "/show", modelMap);
	}

	@RequestMapping("{id}/ok")
	@RequiresPermissions("assetsCategory:ok")
	public ModelAndView ok(HttpServletRequest request, 
								@ModelAttribute("entity") AssetsCategory assetsCategory, 
								@RequestParam(required = false) Boolean parentIsChange, 
								@RequestParam(required = false) AssetsCategory oldParent,
								@RequestParam(required = false) String oldName,
							 	ModelMap modelMap) {
		if(saveAssetsCategory(assetsCategory, parentIsChange, oldParent, oldName)){
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(assetsCategory.getId()) ? NEW_ENTITY_ID : assetsCategory.getId()) + "/show", modelMap);
	}
	
	
	
	private boolean saveAssetsCategory(AssetsCategory assetsCategory, Boolean parentIsChange, AssetsCategory oldParent, String oldName) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			getEntityService().saveAssetsCategory(assetsCategory, parentIsChange, oldParent, oldName);
		}
		return isSucceed;
	}

	@Override
	protected AssetsCategoryService getEntityService() {
		return assetsCategoryService;
	}

	@Override
	protected AssetsCategory newInstance(WebRequest request) {
		return new AssetsCategory();
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("ZC2");
	}
	
}
