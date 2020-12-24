package com.web.test.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.web.test.model.Chat;

@Controller
public class ChatController {
	
	@MessageMapping("chat.sendMessage")
	@SendTo("/topic/public")
	public Chat sendMessage(@Payload Chat chat) {
		return chat;
	}
	
	@MessageMapping("chat.addUser")
	@SendTo("/topic/public")
	public Chat addUser(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("id", chat.getSender());
		return chat;
	}
	
}
