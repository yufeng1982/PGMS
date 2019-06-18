/**
 * 
 */
package com.photo.pgm.core.dao;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.pgm.core.model.PetrolStation;

/**
 * @author FengYu
 *
 */
public interface PetrolStationRepository extends AbsCodeNameEntityRepository<PetrolStation> {

	public PetrolStation findByCodeAndCorporationAndActiveTrue(String code, Corporation corporation);
	public PetrolStation findBySapCodeAndCorporationAndActiveTrue(String sapcode, Corporation corporation);
	public PetrolStation findByCnCodeAndCorporationAndActiveTrue(String cncode, Corporation corporation);
}
