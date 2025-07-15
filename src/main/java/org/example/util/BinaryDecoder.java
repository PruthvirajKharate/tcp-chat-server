package org.example.util;

import org.example.model.Message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class BinaryDecoder {
	private static final Map<Byte, String> TOKEN_TO_TAG  = Map.of(
			(byte) 0x01, "login",
			(byte) 0x02, "msg",
			(byte) 0x03, "logout",
			(byte) 0x04, "sender",
			(byte) 0x05, "receiver",
			(byte) 0x06, "groupId",
			(byte) 0x07, "body"
	);

	public static Message decode(byte[] input) throws IOException{
		ByteArrayInputStream in = new ByteArrayInputStream(input);
		Message message = new Message();

		message.type = TOKEN_TO_TAG.getOrDefault((byte)in.read(), null);

		while(in.available()>0){
			String field = TOKEN_TO_TAG.get((byte) in.read());
			if(field==null){
				continue;
			}
			int length = in.read();
			byte[] valueBytes = in.readNBytes(length);
			String value = new String(valueBytes, StandardCharsets.UTF_8);
			assignValue(message, field, value);
		}
  		return message;
	}

	public static void assignValue(Message msg, String tag, String value){
		switch(tag){
			case "sender" -> msg.sender = value;
			case "receiver" -> msg.receiver=value;
			case "groupId" -> msg.groupId=value;
			case "body" -> msg.body=value;
		}
	}

}
