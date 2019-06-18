/**
 * 
 */
package com.photo.bas.core.web.convert;

import javax.measure.unit.Unit;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.photo.bas.core.utils.Strings;

/**
 * @author FengYu
 *
 */
@SuppressWarnings("rawtypes")
@Component("unitConverter")
public class UnitConverter implements Converter<String, Unit> {

    public Unit convert(String source) {
		Unit unit = null;
		if (!Strings.isEmpty(source)) {
			unit = Unit.valueOf(source);
		}
		return unit;
    }
}
