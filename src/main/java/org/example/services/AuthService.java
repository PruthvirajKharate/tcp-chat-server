package org.example.services;

import org.example.SessionManager;
import org.example.model.EventResponse;
import org.example.model.EventType;
import org.example.model.Message;
import org.example.server.ClientHandler;

import java.io.IOException;

public class AuthService {

	public void handleLogin(Message message, ClientHandler client) throws IOException {
		if(message.sender==null){
			client.sendMessage(new EventResponse(EventType.LOGIN_FAILED, "Username is not provided"));
			return;
		}
		client.setUsername(message.sender);
		SessionManager.register(client.getUsername(), client);
		System.out.println("User "+message.sender+" connected");
		client.sendMessage(new EventResponse(EventType.LOGIN_SUCCESS,"Welcome "+message.sender));
	}

	public void handleLogout(Message message, ClientHandler client) throws IOException{
		if(message.sender==null || !message.sender.equalsIgnoreCase(client.getUsername())){
			client.sendMessage(new EventResponse(EventType.ERROR, "Invalid logout credentials"));
			return;
		}
		SessionManager.unregister(client.getUsername());
		System.out.println("User "+message.sender+" disconnected");
		client.sendMessage(new EventResponse(EventType.LOGOUT_SUCCESS,"User logged out successfully"));
		client.setRunning(false);
	}
}
