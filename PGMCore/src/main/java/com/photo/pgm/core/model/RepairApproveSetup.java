/**
 * 
 */
package com.photo.pgm.core.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.pgm.core.enums.RepairApproveType;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class RepairApproveSetup extends AbsCodeNameEntity {

	
	private static final long serialVersionUID = -5643935158326206466L;

	private Integer seq = new Integer(0);
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Role role;
	
	private String users;
	private String usersText;
	
	@Enumerated(EnumType.STRING)
	private RepairApproveType pat;
	
	private Double judgeAmount = new Double(0);
	
	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("seq", FormatUtils.intValue(seq));
    	jo.put("users", FormatUtils.stringValue(users));
    	jo.put("usersText", FormatUtils.stringValue(usersText));
    	jo.put("role", FormatUtils.idString(role));
    	jo.put("roleText", FormatUtils.stringValue(role == null ? "" : role.getName()));
    	jo.put("judgeAmount", FormatUtils.doubleValue(judgeAmount));
    	return jo;
    }
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getSeq() {
		return seq;
	}


	public void setSeq(Integer seq) {
		this.seq = seq;
	}


	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getUsersText() {
		return usersText;
	}


	public void setUsersText(String usersText) {
		this.usersText = usersText;
	}


	public RepairApproveType getPat() {
		return pat;
	}


	public void setPat(RepairApproveType pat) {
		this.pat = pat;
	}


	public Double getJudgeAmount() {
		return judgeAmount;
	}

	public void setJudgeAmount(Double judgeAmount) {
		this.judgeAmount = judgeAmount;
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
