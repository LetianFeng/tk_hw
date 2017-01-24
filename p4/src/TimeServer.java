import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

public class TimeServer {
	private static int PORT = 27780;
	private ServerSocket serverSocket;

	public TimeServer() {
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server started on port: " + PORT);
			int i = 1;

			while (true) {
				Socket socket = serverSocket.accept();
				communicationDelay();
				System.out.print("NTP Request Connection No." + i++ + ": new Thread(");
				(new Thread(new NTPRequestHandler(socket))).start();
				System.out.println("---------------------------------");
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				serverSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
		new TimeServer();
	}

	private class NTPRequestHandler implements Runnable {
		private Socket client;

		public NTPRequestHandler(Socket client) {
			System.out.println(this.toString() + ")");
			this.client = client;
		}

		@Override
		public void run() {
			///

			try {
				ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
				NTPRequest request = (NTPRequest) inputStream.readObject();
				request.setT2(new Date().getTime() + 1200);
				sendNTPAnswer(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void sendNTPAnswer(NTPRequest request) {
			///
			try {
				request.setT3(new Date().getTime() + 1200);
				ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
				outputStream.writeObject(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	private void communicationDelay() {
		int start = 10;
		int end = 100;
		int delay = 0;
		Random rand = new Random();
		delay = rand.nextInt(end - start + 1) + start;
		//System.out.println("Simulated Client to Server delay is: " + delay);
		threadSleep((long)delay);
	}

}
