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

			List<NTPRequest> resultList = new ArrayList<>(history);
			Collections.sort(resultList, new Comparator<NTPRequest>() {
				public int compare(NTPRequest r1, NTPRequest r2) {
						Double d1 = r1.getD();
						Double d2 = r2.getD();
					    return d1.compareTo(d2);
					}
				});

			this.minNTPrequest = resultList.get(0);
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
		System.out.println("NTP Request Connection No." + (i+1) + " in the last 8 Connections is selected!");
		System.out.println("The time difference is: " + request.getO());
		System.out.println("The corresponding accuracy of the approximation is: " + request.getD());
		System.out.println("------------------------");
	}

	private void printRequest(NTPRequest request, int i) {
		System.out.println("NTP Request Connection No." + (i+1) + ":");
		System.out.println("T1: " + request.getT1());
		System.out.println("T2: " + request.getT2());
		System.out.println("T3: " + request.getT3());
		System.out.println("T4: " + request.getT4());
		System.out.println("O: " + request.getO());
		System.out.println("D: " + request.getD());
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
