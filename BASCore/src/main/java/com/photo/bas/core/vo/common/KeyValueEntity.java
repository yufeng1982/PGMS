/**
 * 
 */
package com.photo.bas.core.vo.common;

/**
 * @author FengYu
 *
 */
public class KeyValueEntity {
	private String key;
	private String value;
	
	public KeyValueEntity() {
	}
	
	public KeyValueEntity(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
