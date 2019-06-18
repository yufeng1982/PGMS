/**
 * 
 */
package com.photo.bas.core.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.photo.bas.core.model.entity.IEntity;

/**
 * @author FengYu
 *
 */
@Deprecated
public class Result<T> implements Serializable {
	
	private static final long serialVersionUID = -4739516385420099273L;
	
	public static final Result<Object> OK = new Result<Object>(0, "OK");	

	public static final Result<Object> ERROR = new Result<Object>(-1, "ERROR", "Error occurred");
	
	private String code;
	private String message;
	
	private int intValue; 
	
	private T t;

	private List<Result<T>> results;
	
	public void resetIterator() {
    	if (results == null)
    		results = new ArrayList<Result<T>>();
    }
	public static <T> Result<T> createSuccess(T object) {
		return new Result<T>(1,  "success", "success", object);
	}
	public static <T> Result<T> createFailure(String message, T object) {
		return new Result<T>(-1,  "failure", message, object);
	}
	public static <T> Result<T> createSuccess() {
		return new Result<T>(1,  "success", "success", null);
	}
	public static <T> Result<T> createFailure(String message) {
		return new Result<T>(-1,  "failure", message, null);
	}
	public static <T> Result<T> createWarn(String code, String message) {
		return new Result<T>(1,  code, message);
	}
	public static <T> Result<T> create(int val, String code, String message){
		return new Result<T>(val,  code, message);
	}
	public static <T> Result<T> create(int val, String code, String message, T object){
		return new Result<T>(val,  code, message, object);
	}
	public static <T> Result<T> createOK(String code, String message, T object) {
		return new Result<T>(0,  code, message, object);
	}
	public static <T> Result<T> createOK(String code, String message) {
		return new Result<T>(0,  code, message);
	}
	public static <T> Result<T> createError(String code, String message, T object) {
		return new Result<T>(-1,  code, message, object);
	}
	public static <T> Result<T> createError(String code, String message) {
		return new Result<T>(-1,  code, message);
	}

	protected Result(int val, String code, String message) {
		this.code = code;
		this.message = message;
		this.intValue = val;
	}
	protected Result(int val, String code, String message, T object) {
		this.code = code;
		this.message = message;
		this.intValue = val;
		this.setObject(object);
	}
	protected Result(int val, String code) {
		this.code = code;
		this.intValue = val;
	}

	public String getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}

	private int getIntValue() {
		return intValue;
	}
	
	public boolean isSuccess() {
		return getIntValue() >= 0;
	}
	public boolean isFailure(){
		return getIntValue() < 0;
	}
	public boolean isOK(){
		return(getIntValue()==0);
	}
	public boolean isError(){
		return(getIntValue()<0);
	}
	public boolean isWarn(){
		return(getIntValue()>0);
	}
	
	public String toString() {
		return "Code [" + this.getCode() + "], Message [" + this.getMessage() + "]";
	}
	
	public T getObject() {
		return t;
	}
	public void setObject(T object) {
		this.t = object;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + intValue;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((t == null) ? 0 : t.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Result))
			return false;
		final Result other = (Result) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (intValue != other.intValue)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (t == null) {
			if (other.t != null)
				return false;
		} else if (!t.equals(other.t))
			return false;
		return true;
	}
	
    public JSONObject toJSONObject() {
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("code", code);
    	jsonObject.put("message", message);
    	if(this.t != null && this.t instanceof IEntity) {
        	jsonObject.put("object", ((IEntity) t).toJSONObject());
    	}
    	return jsonObject;
    }
}
