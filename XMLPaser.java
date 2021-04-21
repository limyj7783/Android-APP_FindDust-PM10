package com.example.finedustpm10.finedustpm10;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//XML �Ľ�
public class XMLPaser {
	
	private String element;
	private String xml;

	public String Parsing(){
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
		
			InputStream is = new ByteArrayInputStream(xml.getBytes());
			
			Document doc = db.parse(is);
			Element ele = doc.getDocumentElement();
			NodeList list = ele.getElementsByTagName(element);

			Node item = list.item(0);
			Node text = item.getFirstChild();
			String value=text.getNodeValue();
			return value;
		}catch(Exception e){
			return e.toString();
		}

	}
	
	public void SetElement(String element){
		this.element = element;
	}
	public String GetElement(){
		return element;
	}

	
	public void SetXML(String xml){
		this.xml = xml;
	}

	public String GetXML(){
		return xml;
	}


	public void PrintXML(){
//		System.out.println(xml);
	}
}
