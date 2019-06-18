package com.photo.bas.core.web.controller.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.entity.AbsSourceEntity;
import com.photo.bas.core.model.entity.IEntity;
import com.photo.bas.core.model.entity.Ownership;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.service.entity.SourceEntityService;
import com.photo.bas.core.utils.Strings;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value="/*/entity/form")
public class SourceEntityController extends AbsFormController<AbsSourceEntity> {

	@Autowired private SourceEntityService sourceEntityService;
	@Override
	protected SourceEntityService getEntityService() {
		return sourceEntityService;
	}

	@RequestMapping(value = "{type}/{id}/json")
	public ModelAndView json(@ModelAttribute("entity") AbsSourceEntity entity, ModelMap modelMap) {
		Ownership owner = getEntityService().getOwner(entity);
		return toJSONView(((IEntity) owner).toJSONObjectAll().toString());
	}
	@RequestMapping(value = "{type}/{id}/show")
	public ModelAndView showInventoryCountingJournal(@ModelAttribute("entity") AbsSourceEntity entity, ModelMap modelMap) {
		SourceEntityType type = entity.getOwnerType();
		return redirectTo(type.getLauncher(entity.getOwnerId()), modelMap);
	}
	
	
	@ModelAttribute("entity")
	public AbsSourceEntity populateSourceEntity(@PathVariable(value="type") SourceEntityType type, @PathVariable(value="id") String id) {
		AbsSourceEntity entity = null;
		if(!Strings.isEmpty(id)) {
			entity = getEntityService().getSourceEntityById(id, type);
		} else {
			entity = new AbsSourceEntity(type, id);
		}
		return entity;
	}

}
