package com.photo.bas.core.web.controller.entity;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.photo.bas.core.model.common.Resource;
import com.photo.bas.core.model.entity.IEntity;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.security.service.AppResourceService;
import com.photo.bas.core.security.service.FunctionNodeManager;
import com.photo.bas.core.service.entity.AbsService;
import com.photo.bas.core.service.entity.SourceEntityService;
import com.photo.bas.core.utils.AppUtils;
import com.photo.bas.core.utils.ClassUtils;
import com.photo.bas.core.utils.EntityReflectionUtils;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.vo.log.SystemInfoLog;
import com.photo.bas.core.web.convert.ERPCustomDateEditor;
import com.photo.bas.core.web.view.JSONView;
import com.photo.bas.core.web.view.ListExcelView;
import com.photo.bas.core.web.view.StreamView;

/**
 * @author FengYu
 */
public abstract class AbsController<T> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private String cName = ClassUtils.getShortName(getClass());
	
	@Autowired protected SourceEntityService sourceEntityService;
	@Autowired protected FunctionNodeManager functionNodeManager;
	@Autowired protected AppResourceService appResourceService;
	
	protected final static String APP_NAME = "APP_NAME";
	protected final static String APP_ENVIRONMENT = "APP_ENVIRONMENT";
	public final static String CONTROLLED_RESOURCES = "CONTROLLED_RESOURCES";
	protected final static String CONTROLLER_NAME = "__CONTROLLER_NAME__";
//	protected final static String PAGE_PREFERENCE_STATE = "__page_preference_state__";
	protected final static String SCOPE_OBJECT_TYPE = "_SCOPE_OBJECT_TYPE_";
	protected final static String NEW_ENTITY_ID = "NEW";
	protected final static String CLONE_ENTITY_ID = "CLONE";
	
	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public AbsController() {
		super();
		this.entityClass = EntityReflectionUtils.getSuperClassGenricType(getClass());
	}
	
	@Autowired
	@Qualifier("messageSource") protected MessageSource messageSource;

	@SuppressWarnings("rawtypes")
	protected abstract AbsService getEntityService();
	
	protected String getMessage(String key) {
		String returnValue = "???"+key+"???";
		if(messageSource != null) {
			try {
				returnValue = messageSource.getMessage(key, new Object[0], ThreadLocalUtils.getCurrentLocale());
			} catch (NoSuchMessageException e) {
				logger.error(e.getMessage());
			}
		}
		return returnValue;
	}
	
	protected String getMessage(String key, Object[] objects) {
		String returnValue = "???"+key+"???";
		if(messageSource != null) {
			try {
				returnValue = messageSource.getMessage(key, objects, ThreadLocalUtils.getCurrentLocale());
			} catch (NoSuchMessageException e) {
				logger.error(e.getMessage());
			}
		}
		return returnValue;
	}
	
	@ModelAttribute(AbsController.CONTROLLER_NAME)
    public String getControllerName() {
		//logger.debug(getClass().getName() + " being called ~!");
    	return cName;
    }
	
	protected ModelAndView toJSONView(JSONObject jsonObject) {
		return toJSONView(jsonObject.toString());
	}
	
	protected ModelAndView toJSONView(String jsonString) {
		return new ModelAndView(new JSONView(jsonString));
	}
	
	protected ModelAndView toStreamView(Resource resource) {
		return new ModelAndView(new StreamView(resource));
	}
	
//	@ModelAttribute(AbsController.PAGE_PREFERENCE_STATE)
//	protected String initPageState(@RequestParam(value="gridUrl", required=false) String gridUrl) {
//    	return stateManager.getCurrentUserState(cName, gridUrl, ThreadLocalUtils.getCurrentUser(), ThreadLocalUtils.getCurrentOwnerCorporation()).toString();
//    }
	
	@ModelAttribute(AbsController.APP_NAME)
	protected String getAppName() {
		return AppUtils.APP_NAME;
    }
	@ModelAttribute(AbsController.APP_ENVIRONMENT)
	protected String getAppEnvironment() {
		return AppUtils.APP_ENVIRONMENT;
    }

	@ModelAttribute("defaultOwnerCorporation")
	protected Corporation getDefaultOwnerCorporation() {
		return ThreadLocalUtils.getCurrentCorporation();
    }
	///HR 
	@ModelAttribute("userId")
	protected String getUserId() {
		return ThreadLocalUtils.getCurrentUser() == null ? "" : ThreadLocalUtils.getCurrentUser().getId();
	}
	@ModelAttribute("employeeCode")
	protected String getEmployeeCode() {
		return ThreadLocalUtils.getCurrentUser() == null ? "" : ThreadLocalUtils.getCurrentUser().getEmployeeNo();
	}
	//HR end
	protected String to(String toUrl){
		return new StringBuffer().append("/app/").append(AppUtils.APP_NAME).append("/").append(toUrl).toString();
	}
	protected String pagePath(String pagePath){
		return new StringBuffer().append(AppUtils.APP_NAME).append("/").append(pagePath).toString();
	}
	
	protected void setPageSize(PageInfo<? extends IEntity> page, int maxPagesize){
		page.setAutoCount(false);
		page.setPageSize(maxPagesize);
	}
	
	protected JSONArray buildJSONArray(Iterable<? extends IEntity> list) {
		JSONArray ja = new JSONArray();
		for (IEntity t : list) {
			JSONObject obj = t.toJSONObject();
			ja.put(obj);
		}
		return ja;
	}
	
	protected JSONArray buildExcelJSONArray(Collection<? extends IEntity> list) {
		return buildJSONArray(list);
	}
	
	protected ModelAndView generateExcelView(String columnConfigStr, ModelMap modelMap, JSONArray dataJSONArray, String fileName) {
		Assert.notNull(fileName);
		JSONArray ja =  null;
		try {
			ja = new JSONArray(columnConfigStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String title = fileName + ".xls";
		title = title.replace(" ", "_");
		modelMap.put("fileName", title); 
		ListExcelView view = new ListExcelView(ja, dataJSONArray);
		return new ModelAndView(view , modelMap);
	}
	
	protected ModelAndView redirectToBefor(ModelMap modelMap) {
		processErrorMsgAndInfoLog(modelMap);
		String url = (String) modelMap.get(AbsFormController.FROM_URI);
		return new ModelAndView(new RedirectView(url), removeUselessParameters4Redirect(modelMap));
	}
	/*ModelMap is used to pass message of workflow or somethig else*/
	protected ModelAndView redirectTo(String redirectToUrl, ModelMap modelMap){
		return redirectTo(AppUtils.APP_NAME , redirectToUrl , modelMap);
	}
	
	protected ModelAndView redirectTo(String appName , String redirectToUrl, ModelMap modelMap){
		processErrorMsgAndInfoLog(modelMap);
		String toUrl = new StringBuffer().append("/app/").append(appName).append("/").append(redirectToUrl).toString();
		modelMap.put(AbsFormController.FROM_URI, toUrl);
		RedirectView redirectView = new RedirectView(toUrl);
		return new ModelAndView(redirectView, removeUselessParameters4Redirect(modelMap));
	}
	
	protected String redirect(String appName, String redirectToUrl, ModelMap modelMap, RedirectAttributes redirectAttrs){
		processErrorMsgAndInfoLog(modelMap);
		String toUrl = new StringBuffer().append("/app/").append(appName).append("/").append(redirectToUrl).toString();
		modelMap.put(AbsFormController.FROM_URI, toUrl);
		removeUselessParameters4Redirect(modelMap);
		return "redirect:" + toUrl;
	}
	
	private ModelMap removeUselessParameters4Redirect(ModelMap modelMap) {
//		modelMap.remove(CONTROLLED_RESOURCES);
//		modelMap.remove(PAGE_PREFERENCE_STATE);
		return modelMap;
	}
	protected boolean hasErrorMessages() {
		Set<String> errorMsgs = ThreadLocalUtils.getErrorMsg();
		return errorMsgs != null && !errorMsgs.isEmpty();
	}
	protected boolean processErrorMsgAndInfoLog(ModelMap modelMap) {
		boolean noError = true;
		if(hasErrorMessages()) {
			noError = false;
			modelMap.put("errorMsgs", ThreadLocalUtils.getErrorMsg());
			modelMap.put("errorTitle", ThreadLocalUtils.getErrorTitle());
		}
    	
		SystemInfoLog infoLog = ThreadLocalUtils.getInfoLog();
		if(infoLog != null) {
			if(infoLog.hasChild()) noError = false;
			modelMap.put("systemInfoLog", infoLog.toJSONArray().toString());
		}
		return noError;
	}
	protected boolean isNew(String entityId) {
		return Strings.isEmpty(entityId) || NEW_ENTITY_ID.endsWith(entityId);
	}
	protected FunctionNode getBelongToFunctionNode() {
		return null;
	}
	
	protected List<FunctionNode> getBelongToFunctionNodeList() {
		return null;
	}
	
	private List<FunctionNode>  getAllBelongToFunctionNodeList() {
		List<FunctionNode> fnList = new ArrayList<FunctionNode>();
		FunctionNode singleFunctionNode = getBelongToFunctionNode();
		if(singleFunctionNode != null) {
			fnList.add(singleFunctionNode);
		}
		List<FunctionNode> multipleFunctionNodes = getBelongToFunctionNodeList();
		if(multipleFunctionNodes != null && multipleFunctionNodes.size() > 0) {
			for(FunctionNode fn : multipleFunctionNodes) {
				fnList.add(fn);
			}
		}
		return fnList;
	}
	@ModelAttribute(CONTROLLED_RESOURCES)
    public String getControlledResources() {
		if(ThreadLocalUtils.getCurrentUser() != null 
				&& "admin".equals(ThreadLocalUtils.getCurrentUser().getUsername())) {
			
			return new JSONObject().toString();
		}
		return appResourceService.getAvailableForCurrentUserByFunctionNodes(getAllBelongToFunctionNodeList()).toString();
    }
	
    @ModelAttribute(SCOPE_OBJECT_TYPE)
    public String getScopeObjectType(HttpServletRequest request) {
    	String url = request.getRequestURI();
    	String MethodName = url.substring(url.lastIndexOf("/") + 1);
    	return MethodName;
    }
	
	@InitBinder
    public void initGlobalBinder(WebDataBinder binder) {
        
		initCustomDateEditorBinder(binder);
        
        NumberFormat nf = NumberFormat.getNumberInstance();
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("#####0.00000");
        df.setMinimumFractionDigits(2);
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        df.setDecimalFormatSymbols(dfs);
        binder.registerCustomEditor(Double.class, null, new CustomNumberEditor(Double.class, df, true));
        
        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
        binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, null, true));
        
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }
	
	protected void initCustomDateEditorBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(FormatUtils.ISO_DATE_MASK);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new ERPCustomDateEditor(dateFormat, true));
	}

	protected void downLoadFile(HttpServletResponse response, String fileName, File file) {
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			response.reset();  
			setResponseInfo(response, fileName);  
	        os.write(FileUtils.readFileToByteArray(file));  
	        os.flush(); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void setResponseInfo(HttpServletResponse response, String fileName) {
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1"));// 解决下载文件名中文乱码问题
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
		if (fileName.contains(".pdf")) {
			response.setContentType("application/pdf; charset=utf-8");
		} else if (fileName.contains(".xls")){
			response.setContentType("application/vnd.ms-excel; charset=utf-8");
		} else if (fileName.contains(".xlsx")){
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
		} else if (fileName.contains(".doc")){
			response.setContentType("application/msword; charset=utf-8");
		} else if (fileName.contains(".docx")){
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document; charset=utf-8");
		} else if (fileName.contains(".text")){
			response.setContentType("text/plain; charset=utf-8");
		}  else if (fileName.contains(".text")){
			response.setContentType("text/plain; charset=utf-8");
		} else if (fileName.contains(".png")){
			response.setContentType("image/jpeg; charset=utf-8");
		} else if (fileName.contains(".jpeg")){
			response.setContentType("image/jpeg; charset=utf-8");
		}
		
	}
	
	
	protected String getApproveCode(String passwordFor) {
//		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
//		String corporation = ThreadLocalUtils.getCurrentOwnerCorporation();
//		Corporation c = Corporation.findByCode(corporation);
//    	
//    	try {
//    		Field field =ReflectionUtils.findField(Corporation.class, passwordFor);
//    		field.setAccessible(true);
//			return encoder.encodePassword((String)field.get(c), null);
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//			return null;
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//			return null;
//		}
		return null;
	}
}
