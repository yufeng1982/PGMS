/**
 * Copyright (c) 2005-20010 springside.org.cn
 * We change/add some extra methods in order to fulfill our project.
 */
package com.photo.bas.core.service.entity;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.photo.bas.core.dao.entity.DefaultEntityDAO;

/**
 * @author FengYu
 *
 */
public class DefaultEntityManagerImpl<T, PK extends Serializable> extends AbsEntityManagerImpl<T, PK> {

	protected DefaultEntityDAO<T, PK> entityDAO;

	@Override
	protected DefaultEntityDAO<T, PK> getEntityDAO() {
		return entityDAO;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityDAO = new DefaultEntityDAO<T, PK>(entityManager, entityClass);
	}
	
}