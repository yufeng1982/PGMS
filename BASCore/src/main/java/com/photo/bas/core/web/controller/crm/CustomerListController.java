/**
 * 
 */
package com.photo.bas.core.web.controller.crm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.crm.Customer;
import com.photo.bas.core.service.crm.CustomerService;
import com.photo.bas.core.vo.crm.CustomerQueryInfo;
import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/core/crm/customer/list")
public class CustomerListController extends AbsQueryPagedListController<Customer, CustomerQueryInfo> {

	private final static String PAGE_LIST_PATH = "crm/customer/customers";
	
	@Autowired private CustomerService customerService;
	
	@RequestMapping
	public String show(ModelMap modelMap){
		return PAGE_LIST_PATH;
	}
	
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") CustomerQueryInfo queryInfo){
		Page<Customer> list = getEntityService().findCustomers(queryInfo);
		return toJSONView(list);
	}
	
	@Override
	public CustomerQueryInfo newPagedQueryInfo() {
		return new CustomerQueryInfo();
	}

	@Override
	protected CustomerService getEntityService() {
		return customerService;
	}

}
