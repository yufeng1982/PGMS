/**
 * 
 */
package com.photo.pgm.core.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
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
import com.photo.pgm.core.enums.CooperationStatus;
import com.photo.pgm.core.model.Cooperation;
import com.photo.pgm.core.model.CooperationAccount;
import com.photo.pgm.core.model.CooperationType;
import com.photo.pgm.core.service.CooperationAccountService;
import com.photo.pgm.core.service.CooperationService;
import com.photo.pgm.core.service.CooperationTypeService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/cooperation/form")
public class CooperationController extends AbsCodeEntityController<Cooperation> {

	private final static String PAGE_FORM_PATH = "project/cooperation/cooperation";
	private final static String PATH = "project/cooperation/form/";
	
	@Autowired private CooperationService cooperationService;
	@Autowired private CooperationTypeService cooperationTypeService;
	@Autowired private CooperationAccountService cooperationAccountService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("cooperation:show")
	public String showCooperation(@ModelAttribute("entity") Cooperation cooperation, ModelMap modelMap) {
		modelMap.addAttribute("status", CooperationStatus.getCooperationStatus());
		List<CooperationType> cooperationTypeList = (List<CooperationType>) cooperationTypeService.getAllByCorporation(cooperation.getCorporation());
		modelMap.addAttribute("cooperationType", cooperationTypeList);
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping("{id}/apply")
	@RequiresPermissions("cooperation:apply")
	public ModelAndView save(HttpServletRequest request, 
								@ModelAttribute("entity") Cooperation cooperation, 
								@RequestParam(value="attachment1" , required = false) MultipartFile attachment1,
								@RequestParam(value="attachment2" , required = false) MultipartFile attachment2,
								@RequestParam(value="attachment3" , required = false) MultipartFile attachment3,
								@RequestParam(value="attachment4" , required = false) MultipartFile attachment4,
								@RequestParam(value="attachment5" , required = false) MultipartFile attachment5,
								@RequestParam(value="attachment6" , required = false) MultipartFile attachment6,
								@RequestParam(value="attachment7" , required = false) MultipartFile attachment7,
								@RequestParam JSONArray cooperationAccountJsons,
								@RequestParam List<String> cooperationAccountDeleteLines,
							 	ModelMap modelMap) {
		
		saveCooperation(cooperation,  attachment1, attachment2 , attachment3 , attachment4, attachment5, attachment6, attachment7, cooperationAccountJsons, cooperationAccountDeleteLines, request);
		
		return redirectTo(PATH + cooperation.getId() + "/show", modelMap);
	}

	@RequestMapping("{id}/ok")
	@RequiresPermissions("cooperation:ok")
	public ModelAndView ok(HttpServletRequest request, 
								@ModelAttribute("entity") Cooperation cooperation, 
								@RequestParam(value="attachment1" , required = false) MultipartFile attachment1,
								@RequestParam(value="attachment2" , required = false) MultipartFile attachment2,
								@RequestParam(value="attachment3" , required = false) MultipartFile attachment3,
								@RequestParam(value="attachment4" , required = false) MultipartFile attachment4,
								@RequestParam(value="attachment5" , required = false) MultipartFile attachment5,
								@RequestParam(value="attachment6" , required = false) MultipartFile attachment6,
								@RequestParam(value="attachment7" , required = false) MultipartFile attachment7,
								@RequestParam JSONArray cooperationAccountJsons,
								@RequestParam List<String> cooperationAccountDeleteLines,
							 	ModelMap modelMap) {
		if(saveCooperation(cooperation,  attachment1, attachment2 , attachment3 , attachment4, attachment5, attachment6, attachment7, cooperationAccountJsons, cooperationAccountDeleteLines, request)){
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(cooperation.getId()) ? NEW_ENTITY_ID : cooperation.getId()) + "/show", modelMap);
	}
	
	@RequestMapping("{id}/uploadFiles")
	@ResponseBody
	public String uploadFiles(HttpServletRequest request,
			@ModelAttribute("entity") Cooperation cooperation,
			@RequestParam String recordId,
			@RequestParam(value = "attachment") MultipartFile file,
			ModelMap modelMap) {
		String imageDir = request.getSession().getServletContext().getRealPath("/fileUpload/bankChanges/");
		return cooperationAccountService.save(recordId, imageDir, file, cooperation);
	}
	
	@RequestMapping("{id}/downLoadBankFile")
	@RequiresPermissions("cooperation:downLoadBankFile")
	@ResponseBody
	public void downLoadBankFile(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("entity") Cooperation cooperation, @RequestParam String recordId) {
		if (!Strings.isEmpty(recordId)) {
			CooperationAccount ca = cooperationAccountService.get(recordId);
			String fileName = ca.getFileName();
			File file = new File(ca.getFileAllPath());
			downLoadFile(response, fileName, file);
		}
	}
	
	
//	@RequestMapping(value = "{id}/deleteImages")
//	@ResponseBody
//	public String deleteImages(@RequestParam(value = "recordId") String id) {
//		return cooperationAccountService.deleteById(id);
//	}
	
	private boolean saveCooperation(Cooperation cooperation, MultipartFile attachment1, 
									 MultipartFile attachment2, MultipartFile attachment3,
									 MultipartFile attachment4, MultipartFile attachment5,
									 MultipartFile attachment6, MultipartFile attachment7,
									 JSONArray cooperationAccountJsons,
									 List<String> cooperationAccountDeleteLines,
									 HttpServletRequest request) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/cooperation");
			getEntityService().save(cooperation, attachment1, attachment2 , attachment3 , attachment4, attachment5, attachment6, attachment7, cooperationAccountJsons, cooperationAccountDeleteLines, fileDir);
		}
		return isSucceed;
	}
	
	@RequestMapping("{id}/downLoad")
	@RequiresPermissions("cooperation:downLoad")
	@ResponseBody
	public void downLoad(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("entity") Cooperation cooperation, @RequestParam Integer index) {
		String folder = cooperation.getName() + cooperation.getId();
		String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/cooperation");
		String filePath = cooperation.getFilePath(index);
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		File file = new File(fileDir + "/" + folder + "/" +fileName);
		downLoadFile(response, fileName, file);
	}

	@Override
	protected CooperationService getEntityService() {
		return cooperationService;
	}

	@Override
	protected Cooperation newInstance(WebRequest request) {
		return new Cooperation();
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("VD1");
	}
	
}
