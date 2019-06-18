package com.photo.bas.core.web.convert;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;

import com.photo.bas.core.utils.DateTimeUtils;
import com.photo.bas.core.utils.ThreadLocalUtils;

public class ERPCustomDateEditor extends CustomDateEditor {

	private final SimpleDateFormat dateFormat;
	
	private final boolean allowEmpty;

	private final int exactDateLength;
	
	public ERPCustomDateEditor(SimpleDateFormat dateFormat, boolean allowEmpty) {
		super(dateFormat, allowEmpty);
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = -1;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		}
		else if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
			throw new IllegalArgumentException(
					"Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
		}
		else {
			try {
				setValue(DateTimeUtils.fromDate(text));
			}
			catch (Exception ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		dateFormat.setTimeZone(ThreadLocalUtils.getTimeZone());
		return (value != null ? dateFormat.format(value) : "");
	}
}
