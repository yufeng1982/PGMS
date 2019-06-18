/**
 * 
 */
package com.photo.pgm.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
public class ProjectApproveBudget extends AbsCodeNameEntity {

	private static final long serialVersionUID = 8414429474174731407L;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Project project;
	
	private Double amount = new Double(0);
	
	private String oilLevel;
	private String approveLevel;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date approveDate;
	
	private String fileName;
	private String filePath;

	public String getProjectCode() {
		return project.getCode();
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getOilLevel() {
		return oilLevel;
	}

	public void setOilLevel(String oilLevel) {
		this.oilLevel = oilLevel;
	}

	public String getApproveLevel() {
		return approveLevel;
	}

	public void setApproveLevel(String approveLevel) {
		this.approveLevel = approveLevel;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("projectId", FormatUtils.idString(getProject()));
    	jo.put("projectCode", FormatUtils.stringValue(getProject().getCode()));
    	jo.put("projectName", FormatUtils.stringValue(getProject().getName()));
    	jo.put("oilLevel", FormatUtils.stringValue(oilLevel));
    	jo.put("approveLevel", FormatUtils.stringValue(approveLevel));
    	jo.put("amount", FormatUtils.doubleValue(amount));
    	jo.put("approveDate", FormatUtils.dateValue(approveDate));
    	jo.put("fileName", FormatUtils.stringValue(fileName));
    	jo.put("name", FormatUtils.stringValue(getProject().getCode()));
    	return jo;
    }
	
	@Override
	public String getDisplayString() {
		return  getCode();
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
