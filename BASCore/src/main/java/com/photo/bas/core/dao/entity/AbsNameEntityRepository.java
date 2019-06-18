/**
 * 
 */
package com.photo.bas.core.dao.entity;

import org.springframework.data.repository.NoRepositoryBean;

import com.photo.bas.core.model.entity.AbsNameEntity;

/**
 * @author FengYu
 *
 */
@NoRepositoryBean
public interface AbsNameEntityRepository<T extends AbsNameEntity> extends AbsEntityRepository<T> {

}
