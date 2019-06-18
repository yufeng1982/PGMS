package com.photo.bas.core.web.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import com.photo.bas.core.utils.Strings;

/**
 * @author FengYu
 *
 */
@Component
public final class EnumConverterFactory implements ConverterFactory<String, Enum<?>> {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter(targetType);
    }

	private final class StringToEnumConverter<T extends Enum<T>> implements Converter<String, T> {

        private Class<T> enumType;

        public StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        public T convert(String source) {
        	if(Strings.isEmpty(source.trim())) return null;
            T valueOf = (T) Enum.valueOf(this.enumType, source.trim());
			return valueOf;
        }
    }
}