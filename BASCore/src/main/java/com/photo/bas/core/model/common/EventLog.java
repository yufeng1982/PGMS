package com.photo.bas.core.model.common;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

import com.photo.bas.core.model.entity.IEntity;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author rxg
 * 
 */
public class EventLog implements IEntity, Serializable {

	private static final long serialVersionUID = 7128682056624504867L;
	
	private String userName;
	
	private String sessionId;

	private String url;
	
	private Date requestDate;
	
	private String remoteAddr;
	
	private long consumption;

	private String params;
	
	public EventLog() {
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getParams() {
		return params;
	}

	public void setConsumption(long consumption) {
		this.consumption = consumption;
	}

	public long getConsumption() {
		return consumption;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public String getDisplayString() {
		return null;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("userName", this.getUserName());
		jo.put("sessionId", this.getSessionId());
		jo.put("url", this.getUrl());
		jo.put("requestDate", FormatUtils.dateTimeValue(this.getRequestDate()));
		jo.put("remoteAddr", this.getRemoteAddr());
		jo.put("consumption", this.getConsumption());
		jo.put("params", this.getParams());
		return jo;
	}

	@Override
	public boolean isNewEntity() {
		return false;
	}

	@Override
	public JSONObject toJSONObjectAll() {
		// TODO Auto-generated method stub
		return toJSONObject();
	}

}
