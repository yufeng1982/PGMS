package com.photo.bas.core.web.controller.entity;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Embedded;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.entity.IAudit;
import com.photo.bas.core.model.entity.RevBaseEntity;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.service.entity.AuditLogManager;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping (value="/log/history/list")
public class LogHistoryController extends AbsListController {
	@Autowired protected AuditLogManager auditLogManager;	

	@RequestMapping("show")
	public String show(String id, String fields, String clazz ,ModelMap modelMap) {
		modelMap.put("id", id);
		modelMap.put("clazz", clazz);
		modelMap.put("fields", fields);
		Class<?> c = SourceEntityType.fromName(clazz).getClazz();
		
		JSONArray arr = buildColumns(c, fields);
		
		modelMap.put("cols", arr.toString());
		return "common/logHistory/logHistory";
    }
	
	private JSONArray buildColumns(Class<?> c, String columns) {
		JSONArray arr = new JSONArray();
		JSONObject o = new JSONObject();
		o.put("id", "version");
		o.put("header", getMessage("Com.Version"));
		o.put("fieldType", "number");
		arr.put(o);
		 
		buildColumns(c, arr , columns);
		
		o = new JSONObject();
		o.put("id", "modDate");
		o.put("width", 140);
		o.put("header", "Audit Date");
		arr.put(o);
		
		o = new JSONObject();
		o.put("id", "modBy");
		o.put("header", "Audit By");
		arr.put(o);
		return arr;
	}

	private void buildColumns(Class<?> c, JSONArray arr, String columns) {
		if(columns != null) {
			String [] clos = columns.split(",");
			for (int j = 0; j < clos.length; j++) {
				buildCol(arr, clos[j]);
			}
		} else {
			Field[] fields = null;
			while (c != null) {
				fields = c.getDeclaredFields();
				for (Field field : fields) {
					if (field.getName().equals("serialVersionUID")) {
						continue;
					}
					if (c.getAnnotation(Audited.class) == null) {
						if (field.getAnnotation(Audited.class) != null) {
							if (field.getAnnotation(Embedded.class) != null) {
								buildColumns(field.getType(), arr, columns);
							} else {
								buildCol(arr, field);
							}
						}
					} else {
						if (field.getAnnotation(Embedded.class) != null) {
							buildColumns(field.getType(), arr, columns);
						} else if(field.getAnnotation(NotAudited.class) == null){
							buildCol(arr, field);
						}
					}
				}
				c = c.getSuperclass();
			}
		}
	}

	private void buildCol(JSONArray arr, Field field) {
		JSONObject o = new JSONObject();
		o.put("id", field.getName());
		String header = Strings.firstCharUpperCase(field.getName()).replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");
		o.put("header", header);
		o.put("width", header.length() * 10);

		Class<?> t = field.getType();
		String type = "text";
		if (Integer.class.equals(t)) {
			type = "integer";
		} else if (Double.class.equals(t) || Long.class.equals(t) || Float.class.equals(t)
				|| BigDecimal.class.equals(t)) {
			type = "number";
		} else if (Date.class.equals(t)) {
//			type = "date";
		} else if (Boolean.class.equals(t)) {
			type = "checkbox";
		} else if (t.isEnum()) {
			type = "combobox";
		}

		o.put("fieldType", type);

		arr.put(o);
	}
	private void buildCol(JSONArray arr, String field) {
		JSONObject o = new JSONObject();
		o.put("id", field);
		String header = Strings.firstCharUpperCase(field).replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");
		o.put("header",header);
		o.put("width",header.length()*8);
		o.put("fieldType", "");
		arr.put(o);
	}
	
	@RequestMapping("json")
	public ModelAndView json(String id, String clazz, PageInfo<IAudit> page) {
		page.setSf_EQ_id(id);
		return toJSONView(buildAuditJSONArray(auditLogManager.findByAuditQuery(page, clazz).getObjResult(), clazz));
    } 
	@RequestMapping("jsonFields")
	public ModelAndView jsonFields(String id, String clazz, String fields, PageInfo<IAudit> page) {
		page.setSf_EQ_id(id);
		return toJSONView(auditLogManager.getSpecialColumsAuditJson(page, clazz,  fields));
	} 
	@RequestMapping("jsonDoc")
	public ModelAndView jsonDoc(String documentHeaderId, String clazz, String fields) {
		return toJSONView(auditLogManager.getChangedIds(documentHeaderId, clazz,  fields));
	} 
	@RequestMapping("jsonRecords")
	public ModelAndView jsonRecords(String ids, String clazz, String fields) {
		String[] idArray = ids.split(","); 
		return toJSONView(auditLogManager.getChangedIds(idArray, clazz,  fields));
	} 
	@RequestMapping
	public ModelAndView jsonDocLastModified(String documentHeaderId, String headerClassName, String lineClassName, String headerFields, String lineFields) {
		return toJSONView(auditLogManager.getDocLastModified(documentHeaderId, headerClassName, lineClassName, headerFields, lineFields));
	} 
	@RequestMapping("jsonFullSpecialColums")
	public ModelAndView jsonFullSpecialColums(String ids, String fields, String clsName) {
		return toJSONView(auditLogManager.getFullSpecialColumsAuditJson(ids, fields, clsName));
	}

	protected JSONArray buildAuditJSONArray(Collection<Object[]> list,String clazz) {
		JSONArray ja = new JSONArray();
		JSONObject obj = null;
		for (Object[] objects : list) {
			IAudit t = (IAudit)objects[0];
			obj = t.toAuditJson(clazz);
			
			RevBaseEntity r = (RevBaseEntity)objects[1];
			obj.put("modDate", FormatUtils.dateTimeValue(r.getDate()));
			obj.put("modBy", r.getUserName());
			obj.put("version", r.getId());
			obj.put("id", r.getId());
			ja.put(obj);
		}
		return ja;
	}

	@Override
	protected AbsEntityService getEntityService() {
		// TODO Auto-generated method stub
		return null;
	}


}
