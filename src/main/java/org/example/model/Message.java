package org.example.model;

public class Message {
	public String type;
	public String sender;
	public String receiver;
	public String groupId;
	public String body;

	public Message() {
	}

	public Message(String type, String sender, String receiver, String groupId, String body) {
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
		this.groupId = groupId;
		this.body = body;
	}
}
