package Client;

import java.net.MalformedURLException;
import java.rmi.*;

public interface GameClientInterface extends Remote {

	public void minionChanged(int x, int y) throws RemoteException,
			MalformedURLException, NotBoundException;

	public String getUsername() throws RemoteException;
}