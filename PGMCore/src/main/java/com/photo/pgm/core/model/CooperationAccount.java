/**
 * 
 */
package com.photo.pgm.core.model;

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
public class CooperationAccount extends AbsCodeNameEntity {

	private static final long serialVersionUID = 7242896272595773855L;

	@ManyToOne
	@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
	private Cooperation cooperation;
	
	private String receiveNo; // 收款账号
	private String receiveName; // 收款人
	private String bank; // 开户银行
	private String filePath; // 附件路径
	private String fileName; // 附件名字
	private String fileAllPath; // 附件绝对路径
	
	
	@Override
	public JSONObject toJSONObject() {
    	JSONObject jo = super.toJSONObject();
    	jo.put("receiveNo", FormatUtils.stringValue(receiveNo));
    	jo.put("receiveName", FormatUtils.stringValue(receiveName));
    	jo.put("bank", FormatUtils.stringValue(bank));
    	jo.put("fileName", FormatUtils.stringValue(fileName));
    	jo.put("filePath", FormatUtils.stringValue(fileName));
    	return jo;
    }
	
	public Cooperation getCooperation() {
		return cooperation;
	}

	public void setCooperation(Cooperation cooperation) {
		this.cooperation = cooperation;
	}

	public String getReceiveNo() {
		return receiveNo;
	}

	public void setReceiveNo(String receiveNo) {
		this.receiveNo = receiveNo;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileAllPath() {
		return fileAllPath;
	}

	public void setFileAllPath(String fileAllPath) {
		this.fileAllPath = fileAllPath;
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
