/**
 * 
 */
package com.photo.bas.core.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author FengYu
 *
 */
public class SpringWebApplicationContextUtils {

	public static Object getApplicationContextBean(String beanName){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		return wac.getBean(beanName);
	}
}
