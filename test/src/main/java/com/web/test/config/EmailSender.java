package com.web.test.config;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.web.test.model.Email;

@Service
public class EmailSender {
	
	@Autowired
	JavaMailSender mailSender;
	
	public void sendEmail(Email email) {
		MimeMessage msg = mailSender.createMimeMessage();
		try {
			msg.setSubject(email.getSubject());
			msg.setText(email.getContent());
			msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email.getReceiver()));
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		mailSender.send(msg);
	}
}
