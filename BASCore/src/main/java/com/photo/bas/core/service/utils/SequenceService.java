package com.photo.bas.core.service.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.model.entity.ISerial;

@Service
@Transactional(readOnly = true)
public class SequenceService {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly = false)
	public void initSequence(String seqName) {
		long increment = 1;
	    long minValue = 1;
	    long maxValue = 9223372036854775807l;
	    long start = 1;
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE SEQUENCE ").append(seqName).append(" INCREMENT ").append(increment);
		sb.append(" MINVALUE ").append(minValue ).append(" MAXVALUE ").append(maxValue).append(" START ").append(start);	            	        
		jdbcTemplate.execute(sb.toString());
		
	}
	
	private String getSerialName(String serial, String orgnization){
		return serial + "_" +  orgnization;
	}
	
	@Transactional(readOnly = false)
	public void initSequenceByOrgnization(String orgnization) {
		List<String> serialList =  new ArrayList<String>();
		serialList.add(getSerialName(ISerial.CUSTOMER_SEQUENCE, orgnization));
		serialList.add(getSerialName(ISerial.WORKFLOW_SEQUENCE, orgnization));
//		serialList.add(getSerialName(ISerial.COOPERATION_SEQUENCE, orgnization));
		//TODO
		for (String serial : serialList) {
			initSequence(serial);
		}
	}
}
