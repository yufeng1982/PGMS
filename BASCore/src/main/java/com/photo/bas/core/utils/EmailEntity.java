package com.photo.bas.core.utils;

import java.io.File;
import java.util.List;

import org.json.JSONObject;

import com.photo.bas.core.model.security.User;

public class EmailEntity {
	
	private User emailToUser;
	private String subject;
	private String templateName;
	private String emailAddress;
	private JSONObject parameters;
	private String content;
	private String attachmentFileName;
	private File attachmentFile;
	private String replyTo;
	private List<User> approveUsers;
	public EmailEntity () {
		super();
	}
	public User getEmailToUser() {
		return emailToUser;
	}
	public void setEmailToUser(User emailToUser) {
		this.emailToUser = emailToUser;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getEmailAddress() {
		if(emailAddress == null && emailToUser != null) return emailToUser.getEmail();
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public JSONObject getParameters() {
		return parameters;
	}
	public void setParameters(JSONObject parameters) {
		this.parameters = parameters;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAttachmentFileName() {
		return attachmentFileName;
	}
	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}
	public File getAttachmentFile() {
		return attachmentFile;
	}
	public void setAttachmentFile(File attachmentFile) {
		this.attachmentFile = attachmentFile;
	}
	public String getReplyTo() {
		return replyTo;
	}
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	public List<User> getApproveUsers() {
		return approveUsers;
	}
	public void setApproveUsers(List<User> approveUsers) {
		this.approveUsers = approveUsers;
	}
	
}
