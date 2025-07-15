package org.example.model;

public class User {
	public String username;
	public String passwordHash;

	public User(String username, String passwordHash) {
		this.username = username;
		this.passwordHash = passwordHash;
	}
}
