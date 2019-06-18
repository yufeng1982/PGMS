package com.photo.bas.core.web.controller.common;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.Resource;
import com.photo.bas.core.model.common.ResourceType;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.service.common.ResourceService;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.web.controller.entity.AbsPagedListController;

@Controller
@RequestMapping(value="/*/resource/list")
public class ResourcesController extends AbsPagedListController<Resource>{

	@Autowired private ResourceService resourceService;
	
	@Override
	protected ResourceService getEntityService() {
		return resourceService;
	}
	
	@RequestMapping("json")
	public ModelAndView json(ModelMap modelMap,
			 @RequestParam(value = "ownerId", required = false) String ownerId , 
			 @RequestParam(value = "ownerType" ,required = false ) SourceEntityType ownerType ,
			 @RequestParam(value = "type" ,required = false ) ResourceType type 
	) {
		
		JSONArray ja = new JSONArray();
		
		if(isNew(ownerId)){
			return toJSONView(ja);
		}
		
		PropertyFilter ownerIdfilter = new PropertyFilter("sourceId", ownerId, MatchType.EQ);
		PropertyFilter ownerTypefilter = new PropertyFilter("sourceType", ownerType, MatchType.EQ);
		PropertyFilter active = new PropertyFilter("active", true, MatchType.EQ);
		PropertyFilter typeFilter = new PropertyFilter("type", type, MatchType.EQ);
		
		Iterable<Resource> list = getEntityService().search(ownerIdfilter, ownerTypefilter, active, typeFilter);
		
		for (Resource resource : list) {
			JSONObject obj = resource.toJSONObject();
			ja.put(obj);
		}
		
		return toJSONView(ja);
	}

}
