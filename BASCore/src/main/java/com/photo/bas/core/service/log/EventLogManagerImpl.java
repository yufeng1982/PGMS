package com.photo.bas.core.service.log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.photo.bas.core.model.common.EventLog;
import com.photo.bas.core.utils.PageInfo;

@Service("eventLogManager")
public class EventLogManagerImpl implements EventLogManager,DisposableBean {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void save(final Vector<EventLog> list) {
		if (list == null || list.size() == 0) {
			return;
		}
		String sql = "insert into event_log(user_name,session_id,url,consumption,remote_addr,params,request_date ) values (?,?,?,?,?,?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				EventLog log = list.get(i);
				ps.setString(1, log.getUserName());
				ps.setString(2, log.getSessionId());
				ps.setString(3, log.getUrl());
				ps.setLong(4, log.getConsumption());
				ps.setString(5, log.getRemoteAddr());
				ps.setString(6, log.getParams());
				ps.setTimestamp(7, new java.sql.Timestamp(log.getRequestDate().getTime()));
			}

			public int getBatchSize() {
				return list.size();
			}
		});
		list.clear();
	}

	@Override
	public void save(EventLog log) {
		String sql = "insert into event_log(user_name,session_id,url,consumption,remote_addr,params,request_date ) values (?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, log.getUserName(), log.getSessionId(), log.getUrl(), log.getConsumption(),
				log.getRemoteAddr(), log.getParams(), new java.sql.Timestamp(log.getRequestDate().getTime()));
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageInfo<EventLog> loadPagedLogs(PageInfo<EventLog> page) {
		String sql = "SELECT * FROM event_log ORDER BY request_date DESC LIMIT ? OFFSET ?";
		List<EventLog> list = jdbcTemplate.query(sql, new LogMapper(), page.getPageSize(), page.getBeginIndex());
		page.setResult(list);
		sql = "SELECT COUNT(1) FROM event_log ";
		page.setTotalCount(jdbcTemplate.queryForLong(sql));
		return page;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@SuppressWarnings("rawtypes")
	protected class LogMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			EventLog log = new EventLog();
			log.setUserName(rs.getString("user_name"));
			log.setSessionId(rs.getString("session_id"));
			log.setConsumption(rs.getLong("consumption"));
			log.setUrl(rs.getString("url"));
			log.setRemoteAddr(rs.getString("remote_addr"));
			log.setParams(rs.getString("params"));
			log.setRequestDate(new Date(rs.getTimestamp("request_date").getTime()));
			return log;
		}
	}
	
	/*
	 *  1. cache a batch of request logs save at once
	 *  2.thread safe cache
	 */
	private Vector<EventLog> defaultLogs = new Vector<EventLog>();

	public Vector<EventLog> getLogs() {
		return defaultLogs;
	}

	public void switchLogs() {
		new Thread(new ConcurrentUtils(defaultLogs)).run();
		defaultLogs = new Vector<EventLog>();
	}

	public synchronized void addLog(EventLog log, int batch) {
		try {
			if (batch <= 0) {
				return;
			}
			defaultLogs.add(log);
			if (defaultLogs.size() >= batch) {
				switchLogs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void destroy() throws Exception {
		System.out.println("Destroy now!!");
		if (defaultLogs.size() > 0) {
			switchLogs();
		}		
	}
	
	public class ConcurrentUtils implements Runnable {

		private Vector<EventLog> logs;
		
		public ConcurrentUtils(Vector<EventLog> logs) {
			this.logs = logs;
		}
		
		@Override
		public void run() {
			save(logs);
			Thread.yield();
		}
	}
}
