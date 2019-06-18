/**
 * 
 */
package com.photo.bas.core.model.entity;

import java.util.EnumSet;

import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.Strings;

/**
 * @author FengYu
 *
 */
public enum TrackingSegmentType implements IEnum {

	GroupType, RuleType, TextType;

	@Override
	public String getKey() {
		return new StringBuffer().append("TrackingSegmentType.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static TrackingSegmentType fromName(String name) {
    	if(Strings.isEmpty(name)) return null;
        return TrackingSegmentType.valueOf(name);
	}
	
	public static EnumSet<TrackingSegmentType> getTrackingSegmentTypes() {
		return EnumSet.allOf(TrackingSegmentType.class);
	}
}
