package com.photo.bas.core.service.entity;

import org.json.JSONArray;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.IAudit;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.utils.PageInfo;

public interface AuditLogManager {
	public PageInfo<IAudit> findByAuditQuery(final PageInfo<IAudit> page, String clsName);
	public JSONObject getSpecialColumsAuditJson(final PageInfo<IAudit> page, String clsName, String fields);
	public String getChangedIds(String[] ids, String clsName, String fields);
	public JSONArray getChangedIds(String documentHeaderId, String clsName, String fields);
	public long getRevisionNumber(String id, SourceEntityType type);
	public long getRevisionNumber(String id, Class<?> c);
	public JSONObject getDocLastModified(String documentHeaderId, String headerClassName, String lineClassName, String headerFields, String lineFields);
	public JSONArray getFullSpecialColumsAuditJson(String ids, String fields, String clsName);
}
