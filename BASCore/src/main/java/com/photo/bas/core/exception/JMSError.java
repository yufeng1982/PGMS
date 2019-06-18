package com.photo.bas.core.exception;

/**
 * @author FengYu
 *
 */
public class JMSError extends ERPException {
	private static final long serialVersionUID = 7897150831209044210L;
	
	public static final String SendToJMSFailed = "JMS.Error.SendToJMSFailed";

	JMSError() {
		super();
	}

	public JMSError(String message, Throwable cause) {
		super(message, cause);
	}

	public JMSError(String message) {
		super(message);
	}
	
	
}
