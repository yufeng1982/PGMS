package com.photo.bas.core.exception;

/**
 * @author FengYu
 *
 */
public class EntityOptimisticLockingFailureException extends ERPException {
	private static final long serialVersionUID = 2279611267119016675L;
	
	public static final String OPTIMISTIC_FAILURE_ERROR_KEY = "Core.EM.Statle.Object.State";

	public EntityOptimisticLockingFailureException() {
		this("Entity Optimistic Locking Failure !");
	}

	protected EntityOptimisticLockingFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityOptimisticLockingFailureException(String message) {
		super(message);
	}

}
