/**
 * 
 */
package com.photo.pgm.core.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsEntity;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.pgm.core.enums.OilType;

/**
 * @author FengYu
 * 油罐行信息
 */
@Entity
@Table(schema = "project")
@Audited
public class OilCanLine extends AbsEntity {

	private static final long serialVersionUID = 6054759094785075529L;

	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Project project;
	
	private Integer seq; // 序号
	
	private Double guanrong = new Double(0);; // 罐容
	
	@Enumerated(EnumType.STRING)
	private OilType oilType;// 油品
	
	private Double qty = new Double(0); // 数量
	
	private Double age = new Double(0);; // 罐龄
	
	public OilCanLine () {
		super();
	}
	
	public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}


	public Integer getSeq() {
		return seq;
	}


	public void setSeq(Integer seq) {
		this.seq = seq;
	}


	public Double getGuanrong() {
		return guanrong;
	}


	public void setGuanrong(Double guanrong) {
		this.guanrong = guanrong;
	}


	public OilType getOilType() {
		return oilType;
	}


	public void setOilType(OilType oilType) {
		this.oilType = oilType;
	}


	public Double getQty() {
		return qty;
	}


	public void setQty(Double qty) {
		this.qty = qty;
	}


	public Double getAge() {
		return age;
	}


	public void setAge(Double age) {
		this.age = age;
	}

	@Override
	public String getDisplayString() {
		return null;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jo = super.toJSONObject();
		jo.put("seq", FormatUtils.intValue(seq));
		jo.put("guanrong", FormatUtils.doubleValue(guanrong));
		jo.put("oilType", FormatUtils.nameString(oilType));
		jo.put("qty", FormatUtils.doubleValue(qty));
		jo.put("age", FormatUtils.doubleValue(age));
		return jo;
	}
	
}
