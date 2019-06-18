package com.photo.pgm.core.akka;

import akka.actor.UntypedActor;

import com.photo.bas.core.utils.EmailEntity;
import com.photo.bas.core.utils.EmailUtils;
import com.photo.bas.core.utils.SpringWebApplicationContextUtils;

public class EmailActor extends UntypedActor {

	private EmailUtils emailUtils;
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		EmailEntity emailEntity = (EmailEntity) arg0;
		emailUtils = (EmailUtils) SpringWebApplicationContextUtils.getApplicationContextBean("emailUtils");
		emailUtils.sendEmail4ApproveUsers(emailEntity);
	}
	
	public EmailActor() {
		super();
	}

}
