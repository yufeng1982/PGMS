/**
 * 
 */
package com.photo.bas.core.model.common;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONObject;

import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "hr")
public class EmployeePosition extends HrBaseEntity {

	private static final long serialVersionUID = -2044855797149738707L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Employee employee;
	
	@ManyToOne
	private Position position;
	
	public EmployeePosition(){
		super();
	}
	
	@Override
	public String getDisplayString() {
		return null;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("positionId", FormatUtils.idString(position));
    	jo.put("position", FormatUtils.displayString(position));
    	return jo;
    }
}
