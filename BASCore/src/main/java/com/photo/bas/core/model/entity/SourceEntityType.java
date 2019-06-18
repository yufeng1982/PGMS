/**
 * 
 */
package com.photo.bas.core.model.entity;

import java.util.EnumSet;

import org.json.JSONArray;
import org.json.JSONObject;

import com.photo.bas.core.exception.SourceEntityError;
import com.photo.bas.core.model.common.Resource;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.utils.AppUtils;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.Strings;

/**
 * @author FengYu
 *
 */
public enum SourceEntityType implements IEnum , XType {

	ImageAndAttachment {
		@Override public Class<Resource> getClazz() { return Resource.class; }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public boolean isChangable() { return false; }
	},
	Employee {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.bas.core.model.common.Employee", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/hr/employee/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/hr/employee/list/json"; }
		@Override public String getGridUrl() { return "hr/employee/_includes/_employeesGrid"; }
		@Override public String getDisplayField() { return "name"; }
		@Override public boolean isChangable() { return false; }
	},
	User {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.bas.core.model.security.User", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/user/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/user/list/json4RO"; }
		@Override public String getGridUrl() { return "security/user/_includes/_usersGrid"; }
		@Override public String getDisplayField() { return "realName"; }
		@Override public boolean isChangable() { return false; }
	},
	ItemPackage {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.ItemPackage", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/photo/itemPackage/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/photo/itemPackage/list/json"; }
		@Override public String getGridUrl() { return "product/itemPackage/_includes/_itemPackagesGrid"; }
		@Override public String getDisplayField() { return "name"; }
		@Override public boolean isChangable() { return false; }
	},
	Department{
		@Override public Class<?> getClazz() { return getRealClazz("com.ods.emp.core.model.Department", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
//		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/organization/list"; }
//		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/organization/list/json"; }
//		@Override public String getGridUrl() { return "security/organization/_includes/_organizationsGrid"; }
//		@Override public boolean isChangable() { return false; }
//		@Override public boolean isCreatable() { return false; }
//		@Override public String getDisplayField() { return "shortName"; }
		public String getValueField() { return "id"; }
		public String getNameField() { return "name"; }
	},
	Position{
		@Override public Class<?> getClazz() { return getRealClazz("com.ods.emp.core.model.Position", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
//		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/organization/list"; }
//		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/organization/list/json"; }
//		@Override public String getGridUrl() { return "security/organization/_includes/_organizationsGrid"; }
//		@Override public boolean isChangable() { return false; }
//		@Override public boolean isCreatable() { return false; }
//		@Override public String getDisplayField() { return "shortName"; }
		public String getValueField() { return "id"; }
		public String getNameField() { return "name"; }
	},
	PetrolStation{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.PetrolStation", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/petrolStation/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/petrolStation/list/json"; }
		@Override public String getGridUrl() { return "project/petrolStation/_includes/_petrolStations4PopupGrid"; }
		@Override public String getDisplayField() { return "name"; }

		@Override public String getOptionsTemplate(){
			return "{code}-{name}";
		}
	},
	OilPetrolStation{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.PetrolStation", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/petrolStation/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/petrolStation/list/oilJson"; }
		@Override public String getGridUrl() { return "project/petrolStation/_includes/_oilPetrolStations4PopupGrid"; }
		@Override public String getDisplayField() { return "name"; }

		@Override public String getOptionsTemplate(){
			return "{code}-{name}";
		}
	},
	ProjectApproveBudget {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.ProjectApproveBudget", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/projectApproveBudget/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/projectApproveBudget/list/json"; }
		@Override public String getGridUrl() { return "project/projectApproveBudget/_includes/_projectApproveBudgetsGrid"; }
		@Override public String getDisplayField() { return "code"; }
		@Override public String getOptionsTemplate(){
			return "{code}-{name}";
		}
	},
	YearBudgets  {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.YearBudgets", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/yearBudgets/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/yearBudgets/list/json"; }
		@Override public String getGridUrl() { return "project/yearBudgets/_includes/_yearBudgetsGrid"; }
		@Override public String getDisplayField() { return "approveContents"; }
		@Override public String getOptionsTemplate(){
			return "{years}-{approveContents}";
		}
	},
	Project{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.Project", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/project/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/project/list/json"; }
		@Override public String getGridUrl() { return "project/project/_includes/_projects4PopupGrid"; }
		@Override public String getDisplayField() { return "code"; }

		@Override public String getOptionsTemplate(){
			return "{code}-{name}";
		}
	},
	OilProject{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.Project", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/project/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/project/list/oilJson"; }
		@Override public String getGridUrl() { return "project/project/_includes/_oilProjects4PopupGrid"; }
		@Override public String getDisplayField() { return "name"; }

		@Override public String getOptionsTemplate(){
			return "{code}-{name}";
		}
	},
	Contract{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.Contract", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/contract/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/contract/list/json"; }
		@Override public String getGridUrl() { return "project/contract/_includes/_contractsGrid"; }
		@Override public String getDisplayField() { return "name"; }
		@Override public String getOptionsTemplate(){
			return "{code}-{name}";
		}
	},
	Asset{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.Asset", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/asset/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/asset/list/json"; }
		@Override public String getGridUrl() { return "project/asset/_includes/_assetsGrid"; }
		@Override public String getDisplayField() { return "name"; }
		@Override public String getOptionsTemplate(){
			return "{code}-{name}";
		}
	},
	RepairOrder{
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.RepairOrder", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/repairOrder/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/repairOrder/list/json"; }
		@Override public String getGridUrl() { return "project/repairOrder/_includes/_repairOrdersGrid"; }
		@Override public String getDisplayField() { return "code"; }
		@Override public String getOptionsTemplate(){
			return "{code}";
		}
	},
	Cooperation {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.Cooperation", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/cooperation/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/cooperation/list/json"; }
		@Override public String getGridUrl() { return "project/cooperation/_includes/_cooperationsGrid"; }
		@Override public String getDisplayField() { return "name"; }
		@Override public String getOptionsTemplate(){
			return "{code}-{name}";
		}
	},
	CooperationAccount {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.CooperationAccount", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/cooperationAccount/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/cooperationAccount/list/json"; }
		@Override public String getGridUrl() { return "project/cooperation/_includes/_cooperationAccountsGrid"; }
		@Override public String getDisplayField() { return "receiveNo"; }
		@Override public String getOptionsTemplate(){
			return "{bank}-{receiveNo}";
		}
	},
	OurSideCorporation {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.OurSideCorporationn", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/ourSideCorporation/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/ourSideCorporation/list/json"; }
		@Override public String getGridUrl() { return "project/ourSideCorporation/_includes/_ourSideCorporationsGrid"; }
		@Override public String getDisplayField() { return "name"; }
		@Override public String getOptionsTemplate(){
			return "{code}-{name}";
		}
	},
	AssetsCategory {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.AssetsCategory", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/assetsCategory/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/assetsCategory/list/json"; }
		@Override public String getGridUrl() { return "project/assetsCategory/_includes/_assetsCategorysGrid"; }
		@Override public String getDisplayField() { return "name"; }
		@Override public String getOptionsTemplate(){
			return "{code}-{name}";
		}
	},
	FlowDefinition {
		@Override public Class<?> getClazz() { return getRealClazz("com.photo.pgm.core.model.FlowDefinition", AbsCodeNameEntity.class); }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/project/flowDefinition/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/project/flowDefinition/list/json"; }
		@Override public String getGridUrl() { return "project/flowDefinition/_includes/_flowDefinitionsGrid"; }
		@Override public String getDisplayField() { return "name"; }
	},
	Corporation {
		@Override public Class<Corporation> getClazz() { return Corporation.class; }
		@Override public Class<ProductSourceEntity> getSourceEntityClazz() { return ProductSourceEntity.class; }
		@Override public String getGridListUrl() { return "/app/" +AppUtils.APP_NAME+ "/organization/list"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/organization/list/json"; }
		@Override public String getGridUrl() { return "security/organization/_includes/_organizationsGrid"; }
		@Override public boolean isChangable() { return false; }
		@Override public boolean isCreatable() { return false; }
		public String getValueField() { return "id"; }
		public String getNameField() { return "shortName"; }
	},
	Address {
		@Override public Class<?> getClazz() { return getRealClazz("com.ods.emp.core.model.Address", AbsCodeNameEntity.class); }
		@Override public Class<?> getSourceEntityClazz() {return null;}
		@Override public boolean isChangable() { return false; }
		@Override public boolean isCreatable() { return false; }
	},


	Role {
		@Override public Class<Role> getClazz() { return Role.class; }
		@Override public String getKey() { return "RelatedToType.Role"; }
		@Override public String getSearchUrl() { return "/app/"+AppUtils.APP_NAME+"/role/list/json"; }
		@Override public String getGridUrl() { return "/security/user/_includes/_rolesGrid"; }
		@Override
		public Class<?> getSourceEntityClazz() {
			return null;
		}
		@Override public String getDisplayField() { return "code"; }
		@Override public JSONArray getStoreFieldArray(){
			JSONArray array = new JSONArray();
			array.put(getValueField());
			array.put(getDisplayField());
			return array;
		}
		
		@Override public String getOptionsTemplate(){
			return "{code}";
		}
		
	};
	
	public static EnumSet<SourceEntityType> getSourceEntityTypes() {
		return EnumSet.allOf(SourceEntityType.class);
	}
	
	SourceEntityType() {}

	public String getKey() {
		return getClazz().getName();
	}
	
	public String getSearchUrl() { return ""; }
	public String getGridUrl() { return ""; }
	public String getFindUrl() { return ""; }
	public String getGridConfigName() { return ""; }
	public String getGridInitParameters() { return ""; }
	public String getValueField() { return "id"; }
	public String getDisplayField() { return "code"; }
	public String getNameField() { return "name"; }
	public String getCodeField() { return "code"; }
	public String getGridListUrl() { return ""; }
	public boolean isChangable() { return true; }
	public boolean isLogable() { return false; }
	public boolean isCreatable() { return true; }
	public String getLauncher(String id) {
		JSONObject jo = new JSONObject();
		jo.put("showMethodName", "show" + getName());
		jo.put("id", id);
		return jo.toString();
	}
	
	public abstract Class<?> getClazz();
	public abstract Class<?> getSourceEntityClazz();
	protected Class<?> getRealClazz(String clazzName, Class<?> clazz) {
		if("steel".equals(AppUtils.APP_NAME)){
			try {
				return Class.forName(clazzName);
			} catch (ClassNotFoundException e) {
				throw new SourceEntityError(SourceEntityError.SOURCE_TYPE_CLAZZ_ERROR);
			}
		}
		return clazz;
		
	}
	public String getText() {
		return ResourceUtils.getText(getKey());
	}
	public String getName() {
		return name();
	}
	public static SourceEntityType fromName(String name) {
    	if(Strings.isEmpty(name)) return null;
        return SourceEntityType.valueOf(name);
	}
	
	public String toString() {
		return getText();
	}
	
	@Override
	public JSONObject regXtype() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("modelName", getName());
			jo.put("storeFieldArray", getStoreFieldArray());
			jo.put("valueField", getValueField());
			jo.put("displayField", getDisplayField());
			jo.put("searchUrl", getSearchUrl());
			jo.put("gridUrl", getGridUrl());
			jo.put("gridInitParameters", getGridInitParameters());
			jo.put("optionsTemplate", getOptionsTemplate());
			jo.put("gridConfigName", getGridConfigName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONArray getStoreFieldArray(){
		JSONArray array = new JSONArray();
		array.put(getValueField());
		array.put(getDisplayField());
		array.put(getNameField());
		array.put(getCodeField());
		return array;
	}
	
	public String getOptionsTemplate(){
		return "{" + getDisplayField() + "}-{" + getNameField() +"}";
	}
	
}
