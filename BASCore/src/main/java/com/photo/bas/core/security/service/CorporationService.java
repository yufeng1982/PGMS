/**
 * 
 */
package com.photo.bas.core.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.photo.bas.core.dao.security.CorporationRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.vo.security.CorporationPageInfo;

/**
 * @author FengYu
 *
 */
@Component
public class CorporationService extends AbsCorporationService<Corporation, CorporationPageInfo> {
	
	@Autowired CorporationRepository corporationRepository;
	
	public Corporation findByActiveTrue(){
		return getRepository().findByActiveTrue();
	}
	
	@Override
	protected CorporationRepository getRepository() {
		return corporationRepository;
	}

}
