package com.photo.bas.core.web.interceptor;

import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.EventLog;
import com.photo.bas.core.model.common.Language;
import com.photo.bas.core.model.common.State;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.security.service.CorporationService;
import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.service.common.StateService;
import com.photo.bas.core.service.log.EventLogManager;
import com.photo.bas.core.utils.AppUtils;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;

/**
 * @author FengYu
 *
 */
public class ThreadLocalHandlerInterceptor implements HandlerInterceptor {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private UserService userService;
	
	@Autowired private MessageSource messageSource;
	@Autowired private MessageSource coreAppSetting;
	@Autowired private EventLogManager eventLogManager;
	@Autowired private StateService stateService;
	@Autowired private CorporationService corporationService;
	
	public final static String PREFERRED_LOCALE_KEY = "PREFERRED_LOCALE_KEY";
	public final static String CURRENT_WORK_DATE_KEY = "CURRENT_WORK_DATE_KEY";
	public final static String DEFAULT_OWNER_CORPORATION_KEY = "DEFAULT_OWNER_CORPORATION_KEY";
	public final static String DEFAULT_P_WAREHOUSE = "DEFAULT_P_WAREHOUSE";
	public final static String DEFAULT_S_WAREHOUSE = "DEFAULT_S_WAREHOUSE";
	public final static String DEFAULT_PLANT = "DEFAULT_PLANT";
	public final static String CURRENT_TIME_ZONE = "CURRENT_TIME_ZONE";
	public final static String CURRENT_OPERATOR_CODE = "CURRENT_OPERATOR_CODE";

	@SuppressWarnings("unused")
	private static final String LOGIN_URL = "/app/login";
    
	private static Pattern loginPattern = Pattern.compile("^/app(/emp)?/(login|logout)+");
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();
    	logger.info(request.getRemoteAddr()+ "|" + request.getRemoteUser() + "|request URI = " + requestURI);
    	
    	watchingRequestParameters(request);
    	request.setAttribute("___START_DATE___", new Date());
        if(loginPattern.matcher(requestURI).find()) {
        	if(!ResourceUtils.isInit()) ResourceUtils.initMessageSource(messageSource, coreAppSetting);
//        	String deployPath = request.getRealPath("");
//        	if(Strings.isEmpty(AppUtils.VELOCITY_PATH)) {
//        		AppUtils.VELOCITY_PATH = deployPath;
//        	}
        	return true;
        }
//
        HttpSession session = request.getSession();
        
        // color setup
        String sessionColor = (String) session.getAttribute("color");
        if (Strings.isEmpty(sessionColor)) {
        	session.setAttribute("color", "blue");
        } else {
        	 String requesColor = request.getParameter("color");
        	 if (!Strings.isEmpty(requesColor) && !sessionColor.equals(requesColor)) {
        		 session.setAttribute("color", requesColor);
        	 }
        }
        
        Subject subject = SecurityUtils.getSubject();
        User currentUser = userService.findByLoginName((String) subject.getPrincipal());
        
		Corporation currentCorporation = null;
		String currentCorporationId = "";
        if(currentUser != null) {
			if(currentUser.isSingleCorporation()) {
				currentCorporation = currentUser.getSingleCorporation();
				currentCorporationId = currentCorporation.getId();
			} else {
				currentCorporationId = request.getParameter(ThreadLocalHandlerInterceptor.DEFAULT_OWNER_CORPORATION_KEY);
				if(Strings.isEmpty(currentCorporationId)) {
					currentCorporationId = (String) session.getAttribute(ThreadLocalHandlerInterceptor.DEFAULT_OWNER_CORPORATION_KEY);
				}
				if(Strings.isEmpty(currentCorporationId)) {
					State corporationState = stateService.getUserCorporationState(currentUser);
					if(corporationState != null) currentCorporationId = corporationState.getValue();
				}
	
				if(!Strings.isEmpty(currentCorporationId)) {
					session.setAttribute(ThreadLocalHandlerInterceptor.DEFAULT_OWNER_CORPORATION_KEY, currentCorporationId);
					currentCorporation = corporationService.get(currentCorporationId);
				}
				if(currentCorporation != null && currentUser != null && !currentUser.isAvailableCorporation(currentCorporation)) {
					currentCorporationId = "";
					currentCorporation = null;
				}
			}
			if(currentCorporation != null && !User.SUPER_ADMIN_NAME.equals(currentUser.getLoginName())) {	
				stateService.saveUserCorporationState(currentCorporation.getId(), currentUser);
			}
		}

		String strLocale = request.getParameter("lang");
		Locale preferredLocale = null;
		
        if (!Strings.isEmpty(strLocale)) {
        	preferredLocale = Language.fromCode(strLocale).getLocale();
        } else if(session.getAttribute(ThreadLocalHandlerInterceptor.PREFERRED_LOCALE_KEY) != null) {
        	preferredLocale = (Locale) session.getAttribute(ThreadLocalHandlerInterceptor.PREFERRED_LOCALE_KEY);
        }  else {
        	preferredLocale = Locale.CHINESE;
        }
//        else if(currentUser != null){
//        	if(currentUser.getLanguage() ==  null) {
//        		preferredLocale = Locale.ENGLISH;
//        		logger.info("!!!!!!!!!!!!---------!!!!!!!!!! Alert : currentUser.getLanguage() ==  null");
//        	} else{
//        		preferredLocale = currentUser.getLanguage().getLocale();
//        	}
//        }
        if (preferredLocale != null) {
            LocaleContextHolder.setLocale(preferredLocale);
            Config.set(session, Config.FMT_LOCALE, preferredLocale);
            session.setAttribute(ThreadLocalHandlerInterceptor.PREFERRED_LOCALE_KEY, preferredLocale);
            session.setAttribute("locale", preferredLocale.getLanguage());
        }
        Date currentWorkDate = null;
        if (session != null) {
        	currentWorkDate = (Date) session.getAttribute(ThreadLocalHandlerInterceptor.CURRENT_WORK_DATE_KEY);
            if (currentWorkDate == null) currentWorkDate = new Date();
        }
        if(currentWorkDate != null) session.setAttribute(ThreadLocalHandlerInterceptor.CURRENT_WORK_DATE_KEY, currentWorkDate);
		
		TimeZone timeZone = (TimeZone) session.getAttribute(ThreadLocalHandlerInterceptor.CURRENT_TIME_ZONE);
		if(request.getParameter("timeZone") != null) {
			timeZone = TimeZone.getTimeZone(request.getParameter("timeZone"));
			session.setAttribute(ThreadLocalHandlerInterceptor.CURRENT_TIME_ZONE, timeZone);
		}
		ThreadLocalUtils.initThreadLocal(currentUser, currentCorporation, preferredLocale, currentWorkDate, timeZone);
		
		if(AppUtils.APP_NAME == null) AppUtils.APP_NAME =  request.getSession().getServletContext().getInitParameter("project.name");
		if(AppUtils.APP_ENVIRONMENT == null) AppUtils.APP_ENVIRONMENT =  request.getSession().getServletContext().getInitParameter("spring.profiles.active");
		if(AppUtils.APP_PATH == null) AppUtils.APP_PATH = request.getSession().getServletContext().getRealPath("/");
		
    	return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//		logRequest(request);
        LocaleContextHolder.setLocaleContext(null);
	}


	@SuppressWarnings("rawtypes")
	private void logRequest(HttpServletRequest request) {
		int batch = ThreadLocalUtils.DEFAULT_LOGS_BATCH;
		String url = request.getRequestURI();
		if(batch <= 0 || !Strings.isEmpty(url) && (url.indexOf("/state/") > 0 || url.indexOf("/eventLog/list/json") > 0)) {
			return;
		}
		EventLog log = new EventLog();
		User user = getCurrentUser(request.getSession(false));
		log.setUserName(user != null ? user.getUsername():"");
		log.setSessionId(request.getSession().getId());
		log.setUrl(request.getRequestURI());
		log.setRemoteAddr(request.getRemoteAddr());
		log.setRequestDate((Date)request.getAttribute("___START_DATE___"));
		long consumption = (new Date().getTime() - log.getRequestDate().getTime());
		log.setConsumption(consumption);
		
		Enumeration enumeration = request.getParameterNames();
		JSONObject jo = new JSONObject();
		while(enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			jo.put(name, request.getParameter(name));
		}
		User login = ThreadLocalUtils.getLoginUser();
		if(user != null && login != null 
				&& !login.getId().equals(user.getId())) {
			jo.put("loginUser", login.getUsername());
		}
		log.setParams(jo.toString());
//		eventLogManager.save(log);
		eventLogManager.addLog(log, batch);
	}

	private User getCurrentUser(HttpSession session) {
		User user = ThreadLocalUtils.getLoginUser();
		if(user != null && "admin".equals(user.getLoginName())) {
			if(session != null && session.getAttribute("loginAs") != null) {
				user = (User)session.getAttribute("loginAs");
			}
		}
		return user;
	}
	
	@SuppressWarnings("rawtypes")
	private void watchingRequestParameters(HttpServletRequest request) {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>");
		Enumeration enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			logger.info(name + "  =:=  " + request.getParameter(name));
		}
		logger.info("<<<<<<<<<<<<<<<<<<<<<<<<");
	}

}
