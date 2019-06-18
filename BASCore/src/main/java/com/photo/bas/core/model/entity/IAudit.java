package com.photo.bas.core.model.entity;

import org.json.JSONObject;

public interface IAudit {

	public JSONObject toAuditJson(String clazz);
}
