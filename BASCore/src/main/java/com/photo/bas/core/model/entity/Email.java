package com.photo.bas.core.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(schema = "public")
public class Email extends AbsEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6427538371043465577L;
	
	@Column(name = "to_address")
	private String to;
	@Column(name = "cc")
	private String cc;
	@Column(name = "bcc")
	private String bcc;
	@Column(name = "type")
	private String type;
	@Column(name = "subject")
	private String subject;
	@Column(name = "body")
	private String body;
	@Column(name = "send_date")
	private Date sendDate;
	
	@Column(name = "file_name")
	private String fileName;
	@Transient
	private String filePath;
	@Transient
	private String documentId;
	@Transient
	private String documentType;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	@Override
	public String getDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}
}
