/**
 * 
 */
package com.photo.pgm.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.web.controller.entity.AbsQueryPagedListController;
import com.photo.pgm.core.model.CooperationAccount;
import com.photo.pgm.core.service.CooperationAccountService;
import com.photo.pgm.core.service.CooperationService;
import com.photo.pgm.core.vo.CooperationAccountQueryInfo;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value="/pgm/project/cooperationAccount/list")
public class CooperationAccountListController extends AbsQueryPagedListController<CooperationAccount, CooperationAccountQueryInfo> {
	
	@Autowired private CooperationAccountService cooperationAccountService;
	@Autowired private CooperationService cooperationService;
	
	@RequestMapping("json")
	public ModelAndView json(@ModelAttribute("pageQueryInfo") CooperationAccountQueryInfo queryInfo, @RequestParam String cooperationId){
		queryInfo.setSort(new Sort(Direction.ASC, "creationDate"));
		queryInfo.setSf_EQ_cooperation(cooperationService.get(cooperationId));
		Page<CooperationAccount> list = getEntityService().findByCooperation(queryInfo);
		return toJSONView(list);
	}

	@Override
	protected CooperationAccountService getEntityService() {
		return cooperationAccountService;
	}


	@Override
	public CooperationAccountQueryInfo newPagedQueryInfo() {
		return new CooperationAccountQueryInfo();
	}
}
