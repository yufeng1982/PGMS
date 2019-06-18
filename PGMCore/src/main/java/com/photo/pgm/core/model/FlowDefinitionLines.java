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
import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class FlowDefinitionLines extends AbsCodeNameEntity {

	private Integer seqNo;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private FlowDefinition flowDefinition;
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Role role;
	
	private String users;
	private String usersText;
	
	@Type(type = "true_false")
	private Boolean edit = Boolean.FALSE;
	
	private static final long serialVersionUID = -7815581294206513115L;

	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}

	public FlowDefinition getFlowDefinition() {
		return flowDefinition;
	}

	public void setFlowDefinition(FlowDefinition flowDefinition) {
		this.flowDefinition = flowDefinition;
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("seqNo", FormatUtils.intValue(seqNo));
    	jo.put("fdlcode", FormatUtils.stringValue(getCode()));
    	jo.put("fdlname", FormatUtils.stringValue(getName()));
    	jo.put("flowDefinition", FormatUtils.idString(flowDefinition));
    	jo.put("flowDefinitionText", FormatUtils.displayString(flowDefinition));
    	jo.put("role", FormatUtils.idString(role));
    	jo.put("roleText", FormatUtils.stringValue(role == null ? "" : role.getName()));
    	jo.put("users", FormatUtils.stringValue(users));
    	jo.put("usersText", FormatUtils.stringValue(usersText));
    	jo.put("edit", FormatUtils.booleanValue(edit));
    	return jo;
    }

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

}
