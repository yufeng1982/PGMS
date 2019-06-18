/**
 * 
 */
package com.photo.pgm.core.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.service.utils.SequenceService;
import com.photo.bas.core.utils.DateTimeUtils;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;
import com.photo.pgm.core.dao.ProjectRepository;
import com.photo.pgm.core.enums.ConstructType;
import com.photo.pgm.core.enums.HaveNoType;
import com.photo.pgm.core.enums.OilType;
import com.photo.pgm.core.model.OilCanLine;
import com.photo.pgm.core.model.OilMachineLine;
import com.photo.pgm.core.model.OtherEquipmentLine;
import com.photo.pgm.core.model.PetrolStation;
import com.photo.pgm.core.model.PetrolStationsLine;
import com.photo.pgm.core.model.Project;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class ProjectService extends AbsCodeNameEntityService<Project, PageInfo<Project>> {

	@Autowired private ProjectRepository projectRepository;
	@Autowired private SequenceService sequenceService;
	@Autowired private CostCenterService costCenterService;
	@Autowired private OilMachineLineService oilMachineLineService;
	@Autowired private OilCanLineService oilCanLineService;
	@Autowired private OtherEquipmentLineService otherEquipmentLineService;
	@Autowired private PetrolStationsLineService petrolStationsLineService;
	
	@Autowired private PetrolStationService petrolStationService;
	
	@Transactional(readOnly = false)
	public Project save(Project project, MultipartFile attachment1,
							MultipartFile attachment2, MultipartFile attachment3,
							MultipartFile attachment4, MultipartFile attachment5, 
							MultipartFile attachment6, MultipartFile attachment7,
							JSONArray oilCanLinejsons,
							JSONArray oilMachineLineJsons,
							JSONArray otherEquipmentJsons,
							JSONArray petrolStationsJsons,
							String fileDir) {
		boolean isCreateSeq = false;
		if (project.isNewEntity())  {
			isCreateSeq = true;
			// 新建油站项目信息时，自动创建一个成本中心，该油站项目就是一个成本中心
//			CostCenter costCenter = costCenterService.findByCodeAndCorporation(project.getCode(), project.getCorporation());
//			if (costCenter == null) {
//				costCenter = new CostCenter();
//				costCenter.setCode(project.getCode());
//				costCenter.setName(project.getName());
//				costCenterService.save(costCenter);
//			}
		}
		super.save(project);
		// 为每个项目创建一个序列，给合同使用，每个新项目的合同序号都是从001开始
		if (isCreateSeq) {
			sequenceService.initSequence("project.contract_sequence_" + project.getId());
			sequenceService.initSequence("project.asset_sequence_" + project.getId());
			
			// 自动创建一个开发管理油站信息
			PetrolStation ps = new PetrolStation();
			ps.setProject(project);
			petrolStationService.save(ps);
		}
		// upload files
		if (attachment1 != null) {
			uploadFiles(project, attachment1, fileDir , 1);
		}
		if (attachment2 != null) {
			uploadFiles(project, attachment2, fileDir , 2);
		}
		if (attachment3 != null) {
			uploadFiles(project, attachment3, fileDir , 3);
		}
		if (attachment4 != null) {
			uploadFiles(project, attachment4, fileDir , 4);
		}
		if (attachment5 != null) {
			uploadFiles(project, attachment5, fileDir , 5);
		}
		if (attachment6 != null) {
			uploadFiles(project, attachment6, fileDir , 6);
		}
		if (attachment7 != null) {
			uploadFiles(project, attachment7, fileDir , 7);
		}
		saveOilCanAndMachines(project, oilCanLinejsons, oilMachineLineJsons);
		saveOtherEquipments(project, otherEquipmentJsons);
		savePetrolStations(project, petrolStationsJsons);
		return project;
	}
	
	private void saveOilCanAndMachines (Project project, JSONArray oilCanLinejsons, JSONArray oilMachineLineJsons) {
		OilMachineLine ocml = null;
		for (int i = 0; i < oilMachineLineJsons.length(); i ++) {
			JSONObject jo = oilMachineLineJsons.getJSONObject(i);
			if (Strings.isEmpty(jo.getString("id"))) {
				ocml = new OilMachineLine();
			} else {
				ocml = oilMachineLineService.get(jo.getString("id"));
			}
			ocml.setSeq(jo.getInt("seq"));
			ocml.setProject(project);
			if (jo.has("pingpai")) ocml.setPingpai(jo.getString("pingpai"));
			if (jo.has("xinghao")) ocml.setXinghao(jo.getString("xinghao"));
			if (jo.has("mqty")) ocml.setMqty(Strings.isEmpty(jo.getString("mqty")) ? 0 : jo.getDouble("mqty"));
			if (jo.has("releaseDate")) ocml.setReleaseDate(DateTimeUtils.stringToDate(jo.getString("releaseDate")));
			oilMachineLineService.save(ocml);
		}
		OilCanLine ocl = null;
		for (int i = 0; i < oilCanLinejsons.length(); i ++) {
			JSONObject jo = oilCanLinejsons.getJSONObject(i);
			if (Strings.isEmpty(jo.getString("id"))) {
				ocl = new OilCanLine();
			} else {
				ocl = oilCanLineService.get(jo.getString("id"));
			}
			ocl.setSeq(jo.getInt("seq"));
			ocl.setProject(project);
			if (jo.has("guanrong")) ocl.setGuanrong(Strings.isEmpty(jo.getString("guanrong")) ? 0 : jo.getDouble("guanrong"));
			if (jo.has("oilType")) ocl.setOilType(Strings.isEmpty(jo.getString("oilType")) ? null : OilType.valueOf(jo.getString("oilType")));
			if (jo.has("qty")) ocl.setQty(Strings.isEmpty(jo.getString("qty")) ? 0 : jo.getDouble("qty"));
			if (jo.has("age")) ocl.setAge(Strings.isEmpty(jo.getString("age")) ? 0 : jo.getDouble("age"));
			oilCanLineService.save(ocl);
		}
	}
	
	private void saveOtherEquipments (Project project, JSONArray otherEquipmentJsons) {
		OtherEquipmentLine oel = null;
		for (int i = 0; i < otherEquipmentJsons.length(); i ++) {
			JSONObject jo = otherEquipmentJsons.getJSONObject(i);
			if (Strings.isEmpty(jo.getString("id"))) {
				oel = new OtherEquipmentLine();
			} else {
				oel = otherEquipmentLineService.get(jo.getString("id"));
			}
			oel.setSeq(jo.getInt("seq"));
			oel.setName(jo.getString("name"));
			oel.setProject(project);
			if (jo.has("enabled")) oel.setEnabled(jo.getBoolean("enabled"));
			if (jo.has("pingpai")) oel.setPingpai(jo.getString("pingpai"));
			if (jo.has("xinghao")) oel.setXinghao(jo.getString("xinghao"));
			if (jo.has("oelReleaseDate") && !Strings.isEmpty(jo.getString("oelReleaseDate"))) oel.setReleaseDate(DateTimeUtils.stringToDate(jo.getString("oelReleaseDate")));
			otherEquipmentLineService.save(oel);
		}
	}
	
	private void savePetrolStations (Project project, JSONArray petrolStationsJsons) {
		PetrolStationsLine psl = null;
		for (int i = 0; i < petrolStationsJsons.length(); i ++) {
			JSONObject jo = petrolStationsJsons.getJSONObject(i);
			if (Strings.isEmpty(jo.getString("id"))) {
				psl = new PetrolStationsLine();
			} else {
				psl = petrolStationsLineService.get(jo.getString("id"));
			}
			psl.setSeq(jo.getInt("seq"));
			psl.setName(jo.getString("name"));
			psl.setProject(project);
			if (jo.has("zpmj")) psl.setZpmj(Strings.isEmpty(jo.getString("zpmj")) ? null : jo.getDouble("zpmj"));;
			if (jo.has("ct")) psl.setCt(Strings.isEmpty(jo.getString("ct")) ? null : ConstructType.valueOf(jo.getString("ct")));
			if (jo.has("bulidYear")) psl.setBulidYear(jo.getString("bulidYear"));
			if (jo.has("bmzk")) psl.setBmzk(jo.getString("bmzk"));
			if (jo.has("wmsl")) psl.setWmsl(jo.getString("wmsl"));
			if (jo.has("wmps")) psl.setWmps(jo.getString("wmps"));
			if (jo.has("ywdd")) psl.setYwdd(Strings.isEmpty(jo.getString("ywdd")) ? null : HaveNoType.valueOf(jo.getString("ywdd")));;
			petrolStationsLineService.save(psl);
		}
	}
	
	private void uploadFiles(Project project, MultipartFile attachment, String fileDir, int n) {
		String folder = project.getName() + project.getId();
		// 如果目录不存在，创建一个目录
		File folderFIle = new File(fileDir + "/" + folder + "/");
		if (!folderFIle.exists()) {
			folderFIle.mkdir();
		}
		String fileName = attachment.getOriginalFilename();
		if (Strings.isEmpty(fileName)) return;
		File originalFile = null;
		String filePath = project.getFilePath(n);
		if(!Strings.isEmpty(filePath)) originalFile = new File(fileDir + "/" + folder + "/" + filePath.substring(filePath.lastIndexOf("/") + 1));
		project.setFilePath(n, "/fileUpload/project/"  + folder + "/" +  fileName);
		if (originalFile!= null && originalFile.exists()) originalFile.delete(); // 删除原有文件
		File uploadFile = new File(fileDir + "/"  + folder + "/" + fileName);
		try {
			FileCopyUtils.copy(attachment.getBytes(), uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Integer findMaxSeq(String code, Corporation corporation){
		List<Project> list = getRepository().findByCodeContainingAndCorporation(code, corporation);
		return list.size();
	}
	
	@Override
	protected ProjectRepository getRepository() {
		return projectRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
