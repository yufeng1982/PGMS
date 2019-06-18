/**
 * 
 */
package com.photo.bas.core.web.controller.entity;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.entity.AbsContactEntity;
import com.photo.bas.core.service.entity.AbsContactService;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public abstract class AbsContactsController<T extends AbsContactEntity> extends AbsListController<T> {
	
	@RequestMapping("json")
	public ModelAndView json(@RequestParam(value="sourceOwnerId") String sourceOwnerId){
		List<T> list = getEntityService().findBySourceOwnerId(sourceOwnerId);
		return toJSONView(list);
	}
	
	@Override
	protected abstract AbsContactService<T, PageInfo<T>> getEntityService();
}
