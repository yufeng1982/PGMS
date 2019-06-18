package com.photo.bas.core.web.convert;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.photo.bas.core.utils.Strings;

/**
 * @author FengYu
 *
 */
@Component("sortConverter")
public class SortConverter implements Converter<String, Sort> {

	private final static String DIRECTION = "direction";
	private final static String PROPERTY = "property";
	
    public Sort convert(String source) {
		JSONArray jsonArray = new JSONArray();
		if (!Strings.isEmpty(source)) {
			try {
				jsonArray = new JSONArray(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} 
		Sort sorts = null;
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jo = (JSONObject) jsonArray.get(i);
			if(sorts == null) {
				sorts = new Sort(new Order(Direction.fromString(jo.getString(DIRECTION)), jo.getString(PROPERTY)));
				continue;
			}
			sorts.and(new Sort(new Order(Direction.fromString(jo.getString(DIRECTION)), jo.getString(PROPERTY))));
		}
		
		return sorts;
    }

}
