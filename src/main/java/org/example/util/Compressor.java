package org.example.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Compressor {
	public static byte[] compreess(byte[] data){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try(GZIPOutputStream g_out = new GZIPOutputStream(out)){
			g_out.write(data);
		}catch (IOException e){
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	public static byte[] decompress(byte[] data){
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		try(GZIPInputStream g_in = new GZIPInputStream(in)){
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[256];
			int len;
			while((len=g_in.read(buffer)) != -1){
				out.write(buffer,0,len);
			}
			return out.toByteArray();
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
