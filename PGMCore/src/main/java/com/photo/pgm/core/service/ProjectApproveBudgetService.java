/**
 * 
 */
package com.photo.pgm.core.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;
import com.photo.pgm.core.dao.ProjectApproveBudgetRepository;
import com.photo.pgm.core.model.ProjectApproveBudget;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class ProjectApproveBudgetService extends AbsCodeNameEntityService<ProjectApproveBudget, PageInfo<ProjectApproveBudget>> {

	@Autowired private ProjectApproveBudgetRepository projectApproveBudgetRepository;
	
	@Transactional(readOnly = false)
	public ProjectApproveBudget save(ProjectApproveBudget projectApproveBudget, MultipartFile attachment1, String fileDir) {
		projectApproveBudget.setName(projectApproveBudget.getProjectCode());
		super.save(projectApproveBudget);
		// upload files
		if (attachment1 != null) {
			uploadFiles(projectApproveBudget, attachment1, fileDir);
		}
		return projectApproveBudget;
	}
	
	
	private void uploadFiles(ProjectApproveBudget projectApproveBudget, MultipartFile attachment, String fileDir) {
		String folder = projectApproveBudget.getId();
		// 如果目录不存在，创建一个目录
		File folderFIle = new File(fileDir + "/" + folder + "/");
		if (!folderFIle.exists()) {
			folderFIle.mkdir();
		}
		String fileName = attachment.getOriginalFilename();
		if (Strings.isEmpty(fileName)) return;
		File originalFile = null;
		String filePath = projectApproveBudget.getFilePath();
		if(!Strings.isEmpty(filePath)) originalFile = new File(fileDir + "/" + folder + "/" + filePath.substring(filePath.lastIndexOf("/") + 1));
		projectApproveBudget.setFilePath( "/fileUpload/projectApproveBudget/"  + folder + "/" +  fileName);
		projectApproveBudget.setFileName(fileName);
		if (originalFile!= null && originalFile.exists()) originalFile.delete(); // 删除原有文件
		File uploadFile = new File(fileDir + "/"  + folder + "/" + fileName);
		try {
			FileCopyUtils.copy(attachment.getBytes(), uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected ProjectApproveBudgetRepository getRepository() {
		return projectApproveBudgetRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
