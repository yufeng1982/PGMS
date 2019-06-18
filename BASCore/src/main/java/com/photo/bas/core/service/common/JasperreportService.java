/**
 * 
 */
package com.photo.bas.core.service.common;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.MultipleDocumentHandling;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.SheetCollate;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRGzipVirtualizer;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.ExporterInputItem;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleExporterInputItem;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.export.SimplePrintServiceReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.photo.bas.core.utils.ThreadLocalUtils;

/**
 * @author FengYu
 *
 */
@Service("jasperreportService")
public class JasperreportService extends AbstractJasperreportService {
	
	public static final String PDF = "pdf";
	public static final String XLSX = "xlsx";
	public static final String CSV = "csv";
	public static final String JEXCEL = "jexcel";
	
	public String export(String jasperFilePath, File outPutFile,Map<String, Object> parameters, JSONObject jasperJson) throws JRException {
		// build jasper parameters
		buildJasperParameters(jasperJson, parameters);
		// setting jasper locale
		setLocal(parameters, jasperJson);
		JasperPrint jasperPrint = null;
		JRGzipVirtualizer virtualizer = null;
		
		JRBeanCollectionDataSource jrbcds = parameters.get("jrDataSource") == null ? null : (JRBeanCollectionDataSource)parameters.get("jrDataSource");
		boolean hasSubReport = jasperJson.getBoolean("hasSubReport");
		boolean isJavaBean = jasperJson.getBoolean("isJavaBean");
		Connection connection = null;
		try {
			connection = getConnection();
			// whether virtralizer for fill data to jasperreprot
			if(jasperJson.getBoolean("isVirtualizer")){
				virtualizer = new JRGzipVirtualizer(100);
				jasperPrint = fillDataByJRVirtualizer(jasperFilePath, null, parameters, hasSubReport , virtualizer, isJavaBean, jrbcds, connection);
			} else {
				jasperPrint = fillData(jasperFilePath, null, parameters, hasSubReport, isJavaBean, jrbcds, connection);
			}
			String fileType = jasperJson.getString("fileType");
			if(fileType.equals(PDF)){
				// export pdf file
				exportPdf(outPutFile, jasperPrint);
			} else if(fileType.equals(XLSX)){
				exportExcel(outPutFile, jasperPrint);
			} else if (fileType.equals(CSV)) {
				exportCsv(outPutFile, jasperPrint);
			} // if want to export other type file, add here 
			if(virtualizer != null) {
				virtualizer.cleanup();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return outPutFile.getName();
	}
	
	public void exportView(FileBufferedOutputStream fbos, String jasperFilePath, Map<String, Object> parameters, JSONObject jasperJson) throws JRException {
		// build jasper parameters
		buildJasperParameters(jasperJson, parameters);
		// setting jasper locale
		setLocal(parameters, jasperJson);
		JasperPrint jasperPrint = null;
		JRGzipVirtualizer virtualizer = null;
		
		boolean hasSubReport = jasperJson.getBoolean("hasSubReport");
		boolean isJavaBean = jasperJson.getBoolean("isJavaBean");
		JRBeanCollectionDataSource jrbcds = parameters.get("jrDataSource") == null ? null : (JRBeanCollectionDataSource)parameters.get("jrDataSource");
		Connection connection = null;
		try {
			connection = getConnection();
			// whether virtralizer for fill data to jasperreprot
			if(jasperJson.getBoolean("isVirtualizer")){
//				virtualizer = new JRGzipVirtualizer(20, new File(jasperFilePath).getParentFile().getParent() + VMDIRECTORYPATH);
				virtualizer = new JRGzipVirtualizer(100);
				jasperPrint = fillDataByJRVirtualizer(jasperFilePath, null, parameters, hasSubReport , virtualizer, isJavaBean, jrbcds, connection);
			} else {
				jasperPrint = fillData(jasperFilePath, null, parameters, hasSubReport, isJavaBean, jrbcds, connection);
			}
			removeBlankPage(jasperPrint.getPages());
			String fileType = jasperJson.getString("fileType");
			if (fileType.equals(PDF)) {
				// export view pdf file
				exportViewPdf(fbos,jasperPrint);
			} else if (fileType.equals(XLSX)){
				// export view excel file
				exportViewExcel(fbos,jasperPrint);
			} else if (fileType.equals(CSV)) {
				exportViewCsv(fbos, jasperPrint);
			} // if want to export view other type file, add here
			if (virtualizer != null) {
				virtualizer.cleanup();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void print(String jasperFilePath, Map<String, Object> parameters, JSONObject jasperJson) throws JRException {
		// build jasper parameters
		buildJasperParameters(jasperJson, parameters);
		// setting jasper locale
		setLocal(parameters, jasperJson);
		// setting print request attributes
		PrintRequestAttributeSet printRequestAttributeSet = buildPrintRequestAttributes(jasperJson);
		// setting pirnt service attributes
		PrintServiceAttributeSet printServiceAttributeSet = buildPrintServiceAttributes(parameters);
		// judge printer whether exist
		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, printServiceAttributeSet);
		if (services.length > 0) {
			JasperPrint jasperPrint = null;
			JRGzipVirtualizer virtualizer = null;
			boolean hasSubReport = jasperJson.getBoolean("hasSubReport");
			boolean isJavaBean = jasperJson.getBoolean("isJavaBean");
			Connection connection = null;
			try {
				connection = getConnection();
				JRBeanCollectionDataSource jrbcds = parameters.get("jrDataSource") == null ? null : (JRBeanCollectionDataSource)parameters.get("jrDataSource");
				// whether virtralizer for fill data to jasperreprot
				if(jasperJson.getBoolean("isVirtualizer")){
//					File file = new File(jasperFilePath);
//					virtualizer = new JRGzipVirtualizer(20, file.getParentFile().getParent() + VMDIRECTORYPATH);
					virtualizer = new JRGzipVirtualizer(100);
					jasperPrint = fillDataByJRVirtualizer(jasperFilePath, null, parameters, hasSubReport , virtualizer, isJavaBean, jrbcds, connection);
				} else {
					jasperPrint = fillData(jasperFilePath, null, parameters, hasSubReport, isJavaBean, jrbcds, connection);
				}
				
				JRPrintServiceExporter exporter = new JRPrintServiceExporter();
				// setting export paramenters
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				buildPrintExportConfigs(jasperJson, printRequestAttributeSet, printServiceAttributeSet, exporter);
				// print report
				long start = System.currentTimeMillis();
				exporter.exportReport();
				logger.info("Printing time : " + (System.currentTimeMillis() - start));
				if (virtualizer != null) {
					virtualizer.cleanup();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			ThreadLocalUtils.addErrorMsg("No suitable print service found.");
			logger.error("No suitable print service found.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void batchPrint(String jasperFilePath, Map<String, Object> parameters, JSONObject jasperJson) throws JRException {
		// batch print list
		List<ExporterInputItem> exporterInputItemList = new ArrayList<ExporterInputItem>();
		JSONArray array = jasperJson.getJSONArray("batchJsonObject");
		List<JRBeanCollectionDataSource> jrbcdsList = (List<JRBeanCollectionDataSource>)parameters.get("jrDataSources");
		JRGzipVirtualizer virtualizer = null;
		Connection connection = null;
		try {
			connection = getConnection();
			for(int i = 0; i < array.length(); i++){
				JSONObject jsonObject = array.getJSONObject(i);
				JRBeanCollectionDataSource jrbcds = null;
				// build jasper parameters
				buildJasperParameters(jsonObject, parameters);
				// setting jasper locale
				setLocal(parameters, jsonObject);
				PrintServiceAttributeSet psas = buildPrintServiceAttributes(parameters);
				PrintService[] services = PrintServiceLookup.lookupPrintServices(null, psas);
				if (services.length > 0) {
					JasperPrint jasperPrint = null;
					boolean hasSubReport = jsonObject.getBoolean("hasSubReport");
					boolean isJavaBean = jsonObject.getBoolean("isJavaBean");
					if(isJavaBean){
						jrbcds = jrbcdsList.get(i);
					}
					// whether virtralizer for fill data to jasperreprot
					if(jsonObject.getBoolean("isVirtualizer")){
//						virtualizer = new JRGzipVirtualizer(20, new File(jasperFilePath).getParentFile().getParent() + VMDIRECTORYPATH);
						virtualizer = new JRGzipVirtualizer(100);
						jasperPrint = fillDataByJRVirtualizer(jasperFilePath, null, parameters, hasSubReport , virtualizer, isJavaBean, jrbcds, connection);
					} else {
						jasperPrint = fillData(jasperFilePath, null, parameters, hasSubReport, isJavaBean, jrbcds, connection);
					}
					exporterInputItemList.add(new SimpleExporterInputItem(jasperPrint));
				} else {
					ThreadLocalUtils.addErrorMsg("No suitable print service found.");
					logger.error("No suitable print service found.");
					break;
				}
			}
			if(ThreadLocalUtils.getErrorMsg().size() == 0){
				JSONObject jsonObject = array.getJSONObject(0);
				PrintRequestAttributeSet printRequestAttributeSet = buildPrintRequestAttributes(jsonObject);
				PrintServiceAttributeSet printServiceAttributeSet = buildPrintServiceAttributes(parameters);
				
				JRPrintServiceExporter exporter = new JRPrintServiceExporter();
				// setting export paramenters
				exporter.setExporterInput(new SimpleExporterInput(exporterInputItemList));
				buildPrintExportConfigs(jasperJson, printRequestAttributeSet, printServiceAttributeSet, exporter);
				// print report
				long start = System.currentTimeMillis();
				exporter.exportReport();
				logger.info("Printing time : " + (System.currentTimeMillis() - start));
				if (virtualizer != null) {
					virtualizer.cleanup();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void buildPrintExportConfigs(JSONObject jasperJson,
											PrintRequestAttributeSet printRequestAttributeSet,
											PrintServiceAttributeSet printServiceAttributeSet,
											JRPrintServiceExporter exporter) {
		SimplePrintServiceExporterConfiguration exporterConfig = new SimplePrintServiceExporterConfiguration();
		SimplePrintServiceReportConfiguration reportConfig = new SimplePrintServiceReportConfiguration();
		exporterConfig.setPrintRequestAttributeSet(printRequestAttributeSet);
		exporterConfig.setPrintServiceAttributeSet(printServiceAttributeSet);
		exporterConfig.setDisplayPageDialog(false);
		exporterConfig.setDisplayPrintDialog(true);
		if(jasperJson.has("offset_x")){
			reportConfig.setOffsetX(new Integer(jasperJson.getInt("offset_x")));
		}
		reportConfig.setZoomRatio(jasperJson.has("zoom_ratio")? new Float(jasperJson.getString("zoom_ratio")) : ZOOM_RATIO);
		exporter.setConfiguration(exporterConfig);
		exporter.setConfiguration(reportConfig);
	}

	private PrintServiceAttributeSet buildPrintServiceAttributes(Map<String, Object> parameters) {
		PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
		printServiceAttributeSet.add(new PrinterName((String)parameters.get("printerName"), null));
		return printServiceAttributeSet;
	}

	private PrintRequestAttributeSet buildPrintRequestAttributes(JSONObject jasperJson) {
		PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		// setting print page size
		printRequestAttributeSet.add(jasperJson.has("pageSize")? getPageSize(jasperJson.getString("pageSize")) : MediaSizeName.NA_LETTER);
		// setting print count
		printRequestAttributeSet.add(new Copies(jasperJson.getInt("copies")));
		// if copies greater then 1 let print order is a b, a b, a b, not a a a b b b
		printRequestAttributeSet.add(SheetCollate.COLLATED);
		printRequestAttributeSet.add(MultipleDocumentHandling.SEPARATE_DOCUMENTS_COLLATED_COPIES);
		return printRequestAttributeSet;
	}
	
	protected JasperPrint fillData(String jasperFilePath, 
									JasperReport jasperReprot,
									Map<String, Object> parameters,
									boolean hasSubReport, 
									boolean isJavaBean, 
									JRBeanCollectionDataSource jrbcds,
									Connection connection) throws JRException {
		if(jasperReprot == null) jasperReprot = (JasperReport)JRLoader.loadObject(new File(jasperFilePath));
		// reset margins for excel
		if(parameters.get("fileType").equals(XLSX)){
			resetPageMarginAndSize(jasperReprot, "leftMargin", 0);
			resetPageMarginAndSize(jasperReprot, "rightMargin", 0);
		}
		JasperPrint jasperPrint = null;
		parameters.put("REPORT_CONNECTION", connection);
		if(isJavaBean){
			long start = System.currentTimeMillis();
			if(hasSubReport){
				parameters.put("SUBREPORT_DIR", new File(jasperFilePath).getParent()+"/");
			}
			jasperPrint = JasperFillManager.fillReport(jasperReprot, parameters, jrbcds);
			logger.info("Filling time : " + (System.currentTimeMillis() - start));
		} else {			
			if(hasSubReport){
				parameters.put("SUBREPORT_DIR", new File(jasperFilePath).getParent()+"/");
			}
			long start = System.currentTimeMillis();
			jasperPrint = JasperFillManager.fillReport(jasperReprot, parameters, connection);
			logger.info("Filling time : " + (System.currentTimeMillis() - start));
		}
		return jasperPrint;
	}

	protected JasperPrint fillDataByJRVirtualizer(String jasperFilePath, JasperReport jasperReprot, Map<String, Object> parameters,
									boolean hasSubReport,JRGzipVirtualizer virtualizer, 
									boolean isJavaBean,JRBeanCollectionDataSource jrbcds, Connection connection) throws JRException {
		if(jasperReprot == null) jasperReprot = (JasperReport)JRLoader.loadObject(new File(jasperFilePath));
		// reset margins for excel
		if(parameters.get("fileType").equals(XLSX)){
			resetPageMarginAndSize(jasperReprot, "leftMargin", 0);
			resetPageMarginAndSize(jasperReprot, "rightMargin", 0);
		}
		JasperPrint jasperPrint = null;
		parameters.put("REPORT_CONNECTION", connection);
		if(isJavaBean){
			long start = System.currentTimeMillis();
			if(hasSubReport){
				parameters.put("SUBREPORT_DIR", new File(jasperFilePath).getParent()+"/");
			}
			jasperPrint = JasperFillManager.fillReport(jasperReprot, parameters, jrbcds);
			logger.info("Filling time : " + (System.currentTimeMillis() - start));
		} else {
			if(hasSubReport){
				parameters.put("SUBREPORT_DIR", new File(jasperFilePath).getParent()+"/");
			}
			parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			long start = System.currentTimeMillis();	
			jasperPrint = JasperFillManager.fillReport(jasperReprot, parameters, connection);
			logger.info("Filling time : " + (System.currentTimeMillis() - start));
		}
		virtualizer.setReadOnly(true);
		
		return jasperPrint;
	}
	
	protected void exportPdf(File outPutFile, JasperPrint jasperPrint) throws JRException {
		JRPdfExporter jrPdfExporter = new JRPdfExporter();
		jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outPutFile));
		long start = System.currentTimeMillis();
		jrPdfExporter.exportReport();
		logger.info("Exporting time : " + (System.currentTimeMillis() - start));
	}
	
	protected void exportViewPdf(FileBufferedOutputStream fbos,JasperPrint jasperPrint) throws JRException {
		JRPdfExporter jrPdfExporter = new JRPdfExporter();
		jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fbos));
		long start = System.currentTimeMillis();
		jrPdfExporter.exportReport();
		logger.info("Viewing time : " + (System.currentTimeMillis() - start));
	}
	
	protected void exportExcel(File outPutFile, JasperPrint jasperPrint) throws JRException {
		JRXlsxExporter jrXlsxExporter = new JRXlsxExporter();
		jrXlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		jrXlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outPutFile));
		jrXlsxExporter.setConfiguration(setExcelParameters());		
		long start = System.currentTimeMillis();
		jrXlsxExporter.exportReport();
		logger.info("Exporting time : " + (System.currentTimeMillis() - start));
	}
	
	
	protected void exportViewExcel(FileBufferedOutputStream fbos,JasperPrint jasperPrint) throws JRException {
		JRXlsxExporter jrXlsxExporter = new JRXlsxExporter();
		jrXlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		jrXlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fbos));
		jrXlsxExporter.setConfiguration(setExcelParameters());				
		long start = System.currentTimeMillis();
		jrXlsxExporter.exportReport();
		logger.info("Viewing time : " + (System.currentTimeMillis() - start));
	}
	
	protected void exportViewCsv(FileBufferedOutputStream fbos, JasperPrint jasperPrint) throws JRException{
		  JRCsvExporter exporter = new JRCsvExporter();
		  exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		  exporter.setExporterOutput(new SimpleWriterExporterOutput(fbos));
		  long start = System.currentTimeMillis();
		  exporter.exportReport();
		  logger.info("CSV creation time : " + (System.currentTimeMillis() - start));
	}
	
	protected void exportCsv(File outPutFile, JasperPrint jasperPrint) throws JRException{
		  JRCsvExporter exporter = new JRCsvExporter();
		  exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		  exporter.setExporterOutput(new SimpleWriterExporterOutput(outPutFile));
		  long start = System.currentTimeMillis();
		  exporter.exportReport();
		  logger.info("CSV creation time : " + (System.currentTimeMillis() - start));
	}
	
	protected SimpleXlsxReportConfiguration setExcelParameters() {
		SimpleXlsxReportConfiguration xlsxConfig = new SimpleXlsxReportConfiguration();
		xlsxConfig.setWhitePageBackground(false);
		xlsxConfig.setIgnoreCellBorder(false);
		xlsxConfig.setRemoveEmptySpaceBetweenRows(true);
		xlsxConfig.setDetectCellType(true);
		xlsxConfig.setMaxRowsPerSheet(1048576);
		xlsxConfig.setUseTimeZone(true);
		xlsxConfig.setWrapText(false);
		xlsxConfig.setCollapseRowSpan(true);
		xlsxConfig.setFitWidth(1);
		return xlsxConfig;
	}
	
	protected void setLocal(Map<String, Object> parameters,JSONObject jasperJson) {	
		parameters.put("REPORT_LOCALE", ThreadLocalUtils.getCurrentLocale());
	}

	/**
	 * summary : reset page margins for excel by reflect
	 * 			
	 * @param jasperReprot jasper template file (note: only modification jasperReport is valid,modification jasperPrint is ivalid )
	 * @param marginType leftMargin and so on
	 * @param number margin count
	 * */
	protected static void resetPageMarginAndSize(JasperReport jasperReprot,String marginType, int number) {
		try {
			Field declaredField = jasperReprot.getClass().getSuperclass().getDeclaredField(marginType);
			try {
				declaredField.setAccessible(true);
				declaredField.setInt(jasperReprot, number);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	private void removeBlankPage(List<JRPrintPage> pages) {
		for (Iterator<JRPrintPage> i = pages.iterator(); i.hasNext();) {
			JRPrintPage page = i.next();
			if (page.getElements().size() == 0)
				i.remove();
		}
	}
}
