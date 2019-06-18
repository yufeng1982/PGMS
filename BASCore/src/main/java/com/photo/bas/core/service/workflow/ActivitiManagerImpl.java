package com.photo.bas.core.service.workflow;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.photo.bas.core.exception.DocumentError;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.model.workflow.WorkFlowActionsInfo;
import com.photo.bas.core.model.workflow.WorkFlowEntity;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;

/**
 * @author FengYu
 *
 */
@Service("activitiManager")
public class ActivitiManagerImpl implements ActivitiManager {
	
	//@Autowired 
	private ProcessEngine processEngine;
	
	private static final String SYSTEM_AUTO = "SYSTEM_ASSIGNEE";
	private static final String TRANSITION_NAME = "transitionName";
	public static final String WITH_ROLE = "WITHROLE";
	private static final String SYSTEM_PARAMETER = "SYSTEM_PARAMETER";
	
	@Override
	public String deployBPMN20FileByClassPath(String path) {
		Deployment deploy = getRepositoryService().createDeployment().addClasspathResource(path).deploy();
		return "Id : " + deploy.getId() + " - Name : " + deploy.getName() + " - Time : " + deploy.getDeploymentTime();
	}
	
	@Override
	public String deployBPMN20File(Resource resource) {
		try {
			Deployment deploy = getRepositoryService().createDeployment().addInputStream(resource.getFile().getAbsolutePath(), resource.getInputStream()).deploy();
			return "deployed " + resource.getFile().getName() + " - Time : " + deploy.getDeploymentTime();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ProcessInstance startInstance(WorkFlowEntity workFlowEntity) {
		ProcessInstance processInstance = getInstance(workFlowEntity);
	    String businessKey = getBusinessKey(workFlowEntity);
	    if(processInstance == null && !Strings.isEmpty(businessKey)) {
		    Map<String, Object> variables = new HashMap<String, Object>();
		    variables.put("workFlowEntityTypeCode", workFlowEntity.getOwnerType().getName());
		    variables.put("workFlowEntityId", workFlowEntity.getOwnerId());
		    variables.put("workFlowSourceEntityId", workFlowEntity.getSourceEntityId());
		    
		    if(getProcessInstancePreKey(workFlowEntity) != null) processInstance = getRuntimeService().startProcessInstanceByKey(getProcessInstancePreKey(workFlowEntity), businessKey, variables);
	    }
		return processInstance;	
	}
	
	private ProcessInstance getInstance(WorkFlowEntity workFlowEntity) {
		ProcessInstance instance = null;
	    String businessKey = getBusinessKey(workFlowEntity); 
		if(!Strings.isEmpty(businessKey)) {
			instance = getRuntimeService().createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
			if(instance == null) {
				businessKey = businessKey.substring(0, businessKey.lastIndexOf("_"));
				instance = getRuntimeService().createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
			}
		}
		return instance;
	}
	
	private String getBusinessKey(WorkFlowEntity workFlowEntity) { 
	    String instancePreKey = getProcessInstancePreKey(workFlowEntity);
		if(Strings.isEmpty(instancePreKey) || Strings.isEmpty(workFlowEntity.getWorkFlowId())) return "";
		return instancePreKey + "_" + workFlowEntity.getWorkFlowId(); 
	}
	
	private String getProcessInstancePreKey(WorkFlowEntity workFlowEntity) {
		return workFlowEntity.getBPMNKey();
	}

	@Override
	public List<Task> getActiveTasks(WorkFlowEntity workFlowEntity) {
		ProcessInstance instance = getInstance(workFlowEntity);
		List<Task> list = new ArrayList<Task>();
		if(instance != null){
			list = getTaskService().createTaskQuery().processInstanceId(instance.getId()).list();
		}
		return list;
	}
	
	@Override
	public List<Task> getActiveTasksByUserId(WorkFlowEntity workFlowEntity, String userId) {
		ProcessInstance instance = getInstance(workFlowEntity);
		List<Task> list = new ArrayList<Task>();
		if(instance != null){
			list = getActiveTasksListByUser(instance, list, userId + "_" + ThreadLocalUtils.getCurrentCorporation().getCode());
		}
		return list;
	}
	
	private List<Task> getActiveTasksListByUser(ProcessInstance instance, List<Task> list, String userId){
		if(list == null) list = new ArrayList<Task>();
		
		if(!Strings.isEmpty(userId)){
			List<Task> taskList = getTaskService().createTaskQuery().processInstanceId(instance.getId()).taskCandidateUser(userId).list();
			for (Task task : taskList) {
				list.add(task);
			}
			ProcessInstance subInstance = getRuntimeService().createProcessInstanceQuery().subProcessInstanceId(instance.getId()).singleResult();
			if(subInstance != null){
				list = getActiveTasksListByUser(subInstance, list, userId);
			}
		}
		
		return list;
	}
	
	@Override
	public List<PvmTransition> getOutPut(WorkFlowEntity workFlowEntity){
		ExecutionEntity executionEntity = (ExecutionEntity) getInstance(workFlowEntity); 
		ProcessDefinition singleResult = getRepositoryService().createProcessDefinitionQuery().
											processDefinitionKey(getProcessInstancePreKey(workFlowEntity)).latestVersion().singleResult();
		
		ProcessDefinitionEntity pde = (ProcessDefinitionEntity) ((RepositoryServiceImpl) getRepositoryService()).getDeployedProcessDefinition(singleResult.getId());
		List<PvmTransition> outgoingTransitions = pde.findActivity(executionEntity.getActivityId()).getOutgoingTransitions();
		return outgoingTransitions;
	}

	@Override
	public List<Task> getSystemActiveTasks(WorkFlowEntity workFlowEntity) {
		ProcessInstance instance = getInstance(workFlowEntity);
		List<Task> list = new ArrayList<Task>();
		if(instance != null){
			list = getSystemActiveTasksList(instance, list);
		}
		return list;
	}
	
	private List<Task> getSystemActiveTasksList(ProcessInstance instance, List<Task> list){
		if(list == null) list = new ArrayList<Task>();
		List<Task> taskList = getTaskService().createTaskQuery().processInstanceId(instance.getId()).taskAssignee(SYSTEM_AUTO).list();
		for (Task task : taskList) {
			list.add(task);
		}
		return list;
	}
	
//	private List<String> extraTransitions(Task task) {
//		List<String> outTransitionIdList = new ArrayList<String>();
//		String name = task.getName();
//		if(name.contains(",")){
//			String[] transitionArray = name.split(",");
//			for (int i = 0; i < transitionArray.length; i++) {
//				outTransitionIdList.add(transitionArray[i]);
//			}
//			return outTransitionIdList;
//		}
//		outTransitionIdList.add(name);
//		return outTransitionIdList;
//		
//	}
	
	private List<String> extraOutPutTransitions(Task task) {
		List<String> outTransitionIdList = new ArrayList<String>();
		String name = task.getTaskDefinitionKey();
		if(name.contains("_")){
			String[] transitionArray = name.split("_");
			for (int i = 0; i < transitionArray.length; i++) {
				String outPutTransitionName = getOutPutTransitionName(transitionArray[i]);
				if(!Strings.isEmpty(outPutTransitionName)) outTransitionIdList.add(outPutTransitionName);
			}
			return outTransitionIdList;
		}
		if(!Strings.isEmpty(getOutPutTransitionName(name))) outTransitionIdList.add(name);
		return outTransitionIdList;
		
	}
	
	private String getOutPutTransitionName(String name){
		if(name.startsWith(WITH_ROLE)){
			
			if(!name.contains("-")) return "";
			String[] roleArray = null;
			String rolesString = name.substring(name.indexOf("-") + 1);
			if(rolesString.indexOf("-") < 0){
				roleArray = new String[]{rolesString};
			}else{
				roleArray = rolesString.split("-");
			}
			if(roleArray.length == 1 && Strings.isEmpty(roleArray[0])) return "";
			List<Group> list = getIdentityService().createGroupQuery().groupMember(ThreadLocalUtils.getCurrentUser().getId() + "_" + ThreadLocalUtils.getCurrentCorporation().getCode()).list();
			for (int i = 0; i < roleArray.length; i++) {
				for (Group group : list) {
					if(roleArray[i].equals(group.getId())) return name;
				}
			}
			return "";
		}
		return name;
	}
	

	private void complete(String taskId, String toTransitionName){
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put(TRANSITION_NAME, toTransitionName);
		getTaskService().complete(taskId, variables);
	}
	
	private void complete(String taskId, String toTransitionName, List<String> parameters){
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put(TRANSITION_NAME, toTransitionName);
		variables.put(SYSTEM_PARAMETER, parameters);
		getTaskService().complete(taskId, variables);
	}
	
	@Override
	public void gotoNext(WorkFlowEntity workFlowEntity, String taskId, String transitionName) {
		if(isTaskCompleted(workFlowEntity, taskId)) throw new DocumentError("Task has been completed by the other process");
		complete(taskId, transitionName);
	}
	
	@Override
	public void gotoNext(WorkFlowEntity workFlowEntity, String taskId, String transitionName, List<String> parameter) {
		if(isTaskCompleted(workFlowEntity, taskId)) throw new DocumentError("Task has been completed by the other process");
		complete(taskId, transitionName, parameter);
	}
	
	private boolean isTaskCompleted(WorkFlowEntity workFlowEntity, String taskId){
		List<Task> activeTasks = getActiveTasks(workFlowEntity);
		for (Task task : activeTasks) {
			if(task.getId().equals(taskId)) return false;
		}
		return true;
	}
	
	@Override
	public WorkFlowActionsInfo getWorkFlowActionsInfo(WorkFlowEntity workFlowEntity, String userId) {
		WorkFlowActionsInfo info = new WorkFlowActionsInfo();
		List<Task> taskList = getActiveTasksByUserId(workFlowEntity, userId);
		for (Task task : taskList) {
			List<String> extraTransitions = extraOutPutTransitions(task);
			info.addTransitions(task.getId(), extraTransitions);
		}
		return info;
	}
	
	@Override
	public void updateActivitiUser(User user, String corportion) {
		Map<Corporation, Set<Role>> map = getRoleSetByCorporation(null);
		for (Corporation corporation : map.keySet()) {
			String corporationUser = user.getId() + "_" + corporation.getCode();
			org.activiti.engine.identity.User activitiUser = (org.activiti.engine.identity.User) getIdentityService().createUserQuery().userId(corporationUser).singleResult();
			if(activitiUser != null) getIdentityService().deleteUser(corporationUser);
			getIdentityService().saveUser(newActivitiUser(user, corporation.getCode()));
			Set<Role> roles = map.get(corporation);
			for (Role role : roles) {
				updateActivitiCandidates(role.getName());
				org.activiti.engine.identity.User result = getIdentityService().createUserQuery().memberOfGroup(role.getName()).userId(corporationUser).singleResult();
				if(result == null){
					getIdentityService().createMembership(corporationUser, role.getName());
				}
			}
		}
	}

	private Map<Corporation, Set<Role>> getRoleSetByCorporation(Set<Role> roles) {
		Map<Corporation, Set<Role>> map = new HashMap<Corporation, Set<Role>>();
		for (Role role : roles) {
			Corporation corporation = role.getCorporation();
			if(corporation == null) continue;
			Set<Role> set = new HashSet<Role>();
			if(map.containsKey(corporation)) set = map.get(corporation);
			set.add(role);
			map.put(corporation, set);
		}
		return map;
	}

	@Override
	public void updateActivitiCandidates(Role role) {
		if(getIdentityService().createGroupQuery().groupId(role.getName()).singleResult() == null){
			Group newGroup = getIdentityService().newGroup(role.getName());
			newGroup.setName(role.getName());
			getIdentityService().saveGroup(newGroup);
		}
	}
	
	private org.activiti.engine.identity.User newActivitiUser(User user, String corporation) {
		org.activiti.engine.identity.User newUser = getIdentityService().newUser(user.getId() + "_" + corporation);
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		return newUser;
	}

	private void updateActivitiCandidates(String roleName) {
		if(getIdentityService().createGroupQuery().groupId(roleName).singleResult() == null){
			Group newGroup = getIdentityService().newGroup(roleName);
			getIdentityService().saveGroup(newGroup);
		}
	}
	
	
	@Override
	public boolean isActiveTask(WorkFlowEntity workFlowEntity, String taskId) {
		return false;
	}
	
	@Override
	public boolean deleteInstanceById(String processInstanceId, boolean deleteHistoryInfo) {
		return false;
	}

	@Override
	public boolean deleteInstance(List<String> processInstanceIdArray, boolean deleteHistoryInfo) {
		return false;
	}

	private RuntimeService getRuntimeService() {
		return processEngine.getRuntimeService();
	}	
	
	private RepositoryService getRepositoryService() {
		return processEngine.getRepositoryService();
	}

	private TaskService getTaskService() {
		return processEngine.getTaskService();
	}

//	private ManagementService getManagementService() {
//		return processEngine.getManagementService();
//	}

//	private HistoryService getHistoryService() {
//		return processEngine.getHistoryService();
//	}
	
	private IdentityService getIdentityService() {
		return processEngine.getIdentityService();
	}


	//------------------activiti restful api----------------------//
	@Override
	public List<ProcessDefinition> getAllDeployedProcess() {
		return getRepositoryService().createProcessDefinitionQuery().list();
	}
	@Override
	public List<ProcessDefinition> getDeployedProcessById(String processDefinitionId) {
		return getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processDefinitionId).list();
	}
	
	@Override
	public List<Task> getTasksByUser(String userId) {
		return getTaskService().createTaskQuery().taskCandidateUser(userId).list();
	}
	
	@Override
	public void autoComplete(String userId, String businessKey) {
		ProcessInstance instance = getRuntimeService().createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
		Task singleResult = getTaskService().createTaskQuery().taskCandidateUser(userId).processInstanceId(instance.getId()).singleResult();
		if(singleResult != null) getTaskService().complete(singleResult.getId());
	}
	
}

