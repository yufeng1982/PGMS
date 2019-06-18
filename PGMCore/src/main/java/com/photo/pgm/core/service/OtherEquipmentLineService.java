/**
 * 
 */
package com.photo.pgm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsNameEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.OtherEquipmentLineRepository;
import com.photo.pgm.core.model.OtherEquipmentLine;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class OtherEquipmentLineService extends AbsNameEntityService<OtherEquipmentLine, PageInfo<OtherEquipmentLine>> {

	@Autowired private OtherEquipmentLineRepository otherEquipmentLineRepository;
	
	@Override
	protected OtherEquipmentLineRepository getRepository() {
		return otherEquipmentLineRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
