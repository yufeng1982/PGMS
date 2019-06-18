/**
 * 
 */
package com.photo.pgm.core.dao;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.pgm.core.model.YearBudgets;

/**
 * @author FengYu
 *
 */
public interface YearBudgetsRepository extends AbsCodeNameEntityRepository<YearBudgets> {

	public YearBudgets findByYears(Integer years);
}
