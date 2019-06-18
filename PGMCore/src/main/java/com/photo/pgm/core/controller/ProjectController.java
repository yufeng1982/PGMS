/**
 * 
 */
package com.photo.pgm.core.controller;

import java.io.File;
import java.util.Calendar;
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
import com.photo.bas.core.utils.JsonUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsCodeEntityController;
import com.photo.pgm.core.enums.ConstructType;
import com.photo.pgm.core.enums.DiPing;
import com.photo.pgm.core.enums.Dishi;
import com.photo.pgm.core.enums.GcCatagory;
import com.photo.pgm.core.enums.GongDian;
import com.photo.pgm.core.enums.GongNuan;
import com.photo.pgm.core.enums.GongShui;
import com.photo.pgm.core.enums.HaveNoType;
import com.photo.pgm.core.enums.HzWay;
import com.photo.pgm.core.enums.KeShiXing;
import com.photo.pgm.core.enums.OilLevel;
import com.photo.pgm.core.enums.OilPosition;
import com.photo.pgm.core.enums.OilType;
import com.photo.pgm.core.enums.PaiWu;
import com.photo.pgm.core.enums.Ydpbxs;
import com.photo.pgm.core.enums.YesNoType;
import com.photo.pgm.core.model.OurSideCorporation;
import com.photo.pgm.core.model.Project;
import com.photo.pgm.core.service.OurSideCorporationService;
import com.photo.pgm.core.service.ProjectService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/project/form")
public class ProjectController extends AbsCodeEntityController<Project> {

	private final static String PAGE_FORM_PATH = "project/project/project";
	private final static String PATH = "project/project/form/";
	
	@Autowired private ProjectService projectService;
	@Autowired private OurSideCorporationService ourSideCorporationService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("project:show")
	public String showProject(@ModelAttribute("entity") Project project, ModelMap modelMap) {
		List<OurSideCorporation> ourSideCorporations = (List<OurSideCorporation>) ourSideCorporationService.getAllByCorporation(ThreadLocalUtils.getCurrentCorporation());
		modelMap.addAttribute("ourSideCorporations", ourSideCorporations);
		modelMap.addAttribute("ct", JsonUtils.buildEnmuCoboxData(ConstructType.getConstructType()));
		modelMap.addAttribute("yw", JsonUtils.buildEnmuCoboxData(HaveNoType.getHaveNoType()));
		modelMap.addAttribute("ot", JsonUtils.buildEnmuCoboxData(OilType.getOilType()));
		
		modelMap.addAttribute("oilLevel", OilLevel.getOilLevel());
		modelMap.addAttribute("hzWay", HzWay.getHzWay());
		modelMap.addAttribute("gcCatagory", GcCatagory.getGcCatagory());
		modelMap.addAttribute("ywgld", HaveNoType.getHaveNoType());
		
		modelMap.addAttribute("oilPosition", OilPosition.getOilPosition());
		modelMap.addAttribute("ydpbxs", Ydpbxs.getYdpbxs());
		modelMap.addAttribute("diShi", Dishi.getDishi());
		modelMap.addAttribute("gongDian", GongDian.getGongDian());
		modelMap.addAttribute("gongShui", GongShui.getGongShui());
		modelMap.addAttribute("gongNuan", GongNuan.getGongNuan());
		modelMap.addAttribute("keShiXing", KeShiXing.getKeShiXing());
		modelMap.addAttribute("diPing", DiPing.getDiPing());
		modelMap.addAttribute("paiWu", PaiWu.getPaiWu());
		
		modelMap.addAttribute("yesNoType", YesNoType.getYesNoType());
		
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping("{id}/apply")
	@RequiresPermissions("project:apply")
	public ModelAndView save(HttpServletRequest request, 
								@ModelAttribute("entity") Project project, 
								@RequestParam(value="attachment1" , required = false) MultipartFile attachment1,
								@RequestParam(value="attachment2" , required = false) MultipartFile attachment2,
								@RequestParam(value="attachment3" , required = false) MultipartFile attachment3,
								@RequestParam(value="attachment4" , required = false) MultipartFile attachment4,
								@RequestParam(value="attachment5" , required = false) MultipartFile attachment5,
								@RequestParam(value="attachment6" , required = false) MultipartFile attachment6,
								@RequestParam(value="attachment7" , required = false) MultipartFile attachment7,
								@RequestParam(required = false) JSONArray oilCanLineJsons,
								@RequestParam(required = false) JSONArray oilMachineLineJsons,
								@RequestParam(required = false) JSONArray otherEquipmentJsons,
								@RequestParam(required = false) JSONArray petrolStationsJsons,
							 	ModelMap modelMap) {
//		String code = project.getCode();
//		if (!code.contains("(YZ)")) {
//			project.setCode(code+"(YZ)");
//		}
		
		saveProject(project, attachment1, attachment2, attachment3, attachment4, attachment5, attachment6, attachment7, oilCanLineJsons, oilMachineLineJsons, otherEquipmentJsons, petrolStationsJsons, request);
		
		return redirectTo(PATH + project.getId() + "/show", modelMap);
	}

	@RequestMapping("{id}/ok")
	@RequiresPermissions("project:ok")
	public ModelAndView ok(HttpServletRequest request, 
								@ModelAttribute("entity") Project project, 
								@RequestParam(value="attachment1" , required = false) MultipartFile attachment1,
								@RequestParam(value="attachment2" , required = false) MultipartFile attachment2,
								@RequestParam(value="attachment3" , required = false) MultipartFile attachment3,
								@RequestParam(value="attachment4" , required = false) MultipartFile attachment4,
								@RequestParam(value="attachment5" , required = false) MultipartFile attachment5,
								@RequestParam(value="attachment6" , required = false) MultipartFile attachment6,
								@RequestParam(value="attachment7" , required = false) MultipartFile attachment7,
								@RequestParam(required = false) JSONArray oilCanLineJsons,
								@RequestParam(required = false) JSONArray oilMachineLineJsons,
								@RequestParam(required = false) JSONArray otherEquipmentJsons,
								@RequestParam(required = false) JSONArray petrolStationsJsons,
							 	ModelMap modelMap) {
//		String code = project.getCode();
//		if (!code.contains("(YZ)")) {
//			project.setCode(code+"(YZ)");
//		}
		if(saveProject(project, attachment1, attachment2, attachment3, attachment4, attachment5, attachment6, attachment7, oilCanLineJsons, oilMachineLineJsons, otherEquipmentJsons, petrolStationsJsons, request)){
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(project.getId()) ? NEW_ENTITY_ID : project.getId()) + "/show", modelMap);
	}
	
	
	
	private boolean saveProject(Project project, MultipartFile attachment1,
										MultipartFile attachment2, MultipartFile attachment3,
										MultipartFile attachment4, MultipartFile attachment5,
										MultipartFile attachment6, MultipartFile attachment7,
										JSONArray oilCanLineJsons,
										JSONArray oilMachineLineJsons,
										JSONArray otherEquipmentJsons,
										JSONArray petrolStationsJsons,
										HttpServletRequest request) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			if (project.isNewEntity()) {
				Calendar cl = Calendar.getInstance();
				cl.setTime(project.getAddDate());
				int year = cl.get(Calendar.YEAR);
				String city = project.getPct().split(" ")[1];
				Integer seq = getEntityService().findMaxSeq(year + "-" + city, project.getCorporation());
				project.setCode(year + "-" + city + "-" + (seq + 1));
			}
			String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/project");
			getEntityService().save(project, attachment1, attachment2, attachment3, attachment4, attachment5,attachment6, attachment7,
									oilCanLineJsons, oilMachineLineJsons, otherEquipmentJsons, petrolStationsJsons, fileDir);
		}
		return isSucceed;
	}
	
	@RequestMapping("{id}/downLoad")
	@RequiresPermissions("project:downLoad")
	@ResponseBody
	public void downLoad(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("entity") Project project, @RequestParam Integer index) {
		String folder = project.getName() + project.getId();
		String fileDir = request.getSession().getServletContext().getRealPath("/fileUpload/project");
		String filePath = project.getFilePath(index);
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		File file = new File(fileDir + "/" + folder + "/" +fileName);
		downLoadFile(response, fileName, file);
	}


	@Override
	protected ProjectService getEntityService() {
		return projectService;
	}

	@Override
	protected Project newInstance(WebRequest request) {
		return new Project();
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("KF01");
	}
	
}
