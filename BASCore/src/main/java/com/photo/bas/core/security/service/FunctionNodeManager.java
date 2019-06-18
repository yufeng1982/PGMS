/**
 * 
 */
package com.photo.bas.core.security.service;

import java.util.List;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.model.security.FunctionNodeType;
import com.photo.bas.core.model.security.User;

/**
 * @author FengYu
 *
 */
public interface FunctionNodeManager {

	public List<FunctionNodeType> findAllFunctionNodeTypes();
	
	public FunctionNode findFunctionNodeByUrl(String url);

	public List<FunctionNodeType> findValidFunctionNodeTypesForCurrentUser();

	public FunctionNode getFunctionNode(String id);
	
	public List<FunctionNode> getFunctionNodes(String[] ids);
	
	public FunctionNode getFunctionNodeFolder(String id);
	
	public FunctionNodeType getFunctionNodeType(String id);

	public String getValidFunctionNodeIdsFromUser(User user);
	
	public List<FunctionNode> findValidFunctionNodesForCurrentUser();
	
	public List<FunctionNodeType> buildFunctionNodesByNodeIds(String functionNodeIds);

}
