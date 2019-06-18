/**
 * 
 */
package com.photo.pgm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.YearBudgetsRepository;
import com.photo.pgm.core.model.YearBudgets;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class YearBudgetsService extends AbsCodeNameEntityService<YearBudgets, PageInfo<YearBudgets>> {

	@Autowired private YearBudgetsRepository yearBudgetsRepository;
	
	public YearBudgets findByYears(Integer years) {
		return getRepository().findByYears(years);
	}
	
	@Override
	protected YearBudgetsRepository getRepository() {
		return yearBudgetsRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
