package org.example;

import org.example.model.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class XMLMessageParser {
	public static Message parse(String xmlString){
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xmlString)));

			Element root = document.getDocumentElement();
			String type = root.getTagName();

			String sender = getTagValue("sender", root);
			String receiver = getTagValue("receiver", root);
			String groupId = getTagValue("groupId", root);
			String body = getTagValue("body", root);

			return  new Message(type, sender, receiver, groupId, body);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}

	}

	private static String getTagValue(String tag, Element element){
		NodeList nodeList = element.getElementsByTagName(tag);
		if(nodeList.getLength()==0) return  null;
		return nodeList.item(0).getTextContent();
	}

	public static String toXml(Message message){
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(message.type).append(">");
		if(message.sender!=null){
			sb.append("<sender>").append(message.sender).append("</sender>");
		}
		if(message.receiver!=null){
			sb.append("<receiver>").append(message.receiver).append("</receiver>");
		}
		if(message.groupId!=null){
			sb.append("<groupId>").append(message.groupId).append("</groupId>");
		}
		if(message.body!=null){
			sb.append("<body>").append(message.body).append("</body>");
		}
		return sb.toString();
	}
}
