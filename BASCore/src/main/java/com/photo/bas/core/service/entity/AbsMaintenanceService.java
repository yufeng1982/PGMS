/**
 * 
 */
package com.photo.bas.core.service.entity;

import org.json.JSONArray;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.entity.IMaintenance;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public abstract class AbsMaintenanceService<T extends AbsCodeNameEntity, P extends PageInfo<T>> extends AbsCodeNameEntityService<T, P> {

	
	public JSONArray buildStore(Iterable<T> modelList) {
		JSONArray jsonArray = new JSONArray();
		for(IMaintenance m : modelList){
			JSONObject record = m.toJSONObject();
			jsonArray.put(record);
		}
		return jsonArray;
	}

	public T getByCode(String code) {
		return getByCode(code);
	}
}
