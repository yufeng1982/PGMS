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
import com.photo.pgm.core.dao.ApproveOpinionsRepository;
import com.photo.pgm.core.model.ApproveOpinions;
import com.photo.pgm.core.vo.ApproveOpinionsQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class ApproveOpinionsService extends AbsEntityService<ApproveOpinions, PageInfo<ApproveOpinions>> {

	@Autowired private ApproveOpinionsRepository approveOpinionsRepository;
	
	public Page<ApproveOpinions> findByContract(ApproveOpinionsQueryInfo queryInfo) {
		if (queryInfo.getSf_EQ_contract() == null) return new PageImpl<ApproveOpinions>(new ArrayList<ApproveOpinions>());
		return getRepository().findAll(byPage(queryInfo), queryInfo);
	}
	
	@Override
	protected ApproveOpinionsRepository getRepository() {
		return approveOpinionsRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
