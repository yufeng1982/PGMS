/**
 * 
 */
package com.photo.pgm.core.service;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.AssetRepository;
import com.photo.pgm.core.model.Asset;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class AssetService extends AbsCodeNameEntityService<Asset, PageInfo<Asset>> {

	@Autowired private AssetRepository assetRepository;
	
	public Integer findMaxSeq(String corporation){
		Integer seq = assetRepository.findMaxSeq(corporation);
		return seq == null ? 0 : seq;
	}
	
	public String getSequeneNumber(String seqName, String projectId) {
		List<BigInteger> obj= getRepository().getSerialNumber(seqName + "_" +  projectId);
		BigInteger lSerialNumber = obj.get(0);
		StringBuffer numOfZeros = new StringBuffer();
		for (int i = 0; i < 4; i ++) {
			numOfZeros.append("0");
		}
		String sq =  new DecimalFormat(numOfZeros.toString()).format(lSerialNumber);
		return sq;
	}
	
	@Override
	protected AssetRepository getRepository() {
		return assetRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
