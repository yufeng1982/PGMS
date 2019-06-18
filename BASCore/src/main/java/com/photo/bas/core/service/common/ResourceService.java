package com.photo.bas.core.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.photo.bas.core.dao.common.ResourceRepository;
import com.photo.bas.core.model.common.Resource;
import com.photo.bas.core.model.common.ResourceType;
import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.PropertyFilter.MatchType;

@Component
public class ResourceService extends AbsCodeNameEntityService<Resource, PageInfo<Resource>> {

	@Autowired private ResourceRepository resourceRepository;
	
	@Override
	protected ResourceRepository getRepository() {
		return resourceRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}
	
	public Resource save(Resource resource) {
		
		if(resource.isPrimaryResource() && resource.getType().equals(ResourceType.IMAGE)){
			updateRelatedResource(resource);
		}
		
		return getRepository().save(resource);
	}

	private void updateRelatedResource(Resource resource) {

		PropertyFilter ownerIdFilter = new PropertyFilter("sourceId" , resource.getSourceId() , PropertyFilter.MatchType.EQ);
		PropertyFilter ownerTypeFilter = new PropertyFilter("sourceType" , resource.getSourceType() , PropertyFilter.MatchType.EQ);
		PropertyFilter primaryFilter = new PropertyFilter("primaryResource" , true , PropertyFilter.MatchType.EQ);
		PropertyFilter typeFilter = new PropertyFilter("type" , ResourceType.IMAGE , PropertyFilter.MatchType.EQ);
		PropertyFilter active = new PropertyFilter("active", true, MatchType.EQ);
		Iterable<Resource> resourceList = search(ownerIdFilter, ownerTypeFilter , typeFilter , primaryFilter, active);
		if(resourceList != null){
			for(Resource resource2 : resourceList ){
				if(Strings.isEmpty(resource.getId())){
					resource2.setPrimaryResource(false);
					getRepository().save(resource2);
				}else if(!Strings.isEmpty(resource.getId()) && !resource.getId().equals(resource2.getId())){
					resource2.setPrimaryResource(false);
					getRepository().save(resource2);
				}
				
			}
		}
	}

}
