/**
 * 
 */
package com.photo.pgm.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.vo.common.TreeNode;
import com.photo.pgm.core.dao.AssetsCategoryRepository;
import com.photo.pgm.core.model.AssetsCategory;
import com.photo.pgm.core.vo.AssetsCategoryQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class AssetsCategoryService extends AbsCodeNameEntityService<AssetsCategory, PageInfo<AssetsCategory>> {

	@Autowired private AssetsCategoryRepository assetsCategoryRepository;
	
	public String findDatas(AssetsCategoryQueryInfo queryInfo) {
		queryInfo.setSf_EQ_level(1);
		Page<AssetsCategory> acRootList = getRepository().findAll(byPage(queryInfo), queryInfo);
		TreeNode rootNode = TreeNode.createTreeNode();
		for (AssetsCategory ac : acRootList.getContent()) {
			rootNode.appendChild(appendRootNode(ac));
		}
		return rootNode.toString();
	}
	
	private TreeNode appendRootNode(AssetsCategory ace) {
		TreeNode node = TreeNode.createTreeNode();
		node.id(ace.getId());
		node.setOtherProperty("code", ace.getCode());
		node.setOtherProperty("name", ace.getName());
		node.setOtherProperty("description", ace.getPathName());
		if(!ace.isLeaf()) {
			node.iconCls("task-folder");
		} else {
			node.iconCls("task");
			node.leaf(true);
		}
		appendChildNode(node, ace);
		return node;
	}
	
	private void appendChildNode(TreeNode node, AssetsCategory ace) {
		TreeNode snode = null;
		List<AssetsCategory> subNodes = getRepository().findByParentAndCorporationAndActiveTrue(ace, ace.getCorporation());
		for(AssetsCategory subNode : subNodes) {
			snode = TreeNode.createTreeNode();
			snode.id(subNode.getId());
			snode.setOtherProperty("code", subNode.getCode());
			snode.setOtherProperty("name", subNode.getName());
			snode.setOtherProperty("description", subNode.getPathName());
			if(!subNode.isLeaf()) {
				snode.iconCls("task-folder");
			} else {
				snode.iconCls("task");
				snode.leaf(true);
			}
			if (!subNode.isLeaf()) {
				appendChildNode(snode, subNode);
			}
			node.appendChild(snode);
		}
	}
	
	@Transactional(readOnly = false)
	public AssetsCategory saveAssetsCategory(AssetsCategory assetsCategory, Boolean parentIsChange, AssetsCategory oldParent, String oldName) {
		AssetsCategory parents = assetsCategory.getParent();
		if (Strings.isEmpty(parents.getCode()) || Strings.isEmpty(parents.getName())) {
			parents = null;
			assetsCategory.setParent(parents);
		}
		if (parents != null) {
			assetsCategory.setLevel(parents.getLevel() + 1);
			parents.setLeaf(false);
			super.save(parents);
			super.save(assetsCategory); // 先保存提方便子节点获取
			assetsCategory = setPaths(assetsCategory, oldName);
			if (!assetsCategory.isNewEntity() /*&& parentIsChange8*/) {
				resetLevel(assetsCategory, oldName); // 重新设置当前节点的字节点的level
			}
		} else {
			super.save(assetsCategory);
			assetsCategory = setPaths(assetsCategory, oldName);
		}
		//更新源父节点是否变成叶子节点
		if (oldParent != null) {
			Integer count = assetsCategoryRepository.findSubNodes(oldParent.getId(), oldParent.getCorporation().getId());
			if (count == 0) {
				oldParent.setLeaf(true);
				super.save(oldParent);
			}
		}
		
		return assetsCategory;
	}
	
	private AssetsCategory setPaths(AssetsCategory assetsCategory, String oldName) {
		String pathIds = "";
		String pathNames = "";
		if (assetsCategory.getParent() != null) {
			pathIds = assetsCategory.getParent().getPathId() + ">" + assetsCategory.getId();
			pathNames = assetsCategory.getParent().getPathName() + ">" + assetsCategory.getName();
		} else {
			if(!assetsCategory.isLeaf()) {
				resetSubPath(assetsCategory, oldName, assetsCategory.getName(), true);
				return assetsCategory;
			} else {
				pathIds = assetsCategory.getId();
				pathNames = assetsCategory.getName();
			}
		}
		assetsCategory.setPathId(pathIds);
		assetsCategory.setPathName(pathNames);
		return assetsCategory;
	}
	
	private void resetLevel(AssetsCategory assetsCategory, String oldName) {
		List<AssetsCategory> subNodes = assetsCategoryRepository.findByParentAndCorporationAndActiveTrue(assetsCategory, assetsCategory.getCorporation());
		for (AssetsCategory subNode : subNodes) {
			subNode.setLevel(subNode.getParent().getLevel() + 1);
			subNode = setPaths(subNode, oldName);
			super.save(subNode);
			if (!subNode.isLeaf()) {
				resetLevel(subNode, oldName);
			}
		}
	}
	
	private void resetSubPath(AssetsCategory assetsCategory, String oldName, String newName, boolean updateCurrentNode) {
		if (updateCurrentNode) {
			assetsCategory.setPathName(assetsCategory.getPathName().replace(oldName, newName));
			save(assetsCategory);
		}
		List<AssetsCategory> subNodes = assetsCategoryRepository.findByParentAndCorporationAndActiveTrue(assetsCategory, assetsCategory.getCorporation());
		for (AssetsCategory subNode : subNodes) {
			subNode.setPathName(subNode.getPathName().replace(oldName, newName));
			super.save(subNode);
			if (!subNode.isLeaf()) {
				resetSubPath(subNode, oldName, newName, false);
			}
		}
	}
	
	@Override
	protected AssetsCategoryRepository getRepository() {
		return assetsCategoryRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
