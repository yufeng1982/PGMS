package com.photo.bas.core.service.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.photo.bas.core.dao.entity.CrmSourceEntityRepository;
import com.photo.bas.core.dao.entity.DocumentLineSourceEntityRepository;
import com.photo.bas.core.dao.entity.DocumentSourceEntityRepository;
import com.photo.bas.core.dao.entity.ProductSourceEntityRepository;
import com.photo.bas.core.dao.entity.SourceEntityRepository;
import com.photo.bas.core.model.entity.AbsSourceEntity;
import com.photo.bas.core.model.entity.CrmSourceEntity;
import com.photo.bas.core.model.entity.DocumentLineSourceEntity;
import com.photo.bas.core.model.entity.DocumentSourceEntity;
import com.photo.bas.core.model.entity.IEntity;
import com.photo.bas.core.model.entity.Ownership;
import com.photo.bas.core.model.entity.ProductSourceEntity;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.ProxyTargetConverterUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.vo.entity.SourceEntityQueryInfo;

/**
 * @author FengYu
 *
 */
@Component
public class SourceEntityService extends AbsService<AbsSourceEntity, SourceEntityQueryInfo> {
	protected Logger logger = LoggerFactory.getLogger(SourceEntityService.class);
	
	private EntityManager entityManager;
	
	@Autowired private ProductSourceEntityRepository productSourceEntityRepository;
	@Autowired private DocumentLineSourceEntityRepository documentLineSourceEntityRepository;
	@Autowired private DocumentSourceEntityRepository documentSourceEntityRepository;
	@Autowired private CrmSourceEntityRepository crmSourceEntityRepository;
	
	@Autowired private SourceEntityRepository sourceEntityRepository;

	@Override
	protected SourceEntityRepository getRepository() {
		return sourceEntityRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public AbsSourceEntity getByCode(String code, SourceEntityType ownerType, Corporation corporation) {
		if(Strings.isEmpty(code) || ownerType == null || Strings.isEmpty(code)) return null;
		
		return getRepository().findByCodeAndOwnerTypeAndCorporation(code, ownerType, corporation);
	}

	public Page<AbsSourceEntity> find(SourceEntityQueryInfo p, SourceEntityType type) {
		return sourceEntityRepository.findAll(
				bySearchFilter(
						new PropertyFilter("code", p.getSf_LIKE_query(), MatchType.LIKE),
						new PropertyFilter("ownerType", type, MatchType.EQ),
						new PropertyFilter("status", p.getStatus(), MatchType.EQ),
						new PropertyFilter("status", p.getStatusIncludedList(), MatchType.IN),
						new PropertyFilter("status", p.getStatusExcludedList(), MatchType.NIN)
				), 
				p);
	}
	
	public Page<AbsSourceEntity> findByMultiSourceEntityType(SourceEntityQueryInfo p, List<SourceEntityType> types) {
		return sourceEntityRepository.findAll(
				bySearchFilter(
						new PropertyFilter("code", p.getSf_LIKE_query(), MatchType.LIKE),
						new PropertyFilter("name", p.getName(), MatchType.LIKE), // for Manufacturing Operation, the name save Operation's id
						new PropertyFilter("ownerType", types, MatchType.IN),
						new PropertyFilter("status", p.getStatus(), MatchType.EQ),
						new PropertyFilter("status", p.getStatusIncludedList(), MatchType.IN),
						new PropertyFilter("status", p.getStatusExcludedList(), MatchType.NIN)
				), 
				p);
	}
	
	public AbsSourceEntity getSourceEntity(AbsSourceEntity sourceEntity) {
		if(sourceEntity == null) return null;
		SourceEntityType ownerType = sourceEntity.getOwnerType();
		if(ownerType == null) return null;
		if(Strings.isEmpty(sourceEntity.getId())) {
			return getSourceEntity(ownerType, sourceEntity.getOwnerId());
		} else {
			return getSourceEntityById(sourceEntity.getId(), ownerType);
		}
	}
	
	public AbsSourceEntity getSourceEntity(Ownership owner) {
		Assert.notNull(owner);
		return getSourceEntity(owner.getOwnerType(), owner.getOwnerId());
	}
	
	public AbsSourceEntity getSourceEntity(SourceEntityType ownerType, String ownerId) {
		Assert.notNull(ownerId);
		Assert.notNull(ownerType);
		return getRepository().findByOwnerTypeAndOwnerId(ownerType, ownerId);
	}
	
	public JSONArray buildSourceEntityJSONArray(JSONArray ja, List<AbsSourceEntity> sourceEntityList) {
		for(int i = 0; i < sourceEntityList.size(); i ++){
			AbsSourceEntity sourceEntity = sourceEntityList.get(i);
			buildSourceEntityJSONArray(ja, sourceEntity.getOwnerId(), sourceEntity.getOwnerType());
		}
		return ja;
	}

	public JSONArray buildSourceEntityJSONArray(JSONArray ja, String ownerId, SourceEntityType ownerType) {
		if(!Strings.isEmpty(ownerId)){
			IEntity sourceEntity = (IEntity) getOwner(ownerId, ownerType);
			ja.put(sourceEntity.toJSONObject());
		}
		return ja;
	}
	
	public void updateOwner(Ownership owner) {
		entityManager.merge(owner);
	}
	public Ownership getOwner(String ownerId, SourceEntityType ownerType) {
		return (Ownership) ProxyTargetConverterUtils.getProxyImplementationObject(entityManager.find(ownerType.getClazz(), ownerId));
	}
	public Ownership getOwner(AbsSourceEntity sourceEntity) {
		return getOwner(sourceEntity.getOwnerId(), sourceEntity.getOwnerType());
	}
	
	public AbsSourceEntity getSourceEntityById(String id, SourceEntityType type) {
		Assert.notNull(id);
		Assert.notNull(type);
		return (AbsSourceEntity) ProxyTargetConverterUtils.getProxyImplementationObject(entityManager.find(type.getSourceEntityClazz(), id));
	}
	
	public Object getSource(Class<?> clazz, String id) {
		return entityManager.find(clazz, id);
	}

	public CrmSourceEntity getCrmSourceEntity(String id) {
		return crmSourceEntityRepository.findOne(id);
	}

	public DocumentLineSourceEntity getDocumentLineSourceEntity(String id) {
		return documentLineSourceEntityRepository.findOne(id);
	}

	public DocumentSourceEntity getDocumentSourceEntity(String id) {
		return documentSourceEntityRepository.findOne(id);
	}

	public ProductSourceEntity getProductSourceEntity(String id) {
		return productSourceEntityRepository.findOne(id);
	}
}
