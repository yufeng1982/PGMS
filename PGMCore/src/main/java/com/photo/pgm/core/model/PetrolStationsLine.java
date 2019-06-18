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

import com.photo.bas.core.model.entity.AbsNameEntity;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.pgm.core.enums.ConstructType;
import com.photo.pgm.core.enums.HaveNoType;

/**
 * @author FengYu
 * 油站状况行信息
 */
@Entity
@Table(schema = "project")
@Audited
public class PetrolStationsLine extends AbsNameEntity {

	private static final long serialVersionUID = -8712387979696920470L;

	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Project project;
	
	private Integer seq; // 序号
	private Double zpmj = new Double(0); // 罩棚面积
	@Enumerated(EnumType.STRING)
	private ConstructType ct; // 结构类型
	private String bulidYear; // 建成年代
	private String bmzk; // 表面状况
	private String wmsl; // 屋面渗漏
	private String wmps; // 屋面排水
	private HaveNoType ywdd; // 有无吊顶
	
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

	public Double getZpmj() {
		return zpmj;
	}

	public void setZpmj(Double zpmj) {
		this.zpmj = zpmj;
	}

	public ConstructType getCt() {
		return ct;
	}

	public void setCt(ConstructType ct) {
		this.ct = ct;
	}

	public String getBulidYear() {
		return bulidYear;
	}

	public void setBulidYear(String bulidYear) {
		this.bulidYear = bulidYear;
	}

	public String getBmzk() {
		return bmzk;
	}

	public void setBmzk(String bmzk) {
		this.bmzk = bmzk;
	}

	public String getWmsl() {
		return wmsl;
	}

	public void setWmsl(String wmsl) {
		this.wmsl = wmsl;
	}

	public String getWmps() {
		return wmps;
	}

	public void setWmps(String wmps) {
		this.wmps = wmps;
	}

	public HaveNoType getYwdd() {
		return ywdd;
	}

	public void setYwdd(HaveNoType ywdd) {
		this.ywdd = ywdd;
	}

	@Override
	public String getDisplayString() {
		return null;
	}
	
	@Override
	public JSONObject toJSONObject() {
		JSONObject jo = super.toJSONObject();
		jo.put("seq", FormatUtils.intValue(seq));
		jo.put("zpmj", FormatUtils.stringValue(zpmj));
		jo.put("ct", FormatUtils.nameString(ct));
		jo.put("bulidYear", FormatUtils.stringValue(bulidYear));
		jo.put("bmzk", FormatUtils.stringValue(bmzk));
		jo.put("wmsl", FormatUtils.stringValue(wmsl));
		jo.put("wmps", FormatUtils.stringValue(wmps));
		jo.put("ywdd", FormatUtils.nameString(ywdd));
		return jo;
	}
}
