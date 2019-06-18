/**
 * 
 */
package com.photo.pgm.core.dao;

import com.photo.bas.core.dao.entity.AbsEntityRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.pgm.core.model.Contract;
import com.photo.pgm.core.model.PayAccount;

/**
 * @author FengYu
 *
 */
public interface PayAccountRepository extends AbsEntityRepository<PayAccount> {

	public PayAccount findByContractAndCorporationAndActiveTrue(Contract contract, Corporation corporation);
}
