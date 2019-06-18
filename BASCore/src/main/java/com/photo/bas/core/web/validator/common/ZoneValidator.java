package com.photo.bas.core.web.validator.common;

import org.springframework.stereotype.Component;

import com.photo.bas.core.model.common.AbsZone;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;

@Component
public class ZoneValidator {

	public void validate(AbsZone zone) {
		
        if(Strings.isEmpty(zone.getCode())){
			ThreadLocalUtils.addErrorMsg(ResourceUtils.getText("Code.Not.Empty"));
		}
        
        if(zone.getCountry() == null){
        	ThreadLocalUtils.addErrorMsg(ResourceUtils.getText("Country.Not.Empty"));
        }
	}
}
