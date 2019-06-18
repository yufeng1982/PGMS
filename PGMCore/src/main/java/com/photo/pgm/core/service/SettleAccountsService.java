/**
 * 
 */
package com.photo.pgm.core.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.utils.Calc;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.utils.Strings;
import com.photo.pgm.core.dao.SettleAccountsRepository;
import com.photo.pgm.core.enums.AdjustType;
import com.photo.pgm.core.model.Contract;
import com.photo.pgm.core.model.SettleAccounts;
import com.photo.pgm.core.vo.SettleAccountsQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class SettleAccountsService extends AbsEntityService<SettleAccounts, PageInfo<SettleAccounts>> {

	@Autowired private SettleAccountsRepository settleAccountsRepository;
	@Autowired private ContractService contractService;
	
	public Page<SettleAccounts> findByQueryInfo(SettleAccountsQueryInfo queryInfo) {
		PropertyFilter departmentF = new PropertyFilter("contract.department", queryInfo.getSf_IN_department(), MatchType.IN);
		PropertyFilter projectF = new PropertyFilter("contract.petrolStation", queryInfo.getSf_EQ_petrolStation(), MatchType.EQ);
		PropertyFilter contractF = new PropertyFilter("contract.code", queryInfo.getSf_LIKE_contractCode(), MatchType.LIKE);
		PropertyFilter corporationF = new PropertyFilter("corporation", queryInfo.getSf_EQ_corporation(), MatchType.EQ);
		return getRepository().findAll(bySearchFilter(departmentF, projectF, contractF, corporationF, PropertyFilter.activeFilter()), queryInfo);
	}
	
	@Transactional(readOnly = false)
	public SettleAccounts save(SettleAccounts settleAccounts, MultipartFile attachment1, String fileDir) {
		Contract contract = settleAccounts.getContract();
		if (settleAccounts.getAdjustType().equals(AdjustType.Sub)) {
			settleAccounts.setSettleAmount(Calc.add(settleAccounts.getContract().getAmount(), -settleAccounts.getAdjustAmount()));
			contract.setAdjustAmount(-settleAccounts.getAdjustAmount());
		} else {
			settleAccounts.setSettleAmount(Calc.add(settleAccounts.getContract().getAmount(), settleAccounts.getAdjustAmount()));
			contract.setAdjustAmount(settleAccounts.getAdjustAmount());
		}
		settleAccounts.setDepartment(settleAccounts.getCreatedBy().getDepartment());
		super.save(settleAccounts);
		// 更新合同调整金额
		contractService.save(contract);
		// upload files
		if (attachment1 != null) {
			uploadFiles(settleAccounts, attachment1, fileDir);
		}
		return settleAccounts;
	}
	
	private void uploadFiles(SettleAccounts settleAccounts, MultipartFile attachment, String fileDir) {
		String folder = "settleAccounts" + settleAccounts.getId();
		// 如果目录不存在，创建一个目录
		File folderFIle = new File(fileDir + "/" + folder + "/");
		if (!folderFIle.exists()) {
			folderFIle.mkdir();
		}
		String fileName = attachment.getOriginalFilename();
		if (Strings.isEmpty(fileName)) return;
		File originalFile = null;
		String filePath = settleAccounts.getFilePath();
		if(!Strings.isEmpty(filePath)) originalFile = new File(fileDir + "/" + folder + "/" + filePath.substring(filePath.lastIndexOf("/") + 1));
		settleAccounts.setFilePath( "/fileUpload/settleAccounts/"  + folder + "/" +  fileName);
		if (originalFile!= null && originalFile.exists()) originalFile.delete(); // 删除原有文件
		File uploadFile = new File(fileDir + "/"  + folder + "/" + fileName);
		try {
			FileCopyUtils.copy(attachment.getBytes(), uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected SettleAccountsRepository getRepository() {
		return settleAccountsRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
