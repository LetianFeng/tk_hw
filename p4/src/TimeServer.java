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
			//

			while (true) {
				Socket socket = serverSocket.accept();
				NTPRequestHandler ntpRequestHandler = new NTPRequestHandler(socket);
				ntpRequestHandler.run();
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
			this.client = client;
		}

		@Override
		public void run() {
			///

			try {
				ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
				NTPRequest request = (NTPRequest) inputStream.readObject();
				request.setT2(new Date().getTime());
				sendNTPAnswer(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void sendNTPAnswer(NTPRequest request) {
			///
			try {
				request.setT3(new Date().getTime());
				ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
				outputStream.writeObject(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
