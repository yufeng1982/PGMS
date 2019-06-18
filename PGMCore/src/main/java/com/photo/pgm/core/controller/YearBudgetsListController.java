/**
 * 
 */
package com.photo.pgm.core.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.YearBudgets;
import com.photo.pgm.core.service.YearBudgetsService;
import com.photo.pgm.core.vo.YearBudgetsQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/yearBudgets/list")
public class YearBudgetsListController extends AbsQueryPagedListController<YearBudgets, YearBudgetsQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/yearBudgets/yearBudgets";
	
	@Autowired private YearBudgetsService yearBudgetsService;
	
	
	@RequestMapping
	@RequiresPermissions("yearBudgets:list")
	public String show(ModelMap modelMap){
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") YearBudgetsQueryInfo queryInfo){
		Sort sort = new Sort(Direction.ASC, "years");
		queryInfo.setSort(sort);
		Page<YearBudgets> list = getEntityService().findLists(queryInfo);
		return toJSONView(list);
	}
	
	@RequestMapping("yearCheck")
	@ResponseBody
	public String yearCheck(@RequestParam Integer years){
		YearBudgets yb = getEntityService().findByYears(years);
		if (yb != null) {
			return "true";
		} 
		return "false";
	}
	
	@Override
	protected YearBudgetsService getEntityService() {
		return yearBudgetsService;
	}

	@Override
	public YearBudgetsQueryInfo newPagedQueryInfo() {
		Sort sort = new Sort(Direction.ASC, "years");
		YearBudgetsQueryInfo queryInfo = new YearBudgetsQueryInfo();
		queryInfo.setSort(sort);
		return queryInfo;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC5");
	}
}
