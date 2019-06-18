/**
 * 
 */
package com.photo.pgm.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.common.Department;
import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class Asset extends AbsCodeNameEntity {

	private static final long serialVersionUID = -2900988957035948110L;

	private Integer seq = new Integer(0); // 序号
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private PetrolStation petrolStation; // 工程管理-油站信息
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Contract contract; // 合同信息
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private AssetsCategory assetsCategory; // 资产类别
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Department department; // 资产属于部门
	
	private String brand; // 品牌
	private String specification; // 规格
	private Integer quantity = new Integer(0); // 数量
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date qualityPeriod;

	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Cooperation vendor;
	
	private String sapcode1; // SAP资产类别号
	private String sapcode2; // SAP台卡号
	
	
	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("seq", FormatUtils.intValue(seq));
    	jo.put("contract",contract == null ? "" : FormatUtils.stringValue(contract.getCode()));
    	jo.put("assetsCategory", FormatUtils.stringValue(assetsCategory.getName()));
    	jo.put("brand", FormatUtils.stringValue(brand));
    	jo.put("specification", FormatUtils.stringValue(specification));
    	jo.put("quantity", FormatUtils.intValue(quantity));
    	jo.put("qualityPeriod", FormatUtils.dateValue(qualityPeriod));
    	jo.put("vendor", FormatUtils.stringValue(vendor == null ? "" : vendor.getName()));
    	jo.put("sapcode1", FormatUtils.stringValue(sapcode1));
    	jo.put("sapcode2", FormatUtils.stringValue(sapcode2));
    	jo.put("department", FormatUtils.displayString(department));
    	return jo;
    }
	
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public PetrolStation getPetrolStation() {
		return petrolStation;
	}

	public void setPetrolStation(PetrolStation petrolStation) {
		this.petrolStation = petrolStation;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public AssetsCategory getAssetsCategory() {
		return assetsCategory;
	}

	public void setAssetsCategory(AssetsCategory assetsCategory) {
		this.assetsCategory = assetsCategory;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getQualityPeriod() {
		return qualityPeriod;
	}

	public void setQualityPeriod(Date qualityPeriod) {
		this.qualityPeriod = qualityPeriod;
	}

	public Cooperation getVendor() {
		return vendor;
	}

	public void setVendor(Cooperation vendor) {
		this.vendor = vendor;
	}

	public String getSapcode1() {
		return sapcode1;
	}

	public void setSapcode1(String sapcode1) {
		this.sapcode1 = sapcode1;
	}

	public String getSapcode2() {
		return sapcode2;
	}

	public void setSapcode2(String sapcode2) {
		this.sapcode2 = sapcode2;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}

}
