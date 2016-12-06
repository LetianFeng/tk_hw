package gui;

public class Test {
	public static void main(String args[]) throws InterruptedException{
		System.out.println("Height is: "+ Constants.windowHeight);
		System.out.println("Width is: "+ Constants.windowWidth);
		GuiClientInterface gui = new Gui();
		gui.initializeAll();
		Thread.sleep(2000);
		gui.invalidDate("date wrong!\n");
		Thread.sleep(2000);
		Thread.sleep(20000);
		gui.lockGUIUntilServerFeedBack();
		Thread.sleep(5000);
		gui.unlockGUI();
		gui.drawSuccessDetails("Book succeededBook succeededBook succeededBook succeededBook succeededBook "
				+ "succeededBook succeededBook succeededBook succeededBook succeededBook succeededBook succeededBook succeeded!");
	}
}
