import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class TimeClient {
	private static String hostUrl = "127.0.0.1";
	private static int PORT = 27780;
	private static int NUM_OF_TRIES = 10;

	private Double minD;
	private NTPRequest minNTPrequest;
	private Socket socket;
	private List<NTPRequest> history;

	public TimeClient() {
		
		history = new ArrayList<>();
		try {

			NTPRequest tmpNTPRequest;

			for (int i = 0; i < NUM_OF_TRIES; i++) {
				socket = new Socket(InetAddress.getByName(hostUrl), PORT);

				tmpNTPRequest = new NTPRequest();
				tmpNTPRequest.setT1(new Date().getTime());
				sendNTPRequest(tmpNTPRequest);

				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
				NTPRequest request = (NTPRequest) inputStream.readObject();
				communicationDelay();
				request.setT4(new Date().getTime());
				request.calculateOandD();

				this.printRequest(request, i);

				if (history.size() == 8)
					history.remove(0);
				history.add(request);

				threadSleep(300);

				socket.close();
			}

			this.minD = Double.MAX_VALUE;
			for (NTPRequest request : history) {
				if (this.minD > request.getD()) {
					this.minD = request.getD();
					this.minNTPrequest = request;
				}
			}

			this.printResult(this.minNTPrequest, history.indexOf(this.minNTPrequest));
			
			socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void printResult(NTPRequest request, int i) {
		System.out.println("[Client] NTP Request Connection No." + (i+1) + " in the last 8 Connections is selected!");
		System.out.println("[Client] The time difference is: " + request.getO());
		System.out.println("[Client] The corresponding accuracy of the approximation is: " + request.getD());
		System.out.println("------------------------");
	}

	private void printRequest(NTPRequest request, int i) {
		System.out.println("[Client] NTP Request Connection No." + (i+1) + ":");
		System.out.println("[Client] T1: " + request.getT1());
		System.out.println("[Client] T2: " + request.getT2());
		System.out.println("[Client] T3: " + request.getT3());
		System.out.println("[Client] T4: " + request.getT4());
		System.out.println("[Client] O: " + request.getO());
		System.out.println("[Client] D: " + request.getD());
		System.out.println("------------------------");
	}

	private void sendNTPRequest(NTPRequest request) {
		//
		try {
			//communicationDelay();
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void communicationDelay() {
		int start = 10;
		int end = 100;
		int delay = 0;
		Random rand = new Random();
		delay = rand.nextInt(end - start + 1) + start;
		//System.out.println("Simulated Server to Client delay is: " + delay);
		threadSleep((long)delay);
	}

	private void threadSleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TimeClient();
	}

}
