package com.photo.bas.core.service.workflow;

import java.util.List;

import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.core.io.Resource;

import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.model.workflow.WorkFlowActionsInfo;
import com.photo.bas.core.model.workflow.WorkFlowEntity;

/**
 * @author FengYu
 *
 */
public interface ActivitiManager {
	public String deployBPMN20FileByClassPath(String path);
	public String deployBPMN20File(Resource resource);
	public ProcessInstance startInstance(WorkFlowEntity workFlowEntity);
	public List<Task> getActiveTasks(WorkFlowEntity workFlowEntity);
	public List<Task> getActiveTasksByUserId(WorkFlowEntity workFlowEntity, String userId);
	public List<Task> getSystemActiveTasks(WorkFlowEntity workFlowEntity);
    public void gotoNext(WorkFlowEntity workFlowEntity, String taskId, String transitionName);
    public void gotoNext(WorkFlowEntity workFlowEntity, String taskId, String transitionName, List<String> parameter);
	public WorkFlowActionsInfo getWorkFlowActionsInfo(WorkFlowEntity workFlowEntity, String userId);
	public void updateActivitiUser(User user, String corportion);
	public void updateActivitiCandidates(Role role);
	public boolean isActiveTask(WorkFlowEntity workFlowEntity, String taskId);
	public boolean deleteInstanceById(String processInstanceId, boolean deleteHistoryInfo);
	public boolean deleteInstance(List<String> processInstanceIdArray, boolean deleteHistoryInfo);
	public List<PvmTransition> getOutPut(WorkFlowEntity workFlowEntity);
	
	//------------------activiti restful api----------------------//
	public List<ProcessDefinition> getAllDeployedProcess();
	public List<ProcessDefinition> getDeployedProcessById(String workFlowId);
	
	public List<Task> getTasksByUser(String userId);
	public void autoComplete(String userId, String businessKey);
	//------------------activiti restful api----------------------//
}
