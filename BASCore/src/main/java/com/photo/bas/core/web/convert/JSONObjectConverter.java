/**
 * 
 */
package com.photo.bas.core.web.convert;

import java.text.ParseException;

import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.photo.bas.core.utils.Strings;

/**
 * @author FengYu
 *
 */
@Component("jsonObjectConverter")
public class JSONObjectConverter implements Converter<String, JSONObject> {

    public JSONObject convert(String source) {
		JSONObject jsonObject = null;
		if (!Strings.isEmpty(source)) {
			try {
				jsonObject = new JSONObject(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			jsonObject = new JSONObject();
		}
		return jsonObject;
    }

}