package com.photo.bas.core.service.workflow;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photo.bas.core.model.workflow.WorkFlowEntity;

@Service("activitiAdvice")
public class ActivitiAdviceImpl implements ActivitiAdvice {
	
	@Autowired private ActivitiManager activitiManager;
	
	@Override
	public Object startWorkFlowAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		Object o = null;
		if(joinPoint.getTarget().getClass().getName().startsWith("com.novasteel")) {
			Object target = joinPoint.getArgs()[0];
			
			boolean isNew = false;
			
			if(target instanceof WorkFlowEntity){
				WorkFlowEntity workFlowEntity = (WorkFlowEntity) target;
				isNew = workFlowEntity.isNewEntity()
							&& !workFlowEntity.isArchive();//hack for import closed/cancel doc
			}

			o = joinPoint.proceed(joinPoint.getArgs());
			
			if(isNew) activitiManager.startInstance((WorkFlowEntity) target);
		}
		return o;
	}

}
