package com.photo.bas.core.dao.common;

import org.springframework.data.repository.NoRepositoryBean;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.model.common.AbsZone;
/**
 * 
 * @author FengYu
 *
 */
@NoRepositoryBean
public interface AbsZoneRepository<T extends AbsZone> extends AbsCodeNameEntityRepository<T> {

}