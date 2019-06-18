/**
 * 
 */
package com.photo.bas.core.web.controller.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsFormController;

/**
 * @author FengYu
 *
 */
public abstract class JasperreportController extends AbsJasperreportController {
	
	@RequestMapping
	public ModelAndView export(HttpServletRequest request,
			@RequestParam(value="jasperJson",required=true) JSONObject jasperJson) throws JRException {
		String jasperFilePath = jasperFilePath(request, jasperJson.getString("fileName"));
		File outPutFile = new File(outPutFilePath(request, jasperJson));
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(jasperJson.has("dataType")){
			parameters.put(JRDATASOURCE, getJRBeanDataSource(jasperJson.getString("dataType"),jasperJson));
		}
		String fileName = getJasperreportService().export(jasperFilePath, outPutFile, parameters, jasperJson);
		logger.info(fileName);
		return toJSONView("");
	}
	
	@RequestMapping("downLoad")
	@ResponseBody
	public void downLoad(HttpServletRequest request, HttpServletResponse response, 
							@RequestParam(value="jasperJson",required=true) JSONObject jasperJson) throws JRException, ServletException, IOException {
		
		String jasperFilePath = jasperFilePath(request, jasperJson.getString("fileName"));
		FileBufferedOutputStream fbos = new FileBufferedOutputStream();
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(jasperJson.has("dataType")){
			parameters.put(JRDATASOURCE, getJRBeanDataSource(jasperJson.getString("dataType"),jasperJson));
		}
		String webDir = request.getSession().getServletContext().getRealPath("/");
		parameters.put("webDir", webDir);
		getJasperreportService().exportView(fbos, jasperFilePath, parameters, jasperJson);
		writeFileStream(response, fbos, jasperJson);
		
//		String jasperFilePath = jasperFilePath(request, jasperJson.getString("fileName"));
//		File outPutFile = new File(outPutFilePath(request, jasperJson));
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		if(jasperJson.has("dataType")){
//			parameters.put(JRDATASOURCE, getJRBeanDataSource(jasperJson.getString("dataType"),jasperJson));
//		}
//		String webDir = request.getSession().getServletContext().getRealPath("/");
//		parameters.put("webDir", webDir);
//		String fileName = getJasperreportService().export(jasperFilePath, outPutFile, parameters, jasperJson);
//		downLoadFile(response, fileName, outPutFile);
	} 
	
	@RequestMapping(value = "print")
	public ModelAndView print(HttpServletRequest request,
			@RequestParam(value="jasperJson",required=true) JSONObject jasperJson) throws JRException {
		String jasperFilePath = jasperFilePath(request, jasperJson.getString("fileName"));
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(jasperJson.has("dataType")){
			parameters.put(JRDATASOURCE, getJRBeanDataSource(jasperJson.getString("dataType"),jasperJson));
		}
		JSONObject printJsonObj = new JSONObject();
		getJasperreportService().print(jasperFilePath, parameters, jasperJson);
		Set<String> errors = ThreadLocalUtils.getErrorMsg();
		if(errors.size() > 0){
			printJsonObj.put("isPrintSuccess", false);
		} else {
			printJsonObj.put("isPrintSuccess", true);
		}
		return toJSONView(printJsonObj.toString());
	}
	
	@RequestMapping(value = "batchPrint")
	public ModelAndView batchPrint(HttpServletRequest request,
			@RequestParam(value="jasperJson",required=true) JSONObject jasperJson) throws JRException {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(jasperJson.getJSONArray("batchJsonObject").getJSONObject(0).has("dataType")){
			List<JRBeanCollectionDataSource> jrdsList = new ArrayList<JRBeanCollectionDataSource>();
			JSONArray array = jasperJson.getJSONArray("batchJsonObject");
			for(int i = 0; i < array.length(); i++){
				JSONObject jsonObject = array.getJSONObject(i);
				jrdsList.add(getJRBeanDataSource(jsonObject.getString("dataType"),jsonObject));
			}
			parameters.put(JRDATASOURCES,jrdsList);
		}
		JSONObject printJsonObj = new JSONObject();
		String fileName = jasperJson.getJSONArray("batchJsonObject").getJSONObject(0).getString("fileName");
		String jasperFilePath = jasperFilePath(request, fileName);
		
		getJasperreportService().batchPrint(jasperFilePath, parameters, jasperJson);
		Set<String> errors = ThreadLocalUtils.getErrorMsg();
		if(errors.size() > 0){
			printJsonObj.put("isPrintSuccess", false);
		} else {
			printJsonObj.put("isPrintSuccess", true);
		}
		return toJSONView(printJsonObj.toString());
	}
	
	@RequestMapping(value = "view")
	public void view(HttpServletRequest request,HttpServletResponse response,
					@RequestParam(value="jasperJson",required=true) JSONObject jasperJson,
					ModelMap modelMap) throws JRException, IOException, ServletException {
		String jasperFilePath = jasperFilePath(request, jasperJson.getString("fileName"));
		FileBufferedOutputStream fbos = new FileBufferedOutputStream();
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(jasperJson.has("dataType")){
			parameters.put(JRDATASOURCE, getJRBeanDataSource(jasperJson.getString("dataType"),jasperJson));
		}
		getJasperreportService().exportView(fbos, jasperFilePath, parameters, jasperJson);
		writeFileStream(response, fbos, jasperJson);
	}
	
	@ModelAttribute(AbsFormController.FROM_URI)
    public String getFromURI(@RequestParam(value=AbsFormController.FROM_URI, required=false) String fromURI, HttpServletRequest request) {
    	return Strings.isEmpty(fromURI) ? request.getRequestURI() : fromURI;
    }
}
