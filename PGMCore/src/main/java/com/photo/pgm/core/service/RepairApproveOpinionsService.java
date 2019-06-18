/**
 * 
 */
package com.photo.pgm.core.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.RepairApproveOpinionsRepository;
import com.photo.pgm.core.model.RepairApproveOpinions;
import com.photo.pgm.core.vo.RepairApproveOpinionsQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class RepairApproveOpinionsService extends AbsEntityService<RepairApproveOpinions, PageInfo<RepairApproveOpinions>> {

	@Autowired private RepairApproveOpinionsRepository repairApproveOpinionsRepository;
	
	public Page<RepairApproveOpinions> findByRepairOrder(RepairApproveOpinionsQueryInfo queryInfo) {
		if (queryInfo.getSf_EQ_repairOrder() == null) return new PageImpl<RepairApproveOpinions>(new ArrayList<RepairApproveOpinions>());
		return getRepository().findAll(byPage(queryInfo), queryInfo);
	}
	
	public Page<RepairApproveOpinions> findByRepairSettleAccount(RepairApproveOpinionsQueryInfo queryInfo) {
		if (queryInfo.getSf_EQ_repairSettleAccount() == null) return new PageImpl<RepairApproveOpinions>(new ArrayList<RepairApproveOpinions>());
		return getRepository().findAll(byPage(queryInfo), queryInfo);
	}
	
	@Override
	protected RepairApproveOpinionsRepository getRepository() {
		return repairApproveOpinionsRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
