package dbm;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import hotel.Booking;

public class BookingManager {
	
	private ArrayList<Booking> bookings;
	private Document bookingDoc;
	
	public BookingManager() 
			throws SAXException, IOException, ParserConfigurationException, ParseException {
		
		this.bookingDoc = XMLUtil.getDocument(DBConfig.bookingDir);
		this.bookings = loadBookingList(this.bookingDoc);
	}
	
	public ArrayList<Booking> getBookings() {
		return this.bookings;
	}
	
	public ArrayList<Booking> getBookingsByService(UUID serviceId, ArrayList<Booking> bl) {
		ArrayList<Booking> arr = new ArrayList<Booking>();
		for (Booking b : bl) {
			if (b.getServiceId().equals(serviceId)) {
				arr.add(b);
			}
		}
		return arr;
	}
	
	public ArrayList<Booking> filterBookingsByDates(Date start, Date end, ArrayList<Booking> bl) {
		ArrayList<Booking> arr = new ArrayList<Booking>();
		for (Booking b : bl) {
			if (!(b.getDate().before(start) || b.getDate().after(end))) {
				arr.add(b);
			}
		}
		return arr;
	}
	
	public Document getDoc() {
		return this.bookingDoc;
	}
	
	public void createBookings(ArrayList<Booking> bookings) throws TransformerException {
		
		Element root = this.bookingDoc.getDocumentElement();
		for (int i = 0; i < bookings.size(); i++) {
			Booking b = bookings.get(i);
			
			Element booking = this.bookingDoc.createElement(DBConfig.bookingTag);
			booking.setAttribute(DBConfig.bookingIdAttr, b.getId().toString());
			
			Element bookingId = this.bookingDoc.createElement(DBConfig.bookingIdTag);
			Element serviceId = this.bookingDoc.createElement(DBConfig.bookingServiceIdTag);
			Element date = this.bookingDoc.createElement(DBConfig.bookingDateTag);
			Element email = this.bookingDoc.createElement(DBConfig.bookingEmailTag);
			bookingId.appendChild(this.bookingDoc.createTextNode(b.getBookingId().toString()));
			serviceId.appendChild(this.bookingDoc.createTextNode(b.getServiceId().toString()));
			date.appendChild(this.bookingDoc.createTextNode(XMLUtil.dateToString(b.getDate())));
			email.appendChild(this.bookingDoc.createTextNode(b.getEmail()));
			
			booking.appendChild(bookingId);
			booking.appendChild(serviceId);
			booking.appendChild(date);
			booking.appendChild(email);
			
			root.appendChild(booking);
		}
		XMLUtil.saveXML(this.bookingDoc, DBConfig.bookingDir);
	}


	public ArrayList<Booking> loadBookingList(Document doc) throws ParseException {
		
		ArrayList<Booking> arr = new ArrayList<Booking>();
		NodeList bookings = doc.getElementsByTagName(DBConfig.bookingTag);
		for(int i = 0; i < bookings.getLength(); i++) {
			Element ele = (Element)bookings.item(i); 
			UUID id = UUID.fromString(XMLUtil.getAttrVal(ele, DBConfig.bookingIdAttr));
			UUID bookingId = UUID.fromString(XMLUtil.getTagVal(ele, DBConfig.bookingIdTag));
			UUID serviceId = UUID.fromString(XMLUtil.getTagVal(ele, DBConfig.bookingServiceIdTag));
			Date date = XMLUtil.stringToDate(XMLUtil.getTagVal(ele, DBConfig.bookingDateTag));
			String email = XMLUtil.getTagVal(ele, DBConfig.bookingEmailTag);
			Booking booking = new Booking(id, bookingId, serviceId, date, email);
			arr.add(booking);
		}
		return arr;
	}
}
