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
import com.photo.pgm.core.model.OtherEquipmentLine;
import com.photo.pgm.core.service.OtherEquipmentLineService;
import com.photo.pgm.core.vo.OtherEquipmentLineQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/otherEquipmentLine/list")
public class OtherEquipmentLineListController extends AbsQueryPagedListController<OtherEquipmentLine, OtherEquipmentLineQueryInfo> {

	@Autowired private OtherEquipmentLineService otherEquipmentLineService;
	
	@RequestMapping("json")
	public ModelAndView findByPageInfo(@ModelAttribute("pageQueryInfo") OtherEquipmentLineQueryInfo page) {
		Sort sort = new Sort(Direction.ASC, "seq");
		page.setSort(sort);
		Page<OtherEquipmentLine> pageResult = null;
		if (page.getSf_EQ_project() == null) {
			pageResult = new PageImpl<OtherEquipmentLine>(new ArrayList<OtherEquipmentLine>());
		} else {
			pageResult = getEntityService().search(page);
		}
		return toJSONView(pageResult);
	}
	
	@Override
	public OtherEquipmentLineQueryInfo newPagedQueryInfo() {
		return new OtherEquipmentLineQueryInfo();
	}

	@Override
	protected OtherEquipmentLineService getEntityService() {
		return otherEquipmentLineService;
	}

}
