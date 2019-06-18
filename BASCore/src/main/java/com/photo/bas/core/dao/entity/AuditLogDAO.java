package com.photo.bas.core.dao.entity;

import java.util.Date;

import javax.persistence.Table;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.criteria.AuditId;
import org.hibernate.envers.query.internal.property.RevisionNumberPropertyName;
import org.hibernate.envers.query.order.AuditOrder;
import org.hibernate.envers.query.order.internal.PropertyAuditOrder;
import org.hibernate.envers.query.projection.AuditProjection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.photo.bas.core.model.entity.IAudit;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;

@Repository
public class AuditLogDAO extends DefaultEntityDAO<IAudit, String>{
	@Autowired
	@Qualifier("coreAppSetting") 
	private MessageSource coreAppSetting;
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public PageInfo<IAudit> findByAuditQuery(final PageInfo<IAudit> page, String clsName) {
		Assert.notNull(page);
		Assert.notNull(page.getSf_EQ_id());
		Assert.notNull(clsName);
		Assert.notNull(SourceEntityType.fromName(clsName));
		
		Class<?> clazz = SourceEntityType.fromName(clsName).getClazz();
		AuditQuery c = getAuditReader().createQuery().forRevisionsOfEntity(clazz , false, true);
		
		c.add(AuditEntity.id().eq(page.getSf_EQ_id()));

		AuditOrder o = new PropertyAuditOrder(new RevisionNumberPropertyName(), false);
		c.addOrder(o);
		long totalCount = 0;
		if (page.isAutoCount()) {
			AuditQuery t = getAuditReader().createQuery().forRevisionsOfEntity(clazz, false, true);
			t.add(AuditEntity.id().eq(page.getSf_EQ_id()));
			totalCount = countAuditQueryResult(t, page);
			page.setTotalCount(totalCount);
		}

		c.setFirstResult(page.getBeginIndex()); 
		c.setMaxResults(page.getPageSize());
		page.setObjResult(c.getResultList());
		return page;
	}
	public JSONArray getFullSpecialColumsAuditJson(String ids, String fields, String clsName) {
		Assert.notNull(ids);
		Assert.notNull(fields);
		Assert.notNull(clsName);
		Assert.notNull(SourceEntityType.fromName(clsName));
		PageInfo<IAudit> page = new PageInfo<IAudit>();
		page.setPageSizeDirect(1);
		Class<?> clazz = SourceEntityType.fromName(clsName).getClazz();
		StringBuffer s = new StringBuffer();
		
		JSONArray r = new JSONArray();
		String [] arrId = ids.split(",");
		for (int i = 0; i < arrId.length; i++) {
			page.setSf_EQ_id(arrId[i]);
			if(!Strings.isEmpty(s.toString())) {
				s.append(" UNION ALL ");
			}
			s.append("(").append(buildAuditSql(clazz, fields, page, false, false, true)).append(")");
		}
		System.out.println(s);
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(s.toString());
		int i;
		String [] clos = fields.split(",");
		while(!rowSet.isLast()){
			i = 1;
			rowSet.next();
			JSONObject o = new JSONObject();
			o.put("id", rowSet.getObject(i++));
			o.put("version", rowSet.getObject(i++));
			o.put("revType", rowSet.getObject(i++));
			
			for (int j = 0; j < clos.length; j++) {
				Object v = rowSet.getObject(i++);
				if(v instanceof Date) {
					o.put(clos[j], FormatUtils.dateTimeValue((Date)v));
				} else {
					o.put(clos[j], v);
				}
			}
			i++;
			o.put("modDate", FormatUtils.dateTimeValue(new Date((Long)rowSet.getObject(i++))));
			o.put("modBy", rowSet.getObject(i++));
			r.put(o);
		}
		return r;
	}
	public JSONObject getSpecialColumsAuditJson(final PageInfo<IAudit> page, String clsName, String fields) {
		Assert.notNull(page);
		Assert.notNull(page.getSf_EQ_id());
		Assert.notNull(clsName);
		Assert.notNull(SourceEntityType.fromName(clsName));
		
		Class<?> clazz = SourceEntityType.fromName(clsName).getClazz();
		
		String sql = buildAuditSql(clazz, fields, page, false, false, false);
		JSONArray r = new JSONArray();
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
		int i;
		String [] clos = fields.split(",");
		while(!rowSet.isLast()){
			i = 2;
			rowSet.next();
			JSONObject o = new JSONObject();
			
			o.put("version", rowSet.getObject(i++));
			o.put("revType", rowSet.getObject(i++));
			
			for (int j = 0; j < clos.length; j++) {
				Object v = rowSet.getObject(i++);
				if(v instanceof Date) {
					o.put(clos[j], FormatUtils.dateTimeValue((Date)v));
				} else {
					o.put(clos[j], v);
				}
			}
			i++;
			o.put("modDate", FormatUtils.dateTimeValue(new Date((Long)rowSet.getObject(i++))));
			o.put("modBy", rowSet.getObject(i++));
			r.put(o);
		}
		
		sql = buildAuditSql(clazz, fields, page, true, false, false);
		JSONObject jso = new JSONObject();
		jso.put("data", r);
		jso.put("totalCount", jdbcTemplate.queryForInt(sql));
		return jso;
	}
	public JSONObject getDocLastModified(String documentHeaderId, String headerClassName, String lineClassName, String headerFields, String lineFields) {
		PageInfo<IAudit> page = new PageInfo<IAudit>();
		page.setSf_EQ_id(documentHeaderId);
		page.setPageSize(1);
		Assert.notNull(page.getSf_EQ_id());
		Assert.notNull(headerClassName);
		Assert.notNull(lineClassName);
		Assert.notNull(headerFields);
		Assert.notNull(lineFields);
		Assert.notNull(SourceEntityType.fromName(headerClassName));
		Assert.notNull(SourceEntityType.fromName(lineClassName));
		
		Class<?> clazz = SourceEntityType.fromName(headerClassName).getClazz();
		
		// get header changed
		String sql = buildAuditSql(clazz, headerFields, page, false, false, false);
		System.out.println(sql);
		System.out.println("==================");
		JSONArray r = new JSONArray();
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
		int i;
		String [] clos = headerFields.split(",");
		while(!rowSet.isLast()){
			i = 2;
			rowSet.next();
			JSONObject o = new JSONObject();
			
			o.put("version", rowSet.getObject(i++));
			o.put("revType", rowSet.getObject(i++));
			for (int j = 0; j < clos.length; j++) {
				Object v = rowSet.getObject(i++);
				if(v instanceof Date) {
					o.put(clos[j], FormatUtils.dateTimeValue((Date)v));
				} else {
					o.put(clos[j], v);
				}
			}
			i++;
			o.put("modDate", FormatUtils.dateTimeValue(new Date((Long)rowSet.getObject(i++))));
			o.put("modBy", rowSet.getObject(i++));
			r.put(o);
		}
		
		// get lines changed
		clazz = SourceEntityType.fromName(lineClassName).getClazz();
		sql = buildAuditSql(clazz, lineFields, page, false, true, false);
		System.out.println(sql);
		clos = lineFields.split(",");
		rowSet = jdbcTemplate.queryForRowSet(sql);
		while(!rowSet.isLast()){
			i = 2;
			rowSet.next();
			JSONObject o = new JSONObject();
			
			o.put("version", rowSet.getObject(i++));
			o.put("revType", rowSet.getObject(i++));
			
			for (int j = 0; j < clos.length; j++) {
				Object v = rowSet.getObject(i++);
				if(v instanceof Date) {
					o.put(clos[j], FormatUtils.dateTimeValue((Date)v));
				} else {
					o.put(clos[j], v);
				}
			}
			i++;
			o.put("modDate", FormatUtils.dateTimeValue(new Date((Long)rowSet.getObject(i++))));
			o.put("modBy", rowSet.getObject(i++));
			r.put(o);
		}
		long lastVersion = 0;
		int k = -1;
		for (int j = 0; j < r.length(); j++) {
			JSONObject o = r.getJSONObject(j);
			if(lastVersion < o.getLong("version")) {
				lastVersion = o.getLong("version");
				k = j;
			}
		}
		if(r.length() > 0 && k >= 0) {
			return r.getJSONObject(k);
		}
		return new JSONObject();
	}
	public String getChangedIds(String[] ids, String clsName, String fields) {
		Assert.notNull(ids);
		Assert.notNull(clsName);
		Assert.notNull(SourceEntityType.fromName(clsName));
		StringBuffer mainSql = new StringBuffer();
		StringBuffer conditions = new StringBuffer();
		StringBuffer expression = new StringBuffer();
		StringBuffer changedIds = new StringBuffer();
		
		Class<?> clazz = SourceEntityType.fromName(clsName).getClazz();
		
		String tableName = getAuditTableName(clazz);
		String colums = fields.replaceAll("(\\p{Ll})(\\p{Lu})", "$1_$2").replaceAll(" ", "").toLowerCase();
		bulidCols(colums,expression ,conditions );
		String fullColums = "id,rev,"+colums;
		
		mainSql.append("SELECT id FROM (");
		mainSql.append("SELECT ").append(fullColums).append(expression).append(" FROM ").append(tableName).append(" WHERE id in(").append(ids).append(") ");
		mainSql.append(") X WHERE ").append(conditions);
		mainSql.append(" group by id having count(id) > 1");
//		System.out.println(mainSql.toString());
		
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(mainSql.toString());
		while(!rowSet.isLast()){
			rowSet.next();
			changedIds.append(rowSet.getObject(1)).append(",");
		}
		return changedIds.toString();
	}
	
	public JSONArray getChangedIds(String documentHeaderId, String clsName, String fields) {
		Assert.notNull(documentHeaderId);
		Assert.notNull(clsName);
		Assert.notNull(SourceEntityType.fromName(clsName));
		
		StringBuffer mainSql = new StringBuffer();
		StringBuffer conditions = new StringBuffer();
		StringBuffer expression = new StringBuffer();
		
		Class<?> clazz = SourceEntityType.fromName(clsName).getClazz();
		
		String tableName = getAuditTableName(clazz);
		String colums = fields.replaceAll("(\\p{Ll})(\\p{Lu})", "$1_$2").replaceAll(" ", "").toLowerCase();
		bulidCols(colums,expression ,conditions );
		String fullColums = "id,code,rev,"+colums;
		
		mainSql.append("SELECT id FROM (");
		mainSql.append("SELECT ").append(fullColums).append(expression).append(" FROM ").append(tableName).append(" WHERE header ='").append(documentHeaderId).append("' ");
		mainSql.append(") X WHERE ").append(conditions);
		mainSql.append(" group by id having count(id) > 1");
		
//		System.out.println(mainSql.toString());
		
		JSONArray r = new JSONArray();
		
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(mainSql.toString());
		while(!rowSet.isLast()){
			rowSet.next();
			JSONObject o = new JSONObject();
			o.put("id", rowSet.getObject(1));
//			o.put("code", rowSet.getObject(2));
			r.put(o);
		}
		return r;
	}
	
	protected Long countAuditQueryResult(final AuditQuery c, final PageInfo<IAudit> page) {

		AuditId a = new AuditId();
		AuditProjection count = a.count("id");
		long totalCount =(Long) c.addProjection(count).getSingleResult();

		return totalCount;
	}
	
	protected AuditReader getAuditReader() {
		return AuditReaderFactory.get(getSession());
	}
	
	private String getTableName(Class<?> c) {
		Table t = c.getAnnotation(Table.class);
		if(t != null) {
			if(!Strings.isEmpty(t.name())) {
				return t.name(); 
			} else {
				return c.getSimpleName().replaceAll("(\\p{Ll})(\\p{Lu})", "$1_$2").toLowerCase(); 
			}
			
		} else {
			return getTableName(c.getSuperclass());
		}
	}
	
	private String getAuditTableName(Class<?> c) {
		String schema = coreAppSetting.getMessage("org.hibernate.envers.default_schema", new Object[0], ThreadLocalUtils.getCurrentLocale());
		String prefix = coreAppSetting.getMessage("org.hibernate.envers.audit_table_prefix", new Object[0], ThreadLocalUtils.getCurrentLocale());
		String suffix = coreAppSetting.getMessage("org.hibernate.envers.audit_table_suffix", new Object[0], ThreadLocalUtils.getCurrentLocale());
		return schema +"." + prefix + getTableName(c) + suffix;
	}
	
	private String buildAuditSql(Class<?> c, String fields, final PageInfo<IAudit> page, boolean isForCount, boolean isForDocLine, boolean oneLine) {
		
		StringBuffer sql = new StringBuffer();
		StringBuffer mainSql = new StringBuffer();
		StringBuffer conditions = new StringBuffer();
		StringBuffer expression = new StringBuffer();
		String defaultKey = "id";
		if(isForDocLine) {
			defaultKey = "header";
		}
		String tableName = getAuditTableName(c);
		String colums = fields.replaceAll("(\\p{Ll})(\\p{Lu})", "$1_$2").replaceAll(" ", "").toLowerCase();
		bulidCols(colums,expression ,conditions );
		
		String fullColums = "id,rev,rev_type,"+colums;
		
		mainSql.append("SELECT *,row_number() OVER (ORDER BY rev) AS rownum FROM (");
		mainSql.append("SELECT ").append(fullColums).append(expression).append(" FROM ").append(tableName).append(" WHERE ").append(defaultKey).append("='").append(page.getSf_EQ_id()).append("' ");
		mainSql.append(") X WHERE ").append(conditions);
//		System.out.println(mainSql.toString());
		if(isForCount) {
			sql.append("SELECT count(1) ");
		} else {
			sql.append("SELECT ").append(this.addAlias(fullColums, "E")).append(",rownum,F.timestamp,F.user_name ");
		}
		sql.append(" FROM (");
		sql.append(mainSql).append(") E inner join audit.rev_base_entity F on E. rev = F.id");
		if(!isForCount) {
			if(oneLine) {
				sql.append(" WHERE rownum > 0");
			}
			
			sql.append(" ORDER BY rev desc");
			sql.append(" offset ").append(page.getBeginIndex()).append("  limit ").append(page.getPageSize());
		}
		System.out.println(sql.toString());
		return sql.toString();
	}
	private String addAlias(String sql, String alias) {
		return alias + "." + sql.replace(",", ","+alias+".");
	}
	private void bulidCols(String columns ,StringBuffer e, StringBuffer c) {
		String [] clos = columns.split(",");
		String col, col2;
		for (int i = 0; i < clos.length; i++) {
			col = clos[i];
			col2 = col+"_diff";
			if(i ==  clos.length-1) {
				c.append("(").append(col).append(" != ").append(col2);
				c.append(" OR (").append(col).append(" is null AND ").append("").append(col2).append(" is not null)");
				c.append(" OR (").append(col).append(" is not null AND ").append("").append(col2).append(" is null))");
			} else {
				c.append("(").append(col).append(" != ").append(col2);
				c.append(" OR (").append(col).append(" is null AND ").append("").append(col2).append(" is not null)");
				c.append(" OR (").append(col).append(" is not null AND ").append("").append(col2).append(" is null))").append(" OR ");
			}
			e.append(",").append("lag(").append(col).append(") OVER (ORDER BY id,rev) as ").append(col2);
		}
	}
	
	public long getRevisionNumber(String id, Class<?> c) {
		Assert.notNull(id);
		Assert.notNull(c);
		String tableName = getAuditTableName(c);
		
		StringBuffer sql = new StringBuffer("SELECT max(rev) from ");
		sql.append(tableName).append(" where id='").append(id).append("'");
		return jdbcTemplate.queryForLong(sql.toString());
	}
	public long getRevisionNumber(String id, SourceEntityType type) {
		Assert.notNull(id);
		Assert.notNull(type);
		Class<?> c = type.getClazz();
		return getRevisionNumber(id, c);
	}
	
}
