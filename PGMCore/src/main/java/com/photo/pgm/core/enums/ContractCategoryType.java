/**
 * 
 */
package com.photo.pgm.core.enums;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

/**
 * @author FengYu
 *
 */
public enum ContractCategoryType implements IEnum {
	BaseBuilding, Consultative, EquipmentPurchase;

	@Override
	public String getKey() {
		return new StringBuffer().append("ContractCategoryType.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<ContractCategoryType> getContractCategoryType() {
		return EnumSet.allOf(ContractCategoryType.class);
	}
}
