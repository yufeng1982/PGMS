/**
 * 
 */
package com.photo.pgm.core.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
public class PetrolStation extends AbsCodeNameEntity {

	private static final long serialVersionUID = 2155217183682856641L;

	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Project project;
	
	private String shortName; // 简称
	private String sapCode;
	private String cnCode;
	
	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("shortName", FormatUtils.stringValue(shortName));
    	jo.put("sapCode", FormatUtils.stringValue(sapCode));
    	jo.put("cnCode", FormatUtils.stringValue(cnCode));
    	jo.put("project", FormatUtils.idString(project));
    	jo.put("projectCode", FormatUtils.stringValue(project.getCode()));
    	jo.put("projectCity", FormatUtils.stringValue(project.getPct()));
    	jo.put("projectAddress", FormatUtils.stringValue(project.getAddress()));
    	return jo;
    }
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getSapCode() {
		return sapCode;
	}

	public void setSapCode(String sapCode) {
		this.sapCode = sapCode;
	}

	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}

}
