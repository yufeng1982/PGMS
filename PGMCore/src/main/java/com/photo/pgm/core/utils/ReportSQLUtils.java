/**
 * 
 */
package com.photo.pgm.core.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.photo.bas.core.utils.Strings;

/**
 * @author FengYu
 *
 */
public class ReportSQLUtils {
	
	public static String getProjectSQL(String ...args) {
		String city = args[0];
		String dateFrom = args[1];
		String dateTo = args[2];
		String hzWay = args[3];
		String salesFrom = args[4];
		String salesTo = args[5];
		
		StringBuffer sql = new StringBuffer();
		sql.append("where p.active = 'T' ");
		if (!Strings.isEmpty(city)) {
			sql.append(" and p.pct like '%" + city +"%'");
		}
		if (!Strings.isEmpty(dateFrom)) {
			sql.append(" and to_char(p.add_date,'yyyy-MM-dd') >= '" + dateFrom +"'");
		}
		if (!Strings.isEmpty(dateTo)) {
			sql.append(" and to_char(p.add_date,'yyyy-MM-dd') <= '" + dateTo +"'");
		}
		if (!Strings.isEmpty(hzWay)) {
			sql.append(" and p.hz_way = '" + hzWay +"'");
		}
		if (!Strings.isEmpty(salesFrom)) {
			sql.append(" and p.sales_forecast >= " + salesFrom);
		}
		if (!Strings.isEmpty(salesTo)) {
			sql.append(" and p.sales_forecast <= " + salesTo );
		}
		return sql.toString();
	}
	
	public static String getProjectAnalysisConditions(String projectId) {
		StringBuffer sql = new StringBuffer();
		if (!Strings.isEmpty(projectId)) {
			sql.append(" and p.id='").append(projectId).append("'");
		}
		return sql.toString();
	}
	
	public static String getProjectConditions(String projectId, String level, String assetCategory) {
		StringBuffer sql = new StringBuffer();
		if (!Strings.isEmpty(projectId)) {
			sql.append(" and c.petrol_station='").append(projectId).append("'");
		}
		if (Strings.isEmpty(level)) {
			sql.append(" and ac.level=2");
		} else {
			sql.append(" and ac.level=").append(level);
		}
		if (!Strings.isEmpty(assetCategory)) {
			sql.append(" and ac.id='").append(assetCategory).append("'");
		}
		return sql.toString();
	}
	
	public static String getRepairAnalysisConditions(String repairType, String years) {
		StringBuffer sql = new StringBuffer();
		if (!Strings.isEmpty(repairType) || !Strings.isEmpty(years)) {
			sql.append("where ");
		} else {
			return sql.toString();
		}
		if (!Strings.isEmpty(repairType) && !Strings.isEmpty(years)) {
			sql.append(" repair_report.repair_type='").append(repairType).append("'");
			sql.append(" and repair_report.year='").append(years).append("'");
		} else if (!Strings.isEmpty(repairType)){
			sql.append(" repair_report.repair_type='").append(repairType).append("'");
		} else if (!Strings.isEmpty(years)) {
			sql.append(" repair_report.year='").append(years).append("'");
		}
		return sql.toString();
	}
	
	protected static Object getApplicationContextBean(String beanName){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		return wac.getBean(beanName);
	}
}
