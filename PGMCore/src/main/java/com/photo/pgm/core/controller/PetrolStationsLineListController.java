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
import com.photo.pgm.core.model.PetrolStationsLine;
import com.photo.pgm.core.service.PetrolStationsLineService;
import com.photo.pgm.core.vo.PetrolStationsLineQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/petrolStationsLine/list")
public class PetrolStationsLineListController extends AbsQueryPagedListController<PetrolStationsLine, PetrolStationsLineQueryInfo> {

	@Autowired private PetrolStationsLineService petrolStationsLineService;
	
	@RequestMapping("json")
	public ModelAndView findByPageInfo(@ModelAttribute("pageQueryInfo") PetrolStationsLineQueryInfo page) {
		Sort sort = new Sort(Direction.ASC, "seq");
		page.setSort(sort);
		Page<PetrolStationsLine> pageResult = null;
		if (page.getSf_EQ_project() == null) {
			pageResult = new PageImpl<PetrolStationsLine>(new ArrayList<PetrolStationsLine>());
		} else {
			pageResult = getEntityService().search(page);
		}
		return toJSONView(pageResult);
	}
	
	@Override
	public PetrolStationsLineQueryInfo newPagedQueryInfo() {
		return new PetrolStationsLineQueryInfo();
	}

	@Override
	protected PetrolStationsLineService getEntityService() {
		return petrolStationsLineService;
	}

}
