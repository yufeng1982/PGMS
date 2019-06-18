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

import com.photo.bas.core.model.entity.AbsEntity;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 * 油罐行信息
 */
@Entity
@Table(schema = "project")
@Audited
public class OilMachineLine extends AbsEntity {

	private static final long serialVersionUID = 6054759094785075529L;

	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Project project;
	
	private Integer seq; // 序号
	
	private String pingpai; // 品牌
	private String xinghao; // 型号
	private Double mqty  = new Double(0);; // 油机数量
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date releaseDate; // 出厂日期
	
	public OilMachineLine () {
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

	public String getPingpai() {
		return pingpai;
	}

	public void setPingpai(String pingpai) {
		this.pingpai = pingpai;
	}

	public String getXinghao() {
		return xinghao;
	}

	public void setXinghao(String xinghao) {
		this.xinghao = xinghao;
	}

	public Double getMqty() {
		return mqty;
	}

	public void setMqty(Double mqty) {
		this.mqty = mqty;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String getDisplayString() {
		return null;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jo = super.toJSONObject();
		jo.put("seq", FormatUtils.intValue(seq));
		jo.put("pingpai", FormatUtils.stringValue(pingpai));
		jo.put("xinghao", FormatUtils.stringValue(xinghao));
		jo.put("mqty", FormatUtils.doubleValue(mqty));
		jo.put("releaseDate", FormatUtils.dateValue(releaseDate));
		return jo;
	}
	
}
