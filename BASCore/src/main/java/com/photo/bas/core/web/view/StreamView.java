package com.photo.bas.core.web.view;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.View;

import com.photo.bas.core.model.common.Resource;
import com.photo.bas.core.utils.ResourceUtils;

public class StreamView implements View {

	private Resource resource;
	
	@Override
	public String getContentType() {
		return "application/octet-stream;charset=utf-8";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BufferedInputStream bis = null;
		    
		response.setHeader("Content-disposition", "attachment;filename=" + resource.getFileName());
		response.setContentType(getContentType());
		ServletOutputStream out = response.getOutputStream();
		
		String path = ResourceUtils.getAppSetting("system.uploadFolder");
		path += resource.getInternalName();
		bis = new BufferedInputStream(new FileInputStream(path));
		FileCopyUtils.copy(bis, out);
	}

	public StreamView(Resource resource) {
		super();
		this.resource = resource;
	}
	
}
