/**
 * 
 */
package com.photo.pgm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.pgm.core.dao.PayAccountRepository;
import com.photo.pgm.core.model.Contract;
import com.photo.pgm.core.model.PayAccount;
import com.photo.pgm.core.vo.PayAccountQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class PayAccountService extends AbsEntityService<PayAccount, PageInfo<PayAccount>> {

	@Autowired private PayAccountRepository payAccountRepository;
	
	public PayAccount findByContract(Contract contract, Corporation corporation){
		return getRepository().findByContractAndCorporationAndActiveTrue(contract, corporation);
	}
	
	public Page<PayAccount> findByQueryInfo(PayAccountQueryInfo queryInfo) {
		PropertyFilter departmentF = new PropertyFilter("contract.department", queryInfo.getSf_IN_department(), MatchType.IN);
		PropertyFilter projectF = new PropertyFilter("contract.petrolStation", queryInfo.getSf_EQ_petrolStation(), MatchType.EQ);
		PropertyFilter contractF = new PropertyFilter("contract.code", queryInfo.getSf_LIKE_contractCode(), MatchType.LIKE);
		PropertyFilter assetsCategoryF = new PropertyFilter("contract.assetsCategory", queryInfo.getSf_EQ_assetsCategory(), MatchType.EQ);
		PropertyFilter cooperationF = new PropertyFilter("contract.cooperation", queryInfo.getSf_EQ_cooperation(), MatchType.EQ);
		PropertyFilter corporationF = new PropertyFilter("corporation", queryInfo.getSf_EQ_corporation(), MatchType.EQ);
		return getRepository().findAll(bySearchFilter(departmentF, projectF, contractF, assetsCategoryF, cooperationF, corporationF, PropertyFilter.activeFilter()), queryInfo);
	}
	
	@Override
	protected PayAccountRepository getRepository() {
		return payAccountRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
