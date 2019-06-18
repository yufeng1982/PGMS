/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.photo.pgm.core.enums.BudgetType;
import com.photo.pgm.core.model.YearBudgets;
import com.photo.pgm.core.service.YearBudgetsService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/yearBudgets/form")
public class YearBudgetsController extends AbsCodeEntityController<YearBudgets> {

	private final static String PAGE_FORM_PATH = "project/yearBudgets/yearBudget";
	private final static String PATH = "project/yearBudgets/form/";
	
	@Autowired private YearBudgetsService yearBudgetsService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("project:show")
	public String showProject(@ModelAttribute("entity") YearBudgets yearBudgets, ModelMap modelMap) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int startYear = year - 10;
		int endYear = year + 10;
		List<Map<String, String>> yearList = new ArrayList<Map<String,String>>();
		for (int i = startYear; i <= endYear; i++) {
			Map<String, String> yearMap = new HashMap<String, String>();
			yearMap.put("text", String.valueOf(i));
			yearMap.put("name", String.valueOf(i));
			yearList.add(yearMap);
		}
		modelMap.addAttribute("years", yearList);
		modelMap.addAttribute("budgetType", BudgetType.getBudgetType());
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping("{id}/apply")
	@RequiresPermissions("project:apply")
	public ModelAndView save(HttpServletRequest request, @ModelAttribute("entity") YearBudgets yearBudgets, ModelMap modelMap) {
		saveYearBudgets(yearBudgets);
		return redirectTo(PATH + yearBudgets.getId() + "/show", modelMap);
	}

	@RequestMapping("{id}/ok")
	@RequiresPermissions("project:ok")
	public ModelAndView ok(HttpServletRequest request, @ModelAttribute("entity") YearBudgets yearBudgets, ModelMap modelMap) {
		if(saveYearBudgets(yearBudgets)){
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(yearBudgets.getId()) ? NEW_ENTITY_ID : yearBudgets.getId()) + "/show", modelMap);
	}
	
	private boolean saveYearBudgets(YearBudgets yearBudgets) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			getEntityService().save(yearBudgets);
		}
		return isSucceed;
	}

	@Override
	protected YearBudgetsService getEntityService() {
		return yearBudgetsService;
	}

	@Override
	protected YearBudgets newInstance(WebRequest request) {
		return new YearBudgets();
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC5");
	}
	
}
