/**
 * 
 */
package com.photo.pgm.core.service;

import java.io.File;
import java.io.IOException;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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
import com.photo.pgm.core.dao.RepairSettleAccountRepository;
import com.photo.pgm.core.enums.ApproveResult;
import com.photo.pgm.core.enums.RepairSettleAccountStatus;
import com.photo.pgm.core.model.RepairApproveOpinions;
import com.photo.pgm.core.model.RepairSettleAccount;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class RepairSettleAccountService extends AbsCodeNameEntityService<RepairSettleAccount, PageInfo<RepairSettleAccount>> {

	@Autowired private RepairSettleAccountRepository repairSettleAccountRepository;
	@Autowired private ActivitiFlowService activitiFlowService;
	@Autowired private RepairApproveOpinionsService repairApproveOpinionsService;
	
	@Transactional(readOnly = false)
	public RepairSettleAccount save(RepairSettleAccount repairSettleAccount, MultipartFile archive1, 
								MultipartFile archive2, String fileDir) {
		super.save(repairSettleAccount);
		// upload files
		if (archive1 != null) {
			uploadFiles(repairSettleAccount, archive1, fileDir , 1);
		}
		if (archive2 != null) {
			uploadFiles(repairSettleAccount, archive2, fileDir , 2);
		}
		
		return repairSettleAccount;
	}
	
	@Transactional(readOnly = false)
	public RepairSettleAccount send(RepairSettleAccount repairSettleAccount) {
		ProcessInstance pi = activitiFlowService.startProcessInstance(repairSettleAccount.getId(), "RepairSettleAccount", "SettleAccounts");
		repairSettleAccount.setRepairSettleAccountStatus(RepairSettleAccountStatus.Pending);
		repairSettleAccount.setProcessInstanceId(pi.getId());
		super.save(repairSettleAccount);
		return repairSettleAccount;
	}
	
	@Transactional(readOnly = false)
	public void approve(RepairSettleAccount repairSettleAccount, String taskId) {
		Task task = activitiFlowService.getTaskById(taskId);
		if (task == null) return;
		activitiFlowService.completeTask(taskId, repairSettleAccount.getId());
		this.save(repairSettleAccount);
		// 添加审批历史记录
		addApproveResultRecord(repairSettleAccount, task, ApproveResult.Passed);
	}
	
	@Transactional(readOnly = false)
	public void reject(RepairSettleAccount repairSettleAccount, String taskId){
		Task task = activitiFlowService.getTaskById(taskId);
		activitiFlowService.deleteProcessInstance(repairSettleAccount.getProcessInstanceId());
		resetRepairOrderGeneral(repairSettleAccount);
		this.save(repairSettleAccount);
		// 添加审批历史记录
		addApproveResultRecord(repairSettleAccount, task, ApproveResult.Reject);
	}
	
	public Task getActivityTask(RepairSettleAccount repairSettleAccount,String candidateUser) {
		Task task = activitiFlowService.getActivityTask(repairSettleAccount.getProcessInstanceId(), candidateUser);
		return task;
	}
	
	public double judgeAmount(String entityId) {
		RepairSettleAccount repairSettleAccount = get(entityId);
		return repairSettleAccount.getSettleAccount();
	}
	
	public double judgeHSEAmount(String entityId){
		RepairSettleAccount repairSettleAccount = get(entityId);
		return repairSettleAccount.getHseAmount();
	}
	
	@Transactional(readOnly = false)
	public void setFlowFinished(String entityId){
		RepairSettleAccount repairSettleAccount = get(entityId);
		repairSettleAccount.setRepairSettleAccountStatus(RepairSettleAccountStatus.Closed);
		super.save(repairSettleAccount);
	}
	
	
	public Double findTotalSettleAccount4(String corporation){
		Double totalSettleAccount = getRepository().findTotalSettleAccount4(corporation);
		return totalSettleAccount == null ? 0 : totalSettleAccount;
	}
	
	private void addApproveResultRecord(RepairSettleAccount repairSettleAccount, Task task, ApproveResult ar) {
		RepairApproveOpinions rapo = new RepairApproveOpinions();
		rapo.setRepairSettleAccount(repairSettleAccount);
		rapo.setTaskId(task.getId());
		rapo.setTaskName(repairSettleAccount.getTaskName()); 
		repairSettleAccount.setProcessInstanceId(repairSettleAccount.getProcessInstanceId());
		setOptions(rapo, repairSettleAccount, task);
		rapo.setApproveResult(ar);
		rapo.setComleteTime(DateTimeUtils.dateTimeNow());
		rapo.setResponsePeople(ThreadLocalUtils.getCurrentUser().getRealName());
		repairApproveOpinionsService.save(rapo);
	}
	
	private void setOptions(RepairApproveOpinions rapo, RepairSettleAccount repairSettleAccount, Task task) {
		switch (task.getTaskDefinitionKey()) {
		case "A000001": 
			rapo.setOpinions(repairSettleAccount.getOpinions1());
			break;
		case "A000002": 
			rapo.setOpinions(repairSettleAccount.getOpinions2());
			break;
		case "A000003": 
			rapo.setOpinions(repairSettleAccount.getOpinions3());
			break;
		case "A000004": 
			rapo.setOpinions(repairSettleAccount.getOpinions4());
			break;
		case "A000005": 
			rapo.setOpinions(repairSettleAccount.getOpinions5());
			break;
		default:
			break;
		}
	}
	
	private void resetRepairOrderGeneral(RepairSettleAccount repairSettleAccount) {
		repairSettleAccount.setOpinions1(null);
		repairSettleAccount.setResponseTime1(null);
		repairSettleAccount.setResponseUser1(null);
		repairSettleAccount.setOpinions3(null);
		repairSettleAccount.setResponseTime3(null);
		repairSettleAccount.setResponseUser3(null);
		repairSettleAccount.setOpinions4(null);
		repairSettleAccount.setResponseTime4(null);
		repairSettleAccount.setResponseUser4(null);
		repairSettleAccount.setOpinions5(null);
		repairSettleAccount.setResponseTime5(null);
		repairSettleAccount.setResponseUser5(null);
		repairSettleAccount.setOpinions2(null);
		repairSettleAccount.setResponseTime2(null);
		repairSettleAccount.setResponseUser2(null);
		repairSettleAccount.setProcessInstanceId(null);
		repairSettleAccount.setRepairSettleAccountStatus(RepairSettleAccountStatus.Draft);
	}
	
	private void uploadFiles(RepairSettleAccount repairSettleAccount, MultipartFile attachment, String fileDir, int n) {
		String folder = "repairSettleAccount" + repairSettleAccount.getId();
		// 如果目录不存在，创建一个目录
		File folderFIle = new File(fileDir + "/" + folder + "/");
		if (!folderFIle.exists()) {
			folderFIle.mkdir();
		}
		String fileName = attachment.getOriginalFilename();
		if (Strings.isEmpty(fileName)) return;
		File originalFile = null;
		String filePath = repairSettleAccount.getFilePath(n);
		if(!Strings.isEmpty(filePath)) originalFile = new File(fileDir + "/" + folder + "/" + filePath.substring(filePath.lastIndexOf("/") + 1));
		repairSettleAccount.setFilePath(n, "/fileUpload/repairSettleAccount/"  + folder + "/" +  fileName);
		if (originalFile!= null && originalFile.exists()) originalFile.delete(); // 删除原有文件
		File uploadFile = new File(fileDir + "/"  + folder + "/" + fileName);
		try {
			FileCopyUtils.copy(attachment.getBytes(), uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected RepairSettleAccountRepository getRepository() {
		return repairSettleAccountRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
