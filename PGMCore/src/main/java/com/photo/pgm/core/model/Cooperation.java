/**
 * 
 */
package com.photo.pgm.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.json.JSONObject;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.utils.FormatUtils;

/**
 * @author FengYu
 *
 */
@Entity
@Table(schema = "project")
@Audited
public class Cooperation extends AbsCodeNameEntity {

	private static final long serialVersionUID = 6556609908321065125L;

	private String corporate; // 法人
	
	@Column(columnDefinition = "text")
	private String address; // 公司地址
	
	private String phone; // 联系电话
	private String email; // 电子邮件
	
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private CooperationType cooperationType; // 供应商类型
	
//	@Enumerated(EnumType.STRING)
//	private CooperationStatus status = CooperationStatus.New; // 状态
	
	private String filePath1; // 营业执照存储路径
	private String filePath2; // 税务登记证附件
	private String filePath3; // 组织机构代码证附件
	private String filePath4; // 资质等级文件附件
	private String filePath5; // 授权证明附件
	private String filePath6; // 其他附件
	private String filePath7; // 变更审批表附件

	@Override
	public String getSavePermission() {
		return null;
	}

	@Override
	public String getDeletePermission() {
		return null;
	}

	@Override
	public String getDisplayString() {
		return getName();
	}
	
	public String getCorporate() {
		return corporate;
	}

	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CooperationType getCooperationType() {
		return cooperationType;
	}

	public void setCooperationType(CooperationType cooperationType) {
		this.cooperationType = cooperationType;
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
	
	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("corporate", FormatUtils.stringValue(corporate));
    	jo.put("filePath1", FormatUtils.stringValue(filePath1));
    	jo.put("address", FormatUtils.stringValue(address));
    	jo.put("phone", FormatUtils.stringValue(phone));
    	jo.put("email", FormatUtils.stringValue(email));
    	return jo;
    }

//	@Override
//	public String getSequenceName() {
//		return COOPERATION_SEQUENCE + "_" + getCorporation().getId();
//	}
//
//	@Override
//	public String getPrefix() {
//		return COOPERATION;
//	}
//
//	@Override
//	public int getSequenceLength() {
//		return 6;
//	}
//
//	@Override
//	public String getSequenceNameSuffix() {
//		return null;
//	}
//
//	@Override
//	public List<String> getOtherDiscriminatorNames() {
//		return null;
//	}
//
//	@Override
//	public List<Object> getOtherDiscriminatorValues() {
//		return null;
//	}
}
