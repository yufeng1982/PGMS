/**
 * 
 */
package com.photo.bas.core.vo.entity;

import java.util.ArrayList;
import java.util.List;

import com.photo.bas.core.model.entity.AbsSourceEntity;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;

/**
 * @author FengYu
 *
 */
public class SourceEntityQueryInfo extends PageInfo<AbsSourceEntity> {
	private String statusIncluded;
	private String statusExcluded;
	private String status;
	private String name;
	
	public SourceEntityQueryInfo() {
		super();
		this.setOrderBy("code");
		this.setOrder("desc");
	}
	public String getStatusIncluded() {
		return statusIncluded;
	}
	public void setStatusIncluded(String statusIncluded) {
		this.statusIncluded = statusIncluded;
	}
	public String getStatusExcluded() {
		return statusExcluded;
	}
	public void setStatusExcluded(String statusExcluded) {
		this.statusExcluded = statusExcluded;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getStatusExcludedList() {
		return getSplitList(getStatusExcluded());
	}

	public List<String> getStatusIncludedList() {
		return getSplitList(getStatusIncluded());
	}

	private List<String> getSplitList(String str) {
		List<String> list = new ArrayList<String>();
		if(!Strings.isEmpty(str)) {
			String[] array = str.split(",");
			for (int i = 0; i < array.length; i++) {
				String s = array[i];
				if(!Strings.isEmpty(s)) list.add(s);
			}
		}
		return list;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
