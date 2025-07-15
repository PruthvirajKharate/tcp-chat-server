package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Sever {
	final private int port;
	final private ServerSocket serverSocket ;
	boolean running = true;
	public Sever() throws IOException {
		this.port = 5000;
		this.serverSocket = new ServerSocket(5000);
		System.out.println("Server started at port "+port);
	}
	private void connect(){
		try{
			while(running){
				Socket clientSocket = serverSocket.accept();
				System.out.println("New socket connected at"+clientSocket);
				new Thread(new ClientHandler(clientSocket)).start();

			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		try{
			Sever sever = new Sever();
			sever.connect();
		}catch (IOException e){
			e.printStackTrace();
		}

	}
}
