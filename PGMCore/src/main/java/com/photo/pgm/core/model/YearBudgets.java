/**
 * 
 */
package com.photo.pgm.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.pgm.core.enums.BudgetType;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class YearBudgets extends AbsCodeNameEntity {

	private static final long serialVersionUID = 3273764140058419247L;

	private Integer years;
	
	private Double budgets = new Double(0);
	
	@Column(columnDefinition = "text")
	private String approveContents;
	
	private String approveNo;
	
	@Enumerated(EnumType.STRING)
	private BudgetType budgetType;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(foreignKey=@ForeignKey(name = "none"))
	private ProjectApproveBudget pab;
	
	
	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("years", FormatUtils.intValue(years));
    	jo.put("budgets", FormatUtils.doubleValue(budgets));
    	jo.put("approveContents", FormatUtils.stringValue(approveContents));
    	jo.put("approveNo", FormatUtils.stringValue(approveNo));
    	jo.put("budgetType", FormatUtils.displayString(budgetType));
    	jo.put("project", FormatUtils.stringValue(pab == null ? "" : pab.getProject().getId()));
    	jo.put("projectName", FormatUtils.stringValue(pab == null ? "" : pab.getProject().getName()));
    	jo.put("projectCode", FormatUtils.stringValue(pab == null ? "" : pab.getProject().getCode()));
    	jo.put("approveDate", FormatUtils.dateValue(pab == null ? null : pab.getApproveDate()));
    	jo.put("pabId", FormatUtils.idString(pab));
    	return jo;
    }
	
	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public Double getBudgets() {
		return budgets;
	}

	public void setBudgets(Double budgets) {
		this.budgets = budgets;
	}

	@Override
	public String getDisplayString() {
		return null;
	}

	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}

	public String getApproveContents() {
		return approveContents;
	}

	public void setApproveContents(String approveContents) {
		this.approveContents = approveContents;
	}

	public String getApproveNo() {
		return approveNo;
	}

	public void setApproveNo(String approveNo) {
		this.approveNo = approveNo;
	}

	public ProjectApproveBudget getPab() {
		return pab;
	}

	public void setPab(ProjectApproveBudget pab) {
		this.pab = pab;
	}

	public BudgetType getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(BudgetType budgetType) {
		this.budgetType = budgetType;
	}
	
}
