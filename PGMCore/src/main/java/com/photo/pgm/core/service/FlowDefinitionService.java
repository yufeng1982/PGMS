/**
 * 
 */
package com.photo.pgm.core.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.security.service.RoleService;
import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;
import com.photo.pgm.core.dao.FlowDefinitionRepository;
import com.photo.pgm.core.model.FlowDefinition;
import com.photo.pgm.core.model.FlowDefinitionLines;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class FlowDefinitionService extends AbsCodeNameEntityService<FlowDefinition, PageInfo<FlowDefinition>> {

	@Autowired private FlowDefinitionRepository flowDefinitionRepository;
	@Autowired private FlowDefinitionLinesService flowDefinitionLinesService;
	@Autowired private RoleService roleService;
	
	@Autowired private ActivitiFlowService activitiFlowService;
	
	@Transactional(readOnly = false)
	public FlowDefinition save(FlowDefinition flowDefinition, JSONArray flowDefinitionJsons, List<String> flowDefinitionDeleteLines, String fileDir) {
		save(flowDefinition);
		saveFlowDefinition(flowDefinition, flowDefinitionJsons, flowDefinitionDeleteLines);
		activitiFlowService.createDynamicBPMN(fileDir, flowDefinition.getCode(), flowDefinition.getName(), flowDefinitionJsons);
		return flowDefinition;
	}
	
	
	private void saveFlowDefinition(FlowDefinition flowDefinition, JSONArray flowDefinitionJsons, List<String> flowDefinitionDeleteLines){
		for(String id : flowDefinitionDeleteLines){
			flowDefinitionLinesService.delete(id);
		}
		for(int i = 0; i < flowDefinitionJsons.length(); i++){
			FlowDefinitionLines flowDefinitionLines = null;
			JSONObject jsonObj = flowDefinitionJsons.getJSONObject(i);
			String id = jsonObj.getString("id");
			if(Strings.isEmpty(id)){
				flowDefinitionLines = new FlowDefinitionLines();
			} else {
				flowDefinitionLines = flowDefinitionLinesService.get(id);
			}
			flowDefinitionLines.setFlowDefinition(flowDefinition);
			flowDefinitionLines.setSeqNo(jsonObj.getInt("seqNo"));
			flowDefinitionLines.setCode(jsonObj.getString("fdlcode"));
			flowDefinitionLines.setName(jsonObj.getString("fdlname"));
			flowDefinitionLines.setDescription(jsonObj.getString("description"));
			Role role = roleService.get(jsonObj.getString("role"));
			flowDefinitionLines.setRole(role);
			flowDefinitionLines.setUsers(jsonObj.getString("users"));
			flowDefinitionLines.setUsersText(jsonObj.getString("usersText"));
			flowDefinitionLines.setEdit(jsonObj.getBoolean("edit"));
			flowDefinitionLinesService.save(flowDefinitionLines);
		}
	}
	
	public List<FlowDefinition> findByCorporation(Corporation corporaton) {
		return getRepository().findByCorporationAndActiveTrue(corporaton);
	}
	
	@Override
	protected FlowDefinitionRepository getRepository() {
		return flowDefinitionRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
