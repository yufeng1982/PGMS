/**
 * 
 */
package com.photo.pgm.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.photo.bas.core.model.common.Position;
import com.photo.bas.core.service.common.PositionService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.vo.common.PositionQueryInfo;
import com.photo.bas.core.web.controller.entity.AbsMaintenanceController;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/hr/position")
public class PositionListController extends AbsMaintenanceController<Position, PageInfo<Position>> {

	@Autowired private PositionService positionService;
	
	@Override
	protected PositionService getEntityService() {
		return positionService;
	}

	@Override
	public String getPropUrl() {
		return null;
	}

	@Override
	public boolean isPaged() {
		return true;
	}
	
	@Override
	@RequiresPermissions("position:list")
	@RequestMapping("list")
	public String list(ModelMap modelMap, HttpServletRequest request, @ModelAttribute("model") Position position,
			@RequestParam(value="parameterNames", required=false) String parameterNames) {
		return super.listCommon(modelMap, request, position, parameterNames);
	}

	@Override
	@RequiresPermissions("position:save")
	@RequestMapping("apply")
	public String apply(@RequestParam(value = "modifiedRecords") JSONArray modifiedRecords, 
			@RequestParam(value = "lineToDelete") String lineToDelete,
			@RequestParam(value = "parameterNames", required=false) String parameterNames, 
			@ModelAttribute("model") Position position, ModelMap modelMap,
			HttpServletRequest request) {
		return super.applyCommon(modifiedRecords, lineToDelete, parameterNames, position, modelMap, request);
	}

	@Override
	public PositionQueryInfo newPagedQueryInfo() {
		return new PositionQueryInfo();
	}
	
	@Override
	public boolean isInfinite() {
		return false;
	}
}
