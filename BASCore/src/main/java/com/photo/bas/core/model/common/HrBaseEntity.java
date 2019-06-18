/**
 * 
 */
package com.photo.bas.core.model.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsEntity;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@MappedSuperclass
public abstract class HrBaseEntity extends AbsEntity {

	private static final long serialVersionUID = 7007212142946038861L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date entryDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date leaveDate;
	
	@Type(type = "true_false")
	private Boolean principal = Boolean.FALSE;
	
	public HrBaseEntity() {
		super();
	}
	
	@Override
	public String getDisplayString() {
		return null;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("entryDate", FormatUtils.dateValue(entryDate));
    	jo.put("leaveDate", FormatUtils.dateValue(leaveDate));
    	jo.put("primary", FormatUtils.booleanValue(principal));
    	return jo;
    }
}
