/**
 * 
 */
package com.photo.bas.core.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.dao.common.PositionRepository;
import com.photo.bas.core.model.common.Position;
import com.photo.bas.core.service.entity.AbsMaintenanceService;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class PositionService extends AbsMaintenanceService<Position, PageInfo<Position>> {

	@Autowired private PositionRepository positionRepository;
	
	public Position findOne(String id){
		return positionRepository.findOne(id);
	}
	
	@Override
	protected PositionRepository getRepository() {
		return positionRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
