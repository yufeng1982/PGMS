/**
 * 
 */
package com.photo.pgm.core.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.Cooperation;
import com.photo.pgm.core.service.CooperationService;
import com.photo.pgm.core.vo.CooperationQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/cooperation/list")
public class CooperationListController extends AbsQueryPagedListController<Cooperation, CooperationQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/cooperation/cooperations";
	
	@Autowired private CooperationService cooperationService;
	
	@RequestMapping
	@RequiresPermissions("cooperation:list")
	public String show(ModelMap modelMap){
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") CooperationQueryInfo queryInfo){
		Page<Cooperation> list = getEntityService().findLists(queryInfo);
		return toJSONView(list);
	}
	
	@RequestMapping("codeCheck")
	@ResponseBody
	public String codeCheck(@RequestParam String code){
		Cooperation cooperation = getEntityService().findByCodeAndCorporation(code, ThreadLocalUtils.getCurrentCorporation());
		if (cooperation != null) {
			return "true";
		} 
		return "false";
	}
	
	@Override
	public CooperationQueryInfo newPagedQueryInfo() {
		return new CooperationQueryInfo();
	}

	@Override
	protected CooperationService getEntityService() {
		return cooperationService;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("VD1");
	}
	
}
