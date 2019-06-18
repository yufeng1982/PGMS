/**
 * 
 */
package com.photo.bas.core.dao.security;

import com.photo.bas.core.model.security.Corporation;

/**
 * @author FengYu
 *
 */
public interface CorporationRepository extends AbsCorporationRepository<Corporation> {

	public Corporation findByActiveTrue();
}
