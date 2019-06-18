/**
 * 
 */
package com.photo.pgm.core.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.OurSideCorporation;
import com.photo.pgm.core.service.OurSideCorporationService;
import com.photo.pgm.core.vo.OurSideCorporationQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/ourSideCorporation/list")
public class OurSideCorporationListController extends AbsQueryPagedListController<OurSideCorporation, OurSideCorporationQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/ourSideCorporation/ourSideCorporations";
	
	@Autowired private OurSideCorporationService ourSideCorporationService;
	
	@RequestMapping
	@RequiresPermissions("ourSideCorporation:list")
	public String show(ModelMap modelMap){
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") OurSideCorporationQueryInfo queryInfo){
		Page<OurSideCorporation> list = getEntityService().findLists(queryInfo);
		return toJSONView(list);
	}

	@RequestMapping(value = "apply")
	@RequiresPermissions("ourSideCorporation:apply")
	public String apply(@RequestParam(value = "modifiedRecords") JSONArray modifiedRecords) {
		for (int i = 0; i < modifiedRecords.length(); i++) {
			Corporation corporation = ThreadLocalUtils.getCurrentCorporation();
			JSONObject jo = modifiedRecords.getJSONObject(i);
			OurSideCorporation ourSideCorporation = getEntityService().buildGroup(jo, corporation);
			getEntityService().save(ourSideCorporation);
		}
		return "redirect:/app/pgm/project/ourSideCorporation/list";
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public void delete(@RequestParam String id) {
		getEntityService().delete(id);
	}
	
	@Override
	protected OurSideCorporationService getEntityService() {
		return ourSideCorporationService;
	}
	
	@Override
	public OurSideCorporationQueryInfo newPagedQueryInfo() {
		return new OurSideCorporationQueryInfo();
	}

}
