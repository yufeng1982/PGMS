package com.photo.bas.core.service.entity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photo.bas.core.dao.entity.AuditLogDAO;
import com.photo.bas.core.model.entity.IAudit;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.utils.PageInfo;

@Service
public class AuditLogManagerImpl implements AuditLogManager {

	@Autowired AuditLogDAO auditLogDAO;
	@Override
	public PageInfo<IAudit> findByAuditQuery(PageInfo<IAudit> page, String clsName) {
		return auditLogDAO.findByAuditQuery(page, clsName);
	}
	@Override
	public JSONObject getSpecialColumsAuditJson(PageInfo<IAudit> page, String clsName, String fields) {
		return auditLogDAO.getSpecialColumsAuditJson(page, clsName, fields);
	}
	public String getChangedIds(String[] ids, String clsName, String fields) {
		return auditLogDAO.getChangedIds(ids, clsName, fields);
	}
	public JSONArray getChangedIds(String documentHeaderId, String clsName, String fields){
		return auditLogDAO.getChangedIds(documentHeaderId, clsName, fields);
	}
	public long getRevisionNumber(String id, SourceEntityType type) {
		return auditLogDAO.getRevisionNumber(id, type);
	}
	public long getRevisionNumber(String id, Class<?> c) {
		return auditLogDAO.getRevisionNumber(id, c);
	}
	public JSONObject getDocLastModified(String documentHeaderId, String headerClassName, String lineClassName, String headerFields, String lineFields) {
		return auditLogDAO.getDocLastModified(documentHeaderId, headerClassName, lineClassName, headerFields, lineFields);
	}
	public JSONArray getFullSpecialColumsAuditJson(String ids, String fields, String clsName) {
		return auditLogDAO.getFullSpecialColumsAuditJson(ids, fields, clsName);
	}

}
