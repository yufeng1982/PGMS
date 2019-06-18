/**
 * 
 */
package com.photo.bas.core.dao.common;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.model.common.Province;

/**
 * @author FengYu
 *
 */
@Repository
public interface ProvinceRepository extends AbsCodeNameEntityRepository<Province> {

	public List<Province> findByCountryIdAndActiveTrue(String countryId);
}
