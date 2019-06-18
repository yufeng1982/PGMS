package com.photo.bas.core.service.entity;

import java.io.InputStream;

/**
 * 
 * @author FengYu
 *
 */
public interface EntityImportManager {
	public int processUserRoleImport(String filePath);
	
	public int processImport(String filePath);
	InputStream getInputStream(String path);
	
	//public Object processSpecificCodeImport(String filePath, String code);
	
	public int processAllCustomer2AccountingSystem(String corporation);
	public int processAllVendor2AccountingSystem(String corporation);
	public void updateLoadingInstructionForSO();
//	public int processCOMSOs4P(String path);
}
