/**
 * 
 */
package com.photo.pgm.core.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class AssetsCategory extends AbsCodeNameEntity{

	private static final long serialVersionUID = 610818357533761917L;

	private String pathId; // 所有节点id路径
	
	private String pathName; // 所有节点名称路径
	
	@Type(type = "true_false")
	@Audited
	private Boolean leaf = Boolean.TRUE; // 是否是叶子节点
	
	private Integer level = new Integer(1); // 级别 默认1级
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private AssetsCategory parent; // 父亲节点
	
	public AssetsCategory(){
		super();
	}
	

	public String getPathId() {
		return pathId;
	}


	public void setPathId(String pathId) {
		this.pathId = pathId;
	}


	public String getPathName() {
		return pathName;
	}


	public void setPathName(String pathName) {
		this.pathName = pathName;
	}


	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public AssetsCategory getParent() {
		return parent;
	}

	public void setParent(AssetsCategory parent) {
		this.parent = parent;
	}

	public String getDisplayString(){
		return getName();
	}
	
	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("leaf", FormatUtils.booleanValue(leaf));
    	jo.put("level", FormatUtils.intValue(level));
    	jo.put("pathId", FormatUtils.stringValue(pathId));
    	jo.put("pathName", FormatUtils.stringValue(pathName));
    	return jo;
    }

	
}
