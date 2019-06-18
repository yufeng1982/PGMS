/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.ArrayList;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.UserType;
import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.PetrolStation;
import com.photo.pgm.core.service.PetrolStationService;
import com.photo.pgm.core.vo.PetrolStationQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/petrolStation/list")
public class PetrolStationListController extends AbsQueryPagedListController<PetrolStation, PetrolStationQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/petrolStation/petrolStations";
	
	@Autowired private PetrolStationService petrolStationService;
	
	
	@RequestMapping
	@RequiresPermissions("petrolStation:list")
	public String show(ModelMap modelMap){
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") PetrolStationQueryInfo queryInfo){
		Sort sort = new Sort(Direction.ASC, "project.code");
		queryInfo.setSort(sort);
		Page<PetrolStation> list = getEntityService().findLists(queryInfo);
		return toJSONView(list);
	}
	
	@RequestMapping("oilJson")
	public ModelAndView oilJson(@ModelAttribute("pageQueryInfo") PetrolStationQueryInfo queryInfo){
		if (ThreadLocalUtils.getCurrentUser().getUserType().equals(UserType.Org)) {
			queryInfo.setSf_IN_id(null);
			Page<PetrolStation> list = getEntityService().findLists(queryInfo);
			return toJSONView(list);
		} 
		if (queryInfo.getSf_IN_id() == null || queryInfo.getSf_IN_id().isEmpty()) {
			Page<PetrolStation> list = new PageImpl<PetrolStation>(new ArrayList<PetrolStation>());
			return toJSONView(list);
		}
		Page<PetrolStation> list = getEntityService().findLists(queryInfo);
		return toJSONView(list);
	}
	
	@RequestMapping("codeCheck")
	@ResponseBody
	public String codeCheck(@RequestParam String code){
		PetrolStation petrolStation = getEntityService().findByCode(code, ThreadLocalUtils.getCurrentCorporation());
		if (petrolStation != null) {
			return "true";
		} 
		return "false";
	}
	
	@RequestMapping("sapcodeCheck")
	@ResponseBody
	public String sapcodeCheck(@RequestParam String sapCode){
		PetrolStation petrolStation = getEntityService().findBySapCode(sapCode, ThreadLocalUtils.getCurrentCorporation());
		if (petrolStation != null) {
			return "true";
		} 
		return "false";
	}
	
	@RequestMapping("cncodeCheck")
	@ResponseBody
	public String cncodeCheck(@RequestParam String cnCode){
		PetrolStation petrolStation = getEntityService().findByCnCode(cnCode, ThreadLocalUtils.getCurrentCorporation());
		if (petrolStation != null) {
			return "true";
		} 
		return "false";
	}
	
	@Override
	public PetrolStationQueryInfo newPagedQueryInfo() {
		return new PetrolStationQueryInfo();
	}

	@Override
	protected PetrolStationService getEntityService() {
		return petrolStationService;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC0");
	}
}
