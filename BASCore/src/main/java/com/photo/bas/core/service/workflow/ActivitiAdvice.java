package com.photo.bas.core.service.workflow;

import org.aspectj.lang.ProceedingJoinPoint;

public interface ActivitiAdvice {
	public Object startWorkFlowAdvice(ProceedingJoinPoint joinPoint) throws Throwable;
}
