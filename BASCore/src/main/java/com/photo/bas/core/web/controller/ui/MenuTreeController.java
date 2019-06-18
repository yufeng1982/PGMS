package com.photo.bas.core.web.controller.ui;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.model.security.FunctionNodeType;
import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.security.service.AppResourceService;
import com.photo.bas.core.security.service.FunctionNodeManager;
import com.photo.bas.core.security.service.RoleService;
import com.photo.bas.core.service.common.StateService;
import com.photo.bas.core.service.entity.AbsService;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.vo.common.TreeNode;
import com.photo.bas.core.web.controller.entity.AbsController;



/**
 * @author FengYu
 */
@Controller
@RequestMapping("/*/ui/menu/tree")
@SuppressWarnings("rawtypes")
public class MenuTreeController extends AbsController<FunctionNode> {
	
	@Autowired FunctionNodeManager functionNodeManager;
	@Autowired private StateService stateService;
	@Autowired private RoleService roleService;
	@Autowired private AppResourceService appResourceService;

	@Override
	protected AbsService getEntityService() {
		return null;
	}
	
	@RequestMapping(value = "root")
	public ModelAndView tree() {
		return toJSONView(getRootLevelMenuJSON().toString());
	}
	
	private JSONArray getRootLevelMenuJSON() {
		List<FunctionNodeType> functionTypeList = functionNodeManager.findValidFunctionNodeTypesForCurrentUser();
		JSONArray menuPanelItemsArray = new JSONArray();
		for (FunctionNodeType eachType : functionTypeList) {
			menuPanelItemsArray.put(eachType.toJson());
		}
		return menuPanelItemsArray;
	}

	@RequestMapping("json")
	public ModelAndView getFirstLevelFunctionNodes(@RequestParam("node") String id) {
		TreeNode root = TreeNode.createRoot();
		String userFunctionNodeIds = functionNodeManager.getValidFunctionNodeIdsFromUser(ThreadLocalUtils.getCurrentUser());
		List<FunctionNode> functionNodes = null;
		FunctionNodeType functionNodeType = functionNodeManager.getFunctionNodeType(id);
		
		if(functionNodeType != null) {
			functionNodes = functionNodeType.getList();
		} else {
			FunctionNode functionNode = functionNodeManager.getFunctionNodeFolder(id);
			functionNodes = functionNode.getList();
		}
		for (FunctionNode eachFun : functionNodes) {
			if(isValidFunctionNode(userFunctionNodeIds, eachFun)) root.appendRootChild(toTreeNode(eachFun));
		}

		return toJSONView(root.toRootString());
	}
	
	@RequestMapping(value = "byRole")
	public ModelAndView getFunctionNodesByRole(@RequestParam("functionNodeIds") String functionNodeIds) {
		Corporation corporation = ThreadLocalUtils.getCurrentCorporation();
		List<FunctionNodeType> functionTypeList = functionNodeManager.findAllFunctionNodeTypes();
		TreeNode root = TreeNode.createRoot();
		if(corporation != null){
//			Role adminRole = roleService.getAdminRole(corporation);
//			String corporationNodes = adminRole.getFunctionNodeIds();
//			functionTypeList = functionNodeManager.buildFunctionNodesByNodeIds(corporationNodes);
			String currentUserfunctionNodeIds = getFunctionNodeByCurrentUser();// 获取当前用户角色的所有fnids
			functionTypeList = functionNodeManager.buildFunctionNodesByNodeIds(currentUserfunctionNodeIds);// 获取当前登录用户的角色的所有 fntype
		}
		for (FunctionNodeType functionNodeType : functionTypeList) {
			if(!functionNodeType.getId().equals("0")) {
				root.appendRootChild(toStaticTreeNode(functionNodeType, functionNodeIds));
			}
		}
		
		return toJSONView(root.toRootString());
	}

	private String getFunctionNodeByCurrentUser() {
		String functionNodeIds = "";
		Set<Role> roles = ThreadLocalUtils.getCurrentUser().getRoles();
		Iterator<Role> itRoles = roles.iterator();
		while(itRoles.hasNext()) {
			Role role = itRoles.next();
			if (Strings.isEmpty(functionNodeIds)) {
				functionNodeIds = role.getFunctionNodeIds();
			} else {
				String currentFnIds = role.getFunctionNodeIds();
				String fnIds[] = currentFnIds.split(",");
				for (String fnId : fnIds) {
					if (!functionNodeIds.contains(fnId)) {
						functionNodeIds += fnId + ",";
					}
				}
			}
		}
		return functionNodeIds;
	}
	
	@RequestMapping(value = "/allResources")
	public ModelAndView getAllResources(@RequestParam("role") Role role) {
		return toJSONView(appResourceService.getAllAsTree(role).toString());
	}
	
	private TreeNode toTreeNode(FunctionNode fn) {
		TreeNode node = TreeNode.createTreeNode();
		node.id(fn.getId());
		String text = getMessage(fn.getKey());
		node.text(text);
		node.icon(fn.getImgSrc());
		node.iconCls(fn.getIconCls());
		node.leaf(fn.isLeaf());
		node.singleClickExpand(true);
		node.url(fn.getUrl());
		node.qtip(text);
		return node;
	}
	
	private TreeNode toStaticTreeNode(FunctionNodeType fnt, String functionNodeIds) {
		TreeNode node = TreeNode.createTreeNode();
		node.id(fnt.getId());
		String text = getMessage(fnt.getKey());
		node.text(text);
		node.icon(fnt.getImgSrc());
//		node.iconCls(fnt.getIconCls());
		node.leaf(false);
		node.singleClickExpand(true);
		node.url("");
		node.qtip(text);
		List<FunctionNode> functionNodes = fnt.getList();
		for (FunctionNode eachFun : functionNodes) {
			appendStaticTreeNodeChild(node,  eachFun, functionNodeIds);
		}
		return node;
	}

	private void appendStaticTreeNodeChild(TreeNode pNode, FunctionNode fn, String functionNodeIds) {
		TreeNode node = TreeNode.createTreeNode();
		node.id(fn.getId());
		String text = getMessage(fn.getKey());
		node.text(text);
		node.icon(fn.getImgSrc());
		node.leaf(fn.isLeaf());
		if(fn.isLeaf()) {
			node.checked(isValidFunctionNode(functionNodeIds, fn.getId()));
		}
		node.singleClickExpand(true);
		node.url(fn.getUrl());
		node.qtip(text);
		List<FunctionNode> functionNodes = fn.getList();
		for (FunctionNode eachFun : functionNodes) {
			appendStaticTreeNodeChild(node, eachFun, functionNodeIds);
		}
		pNode.appendChild(node);
	}
	private boolean isValidFunctionNode(String functionNodeIds, String id) {
		Corporation corporation = ThreadLocalUtils.getCurrentCorporation();
		String corporationNodes = corporation != null?corporation.getFunctionNodes():functionNodeIds;
		if(functionNodeIds == null) return false;
		return functionNodeIds.indexOf(id) != -1 && corporationNodes.indexOf(id) != -1;
	}
	
	private boolean isValidFunctionNode(String functionNodeIds, FunctionNode fn) {
		// TODO, HACK ignore for admin user
		if(ThreadLocalUtils.getCurrentUser().getLoginName().equals("admin")) return true;
		// TODO remove it later
		if(ThreadLocalUtils.getCurrentUser().getLoginName().equals("ian")) return true;
		
		if(fn.isLeaf()) return isValidFunctionNode(functionNodeIds, fn.getId());
		
		List<FunctionNode> nodes = fn.getList();
		for (FunctionNode functionNode : nodes) {
			if(isValidFunctionNode(functionNodeIds, functionNode)) {
				return true;
			}
		}
		return false;
	}

}
