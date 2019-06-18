/**
 * 
 */
package com.photo.bas.core.model.crm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jasypt.hibernate4.type.EncryptedStringType;
import org.json.JSONObject;

import com.photo.bas.core.model.common.Gender;
import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.model.entity.ISerial;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@TypeDef(name="commonEncryptedString", typeClass=EncryptedStringType.class, parameters= {@Parameter(name="encryptorRegisteredName", value="commonStringEncryptor")})

@Entity
@Table(schema = "crm")
public class Customer extends AbsCodeNameEntity implements ISerial {

	private static final long serialVersionUID = -3004689983893882359L;
	
	private String phone; // 电话(唯一)
	private String netWorkContact; // 网络联系方式
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp with time zone")
	private Date birthday; // 生日
	
	@Type(type = "true_false")
	private Boolean vip = Boolean.FALSE; // 是否vip会员
	
	@Enumerated(EnumType.STRING)
	private Gender gender; // 性别
	
	private Integer screenCount = new Integer(0); // 拍摄次数
	
	private Double consumptionAmount = new Double(0); // 消费金额
	
	@Type(type="commonEncryptedString")
	private String email;
	
	private String shortName; // 昵称
	
	private String receiveAddress; // 收件人地址
	
	private String receiveName; // 收件人
	
	private String appellation; // 称呼
	
	private String sinaWeiBoAdr; // 新浪微博地址
	
	public Customer() {
		super();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNetWorkContact() {
		return netWorkContact;
	}

	public void setNetWorkContact(String netWorkContact) {
		this.netWorkContact = netWorkContact;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Integer getScreenCount() {
		return screenCount;
	}

	public void setScreenCount(Integer screenCount) {
		this.screenCount = screenCount;
	}

	public Double getConsumptionAmount() {
		return consumptionAmount;
	}

	public void setConsumptionAmount(Double consumptionAmount) {
		this.consumptionAmount = consumptionAmount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getAppellation() {
		return appellation;
	}

	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}

	public String getSinaWeiBoAdr() {
		return sinaWeiBoAdr;
	}

	public void setSinaWeiBoAdr(String sinaWeiBoAdr) {
		this.sinaWeiBoAdr = sinaWeiBoAdr;
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
    	jo.put("phone", FormatUtils.stringValue(phone));
    	jo.put("netWorkContact", FormatUtils.stringValue(netWorkContact));
    	jo.put("gender", FormatUtils.displayString(gender));
    	jo.put("birthday", FormatUtils.dateValue(birthday));
    	jo.put("vip", FormatUtils.booleanValue(vip));
    	jo.put("screenCount", FormatUtils.intValue(screenCount));
    	jo.put("consumptionAmount", FormatUtils.doubleValue(consumptionAmount));
    	return jo;
    }
	
	@Override
	public String getDisplayString(){
		return getName();
	}
	
	@Override
	public String getSequenceName() {
		return CUSTOMER_SEQUENCE + "_" + getCorporation().getId();
	}

	@Override
	public String getPrefix() {
		return CUSTOMER;
	}

	@Override
	public int getSequenceLength() {
		return 6;
	}

	@Override
	public String getSequenceNameSuffix() {
		return null;
	}

	@Override
	public List<String> getOtherDiscriminatorNames() {
		return null;
	}

	@Override
	public List<Object> getOtherDiscriminatorValues() {
		return null;
	}
}
