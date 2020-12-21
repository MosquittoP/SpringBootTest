package com.web.test.model;

public class Email {
	
	private String subject, content, receiver;

	public Email() {}

	public Email(String subject, String content, String receiver) {
		this.subject = subject;
		this.content = content;
		this.receiver = receiver;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Override
	public String toString() {
		return "Email [subject=" + subject + ", content=" + content + ", receiver=" + receiver + "]";
	}
	
}
