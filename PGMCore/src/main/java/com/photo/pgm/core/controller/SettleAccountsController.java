/**
 * 
 */
package com.photo.pgm.core.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.web.controller.entity.AbsFormController;
import com.photo.pgm.core.enums.AdjustType;
import com.photo.pgm.core.model.SettleAccounts;
import com.photo.pgm.core.service.SettleAccountsService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/settleAccounts/form")
public class SettleAccountsController extends AbsFormController<SettleAccounts> {

	private final static String PAGE_FORM_PATH = "project/settleAccounts/settleAccounts";
	private final static String PATH = "project/settleAccounts/form/";
	
	@Autowired private SettleAccountsService settleAccountsService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("settleAccounts:show")
	public String showPayAccount(@ModelAttribute("entity") SettleAccounts settleAccounts, ModelMap modelMap) {
		modelMap.addAttribute("adjustType", AdjustType.getAdjustTypes());
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping("{id}/apply")
	@RequiresPermissions("settleAccounts:apply")
	public ModelAndView save(HttpServletRequest request, 
								@ModelAttribute("entity") SettleAccounts settleAccounts, 
								@RequestParam(value="attachment1" , required = false) MultipartFile attachment1,
							 	ModelMap modelMap) {
		saveSettleAccounts(settleAccounts, attachment1, request);
		
		return redirectTo(PATH + settleAccounts.getId() + "/show", modelMap);
	}

	@RequestMapping("{id}/ok")
	@RequiresPermissions("settleAccounts:ok")
	public ModelAndView ok(HttpServletRequest request, 
								@ModelAttribute("entity") SettleAccounts settleAccounts, 
								@RequestParam(value="attachment1" , required = false) MultipartFile attachment1,
							 	ModelMap modelMap) {
		if(saveSettleAccounts(settleAccounts, attachment1, request)){
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(settleAccounts.getId()) ? NEW_ENTITY_ID : settleAccounts.getId()) + "/show", modelMap);
	}
	
	@RequestMapping("{id}/downLoad")
	@RequiresPermissions("settleAccounts:downLoad")
	@ResponseBody
	public void downLoad(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("entity") SettleAccounts settleAccounts) {
		String folder = "settleAccounts" + settleAccounts.getId();
		String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/settleAccounts");
		String filePath = settleAccounts.getFilePath();
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		File file = new File(fileDir + "/"  + folder + "/" + fileName);
		downLoadFile(response, fileName, file);
	}
	
	private boolean saveSettleAccounts(SettleAccounts settleAccounts, MultipartFile attachment1, HttpServletRequest request) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/settleAccounts");
			getEntityService().save(settleAccounts, attachment1, fileDir);
		}
		return isSucceed;
	}

	@Override
	protected SettleAccountsService getEntityService() {
		return settleAccountsService;
	}

	@ModelAttribute("entity")
	public SettleAccounts populateEntity(@PathVariable(value="id") String id) {
		SettleAccounts settleAccounts = null;
		if(isNew(id)) {
			settleAccounts = new SettleAccounts();
		} else {
			settleAccounts = getEntityService().get(id);
		}
		return settleAccounts;
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC12");
	}
	
}
