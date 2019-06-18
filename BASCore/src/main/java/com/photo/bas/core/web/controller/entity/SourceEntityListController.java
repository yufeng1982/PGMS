package com.photo.bas.core.web.controller.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.entity.AbsSourceEntity;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.service.entity.SourceEntityService;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.vo.entity.SourceEntityQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value="/*/entity/list")
public class SourceEntityListController extends AbsQueryPagedListController<AbsSourceEntity, SourceEntityQueryInfo> {

	@Autowired private SourceEntityService sourceEntityService;
	@Override
	protected SourceEntityService getEntityService() {
		return sourceEntityService;
	}
	
	@RequestMapping(value = "{type}/json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") SourceEntityQueryInfo page, 
			@PathVariable(value="type") String type, ModelMap modelMap) {
		
		String[] types = type.split(",");
		List<SourceEntityType> list = new ArrayList<SourceEntityType>();
		for(int i = 0; i < types.length; i++) {
			String strSeType = types[i];
			if(Strings.isEmpty(strSeType)) continue;
			SourceEntityType seType = SourceEntityType.valueOf(strSeType);
			list.add(seType);
		}
		Page<AbsSourceEntity> sePage = getEntityService().findByMultiSourceEntityType(page, list);
		return toJSONView(sePage);
	}

	@Override
	public SourceEntityQueryInfo newPagedQueryInfo() {
		return new SourceEntityQueryInfo();
	}
}
