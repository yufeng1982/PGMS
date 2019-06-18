package com.photo.bas.core.service.log;

import java.util.Vector;

import com.photo.bas.core.model.common.EventLog;
import com.photo.bas.core.utils.PageInfo;

public interface EventLogManager {
	
	public void addLog(EventLog log, int batch);
	
	public void save(Vector<EventLog> list);
	
	public void save(EventLog eventLog);
	
	public PageInfo<EventLog> loadPagedLogs(PageInfo<EventLog> page);
	
}
