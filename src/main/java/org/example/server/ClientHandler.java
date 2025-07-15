package org.example.server;



import org.example.*;
import org.example.model.EventResponse;
import org.example.model.EventType;
import org.example.model.Message;
import org.example.services.AuthService;
import org.example.services.MessageService;
import org.example.util.BinaryDecoder;
import org.example.util.BinaryEncoder;
import org.example.util.Compressor;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
	private final Socket socket;
	private String username;
	private boolean running = true;
	private DataOutputStream dataOutput ;
	private static final AuthService authService = new AuthService();
	private static final MessageService messageService = new MessageService();

	public ClientHandler(Socket socket){
		this.socket = socket;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRunning(boolean running){
		this.running = running;
	}

	public  void sendMessage(EventResponse response) throws IOException{
		Message msg = new Message();
		msg.type="event";
		msg.body=response.toString();

		byte[] encoded = BinaryEncoder.encode(msg);
		byte[] compressed = Compressor.compreess(encoded);
		dataOutput.writeShort(compressed.length);
		dataOutput.write(compressed);
		dataOutput.flush();
	}

	public void cleanup() throws IOException{
		SessionManager.unregister(username);
		socket.close();
	}

	@Override
	public void run(){
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			dataOutput = new DataOutputStream(socket.getOutputStream());

			while(running){
				int msgLen = in.readUnsignedShort();
				byte[] compressed = in.readNBytes(msgLen);
				byte[] raw = Compressor.decompress(compressed);
				Message msg = BinaryDecoder.decode(raw);

				switch (msg.type.toLowerCase()){
					case "login" -> authService.handleLogin(msg, this);
					case "msg" -> messageService.handleMessage(msg, this);
					case "logout" -> authService.handleLogout(msg, this);
					default -> {
						sendMessage(new EventResponse(EventType.ERROR, "Undefined type of message or message corrupted"));
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				cleanup();
			}catch (Exception e){
				e.printStackTrace();
			}
		}

	}

}
