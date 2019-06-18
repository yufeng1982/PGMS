/**
 * 
 */
package com.photo.pgm.core.controller;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.photo.bas.core.service.common.JasperreportService;
import com.photo.bas.core.service.entity.AbsService;
import com.photo.bas.core.web.controller.common.JasperreportController;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping(value = "/pgm/report/**")
public class ProjectReportController extends JasperreportController {

	
	@Autowired private JasperreportService jasperreportService;
	
	@Override
	protected JRBeanCollectionDataSource getJRBeanDataSource(String dataType,
			JSONObject jso) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected AbsService getEntityService() {
		return null;
	}

}
