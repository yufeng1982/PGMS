/**
 * 
 */
package com.photo.bas.core.dao.entity;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.photo.bas.core.model.entity.IEntity;

/**
 * @author FengYu
 *
 */
@NoRepositoryBean
public interface AbsRepository <T extends IEntity> extends PagingAndSortingRepository<T, String>, JpaSpecificationExecutor<T> {

}
