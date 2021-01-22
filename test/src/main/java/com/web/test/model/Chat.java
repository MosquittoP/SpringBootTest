package com.web.test.model;

public class Chat {
	
	private MessageType type;
	private String content, sender;
	
	public Chat() {}
	
	public Chat(MessageType type, String content, String sender) {
		this.type = type;
		this.content = content;
		this.sender = sender;
	}
	
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	@Override
	public String toString() {
		return "Chat [type=" + type + ", content=" + content + ", sender=" + sender + "]";
	}
}
