package org.example;

import org.example.server.ClientHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
	private static final Map<String, ClientHandler> onlineUsers = new ConcurrentHashMap<>();

	public static void register(String username, ClientHandler handler){
		onlineUsers.put(username, handler);
	}

	public static ClientHandler getHandler(String username){
		return onlineUsers.get(username);
	}

	public static void unregister(String username){
		onlineUsers.remove(username);
	}
}


