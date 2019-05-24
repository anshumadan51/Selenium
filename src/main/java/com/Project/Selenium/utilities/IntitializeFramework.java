package com.Project.Selenium.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class IntitializeFramework {
	
	
	Properties pr = new Properties();
	
	public String getExcelProperties(String nameOfElement) throws IOException {
		 String getExcelProperty = null;
		File file = new File("src/main/resources/Excel.properties");
		FileInputStream fileinput = new FileInputStream(file);
		pr.load(fileinput);
		fileinput.close();
		getExcelProperty = pr.getProperty(nameOfElement);
		return getExcelProperty;
	}

	public String getConfigurationProperties(String nameOfElement) throws IOException {
		 String getExcelProperty = null;
		File file = new File("src/main/resources/Configurations.properties");
		FileInputStream fileinput = new FileInputStream(file);
		pr.load(fileinput);
		fileinput.close();
		getExcelProperty = pr.getProperty(nameOfElement);
		return getExcelProperty;
	}
	
	public HashMap<String, String> getXMLContent(String tagName) {
		HashMap<String, String> xmlTag = new HashMap<String, String>();
		try {
			String ORXML = "src/main/resources/ObjectRepository.xml";
			
			File xmlFile = new File(ORXML);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			// DocumentBuilder dBuilder =
			// DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList AccessibilityIdNode = (NodeList) (xpath.compile("//" + tagName + "/AccessibilityId/text()")
					.evaluate(doc, XPathConstants.NODESET));
			NodeList IdNode = (NodeList) (xpath.compile("//" + tagName + "/Id/text()").evaluate(doc,
					XPathConstants.NODESET));
			NodeList IndexNode = (NodeList) (xpath.compile("//" + tagName + "/Index/text()").evaluate(doc,
					XPathConstants.NODESET));
			NodeList XPathNode = (NodeList) (xpath.compile("//" + tagName + "/Xpath/text()").evaluate(doc,
					XPathConstants.NODESET));
			NodeList LabelNode = (NodeList) (xpath.compile("//" + tagName + "/Label/text()").evaluate(doc,
					XPathConstants.NODESET));

			if (AccessibilityIdNode.getLength() > 0) {
				xmlTag.put("AccessibilityId", AccessibilityIdNode.item(0).getTextContent());
			}
			
			if (IdNode.getLength() > 0) {
				xmlTag.put("Id", IdNode.item(0).getTextContent());
			}
			
			if (IndexNode.getLength() > 0) {
				xmlTag.put("Index", IndexNode.item(0).getTextContent());
			}

			if (XPathNode.getLength() > 0) {
				xmlTag.put("Xpath", XPathNode.item(0).getTextContent());
			}
			if (LabelNode.getLength() > 0) {
				xmlTag.put("Label", LabelNode.item(0).getTextContent());
			}
			return xmlTag;
		} catch (Exception ex) {

		}
		return xmlTag;

	}

	
	
}
