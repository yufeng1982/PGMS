/**
 * 
 */
package com.photo.bas.core.web.controller.common;

import java.util.List;
import java.util.Set;

import javax.measure.unit.Unit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.common.UnitType;
import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.UnitUtils;
import com.photo.bas.core.web.controller.entity.AbsListController;
import com.photo.bas.core.web.view.JSONView;

/**
 * @author FengYu
 *
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping(value="/*/common/unit")
public class UnitListController extends AbsListController {

	private static final String UNIT_LIST_PAGE = "common/unit/units";

	@RequestMapping("show")
	public String show(@RequestParam(value="unitType", required=false) String unitType, 
			@RequestParam(value="disabledType", required=false) boolean disabledType,
			ModelMap modelMap) {
		if(Strings.isEmpty(unitType)){
			unitType = UnitType.EACH.getName();
		}
		modelMap.addAttribute("unitType", unitType);
		modelMap.addAttribute("disabledType", disabledType);
		return UNIT_LIST_PAGE;
	}
	
	@ModelAttribute("unitTypeList")
	public Set<UnitType> initUnitTypeList(){
		return UnitType.getUnitTypes();
	}
	
	@RequestMapping("json")
	public ModelAndView json(@RequestParam(value="unitType", required=false) String unitTypeId) {
		return toJSONView(getUnitList(unitTypeId));
	}
	private ModelAndView toJSONView(List list) {
		JSONObject jso = new JSONObject();
		JSONArray ja = buildJSONArray(list);
		jso.put("data", ja);
		return new ModelAndView(new JSONView(jso.toString()));
	}

	private JSONArray buildJSONArray(List list) {
		JSONArray ja = new JSONArray();
		for (Object t : list) {
			Unit unit = (Unit) t;
			JSONObject obj = new JSONObject();
			obj.put("id", unit.toString());
			obj.put("code", unit.toString());
			obj.put("name", unit.toString());
			obj.put("displayString", unit.toString());
			ja.put(obj);
		}
		return ja;
	}
	private List getUnitList(String unitType){
		UnitType type;
		if(!Strings.isEmpty(unitType)) {
			type = UnitType.fromName(unitType);
		}else{
			type = UnitType.EACH;
		}
		
		return  UnitUtils.getUnitList(type);
	}

	@Override
	protected AbsEntityService getEntityService() {
		return null;
	}

}
