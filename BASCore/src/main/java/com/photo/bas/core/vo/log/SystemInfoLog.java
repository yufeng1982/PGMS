/**
 * 
 */
package com.photo.bas.core.vo.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.Ownership;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
public class SystemInfoLog implements Serializable {
	private static final long serialVersionUID = -7039719549643617765L;
	
	private Map<SourceEntityType, List<Ownership>> children = new HashMap<SourceEntityType, List<Ownership>>();
	
	public void addChild(Ownership ownership) {
		List<Ownership> list = children.get(ownership.getOwnerType());
		if(list == null) {
			list = new ArrayList<Ownership>();
			children.put(ownership.getOwnerType(), list);
		}
		list.add(ownership);
	}
	
	public boolean hasChild() {
		return children != null && !children.isEmpty();
	}
	
    public JSONArray toJSONArray() {
		JSONArray ja = new JSONArray();
		Set<SourceEntityType> keys = children.keySet();
		for (SourceEntityType type : keys) {
			List<Ownership> list = children.get(type);
			if(list == null || list.isEmpty()) continue;
			
			JSONObject jso = new JSONObject();
			jso.put("text", type.getText());
			JSONArray childrenJSONArray = new JSONArray();
			for (Ownership ownership : list) {
				JSONObject jo = new JSONObject();
				jo.put("leaf", true);
				jo.put("text", ownership.getDisplayName());
				jo.put("id", ownership.getOwnerId());
				jo.put("ownershipType", FormatUtils.nameString(ownership.getOwnerType()));
				childrenJSONArray.put(jo);
			}
			jso.put("children", childrenJSONArray);
			
			ja.put(jso);
		}
		
		return ja;
    }
}
