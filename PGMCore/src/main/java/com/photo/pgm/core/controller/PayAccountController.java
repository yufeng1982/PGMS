/**
 * 
 */
package com.photo.pgm.core.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.utils.Calc;
import com.photo.bas.core.web.controller.entity.AbsFormController;
import com.photo.pgm.core.model.Contract;
import com.photo.pgm.core.model.PayAccount;
import com.photo.pgm.core.service.PayAccountService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/payAccount/form")
public class PayAccountController extends AbsFormController<PayAccount> {

	private final static String PAGE_FORM_PATH = "project/payAccount/payAccount";
	
	@Autowired private PayAccountService payAccountService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("payAccount:show")
	public String showPayAccount(@ModelAttribute("entity") PayAccount payAccount, ModelMap modelMap) {
		Contract contract = payAccount.getContract();
		double settleAmount = Calc.add(contract.getAmount(), contract.getAdjustAmount());
		modelMap.addAttribute("settleAccounts", settleAmount);
		modelMap.addAttribute("finishPercent", settleAmount == 0 ? 0 : Calc.div(contract.getPayAmount(), settleAmount) * 100);
		return PAGE_FORM_PATH;
	}
	

	@Override
	protected PayAccountService getEntityService() {
		return payAccountService;
	}

	@ModelAttribute("entity")
	public PayAccount populateEntity(@PathVariable(value="id") String id) {
		PayAccount payAccount = null;
		if(isNew(id)) {
			payAccount = new PayAccount();
		} else {
			payAccount = getEntityService().get(id);
		}
		return payAccount;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC31");
	}
	
}
