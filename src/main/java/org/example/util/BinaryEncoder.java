package org.example.util;

import org.example.model.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class BinaryEncoder {
	private static final Map<String, Byte> TAG_TO_TOKEN = Map.of(
			"login", (byte) 0x01,
			"msg", (byte) 0x02,
			"logout", (byte) 0x03,
			"sender", (byte) 0x04,
			"receiver", (byte) 0x05,
			"groupId", (byte) 0x06,
			"body", (byte) 0x07
	);

	public static byte[] encode(Message msg) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(getTagByte(msg.type));
		writeField(out, "sender", msg.sender);
		writeField(out, "receiver",msg.receiver);
		writeField(out, "groupId", msg.groupId);
		writeField(out, "body", msg.body);
		return out.toByteArray();
	}

	public static void writeField(ByteArrayOutputStream out, String tag, String value) throws IOException{
		if(value==null) return;
		byte tagByte = getTagByte(tag);
		byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);

		out.write(tagByte);
		out.write(valueBytes.length);
		out.write(valueBytes);
	}

	public static byte getTagByte(String tag){
		return TAG_TO_TOKEN.getOrDefault(tag.toLowerCase(), (byte)0x00);
	}
}
