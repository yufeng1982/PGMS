/**
 * 
 */
package com.photo.bas.core.model.common;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

/**
 * @author FengYu
 *
 */
public enum ResourceType implements IEnum {
	CSV, EDI, IMAGE, TEXT_FILE, BINARY_FILE, PRODUCT_BROCHURE, ASSEMBLY_LIST, QUALITY_ASSURANCE, PRODUCT_DRAWING, CSV_FILE, EDI_FILE;
	
	ResourceType() {}

	public String getKey() {
		return new StringBuffer().append("ResourceType.").append(name()).toString();
	}
	
	public String getName() {
		return name();
	}

	public static EnumSet<ResourceType> getResourceTypes() {
		return EnumSet.allOf(ResourceType.class);
	}

	public String getText() {
		return ResourceUtils.getText(getKey());
	}
	
	public String toString() {
		return getKey();
	}
}
