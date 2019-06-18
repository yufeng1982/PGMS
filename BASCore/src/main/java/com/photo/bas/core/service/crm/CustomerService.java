/**
 * 
 */
package com.photo.bas.core.service.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.photo.bas.core.dao.contact.CustomerRepository;
import com.photo.bas.core.model.crm.Customer;
import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.vo.crm.CustomerQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
public class CustomerService extends AbsCodeNameEntityService<Customer, PageInfo<Customer>> {

	@Autowired private CustomerRepository customerRepository;
	
	public Customer findByPhoneAndActiveTrue(String phone) {
		return customerRepository.findByPhoneAndActiveTrue(phone);
	}
	public Page<Customer> findCustomers(final CustomerQueryInfo queryInfo) {
		return getRepository().findAll(byPage(queryInfo), queryInfo);
	}
	
	public List<Customer> findAll(String paramName, String paramValue) {
		return customerRepository.findAll(
				bySearchFilter(new PropertyFilter(paramName, paramValue, MatchType.EQ),
				PropertyFilter.activeFilter())
		);
	}
	
	@Override
	protected CustomerRepository getRepository() {
		return customerRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
