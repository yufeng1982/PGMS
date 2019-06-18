/**
\ * @author FengYu
 */
package com.photo.bas.core.model.entity;

import java.text.ParseException;
import java.util.Calendar;
import java.util.EnumSet;

import org.json.JSONArray;
import org.json.JSONObject;

import com.photo.bas.core.exception.SourceEntityError;
import com.photo.bas.core.model.common.ColumnConfig;
import com.photo.bas.core.model.common.Country;
import com.photo.bas.core.model.common.Province;
import com.photo.bas.core.utils.AppUtils;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.Strings;

public enum MaintenanceType implements IEnum , XType {
	Country {
		@Override public Class<Country> getClazz() { return Country.class; }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/country/json"; }
		@Override public JSONArray getColumModel(){
			JSONArray ja = getDefaultColumModelWithDescription();
			ja.put(ColumnConfig.getStringColumn("phoneCode", "Com.PhoneCode"));
			ja.put(ColumnConfig.getStringColumn("stateAlias", "Com.StateAlias"));
			ja.put(ColumnConfig.getStringColumn("postalCodeAlias", "Com.PostalCodeAlias"));
			return ja;
		}
	},
	Province {
		@Override public Class<Province> getClazz() { return Province.class; }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/province/json"; }
		@Override public JSONArray getColumModel() {
			JSONArray ja = getDefaultColumModel();
			ja.put(ColumnConfig.getBrowserButtonColumn("country", null, "Com.Country", MaintenanceType.Country).put("width", 130));
			return ja;
		}
	},
	Position {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.Position"); }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/hr/position/json"; }
		@Override public JSONArray getColumModel() {
			JSONArray ja = getDefaultColumModelWithDescription();
			return ja;
		}
	},
	Department {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.Department"); }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/hr/department/json"; }
		@Override public JSONArray getColumModel() {
			JSONArray ja = getDefaultColumModelWithDescription();
			return ja;
		}
	},
	CooperationType {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.CooperationType"); }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/project/cooperationType/json"; }
		@Override public JSONArray getColumModel() {
			JSONArray ja = getDefaultColumModelWithDescription();
			return ja;
		}
	},
	GroupType {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.GroupType"); }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/group/groupType/json"; }
		@Override public JSONArray getColumModel() {
			JSONArray ja = getDefaultColumModelWithDescription();
			ja.put(ColumnConfig.getCheckColumn("mandatoryAssignment").put("width", 140));
			return ja;
		}
	},	
	Group {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.Group"); }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/group/group/json"; }
		@Override public String getParameterNames() {return "groupType"; }
		@Override public JSONArray getColumModel() {
			JSONArray ja = getDefaultColumModelWithDescription();
			return ja;
		}	
	},
	AssetsCategory{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.AssetsCategory"); }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/project/assetsCategory/json"; }
		@Override public JSONArray getColumModel() {
			JSONArray ja = getDefaultColumModelWithDescription();
			return ja;
		}
	},
	ContractCategory{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.ContractCategory"); }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/project/contractCategory/json"; }
		@Override public JSONArray getColumModel() {
			JSONArray ja = new JSONArray();
			ja.put(ColumnConfig.getStringColumn("id"));
			ja.put(ColumnConfig.getComboBoxColumn("contractCategoryType", getRealClazz("com.photo.pgm.core.enums.ContractCategoryType"), null, false));
			ja.put(ColumnConfig.getStringColumn("code", false, true).put("maxLength", 100));
			ja.put(ColumnConfig.getStringColumn("name", false).put("maxLength", 100));
			ja.put(ColumnConfig.getHiddenColumn("displayString"));
			ja.put(ColumnConfig.getStringColumn("description", true, false));
			return ja;
		}
	},
	ContractProperty{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.ContractProperty"); }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/project/contractProperty/json"; }
		@Override public JSONArray getColumModel() {
			JSONArray ja = getDefaultColumModelWithDescription();
			return ja;
		}
	},
	CostCenter{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.CostCenter"); }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/project/costCenter/json"; }
		@Override public JSONArray getColumModel() {
			JSONArray ja = getDefaultColumModelWithDescription();
			return ja;
		}
	},
	YearBudgets{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.YearBudgets"); }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/project/yearBudgets/json"; }
		@Override public JSONArray getColumModel() {
			JSONArray ja = new JSONArray();
			ja.put(ColumnConfig.getStringColumn("id"));
			
			JSONObject jo = new JSONObject();
			jo.put("fieldType", "combobox");
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int startYear = year - 10;
			int endYear = year + 10;
			JSONArray ja1 = new JSONArray();
			for (int i = startYear; i <= endYear; i++) {
				JSONArray jo1 = new JSONArray();
				jo1.put(i);
				jo1.put(i);
				ja1.put(jo1);
			}
			jo.put("controlData", ja1);
			jo.put("notNull", true);
			jo.put("unique", true);
			ColumnConfig.putDefaultValue(jo, "years", "", false);
			
			
			ja.put(jo);
			ja.put(ColumnConfig.getNumberColumn("budgets", false).put("positiveNumber", true));
			ja.put(ColumnConfig.getStringColumn("description", true, false));
			return ja;
		}
	},
	OurSideCorporation{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.OurSideCorporation"); }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/project/ourSideCorporation/json"; }
		@Override public JSONArray getColumModel() {
			JSONArray ja = getDefaultColumModel();
			ja.put(ColumnConfig.getBrowserButtonColumn("city", "project/_includes/_citysGrid", null, "", false , null , false, "").put("width", 180));
			ja.put(ColumnConfig.getStringColumn("description", true, false));
			return ja;
		}
	},
	City{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.City"); }
		@Override public String getSearchUrl() { return "/app/" + AppUtils.APP_NAME + "/project/city/json"; }
		@Override public JSONArray getColumModel() {
			JSONArray ja = getDefaultColumModel();
			ja.put(ColumnConfig.getStringColumn("description", true, false));
			return ja;
		}
	};

	public static EnumSet<MaintenanceType> getMaintenanceTypes() {
		return EnumSet.allOf(MaintenanceType.class);
	}

	
	public abstract String getSearchUrl();
	public abstract Class<?> getClazz();
	
	public JSONArray getColumModel(){
		return getDefaultColumModel();
	}
	public String getHiddenColumns(){
		return "id";
	}
	
	public static JSONArray getDefaultColumModel(){
		JSONArray ja = new JSONArray();
		ja.put(ColumnConfig.getStringColumn("id"));
		ja.put(ColumnConfig.getStringColumn("code", false, true).put("maxLength", 100));
		ja.put(ColumnConfig.getStringColumn("name", false).put("maxLength", 100));
		ja.put(ColumnConfig.getHiddenColumn("displayString"));
		return ja;
	}
	public static JSONArray getDefaultColumModelWithDescription(){
		JSONArray ja = getDefaultColumModel();
		ja.put(ColumnConfig.getStringColumn("description", true, false));
		return ja;
	}
	
	public String getStoreFieldArray(){
		JSONArray array = getColumModel();
		StringBuffer strbff = new StringBuffer("[");
		for(int i  = 0; i < array.length(); i++){
			JSONObject jsobj = array.getJSONObject(i);
			strbff.append("'").append(jsobj.getString("id")).append("'");
			if(i != array.length() - 1){
				strbff.append(",");
			}
		}
		strbff.append("]");
		return strbff.toString();
	}
	
	public String getKey() {
		return new StringBuffer().append("FN.").append(name()).toString();
	}
	protected Class<?> getRealClazz(String strClazz) {
		if("pgm".equals(AppUtils.APP_NAME))
			try {
				return Class.forName(strClazz);
			} catch (ClassNotFoundException e) {
				throw new SourceEntityError(SourceEntityError.SOURCE_TYPE_CLAZZ_ERROR);
			}
		return null;
	}
	public String getParameterNames() {
		return "";
	}
	public String getGridUrl(){
		return "/maintenance/_includes/_maintenanceGrid";
	}
	
	public String getGridInitMethod(){
		return "";
	}
	
	public String getGridConfigName(){
		return "";
	}
	
	public String getGridInitParameters(){
		return "";
	}
	
	public String getValueField(){
		return "id";
	}
	public String getDisplayField(){
		return "name";
	}
	public String getNameField(){
		return "name";
	}
	
	public String getOptionsTemplate(){
		return "{" + getDisplayField() + "}-{" + getNameField() + "}";
	}
	
	public String getText() {
		return ResourceUtils.getText(getKey());
	}
	
	public String getName() {
		return name();
	}
	
	public static MaintenanceType fromName(String name) {
    	if(Strings.isEmpty(name)) return null;
        return MaintenanceType.valueOf(name);
	}
	
	public String toString() {
		return getText();
	}
	
	@Override
	public JSONObject regXtype() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("modelName", getName());
			jo.put("storeFieldArray", new JSONArray(getStoreFieldArray()));
			jo.put("valueField", getValueField());
			jo.put("displayField", getDisplayField());
			jo.put("searchUrl", getSearchUrl());
			jo.put("gridUrl", getGridUrl());
//			jo.put("gridInitMethod", getGridInitMethod());
			jo.put("parameters", new JSONObject());
			jo.put("mtype", getName());
			jo.put("gridInitParameters", getGridInitParameters());
			jo.put("optionsTemplate", getOptionsTemplate());
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jo;
	}
}
