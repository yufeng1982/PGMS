/**
 * 
 */
package com.photo.pgm.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.common.Employee;
import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.utils.FormatUtils;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.pgm.core.enums.DiPing;
import com.photo.pgm.core.enums.Dishi;
import com.photo.pgm.core.enums.GcCatagory;
import com.photo.pgm.core.enums.GongDian;
import com.photo.pgm.core.enums.GongNuan;
import com.photo.pgm.core.enums.GongShui;
import com.photo.pgm.core.enums.HaveNoType;
import com.photo.pgm.core.enums.HzWay;
import com.photo.pgm.core.enums.KeShiXing;
import com.photo.pgm.core.enums.OilLevel;
import com.photo.pgm.core.enums.OilPosition;
import com.photo.pgm.core.enums.PaiWu;
import com.photo.pgm.core.enums.Ydpbxs;
import com.photo.pgm.core.enums.YesNoType;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class Project extends AbsCodeNameEntity {

	private static final long serialVersionUID = 4998477088475288354L;
//-------------------------------------基本信息------------------------------------------------------------		
	// 基本信息
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private OurSideCorporation ourSideCorporation; // 归属公司
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Employee employee; // 开发负责
	
	@Column(columnDefinition = "text") // 项目地址
	private String address;
	
	private Double zjgs = new Double(0); // 资金估算
	
	private String pct; // 生 市 区
	private String province; // 省 
	private String city; // 市
	private String town; // 区
	
	private String filePath1; // 附件1路径
	private String filePath2; // 附件2路径
	private String filePath3; // 附件3路径
	private String filePath4; // 附件4路径
	private String filePath5; // 附件5路径
	private String filePath6; // 附件6名称
	private String filePath7; // 附件7名称
	
	@Type(type = "true_false")
	@Audited
	private Boolean enabled = Boolean.TRUE;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date addDate; // 填写日期
	
	private String fuzeren; // 负责人
	private String phone; // 联系电话
	private Double salesForecast = new Double(0); // 销售量预测
	
	@Enumerated(EnumType.STRING)
	private OilLevel oilLevel = OilLevel.One; // 油站级别
	@Enumerated(EnumType.STRING)
	private HzWay hzWay = HzWay.Sg; // 合作方式
	@Enumerated(EnumType.STRING)
	private GcCatagory gcCatagory = GcCatagory.One; // 工程分类
	
	private Integer carRoadCounts = new Integer(0); // 停车占道数
	
	@Enumerated(EnumType.STRING)
	private HaveNoType ywgld = HaveNoType.Have; // 隔离
	
	private Double qiyouP = new Double(0); // 汽油比
	private Double caiyouP = new Double(0); // 柴油比
//-------------------------------------油站状况------------------------------------------------------------		
	// 油站信息
	@Column(columnDefinition ="text")
	private String otherSpec;// 其他说明
	
	@Enumerated(EnumType.STRING)
	private OilPosition oilPosition = OilPosition.Xsq; // 油站位置县市区
	
	@Enumerated(EnumType.STRING)
	private Ydpbxs ydpbxs = Ydpbxs.Fx; // 油岛排布
	
	@Enumerated(EnumType.STRING)
	private Dishi diShi = Dishi.Gyql; // 地势
	
	@Enumerated(EnumType.STRING)
	private GongDian gongDian = GongDian.Dlbyq; // 供电
	
	@Enumerated(EnumType.STRING)
	private GongShui gongShui = GongShui.Zls; // 供水
	
	@Enumerated(EnumType.STRING)
	private GongNuan gongNuan = GongNuan.Szgn; // 供暖
	
	@Enumerated(EnumType.STRING)
	private KeShiXing keShiXing = KeShiXing.Lh; // 可视性
	
	@Enumerated(EnumType.STRING)
	private DiPing diPing = DiPing.Lh; // 地坪
	
	@Enumerated(EnumType.STRING)
	private PaiWu paiWu = PaiWu.Oth; // 排污
	
//-------------------------------------设备设施------------------------------------------------------------	
	
	@Enumerated(EnumType.STRING)
	private YesNoType ynFbzg = YesNoType.Yes; // 防爆阻隔
	@Enumerated(EnumType.STRING)
	private YesNoType ynGc = YesNoType.Yes; // 罐池
	
	@Column(columnDefinition = "text")
	private String equipmentSpec; // 设备备注
//-------------------------------------电力通信电缆------------------------------------------------------------	
	
	@Enumerated(EnumType.STRING)
	private YesNoType ynJktxx = YesNoType.Yes; // 架空通讯线
	@Enumerated(EnumType.STRING)
	private YesNoType ynJktxxky = YesNoType.Yes; // 是否跨越油站
	
	@Enumerated(EnumType.STRING)
	private YesNoType ynJkdlx = YesNoType.Yes; // 架空电力线
	@Enumerated(EnumType.STRING)
	private YesNoType ynJkdlxky = YesNoType.Yes; // 是否跨越油站
	
	@Enumerated(EnumType.STRING)
	private YesNoType ynMyszgw = YesNoType.Yes; // 埋有市政管网
	@Enumerated(EnumType.STRING)
	private YesNoType ynMyszgwky = YesNoType.Yes; // 是否跨越油站
	
	@Enumerated(EnumType.STRING)
	private YesNoType ynMyjygl = YesNoType.Yes; //  埋有军用光缆
	@Enumerated(EnumType.STRING)
	private YesNoType ynMyjyglky = YesNoType.Yes; // 是否跨越油站
	
	@Column(columnDefinition = "text")
	private String yzzbzk; // 油站周边状况
//-------------------------------------加油站土地及证照------------------------------------------------------------
	private String tdxz; // 土地性质
	
	@Enumerated(EnumType.STRING)
	private YesNoType ynTdz = YesNoType.No; // 是否有土地证
	@Enumerated(EnumType.STRING)
	private YesNoType ynYdhx = YesNoType.No; //  是否有用地红线
	@Enumerated(EnumType.STRING)
	private YesNoType ynYzhgzs = YesNoType.No; //  加油站竣工后是否经消防验收合格，并取得《建筑工程消防验收意见书》？
	@Enumerated(EnumType.STRING)
	private YesNoType ynJyxkz = YesNoType.No; //  是否有经营许可证
	@Enumerated(EnumType.STRING)
	private YesNoType ynJchgz = YesNoType.No; //  是否有检测合格证
	
	private String jzkzxtydhxjl; // 建筑控制线退用地红线距离
	private String dlkzx; // 道路控制线
	
	private String zsbh; // 证书编号
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date releaseDate; // 验收日期
	
	private String xkzbh; // 许可证编号

	private String hgzbh; // 合格证编号
	private String xkzzl; // 许可证种类
	private String xkfw; // 许可范围
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date yxqFrom; // 合格证有效期 from
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date yxqTo; // 合格证有效期 to
	
	@Column(columnDefinition = "text")
	private String othersm;
	
	private Integer yggs = new Integer(0);// 油罐个数
	private Integer yjts = new Integer(0);// 油机台数
	
	private Integer seq = new Integer(0);
	
	public Project() {
		super();
	}
	
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public OurSideCorporation getOurSideCorporation() {
		return ourSideCorporation;
	}

	public void setOurSideCorporation(OurSideCorporation ourSideCorporation) {
		this.ourSideCorporation = ourSideCorporation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getZjgs() {
		return zjgs;
	}

	public void setZjgs(Double zjgs) {
		this.zjgs = zjgs;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getFilePath6() {
		return filePath6;
	}

	public void setFilePath6(String filePath6) {
		this.filePath6 = filePath6;
	}


	public String getFilePath7() {
		return filePath7;
	}

	public void setFilePath7(String filePath7) {
		this.filePath7 = filePath7;
	}

	public String getFilePath1() {
		return filePath1;
	}

	public void setFilePath1(String filePath1) {
		this.filePath1 = filePath1;
	}

	public String getFilePath2() {
		return filePath2;
	}

	public void setFilePath2(String filePath2) {
		this.filePath2 = filePath2;
	}

	public String getFilePath3() {
		return filePath3;
	}

	public void setFilePath3(String filePath3) {
		this.filePath3 = filePath3;
	}

	public String getFilePath4() {
		return filePath4;
	}

	public void setFilePath4(String filePath4) {
		this.filePath4 = filePath4;
	}

	public String getFilePath5() {
		return filePath5;
	}

	public void setFilePath5(String filePath5) {
		this.filePath5 = filePath5;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getFuzeren() {
		return fuzeren;
	}

	public void setFuzeren(String fuzeren) {
		this.fuzeren = fuzeren;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getSalesForecast() {
		return salesForecast;
	}

	public void setSalesForecast(Double salesForecast) {
		this.salesForecast = salesForecast;
	}


	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public OilLevel getOilLevel() {
		return oilLevel;
	}

	public void setOilLevel(OilLevel oilLevel) {
		this.oilLevel = oilLevel;
	}

	public HzWay getHzWay() {
		return hzWay;
	}

	public void setHzWay(HzWay hzWay) {
		this.hzWay = hzWay;
	}

	public GcCatagory getGcCatagory() {
		return gcCatagory;
	}

	public void setGcCatagory(GcCatagory gcCatagory) {
		this.gcCatagory = gcCatagory;
	}

	public HaveNoType getYwgld() {
		return ywgld;
	}

	public void setYwgld(HaveNoType ywgld) {
		this.ywgld = ywgld;
	}

	public Integer getCarRoadCounts() {
		return carRoadCounts;
	}

	public void setCarRoadCounts(Integer carRoadCounts) {
		this.carRoadCounts = carRoadCounts;
	}

	public Double getQiyouP() {
		return qiyouP;
	}

	public void setQiyouP(Double qiyouP) {
		this.qiyouP = qiyouP;
	}

	public Double getCaiyouP() {
		return caiyouP;
	}

	public void setCaiyouP(Double caiyouP) {
		this.caiyouP = caiyouP;
	}

	public YesNoType getYnJktxx() {
		return ynJktxx;
	}

	public void setYnJktxx(YesNoType ynJktxx) {
		this.ynJktxx = ynJktxx;
	}

	public YesNoType getYnJktxxky() {
		return ynJktxxky;
	}

	public void setYnJktxxky(YesNoType ynJktxxky) {
		this.ynJktxxky = ynJktxxky;
	}

	public YesNoType getYnJkdlx() {
		return ynJkdlx;
	}

	public void setYnJkdlx(YesNoType ynJkdlx) {
		this.ynJkdlx = ynJkdlx;
	}

	public YesNoType getYnJkdlxky() {
		return ynJkdlxky;
	}

	public void setYnJkdlxky(YesNoType ynJkdlxky) {
		this.ynJkdlxky = ynJkdlxky;
	}

	public YesNoType getYnMyszgw() {
		return ynMyszgw;
	}

	public void setYnMyszgw(YesNoType ynMyszgw) {
		this.ynMyszgw = ynMyszgw;
	}

	public YesNoType getYnMyszgwky() {
		return ynMyszgwky;
	}

	public void setYnMyszgwky(YesNoType ynMyszgwky) {
		this.ynMyszgwky = ynMyszgwky;
	}

	public YesNoType getYnMyjygl() {
		return ynMyjygl;
	}

	public void setYnMyjygl(YesNoType ynMyjygl) {
		this.ynMyjygl = ynMyjygl;
	}

	public YesNoType getYnMyjyglky() {
		return ynMyjyglky;
	}

	public void setYnMyjyglky(YesNoType ynMyjyglky) {
		this.ynMyjyglky = ynMyjyglky;
	}

	public String getOtherSpec() {
		return otherSpec;
	}

	public void setOtherSpec(String otherSpec) {
		this.otherSpec = otherSpec;
	}

	public OilPosition getOilPosition() {
		return oilPosition;
	}

	public void setOilPosition(OilPosition oilPosition) {
		this.oilPosition = oilPosition;
	}

	public Ydpbxs getYdpbxs() {
		return ydpbxs;
	}

	public void setYdpbxs(Ydpbxs ydpbxs) {
		this.ydpbxs = ydpbxs;
	}

	public Dishi getDiShi() {
		return diShi;
	}

	public void setDiShi(Dishi diShi) {
		this.diShi = diShi;
	}

	public GongDian getGongDian() {
		return gongDian;
	}

	public void setGongDian(GongDian gongDian) {
		this.gongDian = gongDian;
	}

	public GongShui getGongShui() {
		return gongShui;
	}

	public void setGongShui(GongShui gongShui) {
		this.gongShui = gongShui;
	}

	public GongNuan getGongNuan() {
		return gongNuan;
	}

	public void setGongNuan(GongNuan gongNuan) {
		this.gongNuan = gongNuan;
	}

	public KeShiXing getKeShiXing() {
		return keShiXing;
	}

	public void setKeShiXing(KeShiXing keShiXing) {
		this.keShiXing = keShiXing;
	}

	public DiPing getDiPing() {
		return diPing;
	}

	public void setDiPing(DiPing diPing) {
		this.diPing = diPing;
	}

	public PaiWu getPaiWu() {
		return paiWu;
	}

	public void setPaiWu(PaiWu paiWu) {
		this.paiWu = paiWu;
	}

	public String getPct() {
		return pct;
	}

	public void setPct(String pct) {
		this.pct = pct;
	}


	public void setFilePath(int i, String path) {
		if (i == 1) {
			setFilePath1(path);
		} else if (i == 2) {
			setFilePath2(path);
		} else if (i == 3) {
			setFilePath3(path);
		} else if (i == 4) {
			setFilePath4(path);
		} else if (i == 5) {
			setFilePath5(path);
		} else if (i == 6) {
			setFilePath6(path);
		} else {
			setFilePath7(path);
		}
	}
	
	public String getFilePath(int i) {
		if (i == 1) {
			return getFilePath1();
		} else if (i == 2) {
			return getFilePath2();
		} else if (i == 3) {
			return getFilePath3();
		} else if (i == 4) {
			return getFilePath4();
		} else if (i == 5) {
			return getFilePath5();
		} else if (i == 6) {
			return getFilePath6();
		} else {
			return getFilePath7();
		}
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public YesNoType getYnFbzg() {
		return ynFbzg;
	}

	public void setYnFbzg(YesNoType ynFbzg) {
		this.ynFbzg = ynFbzg;
	}

	public YesNoType getYnGc() {
		return ynGc;
	}

	public void setYnGc(YesNoType ynGc) {
		this.ynGc = ynGc;
	}

	public String getEquipmentSpec() {
		return equipmentSpec;
	}

	public void setEquipmentSpec(String equipmentSpec) {
		this.equipmentSpec = equipmentSpec;
	}

	

	public String getYzzbzk() {
		return Strings.isEmpty(yzzbzk) ? "" : ResourceUtils.replaceString(yzzbzk);
	}

	public void setYzzbzk(String yzzbzk) {
		this.yzzbzk = yzzbzk;
	}

	public String getTdxz() {
		return tdxz;
	}

	public void setTdxz(String tdxz) {
		this.tdxz = tdxz;
	}

	public String getJzkzxtydhxjl() {
		return jzkzxtydhxjl;
	}

	public void setJzkzxtydhxjl(String jzkzxtydhxjl) {
		this.jzkzxtydhxjl = jzkzxtydhxjl;
	}

	public String getDlkzx() {
		return dlkzx;
	}

	public void setDlkzx(String dlkzx) {
		this.dlkzx = dlkzx;
	}

	public String getZsbh() {
		return zsbh;
	}

	public void setZsbh(String zsbh) {
		this.zsbh = zsbh;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getXkzbh() {
		return xkzbh;
	}

	public void setXkzbh(String xkzbh) {
		this.xkzbh = xkzbh;
	}

	public String getHgzbh() {
		return hgzbh;
	}

	public void setHgzbh(String hgzbh) {
		this.hgzbh = hgzbh;
	}

	public String getXkzzl() {
		return xkzzl;
	}

	public void setXkzzl(String xkzzl) {
		this.xkzzl = xkzzl;
	}

	public String getXkfw() {
		return xkfw;
	}

	public void setXkfw(String xkfw) {
		this.xkfw = xkfw;
	}

	public Date getYxqFrom() {
		return yxqFrom;
	}

	public void setYxqFrom(Date yxqFrom) {
		this.yxqFrom = yxqFrom;
	}

	public Date getYxqTo() {
		return yxqTo;
	}

	public void setYxqTo(Date yxqTo) {
		this.yxqTo = yxqTo;
	}

	public String getOthersm() {
		return Strings.isEmpty(othersm) ? "" : ResourceUtils.replaceString(othersm);
	}

	public void setOthersm(String othersm) {
		this.othersm = othersm;
	}

	public Integer getYggs() {
		return yggs;
	}

	public void setYggs(Integer yggs) {
		this.yggs = yggs;
	}

	public Integer getYjts() {
		return yjts;
	}

	public void setYjts(Integer yjts) {
		this.yjts = yjts;
	}

	@Override
	public String getDisplayString() {
		return getCode() + "-" + getName();
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public YesNoType getYnTdz() {
		return ynTdz;
	}

	public void setYnTdz(YesNoType ynTdz) {
		this.ynTdz = ynTdz;
	}

	public YesNoType getYnYdhx() {
		return ynYdhx;
	}

	public void setYnYdhx(YesNoType ynYdhx) {
		this.ynYdhx = ynYdhx;
	}

	public YesNoType getYnYzhgzs() {
		return ynYzhgzs;
	}

	public void setYnYzhgzs(YesNoType ynYzhgzs) {
		this.ynYzhgzs = ynYzhgzs;
	}

	public YesNoType getYnJyxkz() {
		return ynJyxkz;
	}

	public void setYnJyxkz(YesNoType ynJyxkz) {
		this.ynJyxkz = ynJyxkz;
	}

	public YesNoType getYnJchgz() {
		return ynJchgz;
	}

	public void setYnJchgz(YesNoType ynJchgz) {
		this.ynJchgz = ynJchgz;
	}

	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}
	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("pct", FormatUtils.stringValue(pct));
    	jo.put("address", FormatUtils.stringValue(address));
    	jo.put("oilLevel", FormatUtils.displayString(oilLevel));
    	jo.put("hzWay", FormatUtils.displayString(hzWay));
    	jo.put("gcCatagory", FormatUtils.displayString(gcCatagory));
    	jo.put("salesForecast", FormatUtils.doubleValue(salesForecast));
    	jo.put("qcyp", FormatUtils.stringValue(FormatUtils.doubleValue(qiyouP) + "/" + FormatUtils.doubleValue(caiyouP)));
    	return jo;
    }
}
