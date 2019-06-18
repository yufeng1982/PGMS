package com.photo.bas.core.web.controller.entity;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.entity.IEntity;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.Strings;

@SuppressWarnings("unchecked")
public abstract class AbsQueryPagedListController<T extends IEntity, P extends PageInfo<T>> extends AbsPagedListController<T> {

	public final static String FROM_URI = "_FROM_URI__";
	
	@RequestMapping("search")
	public ModelAndView search(@ModelAttribute("pageQueryInfo") P page) {
		Page<T> pageResult = getEntityService().search(page, page.getSf_LIKE_query(), getSearchByPropertyNames());
		return toJSONView(pageResult);
	}
	
	@ModelAttribute("pageQueryInfo")
	public P populateData() {
		return newPagedQueryInfo();
	}

	@ModelAttribute(AbsFormController.FROM_URI)
    public String getFromURI(@RequestParam(value=FROM_URI, required=false) String fromURI, HttpServletRequest request) {
    	return Strings.isEmpty(fromURI) ? request.getRequestURI() : fromURI;
    }
	
	public abstract P newPagedQueryInfo();
	
}
