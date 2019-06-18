/**
 * 
 */
package com.photo.bas.core.service.entity;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.data.domain.Page;

import com.photo.bas.core.dao.entity.AbsEntityRepository;
import com.photo.bas.core.model.entity.AbsEntity;
import com.photo.bas.core.model.entity.ISerial;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public abstract class AbsEntityService<T extends AbsEntity, P extends PageInfo<T>> extends AbsService<T, P> {

	protected abstract AbsEntityRepository<T> getRepository();
	
	public Page<T> findLists(final P queryInfo) {
		return getRepository().findAll(byPage(queryInfo), queryInfo);
	}
	
	public void delete(String id) {
		delete(get(id));
	}
	
	public Iterable<T> getAll() {
		return getRepository().findAllByActiveTrue();
	}

	public T save(T t){
		return super.save(t);
	}
	
	public Iterable<T> getAllByCorporation(Corporation corporation) {
		return getRepository().findAllByCorporationAndActiveTrue(corporation);
	}
	
	public String getSerialNumber(ISerial iSerial) {
		List<BigInteger> obj= getRepository().getSerialNumber( iSerial.getSequenceName() );
		BigInteger lSerialNumber = obj.get(0);
		StringBuffer numOfZeros = new StringBuffer();
		for (int i = 0; i < iSerial.getSequenceLength(); i ++) {
			numOfZeros.append("0");
		}
		
		String sq = iSerial.getSequenceLength() == 0 ? lSerialNumber.toString() : new DecimalFormat(numOfZeros.toString()).format(lSerialNumber);

		String seq = new StringBuffer().append(iSerial.getPrefix()).append(sq).toString();
		
		return seq;
	}
//	public String getSerialNumber(ISerial iSerial);
//	public int createSequence(String seqName);
//	public int resetSequenceStart(String seqName, Long start);

//	public T getBySourceEntityId(String sourceEntityId);
	
}
