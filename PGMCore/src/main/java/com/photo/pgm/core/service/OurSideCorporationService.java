/**
 * 
 */
package com.photo.pgm.core.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.service.entity.AbsMaintenanceService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;
import com.photo.pgm.core.dao.OurSideCorporationRepository;
import com.photo.pgm.core.model.OurSideCorporation;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class OurSideCorporationService extends AbsMaintenanceService<OurSideCorporation, PageInfo<OurSideCorporation>> {

	@Autowired private OurSideCorporationRepository ourSideCorporationRepository;
	
	public OurSideCorporation buildGroup(JSONObject jo, Corporation cor){
		OurSideCorporation ourSideCorporation = null;
		if(Strings.isEmpty(jo.getString("id"))) {
			ourSideCorporation = new OurSideCorporation();
		} else {
			ourSideCorporation = get(jo.getString("id"));
		}
		if(jo.has("code")) {
			ourSideCorporation.setCode(jo.getString("code"));
		}
		if(jo.has("name")) {
			ourSideCorporation.setName(jo.getString("name"));
		}
		if(jo.has("description")) {
			ourSideCorporation.setDescription(jo.getString("description"));
		}
		if(jo.has("pct")) {
			ourSideCorporation.setPct(jo.getString("pct"));
		}
		if(jo.has("province")) {
			ourSideCorporation.setProvince(jo.getString("province"));
		}
		if(jo.has("city")) {
			ourSideCorporation.setCity(jo.getString("city"));
		}
		if(jo.has("town")) {
			ourSideCorporation.setTown(jo.getString("town"));
		}
		
		return ourSideCorporation;
	}
	
	@Override
	protected OurSideCorporationRepository getRepository() {
		return ourSideCorporationRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
