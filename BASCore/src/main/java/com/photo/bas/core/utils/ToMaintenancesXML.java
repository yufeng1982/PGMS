package com.photo.bas.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ToMaintenancesXML {
	/**
	 * It is very easy to use: 
	 * 1.It only can be used to '.xlsx'.Please convert '.xls' to '.xlsx'.
	 * 2.Put the final value into you need. 
	 * 3.Execute 'ToMaintenancesXML.java' by 'Java Application'.
	 */
	
	public static Document document;//this property will kill multiple instances of this class. now it's ok.
	private String filename;
	
	// You can put the final value into you need.
	private static final String EXCEL_PATH = "D:\\test1.xlsx";
	private static final String XML_PATH = "D:\\maintenances-steel.xml";
	private static final String SHEET_NAME = "Sheet1";

	public ToMaintenancesXML() {
	}

	public ToMaintenancesXML(String name) {
		filename = name; 
	}

	public void toWrite(String lable, String value, Element i) {
		i.addAttribute(lable, value);
	}

	public void toSave(Document document) {
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setLineSeparator("\r\n");
			format.setEncoding("utf-8");
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(filename), format);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}

	public Document getDocument(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			SAXReader reader = new SAXReader();
			try {
				document = reader.read(file);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} else {
			document = DocumentHelper.createDocument();
			document.addElement("root");
		}
		return document;
	}

	public void excelToXML(ToMaintenancesXML myxml, XSSFSheet sheets, Document baseXML) {
		XSSFRow row = null;
		XSSFCell cell = null;
		Cell cellLableRow = null;
		String cellTypeValue = null;
		Element root;
		Element i;
		Element m;
		int num;
		
		root = baseXML.getRootElement();	
		m = root.addElement("m");
		m.addAttribute("name", "");
		m.addAttribute("discriminator", "code");
		
		for (num = 1; num <= sheets.getLastRowNum(); num++) {
			i = m.addElement("i");
			row = sheets.getRow(num);
			for (Iterator<Cell> it = sheets.getRow(0).cellIterator(); it.hasNext();) {
			    cellLableRow = it.next();
			    String cellLableRowValue = cellLableRow.getStringCellValue();
				if (!cellLableRowValue.equals("")) {
					cell = row.getCell(cellLableRow.getColumnIndex());
					if(cell == null) {
						myxml.toWrite(cellLableRowValue, "", i);
				    }else{
					   if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-M-d");
								cellTypeValue = String.valueOf(sFormat.format(cell.getDateCellValue()));
							} else {
								cellTypeValue = String.valueOf(cell.getNumericCellValue());
							}
					    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						    cellTypeValue = String.valueOf(cell.getBooleanCellValue());
					    } else {
						    cellTypeValue = cell.getStringCellValue();
					    }
					   myxml.toWrite(cellLableRowValue, cellTypeValue, i);
				    } 					
			    }
			}
		}
		myxml.toSave(baseXML);	
	}
	
	public static void main(String args[]) {
		try {
			ToMaintenancesXML myxml = new ToMaintenancesXML(XML_PATH);
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(EXCEL_PATH));
			XSSFSheet sheets = workbook.getSheet(SHEET_NAME);
			Document baseXML = myxml.getDocument(XML_PATH);
			myxml.excelToXML(myxml, sheets, baseXML);
			System.out.print("Your   writing   is   successful! ");
		} catch (Exception e) {
			System.out.println("Already run xlRead() : " + e);
		}
	}
}