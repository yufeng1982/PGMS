/**
 * 
 */
package com.photo.bas.core.web.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.photo.bas.core.service.entity.AbsService;
import com.photo.bas.core.web.controller.entity.AbsController;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/core")
public class ClosePageController extends AbsController<Object>{

	@SuppressWarnings("rawtypes")
	@Override
	protected AbsService getEntityService() {
		return null;
	}
	
	@RequestMapping("/close")
	public String closePage() {
		return "close";
	}
}
