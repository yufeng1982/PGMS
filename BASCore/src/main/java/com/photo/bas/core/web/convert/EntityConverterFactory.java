/**
 * 
 */
package com.photo.bas.core.web.convert;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import com.photo.bas.core.model.entity.AbsEntity;
import com.photo.bas.core.model.entity.AbsSourceEntity;
import com.photo.bas.core.service.entity.SourceEntityService;
import com.photo.bas.core.utils.Strings;

/**
 * @author FengYu
 *
 */
//@Component
public class EntityConverterFactory implements ConditionalGenericConverter {

	@Autowired private SourceEntityService sourceEntityService;
	
	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		Set<ConvertiblePair> set = new HashSet<ConvertiblePair>();
		set.add(new ConvertiblePair(String.class, AbsEntity.class));
		set.add(new ConvertiblePair(String.class, AbsSourceEntity.class));
		return set;
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		String id = (String) source;
		if(Strings.isEmpty(id)) return null;
		Class<?> objectType = targetType.getObjectType();
//		if( objectType.getAnnotation(Embeddable.class) != null) {
//			return objectType
//		}
		return sourceEntityService.getSource(objectType, id);
	}

	@Override
	public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
		return true;
	}
}
