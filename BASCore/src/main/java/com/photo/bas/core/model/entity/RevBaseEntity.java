package com.photo.bas.core.model.entity;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import org.json.JSONObject;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.FormatUtils;

@Entity
@RevisionEntity(RevisionEntityListener.class)
@Table(name = "rev_base_entity", schema = "audit")
public class RevBaseEntity {
    @Id
    @GeneratedValue
    @RevisionNumber
    private long id;

    @RevisionTimestamp
    private long timestamp;

	@ManyToOne(optional=true,fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private User user;
	
	private String userName; // for performance,same with user.getUsername()
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}
	public Date getDate() {
		return new Date(timestamp);
	}

	public long getId() {
		return id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
	
	public JSONObject toJSONObject() {
    	JSONObject jo = new JSONObject();
    	jo.put("modDate", FormatUtils.dateTimeValue(getDate()));
    	jo.put("modBy", userName);
    	jo.put("version", getId());
    	return jo;
	}

	public void setId(long id) {
		this.id = id;
	}
}
