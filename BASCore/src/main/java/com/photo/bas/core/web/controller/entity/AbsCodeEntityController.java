/**
 * 
 */
package com.photo.bas.core.web.controller.entity;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.WebRequest;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.service.entity.AbsCodeEntityService;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public abstract class AbsCodeEntityController<T extends AbsCodeNameEntity> extends AbsFormController<T> {

	@Override
	protected abstract AbsCodeEntityService<T, PageInfo<T>> getEntityService();
	
	protected abstract T newInstance(WebRequest request);	
	
	@ModelAttribute("entity")
	public T populateEntity(@PathVariable(value="id") String id, ModelMap modelMap, WebRequest request) {
		T t = null;
		if(isNew(id)) {
			t = newInstance(request);
		} else {
			t = getEntityService().get(id);
		}
		return t;
	}
}
