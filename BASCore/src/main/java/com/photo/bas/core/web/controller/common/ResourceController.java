package com.photo.bas.core.web.controller.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.Resource;
import com.photo.bas.core.model.common.ResourceType;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.service.common.ResourceService;
import com.photo.bas.core.utils.Collections3;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.web.controller.entity.AbsPagedListController;
import com.photo.bas.core.web.view.ExtUploadJSONView;

@Controller
@SessionAttributes({"companies", "languages"})
@RequestMapping(value="/*/resource")
public class ResourceController extends AbsPagedListController<Resource>{

	private static final String RESOURCE_FORM = "resource/fileUpload";

	@Autowired private ResourceService resourceService;
	
	@Override
	protected ResourceService getEntityService() {
		return resourceService;
	}
	
	@RequestMapping("show")
	public String show(ModelMap modelMap) {
		
		populateAttributes(modelMap);
		
		return RESOURCE_FORM;
	}
	@RequestMapping(value="delete/{id}")
	public ModelAndView delete(@PathVariable("id") String resourceId) {
		JSONObject jo = new JSONObject();
		
		if(!Strings.isEmpty(resourceId)){
			getEntityService().delete(resourceId);
		}
		
		return toJSONView(jo);
	}
	
	@RequestMapping(value="download/{id}")
	public ModelAndView download(@PathVariable("id") String resourceId) {
		Resource resource = getEntityService().get(resourceId);
		return toStreamView(resource);
	}
	
	@RequestMapping("primaryPic")
	public ModelAndView primaryPic(@RequestParam(value = "ownerId", required = false) String sourceId , 
			 @RequestParam(value = "ownerType" ,required = false ) SourceEntityType ownerType){
		JSONObject jo = new JSONObject();
		
		if(Strings.isEmpty(sourceId) && NEW_ENTITY_ID.equals(sourceId)){
			return toJSONView(jo);
		}
		
		PropertyFilter sourceIdFilter = new PropertyFilter("sourceId", sourceId, MatchType.EQ);
		PropertyFilter sourceTypeFilter = new PropertyFilter("sourceType", ownerType, MatchType.EQ);
		PropertyFilter primaryFilter = new PropertyFilter("primaryResource" , true , PropertyFilter.MatchType.EQ);
		PropertyFilter typeFilter = new PropertyFilter("type" , ResourceType.IMAGE , PropertyFilter.MatchType.EQ);
		PropertyFilter active = new PropertyFilter("active", true, MatchType.EQ);
		Iterable<Resource> resourceList = getEntityService().search(sourceIdFilter , sourceTypeFilter, typeFilter , primaryFilter, active);
		if(resourceList != null){
			Resource resource = Collections3.getFirst(resourceList);
			String url = Resource.uploadDir + "/" + resource.getInternalName();
			jo.put("url", url);
		}
		return toJSONView(jo);
	}
	
	@RequestMapping("ok")
	public ModelAndView ok(ModelMap modelMap ,
			@RequestParam(value="file" , required = false) MultipartFile file ,
			@RequestParam("id") String id ,
	        @RequestParam("name") String name , 
	        @RequestParam("description") String description , 
	        @RequestParam("comment") String comment ,
	        @RequestParam(value = "type", required = false) ResourceType fileType,
	        @RequestParam(value = "contentType", required = false) ResourceType contentType,
	        @RequestParam("ownerId") String ownerId ,
	        @RequestParam("sourceType") SourceEntityType sourceType ,
	        @RequestParam(value="primary" , required = false) String primary,
	        HttpServletRequest request 
		  ) {
		Resource resource = null;
		if(Strings.isEmpty(id)){
			resource = new Resource(file.getOriginalFilename() , ownerId, sourceType);
		}else{
			resource = getEntityService().get(id);
		}
		resource.setName(name);
		resource.setDescription(description);
		resource.setComment(comment);
		resource.setType(fileType);
		resource.setContentType(contentType);
		if(!Strings.isEmpty(primary) && "on".equals(primary)){
			resource.setPrimaryResource(true);
		}else{
			resource.setPrimaryResource(false);
		}
		if(Strings.isEmpty(id)){
			if(uploadFile(file , resource)){
				save(resource , null);
			}
		}else{
			save(resource , null);
		}
		
		populateAttributes(modelMap);
		JSONObject jso = new JSONObject();
		jso.put("success", true);
//		jso.put("file", file.getName());
		return new ModelAndView(new ExtUploadJSONView(jso.toString()));
	}

	private boolean uploadFile(MultipartFile file , Resource resource) {
		try {
			String uploadFolder = ResourceUtils.getAppSetting("system.uploadFolder");
			File folder = new File(uploadFolder);
			boolean mkdir = true;
			if(!folder.exists()){
				mkdir = folder.mkdir();
			}
			if(mkdir){
				String path = uploadFolder + resource.getInternalName();
				File f = new File(path);
				boolean newFileCreated = f.createNewFile();
				if(newFileCreated){
					FileCopyUtils.copy(file.getBytes(),f);
					resource.setSize(file.getSize() / 1024);
				}else{
					logger.info("Create file fails!");
				}
			}else{
				logger.info("Create folder fails!");
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void save(Resource resource, BindingResult result) {
	
//		resourceValidator.validate(resource, result);
		boolean isSucceed = true;
		if (isSucceed) {
			getEntityService().save(resource);
		}else{
			List <ObjectError> list = result.getAllErrors();
			for(int i = 0 ; i <list.size() ; i++ ){
				logger.info(list.get(i).toString());
			}
		}
	}
	
	private void populateAttributes(ModelMap modelMap) {}
	
	@RequestMapping(value = "contentType/json")
	public ModelAndView getContentTypeList() {
		JSONArray ja = new JSONArray();
		List<ResourceType> contentTypeList = new ArrayList<ResourceType>();
		contentTypeList.add(ResourceType.IMAGE);
		contentTypeList.add(ResourceType.PRODUCT_DRAWING);
		contentTypeList.add(ResourceType.PRODUCT_BROCHURE);
		contentTypeList.add(ResourceType.QUALITY_ASSURANCE);
		contentTypeList.add(ResourceType.TEXT_FILE);
		
		buildJSONArray(ja ,contentTypeList );
		return toJSONView(ja);
	}
	@RequestMapping(value = "fileType/json")
	public ModelAndView getFileTypeList() {
		JSONArray ja = new JSONArray();
		List<ResourceType> resourceTypeList = new ArrayList<ResourceType>();
		resourceTypeList.add(ResourceType.IMAGE);
		resourceTypeList.add(ResourceType.BINARY_FILE);
		buildJSONArray(ja ,resourceTypeList );
		return toJSONView(ja);
	}

	private void buildJSONArray(JSONArray ja, List<ResourceType> resourceTypeList) {
		
		for(ResourceType rt : resourceTypeList){
			JSONObject jo = new JSONObject();
			jo.put( "code" , rt.getName());
			jo.put( "text" , rt.getText());
			ja.put(jo);
		}
	}

}
