package dbm;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import hotel.Service;

public class ServiceManager {

	private ArrayList<Service> services;
	private Document serviceDoc;
	
	public ServiceManager() 
			throws SAXException, IOException, ParserConfigurationException, ParseException {
		
		this.serviceDoc = XMLUtil.getDocument(DBConfig.serviceDir);
		this.services = loadServiceList(this.serviceDoc);
	}
	
	public ArrayList<Service> getServices() {
		return this.services;
	}
	
	public Document getDoc() {
		return this.serviceDoc;
	}
	
	public Service getServiceById(UUID serviceId) {
		for (Service s : this.services) {
			if (s.getId().equals(serviceId)) {
				return s;
			}
		}
		return null;
	}
	
	public ArrayList<Service> loadServiceList(Document doc) throws ParseException {
		
		ArrayList<Service> arr = new ArrayList<Service>();
		NodeList services = doc.getElementsByTagName(DBConfig.serviceTag);
		for(int i = 0; i < services.getLength(); i++) {
			Element ele = (Element)services.item(i); 
			UUID id = UUID.fromString(XMLUtil.getAttrVal(ele, DBConfig.serviceIdAttr));
			String type = XMLUtil.getTagVal(ele, DBConfig.serviceTypeTag);
			Boolean isRoom = XMLUtil.getTagVal(ele, DBConfig.serviceIsRoomTag).equalsIgnoreCase("true") ? true : false;
			int amount = Integer.parseInt(XMLUtil.getTagVal(ele, DBConfig.serviceAmountTag));
			double price = Double.parseDouble(XMLUtil.getTagVal(ele, DBConfig.servicePriceTag));
			String description = XMLUtil.getTagVal(ele, DBConfig.serviceDescriptionTag);
			Service service = new Service(id, type, isRoom, amount, price, description);
			arr.add(service);
		}
		return arr;
	}
}
