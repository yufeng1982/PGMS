/**
 * 
 */
package com.photo.bas.core.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.dao.common.EmployeePositionRepository;
import com.photo.bas.core.model.common.EmployeePosition;
import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.vo.common.EmployeePositionQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class EmployeePositionService extends AbsEntityService<EmployeePosition, PageInfo<EmployeePosition>> {

	@Autowired private EmployeePositionRepository employeePositionRepository;
	@Autowired private PositionService positionService;
	
	public Page<EmployeePosition> findByEmployee(EmployeePositionQueryInfo queryInfo){
		return getRepository().findAll(byPage(queryInfo), queryInfo);
	}
	
	@Override
	protected EmployeePositionRepository getRepository() {
		return employeePositionRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
