package org.example;

import org.example.model.Message;
import org.example.util.BinaryEncoder;
import org.example.util.Compressor;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TestClient2 {
	public static void main(String[] args) {
		String host = "localhost";
		int port = 5000;

		try (Socket socket = new Socket(host, port);
			 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			 DataInputStream in = new DataInputStream(socket.getInputStream())) {

			Scanner scanner = new Scanner(System.in);

			// Login with different username
			Message loginMsg = new Message();
			loginMsg.type = "login";
			loginMsg.sender = "user2";

			sendMessage(loginMsg, out);

			System.out.println("Sent login message. Waiting for response...");
			readResponse(in);

			// Interactive message loop
			while (true) {
				System.out.print("Enter command (msg|receiver|message OR logout|user2): ");
				String input = scanner.nextLine();
				if (input.trim().isEmpty()) continue;

				String[] parts = input.split("\\|");
				Message msg = new Message();
				msg.type = parts[0];

				if (msg.type.equalsIgnoreCase("msg") && parts.length == 3) {
					msg.sender = "user2";
					msg.receiver = parts[1];
					msg.body = parts[2];
				} else if (msg.type.equalsIgnoreCase("logout") && parts.length == 2) {
					msg.sender = parts[1];
				} else {
					System.out.println("Invalid format.");
					continue;
				}

				sendMessage(msg, out);
				readResponse(in);

				if (msg.type.equalsIgnoreCase("logout")) break;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void sendMessage(Message msg, DataOutputStream out) throws IOException {
		byte[] encoded = BinaryEncoder.encode(msg);
		byte[] compressed = Compressor.compreess(encoded);
		out.writeShort(compressed.length);
		out.write(compressed);
		out.flush();
	}

	private static void readResponse(DataInputStream in) throws IOException {
		int len = in.readUnsignedShort();
		byte[] compressed = in.readNBytes(len);
		byte[] decompressed = Compressor.decompress(compressed);
		String result = new String(decompressed, StandardCharsets.UTF_8);
		System.out.println("Received response: " + result);
	}
}
