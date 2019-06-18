/**
 * 
 */
package com.photo.pgm.core.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.vo.security.UserQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class ActivitiFlowService {

	@Autowired private RepositoryService repositoryService;
	@Autowired private RuntimeService runtimeService;
	@Autowired private TaskService taskService;
	@Autowired private HistoryService historyService;
	
	@Autowired private UserService userService;

	@Transactional(readOnly = false)
	public boolean createDynamicBPMN(String fileDir, String processDefinitionKey, String processDefinitionName, JSONArray flowDefinitionJsons) {
		// build process bpmn model
		BpmnModel model = new BpmnModel();
		Process process = new Process();
		model.addProcess(process);
		process.setId(processDefinitionKey);
		
		// build process node
		process.addFlowElement(createStartEvent());
		for (int i = 0; i < flowDefinitionJsons.length(); i++) {
			JSONObject jo = flowDefinitionJsons.getJSONObject(i);
			String candidateUser = Strings.isEmpty(jo.getString("usersText")) ? "" : jo.getString("usersText");
			if (!Strings.isEmpty(jo.getString("role"))) {
				UserQueryInfo queryInfo = new UserQueryInfo();
				queryInfo.setSf_EQ_roleId(jo.getString("role"));
				Page<User> users = userService.getUsersBySearch(queryInfo);
				for (User user : users.getContent()) {
					String loginName = user.getLoginName();
					if (!candidateUser.contains(loginName)) {
						if (Strings.isEmpty(candidateUser)) {
							candidateUser = loginName;
						} else {
							candidateUser += "," + loginName;
						}
					}
				}
			}
			List<String> candidateUserList = Arrays.asList(candidateUser.split(","));
			process.addFlowElement(createUserTask(jo.getString("fdlcode"), jo.getString("fdlname"), candidateUserList));
		}
		process.addFlowElement(createEndEvent());
		// build process flow line
		for (int i = 0; i < flowDefinitionJsons.length(); i++) {
			JSONObject jo = flowDefinitionJsons.getJSONObject(i);
			JSONObject previousJo = null;
			if (i > 0) previousJo = flowDefinitionJsons.getJSONObject(i - 1);
			if (i == 0) {
				process.addFlowElement(createSequenceFlow("start", "task" + jo.getString("fdlcode")));
			} else if (i == (flowDefinitionJsons.length() - 1)) {
				process.addFlowElement(createSequenceFlow("task" + previousJo.getString("fdlcode"), "task" + jo.getString("fdlcode")));
				process.addFlowElement(createSequenceFlow("task" + jo.getString("fdlcode"), "end"));
			} else {
				process.addFlowElement(createSequenceFlow("task" + previousJo.getString("fdlcode"), "task" + jo.getString("fdlcode")));
			}
		}

		// 2. Generate graphical information
		new BpmnAutoLayout(model).execute();

		// 3. Deploy the process to the engine
		Deployment deployment = repositoryService.createDeployment()
												.addBpmnModel(processDefinitionKey + ".bpmn", model)
												.name(processDefinitionName).deploy();

		// 4. Start a process instance
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                												.deploymentId(deployment.getId())
//                												.processDefinitionKey(processDefinitionKey)
                												.singleResult();

		// 5. Check if task is available.
//		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();

		// 6. Save process diagram to a file
		InputStream processDiagram = repositoryService.getProcessDiagram(processDefinition.getId());
		try {
			FileUtils.copyInputStreamToFile(processDiagram, new File(fileDir + "/" + processDefinitionKey + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 7. Save resulting BPMN xml to a file
		InputStream processBpmn = repositoryService.getResourceAsStream(deployment.getId(), processDefinitionKey + ".bpmn");
		try {
			FileUtils.copyInputStreamToFile(processBpmn, new File(fileDir + "/" + processDefinitionKey + ".bpmn20.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	public Task getTaskById(String taskId) {
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}
	
	public boolean isDeployed(String processDefinitionKey) {
		long pdCounts = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).count();
		if (pdCounts > 0) return true;
		return false;
	}
	
	@Transactional(readOnly = false)
	public ProcessInstance startProcessInstance(String entityId, String processType, String processDefinitionKey) {
		Map<String, Object> varsMap = new HashMap<String, Object>();
		varsMap.put("entityId", entityId);
		varsMap.put("processType", processType);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, varsMap);
		return processInstance;
	}
	
	@Transactional(readOnly = false)
	public void completeTask(String processInstanceId, String candidateUser, String entityId){
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskCandidateUser(candidateUser).singleResult();
		taskService.complete(task.getId());
	}
	
	@Transactional(readOnly = false)
	public void completeTask(String taskId, String entityId){
		taskService.complete(taskId);
	}
	
	@Transactional(readOnly = false)
	public void completeTaskWithTransition(String taskId, String transitionName){
		Map<String, Object> varsMap = new HashMap<String, Object>();
		varsMap.put("transitionName", transitionName);
		taskService.complete(taskId,varsMap);
	}
	
	@Transactional(readOnly = false)
	public void deleteProcessInstance(String processInstanceId){
		runtimeService.deleteProcessInstance(processInstanceId, "流程被驳回起点重新送审");
	}
	
	public ProcessInstance getProcessInstance(String processInstanceId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	}
	
	public HistoricProcessInstance getHistoricProcessInstance(String processInstanceId) {
		return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	}
	
	public List<Task> getTaskList4currentUser4Contract(String candidateUser) {
		List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(candidateUser).processVariableValueEquals("processType", "Contract").orderByTaskCreateTime().asc().list();
		return taskList;
	}
	
	public List<Task> getTaskList4currentUser4Contract(String candidateUser, int beginIndex) {
		List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(candidateUser).processVariableValueEquals("processType", "Contract").orderByTaskCreateTime().asc().listPage(beginIndex, 30);
		return taskList;
	}
	
	public List<HistoricTaskInstance> getHistoryTaskList4currentUser4Contract(String candidateUser) {
		List<HistoricTaskInstance> historytaskList = historyService.createHistoricTaskInstanceQuery().taskCandidateUser(candidateUser).processVariableValueEquals("processType", "Contract").orderByHistoricTaskInstanceEndTime().asc().finished().list();
		return historytaskList;
	}
	
	public List<HistoricTaskInstance> getHistoryTaskList4currentUser4Contract(String candidateUser, int beginIndex) {
		List<HistoricTaskInstance> historytaskList = historyService.createHistoricTaskInstanceQuery().taskCandidateUser(candidateUser).processVariableValueEquals("processType", "Contract").orderByHistoricTaskInstanceEndTime().asc().finished().listPage(beginIndex, 30);
		return historytaskList;
	}
	
	public List<Task> getTaskList4currentUser4Repair(String candidateUser) {
		List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(candidateUser).processVariableValueEquals("processType", "Repair").orderByTaskCreateTime().asc().list();
		return taskList;
	}
	
	public List<Task> getTaskList4currentUser4Repair(String candidateUser, int beginIndex) {
		List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(candidateUser).processVariableValueEquals("processType", "Repair").orderByTaskCreateTime().asc().listPage(beginIndex, 30);
		return taskList;
	}
	
	public List<Task> getTaskList4currentUser4RSA(String candidateUser) {
		List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(candidateUser).processVariableValueEquals("processType", "RepairSettleAccount").orderByTaskCreateTime().asc().list();
		return taskList;
	}
	
	public List<Task> getTaskList4currentUser4RSA(String candidateUser, int beginIndex) {
		List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(candidateUser).processVariableValueEquals("processType", "RepairSettleAccount").orderByTaskCreateTime().asc().listPage(beginIndex, 30);
		return taskList;
	}
	
	public List<HistoricTaskInstance> getHistoryTaskList4currentUser4Repair(String candidateUser) {
		List<HistoricTaskInstance> historytaskList = historyService.createHistoricTaskInstanceQuery().taskCandidateUser(candidateUser).processVariableValueEquals("processType", "Repair").orderByHistoricTaskInstanceEndTime().asc().finished().list();
		return historytaskList;
	}
	
	public List<HistoricTaskInstance> getHistoryTaskList4currentUser4Repair(String candidateUser, int beginIndex) {
		List<HistoricTaskInstance> historytaskList = historyService.createHistoricTaskInstanceQuery().taskCandidateUser(candidateUser).processVariableValueEquals("processType", "Repair").orderByHistoricTaskInstanceEndTime().asc().finished().listPage(beginIndex, 30);
		return historytaskList;
	}
	
	public List<HistoricTaskInstance> getHistoryTaskList4currentUser4RSA(String candidateUser) {
		List<HistoricTaskInstance> historytaskList = historyService.createHistoricTaskInstanceQuery().taskCandidateUser(candidateUser).processVariableValueEquals("processType", "RepairSettleAccount").orderByHistoricTaskInstanceEndTime().asc().finished().list();
		return historytaskList;
	}
	
	public List<HistoricTaskInstance> getHistoryTaskList4currentUser4RSA(String candidateUser, int beginIndex) {
		List<HistoricTaskInstance> historytaskList = historyService.createHistoricTaskInstanceQuery().taskCandidateUser(candidateUser).processVariableValueEquals("processType", "RepairSettleAccount").orderByHistoricTaskInstanceEndTime().asc().finished().listPage(beginIndex, 30);
		return historytaskList;
	}
	
	public List<HistoricTaskInstance> getHistoryTaskList4CurrentPI(String entityId) {
		List<HistoricTaskInstance> historytaskList = historyService.createHistoricTaskInstanceQuery().processVariableValueEquals("entityId", entityId).finished().list();
		return historytaskList;
	}
	
	public Task getActivityTask(String processInstanceId) {
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		return task;
	}
	
	public Map<String, Object> getProcessVariables(String processInstanceId) {
		Map<String, Object> variables = runtimeService.getVariables(processInstanceId);
		return variables;
	}
	
	public Map<String, Object> getHistoryProcessVariables(String processInstanceId) {
		List<HistoricVariableInstance> hviList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
		Map<String, Object> variables = new HashMap<String, Object>();
		for (HistoricVariableInstance hvi : hviList) {
			variables.put(hvi.getVariableName(), hvi.getValue());
		}
		return variables;
	}
	
	public Map<String, Object> getTaskVariables(String taskId) {
		Map<String, Object> variables = taskService.getVariables(taskId);
		return variables;
	}
	
	public Task getActivityTask(String processInstanceId,String candidateUser) {
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskCandidateUser(candidateUser).singleResult();
		return task;
	}
	
	public long getCountsOfPI4CurrentPD (String processDefinitionKey) {
		return runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).count();
	}
	
	public List<ProcessInstance> getPI4CurrentPD (String processDefinitionKey) {
		return runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).list();
	}
	
	public boolean isCanReDeploy(String processDefinitionKey) {
//		List<ProcessInstance> pilist = getPI4CurrentPD(processDefinitionKey);
//		for (ProcessInstance pi : pilist) {
//			deleteProcessInstance(pi.getId());
//		}
		if (getCountsOfPI4CurrentPD(processDefinitionKey) > 0) {
			return false;
		}
		return true;
	}
	
	private UserTask createUserTask(String id, String name, List<String> candidateUsers) {
		UserTask userTask = new UserTask();
		userTask.setName(name);
		userTask.setId("task" + id);
		userTask.setCandidateUsers(candidateUsers);
		// 给任务添加监听类
		List<ActivitiListener> alList = new ArrayList<ActivitiListener>();
		ActivitiListener listener = new ActivitiListener();
		listener.setImplementation("com.photo.pgm.core.service.ContractApproveListener");
		listener.setImplementationType("class");
		listener.setEvent("create");
		alList.add(listener);
		userTask.setTaskListeners(alList);
		return userTask;
	}

	private SequenceFlow createSequenceFlow(String from, String to) {
		SequenceFlow flow = new SequenceFlow();
		flow.setSourceRef(from);
		flow.setTargetRef(to);
		return flow;
	}

	private StartEvent createStartEvent() {
		StartEvent startEvent = new StartEvent();
		startEvent.setId("start");
		return startEvent;
	}

	private EndEvent createEndEvent() {
		EndEvent endEvent = new EndEvent();
		endEvent.setId("end");
		return endEvent;
	}
}
