package com.photo.bas.core.web.convert;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.photo.bas.core.utils.Strings;

/**
 * @author FengYu
 *
 */
@Component("jsonArrayConverter")
public class JSONArrayConverter implements Converter<String, JSONArray> {

    public JSONArray convert(String source) {
		JSONArray jsonArray = null;
		if (!Strings.isEmpty(source)) {
			try {
				if(source.startsWith("[")){
					jsonArray = new JSONArray(source);
				}else if(source.startsWith("{")){
					jsonArray = new JSONArray();
					jsonArray.put(new JSONObject(source));
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			jsonArray = new JSONArray();
		}
		return jsonArray;
    }

}
