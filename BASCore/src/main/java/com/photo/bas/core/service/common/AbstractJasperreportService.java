/**
 * 
 */
package com.photo.bas.core.service.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import javax.print.attribute.standard.MediaSizeName;

import org.apache.commons.dbcp.BasicDataSource;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.photo.bas.core.security.service.CorporationService;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;

/**
 * @author FengYu
 *
 */
public abstract class AbstractJasperreportService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	public static final String VMDIRECTORYPATH = "/tmp";
	protected static final Float ZOOM_RATIO = new Float("0.95");
	protected static final Integer OFFSET_X = new Integer(10);
	protected static final Integer OFFSET_Y = new Integer(10);
	
	@Autowired private CorporationService corporationService;
	@Autowired private BasicDataSource basicDataSource;
	
	public Connection getConnection(){
		try {
			return basicDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void buildJasperParameters(JSONObject jasperJson,Map<String, Object> parameters){
		JSONObject jasperParamObj = jasperJson.getJSONObject("jasperParams");
		parameters.put("corporation", ThreadLocalUtils.getCurrentCorporation().getId());

		parameters.put("_corporation_name", ThreadLocalUtils.getCurrentCorporation().getName());
//		parameters.put("_report_version", ResourceUtils.getAppSetting("report.project.vsersion"));
		parameters.put("_current_user", ThreadLocalUtils.getCurrentUser().getLoginName());
		
		TimeZone timeZoneLondon = ThreadLocalUtils.getTimeZone();
		parameters.put("REPORT_TIME_ZONE", timeZoneLondon);
		String fileType = "pdf";
		if(jasperJson.has("fileType") && !Strings.isEmpty(jasperJson.getString("fileType"))) {
			fileType = jasperJson.getString("fileType");
		}
		parameters.put("fileType", fileType);
		
		parameters.put("printerName", jasperParamObj.has("printerName") && !Strings.isEmpty(jasperParamObj.getString("printerName")) ? jasperParamObj.getString("printerName") : ResourceUtils.getAppSetting("erp.app.printer"));
		Iterator<String> it = jasperParamObj.keys();
		while(it.hasNext()){
			String key = it.next();
			parameters.put(key, jasperParamObj.getString(key));
		}
		setDefaultParams(jasperJson);
		
	}
	
	private void setDefaultParams(JSONObject jasperJson){
		if(!jasperJson.has("hasSubReport")){
			jasperJson.put("hasSubReport", Boolean.FALSE);
		}
		if(!jasperJson.has("isJavaBean")){
			jasperJson.put("isJavaBean", Boolean.FALSE);
		}
		if(!jasperJson.has("fileType")){
			jasperJson.put("fileType", "pdf");
		}
		if(!jasperJson.has("isVirtualizer")){
			jasperJson.put("isVirtualizer", Boolean.FALSE);
		}
		if(!jasperJson.has("copies")){
			jasperJson.put("copies", 1);
		}
	}
	
	protected MediaSizeName getPageSize(String pageSize){
		MediaSizeName size = null;
		if(Strings.isEmpty(pageSize)){
			size = MediaSizeName.NA_LETTER;
		} else {
			if(pageSize.equals("ISO_A4")) {
				size = MediaSizeName.ISO_A4;
			} else if (pageSize.equals("letter")) {
				size = MediaSizeName.NA_LETTER;
			} else if (pageSize.equals("legal")){
				size = MediaSizeName.NA_LEGAL;
			}
		}
		return size;
	}
	
}
