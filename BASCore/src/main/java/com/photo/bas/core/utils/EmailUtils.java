package com.photo.bas.core.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.photo.bas.core.model.security.User;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@SuppressWarnings("unused")
public class EmailUtils {
	protected Logger log = LoggerFactory.getLogger(getClass());
	private static final String DEFAULT_ENCODING = "utf-8";
	
	private JavaMailSender javaMailSender;
	
	private SimpleMailMessage message;
	
	private MimeMessage mimeMessage;

	private VelocityEngine velocityEngine;
	
	private Configuration freemarkerConfiguration;
	
	private String from;
	
	public void sendEmail4ApproveUsers(EmailEntity emaliEntity) {
		this.sendMail2ApproveUsers(emaliEntity.getApproveUsers());
	}
	
	public void sendMail(User user, String templetName, String mailType) {
		try {
			Map<String, String> context = new HashMap<>();
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			helper.setSubject(ResourceUtils.getText("Com.Mail.Subject." + mailType));
			helper.setTo(user.getEmail());
			helper.setFrom(from);
			if(mailType.endsWith("Register")){
				context.put("longintitle", ResourceUtils.getText("Com.Mail.Subject.GetLoginInfo"));
				context.put("username", ResourceUtils.getText("Com.Mail.Subject.UserName"));
				context.put("userpassword", ResourceUtils.getText("Com.Mail.Subject.Password"));
				context.put("name", user.getLoginName());
				context.put("password", user.getPlainPassword());
			} else if (mailType.endsWith("Active") || mailType.endsWith("ResetPassword")) {
				context.put("validationCode", user.getEntryptValidationCode());
				context.put("siteurl", ResourceUtils.getAppSetting("site.url"));
				context.put("forgetpassword", ResourceUtils.getText("Com.Mail.Subject.ForgotPassword"));
				context.put("forgetpasswordinfo", ResourceUtils.getText("Com.Mail.Subject.ForgotPasswordInfo"));
			} 
			String content = generateContent(user, templetName, context);
			helper.setText(content, true);
			javaMailSender.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMail2ApproveUsers(List<User> users) {
		if (users == null || users.isEmpty()) return;
		String emails = "";
		for (User user : users) {
			if (Strings.isEmpty(emails)) {
				emails = user.getEmail();
			} else {
				emails += "," + user.getEmail();
			}
		}
		Map<String, String> context = new HashMap<>();
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			helper.setSubject(ResourceUtils.getText("Com.ApporveMail.Subject"));
			helper.setTo(emails.split(","));
			helper.setFrom(from);
			context.put("approveTitle", ResourceUtils.getText("Com.ApporveMail.Subject"));
			context.put("approveMsg", ResourceUtils.getText("Com.ApporveMail.Msg"));
			String content = generateContent(null, "approveMailTemplate.ftl", context);
			helper.setText(content, true);
			javaMailSender.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	private String generateContent(User user, String mailTemplate, Map<String, String> context) throws MessagingException {
		try {
			return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(mailTemplate) , context);
		} catch (IOException e) {
			throw new MessagingException("FreeMarker Template missing", e);
		} catch (TemplateException e) {
			throw new MessagingException("FreeMarker Template is bad", e);
		}
	}
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setMessage(SimpleMailMessage message) {
		this.message = message;
	}
	
	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public static void main(String[] args){
		
	}

	public MimeMessage getMimeMessage() {
		return mimeMessage;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Configuration getFreemarkerConfiguration() {
		return freemarkerConfiguration;
	}

	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
	}
}
