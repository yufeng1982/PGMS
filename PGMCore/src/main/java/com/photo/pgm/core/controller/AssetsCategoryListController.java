/**
 * 
 */
package com.photo.pgm.core.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.AssetsCategory;
import com.photo.pgm.core.service.AssetsCategoryService;
import com.photo.pgm.core.vo.AssetsCategoryQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/assetsCategory/list")
public class AssetsCategoryListController extends AbsQueryPagedListController<AssetsCategory, AssetsCategoryQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/assetsCategory/assetsCategorys";
	
	@Autowired private AssetsCategoryService assetsCategoryService;
	
	@RequestMapping
	@RequiresPermissions("assetsCategory:list")
	public String show(ModelMap modelMap){
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") AssetsCategoryQueryInfo queryInfo){
		String jo = getEntityService().findDatas(queryInfo);
		return toJSONView(jo);
	}
	
	@RequestMapping("codeCheck")
	@ResponseBody
	public String codeCheck(@RequestParam String code){
		AssetsCategory ac = getEntityService().findByCodeAndCorporation(code, ThreadLocalUtils.getCurrentCorporation());
		if (ac != null) {
			return "true";
		} 
		return "false";
	}
	
	@Override
	protected AssetsCategoryService getEntityService() {
		return assetsCategoryService;
	}


	@Override
	public AssetsCategoryQueryInfo newPagedQueryInfo() {
		return new AssetsCategoryQueryInfo();
	}
	

}
