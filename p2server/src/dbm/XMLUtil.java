package dbm;

import hotel.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.io.File;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

public class XMLUtil {
	
	public static Document getDocument(String docpath) 
			throws SAXException, IOException, ParserConfigurationException {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(docpath);	
		return doc;
	}
	
	public static void addElement(Document doc, Element ele, Node parent) 
			throws SAXException, IOException, ParserConfigurationException, TransformerException {

		if (doc == null) {
			logXML("Document not found.");
			return;
		}
		if (parent == null) {
			logXML("Parent node not found.");
			return;
		}
		if (ele == null) {
			logXML("Element not found.");
			return;
		}
		parent.appendChild(ele);
		//saveXML(doc);
	}
	
	public static String getAttrVal(Element ele, String attr) {
		
		NamedNodeMap attrs = ele.getAttributes();
		Node nodeAttr = attrs.getNamedItem(attr);
		return nodeAttr.getTextContent();
	}
	
	public static String getTagVal(Element ele, String tag) {
		
		NodeList list = ele.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeName().equals(tag)) {
				return list.item(i).getTextContent();
			}
		}
		return null;
	}
	
	public static Booking elementToBooking(Element ele) throws ParseException {

		UUID id = UUID.fromString(XMLUtil.getAttrVal(ele, DBConfig.bookingIdAttr));
		UUID bookingId = UUID.fromString(XMLUtil.getTagVal(ele, DBConfig.bookingIdTag));
		UUID serviceId = UUID.fromString(XMLUtil.getTagVal(ele, DBConfig.bookingIdTag));
		Date date = XMLUtil.stringToDate(XMLUtil.getTagVal(ele, DBConfig.bookingDateTag));
		String email = XMLUtil.getTagVal(ele, DBConfig.bookingEmailTag);
		return new Booking(id, bookingId, serviceId, date, email);
	}
	
	public static Service elementToService(Element ele) throws ParseException {

		UUID id = UUID.fromString(XMLUtil.getAttrVal(ele, DBConfig.bookingIdAttr));
		String type = XMLUtil.getTagVal(ele, DBConfig.serviceTypeTag);
		Boolean isRoom = XMLUtil.getTagVal(ele, DBConfig.serviceIsRoomTag).equalsIgnoreCase("true") ? true : false;
		int amount = Integer.parseInt(XMLUtil.getTagVal(ele, DBConfig.serviceAmountTag));
		float price = Float.parseFloat(XMLUtil.getTagVal(ele, DBConfig.servicePriceTag));
		String description = XMLUtil.getTagVal(ele, DBConfig.serviceDescriptionTag);
		return new Service(id, type, isRoom, amount, price, description);
	}
	
	public static Date stringToDate(String dateStr) throws ParseException {

		DateFormat formatter = new SimpleDateFormat(DBConfig.dateFormat);
		Date date = formatter.parse(dateStr);
		return date;
	}
	
	public static String dateToString(Date date) {
		
		DateFormat formatter = new SimpleDateFormat(DBConfig.dateFormat);
		return formatter.format(date);
	}
	
	public static void saveXML(Document doc, String docpath) 
			throws TransformerException {
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(docpath));
		transformer.transform(source, result);
	}
	
	public static void logXML(String msg) {
		
		System.out.println("XML Log: " + msg);
	}
}
