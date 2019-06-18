/**
 * 
 */
package com.photo.bas.core.model.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.AssertFalse;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.exception.EntityOptimisticLockingFailureException;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.EntityReflectionUtils;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.bas.core.utils.ProxyTargetConverterUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;

/**
 * @author FengYu
 * 
 */
@MappedSuperclass
public abstract class AbsEntity implements IEntity, IDelete, Serializable {
	private static final long serialVersionUID = -6684519880340264939L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	@Column(length = 40)
	@Access(value = AccessType.PROPERTY)
	private String id;

	@Column(length = 255)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date modificationDate;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(foreignKey=@ForeignKey(name = "none")) 
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User createdBy;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(foreignKey=@ForeignKey(name = "none")) 
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User modifiedBy;

	@ManyToOne
	private Corporation corporation;

	@Type(type = "true_false")
	@Audited
	private Boolean active = Boolean.TRUE;

	@Version
	private Integer version;

	@Transient
	private Integer originVersion;

	public AbsEntity() {
		super();
		this.setCorporation(ThreadLocalUtils.getCurrentCorporation());
	}

	public AbsEntity(Corporation corporation) {
		this();
		this.corporation = corporation;
	}

	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Corporation getCorporation() {
		return corporation;
	}

	public void setCorporation(Corporation corporation) {
		this.corporation = corporation;
	}

	public Boolean isActive() {
		if(active == null) return Boolean.FALSE;
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getOriginVersion() {
		if (originVersion == null) {
			this.originVersion = this.version;
		}
		return originVersion;
	}

	public void setOriginVersion() {
		this.originVersion = this.version;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		setOriginVersion();
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;

		if (!ProxyTargetConverterUtils.getProxyImplementationObject(this).getClass().equals(ProxyTargetConverterUtils.getProxyImplementationObject(obj).getClass())) return false;
		
		final AbsEntity other = (AbsEntity) ProxyTargetConverterUtils.getProxyImplementationObject(obj);
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@AssertFalse(message = EntityOptimisticLockingFailureException.OPTIMISTIC_FAILURE_ERROR_KEY)
	public boolean isOptimisticLockingFailure() {
		if (getOriginVersion() == null)
			return false;
		boolean isBadVersion = !getOriginVersion().equals(this.version);
		originVersion = null;
		return isBadVersion;
	}

	@Override
	public int hashCode() {
		return (id == null) ? 0 : id.hashCode();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("id", FormatUtils.stringValue(id));
		jo.put("creationDate", FormatUtils.dateTimeValue(creationDate));
		jo.put("modificationDate", FormatUtils.dateTimeValue(modificationDate));
		jo.put("description", FormatUtils.stringValue(description));
		jo.put("corporation", FormatUtils.idString(corporation));
		jo.put("corporationName", FormatUtils.stringValue(corporation == null ? "" : corporation.getName()));
		jo.put("displayString", FormatUtils.stringValue(getDisplayString()));
		return jo;
	}
	
	@Override
	public JSONObject toJSONObjectAll() {
		return toJSONObject();
	}

	public JSONObject toAuditJson(String clazz) {
		Class<?> c = SourceEntityType.fromName(clazz).getClazz();
		JSONObject jsonObject = new JSONObject();
		
		buildAuditJson(c, this, jsonObject);

		return jsonObject;
	}

	private void buildAuditJson(Class<?> c, Object auditObj, JSONObject jsonObject) {
		Field[] fields = null;
		while (c != null) {
			fields = c.getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals("serialVersionUID")) {
					continue;
				}
				if (c.getAnnotation(Audited.class) == null) {
					if (field.getAnnotation(Audited.class) != null) {
						if(field.getAnnotation(Embedded.class) != null) {
							buildAuditJson(field.getType(), EntityReflectionUtils.invokeGetterMethod(auditObj, field.getName()), jsonObject);
						} else {
							setAuditJson(c, auditObj, field, jsonObject);
						}
					}
				} else {
					if(field.getAnnotation(Embedded.class) != null) {
						buildAuditJson(field.getType(), EntityReflectionUtils.invokeGetterMethod(auditObj, field.getName()), jsonObject);
					} else if(field.getAnnotation(NotAudited.class) == null){
						setAuditJson(c, auditObj, field, jsonObject);
					}
				}
			}
			c = c.getSuperclass();
		}
	}

	@SuppressWarnings("rawtypes")
	private void setAuditJson(Class c, Object auditObj, Field field, JSONObject jsonObject) {
		try {
			Object o = EntityReflectionUtils.invokeGetterMethod(auditObj, field.getName());
			String value = "";
			if(o == null) {
				value = "";
			} else if (String.class.equals(field.getType()) || Double.class.equals(field.getType())
					|| Integer.class.equals(field.getType()) || Long.class.equals(field.getType())
					|| Float.class.equals(field.getType()) || BigDecimal.class.equals(field.getType())) {
				value = o.toString();
			} else if(Date.class.equals(field.getType())) {
				value = FormatUtils.dateTimeValue((Date)o);
			} else if(Boolean.class.equals(field.getType())) {
				boolean vb = (Boolean)o;
				jsonObject.put(field.getName(), vb);
			} else if(field.getType().isEnum()) {
				value = ((IEnum)o).getText();
			} else {
				IEntity t = (IEntity) ProxyTargetConverterUtils.getProxyImplementationObject(o);
//				if(o != null && o.getClass().equals(AbsWarehouse.class)) {
//					new Date();
//				}
				value = t.getDisplayString();
			}
//			System.out.println(field.getName() + ":" + value);
			if(!jsonObject.has(field.getName()))
				jsonObject.put(field.getName(), value);
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	@Override
	public boolean isNewEntity() {
		return Strings.isEmpty(getId());
	}

	@Override
	public boolean isRealDelete() {
		return false;
	}

	@Override
	public void setToInactive() {
		this.setActive(false);
	}
	
	public String getDisplayDescription() {
		return null;
	}
	

	public Boolean getActive() {
		return active;
	}
	
}
