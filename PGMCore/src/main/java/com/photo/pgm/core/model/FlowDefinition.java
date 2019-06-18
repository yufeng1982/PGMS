/**
 * 
 */
package com.photo.pgm.core.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.entity.ISerial;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
public class FlowDefinition extends AbsCodeNameEntity implements ISerial {

	private static final long serialVersionUID = -3991528461277276233L;

	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}

	@Override
	public String getSequenceName() {
		return WORKFLOW_SEQUENCE + "_" + getCorporation().getId();
	}

	@Override
	public String getPrefix() {
		return WORKFLOW;
	}

	@Override
	public int getSequenceLength() {
		return 6;
	}

	@Override
	public String getSequenceNameSuffix() {
		return null;
	}

	@Override
	public List<String> getOtherDiscriminatorNames() {
		return null;
	}

	@Override
	public List<Object> getOtherDiscriminatorValues() {
		return null;
	}
}
