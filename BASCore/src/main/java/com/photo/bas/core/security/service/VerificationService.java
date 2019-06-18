/**
 * 
 */
package com.photo.bas.core.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.dao.security.VerificationRepository;
import com.photo.bas.core.model.security.Verification;
import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class VerificationService extends AbsEntityService<Verification, PageInfo<Verification>> {

	@Autowired private VerificationRepository verificationRepository;
	
	@Override
	protected VerificationRepository getRepository() {
		return verificationRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
