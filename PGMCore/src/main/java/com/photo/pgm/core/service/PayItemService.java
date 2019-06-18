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
import com.photo.bas.core.utils.DateTimeUtils;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.pgm.core.dao.PayItemRepository;
import com.photo.pgm.core.enums.PayType;
import com.photo.pgm.core.model.Contract;
import com.photo.pgm.core.model.PayAccount;
import com.photo.pgm.core.model.PayItem;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class PayItemService extends AbsCodeNameEntityService<PayItem, PageInfo<PayItem>> {

	@Autowired private PayItemRepository payItemRepository;
	@Autowired private ContractService contractService;
	@Autowired private PayAccountService payAccountService;
	
	@Transactional(readOnly = false)
	public PayItem save(PayItem payItem, MultipartFile attachment1, MultipartFile attachment2, String fileDir) {
		Contract contract = payItem.getContract();
		if (payItem.isNewEntity()) {
			payItem.setDepartment(payItem.getCreatedBy().getDepartment());
			payItem.setPayCount(contract.getPayCounts() + 1); // 记录付款申请次数
			contract.setPayCounts(contract.getPayCounts() + 1); // 同步更新合同申请付款次数
			contract.setContinueRequest(false);// 新建申请后 财务不确认不能对该合同继续申请付款
			contractService.save(contract);
		}
		if (contract.isRejectRequest()) {
			contract.setRejectRequest(false);
			contract.setPayCounts(contract.getPayCounts() + 1);
		}
		payItem.setPayType(PayType.UnPaid);
		super.save(payItem);
		// upload files
		if (attachment1 != null) {
			uploadFiles(payItem, attachment1, fileDir , 1);
		}
		if (attachment2 != null) {
			uploadFiles(payItem, attachment2, fileDir , 2);
		}
		
		return payItem;
	}
	
	
	@Transactional(readOnly = false)
	public void approve (PayItem payItem, MultipartFile attachment3, MultipartFile attachment4, String fileDir) {
		payItem.setPayUser(ThreadLocalUtils.getCurrentUser());
		payItem.setPayDate(DateTimeUtils.dateTimeNow());
		payItem.setPayType(PayType.Paid);
		this.save(payItem);
		
		if (attachment3 != null) {
			uploadFiles(payItem, attachment3, fileDir , 3);
		}
		if (attachment4 != null) {
			uploadFiles(payItem, attachment4, fileDir , 4);
		}
		
		Contract contract = payItem.getContract();
		PayAccount pa = payAccountService.findByContract(contract, payItem.getCorporation());
		if (pa == null) {
			pa = new PayAccount();
			pa.setContract(payItem.getContract());
		}
		if (payItem.getPayCount() == 1) {
			pa.setOnePayAmount(payItem.getPayAmount());
			pa.setOneRequestAmount(payItem.getRequestAmount());
			pa.setOnePayUser(payItem.getPayUser());
			pa.setOneRequestUser(payItem.getCreatedBy());
			pa.setOnePayDate(DateTimeUtils.dateTimeNow());
			pa.setFinishAmount(pa.getFinishAmount() + pa.getOnePayAmount()); // 更新台账已完成金额
			pa.setBlanceAmount(contract.getAmount() - pa.getFinishAmount()); // 更新台账剩余金额
			pa.setOnePayFile1(payItem.getFilePath1());
			pa.setOnePayFile2(payItem.getFilePath2());
			pa.setOnePayContents(payItem.getPayContents());
			pa.setOnePayItem(payItem);
			contract.setOnePayAmount(payItem.getPayAmount());
			contract.setOnePayDate(pa.getOnePayDate());
			contract.setPayAmount(contract.getPayAmount() + pa.getOnePayAmount());// 更新合同上的已付款金额
		} else if (payItem.getPayCount() == 2) {
			pa.setTwoPayAmount(payItem.getPayAmount());
			pa.setTwoRequestAmount(payItem.getRequestAmount());
			pa.setTwoPayUser(payItem.getPayUser());
			pa.setTwoRequestUser(payItem.getCreatedBy());
			pa.setTwoPayDate(DateTimeUtils.dateTimeNow());
			pa.setFinishAmount(pa.getFinishAmount() + pa.getTwoPayAmount()); // 更新台账已完成金额
			pa.setBlanceAmount(contract.getAmount() - pa.getFinishAmount()); // 更新台账剩余金额
			pa.setTwoPayFile1(payItem.getFilePath1());
			pa.setTwoPayFile2(payItem.getFilePath2());
			pa.setTwoPayContents(payItem.getPayContents());
			pa.setTwoPayItem(payItem);
			contract.setTwoPayAmount(payItem.getPayAmount());
			contract.setTwoPayDate(pa.getTwoPayDate());
			contract.setPayAmount(contract.getPayAmount() + pa.getTwoPayAmount());// 更新合同上的已付款金额
		} else if (payItem.getPayCount() == 3) {
			pa.setThreePayAmount(payItem.getPayAmount());
			pa.setThreeRequestAmount(payItem.getRequestAmount());
			pa.setThreePayUser(payItem.getPayUser());
			pa.setThreeRequestUser(payItem.getCreatedBy());
			pa.setThreePayDate(DateTimeUtils.dateTimeNow());
			pa.setFinishAmount(pa.getFinishAmount() + pa.getThreePayAmount()); // 更新台账已完成金额
			pa.setBlanceAmount(contract.getAmount() - pa.getFinishAmount()); // 更新台账剩余金额
			pa.setThreePayFile1(payItem.getFilePath1());
			pa.setThreePayFile2(payItem.getFilePath2());
			pa.setThreePayContents(payItem.getPayContents());
			pa.setThreePayItem(payItem);
			contract.setThreePayAmount(payItem.getPayAmount());
			contract.setThreePayDate(pa.getThreePayDate());
			contract.setPayAmount(contract.getPayAmount() + pa.getThreePayAmount());// 更新合同上的已付款金额
		} else {
			pa.setFourPayAmount(payItem.getPayAmount());
			pa.setFourRequestAmount(payItem.getRequestAmount());
			pa.setFourPayUser(payItem.getPayUser());
			pa.setFourRequestUser(payItem.getCreatedBy());
			pa.setFourPayDate(DateTimeUtils.dateTimeNow());
			pa.setFinishAmount(pa.getFinishAmount() + pa.getFourPayAmount()); // 更新台账已完成金额
			pa.setBlanceAmount(contract.getAmount() - pa.getFinishAmount()); // 更新台账剩余金额
			pa.setFourPayFile1(payItem.getFilePath1());
			pa.setFourPayFile2(payItem.getFilePath2());
			pa.setFourPayContents(payItem.getPayContents());
			pa.setFourPayItem(payItem);
			contract.setFourPayAmount(payItem.getPayAmount());
			contract.setFourPayDate(pa.getThreePayDate());
			contract.setPayAmount(contract.getPayAmount() + pa.getFourPayAmount());// 更新合同上的已付款金额
		}
		contract.setContinueRequest(true); 
		payAccountService.save(pa);
		contractService.save(contract);
	}
	
	@Transactional(readOnly = false)
	public void reject (PayItem payItem) {
		payItem.setPayType(PayType.Draft);
		
		Contract contract = payItem.getContract();
		contract.setPayCounts(contract.getPayCounts() - 1);
		contract.setRejectRequest(true);
		contractService.save(contract);
		
		this.save(payItem);
		
	}
	
	private void uploadFiles(PayItem payItem, MultipartFile attachment, String fileDir, int n) {
		String folder = "payItem" + payItem.getId();
		// 如果目录不存在，创建一个目录
		File folderFIle = new File(fileDir + "/" + folder + "/");
		if (!folderFIle.exists()) {
			folderFIle.mkdir();
		}
		String fileName = attachment.getOriginalFilename();
		if (Strings.isEmpty(fileName)) return;
		File originalFile = null;
		String filePath = payItem.getFilePath(n);
		if(!Strings.isEmpty(filePath)) originalFile = new File(fileDir + "/" + folder + "/" + filePath.substring(filePath.lastIndexOf("/") + 1));
		payItem.setFilePath(n, "/fileUpload/payItem/"  + folder + "/" +  fileName);
		if (originalFile!= null && originalFile.exists()) originalFile.delete(); // 删除原有文件
		File uploadFile = new File(fileDir + "/"  + folder + "/" + fileName);
		try {
			FileCopyUtils.copy(attachment.getBytes(), uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected PayItemRepository getRepository() {
		return payItemRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
