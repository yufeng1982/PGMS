/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.OilCanLine;
import com.photo.pgm.core.service.OilCanLineService;
import com.photo.pgm.core.vo.OilCanLineQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/oilcanLine/list")
public class OilCanLineListController extends AbsQueryPagedListController<OilCanLine, OilCanLineQueryInfo> {

	@Autowired private OilCanLineService oilCanLineService;
	
	@RequestMapping("json")
	public ModelAndView findByPageInfo(@ModelAttribute("pageQueryInfo") OilCanLineQueryInfo page) {
		Sort sort = new Sort(Direction.ASC, "seq");
		page.setSort(sort);
		Page<OilCanLine> pageResult = null;
		if (page.getSf_EQ_project() == null) {
			pageResult = new PageImpl<OilCanLine>(new ArrayList<OilCanLine>());
		} else {
			pageResult = getEntityService().search(page);
		}
		return toJSONView(pageResult);
	}
	
	@Override
	public OilCanLineQueryInfo newPagedQueryInfo() {
		return new OilCanLineQueryInfo();
	}

	@Override
	protected OilCanLineService getEntityService() {
		return oilCanLineService;
	}

}
