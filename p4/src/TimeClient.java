import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Random;

public class TimeClient {
	private static String hostUrl = "127.0.0.1";
	private static int PORT = 27780;
	private Double minD;
	private NTPRequest minNTPrequest;
	private Socket socket;

	public TimeClient() {

		try {

			for (int i = 0; i < 10; i++) {
				socket = new Socket(InetAddress.getByName(hostUrl), PORT);

				minNTPrequest = new NTPRequest();
				minNTPrequest.setT1(new Date().getTime());
				sendNTPRequest(minNTPrequest);
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
				NTPRequest request = (NTPRequest) inputStream.readObject();
				request.setT4(new Date().getTime());

				System.out.println(request.getT1());
				System.out.println(request.getT2());
				System.out.println(request.getT3());
				System.out.println(request.getT4());
				System.out.println("------------------------");

				threadSleep(300);

				
				socket.close();
				
			}

			
			socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void sendNTPRequest(NTPRequest request) {
		//

		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
