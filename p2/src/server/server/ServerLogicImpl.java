package server.server;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import server.entry.BookingReq;
import server.entry.BookingResponse;
import server.entry.ServiceMsg;
import dbm.*;
import hotel.*;

public class ServerLogicImpl implements ServerLogic{
	
    @Override
    public BookingResponse postBookingList(ArrayList<BookingReq> bookingList) {
    	
    	BookingManager bm;
    	Date start = null;
    	Date end = null;
    	ArrayList<Booking> bookings = new ArrayList<Booking>();
    	for (BookingReq b : bookingList) {
    		if (start == null || b.date.before(start)) {
    			start = b.date;
    		}
    		if (end == null || b.date.after(end)) {
    			end = b.date;
    		}
    	}
    	
    	try {
			bm = new BookingManager();
			UUID bid = UUID.randomUUID();
			ArrayList<Service> services = requestAvailableService(start, end);
			HashMap<UUID, Service> serviceMap = new HashMap<UUID, Service>();
			for (Service s : services) {
			   serviceMap.put(s.getId(), s);
			}
			for (BookingReq b : bookingList) {
				Service s_t = serviceMap.get(b.serviceId);
				if (s_t.getAmount() > 0) {
					bookings.add(new Booking(bid, b.serviceId, b.date, b.email));
					s_t.setAmount(s_t.getAmount() - 1);
				} else {
					return new BookingResponse(false, "Not enough " + serviceMap.get(b.serviceId).getType() + " available.");
				}		
			}
			bm.createBookings(bookings);
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 	
        return new BookingResponse(true, null);
        //return new BookingResponse(false, "rent car");
    }

    @Override
    public ArrayList<Service> requestAvailableService(Date startDate, Date endDate) {
    	
    	ServiceManager sm;
    	BookingManager bm;
    	ArrayList<Service> services = new ArrayList<Service>();
    	ArrayList<Service> ret_services = new ArrayList<Service>();
		try {
			sm = new ServiceManager();
			services = sm.getServices();
			bm = new BookingManager();
			ArrayList<Booking> bookings = bm.filterBookingsByDates(startDate, endDate, bm.getBookings());
			
			for (Service s : services) {
				int amount = s.getAmount();		
				if (amount == Integer.MAX_VALUE) {
					ret_services.add(s);
					continue;
				}
				
				ArrayList<Booking> bs = bm.getBookingsByService(s.getId(), bookings); 
				Collections.sort(bs, new Comparator<Booking>(){
				     public int compare(Booking b1, Booking b2){
				         return b1.getDate().compareTo(b2.getDate());
				     }
				});
				
				Date current = null;
				int count = amount;
				for (Booking b : bs) {
					if (current == null) {
						current = b.getDate();
					}
					if (b.getDate().equals(current)) {
						count--;
					} else {
						if (count < amount) {
							amount = count;
							count = s.getAmount() - 1;
						}
					}
				}
				
				if (amount > 0) {
					Service s_t = s;
					s_t.setAmount(amount);
					ret_services.add(s_t);
				}
				
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
        return ret_services;
    }
}
