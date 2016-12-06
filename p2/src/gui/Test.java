package gui;

import java.util.ArrayList;
import server.entry.Service;

public class Test {
	public static void main(String args[]) throws InterruptedException{
		System.out.println("Height is: "+ Constants.windowHeight);
		System.out.println("Width is: "+ Constants.windowWidth);
		GuiClientInterface gui = new Gui();
		gui.initializeAll();
		Thread.sleep(2000);
		gui.invalidDate("date wrong!\n");
		Thread.sleep(2000);
		Service service1 = new Service(1, "Single Room", 118, true, null, 30);
		Service service2 = new Service(2, "Double Room", 128, true, null, 30);
		Service service3 = new Service(3, "Big Room", 128, true, null, 30);
		Service service4 = new Service(4, "Small Room", 128, true, null, 30);
		Service service5 = new Service(5, "rentCar", 20, false, null, 30);
		Service service6 = new Service(6, "breakfast", 5, false, null, 300);
		ArrayList<Service> serviceList= new ArrayList<Service>();
		serviceList.add(service1);
		serviceList.add(service2);
		serviceList.add(service3);
		serviceList.add(service4);
		serviceList.add(service5);
		serviceList.add(service6);
		gui.drawService(serviceList);
		Thread.sleep(20000);
		gui.lockGUIUntilServerFeedBack();
		Thread.sleep(5000);
		gui.unlockGUI();
		gui.drawSuccessDetails("Book succeededBook succeededBook succeededBook succeededBook succeededBook "
				+ "succeededBook succeededBook succeededBook succeededBook succeededBook succeededBook succeededBook succeeded!");
	}
}
