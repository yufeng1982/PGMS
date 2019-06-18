/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.security.service.RoleService;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.enums.RepairApproveType;
import com.photo.pgm.core.model.RepairApproveSetup;
import com.photo.pgm.core.service.RepairApproveSetupService;
import com.photo.pgm.core.vo.RepairApproveSetupQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value="/pgm/project/repairApproveSetup/list")
public class RepairApproveSetupListController extends AbsQueryPagedListController<RepairApproveSetup, RepairApproveSetupQueryInfo> {

	private final static String PAGE_LIST_PATH = "project/repairApproveSetup/repairApproveSetups";
	
	@Autowired private RepairApproveSetupService repairApproveSetupService;
	@Autowired private RoleService roleService;
	
	@RequestMapping("smalls")
	@RequiresPermissions("repairApproveSetup:list")
	public String showSmall(ModelMap modelMap){
		modelMap.addAttribute("type", "Small");
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("generals")
	@RequiresPermissions("repairApproveSetup:list")
	public String showGeneral(ModelMap modelMap){
		modelMap.addAttribute("type", "General");
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("settles")
	@RequiresPermissions("repairApproveSetup:list")
	public String showSettle(ModelMap modelMap){
		modelMap.addAttribute("type", "SettleAccount");
		return PAGE_LIST_PATH;
	}
	
	@RequestMapping("json")
	public ModelAndView json4Samll(@ModelAttribute("pageQueryInfo") RepairApproveSetupQueryInfo queryInfo, @RequestParam RepairApproveType pat){
		List<RepairApproveSetup> pasList =  repairApproveSetupService.findByPat(pat);
		return toJSONView(pasList);
	}
	
	@RequestMapping("save")
	@RequiresPermissions("repairApproveSetup:save")
	@ResponseBody
	public String save(@RequestParam JSONArray modifiedRecords) {
		for (int i = 0; i < modifiedRecords.length(); i++) {
			JSONObject jo = modifiedRecords.getJSONObject(i);
			RepairApproveSetup pas = repairApproveSetupService.get(jo.getString("id"));
			if (pas != null) {
				pas.setUsers(jo.getString("users"));
				pas.setUsersText(jo.getString("usersText"));
				if (!Strings.isEmpty(jo.getString("judgeAmount"))) {
					pas.setJudgeAmount(jo.getDouble("judgeAmount"));
				}
				Role role = roleService.get(jo.getString("role"));
				pas.setRole(role);
				repairApproveSetupService.save(pas);
			}
		}
		return "true";
	}
	
	@Override
	public RepairApproveSetupQueryInfo newPagedQueryInfo() {
		return new RepairApproveSetupQueryInfo();
	}

	@Override
	protected RepairApproveSetupService getEntityService() {
		return repairApproveSetupService;
	}

	

}
