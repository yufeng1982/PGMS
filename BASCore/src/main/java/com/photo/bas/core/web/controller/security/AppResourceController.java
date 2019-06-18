package com.photo.bas.core.web.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.photo.bas.core.model.security.AppResource;
import com.photo.bas.core.security.service.AppResourceService;
import com.photo.bas.core.web.controller.entity.AbsController;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/*/ui/resource*")
public class AppResourceController extends AbsController<AppResource> {

	@Autowired private AppResourceService appResourceService;

	@Override
	protected AppResourceService getEntityService() {
		return appResourceService;
	}
}
