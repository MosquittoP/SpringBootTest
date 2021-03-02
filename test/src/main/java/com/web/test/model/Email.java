package com.web.test.model;

public class Email {
	
	private int emailno;
	private String subject, content, receiver;
	private boolean open;

	public Email() {}

	public Email(int emailno, String subject, String content, String receiver) {
		this.emailno = emailno;
		this.subject = subject;
		this.content = content;
		this.receiver = receiver;
		this.open = false;
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

	public int getEmailno() {
		return emailno;
	}

	public void setEmailno(int emailno) {
		this.emailno = emailno;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public String toString() {
		return "Email [emailno=" + emailno + ", subject=" + subject + ", content=" + content + ", receiver=" + receiver
				+ ", open=" + open + "]";
	}
	
}
