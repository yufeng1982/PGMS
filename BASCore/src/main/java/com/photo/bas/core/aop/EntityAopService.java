/**
 * 
 */
package com.photo.bas.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.photo.bas.core.model.entity.AbsEntity;
import com.photo.bas.core.model.entity.AbsSourceEntity;
import com.photo.bas.core.model.entity.IEntity;
import com.photo.bas.core.model.entity.ISerial;
import com.photo.bas.core.model.entity.Ownership;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.service.entity.SourceEntityService;
import com.photo.bas.core.utils.DateTimeUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;

/**
 * @author FengYu
 *
 */
@Aspect
@Component
public class EntityAopService {
	protected Logger log = LoggerFactory.getLogger(getClass());

	@Autowired private SourceEntityService sourceEntityService;
	
	@Before("execution(* com..*.service..*Service.save*(..))")
	public void preprocessEntity(JoinPoint joinPoint) {
		if(joinPoint.getTarget().getClass().getName().startsWith("com.photo.")) {
			processCreationAndModification(joinPoint);
			processSerial(joinPoint);
		}
	}
	
//	@Around("execution(* com..*.service..*Service.save(..))")
//	public void preprocessGroupSourceEntityType(ProceedingJoinPoint joinPoint) throws Throwable {
//	}
	
	@SuppressWarnings("rawtypes")
	private void processSerial(JoinPoint joinPoint) {
		if(joinPoint.getArgs()[0] instanceof ISerial) {
			ISerial serialEntity = (ISerial) joinPoint.getArgs()[0];
			if(Strings.isEmpty(serialEntity.getSequence())) {
				AbsEntityService entityService = (AbsEntityService) joinPoint.getTarget();
				String serialNumber = entityService.getSerialNumber(serialEntity);
				serialEntity.setSequence(serialEntity.getCorporation().getCode() + serialNumber);
			}
		}
	}

	private void processCreationAndModification(JoinPoint joinPoint) {
		User currentUser = ThreadLocalUtils.getCurrentUser();
		if(joinPoint.getArgs()[0] instanceof AbsEntity) {
			AbsEntity entity = (AbsEntity) joinPoint.getArgs()[0];
			if(Strings.isEmpty(entity.getId()) || entity.getCreatedBy() == null || entity.getCreationDate() == null) {
				if(entity.getCreatedBy() == null) entity.setCreatedBy(currentUser);
				if(entity.getCreationDate() == null) entity.setCreationDate(DateTimeUtils.dateTimeNow());
				entity.setModifiedBy(currentUser);
				entity.setModificationDate(DateTimeUtils.dateTimeNow());
			} else {
				entity.setModifiedBy(currentUser);
				entity.setModificationDate(DateTimeUtils.dateTimeNow());
			}
		}
	}
	
	
	public Object preprocessSourceEntity(ProceedingJoinPoint joinPoint) throws Throwable {
		Object o = null;
		if(joinPoint.getTarget().getClass().getName().startsWith("com.photo")) {
			Object target = joinPoint.getArgs()[0];
			boolean isNew = false;
			if(target instanceof IEntity) {
				isNew = ((IEntity) target).isNewEntity();
			}
			
			o = joinPoint.proceed(joinPoint.getArgs());
			
			if(target instanceof Ownership) {
				Ownership owner = (Ownership) target;
				if(owner.getOwnerType() != null && owner.getOwnerType().isCreatable()) {
					if(isNew) {
						AbsSourceEntity se = (AbsSourceEntity) owner.getOwnerType().getSourceEntityClazz().newInstance();
						se.setOwnership(owner);

						sourceEntityService.save(se);
						owner.setSourceEntityId(se.getId());
					} else {
						
						// many owners' the display code and name never being changed after it being created at the first time,
						// so, considering the performance, we could ignore its source entity's updating process
						
						if(owner.getOwnerType().isChangable()) {
							AbsSourceEntity se = sourceEntityService.getSourceEntity(owner);
							
							boolean isCreated = true;
							// only purpose for those enties which had been cascaded created before. For instance contact and ship contact in customer/vendor, default line in document.
							if(se == null) {
								se = (AbsSourceEntity) owner.getOwnerType().getSourceEntityClazz().newInstance();
								isCreated = false;
							}
							se.setOwnership(owner);
							if(!isCreated) {

							}	

							sourceEntityService.save(se);
							
							if(!isCreated) {
								owner.setSourceEntityId(se.getId());
							}	
						}
					}
				}
			}
		}
		return o;
	}
	
	public Object initSequenceAndWarehouseForPlant(ProceedingJoinPoint joinPoint) throws Throwable {
		Object o = null;
		
		return o;
	}
}
