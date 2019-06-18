/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.Calendar;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.photo.bas.core.utils.JsonUtils;
import com.photo.bas.core.web.controller.entity.AbsController;
import com.photo.pgm.core.enums.RepairType;
import com.photo.pgm.core.enums.ReportType;
import com.photo.pgm.core.service.ReportsService;

/**
 * @author FengYu
 *
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/pgm/report/")
public class ReportsController extends AbsController {

	@Autowired private ReportsService reportsService;
	
	private final static String PAGE_PR = "project/report/projectReport";
	private final static String PAGE_PA = "project/report/projectAnalysis";
	private final static String PAGE_RA = "project/report/repairAnalysis";
	
	@RequestMapping("projectReport")
	public String projectReport(ModelMap modelMap) {
		buildModelMaps(modelMap);
		return PAGE_PR;
	}
	
	@RequestMapping("projectAnalysis")
	public String projectAnalysis(ModelMap modelMap) {
		return PAGE_PA;
	}
	
	@RequestMapping("repairAnalysis")
	public String repairAnalysis(ModelMap modelMap){
		modelMap.addAttribute("rtList", JsonUtils.buildEnmuCoboxData(RepairType.getRepairType()));
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int startYear = year - 10;
		int endYear = year + 10;
		JSONArray ja = new JSONArray();
		for (int i = startYear; i <= endYear; i++) {
			JSONArray jso = new JSONArray();
			jso.put(String.valueOf(i));
			jso.put(String.valueOf(i));
			ja.put(jso);
		}
		modelMap.addAttribute("years", ja);
		modelMap.addAttribute("reportType", JsonUtils.buildEnmuCoboxData(ReportType.getReportType()));
		return PAGE_RA;
	}
	
	private void buildModelMaps(ModelMap modelMap) {
		JSONArray ja = new JSONArray();
		JSONArray jso = new JSONArray();
		jso.put(1);
		jso.put(1);
		ja.put(jso);
		jso = new JSONArray();
		jso.put(2);
		jso.put(2);
		ja.put(jso);
		jso = new JSONArray();
		jso.put(3);
		jso.put(3);
		ja.put(jso);
		modelMap.addAttribute("alList", ja);
	}
	
	
	@Override
	protected ReportsService getEntityService() {
		return reportsService;
	}

}
