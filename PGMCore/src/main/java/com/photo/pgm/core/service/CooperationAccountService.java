/**
 * 
 */
package com.photo.pgm.core.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.DateTimeUtils;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.pgm.core.dao.CooperationAccountRepository;
import com.photo.pgm.core.model.Cooperation;
import com.photo.pgm.core.model.CooperationAccount;
import com.photo.pgm.core.vo.CooperationAccountQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class CooperationAccountService extends AbsCodeNameEntityService<CooperationAccount, PageInfo<CooperationAccount>> {

	@Autowired private CooperationAccountRepository cooperationAccountRepository;
	
	public Page<CooperationAccount> findByCooperation(final CooperationAccountQueryInfo queryInfo) {
		if (queryInfo.getSf_EQ_cooperation() == null) return new PageImpl<CooperationAccount>(new ArrayList<CooperationAccount>());
		return getRepository().findAll(byPage(queryInfo), queryInfo);
	}
	
	@Transactional(readOnly = false)
	public String save(String recordId, String imageDir,  MultipartFile file, Cooperation cooperation) {
		String subFilePath = cooperation.getCode() + cooperation.getId();
		// 如果目录不存在，创建一个目录
		File folderFile = new File(imageDir + "/" + subFilePath + "/");
		if (!folderFile.exists()) {
			folderFile.mkdir();
		}
		CooperationAccount cooperationAccount = null;
		if (Strings.isEmpty(recordId)) {
			cooperationAccount = new CooperationAccount();
			cooperationAccount.setCreatedBy(ThreadLocalUtils.getCurrentUser());
			cooperationAccount.setCreationDate(DateTimeUtils.dateTimeNow());
			cooperationAccount.setModifiedBy(ThreadLocalUtils.getCurrentUser());
			cooperationAccount.setModificationDate(cooperationAccount.getCreationDate());
		} else {
			cooperationAccount = get(recordId);
			cooperationAccount.setModifiedBy(ThreadLocalUtils.getCurrentUser());
			cooperationAccount.setModificationDate(DateTimeUtils.dateTimeNow());
		} 
		String fileName = file.getOriginalFilename();
		cooperationAccount.setFilePath("/fileUpload/bankChanges/" + subFilePath + "/" + fileName);
		cooperationAccount.setFileAllPath(imageDir+"/" + subFilePath + "/" + fileName);
		cooperationAccount.setFileName(fileName);
		cooperationAccount.setCooperation(cooperation);
		try {
			// upload image to current web
			File tofile = new File(imageDir+"/"  + subFilePath + "/" + fileName);
			FileCopyUtils.copy(file.getBytes(), tofile);
		} catch (IOException e) {
			e.printStackTrace();
			return "false";
		}
		save(cooperationAccount);
		return "true";
	}
	
	@Override
	protected CooperationAccountRepository getRepository() {
		return cooperationAccountRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
