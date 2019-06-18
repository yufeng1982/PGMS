package com.photo.bas.core.utils;

import org.json.JSONArray;

public class SearchingPage<T> extends PageInfo<T>{
	
	protected JSONArray jsonResult = null;
	private String keys;
	private String fields;

	public SearchingPage() {
		super();
	}

	public SearchingPage(final int pageSize, final int beginIndex) {
		super(pageSize, beginIndex);
	}
	
	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public JSONArray getJsonResult() {
		if (jsonResult == null)
			return new JSONArray();
		return jsonResult;
	}

	public void setJsonResult(final JSONArray jsonResult) {
		this.jsonResult = jsonResult;
	}

}
