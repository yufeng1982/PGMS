/**
 * 
 */
package com.photo.bas.core.model.security;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

/**
 * @author FengYu
 *
 */
public enum SecurityLevel implements IEnum {
	enable {
		@Override public int getPriority() { return 10; }
	},
	visible {
		@Override public int getPriority() { return 8; }
	},
	disabled {
		@Override public int getPriority() { return 6; }
	},
	hidden {
		@Override public int getPriority() { return 1; }
	};
	
	public abstract int getPriority();
	
	public String getKey() { 
		return new StringBuffer().append("Operation.").append(name()).toString();
	}
	
	SecurityLevel() {}

	public static EnumSet<SecurityLevel> getOperations() {
		return EnumSet.allOf(SecurityLevel.class);
	}

	public String getName() {
		return name();
	}
	
	public String toString() {
		return getKey();
	}
	
	public String getText() {
		return ResourceUtils.getText(getKey());
	}
}
