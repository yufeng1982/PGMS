package com.photo.bas.core.model.common;

import java.io.Serializable;


/**
 * @author rxg
 * 
 */

public class EventLogParams implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3530883221167693990L;

	private String id;

	private String params;
	
	EventLogParams() {
		
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getId() {
		return id;
	}
}
