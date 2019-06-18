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

import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;
import com.photo.pgm.core.dao.CooperationRepository;
import com.photo.pgm.core.model.Cooperation;
import com.photo.pgm.core.model.CooperationAccount;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class CooperationService extends AbsCodeNameEntityService<Cooperation, PageInfo<Cooperation>> {

	@Autowired private CooperationRepository cooperationRepository;
	@Autowired private CooperationAccountService cooperationAccountService;
	
	@Transactional(readOnly = false)
	public Cooperation save(Cooperation cooperation, MultipartFile attachment1,
							MultipartFile attachment2, MultipartFile attachment3,
							MultipartFile attachment4, MultipartFile attachment5, 
							MultipartFile attachment6, MultipartFile attachment7,
							JSONArray cooperationAccountJsons,
							List<String> cooperationAccountDeleteLines,
							String fileDir) {
		
		super.save(cooperation);
		
		for(String id : cooperationAccountDeleteLines){
			cooperationAccountService.delete(id);
		}
		for(int i = 0; i < cooperationAccountJsons.length(); i++){
			CooperationAccount cooperationAccount = null;
			JSONObject jsonObj = cooperationAccountJsons.getJSONObject(i);
			String id = jsonObj.getString("id");
			if(Strings.isEmpty(id)){
				cooperationAccount = new CooperationAccount();
			} else {
				cooperationAccount = cooperationAccountService.get(id);
			}
			cooperationAccount.setCooperation(cooperation);
			if (jsonObj.has("bank")) {
				cooperationAccount.setBank(jsonObj.getString("bank"));
			}
			if (jsonObj.has("receiveName")) {
				cooperationAccount.setReceiveName(jsonObj.getString("receiveName"));
			}
			if (jsonObj.has("receiveNo")) {
				cooperationAccount.setReceiveNo(jsonObj.getString("receiveNo"));
			}
			cooperationAccountService.save(cooperationAccount);
		}
		
		if (attachment1 != null) {
			uploadFiles(cooperation, attachment1, fileDir , 1);
		}
		if (attachment2 != null) {
			uploadFiles(cooperation, attachment2, fileDir , 2);
		}
		if (attachment3 != null) {
			uploadFiles(cooperation, attachment3, fileDir , 3);
		}
		if (attachment4 != null) {
			uploadFiles(cooperation, attachment4, fileDir , 4);
		}
		if (attachment5 != null) {
			uploadFiles(cooperation, attachment5, fileDir , 5);
		}
		if (attachment6 != null) {
			uploadFiles(cooperation, attachment6, fileDir , 6);
		}
		if (attachment7 != null) {
			uploadFiles(cooperation, attachment7, fileDir , 7);
		}
		return cooperation;
	}
	
	private void uploadFiles(Cooperation cooperation, MultipartFile attachment, String fileDir, int n) {
		String folder = cooperation.getName() + cooperation.getId();
		// 如果目录不存在，创建一个目录
		File folderFIle = new File(fileDir + "/" + folder + "/");
		if (!folderFIle.exists()) {
			folderFIle.mkdir();
		}
		String fileName = attachment.getOriginalFilename();
		if (Strings.isEmpty(fileName)) return;
		File originalFile = null;
		String filePath = cooperation.getFilePath(n);
		if(!Strings.isEmpty(filePath)) originalFile = new File(fileDir + "/" + folder + "/" + filePath.substring(filePath.lastIndexOf("/") + 1));
		cooperation.setFilePath(n, "/fileUpload/cooperation/"  + folder + "/" +  fileName);
		if (originalFile!= null && originalFile.exists()) originalFile.delete(); // 删除原有文件
		File uploadFile = new File(fileDir + "/"  + folder + "/" + fileName);
		try {
			FileCopyUtils.copy(attachment.getBytes(), uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected CooperationRepository getRepository() {
		return cooperationRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
