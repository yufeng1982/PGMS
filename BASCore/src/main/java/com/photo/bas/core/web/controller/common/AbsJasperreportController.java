/**
 * 
 */
package com.photo.bas.core.web.controller.common;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.photo.bas.core.service.common.JasperreportService;
import com.photo.bas.core.web.controller.entity.AbsController;

/**
 * @author FengYu
 *
 */
public abstract class AbsJasperreportController extends AbsController<Object> {
	
	protected final static String JASPER = ".jasper";
	protected final static String BASEJASPERPATH = "/WEB-INF/classes/report/jasper/";
	protected final static String DYNAMICJASPERPATH = "/WEB-INF/classes/report/jasper/dynamic/";
	protected final static String EXPORTPDFPATH = "/exportFile";
	protected final static String JRDATASOURCE = "jrDataSource";
	protected final static String JRDATASOURCES = "jrDataSources";
	
	@Autowired private JasperreportService jasperreportService;
	
	protected String jasperFilePath(HttpServletRequest request, String fileName){
		return request.getSession().getServletContext().getRealPath(BASEJASPERPATH + fileName + JASPER);
	}
	
	protected String dynamicJasperFilePath(HttpServletRequest request, String fileName){
		return request.getSession().getServletContext().getRealPath(DYNAMICJASPERPATH + fileName + JASPER);
	}
	
	protected String outPutFilePath(HttpServletRequest request, JSONObject jasperJson){
		File pdfDirectory = null;
		pdfDirectory = new File(request.getSession().getServletContext().getRealPath(EXPORTPDFPATH));
		String fileName = jasperJson.getString("fileName");
		
		if(!pdfDirectory.exists()){
			pdfDirectory.mkdir();
		}
		String fileType = jasperJson.getString("fileType");
		String stuffix = fileType.equals("xlsx") ? ".xlsx" : ".pdf";
		return pdfDirectory.getPath() + File.separator + fileName + System.currentTimeMillis() + stuffix;
	}
	
	protected abstract JRBeanCollectionDataSource getJRBeanDataSource(String dataType,JSONObject jso);
	
	protected void writeFileStream(HttpServletResponse response, FileBufferedOutputStream fbos, JSONObject jasperJson) throws ServletException,IOException {
		try {
			fbos.close();
			String fileName = setFileName(jasperJson);
			if (fbos.size() > 0) {
				try {
					response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1"));// 解决下载文件名中文乱码问题
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}  
				if(jasperJson.getString("fileType").equals("pdf")){
					response.setContentType("application/pdf; charset=utf-8");
				} else if (jasperJson.getString("fileType").equals("xlsx")) {
					response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
				} else if (jasperJson.getString("fileType").equals("csv")) {
					response.setContentType("text/csv");
				}
				response.setContentLength(fbos.size());
				ServletOutputStream ouputStream = response.getOutputStream();
				try {
					fbos.writeData(ouputStream);
					fbos.dispose();
					ouputStream.flush();
				} finally {
					if (ouputStream != null) {
						try {
							ouputStream.close();
						} catch (IOException ex) {
						}
					}
				}
			}
		} catch (IOException e) {
			throw new ServletException(e);
		} finally {
			fbos.close();
			fbos.dispose();
		}
	}

	private String setFileName(JSONObject jasperJson) {
		String stuffix = ".pdf";
		String fileName = jasperJson.getString("fileName");
		if(jasperJson.has("subFileName")){
			fileName = fileName + "_" + jasperJson.getString("subFileName");
		}
		fileName += "_" + new DateTime().getMillis();
		if (jasperJson.getString("fileType").equals("pdf")){
			stuffix = ".pdf";
		} else if (jasperJson.getString("fileType").equals("xlsx")) {
			stuffix = ".xlsx";
		}
		return fileName + stuffix;
	}
	

	public JasperreportService getJasperreportService() {
		return jasperreportService;
	}

	public void setJasperreportService(JasperreportService jasperreportService) {
		this.jasperreportService = jasperreportService;
	}
	
}
