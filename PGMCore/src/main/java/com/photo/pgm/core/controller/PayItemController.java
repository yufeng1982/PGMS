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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.web.controller.entity.AbsCodeEntityController;
import com.photo.pgm.core.enums.PayType;
import com.photo.pgm.core.model.PayItem;
import com.photo.pgm.core.service.PayItemService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/payItem/form")
public class PayItemController extends AbsCodeEntityController<PayItem> {

	private final static String PAGE_FORM_PATH = "project/payItem/payItem";
	private final static String PATH = "project/payItem/form/";
	
	@Autowired private PayItemService payItemService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("payItem:show")
	public String showCooperation(@ModelAttribute("entity") PayItem payItem, ModelMap modelMap) {
		modelMap.addAttribute("payStatus", PayType.getPayTypes());
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping("{id}/apply")
	@RequiresPermissions("payItem:apply")
	public ModelAndView save(HttpServletRequest request, 
								@ModelAttribute("entity") PayItem payItem, 
								@RequestParam(value="attachment1" , required = false) MultipartFile attachment1,
								@RequestParam(value="attachment2" , required = false) MultipartFile attachment2,
							 	ModelMap modelMap) {
		savePayItem(payItem, attachment1, attachment2,  request);
		
		return redirectTo(PATH + payItem.getId() + "/show", modelMap);
	}

	@RequestMapping("{id}/ok")
	@RequiresPermissions("payItem:ok")
	public ModelAndView ok(HttpServletRequest request, 
								@ModelAttribute("entity") PayItem payItem, 
								@RequestParam(value="attachment1" , required = false) MultipartFile attachment1,
								@RequestParam(value="attachment2" , required = false) MultipartFile attachment2,
							 	ModelMap modelMap) {
		if(savePayItem(payItem, attachment1, attachment2, request)){
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(payItem.getId()) ? NEW_ENTITY_ID : payItem.getId()) + "/show", modelMap);
	}
	
	@RequestMapping("{id}/approve")
	@RequiresPermissions("payItem:approve")
	public ModelAndView approve(HttpServletRequest request, 
								@ModelAttribute("entity") PayItem payItem, 
								@RequestParam(value="attachment3" , required = false) MultipartFile attachment3,
								@RequestParam(value="attachment4" , required = false) MultipartFile attachment4,
							 	ModelMap modelMap) {
		String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/payItem");
		payItemService.approve(payItem, attachment3, attachment4, fileDir);
		return redirectTo(PATH + payItem.getId() + "/show", modelMap);
	}
	
	@RequestMapping("{id}/reject")
	@RequiresPermissions("payItem:reject")
	public ModelAndView reject(HttpServletRequest request, 
								@ModelAttribute("entity") PayItem payItem, 
							 	ModelMap modelMap) {
		payItemService.reject(payItem);
		return redirectTo(PATH + payItem.getId() + "/show", modelMap);
	}
	
	private boolean savePayItem(PayItem payItem, 
								MultipartFile attachment1, MultipartFile attachment2, 
								HttpServletRequest request) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/payItem");
			getEntityService().save(payItem, attachment1, attachment2, fileDir);
		}
		return isSucceed;
	}
	
	@RequestMapping("{id}/downLoad")
	@RequiresPermissions("payItem:downLoad")
	@ResponseBody
	public void downLoad(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("entity") PayItem payItem, @RequestParam Integer index) {
		String folder = "payItem" + payItem.getId();
		String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/payItem");
		String filePath = payItem.getFilePath(index);
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		File file = new File(fileDir + "/"  + folder + "/" + fileName);
		downLoadFile(response, fileName, file);
	}

	@Override
	protected PayItemService getEntityService() {
		return payItemService;
	}

	@Override
	protected PayItem newInstance(WebRequest request) {
		return new PayItem();
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("GC30");
	}
	
}
