/**
 * 
 */
package com.photo.pgm.core.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.photo.pgm.core.enums.AdjustType;
import com.photo.pgm.core.enums.ApproveResult;
import com.photo.pgm.core.enums.BreakdownReason;
import com.photo.pgm.core.enums.BudgetType;
import com.photo.pgm.core.enums.RepairStatus;
import com.photo.pgm.core.enums.RepairType;

/**
 * @author FengYu
 *
 */
public class ReportUtils {
	
	public static String getBudgetType(String args) {
		return BudgetType.valueOf(args).getText();
	}
	
	public static String getRepairType(String args){
		return RepairType.valueOf(args).getText();
	}
	
	public static String getRepairStatus(String args) {
		return RepairStatus.valueOf(args).getText();
	}
	
	public static String getBreakdown(String args) {
		return BreakdownReason.valueOf(args).getText();
	}
	
	public static String getAdjusttype(String args) {
		return AdjustType.valueOf(args).getText();
	}
	
	public static String getApproveResult(String args) {
		return ApproveResult.valueOf(args).getText();
	}
	
	
	private static Object getApplicationContextBean(String beanName){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		return wac.getBean(beanName);
	}
}
