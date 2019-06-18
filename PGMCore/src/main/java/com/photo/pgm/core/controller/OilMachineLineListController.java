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
import com.photo.pgm.core.model.OilMachineLine;
import com.photo.pgm.core.service.OilMachineLineService;
import com.photo.pgm.core.vo.OilMachineLineQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/oilMachineLine/list")
public class OilMachineLineListController extends AbsQueryPagedListController<OilMachineLine, OilMachineLineQueryInfo> {

	@Autowired private OilMachineLineService oilCanAndMachineLineService;
	
	@RequestMapping("json")
	public ModelAndView findByPageInfo(@ModelAttribute("pageQueryInfo") OilMachineLineQueryInfo page) {
		Sort sort = new Sort(Direction.ASC, "seq");
		page.setSort(sort);
		Page<OilMachineLine> pageResult = null;
		if (page.getSf_EQ_project() == null) {
			pageResult = new PageImpl<OilMachineLine>(new ArrayList<OilMachineLine>());
		} else {
			pageResult = getEntityService().search(page);
		}
		return toJSONView(pageResult);
	}
	
	@Override
	public OilMachineLineQueryInfo newPagedQueryInfo() {
		return new OilMachineLineQueryInfo();
	}

	@Override
	protected OilMachineLineService getEntityService() {
		return oilCanAndMachineLineService;
	}

}
