/**
 * 
 */
package com.photo.bas.core.model.entity;

import java.util.List;

import com.photo.bas.core.model.security.Corporation;


/**
 * @author FengYu
 *
 */
public interface ISerial {
	public static final int sequenceLength = 6;
	public String getSequence();
	public String getSequenceName();
	public String getPrefix();
	public int getSequenceLength();
	public void setSequence(String sequence);
	public String getSequenceNameSuffix();
	
	public Corporation getCorporation();
	
	public List<String> getOtherDiscriminatorNames();
	public List<Object> getOtherDiscriminatorValues();
	
	public static final String PHO = "PHO";
	public static final String ITEM = "I";
	public static final String ITEMPACKAGE = "IP";
	public static final String CUSTOMER = "C";
	public static final String CLOTHES = "CLS";
	public static final String WORKFLOW = "WF";
	public static final String COOPERATION = "CO";
	
//	public static final String PHO_SEQUENCE = "photo.pho_sequence";
//	public static final String ITEM_SEQUENCE = "product.item_sequence";
//	public static final String ITEMPACKAGE_SEQUENCE = "product.itempackage_sequence";
	public static final String CUSTOMER_SEQUENCE = "crm.customer_sequence";
	public static final String WORKFLOW_SEQUENCE = "project.flowDefinition_sequence";
	public static final String CONTRACT_SEQUENCE = "project.contract_sequence";
	public static final String ASSET_SEQUENCE = "project.asset_sequence";
	public static final String COOPERATION_SEQUENCE = "project.cooperation_sequence";
//	public static final String CLOTHES_SEQUENCE = "product.clothes_sequence";
								
}
