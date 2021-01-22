package com.web.test.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.web.test.model.Chat;
import com.web.test.model.MessageType;

@Component
public class WebSocketEventListener {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
	
	@Autowired
	private SimpMessageSendingOperations template;
	
	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		logger.info("Received a new web socket connection");
	}
	
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String id = (String) headerAccessor.getSessionAttributes().get("id");
		if (id != null) {
			logger.info("User Disconnected : " + id);
			Chat chat = new Chat();
			chat.setType(MessageType.LEAVE);
			chat.setSender(id);
			template.convertAndSend("/topic/public", chat);
		}
	}
}
