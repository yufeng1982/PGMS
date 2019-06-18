/**
 * 
 */
package com.photo.bas.core.web.filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author FengYu
 *
 */
public class OpenSessionInViewFilter extends org.springframework.orm.hibernate3.support.OpenSessionInViewFilter {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static final String APP_SERVLET = "/app";
	private static final String FLEX_SERVLET = "/flex";
	private static final String SECURITY_SERVLET = "/j_spring_security_check";

	@Override
	protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
		String fullPath = request.getServletPath();
		if(APP_SERVLET.equals(fullPath) || FLEX_SERVLET.equals(fullPath) || SECURITY_SERVLET.equals(fullPath)) {
			return false;
		}
		return true;
	}
}
