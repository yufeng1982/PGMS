/**
 * 
 */
package com.photo.pgm.core.service;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

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
import com.photo.pgm.core.dao.RepairOrderRepository;
import com.photo.pgm.core.enums.ApproveResult;
import com.photo.pgm.core.enums.RepairStatus;
import com.photo.pgm.core.enums.RepairType;
import com.photo.pgm.core.model.RepairApproveOpinions;
import com.photo.pgm.core.model.RepairApproveSetup;
import com.photo.pgm.core.model.RepairOrder;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class RepairOrderService extends AbsCodeNameEntityService<RepairOrder, PageInfo<RepairOrder>> {

	@Autowired private RepairOrderRepository repairOrderRepository;
	@Autowired private ActivitiFlowService activitiFlowService;
	@Autowired private RepairApproveOpinionsService repairApproveOpinionsService;
	@Autowired private RepairApproveSetupService repairApproveSetupService;
	
	public Integer findMaxSeq(String corporation){
		Integer seq = repairOrderRepository.findMaxSeq(corporation);
		return seq == null ? 0 : seq;
	}
	
	@Transactional(readOnly = false)
	public RepairOrder save(RepairOrder repairOrder, MultipartFile archive1, 
								MultipartFile archive2, MultipartFile archive3, String fileDir) {
		super.save(repairOrder);
		// upload files
		if (archive1 != null) {
			uploadFiles(repairOrder, archive1, fileDir , 1);
		}
		if (archive2 != null) {
			uploadFiles(repairOrder, archive2, fileDir , 2);
		}
		if (archive3 != null) {
			uploadFiles(repairOrder, archive3, fileDir , 3);
		}
		return repairOrder;
	}
	
	@Transactional(readOnly = false)
	public RepairOrder send(RepairOrder repairOrder) {
		ProcessInstance pi = null;
		if(repairOrder.getRepairType().equals(RepairType.Small)){
			pi = activitiFlowService.startProcessInstance(repairOrder.getId(), "Repair", "SmallRepair");
		} else {
			pi = activitiFlowService.startProcessInstance(repairOrder.getId(), "Repair", "GeneralRepair");
		}
		repairOrder.setRepairStatus(RepairStatus.Pending);
		repairOrder.setProcessInstanceId(pi.getId());
		super.save(repairOrder);
		return repairOrder;
	}
	
	@Transactional(readOnly = false)
	public void approve(RepairOrder repairOrder, String taskId, 
							MultipartFile archive4, MultipartFile archive5, 
							MultipartFile archive6, MultipartFile archive7,
							MultipartFile archive8, MultipartFile archive9, String fileDir) {
		Task task = activitiFlowService.getTaskById(taskId);
		if (task == null) return;
		if (repairOrder.getRepairType().equals(RepairType.Small)) {
			activitiFlowService.completeTask(taskId, repairOrder.getId());
		} else {
			String taskKey = task.getTaskDefinitionKey();
			if (taskKey.equals("G000005")) {
				activitiFlowService.completeTaskWithTransition(taskId, "judgeSettleAmount2000");
			} else if (taskKey.equals("G000004")) {
				activitiFlowService.completeTaskWithTransition(taskId, "judgeSettleAmount10000");
			} else if (taskKey.equals("G000006")) {
				activitiFlowService.completeTaskWithTransition(taskId, "judgeSettleAmount50000");
			} else if (taskKey.equals("G000007")) {
				activitiFlowService.completeTaskWithTransition(taskId, "G000008");
			} else if (taskKey.equals("G000012")) {
				if (repairOrder.isAdjust()) {
					activitiFlowService.completeTaskWithTransition(taskId, "judgeSettle2");
				} else {
					activitiFlowService.completeTaskWithTransition(taskId, "end");
				}
			} else if (taskKey.equals("G000013")) {
				activitiFlowService.completeTaskWithTransition(taskId, "judgeSettle5");
			} else if (taskKey.equals("G000014")) {
				activitiFlowService.completeTaskWithTransition(taskId, "judgeSettle10");
			} else if (taskKey.equals("G000015")) {
				activitiFlowService.completeTaskWithTransition(taskId, "end");
			} else {
				activitiFlowService.completeTask(taskId, repairOrder.getId());
			}
			
		}
		
		// upload files
		if (archive4 != null) {
			uploadFiles(repairOrder, archive4, fileDir, 4);
		}
		if (archive5 != null) {
			uploadFiles(repairOrder, archive5, fileDir, 5);
		}
		if (archive6 != null) {
			uploadFiles(repairOrder, archive6, fileDir, 6);
		}
		if (archive7 != null) {
			uploadFiles(repairOrder, archive7, fileDir, 7);
		}
		if (archive8 != null) {
			uploadFiles(repairOrder, archive8, fileDir, 8);
		}
		if (archive9 != null) {
			uploadFiles(repairOrder, archive9, fileDir, 9);
		}
		this.save(repairOrder);
		// 添加审批历史
		addApproveResultRecord(repairOrder, task, ApproveResult.Passed);
	}
	
	@Transactional(readOnly = false)
	public void reject(RepairOrder repairOrder, String taskId){
		Task task = activitiFlowService.getTaskById(taskId);
		if (repairOrder.getRepairType().equals(RepairType.Small)) {
			activitiFlowService.deleteProcessInstance(repairOrder.getProcessInstanceId());
			repairOrder.setProcessInstanceId(null);
			repairOrder.setRepairStatus(RepairStatus.Draft);
			repairOrder.setTk1approve(false);
			repairOrder.setTk2approve(false);
			repairOrder.setTk3approve(false);
			repairOrder.setTk4approve(false);
			repairOrder.setOpinions1("");
			repairOrder.setOpinions2("");
			repairOrder.setOpinions21("");
			repairOrder.setOpinions11("");
		} else {
			String taskKey = task.getTaskDefinitionKey();
			if (taskKey.equals("G000001") || taskKey.equals("G000002") || taskKey.equals("G000003") || taskKey.equals("G0000041")) {
				activitiFlowService.deleteProcessInstance(repairOrder.getProcessInstanceId());
				repairOrder.setProcessInstanceId(null);
				repairOrder.setRepairStatus(RepairStatus.Draft);
			} else if (taskKey.equals("G000004") || taskKey.equals("G000005") || taskKey.equals("G000006") || taskKey.equals("G000007")) {
				activitiFlowService.completeTaskWithTransition(taskId, "G000002");
			} else if (taskKey.equals("G000013") || taskKey.equals("G000014") || taskKey.equals("G000015")) {
				activitiFlowService.completeTaskWithTransition(taskId, "G000012");
			}
		}
		this.save(repairOrder);
		// 添加审批历史
		addApproveResultRecord(repairOrder, task, ApproveResult.Reject);
	}
	
	public int judgeAmount(String entityId) {
		RepairOrder repairOrder = get(entityId);
		RepairApproveSetup pas = repairApproveSetupService.findByCode(repairOrder.getTaskCode());
		if (repairOrder.getCurrentBudget() >= pas.getJudgeAmount()) {
			return 2;
		} else {
			return 1;
		}
	}
	
	public int judgeSettleAmount(String entityId) {
		RepairOrder repairOrder = get(entityId);
		RepairApproveSetup pas = repairApproveSetupService.findByCode(repairOrder.getTaskCode());
		if (repairOrder.getSettleAccount() >= pas.getJudgeAmount()) {
			return 2;
		} else {
			return 1;
		}
	}
	
	public double judgeConsumTime(String entityId){
		RepairOrder repairOrder = get(entityId);
		return repairOrder.getRepairTime();
	}
	
	private void addApproveResultRecord(RepairOrder repairOrder, Task task, ApproveResult ar) {
		RepairApproveOpinions rapo = new RepairApproveOpinions();
		rapo.setRepairOrder(repairOrder);
		rapo.setTaskId(task.getId());
		rapo.setTaskName(repairOrder.getTaskName()); 
		repairOrder.setProcessInstanceId(repairOrder.getProcessInstanceId());
		setOptions(rapo, repairOrder, task);
		rapo.setApproveResult(ar);
		rapo.setComleteTime(DateTimeUtils.dateTimeNow());
		rapo.setResponsePeople(ThreadLocalUtils.getCurrentUser().getRealName());
		repairApproveOpinionsService.save(rapo);
	}
	
	private void setOptions(RepairApproveOpinions rapo, RepairOrder repairOrder, Task task) {
		switch (task.getTaskDefinitionKey()) {
			case "S000001": 
				rapo.setOpinions(repairOrder.getOpinions1());
				break;
			case "S000002": 
				rapo.setOpinions(repairOrder.getOpinions2());
				break;
			case "S000003": 
				rapo.setOpinions(repairOrder.getOpinions21());
				break;
			case "S000004": 
				rapo.setOpinions(repairOrder.getOpinions11());
				break;
			case "G000001": 
				rapo.setOpinions("维修工程师已经响应");
				break;
			case "G000002": 
				rapo.setOpinions(repairOrder.getOpinions1());
				break;
			case "G000003": 
				rapo.setOpinions(repairOrder.getOpinions2());
				break;
			case "G000004": 
				rapo.setOpinions(repairOrder.getOpinions4());
				break;
			case "G0000041": 
				rapo.setOpinions(repairOrder.getOpinions41());
				break;
			case "G000005": 
				rapo.setOpinions(repairOrder.getOpinions5());
				break;
			case "G000006": 
				rapo.setOpinions(repairOrder.getOpinions6());
				break;
			case "G000007": 
				rapo.setOpinions(repairOrder.getOpinions7());
				break;
			case "G000008": 
				rapo.setOpinions(repairOrder.getOpinions8());
				break;
			case "G000009": 
				rapo.setOpinions(repairOrder.getOpinions9());
				break;
			case "G000010": 
				rapo.setOpinions(repairOrder.getOpinions10());
				break;
			case "G000011": 
				rapo.setOpinions(repairOrder.getOpinions11());
				break;
			case "G000012": 
				rapo.setOpinions(repairOrder.getOpinions12());
				break;
			case "G000013": 
				rapo.setOpinions(repairOrder.getOpinions13());
				break;
			case "G000014": 
				rapo.setOpinions(repairOrder.getOpinions14());
				break;
			case "G000015": 
				rapo.setOpinions(repairOrder.getOpinions15());
				break;
			default:
				break;
		}
	}
	
	public Task getActivityTask(RepairOrder repairOrder,String candidateUser) {
		Task task = activitiFlowService.getActivityTask(repairOrder.getProcessInstanceId(), candidateUser);
		return task;
	}
	
	public Double findActualAmount4Small(String corporation) {
		Double actualAmount = getRepository().findActualAmount4Small(corporation);
		return actualAmount == null ? 0 : actualAmount;
	}
	
	private void uploadFiles(RepairOrder repairOrder, MultipartFile attachment, String fileDir, int n) {
		String folder = "repairOrder" + repairOrder.getId();
		// 如果目录不存在，创建一个目录
		File folderFIle = new File(fileDir + "/" + folder + "/");
		if (!folderFIle.exists()) {
			folderFIle.mkdir();
		}
		String fileName = attachment.getOriginalFilename();
		if (n <= 6) {
			String stufix = fileName.substring(fileName.indexOf("."));
			fileName = "IMG_" + n + new Date().getTime() + stufix;
		}
		if (Strings.isEmpty(fileName)) return;
		File originalFile = null;
		String filePath = repairOrder.getFilePath(n);
		if(!Strings.isEmpty(filePath)) originalFile = new File(fileDir + "/" + folder + "/" + filePath.substring(filePath.lastIndexOf("/") + 1));
		repairOrder.setFilePath(n, "/fileUpload/repairOrder/"  + folder + "/" +  fileName);
		if (originalFile!= null && originalFile.exists()) originalFile.delete(); // 删除原有文件
		File uploadFile = new File(fileDir + "/"  + folder + "/" + fileName);
		try {
			FileCopyUtils.copy(attachment.getBytes(), uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getSequeneNumber(String seqName) {
		List<BigInteger> obj= getRepository().getSerialNumber(seqName);
		BigInteger lSerialNumber = obj.get(0);
		StringBuffer numOfZeros = new StringBuffer();
		for (int i = 0; i < 4; i ++) {
			numOfZeros.append("0");
		}
		String sq =  new DecimalFormat(numOfZeros.toString()).format(lSerialNumber);
		return sq;
	}
	
	@Override
	protected RepairOrderRepository getRepository() {
		return repairOrderRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
