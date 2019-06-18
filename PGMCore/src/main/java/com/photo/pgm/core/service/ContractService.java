/**
 * 
 */
package com.photo.pgm.core.service;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.List;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.photo.bas.core.exception.DocumentError;
import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.DateTimeUtils;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;
import com.photo.pgm.core.dao.ContractRepository;
import com.photo.pgm.core.enums.ApproveResult;
import com.photo.pgm.core.enums.ContractStatus;
import com.photo.pgm.core.model.ApproveOpinions;
import com.photo.pgm.core.model.Contract;
import com.photo.pgm.core.model.FlowDefinition;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class ContractService extends AbsCodeNameEntityService<Contract, PageInfo<Contract>> {

	@Autowired private ContractRepository contractRepository;
	@Autowired private ActivitiFlowService activitiFlowService;
	@Autowired private ApproveOpinionsService approveOpinionsService;
	@Autowired private FlowDefinitionService flowDefinitionService;
	
	@Transactional(readOnly = false)
	public void pendingSend(Contract contract) {
		ProcessInstance processInstance = activitiFlowService.startProcessInstance(contract.getId(), "Contract", contract.getFlowDefinition().getCode());
		contract.setContractStatus(ContractStatus.PendingApprove);
		contract.setProcessInstanceId(processInstance.getId());
		contract.setReject(false);
		this.save(contract);
	}
	
	@Transactional(readOnly = false)
	public void approve(Contract contract, String taskId) {
		Task task = activitiFlowService.getTaskById(taskId);
		if (task == null) return;
		activitiFlowService.completeTask(taskId, contract.getId());
		// 判断流程是否结束，结束后更新合同状态到等待归档状态
		ProcessInstance pi = activitiFlowService.getProcessInstance(contract.getProcessInstanceId());
		if (pi == null) {
			contract.setContractStatus(ContractStatus.PendingArchive);
		}
		// 添加审批结果记录
		addApproveResultRecord(contract, taskId, ApproveResult.Passed);
		this.save(contract);
	}

	
	public String getSequeneNumber(String seqName, String projectId) {
		List<BigInteger> obj= getRepository().getSerialNumber(seqName + "_" +  projectId);
		BigInteger lSerialNumber = obj.get(0);
		StringBuffer numOfZeros = new StringBuffer();
		for (int i = 0; i < 3; i ++) {
			numOfZeros.append("0");
		}
		String sq =  new DecimalFormat(numOfZeros.toString()).format(lSerialNumber);
		return sq;
	}
	
	
	@Transactional(readOnly = false)
	public Contract save(Contract contract, MultipartFile attachment, String fileDir) {
		if(contract.isNewEntity()){
			// 自动关联流程
			List<FlowDefinition> fdList = flowDefinitionService.findByCorporation(contract.getCorporation());
			if (fdList.size() > 0) {
				contract.setFlowDefinition(fdList.get(0));
			} else {
				throw new DocumentError("FlowError", "没有关联到任何流程，请检查流程设置或者新建合同审批流程！");
			}
		}
		contract.setDepartment(contract.getCreatedBy().getDepartment());
		this.save(contract);
		uploadFiles(contract, attachment, fileDir, false);
		return contract;
	}
	
	@Transactional(readOnly = false)
	public Contract archive(Contract contract, MultipartFile attachment, String fileDir) {
		contract.setContractStatus(ContractStatus.Archive);;
		this.save(contract);
		uploadFiles(contract, attachment, fileDir, true);
		// 归档后添加最后审批结果记录（签字盖章状态）
		addApproveResultRecord(contract, null, ApproveResult.Archived);
		return contract;
	}
	
	public Task getActivityTask(Contract contract,String candidateUser) {
		Task task = activitiFlowService.getActivityTask(contract.getProcessInstanceId(), candidateUser);
		return task;
	}
	
	@Transactional(readOnly = false)
	public void reject(Contract contract, String taskId){
		activitiFlowService.deleteProcessInstance(contract.getProcessInstanceId());
		
		// 添加审批结果记录
		addApproveResultRecord(contract, taskId, ApproveResult.Reject);
				
		contract.setProcessInstanceId(null);
		contract.setReject(true);
		contract.setContractStatus(ContractStatus.PendingSend);
		this.save(contract);
		
	}
	
	public boolean isDeployed(Contract contract){
		return activitiFlowService.isDeployed(contract.getFlowDefinition().getCode());
	}
	
	private void uploadFiles(Contract contract, MultipartFile attachment, String fileDir, boolean isArchive) {
		String folder = contract.getName() + contract.getId();
		// 如果目录不存在，创建一个目录
		File folderFIle = new File(fileDir + "/" + folder + "/");
		if (!folderFIle.exists()) {
			folderFIle.mkdir();
		}
		String fileName = attachment.getOriginalFilename();
		if (Strings.isEmpty(fileName)) return;
		File originalFile = null;
		String filePath = "";
		if (isArchive) {
			filePath = contract.getArchiveFilePath();
			contract.setArchiveFilePath("/fileUpload/contract/" + folder + "/" + fileName);
		} else {
			filePath = contract.getFilePath();
			contract.setFilePath("/fileUpload/contract/" + folder + "/" + fileName);
		}
		if(!Strings.isEmpty(filePath)) originalFile = new File(fileDir + "/" + folder + "/" + filePath.substring(filePath.lastIndexOf("/") + 1));
		if (originalFile!= null && originalFile.exists()) originalFile.delete(); // 删除原有文件
		
		File uploadFile = new File(fileDir + "/" + folder + "/" + fileName);
		try {
			FileCopyUtils.copy(attachment.getBytes(), uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addApproveResultRecord(Contract contract, String taskId, ApproveResult ar) {
		ApproveOpinions apo = new ApproveOpinions();
		apo.setContract(contract);
		apo.setTaskId(taskId);
		apo.setTaskName(Strings.isEmpty(contract.getTaskName()) ? "签字盖章" : contract.getTaskName()); // 只有归档时 才使用签字盖章
		apo.setProcessInstanceId(contract.getProcessInstanceId());
		apo.setOpinions(contract.getOpinions());
		apo.setApproveResult(ar);
		apo.setComleteTime(DateTimeUtils.dateTimeNow());
		approveOpinionsService.save(apo);
	}
	
	@Override
	protected ContractRepository getRepository() {
		return contractRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
