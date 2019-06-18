/**
 * 
 */
package com.photo.bas.core.web.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.Gender;
import com.photo.bas.core.model.crm.Customer;
import com.photo.bas.core.service.crm.CustomerService;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.web.controller.entity.AbsCodeEntityController;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/core/crm/customer/form")
public class CustomerController extends AbsCodeEntityController<Customer> {

	private final static String PAGE_FORM_PATH = "crm/customer/customer";
	private final static String PATH = "crm/customer/form/";
	
	@Autowired private CustomerService customerService;
	
	/**===============================backend code======================================================================*/
	@RequestMapping(value = "{id}/show")
	public String show(@ModelAttribute("entity") Customer customer, ModelMap modelMap) {
		modelMap.addAttribute("gender", Gender.getGenders());
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping("{id}/apply")
	public ModelAndView save(@ModelAttribute("entity") Customer customer, ModelMap modelMap) {
		customerService.save(customer);
		return redirectTo(PATH + customer.getId() + "/show", modelMap);
	}
	
	@RequestMapping("{id}/ok")
	public ModelAndView ok(@ModelAttribute("entity") Customer customer, ModelMap modelMap) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			customerService.save(customer);
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(customer.getId()) ? NEW_ENTITY_ID : customer.getId()) + "/show", modelMap);
	}
	
	/**===============================front code======================================================================*/
	private final static String FRONT_PATH = "crm/customer/form/page/";
	private final static String FRONT_PAGE_FORM_PATH = "front/customer";
	
	@RequestMapping(value = "/page/{id}/show")
	public String showFront(@ModelAttribute("entity") Customer customer, ModelMap modelMap) {
		return FRONT_PAGE_FORM_PATH;
	}
	
	@RequestMapping("/page/{id}/apply")
	public ModelAndView saveFront(@ModelAttribute("entity") Customer customer, ModelMap modelMap) {
		customerService.save(customer);
		return redirectTo(FRONT_PATH + customer.getId() + "/show", modelMap);
	}
	
	@RequestMapping("/page/{id}/vPhone")
	@ResponseBody
	public String vPhone(@PathVariable(value = "id") String id, @RequestParam(value="phone",required = false) String phone){
		Customer customer = customerService.get(id);
		if (customer.getPhone().equals(phone)) return "true";
		Customer cus = customerService.findByPhoneAndActiveTrue(phone);
		if (cus != null) return "false";
		return "true";
	}
	
	@RequestMapping("/page/{id}/vEmail")
	@ResponseBody
	public String vEmail(@PathVariable(value = "id") String id, @RequestParam(value="email",required = false) String email){
		Customer customer = customerService.get(id);
		if (customer.getEmail().equals(email)) return "true";
		List<Customer> cusList = customerService.findAll("email", email);
		if (cusList.size() > 0) return "false";
		return "true";
	}
	
	@Override 
	protected CustomerService getEntityService() {
		return customerService;
	}

	@Override
	protected Customer newInstance(WebRequest request) {
		return new Customer();
	}

}
