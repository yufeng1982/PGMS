/**
 * 
 */
package com.photo.pgm.core.vo;

import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.OilCanLine;
import com.photo.pgm.core.model.Project;

/**
 * @author FengYu
 *
 */
public class OilCanLineQueryInfo extends PageInfo<OilCanLine> {
	
	private Project sf_EQ_project;

	public Project getSf_EQ_project() {
		return sf_EQ_project;
	}

	public void setSf_EQ_project(Project sf_EQ_project) {
		this.sf_EQ_project = sf_EQ_project;
	}
}
