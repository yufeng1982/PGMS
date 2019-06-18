package com.photo.bas.core.web.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.photo.bas.core.utils.FormatUtils;

public class ExtUploadJSONView extends JSONView {

	// Since ExtJS creates an IFrame to process uploads through AJAX, 
	//we must use a type that can be recognized, 
	//otherwise it will display the "download" popup.
	private static final String CONTENT_TYPE = "text/html";
	
	public ExtUploadJSONView() {
		super();
	}

	public ExtUploadJSONView(String jsonString) {
		super(jsonString);
	}

	@Override
	public String getContentType() {
		return CONTENT_TYPE;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 1);
		if (this.jsonString == null) {

			this.jsonString = FormatUtils.fromMap(model);
		}

		response.setContentType(CONTENT_TYPE);

		PrintWriter out = response.getWriter();
		
		out.write(this.jsonString);
		
	}

	
}
