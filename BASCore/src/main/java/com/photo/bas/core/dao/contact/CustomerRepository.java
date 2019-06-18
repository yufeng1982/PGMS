/**
 * 
 */
package com.photo.bas.core.dao.contact;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.model.crm.Customer;

/**
 * @author FengYu
 *
 */
public interface CustomerRepository extends AbsCodeNameEntityRepository<Customer>{
	public Customer findByPhoneAndActiveTrue(String phone);
}
