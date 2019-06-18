/**
 * 
 */
package com.photo.bas.core.dao.common;

import org.springframework.stereotype.Repository;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.model.common.Country;

/**
 * @author FengYu
 *
 */
@Repository
public interface CountryRepository extends AbsCodeNameEntityRepository<Country> {

}
