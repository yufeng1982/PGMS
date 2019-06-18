/**
 * 
 */
package com.photo.bas.core.model.entity;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.photo.bas.core.utils.ResourceUtils;

/**
 * @author FengYu
 *
 */
public enum SimpleStatus implements IEnum {
	Draft, Active, Inactive;
	

	public String getKey() { 
		return new StringBuffer().append("SimpleStatus.").append(name()).toString();
	}
	
	public String getName() {
		return name();
	}
	
	SimpleStatus() {}

	public static EnumSet<SimpleStatus> getSimpleStatuses() {
		return EnumSet.allOf(SimpleStatus.class);
	}

	public String getText() {
		return ResourceUtils.getText(getKey());
	}
	
	public static List<SimpleStatus> getCommonStatuses() {
		List<SimpleStatus> list = new ArrayList<SimpleStatus>();
		list.add(Active);
		list.add(Inactive);
		return list;
	}
}
