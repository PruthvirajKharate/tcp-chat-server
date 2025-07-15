package org.example.services;

import org.example.SessionManager;
import org.example.model.EventResponse;
import org.example.model.EventType;
import org.example.model.Message;
import org.example.server.ClientHandler;

import java.io.IOException;

public class MessageService {
	public void handleMessage(Message message, ClientHandler clientHandler) throws IOException {
		if(message.sender==null || message.receiver==null || message.body==null){
			clientHandler.sendMessage(new EventResponse(EventType.ERROR,"Invalid Credentials"));
		}
		String sender = message.sender;
		String receiver = message.receiver;
		String body = message.body;
		if(!clientHandler.getUsername().equalsIgnoreCase(sender)){
			clientHandler.sendMessage(new EventResponse(EventType.ERROR, "Invalid User Name"));
			return;
		}
		ClientHandler receiverHandler = SessionManager.getHandler(receiver);
		if(receiverHandler==null){
			clientHandler.sendMessage(new EventResponse(EventType.USER_OFFLINE,"User "+receiver+" is offline"));
			return;
		}
		receiverHandler.sendMessage(new EventResponse(EventType.MESSAGE_RECEIVED, body));
		clientHandler.sendMessage(new EventResponse(EventType.MESSAGE_DELIVERED, "Message sent successfully"));

	}
}
