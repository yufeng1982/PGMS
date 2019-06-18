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

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsNameEntity;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 * 其他设备行信息
 */
@Entity
@Table(schema = "project")
@Audited
public class OtherEquipmentLine extends AbsNameEntity {

	private static final long serialVersionUID = 4192559760746812619L;

	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Project project;
	
	private Integer seq; // 序号
	
	@Type(type = "true_false")
	@Audited
	private Boolean enabled = Boolean.FALSE; // 激活
	
	private String pingpai; // 品牌
	private String xinghao; // 型号
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date releaseDate; // 出厂日期
	
	public OtherEquipmentLine() {
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

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	@Override
	public JSONObject toJSONObject() {
		JSONObject jo = super.toJSONObject();
		jo.put("seq", FormatUtils.intValue(seq));
		jo.put("enabled", FormatUtils.booleanValue(enabled));
		jo.put("pingpai", FormatUtils.stringValue(pingpai));
		jo.put("xinghao", FormatUtils.stringValue(xinghao));
		jo.put("oelReleaseDate", FormatUtils.dateValue(releaseDate));
		return jo;
	}
	
}
