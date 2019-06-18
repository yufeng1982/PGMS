/**
 * 
 */
package com.photo.bas.core.model.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author FengYu
 *
 */

@MappedSuperclass
public class AbsViewEntity implements Serializable {
	private static final long serialVersionUID = -2456556656898099299L;

	@Id
	private String id;
	
	public String getId() {
		return id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof AbsViewEntity == false)
			return false;
		AbsViewEntity other = (AbsViewEntity) obj;
		if (this.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.getId().equals(other.getId()))
			return false;
		
		return true;
	}
}