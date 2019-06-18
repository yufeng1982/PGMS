package com.photo.bas.core.model.entity;

import org.hibernate.envers.RevisionListener;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.ThreadLocalUtils;

public class RevisionEntityListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		RevBaseEntity absRevEntity = (RevBaseEntity) revisionEntity;
		User currentUser = ThreadLocalUtils.getCurrentUser();
		absRevEntity.setUser(currentUser);
		absRevEntity.setUserName(currentUser!=null?currentUser.getUsername():"");
	}
}
